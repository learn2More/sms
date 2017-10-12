package com.ppdai.ac.sms.api.gateway.controller;

import com.ppdai.ac.sms.api.contract.request.voice.SendVoiceRequest;
import com.ppdai.ac.sms.api.contract.response.voice.SendVoiceResponse;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by kiekiyang on 2017/4/24.
 */
@Api(value = "VoiceService", description = "the VoiceService API")
@RequestMapping("/api")
public interface VoiceService {
    @ApiOperation(value = "发送语音短信V2", notes = "sendVoiceSms", response = SendVoiceResponse.class, tags = {"VoiceService"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "发送语音短信V2", response = SendVoiceResponse.class)})
    @RequestMapping(value = {"/v2/voice/send"},
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    SendVoiceResponse sendVoiceSms(@ApiParam(value = "request", required = true) @RequestBody SendVoiceRequest request);


    @ApiOperation(value = "发送语音短信", notes = "sendVoice", tags = {"VoiceService"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "查询结果")})
    @RequestMapping(value = {"/CallMsmq"},
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            method = RequestMethod.POST)
    String sendVoice(int callerId,String callerIp,String recipientIp,String directory,String hostName,
                     String requestUrl,String businessAlias,String templateAlias,String phone,
                     String tokenId);

}
