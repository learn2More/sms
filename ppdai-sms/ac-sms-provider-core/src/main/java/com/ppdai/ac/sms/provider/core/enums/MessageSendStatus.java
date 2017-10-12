package com.ppdai.ac.sms.provider.core.enums;

/**
 * message的状态
 * author cash
 * create 2017-05-12-14:50
 **/

public enum MessageSendStatus {

    INIT(0, "初始化"),
    SENDING(1, "发送中"),
    SUBMITSUCCESS(2, "提交成功"),
    SENDSUCCESS(3, "发送成功"),
    SUBMITFAIL(-1, "提交失败"),
    SENDFAIL(-2, "发送失败"),
    BLACK(-3, "黑名单拦截");

    private int code;
    private String describe;

    public int getCode() {
        return code;
    }

    public String getDescribe() {
        return describe;
    }

    MessageSendStatus(int code, String describe) {
        this.code = code;
        this.describe = describe;
    }
}
