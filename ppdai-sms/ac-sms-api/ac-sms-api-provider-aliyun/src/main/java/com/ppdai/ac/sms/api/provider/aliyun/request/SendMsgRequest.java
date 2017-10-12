package com.ppdai.ac.sms.api.provider.aliyun.request;



import java.util.List;
import java.util.Map;

/**
 * 短信发送信息
 * author cash
 * create 2017-05-05-14:46
 **/

public class SendMsgRequest {

    private String accessKey;

    private String accessSecret;

    private String endpoint;

    private String topicName;

    private String replyQueueName;

    private String failQueueName;

    private String successQueueName;

    private List<String> mobiles;//手机号
    private Map<String,String> paramMap;//参数map
    private String signName;//签名
    private String templateCode;//模板名
    private String extendNo;//扩展码
    private String recordId;//消息记录id


    public String getReplyQueueName() {
        return replyQueueName;
    }

    public void setReplyQueueName(String replyQueueName) {
        this.replyQueueName = replyQueueName;
    }

    public String getFailQueueName() {
        return failQueueName;
    }

    public void setFailQueueName(String failQueueName) {
        this.failQueueName = failQueueName;
    }

    public String getSuccessQueueName() {
        return successQueueName;
    }

    public void setSuccessQueueName(String successQueueName) {
        this.successQueueName = successQueueName;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getAccessSecret() {
        return accessSecret;
    }

    public void setAccessSecret(String accessSecret) {
        this.accessSecret = accessSecret;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public List<String> getMobiles() {
        return mobiles;
    }

    public void setMobiles(List<String> mobiles) {
        this.mobiles = mobiles;
    }

    public Map<String, String> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, String> paramMap) {
        this.paramMap = paramMap;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getExtendNo() {
        return extendNo;
    }

    public void setExtendNo(String extendNo) {
        this.extendNo = extendNo;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }


}
