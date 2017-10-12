package com.ppdai.ac.sms.api.provider.innerxw.configuration;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import org.springframework.stereotype.Component;

/**
 * 玄武 内部平台 服务商配置属性
 * author cash
 * create 2017-05-25-13:25
 **/
@Component
public class XWProviderProperties {



    @ApolloConfig
    private transient Config apolloConfig;


    public String getProviderIds() {
        return apolloConfig.getProperty("innerxw.provider.ids","7,8,9");
    }

    public String getCmHost() {
        return apolloConfig.getProperty("provider.innerxw.cm.host","");
    }

    public int getCmPort() {
        return apolloConfig.getIntProperty("provider.innerxw.cm.port",8090);
    }

    public String getWsHost() {
        return apolloConfig.getProperty("provider.innerxw.ws.host","");
    }

    public int getWsPort() {
        return apolloConfig.getIntProperty("provider.innerxw.ws.port", 8088);
    }

    public int getPerGetMoReportNumber() {
        return apolloConfig.getIntProperty("provider.innerxw.perGetReportNumber",100);
    }

    public int getPerGetReportNumber() {
        return apolloConfig.getIntProperty("provider.innerxw.perGetMoReportNumber",100);
    }
}
