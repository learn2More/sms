package com.ppdai.ac.sms.contract.request.template;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by wangxiaomei02 on 2017/5/8.
 * 申请模板
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "")
public class TemplateApplyRequest {

    @JsonProperty("applyTitle")
    private String applyTitle;

    @JsonProperty("applyDepartment")
    private Integer applyDepartment;

    @JsonProperty("applicantName")
    private String applicantName;

    @JsonProperty("applicantEmail")
    private String applicantEmail;

    @JsonProperty("businessId")
    private Integer businessId;

    @JsonProperty("messageKind")
    private Integer messageKind;

    @JsonProperty("caller")
    private Integer caller;

    @JsonProperty("content")
    private String content;

    @JsonProperty("maxCount")
    private Integer maxCount;

    @JsonProperty("intervalTime")
    private Integer intervalTime;

    @JsonProperty("totalMaxCount")
    private  Integer totalMaxCount;

    @JsonProperty("approverEmail")
    private String approverEmail;

    @JsonProperty("applicantJobId")
    private String jobId;

    @JsonProperty("approverJobId")
    private String approverJobId;

    @ApiModelProperty(value = "审批人工号")
    public String getApproverJobId() {
        return approverJobId;
    }

    public void setApproverJobId(String approverJobId) {
        this.approverJobId = approverJobId;
    }

    public Integer getMaxCount() {
        return maxCount;
    }

    @ApiModelProperty(value = "单日该模板限制条数")
    public void setMaxCount(Integer maxCount) {
        this.maxCount = maxCount;
    }

    @ApiModelProperty(value = "间隔时间")
    public Integer getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(Integer intervalTime) {
        this.intervalTime = intervalTime;
    }
    @ApiModelProperty(value = " 一天总体发送次数")
    public Integer getTotalMaxCount() {
        return totalMaxCount;
    }

    public void setTotalMaxCount(Integer totalMaxCount) {
        this.totalMaxCount = totalMaxCount;
    }

    @ApiModelProperty(value = "申请人工号")
    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    @ApiModelProperty(value = "审批人邮箱")
    public String getApproverEmail() {
        return approverEmail;
    }

    public void setApproverEmail(String approverEmail) {
        this.approverEmail = approverEmail;
    }

    @ApiModelProperty(value = "申请人邮件")
    public String getApplicantEmail() {
        return applicantEmail;
    }

    public void setApplicantEmail(String applicantEmail) {
        this.applicantEmail = applicantEmail;
    }

    @ApiModelProperty(value = "申请人姓名")
    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    @ApiModelProperty(value = "申请部门")
    public Integer getApplyDepartment() {
        return applyDepartment;
    }

    public void setApplyDepartment(Integer applyDepartment) {
        this.applyDepartment = applyDepartment;
    }

    @ApiModelProperty(value = "模板标题")
    public String getApplyTitle() {
        return applyTitle;
    }

    public void setApplyTitle(String applyTitle) {
        this.applyTitle = applyTitle;
    }

    @ApiModelProperty(value = "业务编号")
    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    @ApiModelProperty(value = "接入方")
    public Integer getCaller() {
        return caller;
    }

    public void setCaller(Integer caller) {
        this.caller = caller;
    }

    @ApiModelProperty(value = "模板内容")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @ApiModelProperty(value = "消息类型")
    public Integer getMessageKind() {
        return messageKind;
    }

    public void setMessageKind(Integer messageKind) {
        this.messageKind = messageKind;
    }
}
