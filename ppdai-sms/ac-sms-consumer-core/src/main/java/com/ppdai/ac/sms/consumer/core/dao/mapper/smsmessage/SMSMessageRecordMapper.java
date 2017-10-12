package com.ppdai.ac.sms.consumer.core.dao.mapper.smsmessage;

import com.ppdai.ac.sms.consumer.core.model.entity.SMSMessageRecordDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by kiekiyang on 2017/5/8.
 */
@Component
public interface SMSMessageRecordMapper {
    int save(SMSMessageRecordDTO smsMessageRecordDTO);

    SMSMessageRecordDTO findByRecordId(@Param("recordId") String recordId, @Param("startTime") Timestamp startTime, @Param("endTime") Timestamp endTime);

    int updateRecord(@Param("smsMessageRecordDTO") SMSMessageRecordDTO smsMessageRecordDTO, @Param("startTime") Timestamp startTime, @Param("endTime") Timestamp endTime);

    int querySendRecordCnt(@Param("messageId") String messageId, @Param("startTime") Timestamp startTime, @Param("endTime") Timestamp endTime);

    List<SMSMessageRecordDTO> querySendRecord(@Param("messageId") String messageId, @Param("startTime") Timestamp startTime, @Param("endTime") Timestamp endTime);
}
