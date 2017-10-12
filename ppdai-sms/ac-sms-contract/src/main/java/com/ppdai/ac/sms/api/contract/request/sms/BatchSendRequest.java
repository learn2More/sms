package com.ppdai.ac.sms.api.contract.request.sms;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 批量发送入参
 * author cash
 * create 2017-07-31-16:39
 **/
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-03-10T08:26:37.517Z")
public class BatchSendRequest{

    private String businessAlias;

    private String templateAlias;

    private List<String> mobiles;

    private List<String> parameters;

    private String notifyEmail;

    private Long timing;

    private int callerId;

    private String callerIp;

    private String recipientIp;

    private String directory;

    private String hostName;

    private String requestUrl;


    private String userId;


    @ApiModelProperty(value = "用户id")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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
