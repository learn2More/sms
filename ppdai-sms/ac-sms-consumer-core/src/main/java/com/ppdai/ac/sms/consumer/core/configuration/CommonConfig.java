package com.ppdai.ac.sms.consumer.core.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by kiekiyang on 2017/5/6.
 */
@Configuration
public class CommonConfig {

    @Bean
    public NetWorkProperties netWorkProperties() {
        return new NetWorkProperties();
    }

    @Bean
    public OperatorProperties operatorProperties() {
        return new OperatorProperties();
    }

    @Bean
    public RetryProperties retryProperties() {
        return new RetryProperties();
    }
}
