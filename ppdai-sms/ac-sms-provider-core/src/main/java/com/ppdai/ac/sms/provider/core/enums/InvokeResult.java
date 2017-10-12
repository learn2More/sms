package com.ppdai.ac.sms.provider.core.enums;

/**
 * 返回结果枚举
 * author cash
 * create 2017-05-15-11:03
 **/

public enum InvokeResult {

    SUCCESS(0,"操作成功"),
    FAIL(-1,"操作失败");

    private int code;
    private String describe;


    public int getCode() {
        return code;
    }

    public String getDescribe() {
        return describe;
    }

    InvokeResult(int code, String describe) {
        this.code = code;
        this.describe = describe;
    }
}
