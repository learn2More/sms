package com.ppdai.ac.sms.api.provider.techown.scheduler;

import com.ppdai.ac.sms.api.provider.techown.configuration.TechownProviderProperties;
import com.ppdai.ac.sms.api.provider.techown.domain.biz.TechownProviderBiz;
import com.ppdai.ac.sms.provider.core.enums.InvokeResult;
import com.ppdai.ac.sms.provider.core.response.SmsResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *天畅 回执、上行job
 *公用同一个接口 op区分: mo上行  dr回执
 *author cash
 *create 2017/7/11-11:27
**/

@Component
public class TechownScheduler {

    private final static Logger logger = LoggerFactory.getLogger(TechownScheduler.class);

    @Autowired
    TechownProviderBiz techownProviderBiz;

    @Autowired
    TechownProviderProperties techownProviderProperties;


    /*@Scheduled(fixedDelayString = "${techown.report.interval:5000}")*/
    /*@Scheduled(fixedDelayString = "5000")*/
    public void run(){
        logger.info("------------techown 开始拉取回执---------------");
        String providerIds=techownProviderProperties.getProviderIds();
        if(StringUtils.isNotEmpty(providerIds)){
            String[] idArray=providerIds.split(",");
            for(String providerId:idArray){
                /*SmsResponse smsResponse=techownProviderBiz.getReport(providerId);
                if(InvokeResult.FAIL.getCode()==smsResponse.getResultCode()){
                    logger.error(String.format("techown providerId: %s,拉取报告异常： %s",providerId,smsResponse.getResultMessage()));
                }*/
                techownProviderBiz.getReportAsync(providerId);
            }
        }
    }

}
