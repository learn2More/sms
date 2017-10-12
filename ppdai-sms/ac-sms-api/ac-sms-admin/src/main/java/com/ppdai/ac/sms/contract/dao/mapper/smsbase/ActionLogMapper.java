package com.ppdai.ac.sms.contract.dao.mapper.smsbase;

import com.ppdai.ac.sms.contract.model.entity.ActionLogDTO;
import com.ppdai.ac.sms.contract.utils.Page;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by wangxiaomei02 on 2017/6/27.
 */
public interface ActionLogMapper {
    int saveActionlog(ActionLogDTO dto);

    List<ActionLogDTO> getActionLogList(@Param(value = "userId") Integer userId,
                                        @Param(value = "beginTime") Timestamp beginTime,
                                        @Param(value = "endTime") Timestamp endTime,
                                        @Param("page")Page page);

    int getActionLogCount(@Param(value = "userId") Integer userId,
                          @Param(value = "beginTime") Timestamp beginTime,
                          @Param(value = "endTime") Timestamp endTime);
}
