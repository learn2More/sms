package com.ppdai.ac.sms.api.message.consumer.code.scheduler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ppdai.ac.sms.api.message.consumer.code.enums.BizResult;
import com.ppdai.ac.sms.api.message.consumer.code.model.SMSMessageDTO;
import com.ppdai.ac.sms.consumer.core.api.MessageCoreService;
import com.ppdai.ac.sms.consumer.core.request.MessageSendRequest;
import com.ppdai.ac.sms.consumer.core.response.MessageSendResponse;
import com.ppdai.messagequeue.consumer.Message;
import com.ppdai.messagequeue.consumer.defaultConsumer.ISubscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by kiekiyang on 2017/5/4.
 */
@Component
public class MessageSubscriber implements ISubscriber {
    private static final Logger logger = LoggerFactory.getLogger(MessageSubscriber.class);

    @Autowired
    MessageCoreService messageCoreService;


//    @Autowired
//    MessageSubscriber(){
//        consumer.start();
//    }


    @Override
    public boolean onMessageReceived(Message message) throws Exception {
        logger.info("消费code消息,内容："+ JSONObject.toJSONString(message));
        try {
            SMSMessageDTO messageDTO = JSON.parseObject(message.getBody(), SMSMessageDTO.class);//(SMSMessageDTO)JSON.parse(message.getBody());
            MessageSendRequest request = new MessageSendRequest();
            com.ppdai.ac.sms.consumer.core.model.entity.SMSMessageDTO messageBean = new com.ppdai.ac.sms.consumer.core.model.entity.SMSMessageDTO();
            BeanUtils.copyProperties(messageDTO, messageBean);
            request.setSmsMessageDTO(messageBean);
            //request.setSmsMessageDTO());
            MessageSendResponse response = messageCoreService.messageSend(request);
            if (response.getResultCode() == BizResult.SUCCESS.getCode())
                return true;
        } catch (Exception ex) {
            logger.error("消息订阅topic处理异常", ex);
        }
        return false;
    }
}
