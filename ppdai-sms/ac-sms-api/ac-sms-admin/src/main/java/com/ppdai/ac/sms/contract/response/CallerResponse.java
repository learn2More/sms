package com.ppdai.ac.sms.contract.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by wangxiaomei02 on 2017/5/12.
 * 接入方
 */
public class CallerResponse {
    @JsonProperty("code")
    private int code;

    @JsonProperty("message")
    private String message;

    @JsonProperty("result")
    private Object result;

    @ApiModelProperty(value = "返回值")
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
    @ApiModelProperty(value = "返回消息")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    @ApiModelProperty(value = "返回对象")
    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
