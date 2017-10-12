package com.ppdai.ac.sms.provider.core.enums;

/**
 * record状态枚举
 * author cash
 * create 2017-05-16-16:44
 **/

public enum RecordStatus {
    INIT_REPORT(0,"初始化"),
    SUBMIT_SUCCESS(1, "提交成功"),
    SEND_SUCCESS(2, "发送成功"),
    SUBMIT_FAIL(-1, "提交失败"),
    SEND_FAIL(-2, "发送失败");
    private int code;
    private String describe;

    public int getCode() {
        return code;
    }

    public String getDescribe() {
        return describe;
    }

    RecordStatus(int code, String describe) {
        this.code = code;
        this.describe = describe;
    }
}
