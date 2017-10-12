package com.ppdai.ac.sms.api.provider.jslt.protocal.response;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * author cash
 * create 2017-06-30-15:25
 **/

@XmlRootElement(name="errorstatus")
public class ErrorDeliverDetail {
    private String error;

    private String remark;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "ErrorDeliverDetail{" +
                "error=" + error +
                ",remark=" + remark  + '\'' +
                '}';
    }

    public ErrorDeliverDetail() {
        super();
    }
}
