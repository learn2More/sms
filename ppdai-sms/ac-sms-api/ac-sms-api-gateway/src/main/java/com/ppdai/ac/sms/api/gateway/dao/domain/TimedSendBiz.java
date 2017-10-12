package com.ppdai.ac.sms.api.gateway.dao.domain;

import com.google.common.collect.Lists;
import com.ppdai.ac.sms.api.gateway.configuration.GatewayProperties;
import com.ppdai.ac.sms.api.gateway.dao.mapper.smsbase.TimedSendMapper;
import com.ppdai.ac.sms.api.gateway.enums.InvokeResult;
import com.ppdai.ac.sms.api.gateway.enums.WhetherEnum;
import com.ppdai.ac.sms.api.gateway.model.bo.BizResult;
import com.ppdai.ac.sms.api.gateway.model.entity.TimedSendDTO;
import com.ppdai.ac.sms.api.gateway.request.TimedSendQuery;
import com.ppdai.ac.sms.api.gateway.request.TimedSendUpdate;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

/**
 * author cash
 * create 2017-08-01-15:57
 **/
@Component
public class TimedSendBiz {
    private static final Logger logger = LoggerFactory.getLogger(TimedSendBiz.class);

    @Autowired
    TimedSendMapper timedSendMapper;

    @Autowired
    GatewayProperties gatewayProperties;


    @Autowired
    MessageBiz MessageBiz;

    public void timedSend(){
        TimedSendQuery request=new TimedSendQuery();
        LocalDateTime now=LocalDateTime.now();
        //定时发送 1d
        LocalDateTime yesterday=now.minusDays(1L);
        request.setInsertTime(Timestamp.from(yesterday.atZone(ZoneId.systemDefault()).toInstant()));
        request.setIsSend(WhetherEnum.No.getCode());
        request.setTiming(Timestamp.from(now.atZone(ZoneId.systemDefault()).toInstant()));
        request.setLimitNum(gatewayProperties.getBatchSendLimit());
        try {
            List<TimedSendDTO> timedSendDTOList=timedSendMapper.getTimedSendList(request);
            if(CollectionUtils.isNotEmpty(timedSendDTOList)){
                List<TimedSendUpdate> timedSendUpdateList=Lists.newArrayList();
                for(TimedSendDTO timedSendDTO:timedSendDTOList){
                    TimedSendUpdate timedSendUpdate=new TimedSendUpdate();
                    timedSendUpdate.setTimedSendId(timedSendDTO.getTimedSendId());
                    BizResult bizResult=MessageBiz.sendForBatchSend(timedSendDTO);
                    if(bizResult.getResultCode() == InvokeResult.SUCCESS.getCode()){
                        timedSendUpdate.setRemark(String.format("执行成功,code:%s",bizResult.getResultCode()));
                    }else{
                        timedSendUpdate.setRemark(String.format("执行失败,code:%s,message:%s",bizResult.getResultCode(),bizResult.getResultMessage()));
                    }
                    timedSendUpdate.setIsSend(WhetherEnum.YES.getCode());
                    timedSendUpdateList.add(timedSendUpdate);
                }
                if(CollectionUtils.isNotEmpty(timedSendUpdateList)){
                    timedSendMapper.BatchUpdateSendResult(timedSendUpdateList);
                }
            }else{
                logger.info("timed send,get the list of record that prepare to send is null");
            }

        } catch (SQLException e) {
            logger.error("timed send sql error",e);
        }
    }

}
