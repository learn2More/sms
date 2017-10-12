package com.ppdai.ac.sms.api.provider.innerxw.scheduler;


import com.ppdai.ac.sms.api.provider.innerxw.configuration.XWProviderProperties;
import com.ppdai.ac.sms.api.provider.innerxw.domain.biz.XWProviderBiz;
import com.ppdai.ac.sms.provider.core.enums.InvokeResult;
import com.ppdai.ac.sms.provider.core.response.SmsResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * 玄武拉取上行job
 * author cash
 * create 2017-05-04-20:14
 **/

@Service
public class XWMoScheduler {
    private final static Logger logger = LoggerFactory.getLogger(XWMoScheduler.class);

    @Autowired
    XWProviderBiz xwProviderBiz;

    @Autowired
    XWProviderProperties xwProviderProperties;


    /*@Scheduled(cron = "0 0 0 * 12 ?")*/
    @Scheduled(fixedDelayString = "${innerxw.moReport.interval:2000}")
    public void run(){
        System.out.println("------------玄武开始拉取上行");
        String providerIds=xwProviderProperties.getProviderIds();
        if(StringUtils.isNotEmpty(providerIds)){
            String[] idArray=providerIds.split(",");
            for(String providerId:idArray){
                SmsResponse smsResponse=xwProviderBiz.getDelivers(providerId);
                if(InvokeResult.FAIL.getCode()==smsResponse.getResultCode()){
                    logger.error(smsResponse.getResultMessage());
                }
            }
        }

    }

}
