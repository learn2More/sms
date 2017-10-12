package com.ppdai.ac.sms.api.gateway.request;

/**
 * 定时发送更新
 * author cash
 * create 2017-08-01-17:59
 **/

public class TimedSendUpdate {
    private long timedSendId;
    private int isSend;
    private String remark;

    public long getTimedSendId() {
        return timedSendId;
    }

    public void setTimedSendId(long timedSendId) {
        this.timedSendId = timedSendId;
    }

    public int getIsSend() {
        return isSend;
    }

    public void setIsSend(int isSend) {
        this.isSend = isSend;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "TimedSendUpdate{" +
                "timedSendId=" + timedSendId +
                ", isSend=" + isSend +
                ", remark='" + remark + '\'' +
                '}';
    }
}
