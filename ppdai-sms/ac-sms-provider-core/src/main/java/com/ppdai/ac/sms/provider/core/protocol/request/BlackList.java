package com.ppdai.ac.sms.provider.core.protocol.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by kiekiyang on 2017/5/18.
 */
public class BlackList {
    @JsonProperty("Feature")
    private String features;
    @JsonProperty("Remark")
    private String remark;

    @ApiModelProperty(value = "黑名单特征")
    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    @ApiModelProperty(value = "黑名单描述")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
