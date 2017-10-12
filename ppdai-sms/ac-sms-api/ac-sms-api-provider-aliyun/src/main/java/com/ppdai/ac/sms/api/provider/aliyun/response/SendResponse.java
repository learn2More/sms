package com.ppdai.ac.sms.api.provider.aliyun.response;

/**
 * 发送短信返回
 * author cash
 * create 2017-05-05-15:04
 **/

public class SendResponse extends  AbstractResponse {
    private String msgId;//阿里反向设置msgid

    private String messageBody;//返回消息体

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }
}
