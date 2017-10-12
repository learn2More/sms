package com.ppdai.ac.sms.api.gateway.enums;

/**
 * 是否枚举
 * author cash
 * create 2017-08-01-14:00
 **/

public enum WhetherEnum {
    No(0,"否"),
    YES(1,"是");

    private int code;
    private String comment;
    WhetherEnum(int code,String comment){
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
