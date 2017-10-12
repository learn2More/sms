package com.ppdai.ac.sms.provider.core.dao.mapper.smsmessage;

import com.ppdai.ac.sms.provider.core.model.entity.SMSMessageRecordDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

/**
 * SmsMessageRecord
 * author cash
 * create 2017-05-12-9:38
 **/
@Component
public interface SmsMessageRecordMapper {

     int updateRecordExtByRecordId(@Param("recordId") String recordId,
                                   @Param("recordExt") String recordExt,
                                   @Param("startTime") Timestamp startTime,
                                   @Param("endTime") Timestamp endTime,
                                   @Param("updateTime") Timestamp updateTime) throws SQLException;


     int updateStatusByRecordExt(@Param("recordExt") String recordExt,
                                 @Param("status") int status,
                                 @Param("startTime") Timestamp startTime,
                                 @Param("endTime") Timestamp endTime,
                                 @Param("updateTime") Timestamp updateTime) throws SQLException;

     int updateStatusByRecordId(@Param("recordId") String recordId,
                                 @Param("status") int status,
                                 @Param("startTime") Timestamp startTime,
                                 @Param("endTime") Timestamp endTime,
                                 @Param("updateTime") Timestamp updateTime) throws SQLException;


     SMSMessageRecordDTO getRecordByRecordId(@Param("recordId") String recordId,
                                             @Param("startTime") Timestamp startTime,
                                             @Param("endTime") Timestamp endTime) throws SQLException;

     SMSMessageRecordDTO getRecordByRecordExt(@Param("recordExt") String recordExt,
                                              @Param("startTime") Timestamp startTime,
                                              @Param("endTime") Timestamp endTime) throws SQLException;

     List<SMSMessageRecordDTO> getRecordBeforeSometime(SMSMessageRecordDTO dto) throws SQLException;


     List<SMSMessageRecordDTO> getNeedToSolvedRecord(@Param("providerId") long providerId,
                                                     @Param("startTime") Timestamp startTime,
                                                     @Param("endTime") Timestamp endTime) throws SQLException;
}
