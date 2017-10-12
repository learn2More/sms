package com.ppdai.ac.sms.api.provider.jslt.configuration;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import org.springframework.stereotype.Component;

/**
 * 江苏联通服务商配置属性
 * author cash
 * create 2017-05-25-13:25
 **/
@Component
public class JSLTProviderProperties {

    @ApolloConfig
    private transient Config apolloConfig;


    public String getProviderIds() {
        return apolloConfig.getProperty("jslt.provider.ids","10,11");
    }

    public int getReadTimeout() {
        return apolloConfig.getIntProperty("feign.okhttp.readTimeout",4);
    }

    public int getConnectTimeout() {
        return apolloConfig.getIntProperty("feign.okhttp.connectTimeout",4);
    }

    public int getWriteTimeout() {
        return apolloConfig.getIntProperty("feign.okhttp.writeTimeout",4);
    }

}
