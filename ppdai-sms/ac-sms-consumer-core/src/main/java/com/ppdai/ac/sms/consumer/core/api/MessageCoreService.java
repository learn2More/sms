package com.ppdai.ac.sms.consumer.core.api;

import com.ppdai.ac.sms.consumer.core.request.MessageSendRequest;
import com.ppdai.ac.sms.consumer.core.response.MessageSendResponse;
import org.springframework.stereotype.Component;

/**
 * Created by kiekiyang on 2017/5/8.
 */
@Component
public interface MessageCoreService {
    MessageSendResponse messageSend(MessageSendRequest request);
}
