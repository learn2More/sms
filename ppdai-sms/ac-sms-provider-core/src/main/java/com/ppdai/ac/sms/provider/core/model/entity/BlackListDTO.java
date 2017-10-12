package com.ppdai.ac.sms.provider.core.model.entity;

import java.sql.Timestamp;

/**
 * blacklist
 * author cash
 * create 2017-05-12-9:34
 **/

public class BlackListDTO {
    private Long listId;
    private String feature;
    private int createType;//创建类型 1手动创建 2自动创建
    private String remark;
    private boolean isActive;
    private Timestamp insertTime;
    private Timestamp updateTime;


    public Long getListId() {
        return listId;
    }

    public void setListId(Long listId) {
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
