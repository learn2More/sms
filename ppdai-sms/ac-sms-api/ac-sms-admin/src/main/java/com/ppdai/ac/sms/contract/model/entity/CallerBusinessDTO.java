package com.ppdai.ac.sms.contract.model.entity;

/**
 * Created by wangxiaomei02 on 2017/5/25.
 */
public class CallerBusinessDTO {
    private  Integer cBId;
    private Integer callerId;
    private Integer businessId;

    public Integer getcBId() {
        return cBId;
    }

    public void setcBId(Integer cBId) {
        this.cBId = cBId;
    }

    public Integer getCallerId() {
        return callerId;
    }

    public void setCallerId(Integer callerId) {
        this.callerId = callerId;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }
}
