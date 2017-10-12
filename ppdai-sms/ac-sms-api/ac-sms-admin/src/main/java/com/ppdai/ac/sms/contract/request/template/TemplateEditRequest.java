package com.ppdai.ac.sms.contract.request.template;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by wangxiaomei02 on 2017/5/10.
 * 模板状态变更（启用/禁用）
 */
public class TemplateEditRequest {

    @JsonProperty("templateId")
    private int templateId;

    @JsonProperty("templateStatus")
    private int templateStatus;

    @JsonProperty("operator")
    private String operator;

    @ApiModelProperty(value = "模板id")
    public int getTemplateId() {
        return templateId;
    }

    public void setTemplateId(int templateId) {
        this.templateId = templateId;
    }

    @ApiModelProperty(value = "模板状态")
    public int getTemplateStatus() {
        return templateStatus;
    }

    public void setTemplateStatus(int templateStatus) {
        this.templateStatus = templateStatus;
    }
    @ApiModelProperty(value = "操作人")
    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
