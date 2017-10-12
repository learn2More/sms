package com.ppdai.ac.sms.api.provider.ccp.scheduler;

import com.ppdai.ac.sms.api.provider.ccp.configuration.CcpProviderProperties;
import com.ppdai.ac.sms.api.provider.ccp.domain.biz.CcpProviderBiz;
import com.ppdai.ac.sms.common.redis.DistributedLock;
import com.ppdai.ac.sms.common.redis.RedisUtil;
import com.ppdai.ac.sms.provider.core.enums.InvokeResult;
import com.ppdai.ac.sms.provider.core.response.CallResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 容联云通讯回执报告job
 * author cash
 * create 2017-05-15-15:15
 **/
@Component
public class CcpReportScheduler {

    private final static Logger logger = LoggerFactory.getLogger(CcpReportScheduler.class);

    private final String CCP_REPROT_LOCK_MARKER="SMS:CCPREPORTLOCK";

    @Autowired
    CcpProviderProperties ccpProviderProperties;

    @Autowired
    CcpProviderBiz ccpProviderBiz;

    @Autowired
    DistributedLock distributedLock;

    @Scheduled(fixedDelayString = "${ccp.report.interval:5000}")
    public void run(){
        if(distributedLock.tryLock(CCP_REPROT_LOCK_MARKER,5L)){
            logger.info("------------ccp开始拉取回执---------------");
            String providerIds=ccpProviderProperties.getProviderIds();
            if(StringUtils.isNotEmpty(providerIds)){
                String[] idArray=providerIds.split(",");
                for(String providerId:idArray){
                    CallResponse callResponse=ccpProviderBiz.getReport(providerId);
                    if(InvokeResult.FAIL.getCode()==callResponse.getResultCode()){
                        logger.error(callResponse.getResultMessage()+" providerId:"+providerId);
                    }
                }
            }
            //distributedLock.releaseLock(CCP_REPROT_LOCK_MARKER);
        }

    }

}
