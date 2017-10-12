package com.ppdai.ac.sms.api.gateway.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Created by kiekiyang on 2017/5/18.
 */
public class ProviderConfig {
    @JsonProperty("ConfigId")
    private int configId;
    @JsonProperty("ProviderId")
    private int providerId;
    @JsonProperty("ProviderName")
    private String providerName;
    @JsonProperty("ConfigKey")
    private String configKey;
    @JsonProperty("ConfigValue")
    private String configValue;
    @JsonProperty("IsActive")
    private boolean isActive;
    @JsonProperty("InsertTime")
    private LocalDateTime insertTime;
    @JsonProperty("UpdateTime")
    private LocalDateTime updateTime;

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public int getConfigId() {
        return configId;
    }

    public void setConfigId(int configId) {
        this.configId = configId;
    }

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }

    public String getConfigKey() {
        return configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public LocalDateTime getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(LocalDateTime insertTime) {
        this.insertTime = insertTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}
