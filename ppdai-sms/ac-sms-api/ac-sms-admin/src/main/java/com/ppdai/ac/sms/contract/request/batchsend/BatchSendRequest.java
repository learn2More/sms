package com.ppdai.ac.sms.contract.request.batchsend;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 *短信批量发送
 *author cash
 *create 2017/8/3-17:11
**/

public class BatchSendRequest {

    @JsonProperty("BusinessId")
    private Integer businessId;

    @JsonProperty("CallerId")
    private int callerId;

    @JsonProperty("TemplateAlias")
    private String templateAlias;

    @JsonProperty("Mobile")
    private List<String> mobiles;

    @JsonProperty("Parameters")
    private List<String> parameters;

    @JsonProperty("NotifyEmail")
    private String notifyEmail;

    @JsonProperty("Timing")
    private Long timing;

    @ApiModelProperty(value = "模板id")
    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    @ApiModelProperty(value = "接入方id")
    public int getCallerId() {
        return callerId;
    }

    public void setCallerId(int callerId) {
        this.callerId = callerId;
    }

    @ApiModelProperty(value = "模板别名,新模板需要先申请")
    public String getTemplateAlias() {
        return templateAlias;
    }

    public void setTemplateAlias(String templateAlias) {
        this.templateAlias = templateAlias;
    }

    @ApiModelProperty(value = "手机号list")
    public List<String> getMobiles() {
        return mobiles;
    }

    public void setMobiles(List<String> mobiles) {
        this.mobiles = mobiles;
    }


    @ApiModelProperty(value = "参数list,同一短信参数间用 | 连接")
    public List<String> getParameters() {
        return parameters;
    }

    public void setParameters(List<String> parameters) {
        this.parameters = parameters;
    }


    @ApiModelProperty(value = "通知邮箱")
    public String getNotifyEmail() {
        return notifyEmail;
    }

    public void setNotifyEmail(String notifyEmail) {
        this.notifyEmail = notifyEmail;
    }


    @ApiModelProperty(value = "定时发送时间,非定时,请设置为null或0")
    public Long getTiming() {
        return timing;
    }

    public void setTiming(Long timing) {
        this.timing = timing;
    }
}