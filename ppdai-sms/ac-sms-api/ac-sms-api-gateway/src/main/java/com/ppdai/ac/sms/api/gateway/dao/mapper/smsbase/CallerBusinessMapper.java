package com.ppdai.ac.sms.api.gateway.dao.mapper.smsbase;

import com.ppdai.ac.sms.api.gateway.model.entity.CallerBusinessDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * Created by kiekiyang on 2017/4/25.
 */
@Component
public interface CallerBusinessMapper {
    CallerBusinessDTO getCallerBusinessRelation(@Param("callerId") int callerId, @Param("businessId") int businessId);
}
