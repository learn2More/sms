package com.ppdai.ac.sms.api.gateway;

import com.ppdai.ac.sms.common.redis.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Set;

/**
 * Created by kiekiyang on 2017/4/27.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SendIntervalTest {

    @Autowired
    RedisUtil redisUtil;

    @Test
    public void checkSendIntervalTest(){
        checkSendInterval("SMS:SENDMESSAGE-1,13916818820,bus_yewu1,tpl_xxx_test",5,3,5);

    }

    private boolean checkSendInterval(String key, int intervalSecond, int intervalMaxCount, int intervalTotalMaxCount) {
        if (intervalSecond <= 0 && intervalMaxCount <= 0 && intervalTotalMaxCount <= 0)
            return true;
        /*校验时间间隔*/
        if (intervalSecond > 0) {
            long firstSendTimespan = 0;
            long lastSendTimespan = 0;
            Set<ZSetOperations.TypedTuple<String>> sendList = redisUtil.rangeWithScores(key, 0, -1);
            for (ZSetOperations.TypedTuple<String> tuple : sendList) {
                if (Long.valueOf(tuple.getScore().toString()) > lastSendTimespan)
                    lastSendTimespan = Long.valueOf(tuple.getScore().toString());
                if (Long.valueOf(tuple.getScore().toString()) <= firstSendTimespan)
                    lastSendTimespan = Long.valueOf(tuple.getScore().toString());
            }

//            long start = Timestamp.valueOf(LocalDateTime.now().minusSeconds(maxSendTimespan)).getTime();
//            long end = Timestamp.valueOf(LocalDateTime.now()).getTime();
//            LocalDateTime.p
//            LocalDateTime localDateTime = new LocalDateTime.;
            if (sendList.size() <= 0)
                return true;
            LocalDateTime start = LocalDateTime.now().minusSeconds(lastSendTimespan);
            LocalDateTime end = LocalDateTime.now();
            Duration duration = Duration.between(start, end);

            if (duration.getSeconds() < intervalSecond) {
                return false;
            }

            start = LocalDateTime.now().minusSeconds(firstSendTimespan);
            end = LocalDateTime.now();
            long sencCount = redisUtil.zCount(key, start.toEpochSecond(ZoneOffset.UTC), end.toEpochSecond(ZoneOffset.UTC));
            if (sencCount > intervalMaxCount)
                return false;
        }
        /*校验当天总发送次数*/
        if (intervalTotalMaxCount > 0) {
            int currentDayHour = LocalDateTime.now().getHour();
//            long start = Timestamp.valueOf(LocalDateTime.now().minusHours(currentDayHour)).getTime();
//            long end = Timestamp.valueOf(LocalDateTime.now()).getTime();
            LocalDateTime start = LocalDateTime.now().minusHours(currentDayHour);
            LocalDateTime end = LocalDateTime.now();

            long sencCount = redisUtil.zCount(key, start.toEpochSecond(ZoneOffset.UTC), end.toEpochSecond(ZoneOffset.UTC));
            if (sencCount > intervalTotalMaxCount)
                return false;
        }
        return true;
    }
}
