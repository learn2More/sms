package com.ppdai.ac.sms.api.gateway.dao.mapper.smsbase;

import com.ppdai.ac.sms.api.gateway.model.entity.MessageTemplateDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * Created by kiekiyang on 2017/4/25.
 */
@Component
public interface MessageTemplateMapper {
    MessageTemplateDTO getTemplateByAlias(@Param("templateAlias") String templateAlias);

    MessageTemplateDTO getTemplateByAliasAndBusinessId(@Param("templateAlias") String templateAlias,@Param("businessId") int BusinessId);
}
