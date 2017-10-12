package com.ppdai.ac.sms.contract.enums;

/**
 * Created by kiekiyang on 2017/4/25.
 */
public enum InvokeResult {
    SUCCESS(0, "提交成功"),
    SELECT_SUCCESS(00, "查询成功"),
    VERIFY_ERROR(-7001,"验证失败"),
    FAIL(-999, "提交失败"),
    SYS_ERROR(-1, "系统异常"),
    PARAM_ERROR(-1001, "参数传递错误"),
    PARAM_APPLICANT_ERROR(-1002, "模板申请人和审批人不能相同"),
    PARAM_RESTRICTCOUNTBYDAY_ERROR(-1003, "限制条数不能小于1条，或大于100条"),
    BLACK_ERROR(-2001, "黑名单"),
    MOBILE_EXISTS(-2002,"手机号已存在"),
    TEMPLATE_ERROR(-3001, "模板信息错误"),
    BUSINESS_ERROR(-4001, "业务信息错误"),
    TEMPLATE_BUSINESS_ERROR(-4002, "模板与业务关联不匹配"),
    CALLER_BUSINESS_ERROR(-4003,"业务方与业务关联不匹配"),
    MESSAGETYPE_BUSINESS_ERROR(-4004,"消息类型和业务部匹配");


    private int code;
    private String message;

    private InvokeResult(int code, String message) {
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
