package com.ppdai.ac.sms.api.gateway.dao.mapper.securitycode;

import com.ppdai.ac.sms.api.gateway.model.entity.SecurityCodeDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * Created by kiekiyang on 2017/4/28.
 */
@Component
public interface SecurityCodeMapper {
    int createSecurityCode(SecurityCodeDTO securityCode);

    SecurityCodeDTO getSecurityCodeWithLastOne(@Param("appId") int appId, @Param("codeKey") String codeKey);

    int delSecurityCode(@Param("appId") int appId,@Param("codeKey") String codeKey,@Param("codeValue") String codeValue);
}
