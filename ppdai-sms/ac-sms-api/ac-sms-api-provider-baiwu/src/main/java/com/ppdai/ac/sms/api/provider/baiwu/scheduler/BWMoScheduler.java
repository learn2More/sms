package com.ppdai.ac.sms.api.provider.baiwu.scheduler;

import com.ppdai.ac.sms.api.provider.baiwu.configuration.BWProviderProperties;
import com.ppdai.ac.sms.api.provider.baiwu.domain.biz.BWProviderBiz;
import com.ppdai.ac.sms.provider.core.enums.InvokeResult;
import com.ppdai.ac.sms.provider.core.response.SmsResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by kiekiyang on 2017/4/24.
 * 获取百悟短信上行定时任务
 */
@Component
public class BWMoScheduler {
    private final static Logger logger = LoggerFactory.getLogger(BWMoScheduler.class);

    @Autowired
    BWProviderBiz bwProviderBiz;

    @Autowired
    BWProviderProperties bwProviderProperties;


    /*@Scheduled(cron = "0 0 0 * 12 ?")*/
    /*@Scheduled(fixedDelay = 2000) */
/*    @Scheduled(fixedDelayString = "${baiwu.moReport.interval:2000}")*/
    public void run(){
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
    }

}
