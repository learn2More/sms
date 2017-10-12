package com.ppdai.ac.sms.contract.scheduler;

import com.ppdai.ac.sms.common.redis.RedisUtil;
import com.ppdai.ac.sms.contract.dao.mapper.smsbase.MessageTemplateMapper;
import com.ppdai.ac.sms.contract.model.entity.MessageTemplateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by wangxiaomei02 on 2017/6/20.
 */
@Component
public class TemplateScheduler {

    @Autowired
    MessageTemplateMapper messageTemplateMapper;

    @Autowired
    RedisUtil<String,MessageTemplateDTO> redisUtil;

    /**
     * 每1小时刷新模板缓存数据
     */
    @Scheduled(cron="0 59 *  * * ? ")
    public void run(){
        List<MessageTemplateDTO> dtoList=messageTemplateMapper.getTemplateList();
        if(dtoList !=null){
            for(MessageTemplateDTO dto:dtoList){
                String key=String.format("TEMPLATE:ID-%s",dto.getTemplateId());
                redisUtil.set(key,dto,7200L);

            }
        }
    }
}
