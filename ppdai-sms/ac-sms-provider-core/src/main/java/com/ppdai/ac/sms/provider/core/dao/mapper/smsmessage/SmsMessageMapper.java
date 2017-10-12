package com.ppdai.ac.sms.provider.core.dao.mapper.smsmessage;

import com.ppdai.ac.sms.provider.core.model.entity.SMSMessageDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * author cash
 * create 2017-05-12-15:05
 **/

@Component
public interface SmsMessageMapper {

   int  updateStatusByMessageId(@Param("messageId") String messageId, @Param("status") int status) throws SQLException;


   SMSMessageDTO getMessageByMessageId(@Param("messageId") String messageId,
                                       @Param("startTime") Timestamp startTime,
                                       @Param("endTime") Timestamp endTime) throws SQLException;
}
