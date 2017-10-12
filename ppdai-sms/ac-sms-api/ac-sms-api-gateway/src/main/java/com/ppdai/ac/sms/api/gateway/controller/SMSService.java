package com.ppdai.ac.sms.api.gateway.controller;

import com.ppdai.ac.sms.api.contract.request.sms.BatchSendRequest;
import com.ppdai.ac.sms.api.contract.request.sms.SendMapParamRequest;
import com.ppdai.ac.sms.api.contract.request.sms.SendSmsRequest;
import com.ppdai.ac.sms.api.contract.response.sms.SendSmsResponse;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by kiekiyang on 2017/4/24.
 */
@Api(value = "SMSService", description = "the SMSService API")
@RequestMapping("/api")
public interface SMSService {

    @ApiOperation(value = "发送文字短信V2", notes = "sendMessageSms", response = SendSmsResponse.class, tags = {"SMSService"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "发送文字短信V2", response = SendSmsResponse.class)})
    @RequestMapping(value = {"/v2/sms/send"},
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    SendSmsResponse sendMessageSms(@ApiParam(value = "request", required = true) @RequestBody  SendSmsRequest request);

    @ApiOperation(value = "发送文字短信v3(模板参数,map传入)", notes = "sendSmsWithMapParam", response = SendSmsResponse.class, tags = {"SMSService"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "发送文字短信v3(模板参数,map传入)", response = SendSmsResponse.class)})
    @RequestMapping(value = {"/v3/sms/send"},
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    SendSmsResponse sendSmsWithMapParam(@ApiParam(value = "request", required = true) @RequestBody SendMapParamRequest request);


    @ApiOperation(value = "批量发送文字短信", notes = "batchSend", response = SendSmsResponse.class, tags = {"SMSService"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "批量发送文字短信", response = SendSmsResponse.class)})
    @RequestMapping(value = {"/sms/batchSend"},
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    SendSmsResponse batchSend(@ApiParam(value = "request", required = true) @RequestBody BatchSendRequest request);




    @ApiOperation(value = "发送文字短信", notes = "sendSMS", tags = {"SMSService"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "发送文字短信")})
    @RequestMapping(value = {"/SMSMsmq"},
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            method = RequestMethod.POST)
    String sendSMS(int callerId,String callerIp,String recipientIp,String directory,String hostName,
                   String requestUrl,String businessAlias,String templateAlias,String mobile,
                   String parameters,String tokenId);

}
