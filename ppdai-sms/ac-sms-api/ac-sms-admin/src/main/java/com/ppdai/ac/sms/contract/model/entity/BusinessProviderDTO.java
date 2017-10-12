package com.ppdai.ac.sms.contract.model.entity;

import java.sql.Timestamp;

/**
 * Created by wangxiaomei02 on 2017/6/14.
 */
public class BusinessProviderDTO {

    private Integer BPId;
    private Integer businessId;
    private Integer providerId;
    private Integer messageKind;
    private Integer weight;
    private Integer line;
    private Timestamp insertTime;
    private Timestamp updateTime;

    public Integer getBPId() {
        return BPId;
    }

    public void setBPId(Integer BPId) {
        this.BPId = BPId;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }

    public Integer getMessageKind() {
        return messageKind;
    }

    public void setMessageKind(Integer messageKind) {
        this.messageKind = messageKind;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getLine() {
        return line;
    }

    public void setLine(Integer line) {
        this.line = line;
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
