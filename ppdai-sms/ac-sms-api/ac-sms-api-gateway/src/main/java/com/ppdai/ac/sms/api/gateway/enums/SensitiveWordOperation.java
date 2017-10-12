package com.ppdai.ac.sms.api.gateway.enums;

/**
 * Created by kiekiyang on 2017/4/26.
 */
public enum SensitiveWordOperation {
    IGNORE(1, "忽略"),
    BLOCK(2, "阻止"),
    REPLACE(3, "替换");

    private int code;
    private String comment;

    SensitiveWordOperation(int code, String comment) {
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
