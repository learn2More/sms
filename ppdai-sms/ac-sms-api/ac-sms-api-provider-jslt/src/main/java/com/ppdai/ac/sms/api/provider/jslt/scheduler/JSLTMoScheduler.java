package com.ppdai.ac.sms.api.provider.jslt.scheduler;

import com.ppdai.ac.sms.api.provider.jslt.configuration.JSLTProviderProperties;
import com.ppdai.ac.sms.api.provider.jslt.domain.biz.JSLTProviderBiz;
import com.ppdai.ac.sms.provider.core.enums.InvokeResult;
import com.ppdai.ac.sms.provider.core.response.SmsResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 江苏联通拉取上行job
 * author cash
 * create 2017-05-04-20:14
 **/

@Component
public class JSLTMoScheduler {

    private final static Logger logger = LoggerFactory.getLogger(JSLTMoScheduler.class);

    @Autowired
    JSLTProviderBiz jsltProviderBiz;

    @Autowired
    JSLTProviderProperties jsltProviderProperties;


    /*@Scheduled(cron = "0 0 0 * 12 ?")*/
    /*@Scheduled(fixedDelay = 2000) */
    @Scheduled(fixedDelayString = "${jslt.moReport.interval:2000}")
    public void run(){
        logger.info("------------江苏联通开始拉取上行-----------");
        String providerIds=jsltProviderProperties.getProviderIds();
        if(StringUtils.isNotEmpty(providerIds)){
            String[] idArray=providerIds.split(",");
            for(String providerId:idArray){
                SmsResponse smsResponse=jsltProviderBiz.getDelivers(providerId);
                if(InvokeResult.FAIL.getCode()==smsResponse.getResultCode()){
                    logger.error(smsResponse.getResultMessage()+" providerId:"+providerId);
                }
            }
        }
    }

}

