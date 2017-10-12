package com.ppdai.ac.sms.contract.request.template;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;

/**
 * Created by wangxiaomei02 on 2017/5/16.
 * 修改已审批模板信息
 */
public class TemplateManagementRequest {

    @JsonProperty("status")
    private int status;

    @JsonProperty("departmentId")
    private int departmentId;

    @JsonProperty("businessId")
    private int businessId;

    @JsonProperty("messageKind")
    private int messageKind;

    @JsonProperty("templateContent")
    private String templateContent;

    @JsonProperty("intervalTime")
    private int intervalTime;

    @JsonProperty("maxCount")
    private int maxCount;

    @JsonProperty("totalMaxCount")
    private int totalMaxCount;

    @JsonProperty("callerId")
    private int callerId;

    @ApiModelProperty(value = "技术接入方")
    public int getCallerId() {
        return callerId;
    }

    public void setCallerId(int callerId) {
        this.callerId = callerId;
    }

    @ApiModelProperty(value = "模板状态")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @ApiModelProperty(value = "间隔时间")
    public int getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(int intervalTime) {
        this.intervalTime = intervalTime;
    }

    @ApiModelProperty(value = "最大发送次数")
    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    @ApiModelProperty(value = "一天总体发送次数")
    public int getTotalMaxCount() {
        return totalMaxCount;
    }

    public void setTotalMaxCount(int totalMaxCount) {
        this.totalMaxCount = totalMaxCount;
    }

    @ApiModelProperty(value = "业务类型")
    public int getBusinessId() {
        return businessId;
    }

    public void setBusinessId(int businessId) {
        this.businessId = businessId;
    }

    @ApiModelProperty(value = "申请部门")
    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    @ApiModelProperty(value = "消息类型")
    public int getMessageKind() {
        return messageKind;
    }

    public void setMessageKind(int messageKind) {
        this.messageKind = messageKind;
    }

    @ApiModelProperty(value = "模板内容")

    public String getTemplateContent() {
        return templateContent;
    }

    public void setTemplateContent(String templateContent) {
        this.templateContent = templateContent;
    }
}
