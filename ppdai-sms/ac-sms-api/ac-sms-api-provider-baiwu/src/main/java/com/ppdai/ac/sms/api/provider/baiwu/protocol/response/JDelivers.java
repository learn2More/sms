package com.ppdai.ac.sms.api.provider.baiwu.protocol.response;

import java.util.List;

/**
 * 上行报告json格式返回实体
 * author cash
 * create 2017-06-22-16:02
 **/

public class JDelivers {

    String code;
    String msg;
    String transaction_id;
    List<JDeliverDetail> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public List<JDeliverDetail> getData() {
        return data;
    }

    public void setData(List<JDeliverDetail> data) {
        this.data = data;
    }
}
