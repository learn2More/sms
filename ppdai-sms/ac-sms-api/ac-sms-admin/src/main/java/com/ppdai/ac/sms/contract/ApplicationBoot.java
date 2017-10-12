package com.ppdai.ac.sms.contract;

/**
 * Created by kiekiyang on 2017/4/12.
 */

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//import ppdai.servlet.CatServletFilter;

//开启spring对Aspectj的支持
@EnableAspectJAutoProxy
@SpringBootApplication
@EnableSwagger2
@EnableDiscoveryClient
@EnableFeignClients
@EnableApolloConfig
@ComponentScan(basePackages = {"com.ppdai.ac.sms.contract", "com.ppdai.ac.sms.common.redis",
        "com.ppdai.ac.sms.common.service,com.ppdai.framework.microservice.*"})
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
