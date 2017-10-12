package com.ppdai.ac.sms.provider.core.configuration;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import org.springframework.stereotype.Component;

/**
 * 公共属性
 * author cash
 * create 2017-05-25-15:12
 **/
@Component
public class CommonProperties {

    @ApolloConfig
    private transient Config apolloConfig;

    //获取退订标识
    public String getUnsubscribeMarker() {
        return apolloConfig.getProperty("sms.unsubscribe.marker","bzjs,N");
    }

    public int getRecordSeveralDaysAgo() {
        return apolloConfig.getIntProperty("sms.findRecord.severalDaysAgo",3);
    }



}
