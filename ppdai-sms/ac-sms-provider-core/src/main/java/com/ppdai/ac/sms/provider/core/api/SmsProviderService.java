package com.ppdai.ac.sms.provider.core.api;


import com.ppdai.ac.sms.provider.core.request.SmsRequest;
import com.ppdai.ac.sms.provider.core.response.SmsResponse;

/**
 * 短信服务商公共接口
 * author cash
 * create 2017-05-08-17:05
 **/

public interface SmsProviderService {

    SmsResponse send(SmsRequest request);

}
