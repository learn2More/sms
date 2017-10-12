package com.ppdai.ac.sms.api.gateway.model.entity;

import java.sql.Date;

/**
 * Created by kiekiyang on 2017/4/25.
 */
public class MessageBusinessDTO {
    private int businessId;

    private String businessName;

    private String businessAlias;

    private int messageType;

    private int totalMaxCount;

    private int expireSecond;

    private int verifyMaxCount;

    private boolean isActive;

    private Date insertTime;

    private Date updateTime;

    public int getBusinessId() {
        return businessId;
    }

    public void setBusinessId(int businessId) {
        this.businessId = businessId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessAlias() {
        return businessAlias;
    }

    public void setBusinessAlias(String businessAlias) {
        this.businessAlias = businessAlias;
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int getExpireSecond() {
        return expireSecond;
    }

    public void setExpireSecond(int expireSecond) {
        this.expireSecond = expireSecond;
    }

    public int getVerifyMaxCount() {
        return verifyMaxCount;
    }

    public void setVerifyMaxCount(int verifyMaxCount) {
        this.verifyMaxCount = verifyMaxCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageBusinessDTO that = (MessageBusinessDTO) o;

        if (businessId != that.businessId) return false;
        if (messageType != that.messageType) return false;
        if (totalMaxCount != that.totalMaxCount) return false;
        if (expireSecond != that.expireSecond) return false;
        if (verifyMaxCount != that.verifyMaxCount) return false;
        if (isActive != that.isActive) return false;
        if (businessName != null ? !businessName.equals(that.businessName) : that.businessName != null) return false;
        if (businessAlias != null ? !businessAlias.equals(that.businessAlias) : that.businessAlias != null)
            return false;
        if (insertTime != null ? !insertTime.equals(that.insertTime) : that.insertTime != null) return false;
        return updateTime != null ? updateTime.equals(that.updateTime) : that.updateTime == null;
    }

    @Override
    public int hashCode() {
        int result = businessId;
        result = 31 * result + (businessName != null ? businessName.hashCode() : 0);
        result = 31 * result + (businessAlias != null ? businessAlias.hashCode() : 0);
        result = 31 * result + messageType;
        result = 31 * result + totalMaxCount;
        result = 31 * result + expireSecond;
        result = 31 * result + verifyMaxCount;
        result = 31 * result + (isActive ? 1 : 0);
        result = 31 * result + (insertTime != null ? insertTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MessageBusinessDTO{" +
                "businessId=" + businessId +
                ", businessName='" + businessName + '\'' +
                ", businessAlias='" + businessAlias + '\'' +
                ", messageType=" + messageType +
                ", totalMaxCount=" + totalMaxCount +
                ", expireSecond=" + expireSecond +
                ", verifyMaxCount=" + verifyMaxCount +
                ", isActive=" + isActive +
                ", insertTime=" + insertTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
