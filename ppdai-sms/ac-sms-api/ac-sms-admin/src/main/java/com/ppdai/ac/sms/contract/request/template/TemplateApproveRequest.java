package com.ppdai.ac.sms.contract.request.template;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by wangxiaomei02 on 2017/5/9.
 * 模板审批
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "")
public class TemplateApproveRequest {

    @JsonProperty("templateId")
    private Integer templateId;

    @JsonProperty("templateStatus")
    private Integer templateStatus;

    @JsonProperty("reason")
    private String reason;


    @ApiModelProperty(value = "拒绝原因")
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
    @ApiModelProperty(value = "模板id")
    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(int templateId) {
        this.templateId = templateId;
    }
    @ApiModelProperty(value = "模板审批状态")
    public Integer getTemplateStatus() {
        return templateStatus;
    }

    public void setTemplateStatus(int templateStatus) {
        this.templateStatus = templateStatus;
    }
}
