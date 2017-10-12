package com.ppdai.ac.sms.api.provider.jslt.domain.model;

import com.ppdai.ac.sms.provider.core.utils.MD5Util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * author cash
 * create 2017-06-30-15:25
 **/

public class JSLTSignDTO {
    private String jslt_account;

    private String jslt_password;

    private String timestamp;

    private String sign;

    public JSLTSignDTO(){}

    public JSLTSignDTO(String account,String password){
        this.jslt_account = account;
        this.jslt_password = password;
    }

    public String getTimestamp(){
        Date nowTime = new Date(System.currentTimeMillis());
        SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = sdFormatter.format(nowTime);
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        String signString = jslt_account+jslt_password + this.getTimestamp();
        String sign= MD5Util.MD5(signString);
        return sign.toLowerCase();
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
