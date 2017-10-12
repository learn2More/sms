package com.ppdai.ac.sms.api.contract.request.securitycode;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ppdai.ac.sms.api.contract.request.BaseRequest;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by kiekiyang on 2017/4/24.
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-03-10T08:26:37.517Z")

public class VerifySecurityCodeRequest extends BaseRequest {

    @JsonProperty("Recipient")
    private String recipient;

    @JsonProperty("InputCode")
    private String inputCode;

    @JsonProperty("VerifyCodeBusinessAlias")
    private String verifyCodeBusinessAlias;


    @JsonProperty("OnlyCheck")
    private boolean onlyCheck;

    @JsonProperty("TokenId")
    private String tokenId;

    @ApiModelProperty(value = "接收者")
    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    @ApiModelProperty(value = "待校验Code信息")
    public String getInputCode() {
        return inputCode;
    }

    public void setInputCode(String inputCode) {
        this.inputCode = inputCode;
    }

    public String getVerifyCodeBusinessAlias() {
        return verifyCodeBusinessAlias;
    }

    public void setVerifyCodeBusinessAlias(String verifyCodeBusinessAlias) {
        this.verifyCodeBusinessAlias = verifyCodeBusinessAlias;
    }

    public boolean isOnlyCheck() {
        return onlyCheck;
    }

    @ApiModelProperty(value = "校验验证码唯一令牌")
    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    @ApiModelProperty(value = "是否只做校验不做删除")
    public boolean getOnlyCheck() {
        return onlyCheck;
    }

    public void setOnlyCheck(boolean onlyCheck) {
        this.onlyCheck = onlyCheck;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        VerifySecurityCodeRequest that = (VerifySecurityCodeRequest) o;

        if (onlyCheck != that.onlyCheck) return false;
        if (recipient != null ? !recipient.equals(that.recipient) : that.recipient != null) return false;
        if (inputCode != null ? !inputCode.equals(that.inputCode) : that.inputCode != null) return false;
        if (verifyCodeBusinessAlias != null ? !verifyCodeBusinessAlias.equals(that.verifyCodeBusinessAlias) : that.verifyCodeBusinessAlias != null)
            return false;
        if (tokenId != null ? !tokenId.equals(that.tokenId) : that.tokenId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (recipient != null ? recipient.hashCode() : 0);
        result = 31 * result + (inputCode != null ? inputCode.hashCode() : 0);
        result = 31 * result + (verifyCodeBusinessAlias != null ? verifyCodeBusinessAlias.hashCode() : 0);
        result = 31 * result + (onlyCheck ? 1 : 0);
        result = 31 * result + (tokenId != null ? tokenId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "VerifySecurityCodeRequest{" +
                "recipient='" + recipient + '\'' +
                ", inputCode='" + inputCode + '\'' +
                ", verifyCodeBusinessAlias='" + verifyCodeBusinessAlias + '\'' +
                ", onlyCheck=" + onlyCheck +
                ", tokenId='" + tokenId + '\'' +
                '}';
    }
}
