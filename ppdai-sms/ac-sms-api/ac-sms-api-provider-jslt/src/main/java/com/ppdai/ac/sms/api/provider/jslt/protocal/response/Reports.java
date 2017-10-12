package com.ppdai.ac.sms.api.provider.jslt.protocal.response;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * author cash
 * create 2017-06-30-15:25
 **/

@XmlRootElement(name="returnsms")
public class Reports {

    private List<ReportDetail> reports;

    private List<ErrorReportDetail> errorReports;

    @XmlElements(value={@XmlElement(name="statusbox", type=ReportDetail.class)})
    public List<ReportDetail> getReports() {
        return reports;
    }

    public void setReports(List<ReportDetail> reports) {
        this.reports = reports;
    }

    @XmlElements(value={@XmlElement(name="errorstatus", type=ErrorReportDetail.class)})
    public List<ErrorReportDetail> getErrorReports() {
        return errorReports;
    }

    public void setErrorReports(List<ErrorReportDetail> errorReports) {
        this.errorReports = errorReports;
    }
}
