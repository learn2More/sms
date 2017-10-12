package com.ppdai.ac.sms.api.provider.ccp;

import com.alibaba.fastjson.JSONObject;
import com.ppdai.ac.sms.api.provider.ccp.domain.biz.CcpProviderBiz;
import com.ppdai.ac.sms.api.provider.ccp.protocal.CcpService;
import com.ppdai.ac.sms.api.provider.ccp.protocal.request.CallNotifyRequest;
import com.ppdai.ac.sms.api.provider.ccp.protocal.request.CallVerifyCodeRequest;
import com.ppdai.ac.sms.api.provider.ccp.protocal.response.CallNotifyResponse;
import com.ppdai.ac.sms.api.provider.ccp.protocal.response.CallVerifyCodeResponse;
import com.ppdai.ac.sms.api.provider.ccp.request.CcpCallVerifyCodeRequest;
import com.ppdai.ac.sms.common.redis.DistributedLock;
import com.ppdai.ac.sms.provider.core.dao.domain.SmsMessageRecordBiz;
import com.ppdai.ac.sms.provider.core.model.entity.SMSMessageRecordDTO;
import com.ppdai.ac.sms.provider.core.protocol.MessageService;
import com.ppdai.ac.sms.provider.core.response.CallResponse;
import com.ppdai.ac.sms.provider.core.utils.MD5Util;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * author cash
 * create 2017-05-15-18:57
 **/

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationBoot.class)
public class CcpProviderTest {

    private static final SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");

    @Autowired
    CcpService ccpService;

    @Autowired
    CcpProviderBiz ccpProviderBiz;

    @Autowired
    SmsMessageRecordBiz smsMessageRecordBiz;

    @Autowired
    MessageService messageService;

    @Autowired
    DistributedLock distributedLock;


    @Test
    public void callVerifyCode(){
        //
        //8a216da85c62c9ad015c8c6a5c24110c
        CallVerifyCodeRequest callMsgRequest=new CallVerifyCodeRequest("8a216da85c62c9ad015c8c6a5c24110c","15121002698","2","http://172.17.4.62:8093/ccpProviderService/receiveReport","01052821234","","456678","");

        String softversion="2013-12-26";
        String accountSid="8a48b55148fe48600149185a0f06112d";
        String mainToken="22ea11d71134401b8c54c6510886b888";
        String dateStr=sdf.format(new Date());

        String sigParameter= MD5Util.MD5(accountSid+mainToken+dateStr);
        System.out.println("sigParameter: "+sigParameter);
        String authorization=Base64.encodeBase64String((accountSid+":"+dateStr).getBytes());
        System.out.println("authorization: "+authorization);
        CallVerifyCodeResponse callMsgResponse=ccpService.callVerifyCode(softversion,accountSid,sigParameter,authorization,callMsgRequest);
        System.out.println("---------------"+JSONObject.toJSON(callMsgResponse));

    }

    @Test
    public void callNotify(){
        //
        //8a216da85c62c9ad015c8c6a5c24110c
        CallNotifyRequest callMsgRequest=new CallNotifyRequest("8a216da85c62c9ad015c8c6ae972110d","15121002698","2","http://172.17.4.62:8093/ccpProviderService/receiveReport","01052821234","3.wav","","");

        String softversion="2013-12-26";
        String accountSid="8a48b55148fe48600149185a0f06112d";
        String mainToken="22ea11d71134401b8c54c6510886b888";
        String dateStr=sdf.format(new Date());

        String sigParameter= MD5Util.MD5(accountSid+mainToken+dateStr);
        System.out.println("sigParameter: "+sigParameter);
        String authorization=Base64.encodeBase64String((accountSid+":"+dateStr).getBytes());
        System.out.println("authorization: "+authorization);
        CallNotifyResponse callMsgResponse=ccpService.callNotify(softversion,accountSid,sigParameter,authorization,callMsgRequest);
        System.out.println("---------------"+JSONObject.toJSON(callMsgResponse));

    }

    @Test
    public void bizCall(){

        CcpCallVerifyCodeRequest ccpCallRequest=new CcpCallVerifyCodeRequest();
        ccpCallRequest.setRecordId("f72ff5f1-13c1-4eae-829d-2514c3c663c4");
        ccpCallRequest.setAppId("aaf98f89493ff1d301494f9f97ec08d1");
        ccpCallRequest.setTo("15121002698");
        ccpCallRequest.setPlayTimes("2");
        ccpCallRequest.setRespUrl("");
        ccpCallRequest.setDisplayNum("");
        ccpCallRequest.setVerifyCode("123456");


        CallResponse callResponse=ccpProviderBiz.callVerifyCode(ccpCallRequest);

        System.out.println("------------"+JSONObject.toJSON(callResponse));


    }


    @Test
    public void saveReport(){
        CallResponse callResponse =ccpProviderBiz.getReport("10");
        System.out.println(JSONObject.toJSON(callResponse));
    }

    @Test
    public void getTheStatus(){
        String providerId="1";
        LocalDateTime endTime=LocalDateTime.now();
        //前3天
        LocalDateTime startTime=endTime.minusDays(3);
        List<SMSMessageRecordDTO> list=smsMessageRecordBiz.getNeedToSolvedRecord(providerId,startTime,endTime);
        System.out.println("999999999999"+JSONObject.toJSONString(list));
    }

    @Test
    public void DistributedLockTest(){
        for (int i = 0; i < 10; i++) {
            boolean b=distributedLock.tryLock("SMS:XXXXXXXXXXXX",100L);
            System.out.println(" ------------------ "+b);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
