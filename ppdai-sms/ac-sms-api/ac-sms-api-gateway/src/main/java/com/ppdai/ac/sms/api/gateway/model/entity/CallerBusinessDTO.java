package com.ppdai.ac.sms.api.gateway.model.entity;

import java.sql.Date;

/**
 * Created by kiekiyang on 2017/4/25.
 */
public class CallerBusinessDTO {
    private int cbId;
    private int callerId;
    private int businessId;
    private boolean isActive;
    private Date insertTime;
    private Date updateTime;

    public int getCbId() {
        return cbId;
    }

    public void setCbId(int cbId) {
        this.cbId = cbId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CallerBusinessDTO that = (CallerBusinessDTO) o;

        if (cbId != that.cbId) return false;
        if (callerId != that.callerId) return false;
        if (businessId != that.businessId) return false;
        if (isActive != that.isActive) return false;
        if (insertTime != null ? !insertTime.equals(that.insertTime) : that.insertTime != null) return false;
        return updateTime != null ? updateTime.equals(that.updateTime) : that.updateTime == null;
    }

    @Override
    public int hashCode() {
        int result = cbId;
        result = 31 * result + callerId;
        result = 31 * result + businessId;
        result = 31 * result + (isActive ? 1 : 0);
        result = 31 * result + (insertTime != null ? insertTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CallerBusinessDTO{" +
                "cbId=" + cbId +
                ", callerId=" + callerId +
                ", businessId=" + businessId +
                ", isActive=" + isActive +
                ", insertTime=" + insertTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
