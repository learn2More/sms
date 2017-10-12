package com.ppdai.ac.sms.api.gateway.enums;

/**
 * Created by kiekiyang on 2017/5/3.
 */
public enum MessageType {

    MESSAGE(1,"文字消息"),
    VOICE(2,"语音消息"),
    EMAIL(3,"邮件");

    private int code;
    private String comment;
    MessageType(int code,String comment){
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
