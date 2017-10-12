package com.ppdai.ac.sms.contract.request.provider;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by wangxiaomei02 on 2017/6/29.
 */
public class ProviderInfoRequest {
    @JsonProperty("providerName")
    private String providerName;

    @JsonProperty("messageType")
    private Integer messageType;

    @JsonProperty("messageKind")
    private Integer messageKind;

    @JsonProperty("providerProtocol")
    private Integer providerProtocol;
    @ApiModelProperty(value = "消息类型 1：短信，2：语音，3：邮件")
    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    @ApiModelProperty(value = "业务类型 1：验证码\\n2：通知\\n3：营销\\n4：催收")
    public Integer getMessageKind() {
        return messageKind;
    }

    public void setMessageKind(Integer messageKind) {
        this.messageKind = messageKind;
    }

    @ApiModelProperty(value = "服务商名称")
    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    @ApiModelProperty(value = "供应商通信协议 1：HTTP\\n2：CMPP")
    public Integer getProviderProtocol() {
        return providerProtocol;
    }

    public void setProviderProtocol(Integer providerProtocol) {
        this.providerProtocol = providerProtocol;
    }


}
