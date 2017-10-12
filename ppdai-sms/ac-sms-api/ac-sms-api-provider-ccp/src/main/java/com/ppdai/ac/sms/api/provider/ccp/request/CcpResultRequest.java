package com.ppdai.ac.sms.api.provider.ccp.request;

/**
 * author cash
 * create 2017-05-16-13:54
 **/

public class CcpResultRequest {

    String callsid;//记录id
    String softversion;
    String accountSid;
    String mainToken;

    public String getCallsid() {
        return callsid;
    }

    public void setCallsid(String callsid) {
        this.callsid = callsid;
    }

    public String getSoftversion() {
        return softversion;
    }

    public void setSoftversion(String softversion) {
        this.softversion = softversion;
    }

    public String getAccountSid() {
        return accountSid;
    }

    public void setAccountSid(String accountSid) {
        this.accountSid = accountSid;
    }

    public String getMainToken() {
        return mainToken;
    }

    public void setMainToken(String mainToken) {
        this.mainToken = mainToken;
    }

}
