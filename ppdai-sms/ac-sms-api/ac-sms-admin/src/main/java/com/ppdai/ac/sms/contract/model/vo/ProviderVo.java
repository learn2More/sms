package com.ppdai.ac.sms.contract.model.vo;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by wangxiaomei02 on 2017/6/7.
 */
public class ProviderVo {
    private int providerId;

    private String providerName;

    private int messageKind;

    private int messageType;

    private int providerProtocol;

    private List<ProviderConfigVo> configList;

    private Timestamp insertTime;

    private Timestamp updateTime;

    public List<ProviderConfigVo> getConfigList() {
        return configList;
    }

    public void setConfigList(List<ProviderConfigVo> configList) {
        this.configList = configList;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Timestamp getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Timestamp insertTime) {
        this.insertTime = insertTime;
    }

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public int getMessageKind() {
        return messageKind;
    }

    public void setMessageKind(int messageKind) {
        this.messageKind = messageKind;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public int getProviderProtocol() {
        return providerProtocol;
    }

    public void setProviderProtocol(int providerProtocol) {
        this.providerProtocol = providerProtocol;
    }
}
