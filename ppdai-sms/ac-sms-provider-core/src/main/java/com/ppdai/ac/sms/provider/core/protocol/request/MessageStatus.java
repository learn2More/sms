package com.ppdai.ac.sms.provider.core.protocol.request;

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


    public String getComment() {
        return comment;
    }

}
