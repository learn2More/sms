package com.ppdai.ac.sms.consumer.core.enums;

/**
 * Created by kiekiyang on 2017/4/25.
 */
public enum InvokeResult {
    SUCCESS(0, "提交成功"),
    VERIFY_ERROR(-7001,"验证失败"),
    FREQUENCY_ERROR(-7002,"超过最大校验频次"),
    FAIL(-999, "提交失败"),
    SYS_ERROR(-1, "系统异常"),
    PARAM_ERROR(-1001, "参数传递错误"),
    BLACK_ERROR(-2001, "黑名单"),
    TEMPLATE_ERROR(-3001, "模板信息错误"),
    BUSINESS_ERROR(-4001, "业务信息错误"),
    TEMPLATE_BUSINESS_ERROR(-4002, "模板与业务关联不匹配"),
    CALLER_BUSINESS_ERROR(-4003,"业务方与业务关联不匹配"),
    MESSAGETYPE_BUSINESS_ERROR(-4004,"消息类型和业务部匹配"),
    FREQUENTLY_ERROR(-5001, "发送过于频繁"),
    CONTENTSIZE_ERROR(-6001, "短信内容超长");

    private int code;
    private String message;

    InvokeResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
