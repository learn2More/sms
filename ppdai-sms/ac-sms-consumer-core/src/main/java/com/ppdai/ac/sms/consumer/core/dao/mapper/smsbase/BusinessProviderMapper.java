package com.ppdai.ac.sms.consumer.core.dao.mapper.smsbase;

import com.ppdai.ac.sms.consumer.core.model.entity.BusinessProviderDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by kiekiyang on 2017/5/4.
 */
@Component
public interface BusinessProviderMapper {
    List<BusinessProviderDTO> findBusinessProviderByBusinessId(@Param("businessId") int businessId);

    List<BusinessProviderDTO> findBusinessProviderByBusinessIdAndMessageKind(@Param("businessId") int businessId,@Param("messageKind") int messageKind);
}
