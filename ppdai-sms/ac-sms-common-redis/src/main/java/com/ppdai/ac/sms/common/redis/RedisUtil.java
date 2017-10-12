package com.ppdai.ac.sms.common.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by kiekiyang on 2017/4/12.
 */
@Component
public class RedisUtil<K,V> {

    @Autowired
    private RedisTemplate<K, V> redisTemplate;

    public RedisTemplate<K, V> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<K, V> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void set(final K key, final V value, final long expiredTime) {
        BoundValueOperations<K, V> valueOper = redisTemplate.boundValueOps(key);
        if (expiredTime <= 0) {
            valueOper.set(value);
        } else {
            valueOper.set(value, expiredTime, TimeUnit.SECONDS);
        }
    }

    public V get(final K key) {
        BoundValueOperations<K, V> valueOper = redisTemplate.boundValueOps(key);
        return valueOper.get();
    }


    public V getSet(final K key, final V value){
        BoundValueOperations<K, V> valueOper = redisTemplate.boundValueOps(key);
        //getAndSet返回旧值
        return valueOper.getAndSet(value);
    }

    public void del(K key) {
        if (redisTemplate.hasKey(key)) {
            redisTemplate.delete(key);
        }
    }

    public void incr(K key) {
        redisTemplate.opsForValue().increment(key, 1);
        //明日凌晨
        LocalTime now=LocalTime.now();
        int expireSecond=24*3600-(3600*now.getHour()+60*now.getMinute()+now.getSecond());

        redisTemplate.expire(key,expireSecond,TimeUnit.SECONDS);

    }

    /**
     * 添加一个成员(有效期当天)
     *
     * @param key
     * @param value
     * @param timestamp
     * @return
     */
    public synchronized boolean addzSet(final K key, final V value, final long timestamp) {
        boolean reValue;
        boolean containsKey = redisTemplate.hasKey(key);
        reValue = redisTemplate.opsForZSet().add(key, value, timestamp);
        if (reValue && !containsKey) {
            //明日凌晨
            LocalTime now=LocalTime.now();
            int expireSecond=24*3600-(3600*now.getHour()+60*now.getMinute()+now.getSecond());

            redisTemplate.expire(key,expireSecond,TimeUnit.SECONDS);
            reValue = true;
        }
        return reValue;
    }

    /**
     * 0表示第一个成员，-1表示最后一个成员。WITHSCORES选项表示返回的结果中包含每个成员及其分数，否则只返回成员。
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<ZSetOperations.TypedTuple<V>> rangeWithScores(final K key, final long start, long end) {
        return redisTemplate.opsForZSet().rangeWithScores(key, start, end);
    }

    /**
     * 返回与Key关联的Sorted-Set中，分数满足表达式1 <= score <= 2的成员的数量
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public long zCount(final K key, final long start, final long end) {
        return redisTemplate.opsForZSet().count(key, start, end);
    }

    /**
     * 获取Key键中成员的数量。
     *
     * @param key
     * @return
     */
    public long zCard(final K key) {
        return redisTemplate.opsForZSet().zCard(key);
    }

    public Boolean check(K key, V value) {
        Boolean flag = false;
        if (redisTemplate.hasKey(key)) {
            if (value.equals(get(key))) {
                flag = true;
            }
        }
        return flag;
    }

    public boolean setNX(final K key,V value){
        BoundValueOperations<K, V> valueOper = redisTemplate.boundValueOps(key);
        boolean b= valueOper.setIfAbsent(value);
        return b;
    }




}
