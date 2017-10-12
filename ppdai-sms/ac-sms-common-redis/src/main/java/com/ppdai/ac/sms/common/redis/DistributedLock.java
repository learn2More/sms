package com.ppdai.ac.sms.common.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * redis实现分布式锁
 * author cash
 * create 2017-06-20-15:19
 **/
@Component
public class DistributedLock {
    @Autowired
    RedisUtil redisUtil;

    /**
     * 获取锁
     * @param key
     * @param timeout
     * @return
     */
    public boolean tryLock(String key,Long timeout){
        long timestamp = System.currentTimeMillis()+timeout*1000;
        boolean b=redisUtil.setNX(key,timestamp);
        long now = System.currentTimeMillis();
        if(b||((now>(Long)redisUtil.get(key))&&(now>(long)redisUtil.getSet(key,timestamp)))){
            return true;
        }
        return false;
    }


    /**
     * 释放锁
     * @param key
     */
    public void releaseLock(String key){
        redisUtil.del(key);
    }
}
