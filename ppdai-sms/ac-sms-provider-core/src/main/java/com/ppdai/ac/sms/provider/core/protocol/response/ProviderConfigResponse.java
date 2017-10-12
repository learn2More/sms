package com.ppdai.ac.sms.provider.core.protocol.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * author cash
 * create 2017-05-19-15:15
 **/

public class ProviderConfigResponse {
    @JsonProperty("ResultCode")
    private int resultCode;

    @JsonProperty("ResultMessage")
    private String resultMessage;

    @JsonProperty("ResultObject")
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
