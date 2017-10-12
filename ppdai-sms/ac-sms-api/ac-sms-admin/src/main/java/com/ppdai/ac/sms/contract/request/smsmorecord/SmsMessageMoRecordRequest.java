package com.ppdai.ac.sms.contract.request.smsmorecord;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by wangxiaomei02 on 2017/5/19.
 */
public class SmsMessageMoRecordRequest {
    @JsonProperty("ID")
    private int id;

    @JsonProperty("Phone")
    private char phone;

    @JsonProperty("Provider")
    private int  provider;

    @JsonProperty("CreateTime")
    private Long createTime;

    @JsonProperty("EndTime")
    private Long endTime;

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public char getPhone() {
        return phone;
    }

    public void setPhone(char phone) {
        this.phone = phone;
    }

    public int getProvider() {
        return provider;
    }

    public void setProvider(int provider) {
        this.provider = provider;
    }
}
