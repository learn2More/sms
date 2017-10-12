package com.ppdai.ac.sms.api.provider.sendcloud.protocol.response;

/**
 * Created by zhongyunrui on 2017/7/25.
 * 回执报告结果
 */
public class ReportResponseData {
    private boolean result;
    private int statusCode;
    private String message;
    private  ReportResponseInfo info;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ReportResponseInfo getInfo() {
        return info;
    }

    public void setInfo(ReportResponseInfo info) {
        this.info = info;
    }
}
