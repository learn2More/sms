package com.ppdai.ac.sms.consumer.core.service;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Message;
import com.dianping.cat.message.Transaction;
import com.ppdai.ac.sms.consumer.core.api.MessageCoreService;
import com.ppdai.ac.sms.consumer.core.dao.domain.MessageBiz;
import com.ppdai.ac.sms.consumer.core.enums.InvokeResult;
import com.ppdai.ac.sms.consumer.core.request.MessageSendRequest;
import com.ppdai.ac.sms.consumer.core.response.MessageSendResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by kiekiyang on 2017/5/8.
 */
@Service
public class MessageCoreServiceImpl implements MessageCoreService {
    @Autowired
    MessageBiz messageBiz;

    @Override
    public MessageSendResponse messageSend(MessageSendRequest request) {
        MessageSendResponse response = new MessageSendResponse();
        Transaction transaction = Cat.newTransaction("MqConsumeHandlerTimeWaste ", request.getSmsMessageDTO().getMessageId());
        long startMillis=System.currentTimeMillis();
        try {
            if (messageBiz.sendV2(request.getSmsMessageDTO())) {
                response.setResultCode(InvokeResult.SUCCESS.getCode());
                response.setResultMessage(InvokeResult.SUCCESS.getMessage());
            }else{
                response.setResultCode(InvokeResult.FAIL.getCode());
                response.setResultMessage(InvokeResult.FAIL.getMessage());
            }
            long endMillis=System.currentTimeMillis();
            Cat.logMetricForDuration("sms,Mq消费,处理耗时",
                    endMillis-startMillis);
            transaction.setStatus(Message.SUCCESS);
        } catch (Exception e) {
            Cat.logError(e);
        } finally {
            transaction.complete();
        }
        return response;
    }
}
