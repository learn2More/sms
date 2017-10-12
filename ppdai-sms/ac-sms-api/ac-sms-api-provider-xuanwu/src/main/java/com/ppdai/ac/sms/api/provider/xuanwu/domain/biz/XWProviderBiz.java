package com.ppdai.ac.sms.api.provider.xuanwu.domain.biz;

import com.esms.MOMsg;
import com.esms.MessageData;
import com.esms.PostMsg;
import com.esms.common.entity.Account;
import com.esms.common.entity.GsmsResponse;
import com.esms.common.entity.MTPack;
import com.esms.common.entity.MTReport;
import com.ppdai.ac.sms.api.provider.xuanwu.configuration.XWProviderProperties;
import com.ppdai.ac.sms.api.provider.xuanwu.domain.model.XWReportReturnCode;
import com.ppdai.ac.sms.api.provider.xuanwu.domain.model.XWSendReturnCode;
import com.ppdai.ac.sms.api.provider.xuanwu.request.SendMsg;
import com.ppdai.ac.sms.provider.core.dao.domain.SmsMessageMoRecordBiz;
import com.ppdai.ac.sms.provider.core.dao.domain.SmsMessageReportBiz;
import com.ppdai.ac.sms.provider.core.enums.InvokeResult;
import com.ppdai.ac.sms.provider.core.enums.ReportStatus;
import com.ppdai.ac.sms.provider.core.model.bo.BizResult;
import com.ppdai.ac.sms.provider.core.protocol.ProviderService;
import com.ppdai.ac.sms.provider.core.protocol.response.ProviderConfigResponse;
import com.ppdai.ac.sms.provider.core.response.SmsResponse;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * author cash
 * create 2017-05-04-16:59
 **/

@Service
public class XWProviderBiz {
    private final static Logger logger = LoggerFactory.getLogger(XWProviderBiz.class);

    @Autowired
    XWProviderProperties xwProviderProperties;


    @Autowired
    SmsMessageMoRecordBiz smsMessageMoRecordBiz;

    @Autowired
    SmsMessageReportBiz smsMessageReportBiz;


    @Autowired
    ProviderService providerService;

    private PostMsg pm;

    private  Lock lock = new ReentrantLock();

    public SmsResponse send(SendMsg sendMsg) {

        SmsResponse sendResponse=new SmsResponse();
        sendResponse.setResultObject(null);

        List<String> mobiles=sendMsg.getMobiles();

        //TODO 获取服务商相关配置,初始化
        Account ac = new Account(sendMsg.getName(), sendMsg.getPassword());

        PostMsg pm =getPostMsg();
        MTPack pack = new MTPack();
        pack.setBatchID(UUID.randomUUID());
        pack.setBatchName("");
        pack.setMsgType(MTPack.MsgType.SMS);//短信
        pack.setBizType(0);
        pack.setDistinctFlag(false);//是否去重
        ArrayList<MessageData> msgs = new ArrayList<MessageData>();


        /** 群发，多号码一内容 */
        pack.setSendType(MTPack.SendType.MASS);
        String content = sendMsg.getContent();
        for(String s:mobiles){
            MessageData messageData=new MessageData(s, content);
            messageData.setCustomMsgID(sendMsg.getMessageId());
            //设置扩展码,用于上行匹配
            messageData.setCustomNum(sendMsg.getExtendNo());
            msgs.add(messageData);
        }

        pack.setMsgs(msgs);


        try {
            GsmsResponse resp = pm.post(ac, pack);
            if(XWSendReturnCode.SUCCESS.getCode()==resp.getResult()){
                sendResponse.setResultCode(InvokeResult.SUCCESS.getCode());
                sendResponse.setResultMessage("发送成功");
                sendResponse.setResultObject(resp.getUuid()+"");
            }else if(XWSendReturnCode.BALANCE_IS_NOT_ENOUGH.getCode()==resp.getResult()){
                sendResponse.setResultCode(InvokeResult.FAIL.getCode());
                sendResponse.setResultMessage("玄武短信发送异常,余额不足");
            }else{
                sendResponse.setResultCode(InvokeResult.FAIL.getCode());
                sendResponse.setResultMessage("玄武短信发送异常,"+resp.getMessage());
            }

        } catch (Exception e) {
            logger.error("xuanwu sendMsgError",e);
            sendResponse.setResultCode(InvokeResult.FAIL.getCode());
            sendResponse.setResultMessage("玄武短信发送异常");
        }

        return sendResponse;
    }




    public SmsResponse getDelivers(String providerId){
        // TODO: 2017/5/19   配置需缓存
        logger.info("xuanwu 根据providerId getDelivers begin,providerId: "+providerId);
        SmsResponse smsResponse=new SmsResponse();
        smsResponse.setResultCode(InvokeResult.SUCCESS.getCode());
        smsResponse.setResultMessage("");

        String lusername="";
        String lpasw0rd="";

        String providerName="";

        //TODO 获取服务商相关配置,初始化
        ProviderConfigResponse providerConfigResponse=providerService.getProviderConfigByProviderId(Integer.parseInt(providerId));
        if(InvokeResult.SUCCESS.getCode()==providerConfigResponse.getResultCode()){
            if(null!=providerConfigResponse.getResultObject()){
                List<LinkedHashMap<String,String>> listConfig= (List<LinkedHashMap<String, String>>) providerConfigResponse.getResultObject();
                if(CollectionUtils.isNotEmpty(listConfig)){
                    providerName=listConfig.get(0).get("ProviderName");
                    for(LinkedHashMap<String,String> map :listConfig){
                        if(null!=map.get("ConfigKey")){
                            if("name".equals(map.get("ConfigKey"))){
                                if(null!=map.get("ConfigValue")&&!"".equals(map.get("ConfigValue"))){
                                    lusername=map.get("ConfigValue");
                                    continue;
                                }
                            }
                            if("password".equals(map.get("ConfigKey"))){
                                if(null!=map.get("ConfigValue")&&!"".equals(map.get("ConfigValue"))){
                                    lpasw0rd=map.get("ConfigValue");
                                    continue;
                                }
                            }
                        }
                    }
                }

            }
        }
        if(StringUtils.isEmpty(lusername)||StringUtils.isEmpty(lpasw0rd)){
            logger.error("玄武拉取上行,providerId:"+providerId+" 参数配置错误");
            smsResponse.setResultCode(InvokeResult.FAIL.getCode());
            smsResponse.setResultMessage("玄武拉取上行,参数配置错误");
            return smsResponse;
        }

        Account ac = new Account(lusername, lpasw0rd);
        PostMsg pm = getPostMsg();


        MOMsg[] mos ;
        try {
            mos = pm.getMOMsgs(ac, xwProviderProperties.getPerGetMoReportNumber());
            logger.info(String.format("--------------玄武拉取上行,providerId:%s,每次拉取条数：%s",providerId,xwProviderProperties.getPerGetMoReportNumber()));
            if(mos != null && mos.length>0){
                for(MOMsg mo : mos){
                    String channelNo=providerId;
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
                    BizResult bizResult=smsMessageMoRecordBiz.saveMoRecord(channelNo,providerName,sender,content,extendNo,timestamp);
                    if(InvokeResult.SUCCESS.getCode()!=bizResult.getResultCode()){
                        logger.error("玄武 extendNo: "+extendNo+" mobile: "+sender+" 保存上行记录异常:"+ bizResult.getResultMessage());
                    }
                }
            }else{
                logger.info("玄武providerId: "+providerId+" 拉取上行,无数据");
            }
        } catch (Exception e) {
            //logger.error("玄武拉取上行异常",e);
            logger.error("xuanwu getDeliversError",e);
            smsResponse.setResultCode(InvokeResult.FAIL.getCode());
            smsResponse.setResultMessage("玄武providerId: "+providerId+" 拉取上行异常");
        }

        return  smsResponse;

    }

    public SmsResponse getReports(String providerId){
        // TODO: 2017/5/19   配置需缓存
        logger.info("xuanwu 根据providerId getReports Begin,providerId: "+providerId);

        SmsResponse smsResponse=new SmsResponse();
        smsResponse.setResultCode(InvokeResult.SUCCESS.getCode());
        smsResponse.setResultMessage("");

        String lusername="";
        String lpasw0rd="";

        String providerName="";

        //TODO 获取服务商相关配置,初始化
        ProviderConfigResponse providerConfigResponse=providerService.getProviderConfigByProviderId(Integer.parseInt(providerId));
        if(InvokeResult.SUCCESS.getCode()==providerConfigResponse.getResultCode()){
            if(null!=providerConfigResponse.getResultObject()){
                List<LinkedHashMap<String,String>> listConfig= (List<LinkedHashMap<String, String>>) providerConfigResponse.getResultObject();
                if(CollectionUtils.isNotEmpty(listConfig)){
                    providerName=listConfig.get(0).get("ProviderName");
                    for(LinkedHashMap<String,String> map :listConfig){
                        if(null!=map.get("ConfigKey")){
                            if("name".equals(map.get("ConfigKey"))){
                                if(null!=map.get("ConfigValue")&&!"".equals(map.get("ConfigValue"))){
                                    lusername=map.get("ConfigValue");
                                    continue;
                                }
                            }
                            if("password".equals(map.get("ConfigKey"))){
                                if(null!=map.get("ConfigValue")&&!"".equals(map.get("ConfigValue"))){
                                    lpasw0rd=map.get("ConfigValue");
                                    continue;
                                }
                            }
                        }
                    }
                }

            }
        }

        if(StringUtils.isEmpty(lusername)||StringUtils.isEmpty(lpasw0rd)){
            logger.error("玄武拉取回执,providerId:"+providerId+" 参数配置错误");
            smsResponse.setResultCode(InvokeResult.FAIL.getCode());
            smsResponse.setResultMessage("玄武拉取回执,参数配置错误");
            return smsResponse;
        }
        Account ac = new Account(lusername, lpasw0rd);
        PostMsg pm = getPostMsg();


        MTReport[] mtReports;
        try {
            mtReports = pm.getReports(ac, xwProviderProperties.getPerGetReportNumber());
            logger.info(String.format("--------------玄武拉取回执,providerId:%s,每次拉取条数：%s",providerId,xwProviderProperties.getPerGetReportNumber()));
            if(mtReports != null && mtReports.length>0){
                for(MTReport mtReport:mtReports){
                    String recordId=mtReport.getCustomMsgID();
                    //玄武长短信返回 原 customMsgId+"#"+当前条数
                    recordId=recordId.split("#")[0];
                    String reportResult=mtReport.getOriginResult();
                    logger.info("-------------xuanwu getReport is not null recordId: "+recordId+" reportResult: "+reportResult);
                    //String messageId=mtReport.getMsgID();
                    String messageId="";
                    String recipient=mtReport.getPhone();

                    int reportStatus= ReportStatus.SUCCESS_REPORT.getCode();
                    int code=mtReport.getState();
                    if(XWReportReturnCode.DELIVERED.getCode()!=code){
                        reportStatus=ReportStatus.ERROR_REPORT.getCode();
                    }

                    //完整报告
                    String providerMessage=mtReport.toString();
                    long time=mtReport.getDoneTime();
                    Timestamp reportTime=new Timestamp(time);

                    BizResult bizResult=smsMessageReportBiz.saveMessageReport(recordId,messageId,providerId,providerName,recipient,reportResult,reportStatus,reportTime,null,providerMessage);
                    if(InvokeResult.SUCCESS.getCode()!=bizResult.getResultCode()){
                        logger.error("玄武 recordId: "+recordId+" 保存回执报告异常:"+ bizResult.getResultMessage());
                    }

                }
            }else{
                logger.info("玄武providerId: "+providerId+" 拉取回执,无数据");
            }
        } catch (Exception e) {
            logger.error("xuanwu getReportsError",e);
            smsResponse.setResultCode(InvokeResult.FAIL.getCode());
            smsResponse.setResultMessage("玄武providerId: "+providerId+" 拉取回执异常");
        }

        return  smsResponse;
    }

    private PostMsg getPostMsg(){
        try {
            lock.lock();
            if(null!=pm){
                logger.info("============================================ pm:"+pm);
                return pm;
            }else{
                pm = new PostMsg();
                pm.getCmHost().setHost(xwProviderProperties.getCmHost(),xwProviderProperties.getCmPort());//设置网关的IP和port，用于发送信息
                pm.getWsHost().setHost(xwProviderProperties.getWsHost(), xwProviderProperties.getWsPort());//设置网关的 IP和port，用于获取账号信息、上行、状态报告等等
            }

        } catch (Exception e) {
            logger.error("getPostMsg error",e);
        } finally {
            lock.unlock();
        }
        return pm;
    }


}
