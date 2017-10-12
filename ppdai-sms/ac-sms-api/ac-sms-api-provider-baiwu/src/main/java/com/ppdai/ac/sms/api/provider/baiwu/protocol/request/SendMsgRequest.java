package com.ppdai.ac.sms.api.provider.baiwu.protocol.request;

/**
 * author cash
 * create 2017-05-02-13:22
 **/

public class SendMsgRequest {

    private String corp_id;

    private String corp_pwd;

    private String corp_service;

    private String  mobile;

    private String msg_content;

    private String corp_msg_id;

    private String ext;

    public String getCorp_id() {
        return corp_id;
    }

    public void setCorp_id(String corp_id) {
        this.corp_id = corp_id;
    }

    public String getCorp_pwd() {
        return corp_pwd;
    }

    public void setCorp_pwd(String corp_pwd) {
        this.corp_pwd = corp_pwd;
    }

    public String getCorp_service() {
        return corp_service;
    }

    public void setCorp_service(String corp_service) {
        this.corp_service = corp_service;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMsg_content() {
        return msg_content;
    }

    public void setMsg_content(String msg_content) {
        this.msg_content = msg_content;
    }

    public String getCorp_msg_id() {
        return corp_msg_id;
    }

    public void setCorp_msg_id(String corp_msg_id) {
        this.corp_msg_id = corp_msg_id;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }


    public SendMsgRequest(){}



    public SendMsgRequest(String corp_id, String corp_pwd, String corp_service, String mobile, String msg_content, String corp_msg_id, String ext) {
        this.corp_id = corp_id;
        this.corp_pwd = corp_pwd;
        this.corp_service = corp_service;
        this.mobile = mobile;
        this.msg_content = msg_content;
        this.corp_msg_id = corp_msg_id;
        this.ext = ext;
    }

}
