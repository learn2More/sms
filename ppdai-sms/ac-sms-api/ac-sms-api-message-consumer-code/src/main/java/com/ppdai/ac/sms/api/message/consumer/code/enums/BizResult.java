package com.ppdai.ac.sms.api.message.consumer.code.enums;

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
