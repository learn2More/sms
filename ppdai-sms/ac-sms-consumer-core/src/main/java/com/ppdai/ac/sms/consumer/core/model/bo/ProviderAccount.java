package com.ppdai.ac.sms.consumer.core.model.bo;

/**
 * Created by kiekiyang on 2017/5/5.
 */
public class ProviderAccount {
    private String configKey;
    private String configValue;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProviderAccount that = (ProviderAccount) o;

        if (configKey != null ? !configKey.equals(that.configKey) : that.configKey != null) return false;
        return configValue != null ? configValue.equals(that.configValue) : that.configValue == null;
    }

    @Override
    public int hashCode() {
        int result = configKey != null ? configKey.hashCode() : 0;
        result = 31 * result + (configValue != null ? configValue.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ProviderAccount{" +
                "configKey='" + configKey + '\'' +
                ", configValue='" + configValue + '\'' +
                '}';
    }
}
