package com.ppdai.ac.sms.api.message.consumer.nomal.configuration;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import org.springframework.stereotype.Component;

/**
 * Created by kiekiyang on 2017/5/16.
 */
@Component
public class MessageQueueProperties {
    @ApolloConfig
    private transient Config apolloConfig;

    private String alarmEmail;
    private String groupName;

    public String getAlarmEmail() {
        return apolloConfig.getProperty("sms.message.queue.alarmemai","yanglei@ppdai.com");
    }

    public void setAlarmEmail(String alarmEmail) {
        this.alarmEmail = alarmEmail;
    }

    public String getGroupName() {
        return apolloConfig.getProperty("sms.message.queue.groupname","MessageNomalSub");
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
