package com.ppdai.ac.sms.contract.dao.mapper.smsbase;

import com.ppdai.ac.sms.contract.model.vo.CallerVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by wangxiaomei02 on 2017/5/25.
 */
@Component
public interface CallerMapper {
    List<CallerVo> getCallerList();

    int saveCaller(CallerVo vo);

    int editCaller(@Param(value = "callerName") String callerName,
                   @Param("ipList")String ipList,
                   @Param("callerId")Integer callerId);

    int delCaller(@Param("callerId")Integer callerId);

    CallerVo findCallerById(@Param("callerId")Integer callerId);
}
