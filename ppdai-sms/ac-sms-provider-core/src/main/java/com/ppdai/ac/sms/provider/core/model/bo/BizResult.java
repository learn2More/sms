package com.ppdai.ac.sms.provider.core.model.bo;

/**
 * bizResult
 * author cash
 * create 2017-05-09-18:06
 **/

public class BizResult {
    private int resultCode;
    private String resultMessage;
    private Object resultObject;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public Object getResultObject() {
        return resultObject;
    }

    public void setResultObject(Object resultObject) {
        this.resultObject = resultObject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BizResult result = (BizResult) o;

        if (resultCode != result.resultCode) return false;
        if (resultMessage != null ? !resultMessage.equals(result.resultMessage) : result.resultMessage != null)
            return false;
        return resultObject != null ? resultObject.equals(result.resultObject) : result.resultObject == null;
    }

    @Override
    public int hashCode() {
        int result = resultCode;
        result = 31 * result + (resultMessage != null ? resultMessage.hashCode() : 0);
        result = 31 * result + (resultObject != null ? resultObject.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BizResult{" +
                "resultCode=" + resultCode +
                ", resultMessage='" + resultMessage + '\'' +
                ", resultObject=" + resultObject +
                '}';
    }
}
