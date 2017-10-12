package com.ppdai.ac.sms.consumer.core.response;

/**
 * Created by kiekiyang on 2017/5/8.
 */
public class MessageSendResponse {
    private int resultCode;
    private String resultMessage;
    private Object resultObject;

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
