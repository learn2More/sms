package com.ppdai.ac.sms.api.provider.innerxw.response;

import com.esms.MOMsg;

import java.util.List;

/**
 * 上行记录
 * author cash
 * create 2017-05-04-19:12
 **/

public class DeliversResponse extends  AbstractResponse {

    private List<MOMsg> listDeliver;

    public List<MOMsg> getListDeliver() {
        return listDeliver;
    }

    public void setListDeliver(List<MOMsg> listDeliver) {
        this.listDeliver = listDeliver;
    }
}
