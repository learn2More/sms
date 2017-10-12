package com.ppdai.ac.sms.api.gateway.model.entity;

import java.sql.Timestamp;

/**
 * Created by kiekiyang on 2017/4/28.
 */
public class SecurityCodeDTO {
    private long codeId;
    private int appId;
    private String codeKey;
    private String codeValue;
    private Timestamp expireTimeStamp;
    private boolean isActive;
    private Timestamp insertTime;
    private Timestamp updateTime;

    public long getCodeId() {
        return codeId;
    }

    public void setCodeId(long codeId) {
        this.codeId = codeId;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public String getCodeKey() {
        return codeKey;
    }

    public void setCodeKey(String codeKey) {
        this.codeKey = codeKey;
    }

    public String getCodeValue() {
        return codeValue;
    }

    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue;
    }

    public Timestamp getExpireTimeStamp() {
        return expireTimeStamp;
    }

    public void setExpireTimeStamp(Timestamp expireTimeStamp) {
        this.expireTimeStamp = expireTimeStamp;
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

        SecurityCodeDTO that = (SecurityCodeDTO) o;

        if (codeId != that.codeId) return false;
        if (appId != that.appId) return false;
        if (isActive != that.isActive) return false;
        if (codeKey != null ? !codeKey.equals(that.codeKey) : that.codeKey != null) return false;
        if (codeValue != null ? !codeValue.equals(that.codeValue) : that.codeValue != null) return false;
        if (expireTimeStamp != null ? !expireTimeStamp.equals(that.expireTimeStamp) : that.expireTimeStamp != null)
            return false;
        if (insertTime != null ? !insertTime.equals(that.insertTime) : that.insertTime != null) return false;
        return updateTime != null ? updateTime.equals(that.updateTime) : that.updateTime == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (codeId ^ (codeId >>> 32));
        result = 31 * result + appId;
        result = 31 * result + (codeKey != null ? codeKey.hashCode() : 0);
        result = 31 * result + (codeValue != null ? codeValue.hashCode() : 0);
        result = 31 * result + (expireTimeStamp != null ? expireTimeStamp.hashCode() : 0);
        result = 31 * result + (isActive ? 1 : 0);
        result = 31 * result + (insertTime != null ? insertTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SecurityCodeDTO{" +
                "codeId=" + codeId +
                ", appId=" + appId +
                ", codeKey='" + codeKey + '\'' +
                ", codeValue='" + codeValue + '\'' +
                ", expireTimeStamp=" + expireTimeStamp +
                ", isActive=" + isActive +
                ", insertTime=" + insertTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
