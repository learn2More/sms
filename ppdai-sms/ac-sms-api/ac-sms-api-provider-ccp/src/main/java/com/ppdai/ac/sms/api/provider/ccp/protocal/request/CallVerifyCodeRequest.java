package com.ppdai.ac.sms.api.provider.ccp.protocal.request;

/**
 * author cash
 * create 2017-05-15-16:43
 **/

public class CallVerifyCodeRequest {

    private String appId;//应用Id
    private String to;//接收号码
    private String playTimes;//播放次数 1－3次
    private String respUrl;//用户接听呼叫后，发起请求通知应用侧
    private String displayNum;	//显示号码，显示权由服务器控制
    private String playVerifyCode;//与verifyCode、txtVerifyCode三选一 语音验证码的内容全部播放此节点下的全部语音文件
    private String verifyCode;//与playVerifyCode，txtVerifyCode三选一 验证码内容，为数字和英文字母，不区分大小写，长度4-8位
    private String txtVerifyCode;//与playVerifyCode、verifyCodes三选一	TTS---需传入utf8格式文本完整的验证码内容

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

    public String getPlayVerifyCode() {
        return playVerifyCode;
    }

    public void setPlayVerifyCode(String playVerifyCode) {
        this.playVerifyCode = playVerifyCode;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getTxtVerifyCode() {
        return txtVerifyCode;
    }

    public void setTxtVerifyCode(String txtVerifyCode) {
        this.txtVerifyCode = txtVerifyCode;
    }

    public CallVerifyCodeRequest(String appId, String to, String playTimes, String respUrl, String displayNum, String playVerifyCode, String verifyCode, String txtVerifyCode) {
        this.appId = appId;
        this.to = to;
        this.playTimes = playTimes;
        this.respUrl = respUrl;
        this.displayNum = displayNum;
        this.playVerifyCode = playVerifyCode;
        this.verifyCode = verifyCode;
        this.txtVerifyCode = txtVerifyCode;
    }

    public CallVerifyCodeRequest() {
    }
}
