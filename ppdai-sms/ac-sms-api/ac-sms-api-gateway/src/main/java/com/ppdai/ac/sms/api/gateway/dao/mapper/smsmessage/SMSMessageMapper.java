package com.ppdai.ac.sms.api.gateway.dao.mapper.smsmessage;

import com.ppdai.ac.sms.api.gateway.model.entity.SMSMessageDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Created by kiekiyang on 2017/4/25.
 */
@Component
public interface SMSMessageMapper {
    int save(SMSMessageDTO smsMessageDTO) throws SQLException;

    int changeMessageStatus(@Param("messageId") String messageId, @Param("status") int status, @Param("startTime") Timestamp startTime, @Param("endTime") Timestamp endTime);

    SMSMessageDTO findById(@Param("messageId") String messageId, @Param("startTime") Timestamp startTime, @Param("endTime") Timestamp endTime);
}
