package com.ppdai.ac.sms.contract.dao.mapper.smsbase;

import com.ppdai.ac.sms.contract.model.vo.ProviderVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by wangxiaomei02 on 2017/6/2.
 * 服务商维护
 */
@Component
public interface ProviderMapper {

    int saveProvider(ProviderVo providerVo);

    List<ProviderVo> getProviders(@Param("messageKind")Integer messageKind,@Param("providerBusiness")Integer providerBusiness);

    int editProvider(ProviderVo providerVo);

    int delProvider(@Param("providerId")int providerId);

    int getProviderCount(@Param("messageKind")Integer messageKind,@Param("providerBusiness")Integer providerBusiness);

    List<ProviderVo> getProviderList();
}
