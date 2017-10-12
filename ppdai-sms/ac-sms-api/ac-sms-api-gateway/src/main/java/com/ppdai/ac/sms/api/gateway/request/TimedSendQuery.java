package com.ppdai.ac.sms.api.gateway.request;

import java.sql.Timestamp;

/**
 * 定时发送查询条件
 * author cash
 * create 2017-08-01-10:09
 **/

public class TimedSendQuery {

    private long timedSendId;

    private String businessAlias;

    private String templateAlias;

    private int callerId;

    private int  isTimed;

    private int  isSend;

    private int  isActive;

    private Timestamp insertTime;

    private Timestamp updateTime;

    private Timestamp timing;

    private int limitNum;//limit条数

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

    public int getLimitNum() {
        return limitNum;
    }

    public void setLimitNum(int limitNum) {
        this.limitNum = limitNum;
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

    @Override
    public String toString() {
        return "TimedSendQuery{" +
                "timedSendId=" + timedSendId +
                ", businessAlias='" + businessAlias + '\'' +
                ", templateAlias='" + templateAlias + '\'' +
                ", callerId=" + callerId +
                ", isTimed=" + isTimed +
                ", isSend=" + isSend +
                ", isActive=" + isActive +
                ", insertTime=" + insertTime +
                ", updateTime=" + updateTime +
                ", timing=" + timing +
                ", limitNum=" + limitNum +
                '}';
    }
}
