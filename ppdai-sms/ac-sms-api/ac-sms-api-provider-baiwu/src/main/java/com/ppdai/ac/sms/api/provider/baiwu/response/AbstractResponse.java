package com.ppdai.ac.sms.api.provider.baiwu.response;

/**
 * 返回
 * author cash
 * create 2017-05-02-20:45
 **/

public abstract  class AbstractResponse {

    private int resultCode;

    private String resultMessage;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }


}
