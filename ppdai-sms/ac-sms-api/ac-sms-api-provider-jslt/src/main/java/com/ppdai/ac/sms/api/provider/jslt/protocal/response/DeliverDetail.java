package com.ppdai.ac.sms.api.provider.jslt.protocal.response;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * author cash
 * create 2017-06-30-15:25
 **/

@XmlRootElement(name="callbox")
public class DeliverDetail {
    private String mobile;

    private String taskid;

    private String content;

    private String receivetime;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReceivetime() {
        return receivetime;
    }

    public void setReceivetime(String receivetime) {
        this.receivetime = receivetime;
    }

    public String getExtno() {
        return extno;
    }

    public void setExtno(String extno) {
        this.extno = extno;
    }

    @Override
    public String toString() {
        return "DeliverDetail{" +
                "mobile=" + mobile +
                ",taskid=" + taskid +
                ",content=" + content +
                ",receivetime=" + receivetime +
                ", extno='" + extno + '\'' +
                '}';
    }

    public DeliverDetail() {
        super();
    }
}
