package com.ppdai.ac.sms.api.provider.baiwu.domain.model;

/**
 * 百悟上行返回枚举
 * author cash
 * create 2017-05-15-10:53
 **/

public enum BWDeliverReturnCode {

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

    /*public void setCode(String code) {
        this.code = code;
    }*/

    public String getDescribe() {
        return describe;
    }

    /*public void setDescribe(String describe) {
        this.describe = describe;
    }*/

    BWDeliverReturnCode(String code, String describe) {
        this.code = code;
        this.describe = describe;
    }

    public static String getDescribet(String code){
        String describe="";
        for(BWDeliverReturnCode e:BWDeliverReturnCode.values()){
            if(e.getCode().equals(code)){
                describe=e.getDescribe();
            }
        }
        return describe;
    }
}
