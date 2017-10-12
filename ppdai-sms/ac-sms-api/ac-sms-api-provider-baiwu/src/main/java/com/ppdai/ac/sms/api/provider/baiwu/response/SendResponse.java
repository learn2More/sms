package com.ppdai.ac.sms.api.provider.baiwu.response;

/**
 * 短信发送返回
 * author cash
 * create 2017-05-04-16:35
 **/

public class SendResponse extends  AbstractResponse {
    private String resultContent;

    public String getResultContent() {
        return resultContent;
    }

    public void setResultContent(String resultContent) {
        this.resultContent = resultContent;
    }
}
