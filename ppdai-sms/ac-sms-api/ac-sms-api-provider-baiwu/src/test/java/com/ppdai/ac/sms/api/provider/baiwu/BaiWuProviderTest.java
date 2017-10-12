package com.ppdai.ac.sms.api.provider.baiwu;

import com.alibaba.fastjson.JSONObject;
import com.ppdai.ac.sms.api.provider.baiwu.domain.biz.BWProviderBiz;
import com.ppdai.ac.sms.api.provider.baiwu.domain.model.JsonDeliverReturnCode;
import com.ppdai.ac.sms.api.provider.baiwu.domain.model.JsonReportReturnCode;
import com.ppdai.ac.sms.api.provider.baiwu.protocol.BaiwuService;
import com.ppdai.ac.sms.api.provider.baiwu.protocol.response.*;
import com.ppdai.ac.sms.provider.core.dao.domain.SmsMessageMoRecordBiz;
import com.ppdai.ac.sms.provider.core.dao.domain.SmsMessageReportBiz;
import com.ppdai.ac.sms.provider.core.enums.ReportStatus;
import com.ppdai.ac.sms.provider.core.model.bo.BizResult;
import com.ppdai.ac.sms.provider.core.response.SmsResponse;
import com.ppdai.ac.sms.provider.core.utils.MD5Util;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * author cash
 * create 2017-04-26-20:11
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationBoot.class)
public class BaiWuProviderTest {

    private static final Logger logger= LoggerFactory.getLogger(BaiWuProviderTest.class);

    @Autowired
    BWProviderBiz bwProviderBiz;

    @Autowired
    BaiwuService baiwuService;

    @Autowired
    SmsMessageMoRecordBiz smsMessageMoRecordBiz;

    @Autowired
    SmsMessageReportBiz smsMessageReportBiz;

    @Test
    @Rollback
    public void send(){
        for(int i=0;i<10;i++){
            String content="dear cash,This is for 你";
           /* try {
                //content= URLEncoder.encode(content, "GBK");
                content= URLEncoder.encode("dear cash,这是一条测试短信", "GBK");
                System.out.println("-----------"+content);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }*/

            String msgId= "bw-"+UUID.randomUUID();

            //SendRequest sendRequest=new SendRequest("wj2536","6588wh","1069011612536",mobiles,content,msgId,"");
            //SendMsgRequest sendRequest=new SendMsgRequest("pp6588","mm2289","1069003291618",mobiles,content,msgId,"");
            MultiValueMap<String, String> sendRequest = new LinkedMultiValueMap<>();
            sendRequest.add("corp_id", "pp6588");
            sendRequest.add("corp_pwd", "mm2289");
            sendRequest.add("corp_service", "1069003291618");
            sendRequest.add("mobile", "15121002698");
            sendRequest.add("msg_content", content);
            sendRequest.add("corp_msg_id", msgId);
            sendRequest.add("ext", "xxx");
            String  result=baiwuService.send(sendRequest);
            System.out.println("------"+result);
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    @Test
    public void send2(){
       /* String mobiles="15121002698";
        String content="dear cash,This is for 你";

        String msgId= "bw-"+UUID.randomUUID();

        SendMsgRequest sendRequest=new SendMsgRequest("pp8588","8513vf","106900329703",mobiles,content,msgId,"");
        String  result=bwProviderBiz.send(sendRequest);
        System.out.println("-------------"+result);*/
    }

    @Test
    public void getReport() {
        //ReportRequest reportRequest=new ReportRequest("wj2536","wj2536","6588wh");
        //ReportRequest reportRequest=new ReportRequest("pp6588","pp6588","mm2289");
        MultiValueMap<String, String> reportRequest = new LinkedMultiValueMap<>();

        String corp_id="pp6588";
        String user_id="pp6588";
        String corp_pwd="mm2289";

        reportRequest.add("corp_id", corp_id);
        reportRequest.add("user_id", user_id);
        reportRequest.add("corp_pwd", corp_pwd);

        //String tstamp=System.currentTimeMillis()+"";
        //签名  md5(账号+密码+时间戳)
        //String sign= MD5Util.MD5(corp_id+corp_pwd+tstamp);

        //Reports reports=baiwuService.getReports(tstamp,sign,reportRequest);
        Reports reports=baiwuService.getReports(reportRequest);
        if(null!=reports){
            if(StringUtils.isNotEmpty(reports.getCode())){
                if("0".equals(reports.getCode())){
                    logger.info("拉取百悟回执为空");
                }else{
                    logger.info("拉取百悟回执错误");
                }
            }
            if(CollectionUtils.isNotEmpty(reports.getReports())){
                for(ReportDetail reportDetail:reports.getReports()){
                    String recordId=reportDetail.getMsg_id();
                    String messageId=reportDetail.getMsg_id();
                    String providerId="10";
                    String recipient=reportDetail.getMobile();
                    String reportResult=reportDetail.getFail_desc();
                    int reportStatus= ReportStatus.SUCCESS_REPORT.getCode();

                    if(StringUtils.isNotEmpty(reportDetail.getErr())){
                        if(StringUtils.isNotEmpty(reportDetail.getFail_desc())){
                            reportStatus=ReportStatus.ERROR_REPORT.getCode();
                        }
                    }
                    //完整报告
                    String providerMessage=reportDetail.toString();
                    String  time=reportDetail.getReport_time();// 2010-07-02 00:00:00
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date d =new java.util.Date();
                    try {
                        d = format.parse(time);
                    } catch (Exception e) {
                        logger.error("百悟拉取上行,日期转换异常:"+ e.getMessage());
                    }
                    Timestamp reportTime = new Timestamp(d.getTime());

                    Timestamp sendTime=new Timestamp((new Date()).getTime());

                    BizResult bizResult=smsMessageReportBiz.saveMessageReport(recordId,messageId,providerId,"",recipient,reportResult,reportStatus,reportTime,sendTime,providerMessage);
                    if(0!=bizResult.getResultCode()){
                        logger.error("百悟保存回执报告异常："+bizResult.getResultMessage());
                    }
                }
            }

        }

    }

    @Test
    public void getDeliver() {
        //DeliverRequest deliverRequest=new DeliverRequest("pp6588","pp6588","mm2289");
        MultiValueMap<String, String> deliverRequest = new LinkedMultiValueMap<>();

        String corp_id="pp6588";
        String user_id="pp6588";
        String corp_pwd="mm2289";

        deliverRequest.add("corp_id", corp_id);
        deliverRequest.add("user_id", user_id);
        deliverRequest.add("corp_pwd", corp_pwd);

        //String tstamp=System.currentTimeMillis()+"";
        //签名  md5(账号+密码+时间戳)
        //String sign= MD5Util.MD5(corp_id+corp_pwd+tstamp);


        //Delivers delivers=baiwuService.getDelivers(tstamp,sign,deliverRequest);
        Delivers delivers=baiwuService.getDelivers(deliverRequest);
        if(null!=delivers){
            if(StringUtils.isNotEmpty(delivers.getCode())){
                if("0".equals(delivers.getCode())){
                    logger.info("百悟拉取上行记录为空");
                }else{
                    logger.error("百悟拉取上行记录异常");
                }
            }
            if(!CollectionUtils.isEmpty(delivers.getDelivers())){
                for(DeliverDetail deliverDetail:delivers.getDelivers()){
                    String channelNo="";
                    String sender=deliverDetail.getMobile();
                    String content=deliverDetail.getContent();
                    String extendNo=deliverDetail.getExt();
                    String time=deliverDetail.getTime();// 2010-07-02 00:00:00
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date d =new java.util.Date();
                    try {
                        d = format.parse(time);
                    } catch (Exception e) {
                        logger.error("百悟拉取上行,日期转换异常:"+ e.getMessage());
                    }
                    Timestamp timestamp = new Timestamp(d.getTime());

                    BizResult bizResult=smsMessageMoRecordBiz.saveMoRecord(channelNo,"",sender,content,extendNo,timestamp);
                    if(0!=bizResult.getResultCode()){
                        logger.error("百悟保存上行记录异常:"+ bizResult.getResultMessage());
                    }
                }
            }

        }
    }

    @Test
    public void getJsonReport() {
        //ReportRequest reportRequest=new ReportRequest("wj2536","wj2536","6588wh");
        //ReportRequest reportRequest=new ReportRequest("pp6588","pp6588","mm2289");
        MultiValueMap<String, String> reportRequest = new LinkedMultiValueMap<>();

        String corp_id="pp6588";
        String user_id="pp6588";
        String corp_pwd="mm2289";

        //reportRequest.add("corp_id", corp_id);
        reportRequest.add("user_id", user_id);
        //reportRequest.add("corp_pwd", corp_pwd);

        String tstamp=System.currentTimeMillis()+"";
        //签名  md5(账号+密码+时间戳)
        String sign= MD5Util.MD5(corp_id+corp_pwd+tstamp);

        reportRequest.add("timestamp",tstamp);
        reportRequest.add("sign",sign);

        JReports reports=baiwuService.getJsonReports(reportRequest);
        if(null!=reports){
            if(StringUtils.isNotEmpty(reports.getCode())){
                if(JsonReportReturnCode.NO_MESSAGE.getCode().equals(reports.getCode())){
                    logger.info("拉取百悟回执为空");
                }else if(!JsonReportReturnCode.SUCCESS.getCode().equals(reports.getCode())){
                    logger.info("拉取百悟回执错误: "+reports.getMsg());
                }
            }
            if(CollectionUtils.isNotEmpty(reports.getData())){
                for(JReportDetail reportDetail:reports.getData()){
                    String recordId=reportDetail.getMsg_id();
                    String messageId=reportDetail.getMsg_id();
                    String providerId="10";
                    String recipient=reportDetail.getMobile();
                    String reportResult=reportDetail.getFail_desc();
                    int reportStatus= ReportStatus.SUCCESS_REPORT.getCode();

                    if(StringUtils.isNotEmpty(reportDetail.getErr())){
                        if(StringUtils.isNotEmpty(reportDetail.getFail_desc())){
                            reportStatus=ReportStatus.ERROR_REPORT.getCode();
                        }
                    }
                    //完整报告
                    String providerMessage=reportDetail.toString();
                    String  time=reportDetail.getReport_time();// 2010-07-02 00:00:00
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date d =new java.util.Date();
                    try {
                        d = format.parse(time);
                    } catch (Exception e) {
                        logger.error("百悟拉取上行,日期转换异常:"+ e.getMessage());
                    }
                    Timestamp reportTime = new Timestamp(d.getTime());

                    Timestamp sendTime=new Timestamp((new Date()).getTime());

                    BizResult bizResult=smsMessageReportBiz.saveMessageReport(recordId,messageId,providerId,"",recipient,reportResult,reportStatus,reportTime,sendTime,providerMessage);
                    if(0!=bizResult.getResultCode()){
                        logger.error("百悟保存回执报告异常："+bizResult.getResultMessage());
                    }
                }
            }

        }

    }

    @Test
    public void getJsonDeliver() {
        //DeliverRequest deliverRequest=new DeliverRequest("pp6588","pp6588","mm2289");
        MultiValueMap<String, String> deliverRequest = new LinkedMultiValueMap<>();

        String corp_id = "pp6588";
        String user_id = "pp6588";
        String corp_pwd = "mm2289";

        //deliverRequest.add("corp_id", corp_id);
        deliverRequest.add("user_id", user_id);
        //deliverRequest.add("corp_pwd", corp_pwd);

        String tstamp = System.currentTimeMillis() + "";
        //签名  md5(账号+密码+时间戳)
        String sign = MD5Util.MD5(corp_id + corp_pwd + tstamp);

        deliverRequest.add("timestamp", tstamp);
        deliverRequest.add("sign", sign);


        JDelivers delivers = baiwuService.getJsonDelivers(deliverRequest);
        if (null != delivers) {
            if (StringUtils.isNotEmpty(delivers.getCode())) {
                if (JsonDeliverReturnCode.NO_MESSAGE.getCode().equals(delivers.getCode())) {
                    logger.info("百悟拉取上行记录为空");
                } else if (!JsonReportReturnCode.SUCCESS.getCode().equals(delivers.getCode())) {
                    {
                        logger.error("百悟拉取上行记录异常:" + delivers.getMsg());
                    }
                }
                if (!CollectionUtils.isEmpty(delivers.getData())) {
                    for (JDeliverDetail deliverDetail : delivers.getData()){
                        String channelNo = "";
                        String sender = deliverDetail.getMobile();
                        String content = deliverDetail.getContent();
                        String extendNo = deliverDetail.getExt();
                        String time = deliverDetail.getDeliver_time();// 2010-07-02 00:00:00
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        java.util.Date d = new java.util.Date();
                        try {
                            d = format.parse(time);
                        } catch (Exception e) {
                            logger.error("百悟拉取上行,日期转换异常:" + e.getMessage());
                        }
                        Timestamp timestamp = new Timestamp(d.getTime());

                        BizResult bizResult = smsMessageMoRecordBiz.saveMoRecord(channelNo,"", sender, content, extendNo, timestamp);
                        if (0 != bizResult.getResultCode()) {
                            logger.error("百悟保存上行记录异常:" + bizResult.getResultMessage());
                        }
                    }
                }

            }
        }
    }


    @Test
    public void saveMoRecord(){
        DeliverDetail deliverDetail=new DeliverDetail();
        deliverDetail.setMobile("15890098765");
        deliverDetail.setContent("bzjs");
        deliverDetail.setTime("2017-07-02 00:00:00");
        deliverDetail.setExt("42352342");
        String channelNo="";
        String sender=deliverDetail.getMobile();
        String content=deliverDetail.getContent();
        String extendNo=deliverDetail.getExt();
        String time=deliverDetail.getTime();// 2010-07-02 00:00:00
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date d =new java.util.Date();
        try {
            d = format.parse(time);
        } catch (Exception e) {
            logger.error("百悟拉取上行,日期转换异常:"+ e.getMessage());
        }
        Timestamp timestamp = new Timestamp(d.getTime());
        BizResult bizResult=smsMessageMoRecordBiz.saveMoRecord("百悟","",sender,content,extendNo,timestamp);
        if(0!=bizResult.getResultCode()){
            logger.error("百悟保存上行记录异常:"+ bizResult.getResultMessage());
        }
    }

    @Test
    public void saveMessageReport(){
        ReportDetail reportDetail=new ReportDetail();
        reportDetail.setMsg_id("24159b8f-3889-4404-a504-3542f20a4128");
        reportDetail.setMobile("15121209876");
        reportDetail.setErr("1");
        reportDetail.setFail_desc("holly limit xxxxxx");
        reportDetail.setReport_time("2017-07-02 00:00:00");

        String recordId=reportDetail.getMsg_id();
        String messageId=reportDetail.getMsg_id();
        String providerId="1";
        String recipient=reportDetail.getMobile();
        String reportResult=reportDetail.getFail_desc();
        int reportStatus=Integer.parseInt(reportDetail.getErr());
        String providerMessage=reportDetail.getFail_desc();
        String  time=reportDetail.getReport_time();// 2017-07-02 00:00:00
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d =new Date();
        try {
            d = format.parse(time);
        } catch (Exception e) {
            logger.error("百悟拉取上行,日期转换异常:"+ e.getMessage());
        }
        Timestamp reportTime = new Timestamp(d.getTime());

        Timestamp sendTime=new Timestamp((new Date()).getTime());

        BizResult bizResult=smsMessageReportBiz.saveMessageReport(recordId,messageId,providerId,"",recipient,reportResult,reportStatus,reportTime,sendTime,providerMessage);
        if(0!=bizResult.getResultCode()){
            logger.error("百悟保存回执报告异常："+bizResult.getResultMessage());
        }
    }

    @Test
    public void getDelivers(){
        SmsResponse smsResponse= bwProviderBiz.getDelivers("1");
        System.out.println(JSONObject.toJSON(smsResponse));
    }
}
