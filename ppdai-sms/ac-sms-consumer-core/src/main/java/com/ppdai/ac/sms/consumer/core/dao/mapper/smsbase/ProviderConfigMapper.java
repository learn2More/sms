package com.ppdai.ac.sms.consumer.core.dao.mapper.smsbase;

import com.ppdai.ac.sms.consumer.core.model.entity.ProviderConfigDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by kiekiyang on 2017/5/4.
 */
@Component
public interface ProviderConfigMapper {
    List<ProviderConfigDTO> findProviderConfigByProviderId(@Param("providerId") int providerId);
}
