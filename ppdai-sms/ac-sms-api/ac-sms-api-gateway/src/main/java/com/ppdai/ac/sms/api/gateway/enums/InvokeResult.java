package com.ppdai.ac.sms.api.gateway.enums;

/**
 * Created by kiekiyang on 2017/4/25.
 */
public enum InvokeResult {
    SUCCESS(0, "提交成功"),
    VERIFY_ERROR(-7001, "验证失败"),
    FREQUENCY_ERROR(-7002, "超过最大校验频次"),
    FAIL(-999, "提交失败"),
    SYS_ERROR(-1, "系统异常"),
    PARAM_ERROR(-1001, "参数传递错误"),
    MOBILE_ERROR(-1002, "发送号码错误"),
    BLACK_ERROR(-2001, "黑名单"),
    TEMPLATE_ERROR(-3001, "模板信息错误"),
    BUSINESS_ERROR(-4001, "业务信息错误"),
    TEMPLATE_BUSINESS_ERROR(-4002, "模板与业务关联不匹配"),
    CALLER_BUSINESS_ERROR(-4003, "业务方与业务关联不匹配"),
    MESSAGETYPE_BUSINESS_ERROR(-4004, "消息类型和业务不匹配"),
    BUSINESS_FREQUENTLY_ERROR(-5001, "请不要频繁发送该类型的短信"),
    TEMPLATE_FREQUENTLY_ERROR(-9001, "请不要频繁发送此短信"),
    CONTENTSIZE_ERROR(-6001, "短信内容超长"),
    SENSITIVEWORD_FORBIDEN(-8001,"提交内容中包含敏感词，被系统自动拦截"),
    PARAM_NUM_ERROR(-10001,"模板参数个数错误"),
    MOBILE_AND_PARAM_NUM_NOT_MATCH_ERROR(-10002,"手机号、参数个数不匹配"),
    TOO_MANY_MOBILE_ERROR(-10003,"发送手机数超过上限"),
    EXEC_ROWS_ERROR(-7003, "执行条数为0"),
    NUM_SEGMENT_ERROR(-7004, "发送号码不在配置号段内");

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
