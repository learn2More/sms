package com.ppdai.ac.sms.api.provider.jslt.domain.model;

/**
 * author cash
 * create 2017-06-30-15:25
 **/

public enum JSLTReportStatus {
    SUCCESS("10","发送成功"),
    FAIL("20","发送失败");

    private String code;
    private String describe;

    public String getCode() {
        return code;
    }

    public String getDescribe() {
        return describe;
    }

    JSLTReportStatus(String code, String describe) {
        this.code = code;
        this.describe = describe;
    }

    public static String getDescribet(String code){
        String describe="";
        for(JSLTReportStatus e:JSLTReportStatus.values()){
            if(e.getCode().equals(code)){
                describe=e.getDescribe();
            }
        }
        return describe;
    }
}
