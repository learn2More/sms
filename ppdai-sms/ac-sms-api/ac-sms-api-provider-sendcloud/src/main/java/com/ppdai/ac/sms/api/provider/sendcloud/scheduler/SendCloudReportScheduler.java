package com.ppdai.ac.sms.api.provider.sendcloud.scheduler;

import com.ppdai.ac.sms.api.provider.sendcloud.configuration.SendCloudProviderProperties;
import com.ppdai.ac.sms.api.provider.sendcloud.domain.biz.SendCloudBiz;
import com.ppdai.ac.sms.common.redis.DistributedLock;
import com.ppdai.ac.sms.provider.core.enums.InvokeResult;
import com.ppdai.ac.sms.provider.core.response.SmsResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by zhongyunrui on 2017/7/30.
 * 获取SendCloud回执定时任务
 */
@Component
public class SendCloudReportScheduler {

    private final static Logger logger = LoggerFactory.getLogger(SendCloudReportScheduler.class);
    private final String SENDCLOUD_REPROT_LOCK_MARKER="SMS:SENDCLOUDREPORTLOCK";

    @Autowired
    SendCloudBiz sendCloudBiz;
    @Autowired
    SendCloudProviderProperties sendCloudProviderProperties;

    @Autowired
    DistributedLock distributedLock;

    /*@Scheduled(cron = "0 0 0 * 12 ?")*/
    /*@Scheduled(fixedDelay = 3000)*/
    @Scheduled(fixedDelayString = "${sendcloud.report.interval:5000}")
    public void run(){
        if(distributedLock.tryLock(SENDCLOUD_REPROT_LOCK_MARKER,5L)) {
            logger.info("------------SendCloud拉取回执-------------");
            String providerIds = sendCloudProviderProperties.getProviderIds();
            if (StringUtils.isNotEmpty(providerIds)) {
                String[] idArray = providerIds.split(",");
                for (String providerId : idArray) {
                    try {
                        SmsResponse smsResponse = sendCloudBiz.getReports(providerId);
                        if (InvokeResult.FAIL.getCode() == smsResponse.getResultCode()) {
                            logger.error(smsResponse.getResultMessage() + " providerId:" + providerId);
                        }
                    } catch (Exception ex) {
                        logger.error(String.format("SendCloudReportScheduler error,providerId:%s", providerId), ex);
                    }
                }
            }
        }
    }
}
