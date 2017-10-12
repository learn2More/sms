package com.ppdai.ac.sms.contract.dao.mapper.smsbase;

import com.ppdai.ac.sms.contract.model.entity.MessageTemplateDTO;
import com.ppdai.ac.sms.contract.model.vo.TemplateCheckVo;
import com.ppdai.ac.sms.contract.utils.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by wangxiaomei02 on 2017/5/26.
 */
@Component
public interface MessageTemplateMapper {
    List<TemplateCheckVo> getTemplateCheckList(@Param("templateStatus")Integer templateStatus,
                                               @Param("businessId")Integer businessId,
                                               @Param("messageKind")Integer messageKind,
                                               @Param("departmentId")Integer department,
                                               @Param("page")Page pgae,
                                               @Param("templateAlias")String templateAlias);

    int getTemplateCheckCount(@Param("templateStatus")Integer templateStatus,@Param("businessId")Integer businessId,
                              @Param("messageKind")Integer messageKind,@Param("departmentId")Integer department);

//    int editMessageTemplate(@Param("templateId")Integer templateId,@Param("templateStatus")Integer templateStatus,@Param("operator")String operator);

    int saveMessageTemplate(MessageTemplateDTO messageTemplateDTO);

    List<MessageTemplateDTO> getTemplateList();

    MessageTemplateDTO getTemplateById(@Param("templateId")Integer templateId);

    int editTemplateInfo(TemplateCheckVo vo);

}
