package com.ppdai.ac.sms.api.provider.sendcloud.protocol.response;

import java.util.List;

/**
 * Created by zhongyunrui on 2017/7/24.
 */
public class SendResponseInfo {
  private List<String>  emailIdList;

    public List<String> getEmailIdList() {
        return emailIdList;
    }

    public void setEmailIdList(List<String> emailIdList) {
        this.emailIdList = emailIdList;
    }
}
