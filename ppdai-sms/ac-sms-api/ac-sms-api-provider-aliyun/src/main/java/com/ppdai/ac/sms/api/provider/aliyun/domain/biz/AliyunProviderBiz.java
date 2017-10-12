package com.ppdai.ac.sms.api.provider.aliyun.domain.biz;

import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.CloudTopic;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.common.ServiceException;
import com.aliyun.mns.model.*;
import com.ppdai.ac.sms.api.provider.aliyun.request.SendMsgRequest;
import com.ppdai.ac.sms.api.provider.aliyun.response.ReportResponse;
import com.ppdai.ac.sms.api.provider.aliyun.utils.AliyunUtil;
import com.ppdai.ac.sms.provider.core.dao.domain.SmsMessageMoRecordBiz;
import com.ppdai.ac.sms.provider.core.dao.domain.SmsMessageRecordBiz;
import com.ppdai.ac.sms.provider.core.dao.domain.SmsMessageReportBiz;
import com.ppdai.ac.sms.provider.core.enums.InvokeResult;
import com.ppdai.ac.sms.provider.core.enums.RecordStatus;
import com.ppdai.ac.sms.provider.core.enums.ReportStatus;
import com.ppdai.ac.sms.provider.core.model.bo.BizResult;
import com.ppdai.ac.sms.provider.core.model.entity.SMSMessageRecordDTO;
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
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

/**
 * author cash
 * create 2017-05-05-15:10
 **/

@Service
public class AliyunProviderBiz {
    private static final Logger logger = LoggerFactory.getLogger(AliyunProviderBiz.class);


    @Autowired
    SmsMessageRecordBiz smsMessageRecordBiz;

    @Autowired
    SmsMessageReportBiz smsMessageReportBiz;

    @Autowired
    SmsMessageMoRecordBiz smsMessageMoRecordBiz;

    @Autowired
    ProviderService providerService;

    public SmsResponse send(SendMsgRequest sendMsgRequest) {
        SmsResponse sendResponse=new SmsResponse();
        sendResponse.setResultMessage("");
        /**
         * Step 1. 获取主题引用
         */
        CloudAccount account = new CloudAccount(sendMsgRequest.getAccessKey(), sendMsgRequest.getAccessSecret(), sendMsgRequest.getEndpoint());
        MNSClient client = account.getMNSClient();
        CloudTopic topic = client.getTopicRef(sendMsgRequest.getTopicName());
        /**
         * Step 2. 设置SMS消息体（必须）
         *
         * 注：目前暂时不支持消息内容为空，需要指定消息内容，不为空即可。
         */
        RawTopicMessage msg = new RawTopicMessage();
        msg.setMessageBody("sms-message");
        /**
         * Step 3. 生成SMS消息属性
         */
        MessageAttributes messageAttributes = new MessageAttributes();
        BatchSmsAttributes batchSmsAttributes = new BatchSmsAttributes();
        // 3.1 设置发送短信的签名（SMSSignName）
        batchSmsAttributes.setFreeSignName(sendMsgRequest.getSignName());
        // 3.2 设置发送短信使用的模板（SMSTempateCode）
        batchSmsAttributes.setTemplateCode(sendMsgRequest.getTemplateCode());
        // 3.3 设置发送短信所使用的模板中参数对应的值（在短信模板中定义的，没有可以不用设置）
        BatchSmsAttributes.SmsReceiverParams smsReceiverParams = new BatchSmsAttributes.SmsReceiverParams();
        Map<String,String> params=sendMsgRequest.getParamMap();

        for (Map.Entry<String,String> entry : params.entrySet()) {
            smsReceiverParams.setParam(entry.getKey(), entry.getValue());
        }


        // 3.4 增加接收短信的号码
        for(String mobile:sendMsgRequest.getMobiles()){
            batchSmsAttributes.addSmsReceiver(mobile, smsReceiverParams);

        }
        //设置扩展码,用于上行匹配
        batchSmsAttributes.setExtendCode(sendMsgRequest.getExtendNo());

        messageAttributes.setBatchSmsAttributes(batchSmsAttributes);
        try {
            /**
             * Step 4. 发布SMS消息
             */
            TopicMessage ret = topic.publishMessage(msg, messageAttributes);
            logger.info("----------------------------recordId :"+sendMsgRequest.getRecordId()+" 发送返回messageBoay: "+ret.getMessageBody());
            String recordExt=ret.getMessageId();
            //ali返回消息id,反向设置
            LocalDateTime endTime=LocalDateTime.now();
            //前3天
            LocalDateTime startTime=endTime.minusDays(3);
            if(StringUtils.isNotEmpty(recordExt) && StringUtils.isNotEmpty(sendMsgRequest.getRecordId())){
                smsMessageRecordBiz.updateRecordExtByRecordId(sendMsgRequest.getRecordId(),recordExt,startTime,endTime);
            }

            sendResponse.setResultMessage("发送成功");
            sendResponse.setResultCode(InvokeResult.SUCCESS.getCode());
            sendResponse.setResultObject(ret.getMessageId());
        } catch (ServiceException se) {
            sendResponse.setResultCode(InvokeResult.FAIL.getCode());
            sendResponse.setResultMessage("阿里云 "+se.getRequestId()+"短信发送异常:"+se.getErrorCode());
            logger.error("阿里云 "+se.getRequestId()+"短信发送异常:"+se.getErrorCode(),se);
        } catch (Exception e) {
            sendResponse.setResultCode(InvokeResult.FAIL.getCode());
            sendResponse.setResultMessage("阿里云短信发送异常");
            logger.error("阿里云短信发送异常",e);

        }
        client.close();

        return sendResponse;
    }


    public SmsResponse getDelivers(String providerId){
        logger.info("aliyun 根据providerId获取上行报告开始,providerId: "+providerId);

        SmsResponse smsResponse=new SmsResponse();

        smsResponse.setResultCode(InvokeResult.SUCCESS.getCode());
        smsResponse.setResultMessage("");

        String accessKey="";
        String accessSecret="";
        String endpoint="";
        String replyQueueName="";

        String providerName="";

        ProviderConfigResponse providerConfigResponse=providerService.getProviderConfigByProviderId(Integer.parseInt(providerId));
        if(InvokeResult.SUCCESS.getCode()==providerConfigResponse.getResultCode()
                &&(null!=providerConfigResponse.getResultObject()) ){

                List<LinkedHashMap<String,String>> listConfig= (List<LinkedHashMap<String, String>>) providerConfigResponse.getResultObject();
                if(CollectionUtils.isNotEmpty(listConfig)){
                    providerName=listConfig.get(0).get("ProviderName");
                    for(LinkedHashMap<String,String> map :listConfig){
                        if(null!=map.get("ConfigKey")){
                            if("accessKey".equals(map.get("ConfigKey")) &&
                                     (null!=map.get("ConfigValue")&&!"".equals(map.get("ConfigValue"))) ){
                                    accessKey=map.get("ConfigValue");
                                    continue;
                            }
                            if("accessSecret".equals(map.get("ConfigKey")) &&
                                    (null!=map.get("ConfigValue")&&!"".equals(map.get("ConfigValue"))) ){
                                    accessSecret=map.get("ConfigValue");
                                    continue;
                            }
                            if("endpoint".equals(map.get("ConfigKey")) &&
                                    (null!=map.get("ConfigValue")&&!"".equals(map.get("ConfigValue"))) ){
                                    endpoint=map.get("ConfigValue");
                                    continue;
                            }
                            if("replyQueueName".equals(map.get("ConfigKey")) &&
                                    (null!=map.get("ConfigValue")&&!"".equals(map.get("ConfigValue")))){
                                    replyQueueName=map.get("ConfigValue");
                                    continue;
                            }
                        }
                    }
                }
        }
        if(StringUtils.isEmpty(accessKey)||StringUtils.isEmpty(accessSecret)||StringUtils.isEmpty(endpoint)||StringUtils.isEmpty(replyQueueName)){
            logger.error("aliyun拉取上行,provider: "+providerId+" 配置错误");
            smsResponse.setResultCode(InvokeResult.FAIL.getCode());
            smsResponse.setResultMessage("provider: "+providerId+" 配置错误");
            return smsResponse;
        }

        /**
         * Step 1. 获取主题引用
         */
        CloudAccount account = new CloudAccount(accessKey, accessSecret, endpoint);
        MNSClient mnsClient = account.getMNSClient();

        CloudQueue replyQueue=mnsClient.getQueueRef(replyQueueName);

        List<Message> sMessages = replyQueue.batchPopMessage(16);

        if(CollectionUtils.isNotEmpty(sMessages)){
            for(Message m:sMessages){
                logger.info("------------aliyun上行返回："+m.getMessageBodyAsRawString());
                Map<String,String> map= AliyunUtil.parseMotMsg(m.getMessageBodyAsRawString());

                String channelNo=providerId;
                String sender=map.get("sender");
                String content=map.get("content");
                //扩展码
                String extendNo=map.get("extendNo");

                String  time=map.get("receiveTime");
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
                Date d;
                try {
                    d = format.parse(time);
                } catch (Exception e) {
                    logger.error("阿里云extendNo: "+extendNo+" 拉取上行,日期转换异常",e);
                    continue;
                }

                Timestamp timestamp = new Timestamp(d.getTime());

                BizResult bizResult=smsMessageMoRecordBiz.saveMoRecord(channelNo,providerName,sender,content,extendNo,timestamp);
                if(InvokeResult.SUCCESS.getCode()!=bizResult.getResultCode()){
                    logger.error("阿里云extendNo： :"+extendNo+" 保存上行记录异常:"+ bizResult.getResultMessage());
                    continue;

                }
                //删除已经消费的消息
                replyQueue.deleteMessage(m.getReceiptHandle());
            }
        }else{
            logger.info("阿里云,获取上行短信为空");
        }

        mnsClient.close();

        return smsResponse;
    }

    public SmsResponse getSuccessReport(String providerId){
        logger.info("aliyun 根据providerId获取成功回执开始,providerId: "+providerId);

        SmsResponse smsResponse=new SmsResponse();
        smsResponse.setResultCode(InvokeResult.SUCCESS.getCode());
        smsResponse.setResultMessage("");

        String accessKey="";
        String accessSecret="";
        String endpoint="";
        String successQueueName="";

        String providerName="";

        ProviderConfigResponse providerConfigResponse=providerService.getProviderConfigByProviderId(Integer.parseInt(providerId));
        if(InvokeResult.SUCCESS.getCode()==providerConfigResponse.getResultCode() &&
                null!=providerConfigResponse.getResultObject()){
                List<LinkedHashMap<String,String>> listConfig= (List<LinkedHashMap<String, String>>) providerConfigResponse.getResultObject();
                if(CollectionUtils.isNotEmpty(listConfig)){
                    providerName=listConfig.get(0).get("ProviderName");
                    for(LinkedHashMap<String,String> map :listConfig){
                        if(null!=map.get("ConfigKey")){
                            if("accessKey".equals(map.get("ConfigKey")) &&
                                    (null!=map.get("ConfigValue")&&!"".equals(map.get("ConfigValue")))  ){
                                    accessKey=map.get("ConfigValue");
                                    continue;
                            }
                            if("accessSecret".equals(map.get("ConfigKey")) &&
                                    (null!=map.get("ConfigValue")&&!"".equals(map.get("ConfigValue")))  ){
                                    accessSecret=map.get("ConfigValue");
                                    continue;
                            }
                            if("endpoint".equals(map.get("ConfigKey")) &&
                                    (null!=map.get("ConfigValue")&&!"".equals(map.get("ConfigValue")))){
                                    endpoint=map.get("ConfigValue");
                                    continue;
                            }
                            if("successQueueName".equals(map.get("ConfigKey")) &&
                                    (null!=map.get("ConfigValue")&&!"".equals(map.get("ConfigValue")))){
                                    successQueueName=map.get("ConfigValue");
                                    continue;
                            }
                        }
                    }
                }


        }
        if(StringUtils.isEmpty(accessKey)||StringUtils.isEmpty(accessSecret)||StringUtils.isEmpty(endpoint)||StringUtils.isEmpty(successQueueName)){
            logger.error("aliyun拉取成功的回执报告,provider: "+providerId+" 配置错误");
            smsResponse.setResultCode(InvokeResult.FAIL.getCode());
            smsResponse.setResultMessage("provider: "+providerId+" 配置错误");
            return smsResponse;
        }


        /**
         * Step 1. 获取主题引用
         */
        CloudAccount account = new CloudAccount(accessKey, accessSecret, endpoint);
        MNSClient mnsClient = account.getMNSClient();


        CloudQueue successQueue = mnsClient.getQueueRef(successQueueName);

        List<Message> sMessages = successQueue.batchPopMessage(16);


        if(CollectionUtils.isNotEmpty(sMessages)){
            for(Message m:sMessages){
                logger.info("------------aliyun回执返回："+m.getMessageBodyAsRawString());
                Map<String,String> map= AliyunUtil.parseReportMsg(m.getMessageBodyAsRawString());

                String messageId="";
                Timestamp sendTime=null;
                String recordExt=map.get("messageId");
                //ali 消息的messageId 不是recordId，是recordExt
                LocalDateTime endTime=LocalDateTime.now();
                //前3天
                LocalDateTime startTime=endTime.minusDays(3);
                SMSMessageRecordDTO smsMessageRecordDTO=smsMessageRecordBiz.getRecordByRecordExt(recordExt,startTime,endTime);
                if(null!=smsMessageRecordDTO){
                    messageId=smsMessageRecordDTO.getMessageId();
                    sendTime=smsMessageRecordDTO.getSendTime();
                }
                String recipient=map.get("phone");

                //success queue中拉取的都是成功的
                String reportResult="DELIVRD";
                int reportStatus= ReportStatus.SUCCESS_REPORT.getCode();

                //完整报告
                String providerMessage=m.toString();
                String  time=map.get("receiveTime");
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date d;
                try {
                    d = format.parse(time);
                } catch (Exception e) {
                    logger.error("阿里云recordExt："+recordExt+" 拉取成功的回执,日期转换异常",e);
                    continue;
                }
                Timestamp reportTime = new Timestamp(d.getTime());

                BizResult bizResult=smsMessageReportBiz.saveMessageReport(recordExt,messageId,providerId,providerName,recipient,reportResult,reportStatus,reportTime,sendTime,providerMessage);
                if(InvokeResult.SUCCESS.getCode()!=bizResult.getResultCode()){
                    logger.error("阿里云recordExt："+recordExt+" 保存成功的回执报告异常："+bizResult.getResultMessage());
                    continue;
                }

                //根据recordExt更新record记录的状态
                int status=RecordStatus.SEND_SUCCESS.getCode();
                LocalDateTime localDate=LocalDateTime.now();
                smsMessageRecordBiz.updateStatusByRecordExt(recordExt,status,localDate.minusDays(3),localDate);

                //删除已经消费的消息
                successQueue.deleteMessage(m.getReceiptHandle());
            }
        }else{
            logger.info("阿里云,获取发送成功的短信回执为空");
        }

        mnsClient.close();

        return smsResponse;
    }


    public SmsResponse getFailReport(String providerId){

        logger.info("aliyun 根据providerId获取失败回执开始,providerId: "+providerId);

        SmsResponse smsResponse=new SmsResponse();
        smsResponse.setResultCode(InvokeResult.SUCCESS.getCode());
        smsResponse.setResultMessage("");

        String accessKey="";
        String accessSecret="";
        String endpoint="";
        String failQueueName="";

        String providerName="";

        ProviderConfigResponse providerConfigResponse=providerService.getProviderConfigByProviderId(Integer.parseInt(providerId));
        if(InvokeResult.SUCCESS.getCode()==providerConfigResponse.getResultCode() &&
                null!=providerConfigResponse.getResultObject() ){
                List<LinkedHashMap<String,String>> listConfig= (List<LinkedHashMap<String, String>>) providerConfigResponse.getResultObject();
                if(CollectionUtils.isNotEmpty(listConfig)){
                    providerName=listConfig.get(0).get("ProviderName");
                    for(LinkedHashMap<String,String> map :listConfig){
                        if(null!=map.get("ConfigKey")){

                            if("accessKey".equals(map.get("ConfigKey")) &&
                                    (null!=map.get("ConfigValue")&&!"".equals(map.get("ConfigValue")))){
                                accessKey=map.get("ConfigValue");
                                continue;
                            }

                            if("accessSecret".equals(map.get("ConfigKey")) &&
                                    (null!=map.get("ConfigValue")&&!"".equals(map.get("ConfigValue")))){
                                    accessSecret=map.get("ConfigValue");
                                    continue;
                            }
                            if("endpoint".equals(map.get("ConfigKey")) &&
                                    (null!=map.get("ConfigValue")&&!"".equals(map.get("ConfigValue")))){
                                    endpoint=map.get("ConfigValue");
                                    continue;
                            }
                            if("failQueueName".equals(map.get("ConfigKey")) &&
                                    (null!=map.get("ConfigValue")&&!"".equals(map.get("ConfigValue")))){
                                    failQueueName=map.get("ConfigValue");
                                    continue;
                            }
                        }
                    }
                }
        }
        if(StringUtils.isEmpty(accessKey)||StringUtils.isEmpty(accessSecret)||StringUtils.isEmpty(endpoint)||StringUtils.isEmpty(failQueueName)){
            logger.error("aliyun拉取失败的回执报告,provider: "+providerId+" 配置错误");
            smsResponse.setResultCode(InvokeResult.FAIL.getCode());
            smsResponse.setResultMessage("provider: "+providerId+" 配置错误");
            return smsResponse;
        }


        /**
         * Step 1. 获取主题引用
         */
        CloudAccount account = new CloudAccount(accessKey, accessSecret, endpoint);
        MNSClient mnsClient = account.getMNSClient();

        ReportResponse reportResponse=new ReportResponse();
        reportResponse.setResultMessage("");
        CloudQueue failQueue = mnsClient.getQueueRef(failQueueName);

        List<Message> sMessages = failQueue.batchPopMessage(16);

        reportResponse.setResultCode(InvokeResult.SUCCESS.getCode());
        reportResponse.setMessages(sMessages);
        LocalDateTime endTime=LocalDateTime.now();
        //前3天
        LocalDateTime startTime=endTime.minusDays(3);
        if(CollectionUtils.isNotEmpty(sMessages)){
            for(Message m:sMessages){
                logger.info("------------aliyun回执返回："+m.getMessageBodyAsRawString());
                Map<String,String> map= AliyunUtil.parseReportMsg(m.getMessageBodyAsRawString());
                String messageId="";
                String recordExt=map.get("messageId");

                Timestamp sendTime=null;

                //ali 消息的messageId 不是recordId，是recordExt
                SMSMessageRecordDTO smsMessageRecordDTO=smsMessageRecordBiz.getRecordByRecordExt(recordExt,startTime,endTime);
                if(null!=smsMessageRecordDTO){
                    messageId=smsMessageRecordDTO.getMessageId();
                    sendTime=smsMessageRecordDTO.getSendTime();
                }
                String recipient=map.get("phone");

                //fail queue中拉取的都是失败的
                int reportStatus=ReportStatus.ERROR_REPORT.getCode();
                String reportResult=map.get("errorCode");

                String providerMessage=m.toString();

                String  time=map.get("receiveTime");
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date d;
                try {
                    d = format.parse(time);
                } catch (Exception e) {
                    logger.error("阿里云recordExt "+recordExt+" 拉取失败的回执,日期转换异常",e);
                    continue;
                }
                Timestamp reportTime = new Timestamp(d.getTime());

                BizResult bizResult=smsMessageReportBiz.saveMessageReport(recordExt,messageId,providerId,providerName,recipient,reportResult,reportStatus,reportTime,sendTime,providerMessage);
                if(InvokeResult.SUCCESS.getCode()!=bizResult.getResultCode()){
                    logger.error("阿里云recordExt "+recordExt+" 保存失败的回执报告异常："+bizResult.getResultMessage());
                    continue;
                }

                //根据recordExt更新record记录的状态
                int status=RecordStatus.SEND_SUCCESS.getCode();
                LocalDateTime localDate=LocalDateTime.now();
                smsMessageRecordBiz.updateStatusByRecordExt(recordExt,status,localDate.minusDays(3),localDate);

                //删除已经消费的消息
                failQueue.deleteMessage(m.getReceiptHandle());
            }
        }else{
            logger.info("阿里云,获取发送失败的短信回执为空");
        }

        mnsClient.close();

        return smsResponse;

    }



}
