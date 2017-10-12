package com.ppdai.ac.sms.contract.dao.mapper.smsbase;

import com.ppdai.ac.sms.contract.model.entity.CallerBusinessDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * Created by wangxiaomei02 on 2017/5/25.
 */
@Component
public interface CallerBusinessMapper {
    int saveCallerBusiness(CallerBusinessDTO callerBusinessDTO);

    CallerBusinessDTO findCallerBusinessByParam(@Param("callerId")Integer callerId,@Param("businessId")Integer businessId);
}
