package com.ppdai.ac.sms.consumer.core.configuration;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import org.springframework.stereotype.Component;

/**
 * Created by kiekiyang on 2017/5/5.
 */
@Component
public class OperatorProperties {
    @ApolloConfig
    private transient Config apolloConfig;

    private String chinaMobile;
    private String chinaUnicom;
    private String chinaTelecom;

    public String[] getChinaMobile() {
        return apolloConfig.getArrayProperty("message.phone.operator.chinamobile", ",", new String[]{});
    }

    public String[] getChinaUnicom() {
        return apolloConfig.getArrayProperty("message.phone.operator.chinaunicom", ",", new String[]{});
    }

    public String[] getChinaTelecom() {

        return apolloConfig.getArrayProperty("message.phone.operator.chinatelecom", ",", new String[]{});
    }
}
