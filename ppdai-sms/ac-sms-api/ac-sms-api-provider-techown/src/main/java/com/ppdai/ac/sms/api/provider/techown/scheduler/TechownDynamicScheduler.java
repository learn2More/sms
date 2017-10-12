package com.ppdai.ac.sms.api.provider.techown.scheduler;

import com.ppdai.ac.sms.api.provider.techown.configuration.TechownProviderProperties;
import com.ppdai.ac.sms.api.provider.techown.domain.biz.TechownProviderBiz;
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
 *天畅 回执、上行job, 支持apollo动态更改 cron
 *公用同一个接口 op区分: mo上行  dr回执
 *author cash
 *create 2017/7/11-11:27
**/

@Component
public class TechownDynamicScheduler implements SchedulingConfigurer {

    private final static Logger logger = LoggerFactory.getLogger(TechownDynamicScheduler.class);

    @Autowired
    TechownProviderBiz techownProviderBiz;

    @Autowired
    TechownProviderProperties techownProviderProperties;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addTriggerTask(()-> {
            logger.info("------------techown 开始拉取回执---------------");
            String providerIds=techownProviderProperties.getProviderIds();
            if(StringUtils.isNotEmpty(providerIds)){
                String[] idArray=providerIds.split(",");
                for(String providerId:idArray){
                    techownProviderBiz.getReportAsync(providerId);
                }
            }
        }, (TriggerContext triggerContext) -> {
            // 定时任务触发，可修改定时任务的执行周期
            CronTrigger trigger = new CronTrigger(techownProviderProperties.getReportCron());
            Date nextExecDate = trigger.nextExecutionTime(triggerContext);
            return nextExecDate;
        });
    }
}
