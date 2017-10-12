package com.ppdai.ac.sms.consumer.core.configuration;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import org.springframework.stereotype.Component;

/**
 * Created by kiekiyang on 2017/5/16.
 */
@Component
public class NetWorkProperties {
    @ApolloConfig
    private transient Config apolloConfig;

    private int connectionTimeout;
    private int writeTimeout;
    private int readTimeout;

    public int getConnectionTimeout() {
        return apolloConfig.getIntProperty("network.config.connectionTimeout", 100);
    }

    public int getWriteTimeout() {
        return apolloConfig.getIntProperty("network.config.writeTimeout", 100);
    }

    public int getReadTimeout() {
        return apolloConfig.getIntProperty("network.config.readTimeout", 300);
    }
}
