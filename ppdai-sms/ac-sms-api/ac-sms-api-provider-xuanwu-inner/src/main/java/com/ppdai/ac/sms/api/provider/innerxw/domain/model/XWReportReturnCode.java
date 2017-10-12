package com.ppdai.ac.sms.api.provider.innerxw.domain.model;

/**
 * 玄武回执返回枚举
 * author cash
 * create 2017-05-15-11:25
 **/

public enum XWReportReturnCode {

    DELIVERED(0,"DELIVERED"),
    EXPIRED(1,"EXPIRED"),
    UNDELIVERABLE(2,"UNDELIVERABLE"),
    REJECTED(3,"REJECTED"),
    UNKNOWN(4,"UNKNOWN"),
    DELETED(5,"DELETED");

    private int code;
    private String describe;

    XWReportReturnCode(int code, String describe) {
        this.code = code;
        this.describe = describe;
    }

    public int getCode() {
        return code;
    }


    public String getDescribe() {
        return describe;
    }

}
