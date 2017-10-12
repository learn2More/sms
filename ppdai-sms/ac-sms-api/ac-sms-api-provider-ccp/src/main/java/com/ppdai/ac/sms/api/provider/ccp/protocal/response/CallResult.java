package com.ppdai.ac.sms.api.provider.ccp.protocal.response;

/**
 * author cash
 * create 2017-05-16-13:33
 **/

public class CallResult {
    private String state;
    private String callTime;

    private String callstate;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCallTime() {
        return callTime;
    }

    public void setCallTime(String callTime) {
        this.callTime = callTime;
    }

    public String getCallstate() {
        return callstate;
    }

    public void setCallstate(String callstate) {
        this.callstate = callstate;
    }
}
