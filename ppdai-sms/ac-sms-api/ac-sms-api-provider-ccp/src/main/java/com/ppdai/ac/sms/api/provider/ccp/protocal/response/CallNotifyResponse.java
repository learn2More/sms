package com.ppdai.ac.sms.api.provider.ccp.protocal.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * author cash
 * create 2017-05-15-17:54
 **/

public class CallNotifyResponse {

    private String statusCode;

    @JsonProperty("LandingCall")
    private LandingCall landingCall;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public LandingCall getLandingCall() {
        return landingCall;
    }

    public void setLandingCall(LandingCall landingCall) {
        this.landingCall = landingCall;
    }
}
