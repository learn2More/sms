package com.ppdai.ac.sms.api.provider.ccp.protocal.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 语音报告
 * author cash
 * create 2017-05-16-13:32
 **/

public class CallReportResponse {

    private String statusCode;
    private String statusMsg;

    @JsonProperty("CallResult")
    private CallResult callResult;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }

    public CallResult getCallResult() {
        return callResult;
    }

    public void setCallResult(CallResult callResult) {
        this.callResult = callResult;
    }
}
