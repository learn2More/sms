package com.ppdai.ac.sms.api.gateway.request;

/**
 * 定时发送手机&内容
 * author cash
 * create 2017-08-01-11:14
 **/

public class TimedSendList {
    private String recipient;
    private String content;

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "TimedSendList{" +
                "recipient='" + recipient + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
