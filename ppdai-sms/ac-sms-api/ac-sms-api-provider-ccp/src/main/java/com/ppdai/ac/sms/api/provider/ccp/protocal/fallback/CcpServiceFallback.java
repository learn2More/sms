package com.ppdai.ac.sms.api.provider.ccp.protocal.fallback;

import com.ppdai.ac.sms.api.provider.ccp.protocal.CcpService;
import com.ppdai.ac.sms.api.provider.ccp.protocal.request.CallNotifyRequest;
import com.ppdai.ac.sms.api.provider.ccp.protocal.request.CallVerifyCodeRequest;
import com.ppdai.ac.sms.api.provider.ccp.protocal.response.CallNotifyResponse;
import com.ppdai.ac.sms.api.provider.ccp.protocal.response.CallReportResponse;
import com.ppdai.ac.sms.api.provider.ccp.protocal.response.CallVerifyCodeResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * ccpService熔断器
 * author cash
 * create 2017-06-27-14:06
 **/
@Component
public class CcpServiceFallback implements CcpService {

    @Override
    public CallVerifyCodeResponse callVerifyCode(@PathVariable("softversion") String softversion, @PathVariable("accountSid") String accountSid, @RequestParam("sig") String sigParameter, @RequestParam("authorization") String authorization, @RequestBody CallVerifyCodeRequest callMsg) {
        CallVerifyCodeResponse callVerifyCodeResponse=new CallVerifyCodeResponse();
        callVerifyCodeResponse.setStatusCode("-1");
        callVerifyCodeResponse.setVoiceVerify(null);
        return callVerifyCodeResponse;
    }

    @Override
    public CallNotifyResponse callNotify(@PathVariable("softversion") String softversion, @PathVariable("accountSid") String accountSid, @RequestParam("sig") String sigParameter, @RequestParam("authorization") String authorization, @RequestBody CallNotifyRequest callMsg) {
        CallNotifyResponse callNotifyResponse=new CallNotifyResponse();
        callNotifyResponse.setStatusCode("-1");
        callNotifyResponse.setLandingCall(null);
        return callNotifyResponse;
    }

    @Override
    public CallReportResponse getCallResult(@PathVariable("softversion") String softversion, @PathVariable("accountSid") String accountSid, @RequestParam("sig") String sig, @RequestParam("callsid") String callsid, @RequestParam("authorization") String authorization) {
        return null;
    }
}
