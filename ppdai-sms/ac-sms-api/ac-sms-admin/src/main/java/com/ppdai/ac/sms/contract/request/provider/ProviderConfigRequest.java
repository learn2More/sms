package com.ppdai.ac.sms.contract.request.provider;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by wangxiaomei02 on 2017/6/13.
 */
public class ProviderConfigRequest {

    @JsonProperty("configKey")
    private String configKey;

    @JsonProperty("configValue")
    private String configValue;

    @ApiModelProperty(value = "key值")
    public String getConfigKey() {
        return configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    @ApiModelProperty(value = "value值")
    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }
}

