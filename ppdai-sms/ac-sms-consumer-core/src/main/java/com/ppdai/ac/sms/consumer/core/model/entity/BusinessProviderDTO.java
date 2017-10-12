package com.ppdai.ac.sms.consumer.core.model.entity;

import java.sql.Timestamp;

/**
 * Created by kiekiyang on 2017/5/4.
 */
public class BusinessProviderDTO implements Comparable<BusinessProviderDTO> {
    private int businessId;
    private int providerId;
    private int weight;
    private int line;
    private boolean isActive;
    private Timestamp insertTime;
    private Timestamp updatetime;

    public int getBusinessId() {
        return businessId;
    }

    public void setBusinessId(int businessId) {
        this.businessId = businessId;
    }

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
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

    public Timestamp getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Timestamp updatetime) {
        this.updatetime = updatetime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BusinessProviderDTO that = (BusinessProviderDTO) o;

        if (businessId != that.businessId) return false;
        if (providerId != that.providerId) return false;
        if (weight != that.weight) return false;
        if (line != that.line) return false;
        if (isActive != that.isActive) return false;
        if (insertTime != null ? !insertTime.equals(that.insertTime) : that.insertTime != null) return false;
        return updatetime != null ? updatetime.equals(that.updatetime) : that.updatetime == null;
    }

    @Override
    public int hashCode() {
        int result = businessId;
        result = 31 * result + providerId;
        result = 31 * result + weight;
        result = 31 * result + line;
        result = 31 * result + (isActive ? 1 : 0);
        result = 31 * result + (insertTime != null ? insertTime.hashCode() : 0);
        result = 31 * result + (updatetime != null ? updatetime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BusinessProviderDTO{" +
                "businessId=" + businessId +
                ", providerId=" + providerId +
                ", weight=" + weight +
                ", line=" + line +
                ", isActive=" + isActive +
                ", insertTime=" + insertTime +
                ", updatetime=" + updatetime +
                '}';
    }


    @Override
    public int compareTo(BusinessProviderDTO o) {
        int compare =this.weight-o.getWeight();
        return compare ;
    }
}
