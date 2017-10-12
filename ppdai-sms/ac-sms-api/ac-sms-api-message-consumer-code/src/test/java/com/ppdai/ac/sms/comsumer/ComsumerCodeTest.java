package com.ppdai.ac.sms.comsumer;

import com.alibaba.fastjson.JSONObject;
import com.ppdai.ac.sms.api.message.consumer.code.ApplicationBoot;
import com.ppdai.ac.sms.consumer.core.dao.mapper.smsbase.ProviderConfigMapper;
import com.ppdai.ac.sms.consumer.core.model.entity.ProviderConfigDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * author cash
 * create 2017-06-26-13:53
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationBoot.class)
public class ComsumerCodeTest {
    private static final Logger logger= LoggerFactory.getLogger(ComsumerCodeTest.class);

    @Autowired
    ProviderConfigMapper providerConfigMapper;


    @Test
    public void testGetProviderByProviderId(){
        int providerId=4;
        List<ProviderConfigDTO> providerConfigDTO=providerConfigMapper.findProviderConfigByProviderId(providerId);
        if(null!=providerConfigDTO){
            for(ProviderConfigDTO dto:providerConfigDTO){
                System.out.println(JSONObject.toJSON(dto));
            }
        }

    }
}
