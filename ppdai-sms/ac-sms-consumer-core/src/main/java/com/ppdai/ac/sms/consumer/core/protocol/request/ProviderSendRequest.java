package com.ppdai.ac.sms.consumer.core.protocol.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * Created by kiekiyang on 2017/5/9.
 */
public class ProviderSendRequest {
    @JsonProperty("params")
    private Map<String, String> params;

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }
}
