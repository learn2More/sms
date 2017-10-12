package com.ppdai.ac.sms.api.gateway.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by kiekiyang on 2017/5/18.
 */
public class QueryProviderConfigResponse {
    @JsonProperty("ResultCode")
    private int resultCode;

    @JsonProperty("ResultMessage")
    private String resultMessage;

    @JsonProperty("ResultObject")
    private List<ProviderConfig> resultObject;

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
    public List<ProviderConfig> getResultObject() {
        return resultObject;
    }

    public void setResultObject(List<ProviderConfig> resultObject) {
        this.resultObject = resultObject;
    }
}
