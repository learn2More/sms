package com.ppdai.ac.sms.api.provider.sendcloud.configuration;

import feign.Feign;
import okhttp3.ConnectionPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.netflix.feign.FeignAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * Feign默认使用HttpClient,替换成OkHttp
 * author cash
 * create 2017-05-03-10:25
 **/
@Configuration
@ConditionalOnClass(Feign.class)
@AutoConfigureBefore(FeignAutoConfiguration.class)
public class FeignOkHttpConfig {

    @Autowired
    SendCloudProviderProperties sendCloudProviderProperties;

    @Bean
    public SendCloudProviderProperties sendCloudProviderProperties() {
        return new SendCloudProviderProperties();
    }

    @Bean
    public okhttp3.OkHttpClient okHttpClient(){
        return new okhttp3.OkHttpClient.Builder()
                .readTimeout(sendCloudProviderProperties.getReadTimeout(), TimeUnit.SECONDS)
                .connectTimeout(sendCloudProviderProperties.getConnectTimeout(), TimeUnit.SECONDS)
                .writeTimeout(sendCloudProviderProperties.getWriteTimeout(), TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool())
                //.addInterceptor(new SendCloudOkHttpInterceptor())
                .build();
    }
}
