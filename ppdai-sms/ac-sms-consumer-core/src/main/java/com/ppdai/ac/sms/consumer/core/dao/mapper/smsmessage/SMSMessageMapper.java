package com.ppdai.ac.sms.consumer.core.dao.mapper.smsmessage;

import com.ppdai.ac.sms.consumer.core.model.entity.SMSMessageDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by kiekiyang on 2017/5/4.
 */
@Component
public interface SMSMessageMapper {
    SMSMessageDTO findMessageById(@Param("messageId") String messageId, @Param("beginTime") Timestamp beginTime, @Param("endTime") Timestamp endTime);

    List<SMSMessageDTO> findMessageBySstatus(@Param("contentType") int contentType,@Param("status") int status, @Param("beginTime") Timestamp beginTime, @Param("endTime") Timestamp endTime);

    int updateMessageStatus(@Param("messageId") String messageId, @Param("status") int status, @Param("beginTime") Timestamp beginTime, @Param("endTime") Timestamp endTime);
}
