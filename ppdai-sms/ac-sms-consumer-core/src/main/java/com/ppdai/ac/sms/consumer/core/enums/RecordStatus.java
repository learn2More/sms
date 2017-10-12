package com.ppdai.ac.sms.consumer.core.enums;

/**
 * Created by kiekiyang on 2017/5/8.
 */
public enum RecordStatus {
    INIT(0, "发送中"),
    SUBMITSUCCESS(1, "提交成功"),
    SENDSUCCESS(2, "发送成功"),
    SUBMITFAIL(-1, "提交失败"),
    SENDFAIL(-2, "发送失败");

    private int code;
    private String comment;

    RecordStatus(int code, String comment) {
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
