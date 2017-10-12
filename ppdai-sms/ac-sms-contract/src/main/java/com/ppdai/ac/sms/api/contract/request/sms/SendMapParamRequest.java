package com.ppdai.ac.sms.api.contract.request.sms;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ppdai.ac.sms.api.contract.request.BaseRequest;
import io.swagger.annotations.ApiModelProperty;

import java.util.Map;

/**
 * author cash
 * create 2017-07-21-9:41
 **/
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-03-10T08:26:37.517Z")
public class SendMapParamRequest extends BaseRequest  {

    @JsonProperty("BusinessAlias")
    private String businessAlias;

    @JsonProperty("TemplateAlias")
    private String templateAlias;

    @JsonProperty("Mobile")
    private String mobile;

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

    @ApiModelProperty(value = "手机号")
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @ApiModelProperty(value = "模板参数,Map<String,String>")
    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }


    @ApiModelProperty(value = "发送验证码时的唯一标识,可以是uuid,校验时传入相同")
    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }
}
