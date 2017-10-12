package com.ppdai.ac.sms.api.gateway.dao.mapper.smsbase;

import com.ppdai.ac.sms.api.gateway.model.entity.TimedSendDTO;
import com.ppdai.ac.sms.api.gateway.request.TimedSendQuery;
import com.ppdai.ac.sms.api.gateway.request.TimedSendSave;
import com.ppdai.ac.sms.api.gateway.request.TimedSendUpdate;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

/**
 * 定时发送
 * author cash
 * create 2017-07-31-20:11
 **/
@Component
public interface TimedSendMapper {
    List<TimedSendDTO> getTimedSendList(TimedSendQuery request) throws SQLException;

    int batchSaveTimedSend(TimedSendSave timedSendSave)throws SQLException;

    int updateIsSendByIds(@Param("isSend") Integer isSend, @Param("list") List<Long> listId) throws SQLException;

    int BatchUpdateSendResult(List<TimedSendUpdate> list)throws SQLException;

}
