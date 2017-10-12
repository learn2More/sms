package com.ppdai.ac.sms.contract.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.bouncycastle.cms.PasswordRecipientId;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by wangxiaomei02 on 2017/5/10.
 * 模板查看列表页
 */

public class TemplateCheckVo {
    @JsonProperty("templateId")
    private int templateId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("templateAlias")
    private String templateAlias;

    @JsonProperty("departmentId")
    private int departmentId;

    @JsonProperty("businessId")
    private int businessId;

    @JsonProperty("messageKind")
    private int messageKind;

    @JsonProperty("callerId")
    private int callerId;

    @JsonProperty("templateContent")
    private String templateContent;

    @JsonProperty("approveTime")
    private Timestamp approveTime;

    @JsonProperty("templateStatus")
    private int templateStatus;

    @JsonProperty("applyTime")
    private Timestamp applyTime;

    @JsonProperty("intervalTime")
    private int intervalTime;

    @JsonProperty("maxCount")
    private int maxCount;

    @JsonProperty("totalMaxCount")
    private int totalMaxCount;

    @ApiModelProperty(value = "模板别名")
    public String getTemplateAlias() {
        return templateAlias;
    }

    public void setTemplateAlias(String templateAlias) {
        this.templateAlias = templateAlias;
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

    public Timestamp getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Timestamp applyTime) {
        this.applyTime = applyTime;
    }


    @ApiModelProperty(value = "模板编号")
    public int getTemplateId() {
        return templateId;
    }

    public void setTemplateId(int templateId) {
        this.templateId = templateId;
    }

    @ApiModelProperty(value = "模板标题")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    @ApiModelProperty(value = "接入方")
    public int getCallerId() {
        return callerId;
    }

    public void setCallerId(int callerId) {
        this.callerId = callerId;
    }

    @ApiModelProperty(value = "模板内容")
    public String getTemplateContent() {
        return templateContent;
    }

    public void setTemplateContent(String templateContent) {
        this.templateContent = templateContent;
    }

    @ApiModelProperty(value = "审批时间")
    public Timestamp getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(Timestamp approveTime) {
        this.approveTime = approveTime;
    }
    @ApiModelProperty(value = "模板状态")
    public int getTemplateStatus() {
        return templateStatus;
    }

    public void setTemplateStatus(int templateStatus) {
        this.templateStatus = templateStatus;
    }
}
