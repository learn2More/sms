package com.ppdai.ac.sms.api.contract.request;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by kiekiyang on 2017/4/10.
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-03-10T08:26:37.517Z")


public class BaseRequest {

//    @JsonProperty("IsVerifyCode")
//    private boolean isVerifyCode;

//    @JsonProperty("TokenId")
//    private String tokenId;

    @JsonProperty("CallerId")
    private int callerId;

    @JsonProperty("CallerIp")
    private String callerIp;

    @JsonProperty("RecipientIp")
    private String recipientIp;


    @JsonProperty("Directory")
    private String directory;

    @JsonProperty("HostName")
    private String hostName;

    @JsonProperty("RequestUrl")
    private String requestUrl;

//    @JsonProperty("Timestamp")
//    private long timestamp;
//
//    @ApiModelProperty(value = "是否校验验证码")
//    public boolean isVerifyCode() {
//        return isVerifyCode;
//    }
//
//    public void setVerifyCode(boolean verifyCode) {
//        isVerifyCode = verifyCode;
//    }

//    @ApiModelProperty(value = "Token信息，用于做流控")
//    public String getTokenId() {
//        return tokenId;
//    }
//
//    public void setTokenId(String tokenId) {
//        this.tokenId = tokenId;
//    }

    @ApiModelProperty(value = "调用方编号")
    public int getCallerId() {
        return callerId;
    }

    public void setCallerId(int callerId) {
        this.callerId = callerId;
    }

    @ApiModelProperty(value = "调用服务的ip")
    public String getCallerIp() {
        return callerIp;
    }

    public void setCallerIp(String callerIp) {
        this.callerIp = callerIp;
    }

    @ApiModelProperty(value = "消息接收方ip,可空")
    public String getRecipientIp() {
        return recipientIp;
    }

    public void setRecipientIp(String recipientIp) {
        this.recipientIp = recipientIp;
    }

    @ApiModelProperty(value = "调用服务的路径")
    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    @ApiModelProperty(value = "调用方主机名")
    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    @ApiModelProperty(value = "调用方当前Url信息")
    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }
}
