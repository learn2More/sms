package com.ppdai.ac.sms.api.provider.jslt.protocal.request;

/**
 * author cash
 * create 2017-06-30-15:25
 **/

public class DeliverRequest {
    private String userid;

    private String timestamp;

    private String sign;

    private String action;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public DeliverRequest(){}

    public DeliverRequest(String userid, String timestamp, String sign, String action) {
        this.userid = userid;
        this.timestamp = timestamp;
        this.sign = sign;
        this.action = action;
    }
}
