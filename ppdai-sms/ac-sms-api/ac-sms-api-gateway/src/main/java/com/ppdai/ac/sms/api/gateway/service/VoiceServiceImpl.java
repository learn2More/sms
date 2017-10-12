package com.ppdai.ac.sms.api.gateway.service;

import com.ppdai.ac.sms.api.gateway.dao.domain.MessageBiz;
import com.ppdai.ac.sms.api.gateway.enums.InvokeResult;
import com.ppdai.ac.sms.api.gateway.model.bo.BizResult;
import com.ppdai.ac.sms.api.gateway.controller.VoiceService;
import com.ppdai.ac.sms.api.gateway.enums.MessageType;
import com.ppdai.ac.sms.api.contract.request.voice.SendVoiceRequest;
import com.ppdai.ac.sms.api.contract.response.voice.SendVoiceResponse;
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
public class VoiceServiceImpl implements VoiceService {
    private static final Logger logger = LoggerFactory.getLogger(SMSServiceImpl.class);

    @Autowired
    MessageBiz messageBiz;

    @Override
    public SendVoiceResponse sendVoiceSms(@ApiParam(value = "request", required = true) @RequestBody SendVoiceRequest request) {
        SendVoiceResponse response = new SendVoiceResponse();
        try {
            BizResult bizResult = send(request);
            if (bizResult.getResultCode() == InvokeResult.SUCCESS.getCode()) {
                response.setResultCode(InvokeResult.SUCCESS.getCode());
                response.setResultMessage(InvokeResult.SUCCESS.getMessage());
                response.setResultObject(bizResult.getResultObject());
            } else {
                response.setResultCode(bizResult.getResultCode());
                response.setResultMessage(bizResult.getResultMessage());
            }
        } catch (Exception ex) {
            logger.error("发送语音消息异常", ex);
            response.setResultCode(InvokeResult.SYS_ERROR.getCode());
            response.setResultMessage(InvokeResult.SYS_ERROR.getMessage());
        }
        return response;
    }

    private BizResult send(SendVoiceRequest request) {
        Map<String, Object> requestExtroInfo = new HashMap<>();
        requestExtroInfo.put("requestUrl", request.getRequestUrl());
        requestExtroInfo.put("requestIp", request.getCallerIp());
        requestExtroInfo.put("recipientIp", request.getRecipientIp());
        requestExtroInfo.put("directory", request.getDirectory());
        requestExtroInfo.put("hostName", request.getHostName());
        requestExtroInfo.put("tokenId", request.getTokenId());
        return messageBiz.send(MessageType.VOICE, request.getCallerId(), request.getPhone(), "",
                request.getBusinessAlias(), request.getTemplateAlias(), requestExtroInfo);
    }

    @Override
    public String sendVoice(int callerId,String callerIp,String recipientIp,String directory,String hostName,
                            String requestUrl,String businessAlias,String templateAlias,String phone,
                            String tokenId) {
        String result;
        try {
            SendVoiceRequest request=new SendVoiceRequest();
            request.setBusinessAlias(businessAlias);
            request.setPhone(phone);
            request.setTemplateAlias(templateAlias);
            request.setTokenId(tokenId);
            request.setCallerId(callerId);
            request.setCallerIp(callerIp);
            request.setDirectory(directory);
            request.setHostName(hostName);
            request.setRecipientIp(recipientIp);
            request.setRequestUrl(requestUrl);

            BizResult bizResult = send(request);
            result = bizResult.getResultMessage();
        } catch (Exception ex) {
            logger.error("发送语音消息异常", ex);
            result = "系统错误";
        }
        return result;
    }
}
