package com.ppdai.ac.sms.consumer.core.request;

import com.ppdai.ac.sms.consumer.core.model.entity.SMSMessageDTO;

/**
 * Created by kiekiyang on 2017/5/8.
 */
public class MessageSendRequest {
    private SMSMessageDTO smsMessageDTO;

    public SMSMessageDTO getSmsMessageDTO() {
        return smsMessageDTO;
    }

    public void setSmsMessageDTO(SMSMessageDTO smsMessageDTO) {
        this.smsMessageDTO = smsMessageDTO;
    }
}
