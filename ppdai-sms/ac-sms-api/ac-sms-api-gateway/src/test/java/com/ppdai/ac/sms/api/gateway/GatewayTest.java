package com.ppdai.ac.sms.api.gateway;

import com.ppdai.ac.sms.api.gateway.dao.domain.SecurityCodeBiz;
import com.ppdai.ac.sms.common.redis.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * author cash
 * create 2017-06-08-18:48
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationBoot.class)
public class GatewayTest {
    private static final Logger logger= LoggerFactory.getLogger(GatewayTest.class);

    @Autowired
    RedisUtil<String,String> redisUtil;

    @Autowired
    RedisTemplate<String,String> redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    SecurityCodeBiz securityCodeBiz;




    @Test
    public void providerConfigCacheTest(){

    }

    @Test
    public void testRedis(){
        for(int i=0;i<20;i++){
            String key=String.format("yyyyyyy:yyyyyyyyy %s",i);
            redisUtil.addzSet(key, i+"", System.currentTimeMillis());
        }

    }

    @Test
    public void testRedis2(){
        for(int i=0;i<100;i++){
            String key=String.format("pppppppppppppppppppp:yyyyyyyyy %s",i);
            boolean reValue= redisTemplate.opsForZSet().add(key, i+"", System.currentTimeMillis());
            System.out.println(reValue);
            redisTemplate.expire(key,200, TimeUnit.SECONDS);
        }

    }

    @Test
    public void testRedis3(){
        LocalDateTime localDateTime=LocalDateTime.now();
        LocalDateTime l=localDateTime.plusHours(1);
        for(int i=0;i<40;i++){
            securityCodeBiz.createSecurityCode(100000000+i,"10000",l);
        }
    }

    @Test
    public void testRedis4(){
        boolean b=securityCodeBiz.verifySecurityCode(100000023,"10000","917129",false);
        System.out.println("------------------------ "+b);
    }


    @Test
    public void test(){
        for(int i=0;i<50;i++){
            String cacheKey = String.format("MESSAGE:SC-%s-%s ", 333333333, "1000"+i);
            String securityCode="299993";
            redisUtil.set(cacheKey,securityCode,1000L);
        }
        //String key=String.format("MESSAGE:SC-%s-%s", 189999999, "10001");
        /*String key="MESSAGE:SC-579999999-100015";
        boolean b=redisTemplate.hasKey(key);
        if(b){
            BoundValueOperations<String, String> valueOper = redisTemplate.boundValueOps(key);
            String s=valueOper.get();
            s="839393";
            System.out.println("-----------------------"+s);
            String s1=s.replaceAll("\"","");
            System.out.println("-----------------------"+s1);
        }*/
    }



}
