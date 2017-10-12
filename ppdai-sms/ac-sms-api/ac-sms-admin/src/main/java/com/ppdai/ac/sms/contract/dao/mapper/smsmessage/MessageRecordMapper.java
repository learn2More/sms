package com.ppdai.ac.sms.contract.dao.mapper.smsmessage;

import com.ppdai.ac.sms.contract.model.vo.MessageVo;
import com.ppdai.ac.sms.contract.model.vo.MessageReportVo;
import com.ppdai.ac.sms.contract.utils.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by wangxiaomei02 on 2017/6/16.
 */
@Component
public interface MessageRecordMapper {

    List<MessageVo> getMessageRecordByparam(@Param(value = "list") List<MessageVo> recordRequest,
                                            @Param(value = "beginTime") Timestamp insertTime,
                                            @Param(value = "endTime") Timestamp endTime,
                                            @Param(value = "page") Page page);

    List<MessageVo> getMessageRecordCountByparam(@Param(value = "list") List<MessageVo> recordRequest,
                                                 @Param(value = "beginTime") Timestamp insertTime,
                                                 @Param(value = "endTime") Timestamp endTime);

    List<MessageReportVo> getMessageRecordByMessageId(@Param(value = "messageId") String messageId,
                                                       @Param(value = "beginTime") Timestamp insertTime,
                                                       @Param(value = "endTime") Timestamp endTime);



}
