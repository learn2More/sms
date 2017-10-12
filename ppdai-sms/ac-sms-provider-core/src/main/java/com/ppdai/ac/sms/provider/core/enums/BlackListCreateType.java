package com.ppdai.ac.sms.provider.core.enums;

/**
 * 黑名单 创建类型
 * author cash
 * create 2017-05-12-11:05
 **/

public enum BlackListCreateType {

    MANUAL_CREATE(1,"自动创建"),
    AUTO_CREATE(2,"手动创建");


    private int code;
    private String describe;

    BlackListCreateType(int code, String describe) {
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
