package com.ppdai.ac.sms.provider.core.request;

import java.util.Map;

/**
 * author cash
 * create 2017-05-15-16:26
 **/

public class CallRequest {
    Map<String,String> params;

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }
}
