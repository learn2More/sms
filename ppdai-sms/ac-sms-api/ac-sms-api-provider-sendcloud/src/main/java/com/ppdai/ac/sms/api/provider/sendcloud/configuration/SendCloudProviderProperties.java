package com.ppdai.ac.sms.api.provider.sendcloud.configuration;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import org.springframework.stereotype.Component;

/**
 * SendCloud服务商配置属性
 * author zhongyunrui
 * create 2017-07-30
 **/
@Component
public class SendCloudProviderProperties {

    @ApolloConfig
    private transient Config apolloConfig;

    public String getProviderIds() {
        return apolloConfig.getProperty("sendcloud.provider.ids","30");
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

    public  String  getReportSuccessStatusKey(){
        return apolloConfig.getProperty("sendcloud.getreports.successstatuskey","送达");
    }

    public  String  getReportSendingStatusKey(){
        return apolloConfig.getProperty("sendcloud.getreports.sendingstatuskey","请求中");
    }

}
