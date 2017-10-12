package com.ppdai.ac.sms.api.gateway.service;

import com.ppdai.ac.sms.api.contract.request.mail.SendMailRequest;
import com.ppdai.ac.sms.api.contract.response.mail.SendMailResponse;
import com.ppdai.ac.sms.api.gateway.controller.MailService;
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
 * Created by zhongyunrui on 2017/08/01.
 */
@RestController
public class MailServiceImpl implements MailService {
    private static final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

    @Autowired
    MessageBiz messageBiz;

    @Autowired
    MessageBusinessMapper messageBusinessMapper;

    @Override
    public SendMailResponse sendMail(@ApiParam(value = "request", required = true) @RequestBody SendMailRequest request) {
        SendMailResponse response = new SendMailResponse();
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
            logger.error("发送邮件消息异常", ex);
            response.setResultCode(InvokeResult.SYS_ERROR.getCode());
            response.setResultMessage(InvokeResult.SYS_ERROR.getMessage());
        }
        return response;
    }

    private BizResult send(SendMailRequest request) {
        Map<String, Object> requestExtroInfo = new HashMap<>();
        requestExtroInfo.put("requestUrl", request.getRequestUrl());
        requestExtroInfo.put("requestIp", request.getCallerIp());
        requestExtroInfo.put("recipientIp", request.getCallerIp());
        requestExtroInfo.put("directory", request.getDirectory());
        requestExtroInfo.put("hostName", request.getHostName());
        requestExtroInfo.put("tokenId", request.getTokenId());

        /**map 型参数**/
        requestExtroInfo.put("paramMap",request.getParameters());

        return messageBiz.send(MessageType.EMAIL, request.getCallerId(), request.getEmail(),"",
                request.getBusinessAlias(), request.getTemplateAlias(), requestExtroInfo);
    }

}
