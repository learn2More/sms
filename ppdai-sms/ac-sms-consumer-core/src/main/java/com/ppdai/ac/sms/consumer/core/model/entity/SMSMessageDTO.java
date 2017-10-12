package com.ppdai.ac.sms.consumer.core.model.entity;

import java.sql.Timestamp;

/**
 * Created by kiekiyang on 2017/5/4.
 */
public class SMSMessageDTO {
    private String messageId;

    private String recipient;

    private String content;

    private int callerId;

    private int businessId;

    private int templateId;

    private int contentType;

    private int status;

    private String requestIp;

    private String recipientIp;

    private String requestUrl;

    private String directory;

    private String hostName;

    private boolean isActive;

    private Timestamp insertTime;

    private Timestamp updateTime;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCallerId() {
        return callerId;
    }

    public void setCallerId(int callerId) {
        this.callerId = callerId;
    }

    public int getBusinessId() {
        return businessId;
    }

    public void setBusinessId(int businessId) {
        this.businessId = businessId;
    }

    public int getTemplateId() {
        return templateId;
    }

    public void setTemplateId(int templateId) {
        this.templateId = templateId;
    }

    public int getContentType() {
        return contentType;
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRequestIp() {
        return requestIp;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

    public String getRecipientIp() {
        return recipientIp;
    }

    public void setRecipientIp(String recipientIp) {
        this.recipientIp = recipientIp;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Timestamp getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Timestamp insertTime) {
        this.insertTime = insertTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SMSMessageDTO that = (SMSMessageDTO) o;

        if (callerId != that.callerId) return false;
        if (businessId != that.businessId) return false;
        if (templateId != that.templateId) return false;
        if (contentType != that.contentType) return false;
        if (status != that.status) return false;
        if (isActive != that.isActive) return false;
        if (messageId != null ? !messageId.equals(that.messageId) : that.messageId != null) return false;
        if (recipient != null ? !recipient.equals(that.recipient) : that.recipient != null) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (requestIp != null ? !requestIp.equals(that.requestIp) : that.requestIp != null) return false;
        if (recipientIp != null ? !recipientIp.equals(that.recipientIp) : that.recipientIp != null) return false;
        if (requestUrl != null ? !requestUrl.equals(that.requestUrl) : that.requestUrl != null) return false;
        if (directory != null ? !directory.equals(that.directory) : that.directory != null) return false;
        if (hostName != null ? !hostName.equals(that.hostName) : that.hostName != null) return false;
        if (insertTime != null ? !insertTime.equals(that.insertTime) : that.insertTime != null) return false;
        return updateTime != null ? updateTime.equals(that.updateTime) : that.updateTime == null;
    }

    @Override
    public int hashCode() {
        int result = messageId != null ? messageId.hashCode() : 0;
        result = 31 * result + (recipient != null ? recipient.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + callerId;
        result = 31 * result + businessId;
        result = 31 * result + templateId;
        result = 31 * result + contentType;
        result = 31 * result + status;
        result = 31 * result + (requestIp != null ? requestIp.hashCode() : 0);
        result = 31 * result + (recipientIp != null ? recipientIp.hashCode() : 0);
        result = 31 * result + (requestUrl != null ? requestUrl.hashCode() : 0);
        result = 31 * result + (directory != null ? directory.hashCode() : 0);
        result = 31 * result + (hostName != null ? hostName.hashCode() : 0);
        result = 31 * result + (isActive ? 1 : 0);
        result = 31 * result + (insertTime != null ? insertTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SMSMessageDTO{" +
                "messageId='" + messageId + '\'' +
                ", recipient='" + recipient + '\'' +
                ", content='" + content + '\'' +
                ", callerId=" + callerId +
                ", businessId=" + businessId +
                ", templateId=" + templateId +
                ", contentType=" + contentType +
                ", status=" + status +
                ", requestIp='" + requestIp + '\'' +
                ", recipientIp='" + recipientIp + '\'' +
                ", requestUrl='" + requestUrl + '\'' +
                ", directory='" + directory + '\'' +
                ", hostName='" + hostName + '\'' +
                ", isActive=" + isActive +
                ", insertTime=" + insertTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
