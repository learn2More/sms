package com.ppdai.ac.sms.provider.core.model.entity;

import java.sql.Timestamp;

/**
 * smsmessagemorecord
 * author cash
 * create 2017-05-09-18:05
 **/

public class SMSMessageMoRecordDTO {

    private Long moRecordId;
    private String channelNo;
    private String sender;
    private String content;
    private String  extendNo;//扩展码
    private boolean isActive;
    private Timestamp reportTime;

    private Timestamp insertTime;

    private Timestamp updateTime;

    public Long getMoRecordId() {
        return moRecordId;
    }

    public void setMoRecordId(Long moRecordId) {
        this.moRecordId = moRecordId;
    }

    public String getChannelNo() {
        return channelNo;
    }

    public void setChannelNo(String channelNo) {
        this.channelNo = channelNo;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExtendNo() {
        return extendNo;
    }

    public void setExtendNo(String extendNo) {
        this.extendNo = extendNo;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Timestamp getReportTime() {
        return reportTime;
    }

    public void setReportTime(Timestamp reportTime) {
        this.reportTime = reportTime;
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
}
