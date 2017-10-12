package com.ppdai.ac.sms.provider.core.response;

/**
 * author zhongyunrui
 * create 2017-07-25-16:26
 **/

public class EmailResponse {

    private int resultCode;

    private String resultMessage;

    private   Object resultObject;

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

    public Object getResultObject() {
        return resultObject;
    }

    public void setResultObject(Object resultObject) {
        this.resultObject = resultObject;
    }
}
