package com.ppdai.ac.sms.provider.core.dao.domain;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Message;
import com.dianping.cat.message.Transaction;
import com.ppdai.ac.sms.provider.core.configuration.CommonProperties;
import com.ppdai.ac.sms.provider.core.dao.mapper.smsmessage.SmsMessageMoRecordMapper;
import com.ppdai.ac.sms.provider.core.enums.InvokeResult;
import com.ppdai.ac.sms.provider.core.model.bo.BizResult;
import com.ppdai.ac.sms.provider.core.model.entity.SMSMessageMoRecordDTO;
import com.ppdai.ac.sms.provider.core.protocol.BlackListService;
import com.ppdai.ac.sms.provider.core.protocol.request.BlackList;
import com.ppdai.ac.sms.provider.core.protocol.request.ImportBlackListRequest;
import com.ppdai.ac.sms.provider.core.protocol.response.ImportBlackListResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * author cash
 * create 2017-05-09-19:09
 **/

@Service
public class SmsMessageMoRecordBiz {

    private static final Logger logger = LoggerFactory.getLogger(SmsMessageMoRecordBiz.class);

/*    @Autowired
    BlackListBiz blackListBiz;*/

    @Autowired
    CommonProperties commonProperties;

    @Autowired(required = false)
    BlackListService blackListService;


    @Autowired
    SmsMessageMoRecordMapper smsMessageMoRecordMapper;


    public BizResult saveMoRecord(String channelNo, String providerName,String sender, String content, String extendNo, Timestamp reportTime) {
        Transaction transaction= Cat.newTransaction("getMoReport",String.format("%s",channelNo)+" : "+sender);

        BizResult bizResult = new BizResult();

        try {

            Cat.logMetricForCount(String.format("拉取上行条数,服务商:%s-%s",channelNo,providerName));

            SMSMessageMoRecordDTO smsMessageMoRecordDTO = new SMSMessageMoRecordDTO();
            smsMessageMoRecordDTO.setChannelNo(channelNo);
            smsMessageMoRecordDTO.setSender(sender);
            smsMessageMoRecordDTO.setContent(content);
            smsMessageMoRecordDTO.setExtendNo(extendNo);
            smsMessageMoRecordDTO.setReportTime(reportTime);

            Timestamp nowTimeStamp=Timestamp.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
            smsMessageMoRecordDTO.setInsertTime(nowTimeStamp);
            smsMessageMoRecordDTO.setUpdateTime(nowTimeStamp);
            try {
                int cnt = smsMessageMoRecordMapper.saveMoRecord(smsMessageMoRecordDTO);
                if (cnt > 0) {
                    Long id = smsMessageMoRecordDTO.getMoRecordId();
                    //加入黑名单
                    if (StringUtils.isNotEmpty(content)) {
                        String unsubscribeMarker=commonProperties.getUnsubscribeMarker();
                        if(StringUtils.isNotEmpty(unsubscribeMarker)){
                            for(String s:unsubscribeMarker.split(",")){
                                if (0 ==s.compareToIgnoreCase(content)) {
                                    ImportBlackListRequest importBlackListRequest=new ImportBlackListRequest();
                                    List<BlackList> listBlackList=new ArrayList<BlackList>();
                                    BlackList blackList=new BlackList();
                                    blackList.setFeatures(sender);
                                    blackList.setRemark("退订编号: "+id+" 自动退订");
                                    listBlackList.add(blackList);
                                    importBlackListRequest.setBlackLists(listBlackList);
                                    ImportBlackListResponse importBlackListResponse=blackListService.importBlackList(importBlackListRequest);
                                    if(InvokeResult.SUCCESS.getCode()!=importBlackListResponse.getResultCode()){
                                        logger.error("上行id： "+id+" 加入黑名单失败:"+importBlackListResponse.getResultMessage());
                                    }
                                    break;
                                }

                            }
                        }

                    }
                    bizResult.setResultCode(0);
                    bizResult.setResultMessage("");
                    bizResult.setResultObject(id);
                } else {
                    logger.error(String.format("保存上行报告异常,channelNo:%s,sender:%s",channelNo,sender));
                    bizResult.setResultCode(-1);
                    bizResult.setResultMessage(String.format("保存上行报告异常,channelNo:%s,sender:%s",channelNo,sender));
                }

            } catch (SQLException e) {
                logger.error(String.format("保存上行报告异常,channelNo:%s,sender:%s",channelNo,sender), e);
                bizResult.setResultCode(-1);
                bizResult.setResultMessage(String.format("保存上行报告异常,channelNo:%s,sender:%s",channelNo,sender));
            }

            transaction.setStatus(Message.SUCCESS);
        } catch (Exception e) {
            Cat.logError(e);
        } finally {
            transaction.complete();
        }
        return bizResult;
    }
}
