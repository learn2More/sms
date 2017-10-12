package com.ppdai.ac.sms.api.provider.xuanwu;

import com.alibaba.fastjson.JSONObject;
import com.esms.MOMsg;
import com.esms.PostMsg;
import com.esms.common.entity.Account;
import com.esms.common.entity.MTReport;
import com.ppdai.ac.sms.api.provider.xuanwu.domain.biz.XWProviderBiz;
import com.ppdai.ac.sms.api.provider.xuanwu.domain.model.XWReportReturnCode;
import com.ppdai.ac.sms.api.provider.xuanwu.request.SendMsg;
import com.ppdai.ac.sms.provider.core.dao.domain.SmsMessageBiz;
import com.ppdai.ac.sms.provider.core.dao.domain.SmsMessageMoRecordBiz;
import com.ppdai.ac.sms.provider.core.dao.domain.SmsMessageRecordBiz;
import com.ppdai.ac.sms.provider.core.dao.domain.SmsMessageReportBiz;
import com.ppdai.ac.sms.provider.core.enums.InvokeResult;
import com.ppdai.ac.sms.provider.core.enums.ReportStatus;
import com.ppdai.ac.sms.provider.core.model.bo.BizResult;
import com.ppdai.ac.sms.provider.core.model.entity.SMSMessageDTO;
import com.ppdai.ac.sms.provider.core.model.entity.SMSMessageRecordDTO;
import com.ppdai.ac.sms.provider.core.protocol.MessageService;
import com.ppdai.ac.sms.provider.core.protocol.request.ChangeMessageStatusRequest;
import com.ppdai.ac.sms.provider.core.protocol.request.MessageQueryRequest;
import com.ppdai.ac.sms.provider.core.protocol.request.MessageStatus;
import com.ppdai.ac.sms.provider.core.protocol.response.ChangeMessageStatueResponse;
import com.ppdai.ac.sms.provider.core.protocol.response.MessageQueryResponse;
import com.ppdai.ac.sms.provider.core.response.SmsResponse;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * author cash
 * create 2017-04-26-20:11
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationBoot.class)
public class XuanuProviderTest {

    private final static Logger logger = LoggerFactory.getLogger(XuanuProviderTest.class);


    @Autowired
    XWProviderBiz xwProviderBiz;

    @Autowired
    SmsMessageReportBiz smsMessageReportBiz;

    @Autowired
    SmsMessageMoRecordBiz smsMessageMoRecordBiz;


    @Autowired
    SmsMessageBiz smsMessageBiz;

    @Autowired
    SmsMessageRecordBiz smsMessageRecordBiz;

    @Autowired
    MessageService messageService;


  /*  @Value("${provider.xuanwu.name}")
    private String username;

    @Value("${provider.xuanwu.password}")
    private String password;*/

    @Test
    public void send(){
        SendMsg sendMsg=new SendMsg();
        List<String> mobiles=new ArrayList<>();
        mobiles.add("15121002698");
        sendMsg.setMobiles(mobiles);
        sendMsg.setContent("cash,.support.DefaultListableB出,请!");
        /*sendMsg.setName("shppdcs01@shppdcs2");
        sendMsg.setPassword("02my3wB7");*/
        sendMsg.setName("bjplscs@bjplscs");
        sendMsg.setPassword("04ycZxrD");
        sendMsg.setMessageId(System.currentTimeMillis()+"");
        sendMsg.setExtendNo("19919");
        SmsResponse sendResponse=xwProviderBiz.send(sendMsg);

        System.out.println("-------------"+sendResponse.getResultCode()+"--------"+sendResponse.getResultMessage()+"------"+sendResponse.getResultObject());
    }

    @Test
    public void getReport(){
        String lusername="shppdcs01@shppdcs2";
        String lpassword="02my3wB7";
        String lcmHost="211.147.239.62";
        int lcmPort=9080;
        String lwsHost="211.147.239.62";
        int lwsport=9070;
        Account ac = new Account(lusername, lpassword);
        PostMsg pm = new PostMsg();

        pm.getCmHost().setHost(lcmHost,lcmPort);//设置网关的IP和port，用于发送信息
        pm.getWsHost().setHost(lwsHost, lwsport);//设置网关的 IP和port，用于获取账号信息、上行、状态报告等等

        MTReport[] mtReports;
        try {
            mtReports = pm.getReports(ac, 10);
            if(mtReports != null && mtReports.length>0){
                logger.info("------------玄武拉取回执,完整返回----------------------:"+ JSONObject.toJSONString(mtReports));
                for(MTReport mtReport:mtReports){
                    String recordId=mtReport.getCustomMsgID();
                    //String messageId=mtReport.getMsgID();
                    String messageId="";
                    String recipient=mtReport.getPhone();
                    String reportResult=mtReport.getOriginResult();

                    int reportStatus= ReportStatus.SUCCESS_REPORT.getCode();
                    int code=mtReport.getState();
                    if(XWReportReturnCode.DELIVERED.getCode()!=code){
                        reportStatus=ReportStatus.ERROR_REPORT.getCode();
                    }

                    //完整报告
                    String providerMessage=mtReport.toString();
                    long time=mtReport.getDoneTime();
                    Timestamp reportTime=new Timestamp(time);

                    BizResult bizResult=smsMessageReportBiz.saveMessageReport(recordId,messageId,"2","",recipient,reportResult,reportStatus,reportTime,null,providerMessage);
                    if(InvokeResult.SUCCESS.getCode()!=bizResult.getResultCode()){
                        logger.error("玄武 recordId: "+recordId+" 保存回执报告异常:"+ bizResult.getResultMessage());
                    }

                }
            }
        } catch (Exception e) {
            logger.error("玄武拉取回执异常",e);
        }
    }

    @Test
    public void getDeliver(){

        String lusername="shppdcs01@shppdcs2";
        String lpassword="02my3wB7";
        String lcmHost="211.147.239.62";
        int lcmPort=9080;
        String lwsHost="211.147.239.62";
        int lwsport=9070;
        Account ac = new Account(lusername, lpassword);
        PostMsg pm = new PostMsg();

        pm.getCmHost().setHost(lcmHost,lcmPort);//设置网关的IP和port，用于发送信息
        pm.getWsHost().setHost(lwsHost, lwsport);//设置网关的 IP和port，用于获取账号信息、上行、状态报告等等

        MOMsg[] mos ;
        try {
            mos = pm.getMOMsgs(ac, 10);
            if(mos != null && mos.length>0){
                logger.info("------------玄武拉取上行,完整返回----------------------:"+ JSONObject.toJSONString(mos));
                for(MOMsg mo : mos){
                    String channelNo="";
                    String sender=mo.getPhone();
                    String content=mo.getContent();
                    String extendNo="";
                    //端口号 (通道号+扩展码),设置5位扩展码,反向截取5位
                    String sepcNumber=mo.getSepcNumber();
                    if(StringUtils.isNotEmpty(sepcNumber)&&sepcNumber.length()>5){
                        extendNo=sepcNumber.substring(sepcNumber.length()-5);
                    }
                    long time=mo.getRecevieTime();
                    Timestamp timestamp=new Timestamp(time);
                    BizResult bizResult=smsMessageMoRecordBiz.saveMoRecord(channelNo,"",sender,content,extendNo,timestamp);
                    if(InvokeResult.SUCCESS.getCode()!=bizResult.getResultCode()){
                        logger.error("玄武 extendNo: "+extendNo+" mobile: "+sender+" 保存上行记录异常:"+ bizResult.getResultMessage());
                    }
                }
            }else{
                logger.info("玄武providerId: "+2+" 拉取上行,无数据");
            }
        } catch (Exception e) {
            logger.error("玄武拉取上行异常",e);
        }
    }


    @Test
    public void saveMoRecord(){
        String channelNo="";
        String sender="15121000000";
        String content="n";
        String extendNo="";
        String sepcNumber="823499023403490901";
        if(StringUtils.isNotEmpty(sepcNumber)&&sepcNumber.length()>5){
            extendNo=sepcNumber.substring(sepcNumber.length()-5);
        }
        long time= System.currentTimeMillis();
        Timestamp timestamp=new Timestamp(time);
        BizResult bizResult=smsMessageMoRecordBiz.saveMoRecord(channelNo,"",sender,content,extendNo,timestamp);
        System.out.println(bizResult.getResultObject());
    }

    @Test
    public void saveMessageReport(){
        String recordId= "13402f4f-0d8c-41a1-b1d7-694f63e2c282";
        String messageId=UUID.randomUUID()+"";
        String providerId="10";
        String recipient="15100000000";
        String reportResult="成功 limit,很好!!!!";
        int reportStatus=0;
        String providerMessage="";
        long time= System.currentTimeMillis();
        Timestamp reportTime=new Timestamp(time);

        BizResult bizResult=smsMessageReportBiz.saveMessageReport(recordId,messageId,providerId,"",recipient,reportResult,reportStatus,reportTime,null,providerMessage);
        System.out.println(bizResult.getResultObject());
    }

    @Test
    public void getMessageById(){
        String messageId="b1befc67-e957-4929-b875-94987a0b007e";
        LocalDateTime endTime=LocalDateTime.now();
        //前3天
        LocalDateTime startTime=endTime.minusDays(3);
        SMSMessageDTO smsMessageDTO=smsMessageBiz.getMessageByMessageId(messageId,startTime,endTime);
        System.out.println(JSONObject.toJSON(smsMessageDTO));
    }


    @Test
    public void updateRecordExtByRecordId(){
        String recordId="f9c8a7bd-de2b-4fb6-9659-ed71ce7ce208";
        String callSid="cash xu2";
        LocalDateTime endTime=LocalDateTime.now();
        //前3天
        LocalDateTime startTime=endTime.minusDays(3);
        smsMessageRecordBiz.updateRecordExtByRecordId(recordId,callSid,startTime,endTime);
    }

    @Test
    public void getRecordByRecordExt(){
        String callSid="cash xu";
        LocalDateTime endTime=LocalDateTime.now();
        //前3天
        LocalDateTime startTime=endTime.minusDays(3);
        SMSMessageRecordDTO smsMessageRecordDTO=smsMessageRecordBiz.getRecordByRecordExt(callSid,startTime,endTime);
        System.out.println("0000000000000000"+JSONObject.toJSON(smsMessageRecordDTO));
    }

    @Test
    public void updateRecordStatusByRecordExt(){
        String s = "logback-spring.xml";
        URL url = this.getClass().getClassLoader().getResource(s);
        System.out.println(url);
        String callSid="cash xu";
        int status=-98;
        LocalDateTime endTime=LocalDateTime.now();
        //前3天
        LocalDateTime startTime=endTime.minusDays(3);
        smsMessageRecordBiz.updateStatusByRecordExt(callSid,status,startTime,endTime);
    }

    @Test
    public void updateRecordStatusByRecordId(){
        String recordId="fbc3ed05-f09a-4023-9ab6-df0c013de4e6";
        int status=-99;
        LocalDateTime endTime=LocalDateTime.now();
        //前4天
        LocalDateTime startTime=endTime.minusDays(4);
        smsMessageRecordBiz.updateStatusByRecordId(recordId,status,startTime,endTime);
    }


    @Test
    public void updateMessageStatusByMessageId(){
        LocalDateTime end=LocalDateTime.now();
        //三天之前
        LocalDateTime start=end.minusDays(7);
        LocalDateTime end1=end.minusDays(6);
        String messageId="c7ced0d2-491d-4ca5-91fb-924c43ca2740";

        if(!StringUtils.isEmpty(messageId)){

            ChangeMessageStatusRequest changeMessageStatusRequest=new ChangeMessageStatusRequest();
            changeMessageStatusRequest.setMessageId(messageId);
            //前3天
            changeMessageStatusRequest.setStartTime(start+"");
            changeMessageStatusRequest.setEndTime(end+"");

            changeMessageStatusRequest.setMessageStatus(MessageStatus.SENDFAIL);

            ChangeMessageStatueResponse changeMessageStatueResponse=messageService.changeMessageStatue(changeMessageStatusRequest);
            if(InvokeResult.SUCCESS.getCode()!=changeMessageStatueResponse.getResultCode()){
                logger.error("根据MessageId更新消息状态异常,messageId:"+messageId+" : "+changeMessageStatueResponse.getResultMessage());
            }
        }
    }


    @Test
    public void getMessageByMessageId(){
        String messageId="c7ced0d2-491d-4ca5-91fb-924c43ca2740";
        //查询recipient
        MessageQueryRequest messageQueryRequest=new MessageQueryRequest();
        messageQueryRequest.setMessageId(messageId);

        LocalDateTime localDate=LocalDateTime.now();
        //前3天
        messageQueryRequest.setStartTime(localDate.minusDays(3)+"");
        messageQueryRequest.setEndTime(localDate+"");

        MessageQueryResponse messageQueryResponse=messageService.query(messageQueryRequest);
        System.out.println(JSONObject.toJSON(messageQueryResponse));
    }
}
