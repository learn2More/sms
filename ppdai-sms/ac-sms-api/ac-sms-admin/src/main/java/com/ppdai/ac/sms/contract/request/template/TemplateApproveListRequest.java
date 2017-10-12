package com.ppdai.ac.sms.contract.request.template;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by wangxiaomei02 on 2017/5/24.
 * 模板审批页面查询条件
 */
public class TemplateApproveListRequest {

    @JsonProperty("templateId")
    private Integer templateId;

    @JsonProperty("approveStatus")
    private Integer approveStatus;

    @JsonProperty("approveEmail")
    private String approveEmail;

    @JsonProperty("reason")
    private String reason;

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public Integer getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(Integer approveStatus) {
        this.approveStatus = approveStatus;
    }

    public String getApproveEmail() {
        return approveEmail;
    }

    public void setApproveEmail(String approveEmail) {
        this.approveEmail = approveEmail;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
