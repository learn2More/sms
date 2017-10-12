package com.ppdai.ac.sms.api.provider.sendcloud.protocol.response;

import java.util.List;

/**
 * Created by zhongyunrui on 2017/7/25.
 * 回执报告结果
 */
public class ReportResponseInfo {
    private  int total;
    private  int voListSize;
    private List<ReportVoList>  voList;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getVoListSize() {
        return voListSize;
    }

    public void setVoListSize(int voListSize) {
        this.voListSize = voListSize;
    }

    public List<ReportVoList> getVoList() {
        return voList;
    }

    public void setVoList(List<ReportVoList> voList) {
        this.voList = voList;
    }
}
