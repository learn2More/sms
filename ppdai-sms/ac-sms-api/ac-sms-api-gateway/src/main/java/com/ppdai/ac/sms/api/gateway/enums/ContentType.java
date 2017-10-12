package com.ppdai.ac.sms.api.gateway.enums;

/**
 * Created by kiekiyang on 2017/4/25.
 */
public enum ContentType {
    CODE(1,"验证码消息"),
    MESSAGE(2,"普通消息");

    private int code;
    private String comment;
    ContentType(int code,String comment){
        this.code = code;
        this.comment = comment;
    }

    public int getCode() {
        return code;
    }

/*    public void setCode(int code) {
        this.code = code;
    }*/

    public String getComment() {
        return comment;
    }

/*    public void setComment(String comment) {
        this.comment = comment;
    }*/
}
