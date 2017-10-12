package com.ppdai.ac.sms.api.gateway.request;

import java.sql.Timestamp;
import java.util.List;

/**
 * 定时发送保存
 * author cash
 * create 2017-08-01-11:16
 **/

public class TimedSendSave {
    private List<TimedSendList> timedSendList;

    private String businessAlias;

    private String templateAlias;

    private int callerId;

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

    public List<TimedSendList> getTimedSendList() {
        return timedSendList;
    }

    public void setTimedSendList(List<TimedSendList> timedSendList) {
        this.timedSendList = timedSendList;
    }

    public int getCallerId() {
        return callerId;
    }

    public void setCallerId(int callerId) {
        this.callerId = callerId;
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
        return "TimedSendSave{" +
                "timedSendList=" + timedSendList +
                ", businessAlias='" + businessAlias + '\'' +
                ", templateAlias='" + templateAlias + '\'' +
                ", callerId=" + callerId +
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
