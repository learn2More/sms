package com.ppdai.ac.sms.api.gateway.configuration;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by kiekiyang on 2017/5/16.
 */
@Component
public class MessageQueueProperties {

    @ApolloConfig
    private transient Config apolloConfig;

    private String alarmEmail;
    private int messageConatentMaxSize;

    private String messageCodeTopic;

    private String messageNomalTopic;
    private int codeExpireTime;

    public String getAlarmEmail() {
        return apolloConfig.getProperty("sms.message.queue.alarmemai", "yanglei@ppdai.com");
    }

    public int getMessageConatentMaxSize() {
        return apolloConfig.getIntProperty("sms.message.content.maxsize", 500);
    }

    public String getMessageCodeTopic() {
        return apolloConfig.getProperty("message.code.topic", "MessageCode");
    }

    public String getMessageNomalTopic() {
        return apolloConfig.getProperty("messageNomalTopic", "MessageNomal");
    }

    public int getCodeExpireTime() {
        return apolloConfig.getIntProperty("sms.message.code.expiretime", 350);
    }

    public Config getApolloConfig() {
        return apolloConfig;
    }

    public void setApolloConfig(Config apolloConfig) {
        this.apolloConfig = apolloConfig;
    }

    public void setAlarmEmail(String alarmEmail) {
        this.alarmEmail = alarmEmail;
    }

    public void setMessageConatentMaxSize(int messageConatentMaxSize) {
        this.messageConatentMaxSize = messageConatentMaxSize;
    }

    public void setMessageCodeTopic(String messageCodeTopic) {
        this.messageCodeTopic = messageCodeTopic;
    }

    public void setMessageNomalTopic(String messageNomalTopic) {
        this.messageNomalTopic = messageNomalTopic;
    }

    public void setCodeExpireTime(int codeExpireTime) {
        this.codeExpireTime = codeExpireTime;
    }
}
