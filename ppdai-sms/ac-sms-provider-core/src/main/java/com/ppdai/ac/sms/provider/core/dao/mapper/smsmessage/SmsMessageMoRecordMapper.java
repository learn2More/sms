package com.ppdai.ac.sms.provider.core.dao.mapper.smsmessage;

import com.ppdai.ac.sms.provider.core.model.entity.SMSMessageMoRecordDTO;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

/**
 * 上行记录
 * author cash
 * create 2017-05-09-11:08
 **/
@Component
public interface SmsMessageMoRecordMapper {
    int saveMoRecord(SMSMessageMoRecordDTO dto) throws SQLException;
}
