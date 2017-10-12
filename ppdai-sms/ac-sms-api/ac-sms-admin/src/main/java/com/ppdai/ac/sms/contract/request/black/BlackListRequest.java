package com.ppdai.ac.sms.contract.request.black;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by wangxiaomei02 on 2017/5/19.
 */
public class BlackListRequest {
    @JsonProperty("mobile")
    private String mobile;

    @JsonProperty("remark")
    private String remark;

    @ApiModelProperty(value = "手机号")
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @ApiModelProperty(value = "原因")

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}