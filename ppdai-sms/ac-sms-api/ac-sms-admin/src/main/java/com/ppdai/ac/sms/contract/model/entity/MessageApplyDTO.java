package com.ppdai.ac.sms.contract.model.entity;

/**
 * Created by wangxiaomei02 on 2017/5/22.
 */
public class MessageApplyDTO {
    private int applyID;
    private int businessId;
    private int callerId;
    private String userId;
    private String applyTitle;
    private String applicant;
    private String applicantEmail;
    private int applyDepartmentId;
    private String templateContent;
    private int messageKind;
    private String reason;
    private int applyStatus;
    private int isActive;
    private int maxCount;
    private int totalMaxCount;
    private int intervalTime;
    private String approverJobId;

    public int getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(int intervalTime) {
        this.intervalTime = intervalTime;
    }


    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }
    public int getTotalMaxCount() {
        return totalMaxCount;
    }

    public void setTotalMaxCount(int totalMaxCount) {
        this.totalMaxCount = totalMaxCount;
    }

    public String getApproverJobId() {
        return approverJobId;
    }

    public void setApproverJobId(String approverJobId) {
        this.approverJobId = approverJobId;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public String getApplicantEmail() {
        return applicantEmail;
    }

    public void setApplicantEmail(String applicantEmail) {
        this.applicantEmail = applicantEmail;
    }

    public int getApplyDepartmentId() {
        return applyDepartmentId;
    }

    public void setApplyDepartmentId(int applyDepartmentId) {
        this.applyDepartmentId = applyDepartmentId;
    }

    public int getApplyID() {
        return applyID;
    }

    public void setApplyID(int applyID) {
        this.applyID = applyID;
    }

    public int getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(int applyStatus) {
        this.applyStatus = applyStatus;
    }

    public String getApplyTitle() {
        return applyTitle;
    }

    public void setApplyTitle(String applyTitle) {
        this.applyTitle = applyTitle;
    }

    public int getBusinessId() {
        return businessId;
    }

    public void setBusinessId(int businessId) {
        this.businessId = businessId;
    }

    public int getCallerId() {
        return callerId;
    }

    public void setCallerId(int callerId) {
        this.callerId = callerId;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int active) {
        isActive = active;
    }

    public int getMessageKind() {
        return messageKind;
    }

    public void setMessageKind(int messageKind) {
        this.messageKind = messageKind;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getTemplateContent() {
        return templateContent;
    }

    public void setTemplateContent(String templateContent) {
        this.templateContent = templateContent;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageApplyDTO that = (MessageApplyDTO) o;

        if (applyID != that.applyID ) return false;
        if (businessId != that.businessId) return false;
        if (callerId != that.callerId) return false;
        if (userId != that.userId) return false;
        if (applyTitle != null ? !applyTitle.equals(that.applyTitle) : that.applyTitle != null) return false;
        if (applicant != null ? !applicant.equals(that.applicant) : that.applicant != null) return false;
        if (applicantEmail != null ? !applicantEmail.equals(that.applicantEmail) : that.applicantEmail != null) return false;
        if (applyDepartmentId != that.applyDepartmentId) return false;
        if (templateContent != null ? !templateContent.equals(that.templateContent) : that.templateContent != null) return false;
        if (messageKind != that.messageKind) return false;
        if (reason != null ? !reason.equals(that.reason) : that.reason != null) return false;
        if (applyStatus != that.applyStatus) return false;
        return (isActive != that.isActive);

    }

    @Override
    public int hashCode() {
        int result = applyID;
        result = 31 * result + businessId;
        result = 31 * result + callerId;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (applyTitle != null ? applyTitle.hashCode() : 0);
        result = 31 * result + (applicant != null ? applicant.hashCode() : 0);
        result = 31 * result + (applicantEmail != null ? applicantEmail.hashCode() : 0);
        result = 31 * result + applyDepartmentId;
        result = 31 * result + (templateContent != null ? templateContent.hashCode() : 0);
        result = 31 * result + messageKind;
        result = 31 * result + (reason != null ? reason.hashCode() : 0);
        result = 31 * result + applyStatus;
        result = 31 * result + isActive;
        return result;
    }

    @Override
    public String toString() {
        return "MessageApplyDTO{" +
                "applyID=" + applyID +
                ", businessId=" + businessId +
                ", callerId=" + callerId +
                ", userId='" + userId +
                ", applyTitle=" + applyTitle +'\'' +
                ", applicant='" + applicant + '\'' +
                ", applicantEmail=" + applicantEmail +'\'' +
                ", applyDepartmentId=" + applyDepartmentId +
                ", templateContent=" + templateContent +'\'' +
                ", messageKind=" + messageKind +
                ", reason=" + reason +'\'' +
                ", applyStatus=" + applyStatus +
                ", isActive=" + isActive +
                '}';
    }
}
