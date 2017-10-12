package com.ppdai.ac.sms.api.provider.sendcloud;

import com.alibaba.fastjson.JSONObject;
import com.ppdai.ac.sms.api.provider.sendcloud.protocol.SendCloudService;
import com.ppdai.ac.sms.api.provider.sendcloud.protocol.response.ReportResponseData;
import com.ppdai.ac.sms.api.provider.sendcloud.protocol.response.SendResponseData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * author cash
 * create 2017-04-26-20:11
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationBoot.class)
public class SendCloudProviderTest {
    private static final Logger logger = LoggerFactory.getLogger(SendCloudProviderTest.class);

    @Autowired
    SendCloudService  sendCloudService;

    @Test
    public void send2() {
        MultiValueMap<String, String> sendRequest = new LinkedMultiValueMap<>();
        sendRequest.add("apiUser", "postmaster@account.mail3.ppdai.com");
        sendRequest.add("apiKey", "IrSzSW8VRKQO3IWj");
        sendRequest.add("from", "account@mail3.ppdai.com");
        sendRequest.add("fromname", "拍拍贷账户");
        sendRequest.add("to", "1052647407@qq.com");
        sendRequest.add("subject", "test");
        sendRequest.add("html", "test1111");
        sendRequest.add("respEmailId", "true");
        sendRequest.add("useNotification", "true");
        try {
            SendResponseData responseData= sendCloudService.send(sendRequest);
            System.out.println("-----------------"+JSONObject.toJSONString(responseData));
        }catch (Exception ex){
            logger.error("send",ex);
        }
    }


    @Test
    public void getReport2() {
        MultiValueMap<String, String> sendRequest = new LinkedMultiValueMap<>();
        sendRequest.add("apiUser", "postmaster@account.mail3.ppdai.com");
        sendRequest.add("apiKey", "IrSzSW8VRKQO3IWj11");
        sendRequest.add("days", "2");
        try {
            ReportResponseData responseData= sendCloudService.getReports(sendRequest);
            System.out.println("-----------------"+JSONObject.toJSONString(responseData));
        }catch (Exception ex){
            logger.error("getReport",ex);
        }

    }

}
