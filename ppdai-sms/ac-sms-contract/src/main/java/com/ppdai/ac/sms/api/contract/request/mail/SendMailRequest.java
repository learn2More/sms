package com.ppdai.ac.sms.api.contract.request.mail;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ppdai.ac.sms.api.contract.request.BaseRequest;
import io.swagger.annotations.ApiModelProperty;

import java.util.Map;

/**
 * Created by kiekiyang on 2017/4/10.
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-03-10T08:26:37.517Z")

public class SendMailRequest extends BaseRequest {
    @JsonProperty("BusinessAlias")
    private String businessAlias;

    @JsonProperty("TemplateAlias")
    private String templateAlias;

    @JsonProperty("Email")
    private String email;

    @JsonProperty("Parameters")
    private Map<String,String> parameters;

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

    @ApiModelProperty(value = "邮箱")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @ApiModelProperty(value = "模板动态参数，键值对")
    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    @ApiModelProperty(value = "发送邮箱验证码时的唯一标识,可以是uuid,校验时传入相同")
    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

}
