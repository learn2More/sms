package com.ppdai.ac.sms.api.provider.innerxw.request;

import java.util.List;

/**
 * 发送信息
 * author cash
 * create 2017-05-04-16:05
 **/

public class SendMsg {

    private List<String> mobiles;
    private String content;
    private String name;
    private String password;
    private String messageId;
    private String extendNo;

    private String cmHost;
    private String cmPort;
    private String wsHost;
    private String wsPort;


    public List<String> getMobiles() {
        return mobiles;
    }

    public void setMobiles(List<String> mobiles) {
        this.mobiles = mobiles;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getExtendNo() {
        return extendNo;
    }

    public void setExtendNo(String extendNo) {
        this.extendNo = extendNo;
    }

    public String getCmHost() {
        return cmHost;
    }

    public void setCmHost(String cmHost) {
        this.cmHost = cmHost;
    }

    public String getCmPort() {
        return cmPort;
    }

    public void setCmPort(String cmPort) {
        this.cmPort = cmPort;
    }

    public String getWsHost() {
        return wsHost;
    }

    public void setWsHost(String wsHost) {
        this.wsHost = wsHost;
    }

    public String getWsPort() {
        return wsPort;
    }

    public void setWsPort(String wsPort) {
        this.wsPort = wsPort;
    }
}
