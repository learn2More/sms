package com.ppdai.ac.sms.api.gateway.model.entity;

import java.sql.Timestamp;

/**
 * 定时发送
 * author cash
 * create 2017-07-31-20:14
 **/

public class TimedSendDTO {

    private long timedSendId;

    private String businessAlias;

    private String templateAlias;

    private int callerId;

    private String recipient;

    private String content;

    private String requestIp;

    private String requestUrl;

    private String directory;

    private String hostName;

    private String recipientIp;

    private int isTimed;

    private int isSend;

    private int isActive;

    private String remark;

    private Timestamp insertTime;

    private Timestamp updateTime;

    //定时发送时间
    private Timestamp timing;

    public long getTimedSendId() {
        return timedSendId;
    }

    public void setTimedSendId(long timedSendId) {
        this.timedSendId = timedSendId;
    }

    public int getCallerId() {
        return callerId;
    }

    public void setCallerId(int callerId) {
        this.callerId = callerId;
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

    public String getRequestIp() {
        return requestIp;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
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

    public String getRecipientIp() {
        return recipientIp;
    }

    public void setRecipientIp(String recipientIp) {
        this.recipientIp = recipientIp;
    }

    public int getIsTimed() {
        return isTimed;
    }

    public void setIsTimed(int isTimed) {
        this.isTimed = isTimed;
    }

    public int getIsSend() {
        return isSend;
    }

    public void setIsSend(int isSend) {
        this.isSend = isSend;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
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

    public Timestamp getTiming() {
        return timing;
    }

    public void setTiming(Timestamp timing) {
        this.timing = timing;
    }

    public String getBusinessAlias() {
        return businessAlias;
    }

    public void setBusinessAlias(String businessAlias) {
        this.businessAlias = businessAlias;
    }

    public String getTemplateAlias() {
        return templateAlias;
    }

    public void setTemplateAlias(String templateAlias) {
        this.templateAlias = templateAlias;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "TimedSendDTO{" +
                "timedSendId=" + timedSendId +
                ", businessAlias='" + businessAlias + '\'' +
                ", templateAlias='" + templateAlias + '\'' +
                ", callerId=" + callerId +
                ", recipient='" + recipient + '\'' +
                ", content='" + content + '\'' +
                ", requestIp='" + requestIp + '\'' +
                ", requestUrl='" + requestUrl + '\'' +
                ", directory='" + directory + '\'' +
                ", hostName='" + hostName + '\'' +
                ", recipientIp='" + recipientIp + '\'' +
                ", isTimed=" + isTimed +
                ", isSend=" + isSend +
                ", isActive=" + isActive +
                ", remark='" + remark + '\'' +
                ", insertTime=" + insertTime +
                ", updateTime=" + updateTime +
                ", timing=" + timing +
                '}';
    }
}
