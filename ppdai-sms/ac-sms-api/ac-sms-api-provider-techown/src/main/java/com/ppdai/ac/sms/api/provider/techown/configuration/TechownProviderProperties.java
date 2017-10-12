package com.ppdai.ac.sms.api.provider.techown.configuration;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import org.springframework.stereotype.Component;

/**
 *天畅服务商配置属性
 *author cash
 *create 2017/7/11-11:19
**/

@Component
public class TechownProviderProperties {

    @ApolloConfig
    private transient Config apolloConfig;

    public String getProviderIds() {
        return apolloConfig.getProperty("techown.provider.ids","12");
    }

    public int getReadTimeout() {
        return apolloConfig.getIntProperty("feign.okhttp.readTimeout",5);
    }

    public int getConnectTimeout() {
        return apolloConfig.getIntProperty("feign.okhttp.connectTimeout",5);
    }

    public int getWriteTimeout() {
        return apolloConfig.getIntProperty("feign.okhttp.writeTimeout",5);
    }

    public int getPerGetReportNumber() {
        return apolloConfig.getIntProperty("techown.report.perGetReportNumber",100);
    }

    /**
     * 往 前几天 找寻对应记录  可配置
     * @return
     */
    public int getRecordSeveralDaysAgo() {
        return apolloConfig.getIntProperty("techown.findRecord.severalDaysAgo",3);
    }

    public String getReportCron() {
        return apolloConfig.getProperty("techown.report.cron","0/2 * * * * ?");
    }
}
