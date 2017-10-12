package com.ppdai.ac.sms.contract.dao.mapper.smsbase;

import com.ppdai.ac.sms.contract.model.entity.MessageApplyDTO;
import com.ppdai.ac.sms.contract.model.vo.TemplateApproveVo;
import com.ppdai.ac.sms.contract.utils.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by kiekiyang on 2017/4/28.
 */
@Component
public interface TemplateWorkFlowMapper {
    int saveTemplateApply(MessageApplyDTO messageApplyDTO);



    List<TemplateApproveVo> getTemplateApproveList(@Param("approveStatus")Integer approveStatus,
                                                   @Param("list")List<Integer> departmentIds,
                                                   @Param("page")Page page);

    int getTemplateApproveCount(@Param("approveStatus")Integer approveStatus,
                                @Param("list")List<Integer> departmentIds);

    MessageApplyDTO findTemplateApplyById(@Param("applyId")Integer applyId);

    int editMessageApply(@Param("applyId")Integer applyId,@Param("reason")String reason,@Param("approveStatus")Integer approveStatus);

    int getTemplateByparam(@Param("callerId")Integer callerId,@Param("businessId")Integer businessId);

    List<Integer> findDepartmentsByJobId(String jobId);
}
