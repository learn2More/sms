package com.ppdai.ac.sms.contract.service.messagerecord;

import com.ppdai.ac.sms.common.redis.RedisUtil;
import com.ppdai.ac.sms.contract.controller.messagerecord.MessageRecordService;
import com.ppdai.ac.sms.contract.dao.mapper.smsbase.MessageBusinessMapper;
import com.ppdai.ac.sms.contract.dao.mapper.smsmessage.MessageMapper;
import com.ppdai.ac.sms.contract.dao.mapper.smsmessage.MessageRecordMapper;
import com.ppdai.ac.sms.contract.dao.mapper.smsmessage.MessageReportMapper;
import com.ppdai.ac.sms.contract.dao.mapper.smsmessage.MessagemoRecordMapper;
import com.ppdai.ac.sms.contract.enums.InvokeResult;
import com.ppdai.ac.sms.contract.model.entity.MessageTemplateDTO;
import com.ppdai.ac.sms.contract.model.vo.*;
import com.ppdai.ac.sms.contract.response.MessageRecordResponse;
import com.ppdai.ac.sms.contract.response.MessageReportResponse;
import com.ppdai.ac.sms.contract.response.SmsMessageMoRecordResponse;
import com.ppdai.ac.sms.contract.utils.Page;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wangxiaomei02 on 2017/5/19.
 */
@RestController
public class MessageRecordServiceImpl implements MessageRecordService {

    private static final Logger logger = LoggerFactory.getLogger(MessageRecordServiceImpl.class);

    @Autowired
    MessageBusinessMapper messageBusinessMapper;

    @Autowired
    MessagemoRecordMapper messagemoRecordMapper;

    @Autowired
    MessageRecordMapper messageRecordMapper;

    @Autowired
    MessageReportMapper messageReportMapper;

    @Autowired
    MessageMapper messageMapper;

    @Autowired
    RedisUtil<String,MessageTemplateDTO> redisUtil_template;

    @Autowired
    RedisUtil<Integer,String> redisUtil_provider;

    @Override
    public MessageRecordResponse getMessageRecordList(@RequestParam(value = "mobile",required = false)String mobile,
                                                      @RequestParam(value ="status",required = false)Integer status,
                                                      @RequestParam("messageType")Integer messageType,
                                                      @RequestParam(value = "callerId",required = false)Integer callerId,
                                                      @RequestParam(value = "businessId",required = false)Integer businessId,
                                                      @RequestParam("beginTime")long beginTime,
                                                      @RequestParam("endTime")long endTime,
                                                      @RequestParam("pageNum")Integer pageNum,
                                                      @RequestParam("pageSize")Integer pageSize)  {
        MessageRecordResponse response=new MessageRecordResponse();
        logger.info("查询消息发送记录,入参：messageType="+messageType+",beginTime="+beginTime+"" +
                ",endTime="+endTime+",pageNum="+pageNum+",pageSize="+pageSize+",mobile="+mobile+"," +
                "status="+status+",callerId="+callerId+",businessId="+businessId );
        if(messageType==null || beginTime<=0 ||endTime<=0){
            response.setCode(InvokeResult.PARAM_ERROR.getCode());
            response.setMessage(InvokeResult.PARAM_ERROR.getMessage());
            return response;
        }
        try {
            response.setCode(InvokeResult.SELECT_SUCCESS.getCode());
            response.setMessage(InvokeResult.SELECT_SUCCESS.getMessage());
            response.setResult(new ArrayList<>());
            response.setTotal(0);
            /**
             * 根据消息类型查询业务集合
             */
            List<MessageBusinessVo> businessList=messageBusinessMapper.getBusinessListByMessageType(messageType);
            if(businessList != null && !businessList.isEmpty()) {
                /**
                 * 根据businessId查询message表消息记录
                 */

                Page page=new Page(pageNum,pageSize);
                List<MessageVo> messageList = messageMapper.getMessageListByBusinessId(businessList, new Timestamp(beginTime), new Timestamp(endTime), mobile, status,callerId,businessId,page);
                if (messageList != null && !messageList.isEmpty()) {
                    for(MessageVo vo:messageList){
                        MessageTemplateDTO dto=redisUtil_template.get("TEMPLATE:ID-"+vo.getTemplateId());//从缓存中根据模板id取模板名称
                        if(dto !=null)  vo.setTemplateName(dto.getTemplateName());
                    }
                        response.setCode(InvokeResult.SELECT_SUCCESS.getCode());
                        response.setMessage(InvokeResult.SELECT_SUCCESS.getMessage());
                        response.setTotal(0);
                        response.setResult(messageList);
                }
            }
        } catch (Exception e) {
            logger.error("查询消息发送异常，返回：", e);
            response.setCode(InvokeResult.SYS_ERROR.getCode());
            response.setMessage(InvokeResult.SYS_ERROR.getMessage());
        }
        return response;

    }
    @Override
    public SmsMessageMoRecordResponse getSmsMessageMoRecord(@RequestParam(value = "mobile",required = false)String mobile,
                                                            @RequestParam(value = "providerId",required = false) Integer providerId,
                                                            @RequestParam("beginTime")long beginTime,
                                                            @RequestParam("endTime")long endTime,
                                                            @RequestParam("pageNum")Integer pageNum,
                                                            @RequestParam("pageSize")Integer pageSize) {
        SmsMessageMoRecordResponse response=new SmsMessageMoRecordResponse();
        logger.info("获取短信上行记录列表,入参 :mobile="+mobile+",providerId="+providerId+",,beginTime="+beginTime+",endTime="+endTime+",pageNum="+pageNum+",pageSize="+pageSize);

        try {
            Page page=new Page(pageNum,pageSize);
            List<MessageUpRecordVo> messageList=messagemoRecordMapper.getMessageMoRecordList(new Timestamp(beginTime), new Timestamp(endTime),mobile,providerId,page);
            if(messageList !=null && !messageList.isEmpty()) {
                int total = messagemoRecordMapper.getMessageMoRecordCount(new Timestamp(beginTime), new Timestamp(endTime),mobile,providerId);
                response.setCode(InvokeResult.SELECT_SUCCESS.getCode());
                response.setMessage(InvokeResult.SELECT_SUCCESS.getMessage());
                response.setTotal(total);
                response.setResult(messageList);
            }
            else {
                response.setCode(InvokeResult.SELECT_SUCCESS.getCode());
                response.setMessage(InvokeResult.SELECT_SUCCESS.getMessage());
                response.setResult(new ArrayList<>());
                response.setTotal(0);
                logger.info("没有符合条件的数据");
            }
        }catch (Exception e){
            response.setCode(InvokeResult.SYS_ERROR.getCode());
            response.setMessage(InvokeResult.SYS_ERROR.getMessage());
            response.setResult("系统异常");
            logger.error("获取短信上行记录列表,异常,接口返回："+e);
        }
        return response;
    }

    @Override
    public MessageReportResponse getSmsMessageReport(@RequestParam("beginTime") Long beginTime, @RequestParam("endTime") Long endTime, @RequestParam("messageId") String messageId) {
        MessageReportResponse response=new MessageReportResponse();
        logger.info("获取回执消息记录列表,入参 :beginTime="+beginTime+",endTime="+endTime+",messageId="+messageId);
        if (beginTime <0 || endTime <0 || StringUtils.isBlank(messageId)){
            response.setCode(InvokeResult.PARAM_ERROR.getCode());
            response.setMessage(InvokeResult.PARAM_ERROR.getMessage());
            return response;
        }
        try {
            response.setCode(InvokeResult.SELECT_SUCCESS.getCode());
            response.setMessage(InvokeResult.SELECT_SUCCESS.getMessage());
            response.setResult(new ArrayList<>());

            //record集合数据
            List<MessageReportVo> recordList=messageRecordMapper.getMessageRecordByMessageId(messageId,new Timestamp(beginTime), new Timestamp(endTime));
            if(recordList !=null && !recordList.isEmpty()) {

                for(MessageReportVo recordVo:recordList) {
                    //查询report表 回执时间
                    MessageReportVo reportVo = messageReportMapper.getMessageReportByRecordId(recordVo.getMessageId(), new Timestamp(beginTime), new Timestamp(endTime));
                    if(reportVo != null){
                        recordVo.setReportTime(reportVo.getReportTime());
                    }
            }
            response.setCode(InvokeResult.SELECT_SUCCESS.getCode());
            response.setMessage(InvokeResult.SELECT_SUCCESS.getMessage());
            response.setResult(recordList);
        }else {
                response.setCode(InvokeResult.SELECT_SUCCESS.getCode());
                response.setMessage(InvokeResult.SELECT_SUCCESS.getMessage());
                response.setResult(new ArrayList<>());
                logger.info("没有符合条件的数据");
            }
        }catch (Exception e){
            response.setCode(InvokeResult.SYS_ERROR.getCode());
            response.setMessage(InvokeResult.SYS_ERROR.getMessage());
            response.setResult("系统异常");
            logger.error("获取回执消息记录列表,异常,接口返回："+e);
        }

        return response;
    }

    @Override
    public MessageRecordResponse getMessageSendTotal(@RequestParam(value = "mobile", required = false) String mobile, @RequestParam(value = "status", required = false) Integer status, @RequestParam("messageType") Integer messageType, @RequestParam(value = "callerId", required = false) Integer callerId, @RequestParam(value = "businessId", required = false) Integer businessId, @RequestParam("beginTime") long beginTime, @RequestParam("endTime") long endTime) {
        MessageRecordResponse response=new MessageRecordResponse();
        logger.info("查询消息发送总条数,入参：messageType="+messageType+",beginTime="+beginTime+"" +
                ",endTime="+endTime+",mobile="+mobile+"," +
                "status="+status+",callerId="+callerId+",businessId="+businessId );
        if(messageType<=0 || beginTime<=0 ||endTime<=0){
            response.setCode(InvokeResult.PARAM_ERROR.getCode());
            response.setMessage(InvokeResult.PARAM_ERROR.getMessage());
            return response;
        }
        try {
            List<MessageBusinessVo> businessList=messageBusinessMapper.getBusinessListByMessageType(messageType);
            if(businessList != null && !businessList.isEmpty()) {
                int total = messageMapper.getMessageCount(businessList, new Timestamp(beginTime), new Timestamp(endTime), mobile, status,callerId,businessId);
                if(total >0){
                    response.setCode(InvokeResult.SELECT_SUCCESS.getCode());
                    response.setMessage(InvokeResult.SELECT_SUCCESS.getMessage());
                    response.setResult(new ArrayList<>());
                    response.setTotal(total);
                }
                else{
                    response.setCode(InvokeResult.SELECT_SUCCESS.getCode());
                    response.setMessage(InvokeResult.SELECT_SUCCESS.getMessage());
                    response.setResult(new ArrayList<>());
                    response.setTotal(0);
                    logger.info("没有符合条件的发送记录总条数");
                }
            }


        } catch (Exception e) {
            response.setCode(InvokeResult.SYS_ERROR.getCode());
            response.setMessage(InvokeResult.SYS_ERROR.getMessage());
            response.setResult("系统异常");
            logger.error("查询发送记录总条数,异常,接口返回："+e);
        }

        return response;
    }
}
