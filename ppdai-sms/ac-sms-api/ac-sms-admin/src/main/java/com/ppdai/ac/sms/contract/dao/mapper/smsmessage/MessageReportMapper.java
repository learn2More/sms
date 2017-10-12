package com.ppdai.ac.sms.contract.dao.mapper.smsmessage;

import com.ppdai.ac.sms.contract.model.vo.MessageReportVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by wangxiaomei02 on 2017/6/20.
 */
@Component
public interface MessageReportMapper {
    MessageReportVo getMessageReportByRecordId(@Param(value = "messageId") String messageId, @Param(value = "beginTime") Timestamp insertTime, @Param(value = "endTime") Timestamp endTime);

    Timestamp getReportTimeByRecordId(@Param(value = "recordId") String recordId, @Param(value = "beginTime") Timestamp insertTime, @Param(value = "endTime")Timestamp endTime);
}
