package com.ppdai.ac.sms.api.provider.sendcloud.domain.model;

/**
 * Created by zhongyunrui on 2017/7/24.
 */
public class GetReportsInput {
    private  String apiUser;  //是	API_USER
    private  String apiKey;  //是	API_KEY
    private  String  email; //否	收件人地址
    private  String  emailIds; //否	调用api发送邮件成功返回的emailId. 多个地址使用';'分隔,如:emailIds=a;b;c
    private  String  labelId;   //否	用户创建的标签对应的标签ID
    private  String  days;   //*	过去 days 天内的投递数据，(days=1表示今天),时间不超过30天
    private  String  startDate;  //* 开始日期, 格式为yyyy-MM-dd，和结束时间间隔不超过30天
    private  String endDate;  //*	结束日期, 格式为yyyy-MM-dd，和起始时间间隔不超过30天
    private  String  apiUserList;  //否	用户的多个apiUser. 多个apiUser使用';'分隔，如:apiUserList=a;b;c
    private  String start;  //否	查询起始位置, 取值区间 [0-], 默认为 0
    private  int  limit;  //否	查询个数, 取值区间 [0-100], 默认为 100

    public String getApiUser() {
        return apiUser;
    }

    public void setApiUser(String apiUser) {
        this.apiUser = apiUser;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailIds() {
        return emailIds;
    }

    public void setEmailIds(String emailIds) {
        this.emailIds = emailIds;
    }

    public String getLabelId() {
        return labelId;
    }

    public void setLabelId(String labelId) {
        this.labelId = labelId;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getApiUserList() {
        return apiUserList;
    }

    public void setApiUserList(String apiUserList) {
        this.apiUserList = apiUserList;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
