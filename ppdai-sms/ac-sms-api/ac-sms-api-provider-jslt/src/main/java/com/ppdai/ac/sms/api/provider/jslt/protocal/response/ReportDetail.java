package com.ppdai.ac.sms.api.provider.jslt.protocal.response;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * author cash
 * create 2017-06-30-15:25
 **/

@XmlRootElement(name="statusbox")
public class ReportDetail {
    private String mobile;

    private String taskid;

    private String status;

    private String receivetime;

    private String errorcode;

    private String extno;


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReceivetime() {
        return receivetime;
    }

    public void setReceivetime(String receivetime) {
        this.receivetime = receivetime;
    }

    public String getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(String errorcode) {
        this.errorcode = errorcode;
    }

    public String getExtno() {
        return extno;
    }

    public void setExtno(String extno) {
        this.extno = extno;
    }



    @Override
    public String toString() {
        return "ReportDetail{" +
                "taskid=" + taskid +
                ",mobile=" + mobile +
                ",status=" + status +
                ",receivetime=" + receivetime +
                ",errorcode=" + errorcode +
                ",extno=" + extno +
                '}';
    }
}
