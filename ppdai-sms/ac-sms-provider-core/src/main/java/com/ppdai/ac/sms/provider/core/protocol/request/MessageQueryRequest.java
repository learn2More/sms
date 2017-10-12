package com.ppdai.ac.sms.provider.core.protocol.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * author cash
 * create 2017-05-19-13:14
 **/

public class MessageQueryRequest {


    @JsonProperty("MessageId")
    private String messageId;

    @JsonProperty("StartTime")
    private String  startTime;

    @JsonProperty("EndTime")
    private String endTime;

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
}
