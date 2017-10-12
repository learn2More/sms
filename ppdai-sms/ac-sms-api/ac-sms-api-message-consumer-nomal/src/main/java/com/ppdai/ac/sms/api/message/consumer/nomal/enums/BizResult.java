package com.ppdai.ac.sms.api.message.consumer.nomal.enums;

/**
 * Created by kiekiyang on 2017/5/8.
 */
public enum BizResult {
    SUCCESS(0, "提交成功"),
    FAIL(-1, "提交失败");

    private int code;
    private String comment;

    BizResult(int code, String comment) {
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
