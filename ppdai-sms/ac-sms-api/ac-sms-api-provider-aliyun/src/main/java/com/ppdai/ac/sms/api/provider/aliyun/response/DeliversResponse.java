package com.ppdai.ac.sms.api.provider.aliyun.response;

import com.aliyun.mns.model.Message;

import java.util.List;

/**
 * 上行报告
 * author cash
 * create 2017-05-08-10:36
 **/

public class DeliversResponse extends  AbstractResponse {
    private List<Message> messages;

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
