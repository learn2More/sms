package com.ppdai.ac.sms.api.gateway.model.entity;

import java.sql.Timestamp;

/**
 * Created by kiekiyang on 2017/5/4.
 */
public class ProviderConfigDTO {
    private int configId;
    private int providerId;
    private String providerName;
    private String configKey;
    private String configValue;
    private boolean isActive;
    private Timestamp insertTime;
    private Timestamp updateTime;

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

    public Timestamp getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Timestamp insertTime) {
        this.insertTime = insertTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProviderConfigDTO that = (ProviderConfigDTO) o;

        if (configId != that.configId) return false;
        if (providerId != that.providerId) return false;
        if (isActive != that.isActive) return false;
        if (configKey != null ? !configKey.equals(that.configKey) : that.configKey != null) return false;
        if (configValue != null ? !configValue.equals(that.configValue) : that.configValue != null) return false;
        if (insertTime != null ? !insertTime.equals(that.insertTime) : that.insertTime != null) return false;
        return updateTime != null ? updateTime.equals(that.updateTime) : that.updateTime == null;
    }

    @Override
    public int hashCode() {
        int result = configId;
        result = 31 * result + providerId;
        result = 31 * result + (configKey != null ? configKey.hashCode() : 0);
        result = 31 * result + (configValue != null ? configValue.hashCode() : 0);
        result = 31 * result + (isActive ? 1 : 0);
        result = 31 * result + (insertTime != null ? insertTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ProviderConfigDTO{" +
                "configId=" + configId +
                ", providerId=" + providerId +
                ", providerName=" + providerName +
                ", configKey='" + configKey + '\'' +
                ", configValue='" + configValue + '\'' +
                ", isActive=" + isActive +
                ", insertTime=" + insertTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
