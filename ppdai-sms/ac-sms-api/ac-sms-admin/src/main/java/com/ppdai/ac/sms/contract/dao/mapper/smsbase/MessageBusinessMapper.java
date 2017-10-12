package com.ppdai.ac.sms.contract.dao.mapper.smsbase;

import com.ppdai.ac.sms.contract.model.entity.MessageBusinessDTO;
import com.ppdai.ac.sms.contract.model.vo.MessageBusinessVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by wangxiaomei02 on 2017/5/25.
 */
@Component
public interface MessageBusinessMapper {
    List<MessageBusinessVo> getMessageBusinessList();

    int saveMessageBusiness(MessageBusinessDTO dto);

    int editMessageBusiness(MessageBusinessDTO dto);

    int delMessageBusiness(@Param("businessId")Integer bId);

    MessageBusinessVo findMessageBusinessById(@Param("businessId")Integer mbId);

    List<MessageBusinessVo> getBusinessListByMessageType(@Param("messageType")Integer messageType);
}
