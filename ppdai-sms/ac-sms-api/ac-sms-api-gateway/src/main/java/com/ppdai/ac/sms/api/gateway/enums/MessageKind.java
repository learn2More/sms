package com.ppdai.ac.sms.api.gateway.enums;

/**
 * Created by kiekiyang on 2017/4/25.
 */
public enum MessageKind {
    Code(1,"验证码"),
    Notify(2,"通知"),
    Marketing(3,"营销"),
    ProlactinMoney(4,"催收");

    private int code;
    private String comment;
    MessageKind(int code,String comment){
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
