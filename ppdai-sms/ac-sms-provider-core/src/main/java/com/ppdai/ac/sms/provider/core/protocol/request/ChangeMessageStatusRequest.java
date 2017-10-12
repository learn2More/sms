package com.ppdai.ac.sms.provider.core.protocol.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * author cash
 * create 2017-05-19-16:19
 **/

public class ChangeMessageStatusRequest {

    @JsonProperty("MessageId")
    private String messageId;

    @JsonProperty("StartTime")
    private String  startTime;

    @JsonProperty("EndTime")
    private String endTime;

    @JsonProperty("MessageStatus")
    private MessageStatus messageStatus;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public MessageStatus getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(MessageStatus messageStatus) {
        this.messageStatus = messageStatus;
    }
}
