package com.ppdai.ac.sms.api.gateway;

import com.alibaba.fastjson.JSONObject;
import com.ppdai.ac.sms.api.gateway.dao.domain.MessageBiz;
import com.ppdai.ac.sms.api.gateway.model.bo.BizResult;
import com.ppdai.ac.sms.api.gateway.enums.MessageType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;

/**
 * Created by kiekiyang on 2017/4/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SMSMessageTest {

    @Autowired
    MessageBiz messageBiz;

    @Test
    public void sendTest() {
        for (int i = 0; i < 10; i++) {
            BizResult result = messageBiz.send(MessageType.MESSAGE,1, "13916818820", "1111", "bus_yewu1", "tpl_xxx_test", null);
            System.out.print(result.getResultMessage());
        }
    }

    @Test
    public void getMessageById(){
        String messageId="b1befc67-e957-4929-b875-94987a0b007e";
        LocalDateTime endTime=LocalDateTime.now();
        //前3天
        LocalDateTime startTime=endTime.minusDays(3);
        BizResult bizResult=messageBiz.queryMessageById(messageId,startTime,endTime);
        System.out.println(JSONObject.toJSON(bizResult));
    }
}
