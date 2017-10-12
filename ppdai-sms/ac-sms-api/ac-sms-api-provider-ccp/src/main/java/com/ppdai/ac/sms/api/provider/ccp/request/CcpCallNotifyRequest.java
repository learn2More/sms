package com.ppdai.ac.sms.api.provider.ccp.request;

/**
 * 语音通知
 * author cash
 * create 2017-05-16-13:51
 **/

public class CcpCallNotifyRequest {

    String recordId;
    String softversion;
    String accountSid;
    String mainToken;

    String appId;
    String to;
    String playTimes;
    String respUrl;
    String displayNum;
    String mediaName;
    String mediaNameType;
    String mediaTxt;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
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


    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getPlayTimes() {
        return playTimes;
    }

    public void setPlayTimes(String playTimes) {
        this.playTimes = playTimes;
    }

    public String getRespUrl() {
        return respUrl;
    }

    public void setRespUrl(String respUrl) {
        this.respUrl = respUrl;
    }

    public String getDisplayNum() {
        return displayNum;
    }

    public void setDisplayNum(String displayNum) {
        this.displayNum = displayNum;
    }

    public String getMediaName() {
        return mediaName;
    }

    public void setMediaName(String mediaName) {
        this.mediaName = mediaName;
    }

    public String getMediaNameType() {
        return mediaNameType;
    }

    public void setMediaNameType(String mediaNameType) {
        this.mediaNameType = mediaNameType;
    }

    public String getMediaTxt() {
        return mediaTxt;
    }

    public void setMediaTxt(String mediaTxt) {
        this.mediaTxt = mediaTxt;
    }
}
