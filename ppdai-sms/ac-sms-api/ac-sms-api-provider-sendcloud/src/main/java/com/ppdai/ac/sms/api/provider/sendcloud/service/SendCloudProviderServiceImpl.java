package com.ppdai.ac.sms.api.provider.sendcloud.service;

import com.ppdai.ac.sms.api.provider.sendcloud.domain.biz.SendCloudBiz;
import com.ppdai.ac.sms.api.provider.sendcloud.domain.model.SendMailInput;
import com.ppdai.ac.sms.provider.core.api.EmailProviderService;
import com.ppdai.ac.sms.provider.core.enums.InvokeResult;
import com.ppdai.ac.sms.provider.core.request.EmailRequest;
import com.ppdai.ac.sms.provider.core.response.EmailResponse;
import com.ppdai.ac.sms.provider.core.response.SmsResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * author cash
 * create 2017-05-02-13:30
 **/
@Api(value = "sendcloudProviderService", description = "the sendcloudProviderService")
@Controller
@RequestMapping(value = "/sendcloudProviderService")
public class SendCloudProviderServiceImpl implements EmailProviderService {

    @Autowired
    SendCloudBiz sendCloudBiz;

    private final static Logger logger = LoggerFactory.getLogger(SendCloudProviderServiceImpl.class);

    @ApiOperation(value = "SendCloud邮件发送", notes = "sendcloudProviderService.send", response = SmsResponse.class, tags = {"sendcloudProviderService.send"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SendCloud邮件发送", response = SmsResponse.class)})
    @RequestMapping(value = "/send",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    EmailResponse send(@RequestBody EmailRequest request) {
        EmailResponse emailResponse = new EmailResponse();
        Map<String, String> params = request.getParams();
        if (null != params) {
            if (StringUtils.isEmpty(params.get("apiUser")) || StringUtils.isEmpty(params.get("apiKey"))) {
                logger.error("SendCloud 配置异常");
                emailResponse.setResultCode(InvokeResult.FAIL.getCode());
                emailResponse.setResultMessage("SendCloud发送邮件,配置异常");
                return emailResponse;
            }
        }
        try {
            SendMailInput sendMailInput = new SendMailInput();
            sendMailInput.setApiKey(params.get("apiKey"));
            sendMailInput.setApiUser(params.get("apiUser"));
            sendMailInput.setFrom(params.get("from"));
            sendMailInput.setFromName(params.get("fromname"));

            sendMailInput.setToAddress(params.get("mobile"));
            sendMailInput.setSubject(params.get("templatename"));
            sendMailInput.setHtml(params.get("content"));
            sendMailInput.setRecordId(params.get("recordId"));
            SmsResponse smsResponse = sendCloudBiz.send(sendMailInput);
            if(smsResponse.getResultCode()==InvokeResult.SUCCESS.getCode()) {
                emailResponse.setResultCode(InvokeResult.SUCCESS.getCode());
                emailResponse.setResultMessage("发送成功");
                emailResponse.setResultObject(smsResponse.getResultMessage());
            }else{
                emailResponse.setResultCode(InvokeResult.FAIL.getCode());
                emailResponse.setResultMessage("发送失败");
            }
        }catch (Exception  ex){
            logger.error("SendCloudProviderServiceImpl  send  error",ex);
            emailResponse.setResultCode(InvokeResult.FAIL.getCode());
            emailResponse.setResultMessage("SendCloud发送邮件异常");
        }
        return  emailResponse;
    }
}
