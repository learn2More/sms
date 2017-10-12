package com.ppdai.ac.sms.provider.core.api;


import com.ppdai.ac.sms.provider.core.request.CallRequest;
import com.ppdai.ac.sms.provider.core.response.CallResponse;

/**
 * 语音服务商公共接口
 * author cash
 * create 2017-05-15-15:35
 **/

public interface CallProviderService {

    CallResponse callVerifyCode(CallRequest request);


    CallResponse callNotify(CallRequest request);
}
