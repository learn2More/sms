package com.ppdai.ac.sms.api.provider.sendcloud.request;

import java.util.List;

/**
 * 发送信息
 * author cash
 * create 2017-05-04-16:05
 **/

public class SendMsg {

    private List<String> mobiles;
    private String content;
    private String msgId;//自定义消息id


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

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }
}
