package com.ppdai.ac.sms.consumer.core.configuration;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;

/**
 * Created by kiekiyang on 2017/5/19.
 */
public class RetryProperties {
    @ApolloConfig
    private transient Config apolloConfig;

    private int contentType;

    private int retryMaxCount;

    public int getContentType() {

        return apolloConfig.getIntProperty("sms.message.retry.contenttype", 2);
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }

    public int getRetryMaxCount() {
        return apolloConfig.getIntProperty("sms.message.retry.maxcount", 3);
    }

    public void setRetryMaxCount(int retryMaxCount) {
        this.retryMaxCount = retryMaxCount;
    }
}
