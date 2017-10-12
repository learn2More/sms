package com.ppdai.ac.sms.api.provider.ccp;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by cash on 2017/5/15.
 */
@SpringBootApplication
@EnableScheduling
@EnableFeignClients(basePackages = {"com.ppdai.ac.sms.api.provider.ccp","com.ppdai.ac.sms.provider.core"})
@EnableDiscoveryClient
@EnableSwagger2
@EnableApolloConfig
@ComponentScan(basePackages = {"com.ppdai.ac.sms.api.provider.ccp", "com.ppdai.ac.sms.provider.core",
        "com.ppdai.ac.sms.common.redis",
        "ppdai.httptracing.okhttp.*",
        "com.ppdai.framework.microservice.*"})
public class ApplicationBoot extends SpringBootServletInitializer implements CommandLineRunner {

    public void run(String... arg0) throws Exception {
        if (arg0.length > 0 && arg0[0].equals("exitcode")) {
            throw new ExitException();
        }
    }

    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return super.configure(builder);
    }


    public static void main(String[] args) throws Exception {
        new SpringApplicationBuilder(ApplicationBoot.class).web(true).run(args);
    }

    class ExitException extends RuntimeException implements ExitCodeGenerator {
        private static final long serialVersionUID = 1L;

        @Override
        public int getExitCode() {
            return 10;
        }

    }

}