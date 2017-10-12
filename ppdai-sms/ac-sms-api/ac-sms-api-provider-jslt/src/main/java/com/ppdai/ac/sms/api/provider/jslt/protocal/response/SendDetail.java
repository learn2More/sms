package com.ppdai.ac.sms.api.provider.jslt.protocal.response;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * author cash
 * create 2017-06-30-15:25
 **/

@XmlRootElement(name="returnsms")
public class SendDetail {
    private String returnstatus;

    private String message;

    private String remainpoint;

    private String taskID;

    private String successCounts;

    public String getReturnstatus() {
        return returnstatus;
    }

    public void setReturnstatus(String returnstatus) {
        this.returnstatus = returnstatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRemainpoint() {
        return remainpoint;
    }

    public void setRemainpoint(String remainpoint) {
        this.remainpoint = remainpoint;
    }

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public String getSuccessCounts() {
        return successCounts;
    }

    public void setSuccessCounts(String successCounts) {
        this.successCounts = successCounts;
    }

    @Override
    public String toString() {
        return "SendDetail{" +
                "returnstatus=" + returnstatus +
                ",message=" + message +
                ",remainpoint=" + remainpoint +
                ",taskID=" + taskID +
                ", successCounts='" + successCounts + '\'' +
                '}';
    }

    public SendDetail() {
        super();
    }
}
