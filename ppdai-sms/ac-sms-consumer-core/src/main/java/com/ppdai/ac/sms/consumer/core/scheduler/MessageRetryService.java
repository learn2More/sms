package com.ppdai.ac.sms.consumer.core.scheduler;

import com.ppdai.ac.sms.common.redis.DistributedLock;
import com.ppdai.ac.sms.consumer.core.configuration.RetryProperties;
import com.ppdai.ac.sms.consumer.core.dao.domain.MessageBiz;
import com.ppdai.ac.sms.consumer.core.dao.domain.MessageRecordBiz;
import com.ppdai.ac.sms.consumer.core.dao.mapper.smsmessage.SMSMessageMapper;
import com.ppdai.ac.sms.consumer.core.enums.MessageSendStatus;
import com.ppdai.ac.sms.consumer.core.model.entity.SMSMessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

/**
 * Created by kiekiyang on 2017/5/19.
 */
@Component
public class MessageRetryService {
    private final static Logger logger = LoggerFactory.getLogger(MessageRetryService.class);
    private final String MESSAGE_RETRY_LOCK_MARKER="SMS:MESSAGERETRYLOCK";

    @Autowired
    SMSMessageMapper smsMessageMapper;

    @Autowired
    MessageBiz messageBiz;

    @Autowired
    MessageRecordBiz messageRecordBiz;

    @Autowired
    RetryProperties retryProperties;

    @Autowired
    DistributedLock distributedLock;

    @Scheduled(fixedDelay = 30000)
    public void run() {
        if(distributedLock.tryLock(MESSAGE_RETRY_LOCK_MARKER,30L)){
            /**
             * 重试一个小时内的 提交失败的 普通短信 contentType为 2
             */
            LocalDateTime startTime = LocalDateTime.now().minusHours(1);
            LocalDateTime endTime = LocalDateTime.now().minusMinutes(2);
            Timestamp start = Timestamp.from(startTime.atZone(ZoneId.systemDefault()).toInstant());
            Timestamp end = Timestamp.from(endTime.atZone(ZoneId.systemDefault()).toInstant());
            logger.debug(String.format("开始重试类型为:%s 的提交失败的消息，时间范围：%s--%s", retryProperties.getContentType(),
                    start.toLocalDateTime(), end.toLocalDateTime()));
            List<SMSMessageDTO> retryList = smsMessageMapper.findMessageBySstatus(retryProperties.getContentType(), MessageSendStatus.SUBMITFAIL.getCode(),
                    start, end
            );
            for (SMSMessageDTO dto : retryList) {
                LocalDateTime now=LocalDateTime.now();
                int sendCnt = messageRecordBiz.querySendRecordCnt(dto.getMessageId(), startTime, now);
                if (sendCnt < retryProperties.getRetryMaxCount())
                    messageBiz.sendV2(dto);
            }
            //distributedLock.releaseLock(MESSAGE_RETRY_LOCK_MARKER);
        }

    }
}
