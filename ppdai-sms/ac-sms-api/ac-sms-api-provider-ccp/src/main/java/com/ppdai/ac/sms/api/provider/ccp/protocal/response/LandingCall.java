package com.ppdai.ac.sms.api.provider.ccp.protocal.response;

/**
 * author cash
 * create 2017-06-15-21:12
 **/

public class LandingCall {
    private String dateCreated;
    private String callSid;
    private String orderId;

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getCallSid() {
        return callSid;
    }

    public void setCallSid(String callSid) {
        this.callSid = callSid;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
