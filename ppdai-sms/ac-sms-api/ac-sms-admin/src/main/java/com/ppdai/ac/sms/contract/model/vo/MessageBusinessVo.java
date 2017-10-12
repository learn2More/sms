package com.ppdai.ac.sms.contract.model.vo;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by wangxiaomei02 on 2017/5/25.
 *
 */
public class MessageBusinessVo {
    private int businessId;
    private String businessName;
    private String businessAlias;
    private int messageType;
    private int VerifyMaxCount;
    private int totalMaxCount;
    private int expireSecond;
    private Timestamp insertTime;
    private Timestamp updateTime;

    public int getVerifyMaxCount() {
        return VerifyMaxCount;
    }

    public void setVerifyMaxCount(int verifyMaxCount) {
        VerifyMaxCount = verifyMaxCount;
    }

    public int getExpireSecond() {
        return expireSecond;
    }

    public void setExpireSecond(int expireSecond) {
        this.expireSecond = expireSecond;
    }

    public int getBusinessId() {
        return businessId;
    }

    public void setBusinessId(int businessId) {
        this.businessId = businessId;
    }

    public String getBusinessAlias() {
        return businessAlias;
    }

    public void setBusinessAlias(String businessAlias) {
        this.businessAlias = businessAlias;
    }


    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public Timestamp getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Timestamp insertTime) {
        this.insertTime = insertTime;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public int getTotalMaxCount() {
        return totalMaxCount;
    }

    public void setTotalMaxCount(int totalMaxCount) {
        this.totalMaxCount = totalMaxCount;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }
}
