package com.ppdai.ac.sms.contract.dao.mapper.smsbase;

import com.ppdai.ac.sms.contract.model.entity.ProviderConfigDTO;
import com.ppdai.ac.sms.contract.model.vo.ProviderConfigVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by wangxiaomei02 on 2017/6/12.
 */
@Component
public interface ProviderConfigMapper {
    int batchSaveProviderConfig(@Param(value ="list") List<ProviderConfigDTO> list);

    int saveProviderConfig(ProviderConfigDTO dto);

    List<ProviderConfigVo> getConfigByProviderId(@Param("providerId")Integer providerId);

    int delProviderConfig(@Param("list") List<ProviderConfigVo> list);

    int editProviderConfig(ProviderConfigVo vo);

}
