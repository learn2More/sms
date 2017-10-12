package com.ppdai.ac.sms.api.provider.techown;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.ppdai.ac.sms.api.provider.techown.domain.biz.TechownProviderBiz;
import com.ppdai.ac.sms.provider.core.request.SmsRequest;
import com.ppdai.ac.sms.provider.core.response.SmsResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

/**
 * author cash
 * create 2017-07-12-15:20
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationBoot.class)
public class TechownProviderTest {

    private static final Logger logger= LoggerFactory.getLogger(TechownProviderTest.class);

    @Autowired
    private TechownProviderBiz techownProviderBiz;


    @Test
    public void send() throws InterruptedException {
        for(int i=0;i<2;i++){
            SmsRequest request=new SmsRequest();
            Map<String, String> map=Maps.newHashMap();
            //map.put("name","020413");
            //map.put("password","27AUY1ZA");
            map.put("name","020399");
            map.put("password","UW28ZAXA");
            map.put("mobile","15121002698");
            map.put("content","这是一条测试短信,返回Jsonxxx");
            map.put("recordId","4e693c95-be1c-4427-a363-3d94801a26c6");
            request.setParams(map);

            SmsResponse smsResponse=techownProviderBiz.send(request);
            System.out.println("----------xxxxxxxxxxxxxxxx"+JSONObject.toJSON(smsResponse));

            Thread.sleep(100);
        }

    }

}
