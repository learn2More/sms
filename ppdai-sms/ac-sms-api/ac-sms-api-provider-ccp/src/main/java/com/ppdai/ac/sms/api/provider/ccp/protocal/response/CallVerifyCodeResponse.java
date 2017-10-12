package com.ppdai.ac.sms.api.provider.ccp.protocal.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * author cash
 * create 2017-05-15-17:54
 **/

public class CallVerifyCodeResponse {

    private String statusCode;

    @JsonProperty("VoiceVerify")
    private VoiceVerify voiceVerify;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public VoiceVerify getVoiceVerify() {
        return voiceVerify;
    }

    public void setVoiceVerify(VoiceVerify voiceVerify) {
        this.voiceVerify = voiceVerify;
    }

}
