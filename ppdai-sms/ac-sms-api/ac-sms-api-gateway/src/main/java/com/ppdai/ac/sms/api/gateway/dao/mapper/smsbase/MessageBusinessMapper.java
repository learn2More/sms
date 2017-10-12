package com.ppdai.ac.sms.api.gateway.dao.mapper.smsbase;

import com.ppdai.ac.sms.api.gateway.model.entity.MessageBusinessDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * Created by kiekiyang on 2017/4/25.
 */
@Component
public interface MessageBusinessMapper {
    MessageBusinessDTO getBussinessByAlias(@Param("businessAlias") String businessAlias);

    MessageBusinessDTO getBussinessByAliasAndMessageType(@Param("businessAlias") String businessAlias,@Param("messageType") int  messageType);
}
