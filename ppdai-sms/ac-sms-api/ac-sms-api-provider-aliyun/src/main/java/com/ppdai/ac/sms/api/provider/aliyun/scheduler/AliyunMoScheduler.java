package com.ppdai.ac.sms.api.provider.aliyun.scheduler;

import com.ppdai.ac.sms.api.provider.aliyun.configuration.AliyunProviderProperties;
import com.ppdai.ac.sms.api.provider.aliyun.domain.biz.AliyunProviderBiz;
import com.ppdai.ac.sms.provider.core.dao.domain.SmsMessageMoRecordBiz;
import com.ppdai.ac.sms.provider.core.enums.InvokeResult;
import com.ppdai.ac.sms.provider.core.response.SmsResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 阿里云上行定时任务
 * author cash
 * create 2017-05-08-9:46
 **/
@Component
public class AliyunMoScheduler {

    private Logger logger= LoggerFactory.getLogger(AliyunMoScheduler.class);


    @Autowired
    AliyunProviderBiz aliyunProviderBiz;

    @Autowired
    SmsMessageMoRecordBiz smsMessageMoRecordBiz;

    @Autowired
    AliyunProviderProperties aliyunProviderProperties;


    @Scheduled(fixedDelayString ="${aliyun.moReport.interval:2000}")
    /*@Scheduled(cron = "0 0 0 * 12 ?")*/
    public void getDelivers(){
        System.out.println("------------aliyun开始拉取上行");
        String providerIds=aliyunProviderProperties.getProviderIds();
        if(StringUtils.isNotEmpty(providerIds)){
            String[] idArray=providerIds.split(",");
            for(String providerId:idArray){
                SmsResponse smsResponse= aliyunProviderBiz.getDelivers(providerId);
                if(InvokeResult.FAIL.getCode()==smsResponse.getResultCode()){
                    logger.error("阿里云拉取上行记录异常:"+ smsResponse.getResultMessage()+" providerId:"+providerId);
                }
            }
        }
    }
}
