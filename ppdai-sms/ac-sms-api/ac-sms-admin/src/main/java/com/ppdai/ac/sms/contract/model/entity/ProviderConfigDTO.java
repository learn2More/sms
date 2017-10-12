package com.ppdai.ac.sms.contract.model.entity;

import java.sql.Timestamp;

/**
 * Created by wangxiaomei02 on 2017/6/26.
 */
public class ProviderConfigDTO {
    private int providerId;
    private String configKey;
    private String configValue;
    private Timestamp updateTime;

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
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
}
