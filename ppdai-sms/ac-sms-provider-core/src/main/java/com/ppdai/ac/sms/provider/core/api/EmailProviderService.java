package com.ppdai.ac.sms.provider.core.api;


import com.ppdai.ac.sms.provider.core.request.EmailRequest;
import com.ppdai.ac.sms.provider.core.response.EmailResponse;

/**
 * 邮件服务商公共接口
 * author zhongyunrui
 * create 2017-07-25-16:26
 **/

public interface EmailProviderService {

    EmailResponse send(EmailRequest request);

}
