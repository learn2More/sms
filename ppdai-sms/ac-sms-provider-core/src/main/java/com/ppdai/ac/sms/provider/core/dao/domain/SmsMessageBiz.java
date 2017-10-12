package com.ppdai.ac.sms.provider.core.dao.domain;

import com.ppdai.ac.sms.provider.core.dao.mapper.smsmessage.SmsMessageMapper;
import com.ppdai.ac.sms.provider.core.model.entity.SMSMessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * SmsMessage
 * author cash
 * create 2017-05-12-14:54
 **/

@Component
public class SmsMessageBiz {

    private static final Logger logger= LoggerFactory.getLogger(SmsMessageBiz.class);

    @Autowired
    SmsMessageMapper smsMessageMapper;

    public int updateStatusByMessageId(String messageId,int status){
        int cnt=0;
        try {
            cnt=smsMessageMapper.updateStatusByMessageId(messageId,status);
        } catch (SQLException e) {
            logger.error("根据MessageId更新message状态异常,messageId:"+messageId,e);
        }
        return cnt;
    }

    //String messageId, LocalDateTime start, LocalDateTime end
    public SMSMessageDTO getMessageByMessageId(String messageId, LocalDateTime start, LocalDateTime end){

        SMSMessageDTO smsMessageDTO=new SMSMessageDTO();
        try {
            Timestamp startTime=Timestamp.from(start.atZone(ZoneId.systemDefault()).toInstant());
            Timestamp endTime=Timestamp.from(end.atZone(ZoneId.systemDefault()).toInstant());
            smsMessageDTO=smsMessageMapper.getMessageByMessageId(messageId,startTime,endTime);
        } catch (SQLException e) {
            logger.error("根据MessageId查询message信息异常,messageId:"+messageId,e);
        }

        return smsMessageDTO;
    }

}
