package com.ppdai.ac.sms.api.provider.baiwu.protocol.response;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 百悟回执报告
 * author cash
 * create 2017-04-26-9:51
 **/

@XmlRootElement(name="report")
public class ReportDetail  {

    private String corp_id;

    private String mobile;

    private String sub_seq;

    private String msg_id;

    private String err;

    private String fail_desc;

    private String report_time;

    public String getCorp_id() {
        return corp_id;
    }

    public void setCorp_id(String corp_id) {
        this.corp_id = corp_id;
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

    @Override
    public String toString() {
        return "ReportDetail{" +
                "corp_id=" + corp_id +
                ",mobile=" + mobile +
                ",sub_seq=" + sub_seq +
                ",msg_id=" + msg_id +
                ",err=" + err +
                ",fail_desc=" + fail_desc +
                ", report_time='" + report_time + '\'' +
                '}';
    }

}
