package com.ppdai.ac.sms.api.provider.techown.protocal.response;

import java.util.List;

/**
 * author cash
 * create 2017-07-13-13:56
 **/

public class TcGetReportResponse {

    private boolean success;

    private List<TcReportResponse> data;

    private Integer r;//错误码

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<TcReportResponse> getData() {
        return data;
    }

    public void setData(List<TcReportResponse> data) {
        this.data = data;
    }

    public Integer getR() {
        return r;
    }

    public void setR(Integer r) {
        this.r = r;
    }
}
