package com.ppdai.ac.sms.api.provider.ccp.protocal;

import com.ppdai.ac.sms.api.provider.ccp.protocal.fallback.CcpServiceFallback;
import com.ppdai.ac.sms.api.provider.ccp.protocal.request.CallNotifyRequest;
import com.ppdai.ac.sms.api.provider.ccp.protocal.request.CallVerifyCodeRequest;
import com.ppdai.ac.sms.api.provider.ccp.protocal.response.CallNotifyResponse;
import com.ppdai.ac.sms.api.provider.ccp.protocal.response.CallReportResponse;
import com.ppdai.ac.sms.api.provider.ccp.protocal.response.CallVerifyCodeResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * 容联service
 * author cash
 * create 2017-05-15-15:22
 **/

@FeignClient(name="${spring.application.name}",url ="${ccp.provider.url}",fallback = CcpServiceFallback.class)
public interface CcpService {


    @RequestMapping(value="{softversion}/Accounts/{accountSid}/Calls/VoiceVerify",
            method= RequestMethod.POST,
            produces = "application/json;charset=utf-8",
            consumes = "application/json;charset=utf-8",
            headers = {"Authorization={authorization}"})
    @ResponseBody
    CallVerifyCodeResponse callVerifyCode(@PathVariable("softversion") String softversion,
                                          @PathVariable("accountSid") String accountSid,
                                          @RequestParam("sig") String sigParameter,
                                          @RequestParam("authorization") String authorization,
                                          @RequestBody CallVerifyCodeRequest callMsg);


    @RequestMapping(value="{softversion}/Accounts/{accountSid}/Calls/LandingCalls",
            method= RequestMethod.POST,
            produces = "application/json;charset=utf-8",
            consumes = "application/json;charset=utf-8",
            headers = {"Authorization={authorization}"})
    @ResponseBody
    CallNotifyResponse callNotify(@PathVariable("softversion") String softversion,
                                  @PathVariable("accountSid") String accountSid,
                                  @RequestParam("sig") String sigParameter,
                                  @RequestParam("authorization") String authorization,
                                  @RequestBody CallNotifyRequest callMsg);



    @RequestMapping(value="{softversion}/Accounts/{accountSid}/CallResult",
            method= RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE,
            headers = {"Authorization={authorization}"})
    @ResponseBody
    CallReportResponse getCallResult(@PathVariable("softversion") String softversion,
                                     @PathVariable("accountSid") String accountSid,
                                     @RequestParam("sig") String sig,
                                     @RequestParam("callsid") String callsid,
                                     @RequestParam("authorization") String authorization);


}
