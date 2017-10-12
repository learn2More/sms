package com.ppdai.ac.sms.api.provider.techown.protocal.response;

/**
 * 天畅发送返回
 * author cash
 * create 2017-07-12-14:21
 **/

public class TcSendResponse {

    private String id; //messageId
    private boolean success;
    private Integer r;//错误码

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Integer getR() {
        return r;
    }

    public void setR(Integer r) {
        this.r = r;
    }
}
