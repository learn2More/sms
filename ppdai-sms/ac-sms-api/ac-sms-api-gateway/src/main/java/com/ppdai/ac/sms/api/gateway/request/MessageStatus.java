package com.ppdai.ac.sms.api.gateway.request;

/**
 * Created by kiekiyang on 2017/5/18.
 */
public enum MessageStatus {
    SENDSUCCESS(3, "发送成功"),
    SENDFAIL(-2, "发送失败");

    private int code;
    private String comment;

    MessageStatus(int code, String comment) {
        this.code = code;
        this.comment = comment;
    }

    public int getCode() {
        return code;
    }

    /*public void setCode(int code) {
        this.code = code;
    }*/

    public String getComment() {
        return comment;
    }

    /*public void setComment(String comment) {
        this.comment = comment;
    }*/
}
