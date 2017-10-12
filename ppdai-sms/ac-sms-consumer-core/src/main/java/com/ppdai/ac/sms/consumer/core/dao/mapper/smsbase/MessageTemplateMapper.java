package com.ppdai.ac.sms.consumer.core.dao.mapper.smsbase;


import com.ppdai.ac.sms.consumer.core.model.entity.MessageTemplateDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * Created by zhongyunrui on 2017/08/01.
 */
@Component
public interface MessageTemplateMapper {
    MessageTemplateDTO getTemplateByTemlateId(@Param("templateId") int templateId);
}
