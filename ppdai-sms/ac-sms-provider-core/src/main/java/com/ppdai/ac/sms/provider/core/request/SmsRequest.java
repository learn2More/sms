package com.ppdai.ac.sms.provider.core.request;

import java.util.Map;

/**
 * author cash
 * create 2017-05-02-13:22
 **/

public class SmsRequest {

    Map<String,String> params;

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }
}
