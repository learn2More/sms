package com.ppdai.ac.sms.contract.request.messagerecord;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ppdai.ac.sms.contract.utils.Page;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;

/**
 * Created by wangxiaomei02 on 2017/5/19.
 */
public class MessageRecordRequest {

    private Integer businessId;

    private String messageId;

    @JsonProperty("mobile")
    private String mobile;

    @JsonProperty("status")
    private Integer status;

    @JsonProperty("messageKind")
    private Integer messageKind;

    @JsonProperty("providerId")
    private Integer providerId;

    @JsonProperty("beginTime")
    private Timestamp beginTime;

    @JsonProperty("endTime")
    private Timestamp endTime;

    private Page page;

    private Integer messageType;

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    @ApiModelProperty(value = "消息类型")
    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    @ApiModelProperty(value = "业务类型")
    public Integer getMessageKind() {
        return messageKind;
    }

    public void setMessageKind(Integer messageKind) {
        this.messageKind = messageKind;
    }

    @ApiModelProperty(value = "开始时间")
    public Timestamp getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Timestamp beginTime) {
        this.beginTime = beginTime;
    }

    @ApiModelProperty(value = "结束时间")
    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    @ApiModelProperty(value = "手机号")
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @ApiModelProperty(value = "服务商")
    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }

    @ApiModelProperty(value = "状态")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
