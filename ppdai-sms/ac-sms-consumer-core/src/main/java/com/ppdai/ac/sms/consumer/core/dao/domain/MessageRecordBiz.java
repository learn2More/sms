package com.ppdai.ac.sms.consumer.core.dao.domain;

import com.ppdai.ac.sms.consumer.core.dao.mapper.smsmessage.SMSMessageRecordMapper;
import com.ppdai.ac.sms.consumer.core.enums.RecordStatus;
import com.ppdai.ac.sms.consumer.core.model.entity.SMSMessageRecordDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Created by kiekiyang on 2017/5/8.
 */
@Component
public class MessageRecordBiz {
    private static final Logger logger = LoggerFactory.getLogger(MessageRecordBiz.class);
    @Autowired
    SMSMessageRecordMapper smsMessageRecordMapper;

    public SMSMessageRecordDTO findRecordById(String recordId, LocalDateTime startTime, LocalDateTime endTime) {
        Timestamp start = Timestamp.from(startTime.atZone(ZoneId.systemDefault()).toInstant());
        Timestamp end = Timestamp.from(endTime.atZone(ZoneId.systemDefault()).toInstant());
        return smsMessageRecordMapper.findByRecordId(recordId, start, end);
    }

    /**
     * 添加操作记录
     *
     * @param messageId
     * @param providerId
     * @param messageCount
     * @param receiveTime
     * @param sendTime
     * @return
     */
    public boolean messageRecord(String recordId, String messageId, int providerId, int messageCount, LocalDateTime receiveTime, LocalDateTime sendTime) {
        try {
            SMSMessageRecordDTO smsMessageRecordDTO = new SMSMessageRecordDTO();
            smsMessageRecordDTO.setRecordId(recordId);
            smsMessageRecordDTO.setMessageId(messageId);
            smsMessageRecordDTO.setProviderId(providerId);
            smsMessageRecordDTO.setMessageCount(messageCount);
            smsMessageRecordDTO.setStatus(RecordStatus.INIT.getCode());
            //smsMessageRecordDTO.setSendResult(sendResult);
            smsMessageRecordDTO.setReceiveTime(Timestamp.from(receiveTime.atZone(ZoneId.systemDefault()).toInstant()));
            smsMessageRecordDTO.setSendTime(Timestamp.from(sendTime.atZone(ZoneId.systemDefault()).toInstant()));
            //smsMessageRecordDTO.setReponseTime(Timestamp.from(responseTime.atZone(ZoneId.systemDefault()).toInstant()));
            smsMessageRecordDTO.setInsertTime(Timestamp.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
            smsMessageRecordDTO.setUpdateTime(Timestamp.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
            if (smsMessageRecordMapper.save(smsMessageRecordDTO) > 0)
                return true;
            else {
                logger.warn(String.format("添加消息记录失败,MessageId: %s", messageId));
                return false;
            }
        } catch (Exception ex) {
            logger.error(String.format("添加消息记录异常,MessageId: %s", messageId), ex);
        }
        return false;
    }

    /**
     * 查询消息发送次数
     *
     * @param messageId
     * @param startTime
     * @param endTime
     * @return
     */
    public int querySendRecordCnt(String messageId, LocalDateTime startTime, LocalDateTime endTime) {
        Timestamp start = Timestamp.from(startTime.atZone(ZoneId.systemDefault()).toInstant());
        Timestamp end = Timestamp.from(endTime.atZone(ZoneId.systemDefault()).toInstant());
        return smsMessageRecordMapper.querySendRecord(messageId, start, end).size();
    }


    /**
     * 修改操作记录
     *
     * @param recordId
     * @param recordStatus
     * @param sendResult
     * @param responseTime
     * @return
     */
    public boolean updateRecord(String recordId, RecordStatus recordStatus, String sendResult, LocalDateTime responseTime, LocalDateTime start, LocalDateTime end) {
        try {
            SMSMessageRecordDTO smsMessageRecordDTO = new SMSMessageRecordDTO();
            smsMessageRecordDTO.setRecordId(recordId);
            smsMessageRecordDTO.setStatus(recordStatus.getCode());
            smsMessageRecordDTO.setSendResult(sendResult);
            smsMessageRecordDTO.setUpdateTime(Timestamp.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
            smsMessageRecordDTO.setResponseTime(Timestamp.from(responseTime.atZone(ZoneId.systemDefault()).toInstant()));
            Timestamp startTime = Timestamp.from(start.atZone(ZoneId.systemDefault()).toInstant());
            Timestamp endTime = Timestamp.from(end.atZone(ZoneId.systemDefault()).toInstant());
            if (smsMessageRecordMapper.updateRecord(smsMessageRecordDTO, startTime, endTime) > 0)
                return true;
            else {
                logger.warn(String.format("修改消息记录失败,RecordId: %s", recordId));
                return false;
            }
        } catch (Exception ex) {
            logger.error(String.format("修改消息记录异常,RecordId: %s", recordId), ex);
        }
        return false;
    }
}
