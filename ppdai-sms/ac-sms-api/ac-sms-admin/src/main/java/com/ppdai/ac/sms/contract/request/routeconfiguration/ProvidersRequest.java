package com.ppdai.ac.sms.contract.request.routeconfiguration;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by wangxiaomei02 on 2017/6/14.
 */
public class ProvidersRequest {

    @JsonProperty("providerId")
    private  Integer providerId;

    @JsonProperty("weight")
    private Integer weight;

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
}
