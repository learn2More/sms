package com.ppdai.ac.sms.consumer.core.dao.mapper.smsbase;

import com.ppdai.ac.sms.consumer.core.model.entity.MessageBusinessDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * Created by kiekiyang on 2017/4/25.
 */
@Component
public interface MessageBusinessMapper {
    MessageBusinessDTO getBussinessByBussinessId(@Param("bussinessId") int bussinessId);
}
