package com.ppdai.ac.sms.api.provider.xuanwu.response;

import com.esms.common.entity.MTReport;

import java.util.List;

/**
 * 回执report
 * author cash
 * create 2017-05-04-19:02
 **/

public class ReportsResponse extends AbstractResponse {

    private List<MTReport> listReport;

    public List<MTReport> getListReport() {
        return listReport;
    }

    public void setListReport(List<MTReport> listReport) {
        this.listReport = listReport;
    }
}
