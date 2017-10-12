package com.ppdai.ac.sms.contract.dao.mapper.smsbase;

import com.ppdai.ac.sms.contract.model.entity.BusinessProviderDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by wangxiaomei02 on 2017/6/14.
 */

@Component
public interface BusinessProviderMapper {

    int saveBusinessProvider(BusinessProviderDTO dto );

    List<BusinessProviderDTO> getBusinessProviderBybusinessId(@Param("businessId")Integer businessId);

    int editBusinessProvider(BusinessProviderDTO dto);

    int delBusinessProvider(@Param("channelId")Integer channelId);
}
