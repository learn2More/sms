package com.ppdai.ac.sms.contract.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by wangxiaomei02 on 2017/5/10.
 * 模板审批列表页
 *
 */
public class TemplateApproveVo {

    @JsonProperty("departmentId")
    private int departmentId;

    @JsonProperty("applicantEmail")
    private String applicantEmail;

    @JsonProperty("content")
    private String content;

    @JsonProperty("maxCount")
    private int maxCount;

    @JsonProperty("totalMaxCount")
    private int totalMaxCount;

    @JsonProperty("intervalTime")
    private int intervalTime;

    @JsonProperty("templateId")
    private int templateId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("applicant")
    private String applicant;

    @JsonProperty("applyTime")
    private Timestamp applyTime;

    @JsonProperty("businessId")
    private int businessId;

    @JsonProperty("messageKind")
    private int messageKind;

    @JsonProperty("callerId")
    private int callerId;

    @JsonProperty("reason")
    private String reason;

    @JsonProperty("approveStatus")
    private int approveStatus;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    @ApiModelProperty(value = "部门")
    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    @ApiModelProperty(value = "申请人邮箱")
    public String getApplicantEmail() {
        return applicantEmail;
    }

    public void setApplicantEmail(String applicantEmail) {
        this.applicantEmail = applicantEmail;
    }

    @ApiModelProperty(value = "模板内容")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @ApiModelProperty(value = "单日限制条数")
    public int getTotalMaxCount() {
        return totalMaxCount;
    }

    public void setTotalMaxCount(int totalMaxCount) {
        this.totalMaxCount = totalMaxCount;
    }

    @ApiModelProperty(value = "间隔时间")
    public int getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(int intervalTime) {
        this.intervalTime = intervalTime;
    }

    @ApiModelProperty(value = "模板状态")
    public int getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(int approveStatus) {
        this.approveStatus = approveStatus;
    }

    @ApiModelProperty(value = "标题")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @ApiModelProperty(value = "申请人")
    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    @ApiModelProperty(value = "申请时间")
    public Timestamp getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Timestamp applyTime) {
        this.applyTime = applyTime;
    }

    @ApiModelProperty(value = "业务类型")
    public int getBusinessId() {
        return businessId;
    }

    public void setBusinessId(int businessId) {
        this.businessId = businessId;
    }

    @ApiModelProperty(value = "模板类型")
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

    @ApiModelProperty(value = "模板id")
    public int getTemplateId() {
        return templateId;
    }

    public void setTemplateId(int templateId) {
        this.templateId = templateId;
    }

}
