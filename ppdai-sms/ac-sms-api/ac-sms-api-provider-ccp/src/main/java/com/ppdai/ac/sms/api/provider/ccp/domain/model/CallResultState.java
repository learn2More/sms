package com.ppdai.ac.sms.api.provider.ccp.domain.model;

/**
 * author cash
 * create 2017-05-16-13:36
 **/

public enum CallResultState {

    //0 成功，1未接听，2失败

    SUCCESS("0","成功"),
    NOBODY_IS_ANSWERING("1","未接听"),
    FAIL("2","失败");

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

    CallResultState(String code, String describe) {
        this.code = code;
        this.describe = describe;
    }


}
