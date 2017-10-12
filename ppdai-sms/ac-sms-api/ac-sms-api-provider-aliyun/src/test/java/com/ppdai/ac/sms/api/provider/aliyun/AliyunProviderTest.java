package com.ppdai.ac.sms.api.provider.aliyun;

import com.ppdai.ac.sms.api.provider.aliyun.domain.biz.AliyunProviderBiz;
import com.ppdai.ac.sms.api.provider.aliyun.request.SendMsgRequest;
import com.ppdai.ac.sms.api.provider.aliyun.utils.AliyunUtil;
import com.ppdai.ac.sms.provider.core.dao.domain.SmsMessageMoRecordBiz;
import com.ppdai.ac.sms.provider.core.dao.domain.SmsMessageRecordBiz;
import com.ppdai.ac.sms.provider.core.dao.domain.SmsMessageReportBiz;
import com.ppdai.ac.sms.provider.core.model.bo.BizResult;
import com.ppdai.ac.sms.provider.core.model.entity.SMSMessageRecordDTO;
import com.ppdai.ac.sms.provider.core.response.SmsResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author cash
 * create 2017-04-26-20:11
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationBoot.class)
public class AliyunProviderTest {

    private static final  Logger logger= LoggerFactory.getLogger(AliyunProviderTest.class);

    @Autowired
    AliyunProviderBiz aliyunProviderBiz;

    @Autowired
    SmsMessageMoRecordBiz smsMessageMoRecordBiz;

    @Autowired
    SmsMessageReportBiz smsMessageReportBiz;

    @Autowired
    SmsMessageRecordBiz smsMessageRecordBiz;

    @Test
    public void test(){
        SendMsgRequest sendRequest=new SendMsgRequest();
        sendRequest.setSignName("骑兵服务");
        sendRequest.setTemplateCode("SMS_46165066");
        List<String> mobiles=new ArrayList<>();
        //mobiles.add("15121002698");
        mobiles.add("15721480029");
        Map<String,String> params=new HashMap<>();
        params.put("msg","sendRequest.34752348!   -TD");
        sendRequest.setMobiles(mobiles);
        sendRequest.setParamMap(params);
        sendRequest.setRecordId("15");
        sendRequest.setAccessSecret("mm72AzeQgfeUkTDFZNGzMtCCcdUN5A");
        sendRequest.setAccessKey("LTAIUstKVYl9F8bt");
        sendRequest.setEndpoint("http://1976481783183439.mns.cn-shanghai.aliyuncs.com");
        sendRequest.setTopicName("topic-sms");
        SmsResponse sendResponse=aliyunProviderBiz.send(sendRequest);
        System.out.println("code="+sendResponse.getResultCode());
        System.out.println("msgId="+sendResponse.getResultObject());

    }

    @Test
    public void saveMoRecord(){
        Map<String,String> map= AliyunUtil.parseMotMsg("sender=42354fa&content=刚才没开xx回信&receive_time=20170331132401&extend_code=111&ver=1.0&event=ReplyMessage");

        String channelNo="";
        String sender=map.get("sender");
        String content=map.get("content");
        String extendNo=map.get("extendNo");

        String  time=map.get("receiveTime");
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        java.util.Date d =new java.util.Date();
        try {
            d = format.parse(time);
        } catch (Exception e) {
            logger.error("阿里拉取上行,日期转换异常:"+ e.getMessage());
        }
        Timestamp timestamp = new Timestamp(d.getTime());

        BizResult bizResult=smsMessageMoRecordBiz.saveMoRecord(channelNo,null,sender,content,extendNo,timestamp);
        if(0!=bizResult.getResultCode()){
            logger.error("阿里云保存上行记录异常:"+ bizResult.getResultMessage());
        }
    }


    @Test
    public void saveMessageReport(){
        //Map<String,String> map= AliyunUtil.parseReportMsg("messageID=23B500FASF59CA1B-1-15B5567FC70-200000009&receiver=12345678901&state=1&biz_id=103245234562^1324561234567&template_code=SMS_12346789&sms_count=1&receive_time=2017-03-31 11:28:19&ver=1.0&event=SendSuccessfully");
        Map<String,String> map= AliyunUtil.parseReportMsg("messageID=23B522F13F59CA1B-1-15B887C54B3-20000000A&receiver=11234567890&state=2&err_code=Invrmed.&event=SendFailed");

        String recordId="312412341";
        String messageId=map.get("messageId");
        String providerId="10";
        String recipient=map.get("phone");
        String reportResult=map.get("errorCode");
        int reportStatus=0;
        String providerMessage=map.get("errorCode");
        String  time=map.get("receiveTime");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date d =new java.util.Date();
        try {
            d = format.parse(time);
        } catch (Exception e) {
            logger.error("阿里拉取回执,日期转换异常:"+ e.getMessage());
        }
        //Date date = new Date(d.getTime());

        Timestamp reportTime = new Timestamp(d.getTime());
        Timestamp sendTime = new Timestamp(d.getTime());

        BizResult bizResult=smsMessageReportBiz.saveMessageReport(recordId,messageId,providerId,null,recipient,reportResult,reportStatus,reportTime,sendTime,providerMessage);
        if(0!=bizResult.getResultCode()){
            logger.error("阿里保存回执报告异常："+bizResult.getResultMessage());
        }
    }

    @Test
    public void getRecordByRecordExt(){
        String recordExt="C286161CE7133671-1-15BFB2ACC34-30003DC9B";
        SMSMessageRecordDTO smsMessageRecordDTO=smsMessageRecordBiz.getRecordByRecordExt(recordExt,null,null);
        if(null!=smsMessageRecordDTO){
            System.out.println(smsMessageRecordDTO.getRecordId()+"------"+smsMessageRecordDTO.getMessageId());
        }

    }
}
