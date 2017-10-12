package com.ppdai.ac.sms.contract.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by wangxiaomei02 on 2017/5/12.
 */
public class CallerVo {
    @JsonProperty("callerId")
    private int callerId;
    @JsonProperty("callerName")
    private String callerName;
    @JsonProperty("ipList")
    private String ipList;
    @JsonProperty("isActive")
    private String isActive;
    @JsonProperty("insertTime")
    private Timestamp insertTime;
    @JsonProperty("updateTime")
    private Timestamp updateTime;

    @ApiModelProperty(value = "接入方id")
    public int getCallerId() {
        return callerId;
    }

    public void setCallerId(int callerId) {
        this.callerId = callerId;
    }

    @ApiModelProperty(value = "接入方名称")
    public String getCallerName() {
        return callerName;
    }

    public void setCallerName(String callerName) {
        this.callerName = callerName;
    }

    @ApiModelProperty(value = "创建时间")
    public Timestamp getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Timestamp insertTime) {
        this.insertTime = insertTime;
    }

    @ApiModelProperty(value = "白名单列表")
    public String getIpList() {
        return ipList;
    }

    public void setIpList(String ipList) {
        this.ipList = ipList;
    }

    @ApiModelProperty(value = "是否有效")
    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    @ApiModelProperty(value = "更新时间")
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }
}
