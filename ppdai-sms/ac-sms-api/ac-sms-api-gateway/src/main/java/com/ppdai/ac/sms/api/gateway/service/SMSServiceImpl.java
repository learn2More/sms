package com.ppdai.ac.sms.api.gateway.service;

import com.ppdai.ac.sms.api.contract.request.sms.BatchSendRequest;
import com.ppdai.ac.sms.api.contract.request.sms.SendMapParamRequest;
import com.ppdai.ac.sms.api.contract.request.sms.SendSmsRequest;
import com.ppdai.ac.sms.api.contract.response.sms.SendSmsResponse;
import com.ppdai.ac.sms.api.gateway.controller.SMSService;
import com.ppdai.ac.sms.api.gateway.dao.domain.MessageBiz;
import com.ppdai.ac.sms.api.gateway.dao.mapper.smsbase.MessageBusinessMapper;
import com.ppdai.ac.sms.api.gateway.enums.InvokeResult;
import com.ppdai.ac.sms.api.gateway.enums.MessageType;
import com.ppdai.ac.sms.api.gateway.model.bo.BizResult;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kiekiyang on 2017/4/24.
 */
@RestController
public class SMSServiceImpl implements SMSService {
    private static final Logger logger = LoggerFactory.getLogger(SMSServiceImpl.class);

    @Autowired
    MessageBiz messageBiz;

    @Autowired
    MessageBusinessMapper messageBusinessMapper;

    @Override
    public SendSmsResponse sendMessageSms(@ApiParam(value = "request", required = true) @RequestBody SendSmsRequest request) {
        //TODO 黑名单过滤,业务、接入方、模板校验、敏感词过滤、消息发送到mq
        SendSmsResponse response = new SendSmsResponse();
        try {
            BizResult result = send(request);
            if (result.getResultCode() == InvokeResult.SUCCESS.getCode()) {
                response.setResultCode(InvokeResult.SUCCESS.getCode());
                response.setResultMessage(InvokeResult.SUCCESS.getMessage());
                response.setResultObject(result.getResultObject());
            } else {
                response.setResultCode(result.getResultCode());
                response.setResultMessage(result.getResultMessage());
            }
        } catch (Exception ex) {
            logger.error("发送短信消息异常", ex);
            response.setResultCode(InvokeResult.SYS_ERROR.getCode());
            response.setResultMessage(InvokeResult.SYS_ERROR.getMessage());
        }
        return response;
    }

    @Override
    public SendSmsResponse sendSmsWithMapParam(@ApiParam(value = "request", required = true) @RequestBody SendMapParamRequest request) {
        SendSmsResponse response = new SendSmsResponse();

        Map<String, Object> requestExtroInfo = new HashMap<>();
        requestExtroInfo.put("requestUrl", request.getRequestUrl());
        requestExtroInfo.put("requestIp", request.getCallerIp());
        requestExtroInfo.put("recipientIp", request.getCallerIp());
        requestExtroInfo.put("directory", request.getDirectory());
        requestExtroInfo.put("hostName", request.getHostName());
        requestExtroInfo.put("tokenId", request.getTokenId());

        /**map 型参数**/
        requestExtroInfo.put("paramMap",request.getParameters());

        BizResult bizResult=messageBiz.send(MessageType.MESSAGE, request.getCallerId(), request.getMobile(),"",
                request.getBusinessAlias(), request.getTemplateAlias(), requestExtroInfo);

        if (bizResult.getResultCode() == InvokeResult.SUCCESS.getCode()) {
            response.setResultCode(InvokeResult.SUCCESS.getCode());
            response.setResultMessage(InvokeResult.SUCCESS.getMessage());
            response.setResultObject(bizResult.getResultObject());
        } else {
            response.setResultCode(bizResult.getResultCode());
            response.setResultMessage(bizResult.getResultMessage());
        }

        return response;
    }

    @Override
    public SendSmsResponse batchSend(@ApiParam(value = "request", required = true) @RequestBody BatchSendRequest request) {
        SendSmsResponse response = new SendSmsResponse();
        BizResult bizResult=messageBiz.batchSendSave(request);
        if (bizResult.getResultCode() == InvokeResult.SUCCESS.getCode()) {
            response.setResultCode(InvokeResult.SUCCESS.getCode());
            response.setResultMessage(InvokeResult.SUCCESS.getMessage());
            response.setResultObject(bizResult.getResultObject());
        } else {
            response.setResultCode(bizResult.getResultCode());
            response.setResultMessage(bizResult.getResultMessage());
        }
        return response;
    }


    private BizResult send(SendSmsRequest request) {
        Map<String, Object> requestExtroInfo = new HashMap<>();
        requestExtroInfo.put("requestUrl", request.getRequestUrl());
        requestExtroInfo.put("requestIp", request.getCallerIp());
        requestExtroInfo.put("recipientIp", request.getCallerIp());
        requestExtroInfo.put("directory", request.getDirectory());
        requestExtroInfo.put("hostName", request.getHostName());
        requestExtroInfo.put("tokenId", request.getTokenId());
        return messageBiz.send(MessageType.MESSAGE, request.getCallerId(), request.getMobile(), request.getParameters(),
                request.getBusinessAlias(), request.getTemplateAlias(), requestExtroInfo);
    }

    @Override
    public String sendSMS(int callerId, String callerIp, String recipientIp, String directory, String hostName,
                          String requestUrl, String businessAlias, String templateAlias, String mobile,
                          String parameters, String tokenId) {
        String result = "";
        try {
            SendSmsRequest smsRequest = new SendSmsRequest();
            smsRequest.setBusinessAlias(businessAlias);
            smsRequest.setMobile(mobile);
            smsRequest.setParameters(parameters);
            smsRequest.setTemplateAlias(templateAlias);
            smsRequest.setTokenId(tokenId);
            smsRequest.setCallerId(callerId);
            smsRequest.setCallerIp(callerIp);
            smsRequest.setDirectory(directory);
            smsRequest.setHostName(hostName);
            smsRequest.setRecipientIp(recipientIp);
            smsRequest.setRequestUrl(requestUrl);
            BizResult bizResult = send(smsRequest);
            result = bizResult.getResultMessage();
        } catch (Exception ex) {
            logger.error("发送短信消息异常", ex);
            result = "系统错误";
        }
        return result;
    }
}
