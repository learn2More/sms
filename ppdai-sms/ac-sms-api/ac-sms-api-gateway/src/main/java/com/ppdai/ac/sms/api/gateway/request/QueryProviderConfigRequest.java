package com.ppdai.ac.sms.api.gateway.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by kiekiyang on 2017/5/18.
 */
public class QueryProviderConfigRequest {
    @JsonProperty("ProviderId")
    private int providerId;

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }
}
