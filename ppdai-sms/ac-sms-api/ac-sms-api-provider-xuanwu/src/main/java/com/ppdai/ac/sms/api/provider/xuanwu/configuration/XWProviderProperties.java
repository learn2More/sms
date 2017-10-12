package com.ppdai.ac.sms.api.provider.xuanwu.configuration;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import org.springframework.stereotype.Component;

/**
 * 玄武服务商配置属性
 * author cash
 * create 2017-05-25-13:25
 **/
@Component
public class XWProviderProperties {

    @ApolloConfig
    private transient Config apolloConfig;

    public String getProviderIds() {
        return apolloConfig.getProperty("xuanwu.provider.ids","2,3,4");
    }

    public String getCmHost() {
        return apolloConfig.getProperty("provider.xuanwu.cm.host","");
    }

    public int getCmPort() {
        return apolloConfig.getIntProperty("provider.xuanwu.cm.port",9080);
    }

    public String getWsHost() {
        return apolloConfig.getProperty("provider.xuanwu.ws.host","");
    }

    public int getWsPort() {
        return apolloConfig.getIntProperty("provider.xuanwu.ws.port", 9070);
    }

    public int getPerGetMoReportNumber() {
        return apolloConfig.getIntProperty("provider.xuanwu.perGetReportNumber",100);
    }

    public int getPerGetReportNumber() {
        return apolloConfig.getIntProperty("provider.xuanwu.perGetMoReportNumber",100);
    }
}
