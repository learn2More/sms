package com.ppdai.ac.sms.contract.model.entity;

import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;

/**
 * Created by wangxiaomei02 on 2017/6/2.
 */
public class MessageBusinessDTO {
    private  int businessId;
    private String businessName;
    private String businessAlias;
    private int messageType;
    private int totalMaxCount;
    private int verifyMaxCount;
    private int expireSecond;
    private Timestamp updateTime;

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @ApiModelProperty(value = "业务ID")
    public int getBusinessId() {
        return businessId;
    }

    public void setBusinessId(int businessId) {
        this.businessId = businessId;
    }

    @ApiModelProperty(value = "最多验证次数（验证码）")
    public int getVerifyMaxCount() {
        return verifyMaxCount;
    }

    public void setVerifyMaxCount(int verifyMaxCount) {
        this.verifyMaxCount = verifyMaxCount;
    }

    @ApiModelProperty(value = "验证码过期时间(s)")
    public int getExpireSecond() {
        return expireSecond;
    }

    public void setExpireSecond(int expireSecond) {
        this.expireSecond = expireSecond;
    }

    @ApiModelProperty(value = "业务别名")
    public String getBusinessAlias() {
        return businessAlias;
    }

    public void setBusinessAlias(String businessAlias) {
        this.businessAlias = businessAlias;
    }

    @ApiModelProperty(value = "业务名称")
    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }


    @ApiModelProperty(value = "业务类型1：短信 2：语音 3：邮件',")
    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }


    @ApiModelProperty(value = "一天总体发送次数")
    public int getTotalMaxCount() {
        return totalMaxCount;
    }

    public void setTotalMaxCount(int totalMaxCount) {
        this.totalMaxCount = totalMaxCount;
    }
}
