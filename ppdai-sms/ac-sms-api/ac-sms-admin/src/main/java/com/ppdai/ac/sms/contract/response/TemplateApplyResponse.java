package com.ppdai.ac.sms.contract.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by wangxiaomei02 on 2017/5/9.
 * 模板申请
 */
public class TemplateApplyResponse {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TemplateApplyResponse response = (TemplateApplyResponse) o;

        if (code != response.code) return false;
        if (message != null ? !message.equals(response.message) : response.message != null)
            return false;
        if (result != null ? !result.equals(response.result) : response.result != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int results = code;
        results = 31 * results + (message != null ? message.hashCode() : 0);
        results = 31 * results + (result != null ? result.hashCode() : 0);
        return results;
    }

    @Override
    public String toString() {
        return "TemplateApplyResponse{" +
                "resultCode=" + code +
                ", resultMessage='" + message + '\'' +
                ", resultObject=" + result +
                '}';
    }
}
