package com.ppdai.ac.sms.api.provider.ccp.configuration;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import org.springframework.stereotype.Component;

/**
 * 百悟服务商配置属性
 * author cash
 * create 2017-05-25-15:12
 **/
@Component
public class CcpProviderProperties {

    @ApolloConfig
    private transient Config apolloConfig;

   /* private String providerIds;
    private int readTimeout;
    private int connectTimeout;
    private int writeTimeout;*/

    public String getProviderIds() {
        return apolloConfig.getProperty("ccp.provider.ids","5,6");
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
