package com.ppdai.ac.sms.api.gateway.controller;

import com.ppdai.ac.sms.api.contract.request.securitycode.VerifySecurityCodeRequest;
import com.ppdai.ac.sms.api.contract.response.securitycode.VerifySecurityCodeResponse;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by kiekiyang on 2017/4/24.
 */
@Api(value = "SecurityCodeService", description = "the SecurityCodeService API")
@RequestMapping("/api")
public interface SecurityCodeService {

    @ApiOperation(value = "校验验证码v2", notes = "verifySecurityCode", tags = {"SecurityCodeService"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "校验验证码v2")})
    @RequestMapping(value = {"/v2/CheckVerifyCode"},
            produces = {"application/json"},
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            method = RequestMethod.POST)
    String verifySecurityCodeV2(@ApiParam(value = "request", required = true) @RequestBody VerifySecurityCodeRequest request);


    @ApiOperation(value = "校验验证码", notes = "verifySecurityCode", tags = {"SecurityCodeService"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "校验验证码")})
    @RequestMapping(value = {"/CheckVerifyCode"},
            produces = {"application/json"},
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            method = RequestMethod.POST)
    String verifySecurityCode(int callerId,String callerIp,String recipientIp,String directory,String hostName,
                              String requestUrl,String recipient,String inputCode,
                              String verifyCodeBusinessAlias, boolean onlyCheck,String tokenId);

}