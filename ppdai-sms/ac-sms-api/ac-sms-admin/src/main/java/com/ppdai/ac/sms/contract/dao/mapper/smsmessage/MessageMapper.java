package com.ppdai.ac.sms.contract.dao.mapper.smsmessage;

import com.ppdai.ac.sms.contract.model.vo.MessageBusinessVo;
import com.ppdai.ac.sms.contract.model.vo.MessageVo;
import com.ppdai.ac.sms.contract.request.messagerecord.MessageRecordRequest;
import com.ppdai.ac.sms.contract.utils.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by wangxiaomei02 on 2017/6/20.
 */
@Component
public interface MessageMapper {

    List<MessageVo> getInterceptRecordList(MessageRecordRequest recordRequest);

    int getInterceptRecordCount(MessageRecordRequest recordRequest);

    List<MessageVo> getMessageListByBusinessId(@Param(value = "list")List<MessageBusinessVo> recordRequest,
                                               @Param(value = "beginTime") Timestamp beginTime,
                                               @Param(value = "endTime") Timestamp endTime,
                                               @Param(value = "mobile") String mobile,
                                               @Param(value = "status") Integer status,
                                               @Param(value = "callerId") Integer callerId,
                                               @Param(value = "businessId") Integer businessId,
                                               @Param(value = "page") Page page);

    int getMessageCount(@Param(value = "list")List<MessageBusinessVo> recordRequest,
                        @Param(value = "beginTime") Timestamp beginTime,
                        @Param(value = "endTime") Timestamp endTime,
                        @Param(value = "mobile") String mobile,
                        @Param(value = "status") Integer status,
                        @Param(value = "callerId") Integer callerId,
                        @Param(value = "businessId") Integer businessId);

}
