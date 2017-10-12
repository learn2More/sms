package com.ppdai.ac.sms.api.provider.baiwu.domain.model;

/**
 * json格式上行返回状态码
 * author cash
 * create 2017-06-22-16:49
 **/

public enum JsonDeliverReturnCode {

    SUCCESS("1000","请求成功"),
    ILLEGAL_USER("1001","账户不存在或者已经关闭"),
    ILLEGAL_SIGN("1002","sign签名错误"),
    INVALID_SIGN("1003","sign签名已经过期"),
    ILLEGAL_IP("1004","非法IP访问"),
    TOO_HIGH_FREQUENCY("1005","访问频率过高(不低于200ms)"),
    NO_MESSAGE("1006","尚未有信息产生"),
    NO_PERMISSIONS("1007","没有使用该接口的权限");

    private String code;
    private String describe;

    public String getCode() {
        return code;
    }

    /*public void setCode(String code) {
        this.code = code;
    }*/

    public String getDescribe() {
        return describe;
    }

/*
    public void setDescribe(String describe) {
        this.describe = describe;
    }
*/

    JsonDeliverReturnCode(String code, String describe) {
        this.code = code;
        this.describe = describe;
    }

    public static String getDescribe(String code){
        for(BWReportReturnCode e:BWReportReturnCode.values()){
            if(e.getCode().equals(code)){
                return e.getDescribe();
            }
        }
        return "";
    }
}
