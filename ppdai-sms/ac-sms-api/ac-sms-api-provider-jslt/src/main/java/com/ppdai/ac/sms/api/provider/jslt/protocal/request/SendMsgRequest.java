package com.ppdai.ac.sms.api.provider.jslt.protocal.request;

import java.util.List;

/**
 * author cash
 * create 2017-06-30-15:25
 **/

public class SendMsgRequest {
    private String userid;

    private String timestamp;

    private String sign;

    private List<String> mobiles;

    private String content;

    private String sendTime;

    private String action;

    private String extno;

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

    public List<String> getMobiles() {
        return mobiles;
    }

    public void setMobiles(List<String> mobiles) {
        this.mobiles = mobiles;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getExtno() {
        return extno;
    }

    public void setExtno(String extno) {
        this.extno = extno;
    }


    public SendMsgRequest(){}

    public SendMsgRequest(String userid, String timestamp, String sign, List<String> mobiles, String content, String sendTime, String action, String extno) {
        this.userid = userid;
        this.timestamp = timestamp;
        this.sign = sign;
        this.mobiles = mobiles;
        this.content = content;
        this.sendTime = sendTime;
        this.action = action;
        this.extno = extno;
    }

}
