package com.ppdai.ac.sms.contract.request.caller;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by wangxiaomei02 on 2017/5/16.
 */
public class CallerRequest {

    @JsonProperty("callerName")
    private String callerName;

    @JsonProperty("ipList")
    private String ipList;

    @ApiModelProperty(value = "接入方名称")
    public String getCallerName() {
        return callerName;
    }

    public void setCallerName(String callerName) {
        this.callerName = callerName;
    }
    @ApiModelProperty(value = "白名单列表")
    public String getIpList() {
        return ipList;
    }

    public void setIpList(String ipList) {
        this.ipList = ipList;
    }

}
