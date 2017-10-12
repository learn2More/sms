package com.ppdai.ac.sms.contract.request.routeconfiguration;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by wangxiaomei02 on 2017/5/19.
 */

public class RouteConfigurationRequest {
    @JsonProperty("businessId")
    private Integer businessId;

    @JsonProperty("messageKind")
    private Integer messageKind;

    @JsonProperty("providerId")
    private Integer providerId;

    @JsonProperty("weight")
    private Integer weight;

    @JsonProperty("line")
    private Integer line;


    @ApiModelProperty(value = "服务商编号")
    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }

    @ApiModelProperty(value = "权重")
    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    @ApiModelProperty(value = "业务编号")
    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }


    @ApiModelProperty(value = "消息类型")
    public Integer getMessageKind() {
        return messageKind;
    }

    public void setMessageKind(Integer messageKind) {
        this.messageKind = messageKind;
    }

    @ApiModelProperty(value = "线路")
    public Integer getLine() {
        return line;
    }

    public void setLine(Integer line) {
        this.line = line;
    }
}
