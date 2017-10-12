package com.ppdai.ac.sms.api.gateway.model.entity;

import java.sql.Date;

/**
 * Created by kiekiyang on 2017/4/25.
 */
public class BlackListDTO {

    private int listId;

    private String feature;

    private int createType;

    private String remark;

    private boolean isActive;

    private Date insertTime;

    private Date updateTime;

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public int getCreateType() {
        return createType;
    }

    public void setCreateType(int createType) {
        this.createType = createType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

        BlackListDTO that = (BlackListDTO) o;

        if (listId != that.listId) return false;
        if (createType != that.createType) return false;
        if (isActive != that.isActive) return false;
        if (feature != null ? !feature.equals(that.feature) : that.feature != null) return false;
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;
        if (insertTime != null ? !insertTime.equals(that.insertTime) : that.insertTime != null) return false;
        return updateTime != null ? updateTime.equals(that.updateTime) : that.updateTime == null;
    }

    @Override
    public int hashCode() {
        int result = listId;
        result = 31 * result + (feature != null ? feature.hashCode() : 0);
        result = 31 * result + createType;
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (isActive ? 1 : 0);
        result = 31 * result + (insertTime != null ? insertTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BlackListDTO{" +
                "listId=" + listId +
                ", feature='" + feature + '\'' +
                ", createType=" + createType +
                ", remark='" + remark + '\'' +
                ", isActive=" + isActive +
                ", insertTime=" + insertTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
