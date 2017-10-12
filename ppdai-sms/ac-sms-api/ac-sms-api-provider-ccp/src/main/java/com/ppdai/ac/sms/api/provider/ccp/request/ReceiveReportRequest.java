package com.ppdai.ac.sms.api.provider.ccp.request;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * author cash
 * create 2017-05-17-11:21
 **/

@XmlRootElement(name = "Request")
public class ReceiveReportRequest {

    private String action;
    private String callSid;
    private String number;
    private String state;
    private String duration;
    private String userData;
    private String callstate;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getCallSid() {
        return callSid;
    }

    public void setCallSid(String callSid) {
        this.callSid = callSid;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getUserData() {
        return userData;
    }

    public void setUserData(String userData) {
        this.userData = userData;
    }

    public String getCallstate() {
        return callstate;
    }

    public void setCallstate(String callstate) {
        this.callstate = callstate;
    }
}
