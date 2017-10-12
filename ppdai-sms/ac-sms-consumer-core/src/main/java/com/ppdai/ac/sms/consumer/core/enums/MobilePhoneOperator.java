package com.ppdai.ac.sms.consumer.core.enums;

/**
 * Created by kiekiyang on 2017/5/5.
 */
public enum MobilePhoneOperator {
    CHINAMOBILE(1, "中国移动"),
    CHINAUNICOM(2, "中国联通"),
    CHINATELECOM(3, "中国电信"),
    PHS(4, "小灵通");

    private int code;
    private String comment;

    MobilePhoneOperator(int code, String comment) {
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
