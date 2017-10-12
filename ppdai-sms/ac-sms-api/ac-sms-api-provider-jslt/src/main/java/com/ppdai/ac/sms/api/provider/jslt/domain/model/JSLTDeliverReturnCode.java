package com.ppdai.ac.sms.api.provider.jslt.domain.model;

/**
 * author cash
 * create 2017-06-30-15:25
 **/

public enum JSLTDeliverReturnCode {

    SUCCESS("1000","发送成功"),
    NO_ACCOUNT_PASSWORD("-1001","用户名或密码不能为空"),
    ERROR_ACCOUNT_PASSWORD("-1002","用户名或密码错误"),
    NO_ACCESS_TO_REPORT("-1003","该用户不允许查看状态报告"),
    PARAM_ERROR("-1004","参数不正确");

    private String code;
    private String describe;

    public String getCode() {
        return code;
    }

    public String getDescribe() {
        return describe;
    }

    JSLTDeliverReturnCode(String code, String describe) {
        this.code = code;
        this.describe = describe;
    }

    public static String getDescribet(String code){
        String describe="";
        for(JSLTDeliverReturnCode e:JSLTDeliverReturnCode.values()){
            if(e.getCode().equals(code)){
                describe=e.getDescribe();
            }
        }
        return describe;
    }


}
