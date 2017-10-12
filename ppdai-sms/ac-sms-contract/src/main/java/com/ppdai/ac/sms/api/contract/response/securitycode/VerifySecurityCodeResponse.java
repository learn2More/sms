package com.ppdai.ac.sms.api.contract.response.securitycode;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by kiekiyang on 2017/4/24.
 */
public class VerifySecurityCodeResponse {
    @JsonProperty("ResultCode")
    private int resultCode;

    @JsonProperty("ResultMessage")
    private String resultMessage;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VerifySecurityCodeResponse that = (VerifySecurityCodeResponse) o;

        if (resultCode != that.resultCode) return false;
        return resultMessage != null ? resultMessage.equals(that.resultMessage) : that.resultMessage == null;
    }

    @Override
    public int hashCode() {
        int result = resultCode;
        result = 31 * result + (resultMessage != null ? resultMessage.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "VerifySecurityCodeResponse{" +
                "resultCode=" + resultCode +
                ", resultMessage='" + resultMessage + '\'' +
                '}';
    }
}
