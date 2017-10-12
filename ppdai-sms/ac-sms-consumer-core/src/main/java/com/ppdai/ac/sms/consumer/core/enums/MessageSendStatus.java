package com.ppdai.ac.sms.consumer.core.enums;

/**
 * Created by kiekiyang on 2017/4/25.
 */
public enum MessageSendStatus {

    INIT(0, "初始化"),
    SENDING(1, "发送中"),
    SUBMITSUCCESS(2, "提交成功"),
    SENDSUCCESS(3, "发送成功"),
    SUBMITFAIL(-1, "提交失败"),
    SENDFAIL(-2, "发送失败"),
    BLACK(-3, "黑名单拦截");

    private int code;
    private String comment;

    MessageSendStatus(int code, String comment) {
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
