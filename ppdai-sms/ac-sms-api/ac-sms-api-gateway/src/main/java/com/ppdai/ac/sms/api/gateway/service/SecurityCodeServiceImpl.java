package com.ppdai.ac.sms.api.gateway.service;

import com.alibaba.fastjson.JSONObject;
import com.ppdai.ac.sms.api.contract.request.securitycode.VerifySecurityCodeRequest;
import com.ppdai.ac.sms.api.contract.response.securitycode.VerifySecurityCodeResponse;
import com.ppdai.ac.sms.api.gateway.controller.SecurityCodeService;
import com.ppdai.ac.sms.api.gateway.dao.domain.MessageBiz;
import com.ppdai.ac.sms.api.gateway.enums.InvokeResult;
import com.ppdai.ac.sms.api.gateway.model.bo.BizResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kiekiyang on 2017/4/24.
 */
@RestController
public class SecurityCodeServiceImpl implements SecurityCodeService {
    private static final Logger logger = LoggerFactory.getLogger(SecurityCodeServiceImpl.class);

    @Autowired
    MessageBiz messageBiz;

    @Override
    public String verifySecurityCodeV2(VerifySecurityCodeRequest request) {
        logger.info("------------verifySecurityCodeV2 校验验证码入参： "+ JSONObject.toJSONString(request));
        VerifySecurityCodeResponse response = new VerifySecurityCodeResponse();
        try {
            Map<String, String> requestExtroInfo = new HashMap<>();
            requestExtroInfo.put("tokenId", request.getTokenId());
            BizResult result = messageBiz.verifyMessageSecurityCode(request.getCallerId(), request.getRecipient(), request.getVerifyCodeBusinessAlias(),
                    request.getInputCode(), request.getOnlyCheck(), requestExtroInfo);

            if (result.getResultCode() == InvokeResult.SUCCESS.getCode()) {
                response.setResultCode(InvokeResult.SUCCESS.getCode());
                response.setResultMessage("验证成功");
                
            } else {
                response.setResultCode(result.getResultCode());
                response.setResultMessage(result.getResultMessage());
            }
        } catch (Exception ex) {
            logger.error("发送短信消息异常", ex);
            response.setResultCode(InvokeResult.SYS_ERROR.getCode());
            response.setResultMessage(InvokeResult.SYS_ERROR.getMessage());
        }
        logger.info("------------verifySecurityCodeV2 校验验证码返回： "+ JSONObject.toJSONString(response));
        return response.getResultMessage();
    }

    @Override
    public String verifySecurityCode(int callerId, String callerIp, String recipientIp, String directory, String hostName, String requestUrl, String recipient, String inputCode, String verifyCodeBusinessAlias, boolean onlyCheck, String tokenId) {
        logger.info("------------verifySecurityCode 校验验证码入参： "+ " callerId: "+callerId+" recipient: "+recipient+" inputCode: "+inputCode
        +" verifyCodeBusinessAlias: "+verifyCodeBusinessAlias+" onlyCheck: "+onlyCheck+" tokenId: "+tokenId);
        VerifySecurityCodeResponse response = new VerifySecurityCodeResponse();
        try {
            Map<String, String> requestExtroInfo = new HashMap<>();
            requestExtroInfo.put("tokenId", tokenId);
            BizResult result = messageBiz.verifyMessageSecurityCode(callerId, recipient, verifyCodeBusinessAlias,
                    inputCode, onlyCheck, requestExtroInfo);

            if (result.getResultCode() == InvokeResult.SUCCESS.getCode()) {
                response.setResultCode(InvokeResult.SUCCESS.getCode());
                response.setResultMessage("验证成功");

            } else {
                response.setResultCode(result.getResultCode());
                response.setResultMessage(result.getResultMessage());
            }
        } catch (Exception ex) {
            logger.error("发送短信消息异常", ex);
            response.setResultCode(InvokeResult.SYS_ERROR.getCode());
            response.setResultMessage(InvokeResult.SYS_ERROR.getMessage());
        }
        logger.info("------------verifySecurityCode 校验验证码返回： "+ JSONObject.toJSONString(response));
        return response.getResultMessage();
    }
}
