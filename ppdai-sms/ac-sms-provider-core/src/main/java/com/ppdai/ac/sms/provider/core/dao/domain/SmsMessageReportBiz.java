package com.ppdai.ac.sms.provider.core.dao.domain;

import com.alibaba.fastjson.JSONObject;
import com.dianping.cat.Cat;
import com.dianping.cat.message.Message;
import com.dianping.cat.message.Transaction;
import com.ppdai.ac.sms.provider.core.configuration.CommonProperties;
import com.ppdai.ac.sms.provider.core.dao.mapper.smsmessage.SmsMessageRecordMapper;
import com.ppdai.ac.sms.provider.core.dao.mapper.smsmessage.SmsMessageReportMapper;
import com.ppdai.ac.sms.provider.core.enums.InvokeResult;
import com.ppdai.ac.sms.provider.core.enums.RecordStatus;
import com.ppdai.ac.sms.provider.core.enums.ReportStatus;
import com.ppdai.ac.sms.provider.core.model.bo.BizResult;
import com.ppdai.ac.sms.provider.core.model.entity.SMSMessageRecordDTO;
import com.ppdai.ac.sms.provider.core.model.entity.SMSMessageReportDTO;
import com.ppdai.ac.sms.provider.core.protocol.MessageService;
import com.ppdai.ac.sms.provider.core.protocol.request.ChangeMessageStatusRequest;
import com.ppdai.ac.sms.provider.core.protocol.request.MessageStatus;
import com.ppdai.ac.sms.provider.core.protocol.response.ChangeMessageStatueResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * author cash
 * create 2017-05-09-19:09
 **/

@Component
public class SmsMessageReportBiz {

    private static final Logger logger = LoggerFactory.getLogger(SmsMessageReportBiz.class);

    @Autowired
    CommonProperties commonProperties;

    @Autowired
    MessageService messageService;


    @Autowired
    SmsMessageReportMapper smsMessageReportMapper;

    @Autowired
    SmsMessageRecordMapper smsMessageRecordMapper;


    public BizResult saveMessageReport(String  recordId, String messageId, String providerId,String providerName, String recipient,
                                       String reportResult, int reportStatus, Timestamp reportTime,Timestamp sendTime, String providerMessage){

        Transaction transaction= Cat.newTransaction("saveMessageReport",String.format("%s",providerId)+" : "+recordId);

        logger.info(String.format("saveMessageReport provider:%s  recordId:%s  messageId:%s ",providerId,recordId,messageId));

        String tempMsgId=messageId;
        Timestamp tempSdTime=sendTime;

        BizResult bizResult= null;
        try {
            bizResult = new BizResult();
            bizResult.setResultMessage("");
            SMSMessageReportDTO dto=new SMSMessageReportDTO();
            dto.setRecordId(recordId);

            LocalDateTime end=LocalDateTime.now();
            //n 天之前
            LocalDateTime start=end.minusDays(commonProperties.getRecordSeveralDaysAgo());
            if(StringUtils.isNotEmpty(recordId)){
                Timestamp startTime=Timestamp.from(start.atZone(ZoneId.systemDefault()).toInstant());
                Timestamp endTime=Timestamp.from(end.atZone(ZoneId.systemDefault()).toInstant());


                //更新record记录的状态
                try {
                    int status=0;
                    if(ReportStatus.SUCCESS_REPORT.getCode()==reportStatus){
                        status= RecordStatus.SEND_SUCCESS.getCode();
                    }else if(ReportStatus.ERROR_REPORT.getCode()==reportStatus){
                        status=RecordStatus.SEND_FAIL.getCode();
                    }
                    //更新时间 now
                    Timestamp updateTime=Timestamp.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
                    smsMessageRecordMapper.updateStatusByRecordId(recordId,status,startTime,endTime,updateTime);
                } catch (SQLException e) {
                    logger.error("通过recordId的更新record状态异常,recordId:"+recordId,e);
                }


                //根据recordId,查询messageId
                try {
                    if(StringUtils.isEmpty(messageId)){
                        SMSMessageRecordDTO smsMessageRecordDTO;
                        smsMessageRecordDTO = smsMessageRecordMapper.getRecordByRecordId(recordId,startTime,endTime);
                        if(null!=smsMessageRecordDTO){
                            tempMsgId=smsMessageRecordDTO.getMessageId();
                            //入参sendTime为空的话,sendTime赋值
                            if(null==sendTime){
                                tempSdTime=smsMessageRecordDTO.getSendTime();
                            }
                        }
                    }
                } catch (SQLException e) {
                    logger.error("通过recordId的查询record信息异常,recordId:"+recordId,e);
                }


                //更新消息的发送状态
                if(StringUtils.isNotEmpty(tempMsgId)){

                    ChangeMessageStatusRequest changeMessageStatusRequest=new ChangeMessageStatusRequest();
                    changeMessageStatusRequest.setMessageId(tempMsgId);
                    //前3天
                    changeMessageStatusRequest.setStartTime(start+"");
                    changeMessageStatusRequest.setEndTime(end+"");
                    if(ReportStatus.SUCCESS_REPORT.getCode()==reportStatus){
                        changeMessageStatusRequest.setMessageStatus(MessageStatus.SENDSUCCESS);
                    }else if(ReportStatus.ERROR_REPORT.getCode()==reportStatus){
                        changeMessageStatusRequest.setMessageStatus(MessageStatus.SENDFAIL);
                    }
                    ChangeMessageStatueResponse changeMessageStatueResponse=messageService.changeMessageStatue(changeMessageStatusRequest);
                    if(InvokeResult.SUCCESS.getCode()!=changeMessageStatueResponse.getResultCode()){
                        logger.error("根据MessageId更新消息状态异常,messageId:"+tempMsgId+" : "+changeMessageStatueResponse.getResultMessage());
                    }
                }

            }

            if(null!=tempSdTime && null!=reportTime){
                //记录供应商执行耗时
                long gap=reportTime.getTime()-tempSdTime.getTime();
                //sendTime是记录到毫秒,服务商返回的reportTime只到秒,同一秒内,就存在负值,默认值为1s
                Cat.logMetricForDuration(String.format("触达耗时,服务商:%s-%s：",providerId,providerName),
                        gap>0?(int)((double)gap/1000):1);
            }

            Cat.logMetricForCount(String.format("拉取回执条数,服务商:%s-%s",providerId,providerName));

            dto.setMessageId(tempMsgId);
            dto.setProviderId(providerId);
            dto.setRecipient(recipient);
            dto.setReportResult(reportResult);
            dto.setReportStatus(reportStatus);
            dto.setReportTime(reportTime);
            dto.setProviderMessage(providerMessage);
            Timestamp nowTimeStamp=Timestamp.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
            dto.setInsertTime(nowTimeStamp);
            dto.setUpdateTime(nowTimeStamp);

            try {
                logger.debug("---------save messageReport,report:"+ JSONObject.toJSONString(dto));
                int cnt=smsMessageReportMapper.saveMessageReport(dto);
                logger.debug("---------save messageReport,return:"+ cnt);
                bizResult.setResultCode(0);
                bizResult.setResultObject(cnt);

                transaction.setStatus(Message.SUCCESS);
            } catch (SQLException e) {
                logger.error(" 保存回执信息异常,recordId:"+recordId,e);
                bizResult.setResultCode(-1);
                bizResult.setResultMessage("保存回执信息,异常"+e.getMessage());
            }
        } catch (Exception e) {
           Cat.logError(e);
        } finally {
            transaction.complete();
        }

        return bizResult;
    }
}
