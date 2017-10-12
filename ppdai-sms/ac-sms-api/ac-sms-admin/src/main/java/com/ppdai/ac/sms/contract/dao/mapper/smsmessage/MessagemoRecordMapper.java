package com.ppdai.ac.sms.contract.dao.mapper.smsmessage;

import com.ppdai.ac.sms.contract.model.vo.MessageUpRecordVo;
import com.ppdai.ac.sms.contract.request.messagerecord.MessageRecordRequest;
import com.ppdai.ac.sms.contract.utils.Page;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by wangxiaomei02 on 2017/6/7.
 */
public interface MessagemoRecordMapper {
    List<MessageUpRecordVo> getMessageMoRecordList( @Param(value = "beginTime") Timestamp beginTime,
                                                    @Param(value = "endTime") Timestamp endTime,
                                                    @Param(value = "mobile") String mobile,
                                                    @Param(value = "providerId") Integer providerId,
                                                    @Param(value = "page") Page page);

    Integer  getMessageMoRecordCount(@Param(value = "beginTime") Timestamp beginTime,
                                     @Param(value = "endTime") Timestamp endTime,
                                     @Param(value = "mobile") String mobile,
                                     @Param(value = "providerId") Integer providerId);

}
