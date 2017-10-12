package com.ppdai.ac.sms.api.provider.ccp.protocal.request;

/**
 * author cash
 * create 2017-05-15-16:43
 **/

public class CallNotifyRequest {

    private String appId;//应用Id
    private String to;//接收号码
    private String playTimes;//播放次数 1－3次
    private String respUrl;//用户接听呼叫后，发起请求通知应用侧
    private String displayNum;	//显示号码，显示权由服务器控制
    private String mediaName;//语音文件名称，格式 wav，播放多个文件用英文分号隔开。与mediaTxt不能同时为空。当不为空时mediaTxt属性失效。测试用默认语音：ccp_marketingcall.wav
    private String mediaNameType;//语音文件名的类型，默认值为0，表示用户语音文件；　值为1表示平台通用文件。此为json参数，在xml时为属性type值
    private String mediaTxt;//文本内容，文本中汉字要求utf8编码，默认值为空。当mediaName为空才有效

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


    public CallNotifyRequest(String appId, String to, String playTimes, String respUrl, String displayNum, String mediaName, String mediaNameType, String mediaTxt) {
        this.appId = appId;
        this.to = to;
        this.playTimes = playTimes;
        this.respUrl = respUrl;
        this.displayNum = displayNum;
        this.mediaName = mediaName;
        this.mediaNameType = mediaNameType;
        this.mediaTxt = mediaTxt;
    }

    public CallNotifyRequest() {
    }
}
