package com.ppdai.ac.sms.contract.model.vo;

/**
 * Created by wangxiaomei02 on 2017/6/16.
 */
public class BusinessConifgVo {
    private int businessId;
    private int businessType;
    private int messageType;
    private String name;

    public int getBusinessId() {
        return businessId;
    }

    public void setBusinessId(int businessId) {
        this.businessId = businessId;
    }

    public int getBusinessType() {
        return businessType;
    }

    public void setBusinessType(int businessType) {
        this.businessType = businessType;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
