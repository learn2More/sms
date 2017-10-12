package com.ppdai.ac.sms.api.provider.jslt.domain.model;

/**
 * 江苏联通回执返回枚举
 * author cash
 * create 2017-05-15-11:25
 **/

public enum JSLTReportReturnCode {

    SUCCESS("1000","请求成功"),
    ACCOUNT_IS_CLOSED("-1001","用户名或密码不能为空"),
    WRONG_USERNAME("-1002","用户名或密码错误"),
    WRONG_PASSWORD("-1003","该用户不允许查看状态报告"),
    NOT_SUPPORT_TO_GET("-1004","参数不正确");

    private String code;
    private String describe;

    public String getCode() {
        return code;
    }

    public String getDescribe() {
        return describe;
    }

    JSLTReportReturnCode(String code, String describe) {
        this.code = code;
        this.describe = describe;
    }

    public static String getDescribet(String code){
        String describe="";
        for(JSLTReportReturnCode e:JSLTReportReturnCode.values()){
            if(e.getCode().equals(code)){
                describe=e.getDescribe();
            }
        }
        return describe;
    }
}
