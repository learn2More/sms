package com.ppdai.ac.sms.api.gateway.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;

/**
 * Created by kiekiyang on 2017/5/18.
 */
public class ChangeMessageStatusRequest {

    @JsonProperty("MessageId")
    private String messageId;
    @JsonProperty("MessageStatus")
    private MessageStatus messageStatus;
    @JsonProperty("StartTime")

    private LocalDateTime startTime;
    @JsonProperty("EndTime")

    private LocalDateTime endTime;

    @ApiModelProperty(value = "消息编号")
    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    @ApiModelProperty(value = "需要变更的消息状态")
    public MessageStatus getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(MessageStatus messageStatus) {
        this.messageStatus = messageStatus;
    }

    @ApiModelProperty(value = "查询消息的开始时间")
    @JSONField(name = "EndTime",format="yyyy-MM-dd HH:mm:ss")
    public LocalDateTime getStartTime() {
        return startTime;
    }

    @JSONField(name = "EndTime",format="yyyy-MM-dd HH:mm:ss")
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    @ApiModelProperty(value = "查询消息的结束时间")
    @JSONField(name = "StartTime",format="yyyy-MM-dd HH:mm:ss")
    public LocalDateTime getEndTime() {
        return endTime;
    }

    @JSONField(name = "EndTime",format="yyyy-MM-dd HH:mm:ss")
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
