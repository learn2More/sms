package com.ppdai.ac.sms.api.message.consumer.nomal.configuration;

import com.ppdai.ac.sms.api.message.consumer.nomal.scheduler.MessageSubscriber;
import com.ppdai.messagequeue.MessageQueueFactory;
import com.ppdai.messagequeue.consumer.defaultConsumer.Consumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * Created by kiekiyang on 2017/5/4.
 */
@Configuration
@Component
public class MessageQueueConfig {
    @Autowired
    MessageQueueProperties messageQueueProperties;


    @Bean
    public MessageSubscriber messageSubscriber() {
        return new MessageSubscriber();
    }

    @Bean
    public MessageQueueProperties messageQueueProperties() {
        return new MessageQueueProperties();
    }


    @Bean
    public Consumer consumer(){
        Properties properties = new Properties();
        properties.put("group_name", messageQueueProperties.getGroupName());
        properties.put("max_delay_time", "2000");
        properties.put("handle_error_alarm_emails", messageQueueProperties.getAlarmEmail());//必须参数
        properties.put("retry_size","10");//重试策略,重试次数 间隔1min

        Consumer consumer = MessageQueueFactory.createConsumer(properties);
        consumer.setSubscriber(messageSubscriber());
        consumer.start();
        return consumer;
    }
}
