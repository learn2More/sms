package com.ppdai.ac.sms.api.gateway.controller;

import com.ppdai.ac.sms.api.contract.request.mail.SendMailRequest;
import com.ppdai.ac.sms.api.contract.response.mail.SendMailResponse;
import com.ppdai.ac.sms.api.contract.response.sms.SendSmsResponse;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by kiekiyang on 2017/4/24.
 */
@Api(value = "MailService", description = "the MailService API")
@RequestMapping("/api")
public interface MailService {

    @ApiOperation(value = "发送邮件", notes = "sendMail", response = SendSmsResponse.class, tags = {"MailService"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "发送邮件", response = SendSmsResponse.class)})
    @RequestMapping(value = {"/v2/mail/send"},
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    SendMailResponse sendMail(@ApiParam(value = "request", required = true) @RequestBody SendMailRequest request);

}
