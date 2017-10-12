package com.ppdai.ac.sms.api.provider.ccp.response;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * author cash
 * create 2017-05-17-13:09
 **/

@XmlRootElement(name = "Response")
public class ReceiveReportResponse {
    private String statuscode;

    public String getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(String statuscode) {
        this.statuscode = statuscode;
    }
}
