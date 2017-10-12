package com.ppdai.ac.sms.api.provider.baiwu.protocol.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang.StringUtils;

/**
 * author cash
 * create 2017-05-02-11:10
 **/

public class ReportRequest {

    private String corp_id;

    private String user_id;

    private String corp_pwd;

    public String getCorp_id() {
        return corp_id;
    }

    public void setCorp_id(String corp_id) {
        this.corp_id = corp_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCorp_pwd() {
        return corp_pwd;
    }

    public void setCorp_pwd(String corp_pwd) {
        this.corp_pwd = corp_pwd;
    }

    public ReportRequest(String corp_id, String user_id, String corp_pwd) {
        this.corp_id = corp_id;
        this.user_id = user_id;
        this.corp_pwd = corp_pwd;
    }

    public ReportRequest() {
    }



}
