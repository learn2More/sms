package com.ppdai.ac.sms.api.gateway.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by kiekiyang on 2017/5/18.
 */
public class QueryMessageByIdResponse {
    @JsonProperty("ResultCode")
    private int resultCode;

    @JsonProperty("ResultMessage")
    private String resultMessage;

    @JsonProperty("ResultObject")
    private SMSMessage resultObject;

    @ApiModelProperty(value = "返回值")
    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    @ApiModelProperty(value = "返回消息")
    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    @ApiModelProperty(value = "返回消息实体")
    public SMSMessage getResultObject() {
        return resultObject;
    }

    public void setResultObject(SMSMessage resultObject) {
        this.resultObject = resultObject;
    }
}