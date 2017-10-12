package com.ppdai.ac.sms.api.contract.request.voice;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ppdai.ac.sms.api.contract.request.BaseRequest;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by kiekiyang on 2017/4/10.
 */
public class SendVoiceRequest extends BaseRequest {

    @JsonProperty("BusinessAlias")
    private String businessAlias;

    @JsonProperty("TemplateAlias")
    private String templateAlias;

    @JsonProperty("Phone")
    private String phone;

    @JsonProperty("TokenId")
    private String tokenId;

    @ApiModelProperty(value = "业务别名")
    public String getBusinessAlias() {
        return businessAlias;
    }

    public void setBusinessAlias(String businessAlias) {
        this.businessAlias = businessAlias;
    }

    @ApiModelProperty(value = "模板别名,新模板需要先申请")
    public String getTemplateAlias() {
        return templateAlias;
    }

    public void setTemplateAlias(String templateAlias) {
        this.templateAlias = templateAlias;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @ApiModelProperty(value = "发送验证码时的唯一标识,校验时,传入相同")
    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        SendVoiceRequest that = (SendVoiceRequest) o;

        if (businessAlias != null ? !businessAlias.equals(that.businessAlias) : that.businessAlias != null)
            return false;
        if (templateAlias != null ? !templateAlias.equals(that.templateAlias) : that.templateAlias != null)
            return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        return tokenId != null ? tokenId.equals(that.tokenId) : that.tokenId == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (businessAlias != null ? businessAlias.hashCode() : 0);
        result = 31 * result + (templateAlias != null ? templateAlias.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (tokenId != null ? tokenId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SendVoiceRequest{" +
                "businessAlias='" + businessAlias + '\'' +
                ", templateAlias='" + templateAlias + '\'' +
                ", mobile='" + phone + '\'' +
                ", tokenId='" + tokenId + '\'' +
                '}';
    }
}
