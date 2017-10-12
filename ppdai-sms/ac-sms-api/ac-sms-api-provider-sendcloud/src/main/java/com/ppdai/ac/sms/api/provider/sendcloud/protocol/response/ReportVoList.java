package com.ppdai.ac.sms.api.provider.sendcloud.protocol.response;

/**
 * Created by zhongyunrui on 2017/7/24.
 * 回执报告结果
 */
public class ReportVoList {
    /**
     * 调用api发送邮件成功返回的emailId
     */
    private  String emailId;
    /**
     * 投递状态
     */
    private String  status;
    /**
     * apiUser名称
     */
    private  String  apiUser;
    /**
     * 收件人地址
     */
    private String  recipients;
    /**
     * 请求时间
     */
    private  String  requestTime;
    /**
     * 状态更新时间
     */
    private  String  modifiedTime;
    /**
     * 发送日志
     */
    private  String  sendLog;

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getApiUser() {
        return apiUser;
    }

    public void setApiUser(String apiUser) {
        this.apiUser = apiUser;
    }

    public String getRecipients() {
        return recipients;
    }

    public void setRecipients(String recipients) {
        this.recipients = recipients;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public String getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(String modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getSendLog() {
        return sendLog;
    }

    public void setSendLog(String sendLog) {
        this.sendLog = sendLog;
    }
}
