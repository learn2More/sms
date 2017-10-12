package com.ppdai.ac.sms.contract.request.messagebusiness;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by wangxiaomei02 on 2017/5/25.
 */
public class MessageBusinessRequest {
    private String businessName;
    private String businessAlias;
    private Integer messageType;
    private Integer totalMaxCount;
    private Integer verifyMaxCount;
    private Integer expireSecond;


    @ApiModelProperty(value = "最多验证次数（验证码）")
    public Integer getVerifyMaxCount() {
        return verifyMaxCount;
    }

    public void setVerifyMaxCount(Integer verifyMaxCount) {
        this.verifyMaxCount = verifyMaxCount;
    }

    @ApiModelProperty(value = "验证码过期时间(s)")
    public Integer getExpireSecond() {
        return expireSecond;
    }

    public void setExpireSecond(Integer expireSecond) {
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
    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }


    @ApiModelProperty(value = "一天总体发送次数")
    public Integer getTotalMaxCount() {
        return totalMaxCount;
    }

    public void setTotalMaxCount(Integer totalMaxCount) {
        this.totalMaxCount = totalMaxCount;
    }

}
