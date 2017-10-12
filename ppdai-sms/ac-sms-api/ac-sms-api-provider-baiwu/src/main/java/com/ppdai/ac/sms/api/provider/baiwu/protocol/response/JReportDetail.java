package com.ppdai.ac.sms.api.provider.baiwu.protocol.response;

/**
 * 回执报告json格式
 * author cash
 * create 2017-06-22-16:03
 **/

public class JReportDetail {

    private String user_id;

    private String mobile;

    private String sub_seq;

    private String msg_id;

    private String err;

    private String fail_desc;

    private String report_time;

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

    public String getSub_seq() {
        return sub_seq;
    }

    public void setSub_seq(String sub_seq) {
        this.sub_seq = sub_seq;
    }

    public String getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(String msg_id) {
        this.msg_id = msg_id;
    }

    public String getErr() {
        return err;
    }

    public void setErr(String err) {
        this.err = err;
    }

    public String getFail_desc() {
        return fail_desc;
    }

    public void setFail_desc(String fail_desc) {
        this.fail_desc = fail_desc;
    }

    public String getReport_time() {
        return report_time;
    }

    public void setReport_time(String report_time) {
        this.report_time = report_time;
    }
}
