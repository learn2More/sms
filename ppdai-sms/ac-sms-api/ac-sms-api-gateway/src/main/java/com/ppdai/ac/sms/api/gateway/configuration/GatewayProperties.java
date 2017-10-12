package com.ppdai.ac.sms.api.gateway.configuration;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import org.springframework.stereotype.Component;

/**
 * 号段配置
 * author cash
 * create 2017-07-07-13:23
 **/

@Component
public class GatewayProperties {
    @ApolloConfig
    private transient Config apolloConfig;

    private String chinaMobile;
    private String chinaUnicom;
    private String chinaTelecom;

    private int batchMaxSend;//最大的批量发送量

    public String[] getChinaMobile() {
        return apolloConfig.getArrayProperty("message.phone.operator.chinamobile", ",", new String[]{});
    }

    public String[] getChinaUnicom() {
        return apolloConfig.getArrayProperty("message.phone.operator.chinaunicom", ",", new String[]{});
    }

    public String[] getChinaTelecom() {

        return apolloConfig.getArrayProperty("message.phone.operator.chinatelecom", ",", new String[]{});
    }

    public int getBatchMaxSend(){
        return apolloConfig.getIntProperty("message.batch.send.max.num",1000);
    }

    public int getBatchSendLimit(){
        return apolloConfig.getIntProperty("message.batch.send.limit.num",100);
    }

    public int getBatchSendNotifyEmailFlag(){
        return apolloConfig.getIntProperty("message.batch.send.notify.email",0);
    }

    public String getSendMailAddr(){//邮件发件人地址
        return apolloConfig.getProperty("spring.mail.username","");
    }

    public String getTimedSendSchedulerCron(){
        return apolloConfig.getProperty("message.timed.send.cron","0/5 * * * * ?");
    }

    public int getTimedSendSchedulerInterval(){
        return apolloConfig.getIntProperty("message.timed.send.interval",5);
    }

    public String getCasSystemAppId(){
        return apolloConfig.getProperty("system_appId","");
    }

    public String getCasSystemIsProduct(){
        return apolloConfig.getProperty("system_IsProduct","");
    }

}
