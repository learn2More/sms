package com.ppdai.ac.sms.api.provider.aliyun.configuration;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import org.springframework.stereotype.Component;

/**
 * aliyun配置
 * author cash
 * create 2017-06-05-16:23
 **/

@Component
public class AliyunProviderProperties {

    @ApolloConfig
    private transient Config apolloConfig;

    public String getProviderIds() {
        return apolloConfig.getProperty("aliyun.provider.ids","3");
    }
}
