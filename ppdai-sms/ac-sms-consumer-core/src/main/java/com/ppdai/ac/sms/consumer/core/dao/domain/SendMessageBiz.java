package com.ppdai.ac.sms.consumer.core.dao.domain;

import com.alibaba.fastjson.JSONObject;
import com.dianping.cat.Cat;
import com.dianping.cat.message.Message;
import com.dianping.cat.message.Transaction;
import com.dianping.cat.message.internal.MessageId;
import com.ppdai.ac.sms.common.redis.RedisUtil;
import com.ppdai.ac.sms.consumer.core.configuration.NetWorkProperties;
import com.ppdai.ac.sms.consumer.core.dao.mapper.smsbase.MessageTemplateMapper;
import com.ppdai.ac.sms.consumer.core.dao.mapper.smsbase.ProviderConfigMapper;
import com.ppdai.ac.sms.consumer.core.dao.mapper.smsmessage.SMSMessageMapper;
import com.ppdai.ac.sms.consumer.core.enums.InvokeResult;
import com.ppdai.ac.sms.consumer.core.enums.MessageSendStatus;
import com.ppdai.ac.sms.consumer.core.enums.RecordStatus;
import com.ppdai.ac.sms.consumer.core.model.bo.BizResult;
import com.ppdai.ac.sms.consumer.core.model.entity.MessageTemplateDTO;
import com.ppdai.ac.sms.consumer.core.model.entity.ProviderConfigDTO;
import com.ppdai.ac.sms.consumer.core.model.entity.SMSMessageRecordDTO;
import com.ppdai.ac.sms.consumer.core.protocol.request.ProviderSendRequest;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by kiekiyang on 2017/5/5.
 */
@Component
public class SendMessageBiz {
    private static final Logger logger = LoggerFactory.getLogger(SendMessageBiz.class);
    public final com.squareup.okhttp.MediaType APPLICATION_JSON = com.squareup.okhttp.MediaType.parse("application/json; charset=utf-8");
    private final com.squareup.okhttp.OkHttpClient client = new com.squareup.okhttp.OkHttpClient();
    @Autowired
    NetWorkProperties netWorkProperties;

    @Autowired
    MessageRecordBiz messageRecordBiz;

    @Autowired
    SMSMessageMapper smsMessageMapper;

    @Autowired
    ProviderConfigMapper providerConfigMapper;

    @Autowired
    RedisUtil<String,List<ProviderConfigDTO>> redisUtil;

    @Autowired
    MessageTemplateMapper messageTemplateMapper;

    private final String PROVIDER_CONFIG = "SMS:CORE_PROVIDER_CONFIG:%s";

    Lock successLock = new ReentrantLock();
    Lock failLock = new ReentrantLock();

    public BizResult send(String messageId, LocalDateTime messageReceiveTime, String mobile, String content, List<ProviderConfigDTO> configs) {
        BizResult result = new BizResult();
        try {
            Map<String, String> providerConfig = new ConcurrentHashMap<>();
            String url = "";
            for (ProviderConfigDTO config : configs) {
                if (config.getConfigKey().toLowerCase().equals("messageurl"))
                    url = config.getConfigValue();
                else
                    providerConfig.put(config.getConfigKey(), config.getConfigValue());
            }
            providerConfig.put("content", content);
            providerConfig.put("mobile", mobile);
            //添加providerName 说明
            providerConfig.put("providerName",configs.get(0).getProviderName());
            String sign = providerConfig.get("sign") == null ? "" : providerConfig.get("sign");
            String recordId = UUID.randomUUID().toString();
            boolean recordResult = messageRecordBiz.messageRecord(recordId, messageId, configs.get(0).getProviderId(), computeMessageCnt((sign + content)), messageReceiveTime, LocalDateTime.now());
            if (StringUtils.isNotBlank(url) && recordResult) {
                // && syncPost(messageId, url, providerConfig
                asyncPost(recordId, messageId,url, providerConfig);
                //syncPost(messageId, url, providerConfig);
                result.setResultCode(InvokeResult.SUCCESS.getCode());
                result.setResultMessage(InvokeResult.SUCCESS.getMessage());
            } else {
                result.setResultCode(InvokeResult.FAIL.getCode());
                result.setResultMessage(InvokeResult.FAIL.getMessage());
                result.setResultObject(String.format("手机号：%s 发送短信失败，短信供应商：%s 可能未配messageurl信息",
                        mobile, configs.get(0).getProviderId()));
            }
        } catch (Exception ex) {
            result.setResultCode(InvokeResult.FAIL.getCode());
            result.setResultMessage(InvokeResult.FAIL.getMessage());
            result.setResultObject(String.format("手机号:%s,向供应商发送请求异常", mobile));
            logger.error(String.format("手机号:%s,向供应商发送请求异常", mobile), ex);
        }
        return result;
    }

    private boolean syncPost(String recordId, String messageId, String url, Map<String, String> mapParams) {
        ProviderSendRequest providerSendRequest = new ProviderSendRequest();
        mapParams.put("messageId", messageId);
        providerSendRequest.setParams(mapParams);
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(APPLICATION_JSON, com.alibaba.fastjson.JSON.toJSONString(providerSendRequest)))
                .build();
        client.setConnectTimeout(netWorkProperties.getConnectionTimeout(), TimeUnit.SECONDS);
        client.setWriteTimeout(netWorkProperties.getWriteTimeout(), TimeUnit.SECONDS);
        client.setReadTimeout(netWorkProperties.getReadTimeout(), TimeUnit.SECONDS);

        //同步
        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                failureCallback(recordId, messageId,null, null, new IOException("xxx"));
            } else {
                successCallback(recordId, messageId, null,response);
                return true;
            }
        } catch (Exception ex) {
            logger.error(String.format("服务调用错误,messageId:%s,url:%s,mapParams:%s", messageId, url, mapParams), ex);
        }
        return false;
    }


    /**
     * 异步发送请求
     *
     * @param url
     * @param mapParams
     */
    private void asyncPost(final String recordId, final String messageId, String url, Map<String, String> mapParams) {
        ProviderSendRequest providerSendRequest = new ProviderSendRequest();
        mapParams.put("messageId", messageId);
        mapParams.put("recordId", recordId);
        providerSendRequest.setParams(mapParams);
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(APPLICATION_JSON, com.alibaba.fastjson.JSON.toJSONString(providerSendRequest)))
                .build();
        client.setConnectTimeout(netWorkProperties.getConnectionTimeout(), TimeUnit.SECONDS);
        client.setWriteTimeout(netWorkProperties.getWriteTimeout(), TimeUnit.SECONDS);
        client.setReadTimeout(netWorkProperties.getReadTimeout(), TimeUnit.SECONDS);

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Request request, IOException e) {
                /*调用失败处理*/
//                PropertyContext context = new PropertyContext();
//                context.addProperty(Cat.Context.ROOT, request.header(CatConstants.HTTP_HEADER_ROOT_MESSAGE_ID));
//                context.addProperty(Cat.Context.PARENT, request.header(CatConstants.HTTP_HEADER_PARENT_MESSAGE_ID));
//                context.addProperty(Cat.Context.CHILD, request.header(CatConstants.HTTP_HEADER_CHILD_MESSAGE_ID));
//                Cat.logRemoteCallServer(context);

                failureCallback(recordId, messageId, mapParams.get("providerName"),request, e);
            }

            @Override
            public void onResponse(Response response) {
                /*调用成功处理*/
                try {
                    if (response.isSuccessful()) {
//                        PropertyContext context = new PropertyContext();
//                        context.addProperty(Cat.Context.ROOT, response.request().header(CatConstants.HTTP_HEADER_ROOT_MESSAGE_ID));
//                        context.addProperty(Cat.Context.PARENT, response.request().header(CatConstants.HTTP_HEADER_PARENT_MESSAGE_ID));
//                        context.addProperty(Cat.Context.CHILD, response.request().header(CatConstants.HTTP_HEADER_CHILD_MESSAGE_ID));
//                        Cat.logRemoteCallServer(context);

                        successCallback(recordId, messageId,mapParams.get("providerName"), response);
                    } else {
                        logger.warn(String.format("发送短信内容失败,Response:%s", response));
                    }
                } catch (Exception ex) {
                    logger.error(String.format("发送短信内容异常,Response:%s", response), ex);
                }
            }
        });
    }

    /**
     * 失败回调
     *
     * @param request
     * @param ex
     */
    private void failureCallback(String recordId, String messageId,String providerName, Request request, IOException ex) {
        failLock.lock();
        Transaction transaction = Cat.newTransaction("failureCallback", messageId);
        try {
            Timestamp start = Timestamp.from(LocalDateTime.now().minusDays(1).atZone(ZoneId.systemDefault()).toInstant());
            Timestamp end = Timestamp.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
            SMSMessageRecordDTO recordDTO = messageRecordBiz.findRecordById(recordId, start.toLocalDateTime()
                    , end.toLocalDateTime());
            if (recordDTO != null) {
                //记录供应商执行耗时
                Duration duration = Duration.between(recordDTO.getSendTime().toLocalDateTime(), LocalDateTime.now());
                Cat.logMetricForDuration(String.format("MQ异步执行耗时,供应商:%s-%s ", recordDTO.getProviderId(),providerName),
                        duration.toMillis());
                Cat.logMetricForCount(String.format("执行失败次数,供应商：%s-%s", recordDTO.getProviderId(),providerName));
            }
            StringBuilder errorMsg = new StringBuilder();
            errorMsg.append(request);
            errorMsg.append("|");
            errorMsg.append(ex);
            if (messageRecordBiz.updateRecord(recordId, RecordStatus.SUBMITFAIL, ex.getMessage(),
                    LocalDateTime.now(), start.toLocalDateTime(), end.toLocalDateTime())) {
                smsMessageMapper.updateMessageStatus(messageId, MessageSendStatus.SUBMITFAIL.getCode(), start, end);
                logger.warn(String.format("消息编号：%s 提交失败，原因：%s", messageId, errorMsg));
            }
        } catch (Exception ex1) {
            Cat.logError(ex1);
        } finally {
            transaction.complete();
            failLock.unlock();
        }
    }

    /**
     * 成功回调
     *
     * @param response
     */
    private void successCallback(String recordId, String messageId,String providerName, Response response) {
        successLock.lock();
        Transaction transaction = Cat.newTransaction("successCallback", messageId);
        try {

            Timestamp start = Timestamp.from(LocalDateTime.now().minusDays(1).atZone(ZoneId.systemDefault()).toInstant());
            Timestamp end = Timestamp.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
            SMSMessageRecordDTO recordDTO = messageRecordBiz.findRecordById(recordId, start.toLocalDateTime()
                    , end.toLocalDateTime());
            if (recordDTO != null) {
                //记录供应商执行耗时
                Duration duration = Duration.between(recordDTO.getSendTime().toLocalDateTime(), LocalDateTime.now());
                Cat.logMetricForDuration(String.format("MQ异步执行耗时,供应商:%s-%s", recordDTO.getProviderId(),providerName),
                        duration.toMillis());
                Cat.logMetricForCount(String.format("执行成功次数,供应商：%s-%s", recordDTO.getProviderId(),providerName));
            }
            String resultBody = response.body().string();
            if (messageRecordBiz.updateRecord(recordId, RecordStatus.SUBMITSUCCESS, resultBody,
                    LocalDateTime.now(), start.toLocalDateTime(), end.toLocalDateTime())) {
                smsMessageMapper.updateMessageStatus(messageId, MessageSendStatus.SUBMITSUCCESS.getCode(), start, end);
                logger.info(String.format("消息编号：%s 提交成功,接口返回：%s", messageId,resultBody));

                transaction.setStatus(Message.SUCCESS);
            } else {
                logger.info(String.format("消息提交成功，但记录更新记录失败,Message:%s,消息返回:%s", messageId, resultBody));
            }

        } catch (IOException ex) {
            logger.error(String.format("消息提交成功，但记录更新记录异常,Message:%s", messageId), ex);
        } catch (Exception ex) {
            Cat.logError(ex);
        } finally {
            transaction.complete();

            successLock.unlock();
        }
    }

    /**
     * 消息拆分长度
     *
     * @param content
     * @return
     */
    private int computeMessageCnt(String content) {
        if (content.length() <= 70)
            return 1;
        return (int) Math.ceil(content.length() / 67.0);
    }

    /**
     * 异步返回失败,需要切换服务商
     * @param messageId
     * @param messageReceiveTime
     * @param mobile
     * @param content
     * @param candidates
     * @return
     */
    public BizResult sendMsg(String messageId, LocalDateTime messageReceiveTime, String mobile, String content, List<Integer> candidates,int templateId,Integer messageType) {
        BizResult result = new BizResult();

        //第一候选 服务商
        int providerId=candidates.get(0);

        String cacheKey = String.format(PROVIDER_CONFIG, providerId);
        List<ProviderConfigDTO> providerConfigDTO=redisUtil.get(cacheKey);
        if(null==providerConfigDTO||CollectionUtils.isEmpty(providerConfigDTO)){
            providerConfigDTO = providerConfigMapper.findProviderConfigByProviderId(providerId);
            if (null==providerConfigDTO||CollectionUtils.isEmpty(providerConfigDTO)) {
                logger.warn(String.format("供应商没有有效的配置信息 供应商编号:%s", providerId));

                result.setResultCode(InvokeResult.FAIL.getCode());
                result.setResultMessage(String.format("供应商没有有效的配置信息 供应商编号:%s", providerId));
                return result;
            }else{
                //服务商配置信息 缓存 1min
                Duration duration = Duration.between(LocalDateTime.now(), LocalDateTime.now().plusMinutes(1));
                redisUtil.set(cacheKey,providerConfigDTO,duration.getSeconds());
            }
        }
        Map<String, String> providerConfig = new ConcurrentHashMap<>();
        String url = "";
        for (ProviderConfigDTO config : providerConfigDTO) {
            if (config.getConfigKey().toLowerCase().equals("messageurl"))
                url = config.getConfigValue();
            else
                providerConfig.put(config.getConfigKey(), config.getConfigValue());
        }
        providerConfig.put("content", content);
        providerConfig.put("mobile", mobile);
        //添加providerName 说明
        providerConfig.put("providerName",providerConfigDTO.get(0).getProviderName());
        String sign = providerConfig.get("sign") == null ? "" : providerConfig.get("sign");
        String recordId = UUID.randomUUID().toString();

        boolean recordResult = messageRecordBiz.messageRecord(recordId, messageId, providerConfigDTO.get(0).getProviderId(), computeMessageCnt((sign + content)), messageReceiveTime, LocalDateTime.now());

        if (StringUtils.isNotBlank(url) && recordResult) {

            //移除已经尝试发送的服务商
            candidates.remove(0);

            asyncPostV2(recordId, messageId,url, providerConfig,mobile,content,candidates,templateId,messageType);
            result.setResultCode(InvokeResult.SUCCESS.getCode());
            result.setResultMessage(InvokeResult.SUCCESS.getMessage());
        } else {
            result.setResultCode(InvokeResult.FAIL.getCode());
            result.setResultMessage(InvokeResult.FAIL.getMessage());
            result.setResultObject(String.format("手机号：%s 发送短信失败，短信供应商：%s 可能未配messageurl信息",
                    mobile, providerConfigDTO.get(0).getProviderId()));
        }

        return result;
    }

    /**
     * 异步发送请求
     *
     * @param url
     * @param mapParams
     */
    private void asyncPostV2(final String recordId, final String messageId, String url, Map<String, String> mapParams, String mobile, String content,List<Integer> candidates,int templateId,Integer messageType) {
        ProviderSendRequest providerSendRequest = new ProviderSendRequest();
        mapParams.put("messageId", messageId);
        mapParams.put("recordId", recordId);

        //消息类型为邮件时加上模板名称
        if(messageType!=null && messageType==com.ppdai.ac.sms.consumer.core.enums.MessageType.EMAIL.getCode()) {
            MessageTemplateDTO messageTemplateDTO = messageTemplateMapper.getTemplateByTemlateId(templateId);
            if (messageTemplateDTO != null) {
                mapParams.put("templatename", messageTemplateDTO.getTemplateName());
            }
        }
        providerSendRequest.setParams(mapParams);
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(APPLICATION_JSON, com.alibaba.fastjson.JSON.toJSONString(providerSendRequest)))
                .build();
        client.setConnectTimeout(netWorkProperties.getConnectionTimeout(), TimeUnit.SECONDS);
        client.setWriteTimeout(netWorkProperties.getWriteTimeout(), TimeUnit.SECONDS);
        client.setReadTimeout(netWorkProperties.getReadTimeout(), TimeUnit.SECONDS);

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Request request, IOException e) {

                //发送失败,调用其他候选服务商
                if(CollectionUtils.isNotEmpty(candidates)){
                    LocalDateTime localDateTime=LocalDateTime.now();
                    sendMsg(messageId,localDateTime,mobile,content,candidates,templateId,messageType);
                }
                failureCallback(recordId, messageId, mapParams.get("providerName"),request, e);
            }

            @Override
            public void onResponse(Response response) {
                /*调用成功处理*/
                try {
                    if (response.isSuccessful()) {
                        successCallback(recordId, messageId,mapParams.get("providerName"), response);
                    } else {
                        //发送失败,调用其他候选服务商
                        if(CollectionUtils.isNotEmpty(candidates)){
                            LocalDateTime localDateTime=LocalDateTime.now();
                            sendMsg(messageId,localDateTime,mobile,content,candidates,templateId,messageType);
                        }
                        logger.error(String.format("messageId:%s 发送短信内容失败,Response:%s",messageId, JSONObject.toJSON(response)));
                    }
                } catch (Exception ex) {
                    logger.error(String.format("messageId:%s 发送短信内容异常,Response:%s", messageId, JSONObject.toJSON(response)), ex);
                }
            }
        });
    }
}
