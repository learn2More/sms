package com.ppdai.ac.sms.api.provider.baiwu.protocol.response;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 百悟上行报告
 * author cash
 * create 2017-04-26-10:17
 **/
@XmlRootElement(name="deliver")
public class DeliverDetail {

    private String corp_id;

    private String mobile;

    private String ext;

    private String time;

    private String content;

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

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "DeliverDetail{" +
                "corp_id=" + corp_id +
                ",mobile=" + mobile +
                ",ext=" + ext +
                ",time=" + time +
                ", content='" + content + '\'' +
                '}';
    }

    public DeliverDetail() {
        super();
    }
}
