package com.ppdai.ac.sms.api.provider.baiwu.scheduler;

import com.ppdai.ac.sms.api.provider.baiwu.configuration.BWProviderProperties;
import com.ppdai.ac.sms.api.provider.baiwu.domain.biz.BWProviderBiz;
import com.ppdai.ac.sms.provider.core.enums.InvokeResult;
import com.ppdai.ac.sms.provider.core.response.SmsResponse;
import org.apache.commons.lang.StringUtils;
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
 *拉取上行定时任务 cron 支持apollo动态修改
 *author cash
 *create 2017/7/26-11:23
**/

@Component
public class BWMoDynamicScheduler implements SchedulingConfigurer {
    private final static Logger logger = LoggerFactory.getLogger(BWMoDynamicScheduler.class);

    @Autowired
    BWProviderBiz bwProviderBiz;

    @Autowired
    BWProviderProperties bwProviderProperties;


    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addTriggerTask(()-> {
                logger.info("------------百悟开始拉取上行-----------");
                String providerIds=bwProviderProperties.getProviderIds();
                if(StringUtils.isNotEmpty(providerIds)){
                    String[] idArray=providerIds.split(",");
                    for(String providerId:idArray){
                        SmsResponse smsResponse=bwProviderBiz.getDelivers(providerId);
                        if(InvokeResult.FAIL.getCode()==smsResponse.getResultCode()){
                            logger.error(smsResponse.getResultMessage()+" providerId:"+providerId);
                        }
                    }
                }
        }, (TriggerContext triggerContext) -> {
                // 定时任务触发，可修改定时任务的执行周期
                CronTrigger trigger = new CronTrigger(bwProviderProperties.getMoReportSchedulerCron());
                Date nextExecDate = trigger.nextExecutionTime(triggerContext);
                return nextExecDate;
        });
    }
}
