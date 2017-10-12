package com.ppdai.ac.sms.provider.core.dao.domain;

import com.alibaba.fastjson.JSONObject;
import com.ppdai.ac.sms.provider.core.dao.mapper.smsmessage.SmsMessageRecordMapper;
import com.ppdai.ac.sms.provider.core.model.entity.SMSMessageRecordDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * SmsMessageRecord
 * author cash
 * create 2017-05-11-18:57
 **/

@Component
public class SmsMessageRecordBiz {
    private static final Logger logger = LoggerFactory.getLogger(SmsMessageRecordBiz.class);

    @Autowired
    SmsMessageRecordMapper smsMessageRecordMapper;

    public int updateRecordExtByRecordId(String recordId,String recordExt,LocalDateTime start, LocalDateTime end){
        int cnt=0;
        try {
            Timestamp startTime=Timestamp.from(start.atZone(ZoneId.systemDefault()).toInstant());
            Timestamp endTime=Timestamp.from(end.atZone(ZoneId.systemDefault()).toInstant());

            //更新时间 now
            Timestamp updateTime=Timestamp.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
            cnt=smsMessageRecordMapper.updateRecordExtByRecordId(recordId,recordExt,startTime,endTime,updateTime);
        } catch (SQLException e) {
            logger.error("通过recoredId更新消息record异常,recordId:"+recordId,e);
        }
        return cnt;
    }


    public int updateStatusByRecordExt(String recordExt,int status,LocalDateTime start, LocalDateTime end){
        int cnt=0;
        try {
            Timestamp startTime=Timestamp.from(start.atZone(ZoneId.systemDefault()).toInstant());
            Timestamp endTime=Timestamp.from(end.atZone(ZoneId.systemDefault()).toInstant());

            Timestamp updateTime=Timestamp.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
            cnt=smsMessageRecordMapper.updateStatusByRecordExt(recordExt,status,startTime,endTime,updateTime);
        } catch (SQLException e) {
            logger.error("通过record的扩展信息更新消息record异常,recordExt:"+recordExt,e);
        }
        return cnt;
    }

    public int updateStatusByRecordId(String recordId,int status,LocalDateTime start, LocalDateTime end){
        int cnt=0;
        try {
            Timestamp startTime=Timestamp.from(start.atZone(ZoneId.systemDefault()).toInstant());
            Timestamp endTime=Timestamp.from(end.atZone(ZoneId.systemDefault()).toInstant());

            Timestamp updateTime=Timestamp.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
            cnt=smsMessageRecordMapper.updateStatusByRecordId(recordId,status,startTime,endTime,updateTime);
        } catch (SQLException e) {
            logger.error("通过record的扩展信息更新消息record异常,recordId:"+recordId,e);
        }
        return cnt;
    }


    public SMSMessageRecordDTO getRecordByRecordId(String recordId,LocalDateTime start, LocalDateTime end){
        SMSMessageRecordDTO smsMessageRecordDTO=null;
        try {
            Timestamp startTime=Timestamp.from(start.atZone(ZoneId.systemDefault()).toInstant());
            Timestamp endTime=Timestamp.from(end.atZone(ZoneId.systemDefault()).toInstant());
            smsMessageRecordDTO=smsMessageRecordMapper.getRecordByRecordId(recordId,startTime,endTime);
        } catch (SQLException e) {
            logger.error("通过recordId的查询record信息异常,recordId:"+recordId,e);
        }

        return smsMessageRecordDTO;
    }

    public SMSMessageRecordDTO getRecordByRecordExt(String recordExt,LocalDateTime start,LocalDateTime end){
        SMSMessageRecordDTO smsMessageRecordDTO=null;
        try {
            Timestamp startTime=Timestamp.from(start.atZone(ZoneId.systemDefault()).toInstant());
            Timestamp endTime=Timestamp.from(end.atZone(ZoneId.systemDefault()).toInstant());
            smsMessageRecordDTO=smsMessageRecordMapper.getRecordByRecordExt(recordExt,startTime,endTime);
        } catch (SQLException e) {
            logger.error("通过recordExt的查询record信息异常,recordExt:"+recordExt,e);
        }

        return smsMessageRecordDTO;
    }

    public List<SMSMessageRecordDTO> getRecordBeforeSometime(SMSMessageRecordDTO dto){
        List<SMSMessageRecordDTO> listDto =new ArrayList<>();
        try {
            listDto=smsMessageRecordMapper.getRecordBeforeSometime(dto);
        } catch (SQLException e) {
            logger.error("通过条件查询record信息异常,入参:"+ JSONObject.toJSONString(dto),e);
        }
        return listDto;
    }

    public List<SMSMessageRecordDTO> getNeedToSolvedRecord(String providerId,LocalDateTime start,LocalDateTime end){
        List<SMSMessageRecordDTO> listDto =new ArrayList<>();
        long lProviderId=Long.parseLong(providerId);
        Timestamp startTime=Timestamp.from(start.atZone(ZoneId.systemDefault()).toInstant());
        Timestamp endTime=Timestamp.from(end.atZone(ZoneId.systemDefault()).toInstant());
        try {
            listDto=smsMessageRecordMapper.getNeedToSolvedRecord(lProviderId,startTime,endTime);
        } catch (SQLException e) {
            logger.error("查询状态是需要处理的record异常",e);
        }
        return listDto;
    }
}
