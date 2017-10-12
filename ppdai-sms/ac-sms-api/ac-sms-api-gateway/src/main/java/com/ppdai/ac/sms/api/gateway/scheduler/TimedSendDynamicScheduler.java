package com.ppdai.ac.sms.api.gateway.scheduler;

import com.ppdai.ac.sms.api.gateway.configuration.GatewayProperties;
import com.ppdai.ac.sms.api.gateway.dao.domain.TimedSendBiz;
import com.ppdai.ac.sms.common.redis.DistributedLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 *拉取回执定时任务 cron 支持apollo动态修改
 *author cash
 *create 2017/7/26-11:28
**/

@Component
public class TimedSendDynamicScheduler implements SchedulingConfigurer {

    private final static Logger logger = LoggerFactory.getLogger(TimedSendDynamicScheduler.class);

    private final String GATEWAY_TIMED_SEND_MARKER="SMS:GATEWAY_TIMED_SEND_LOCK";


    @Autowired
    GatewayProperties gatewayProperties;

    @Autowired
    DistributedLock distributedLock;


    @Autowired
    TimedSendBiz timedSendBiz;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addTriggerTask(() -> {
            if(distributedLock.tryLock(GATEWAY_TIMED_SEND_MARKER, Long.valueOf(gatewayProperties.getTimedSendSchedulerInterval()))){
                logger.info("------------定时发送job开始执行-------------");
                timedSendBiz.timedSend();
            }
        },(TriggerContext triggerContext) -> {
                // 定时任务触发，可修改定时任务的执行周期
                CronTrigger trigger = new CronTrigger(gatewayProperties.getTimedSendSchedulerCron());
                Date nextExecDate = trigger.nextExecutionTime(triggerContext);
                return nextExecDate;
        });

    }
}
