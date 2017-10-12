package com.ppdai.ac.sms.api.provider.jslt.domain.model;

/**
 * 江苏联通短信发送返回枚举
 * author cash
 * create 2017-05-15-11:31
 **/

public enum JSLTSendReturnCode {

    SUCCESS("1000","请求成功"),
    NO_MORE_DATE("0","暂时没有待推送的数据"),
    ACCOUNT_IS_CLOSED("-11","账户关闭"),
    WRONG_USERNAME("-16","用户名错误或用户名不存在"),
    WRONG_PASSWORD("-17","密码错误"),
    NOT_SUPPORT_TO_GET("-18","不支持客户主动获取"),
    TOO_FAST_VISIT("-19","用户访问超过限制频率"),
    WRONG_IP("108","指定访问IP错误");

    private String code;
    private String describe;

    public String getCode() {
        return code;
    }

    public String getDescribe() {
        return describe;
    }

    JSLTSendReturnCode(String code, String describe) {
        this.code = code;
        this.describe = describe;
    }

    public static String getDescribe(String code){
        for(JSLTSendReturnCode e:JSLTSendReturnCode.values()){
            if(e.getCode().equals(code)){
                return e.getDescribe();
            }
        }
        return "";
    }
}
