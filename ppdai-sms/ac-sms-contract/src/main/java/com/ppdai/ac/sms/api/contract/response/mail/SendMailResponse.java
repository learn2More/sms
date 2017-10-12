package com.ppdai.ac.sms.api.contract.response.mail;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by kiekiyang on 2017/4/10.
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-03-10T08:26:37.517Z")
public class SendMailResponse {
    @JsonProperty("ResultCode")
    private int resultCode;

    @JsonProperty("ResultMessage")
    private String resultMessage;

    @JsonProperty("ResultObject")
    private Object resultObject;

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

    @ApiModelProperty(value = "返回对象")
    public Object getResultObject() {
        return resultObject;
    }

    public void setResultObject(Object resultObject) {
        this.resultObject = resultObject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SendMailResponse response = (SendMailResponse) o;

        if (resultCode != response.resultCode) return false;
        if (resultMessage != null ? !resultMessage.equals(response.resultMessage) : response.resultMessage != null)
            return false;
        if (resultObject != null ? !resultObject.equals(response.resultObject) : response.resultObject != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = resultCode;
        result = 31 * result + (resultMessage != null ? resultMessage.hashCode() : 0);
        result = 31 * result + (resultObject != null ? resultObject.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SendSmsResponse{" +
                "resultCode=" + resultCode +
                ", resultMessage='" + resultMessage + '\'' +
                ", resultObject=" + resultObject +
                '}';
    }
}
