package com.ppdai.ac.sms.api.gateway.configuration;

import com.ppdai.messagequeue.MessageQueueFactory;
import com.ppdai.messagequeue.producer.sync.SyncProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * 拍拍贷消息2.0配置
 * Created by kiekiyang on 2017/4/26.
 */
@Configuration
public class MessageQueueConfig {

    @Autowired
    MessageQueueProperties messageQueueProperties;

    @Bean
    public MessageQueueProperties messageQueueProperties() {
        return new MessageQueueProperties();
    }

    @Bean
    public SyncProducer syncProducer() throws Exception {
        Properties properties = new Properties();
        properties.put("send_error_alarm_emails", messageQueueProperties.getAlarmEmail());//必须参数

        return MessageQueueFactory.createSyncProducer(properties);
    }
}
