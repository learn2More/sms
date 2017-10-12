package com.ppdai.ac.sms.api.gateway.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;

/**
 * Created by kiekiyang on 2017/5/18.
 */
public class QueryMessageByIdRequest {
    @JsonProperty("MessageId")
    private String messageId;
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

    @ApiModelProperty(value = "查询消息开始时间")
    @JSONField(name = "EndTime",format="yyyy-MM-dd HH:mm:ss")
    public LocalDateTime getStartTime() {
        return startTime;
    }

    @JSONField(name = "EndTime",format="yyyy-MM-dd HH:mm:ss")
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    @ApiModelProperty(value = "查询消息结束时间")
    @JSONField(name = "EndTime",format="yyyy-MM-dd HH:mm:ss")
    public LocalDateTime getEndTime() {
        return endTime;
    }

    @JSONField(name = "EndTime",format="yyyy-MM-dd HH:mm:ss")
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
