package com.ppdai.ac.sms.provider.core.dao.mapper.smsmessage;

import com.ppdai.ac.sms.provider.core.model.entity.SMSMessageReportDTO;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

/**
 * 回执报告
 * author cash
 * create 2017-05-09-11:09
 **/

@Component
public interface SmsMessageReportMapper {
    int saveMessageReport(SMSMessageReportDTO dto) throws SQLException;
}
