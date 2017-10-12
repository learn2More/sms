package com.ppdai.ac.sms.api.provider.baiwu.protocol.response;

/**
 * 上行报告json格式
 * author cash
 * create 2017-06-22-16:04
 **/

public class JDeliverDetail {

    String user_id;
    String mobile;
    String ext;//上行业务扩展号码
    String deliver_time;//网关接收到客户上行信息的时间
    String content;
    String pk_total;//值大于1时，表示上行的信息为长短信，该值为单条上行信息为拆分的总长度
    String pk_number;//上行信息的长短信的第N条
    String sub_msg_id;//同一条上行长短信该值相同


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getDeliver_time() {
        return deliver_time;
    }

    public void setDeliver_time(String deliver_time) {
        this.deliver_time = deliver_time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPk_total() {
        return pk_total;
    }

    public void setPk_total(String pk_total) {
        this.pk_total = pk_total;
    }

    public String getPk_number() {
        return pk_number;
    }

    public void setPk_number(String pk_number) {
        this.pk_number = pk_number;
    }

    public String getSub_msg_id() {
        return sub_msg_id;
    }

    public void setSub_msg_id(String sub_msg_id) {
        this.sub_msg_id = sub_msg_id;
    }
}
