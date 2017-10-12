package com.ppdai.ac.sms.api.provider.aliyun.scheduler;

import com.ppdai.ac.sms.api.provider.aliyun.configuration.AliyunProviderProperties;
import com.ppdai.ac.sms.api.provider.aliyun.domain.biz.AliyunProviderBiz;
import com.ppdai.ac.sms.provider.core.dao.domain.SmsMessageRecordBiz;
import com.ppdai.ac.sms.provider.core.dao.domain.SmsMessageReportBiz;
import com.ppdai.ac.sms.provider.core.enums.InvokeResult;
import com.ppdai.ac.sms.provider.core.response.SmsResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 阿里云回执报告定时任务
 * author cash
 * create 2017-05-08-9:47
 **/

@Component
public class AliyunReportScheduler {

    private final static Logger logger = LoggerFactory.getLogger(AliyunReportScheduler.class);

    @Autowired
    AliyunProviderBiz aliyunProviderBiz;

    @Autowired
    SmsMessageReportBiz smsMessageReportBiz;

    @Autowired
    SmsMessageRecordBiz smsMessageRecordBiz;

    @Autowired
    AliyunProviderProperties aliyunProviderProperties;


    @Scheduled(fixedDelayString ="${aliyun.report.interval:2000}")
    /*@Scheduled(cron = "0 0 0 * 12 ?")*/
    public void getReports(){
        System.out.println("------------aliyun开始拉取回执");
        String providerIds=aliyunProviderProperties.getProviderIds();
        if(StringUtils.isNotEmpty(providerIds)){
            String[] idArray=providerIds.split(",");
            for(String providerId:idArray){
                //发送成功的回执
                SmsResponse successReportRsp=aliyunProviderBiz.getSuccessReport(providerId);
                if(InvokeResult.FAIL.getCode()==successReportRsp.getResultCode()){
                    logger.error(successReportRsp.getResultMessage()+" providerId:"+providerId);
                }

                //发送失败的回执
                SmsResponse failReportRsp=aliyunProviderBiz.getFailReport(providerId);
                if(InvokeResult.FAIL.getCode()==failReportRsp.getResultCode()){
                    logger.error(failReportRsp.getResultMessage()+" providerId:"+providerId);
                }
            }
        }
    }

}
