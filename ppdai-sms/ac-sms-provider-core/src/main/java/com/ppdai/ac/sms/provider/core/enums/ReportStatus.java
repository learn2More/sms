package com.ppdai.ac.sms.provider.core.enums;

/**
 * 回执报告状态
 * author cash
 * create 2017-05-10-15:32
 **/

public enum ReportStatus {

    SUCCESS_REPORT(0,"成功转态"),
    ERROR_REPORT(-1,"失败状态");

    private int code;
    private String describe;

    public int getCode() {
        return code;
    }

    public String getDescribe() {
        return describe;
    }

    ReportStatus(int code, String describe) {
        this.code = code;
        this.describe = describe;
    }
}
