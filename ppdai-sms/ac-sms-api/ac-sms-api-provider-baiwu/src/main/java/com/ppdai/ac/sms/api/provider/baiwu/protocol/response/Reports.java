package com.ppdai.ac.sms.api.provider.baiwu.protocol.response;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * 百悟回执报告
 * author cash
 * create 2017-04-26-9:51
 **/
@XmlRootElement(name = "reports")
public class Reports {

    private String code;

    private List<ReportDetail> reports;

    @XmlElements(value={@XmlElement(name="report", type=ReportDetail.class)})
    public List<ReportDetail> getReports() {
        return reports;
    }

    public void setReports(List<ReportDetail> reports) {
        this.reports = reports;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
