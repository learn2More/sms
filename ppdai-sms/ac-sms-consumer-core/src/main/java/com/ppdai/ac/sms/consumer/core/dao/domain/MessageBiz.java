package com.ppdai.ac.sms.consumer.core.dao.domain;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.ppdai.ac.sms.common.redis.RedisUtil;
import com.ppdai.ac.sms.consumer.core.configuration.OperatorProperties;
import com.ppdai.ac.sms.consumer.core.dao.mapper.smsbase.MessageBusinessMapper;
import com.ppdai.ac.sms.consumer.core.dao.mapper.smsbase.ProviderConfigMapper;
import com.ppdai.ac.sms.consumer.core.dao.mapper.smsmessage.SMSMessageMapper;
import com.ppdai.ac.sms.consumer.core.enums.InvokeResult;
import com.ppdai.ac.sms.consumer.core.enums.MessageSendStatus;
import com.ppdai.ac.sms.consumer.core.model.bo.BizResult;
import com.ppdai.ac.sms.consumer.core.model.bo.ProviderChannels;
import com.ppdai.ac.sms.consumer.core.model.bo.ProviderLine;
import com.ppdai.ac.sms.consumer.core.model.entity.BusinessProviderDTO;
import com.ppdai.ac.sms.consumer.core.model.entity.MessageBusinessDTO;
import com.ppdai.ac.sms.consumer.core.model.entity.ProviderConfigDTO;
import com.ppdai.ac.sms.consumer.core.model.entity.SMSMessageDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by kiekiyang on 2017/5/4.
 */
@Component
public class MessageBiz {
    private static final Logger logger = LoggerFactory.getLogger(MessageBiz.class);
    @Autowired
    BusinessProviderBiz businessProviderBiz;
    @Autowired
    SMSMessageMapper smsMessageMapper;
    @Autowired
    ProviderConfigMapper providerConfigMapper;

    @Autowired
    RedisUtil<String,List<BusinessProviderDTO>> redisUtil;

    @Autowired
    RedisUtil<String,List<ProviderConfigDTO>> redisUtil2;

    @Autowired
    SendMessageBiz sendMessageBiz;

    @Autowired
    OperatorProperties operatorProperties;

    @Autowired
    MessageBusinessMapper messageBusinessMapper;

    private final String BUSINESS_PROVIDER = "SMS:BUSINESS_PROVIDER:%s-%s";

    private final String PROVIDER_CONFIG = "SMS:CORE_PROVIDER_CONFIG:%s";



    /**
     * 发送消息
     *
     * @param smsMessageDTO
     * @return
     */
    public boolean send(SMSMessageDTO smsMessageDTO) {
        logger.info("---------消息发送入参: "+ JSONObject.toJSONString(smsMessageDTO));
        //TODO: 检查消息状态，修改消息状态为发送中，选择短信最终发送通道，记录发送记录
        try {
            if (!checkMessageStatus(smsMessageDTO.getMessageId()))
                return false;
            List<ProviderChannels> channels = routeProvider(smsMessageDTO.getBusinessId(),smsMessageDTO.getContentType());
            for (ProviderChannels channel : channels) {
                ProviderLine providerLine = new ProviderLine(channel.getLine());
                /*检查手机号对应运营商是否在供应商线路支持的列表中*/
                if (checkMobilePhoneOperator(smsMessageDTO.getRecipient(), providerLine)){
                    int providerId=channel.getProviderId();

                    String cacheKey = String.format(PROVIDER_CONFIG, providerId);
                    List<ProviderConfigDTO> providerConfigDTO=redisUtil2.get(cacheKey);
                    if(null==providerConfigDTO||CollectionUtils.isEmpty(providerConfigDTO)){
                        providerConfigDTO = providerConfigMapper.findProviderConfigByProviderId(providerId);
                        if (null==providerConfigDTO||CollectionUtils.isEmpty(providerConfigDTO)) {
                            logger.warn(String.format("供应商没有有效的配置信息 供应商编号:%s", channel.getProviderId()));
                            return false;
                        }else{
                            //服务商配置信息 缓存 1min
                            Duration duration = Duration.between(LocalDateTime.now(), LocalDateTime.now().plusMinutes(1));
                            redisUtil2.set(cacheKey,providerConfigDTO,duration.getSeconds());
                        }
                    }
                    LocalDateTime receiveTime = smsMessageDTO.getInsertTime() == null ? LocalDateTime.now() : smsMessageDTO.getInsertTime().toLocalDateTime();
                    BizResult result = sendMessageBiz.send(smsMessageDTO.getMessageId(), receiveTime, smsMessageDTO.getRecipient(), smsMessageDTO.getContent(), providerConfigDTO);
                    if (result.getResultCode() == InvokeResult.SUCCESS.getCode())
                        return true;
                    else
                        continue;
                }
            }
            logger.warn(String.format("该手机号没有可支持的供应商发送:%s", smsMessageDTO.getRecipient()));
        } catch (Exception ex) {
            logger.error(String.format("发送消息ID：%s 异常", smsMessageDTO.getMessageId()), ex);
        }
        return false;
    }

    /**
     * 发送消息 v2
     *
     * @param smsMessageDTO
     * @return
     */
    public boolean sendV2(SMSMessageDTO smsMessageDTO) {
        logger.info("---------消息发送入参: "+ JSONObject.toJSONString(smsMessageDTO));
        try {
            List<ProviderChannels> channels = routeProvider(smsMessageDTO.getBusinessId(),smsMessageDTO.getContentType());
            List<Integer> candidates=Lists.newArrayList();

            //查询消息类型
            MessageBusinessDTO messageBusinessDTO=messageBusinessMapper.getBussinessByBussinessId(smsMessageDTO.getBusinessId());
            Integer messageType=null;
            if(messageBusinessDTO!=null){
                messageType=messageBusinessDTO.getMessageType();
            }

            if(null!=channels&&CollectionUtils.isNotEmpty(channels)){
                for (ProviderChannels channel : channels) {
                    if(messageType!=null && messageType== com.ppdai.ac.sms.consumer.core.enums.MessageType.EMAIL.getCode()){
                        candidates.add(channel.getProviderId());
                    }else {
                        ProviderLine providerLine = new ProviderLine(channel.getLine());
                        /*过滤 手机号对应运营商在供应商线路支持的列表中*/
                        if (checkMobilePhoneOperator(smsMessageDTO.getRecipient(), providerLine)) {
                            candidates.add(channel.getProviderId());
                        }
                    }
                }
                if(CollectionUtils.isNotEmpty(candidates)){
                    if (!checkMessageStatus(smsMessageDTO.getMessageId())){
                        logger.warn(String.format("消息更新状态为 发送中 失败:%s", smsMessageDTO.getMessageId()));
                        return false;
                    }
                    LocalDateTime receiveTime = smsMessageDTO.getInsertTime() == null ? LocalDateTime.now() : smsMessageDTO.getInsertTime().toLocalDateTime();
                    BizResult result = sendMessageBiz.sendMsg(smsMessageDTO.getMessageId(), receiveTime, smsMessageDTO.getRecipient(), smsMessageDTO.getContent(), candidates,smsMessageDTO.getTemplateId(),messageType);
                    if (result.getResultCode() == InvokeResult.SUCCESS.getCode())
                        return true;
                }else{
                    logger.error(String.format("该手机号没有可支持的供应商发送:%s", smsMessageDTO.getRecipient()));
                }
            }else{
                logger.error(String.format("该业务没有可支持的供应商发送:%s", smsMessageDTO.getBusinessId()));
            }
        } catch (Exception ex) {
            logger.error(String.format("发送消息ID：%s 异常", smsMessageDTO.getMessageId()), ex);
        }
        return false;
    }

    /**
     * 检查手机号运营商类型
     *
     * @param mobile
     * @return
     */
    private boolean checkMobilePhoneOperator(String mobile, ProviderLine providerLine) {
        for (String prefix : operatorProperties.getChinaMobile()) {
            int prefixSize = prefix.length();
            if (mobile.substring(0, prefixSize).equals(prefix)) {
                return providerLine.isChinaMobileLine();
            }
        }
        for (String prefix : operatorProperties.getChinaUnicom()) {
            int prefixSize = prefix.length();
            if (mobile.substring(0, prefixSize).equals(prefix)) {
                return providerLine.isChinaUnicomLine();
            }
        }
        for (String prefix : operatorProperties.getChinaTelecom()) {
            int prefixSize = prefix.length();
            if (mobile.substring(0, prefixSize).equals(prefix)) {
                return providerLine.isChinaTelecom();
            }
        }
        return providerLine.isPhs();
    }


    /**
     * 检查消息状态，并修改状态为处理中
     *
     * @param messageId
     * @return
     */
    private boolean checkMessageStatus(String messageId) {
        Timestamp startTime = Timestamp.from(LocalDateTime.now().minusDays(1).atZone(ZoneId.systemDefault()).toInstant());
        Timestamp endTime = Timestamp.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        SMSMessageDTO smsMessageDTO = smsMessageMapper.findMessageById(messageId, startTime, endTime);
        if (smsMessageDTO == null ||
                (smsMessageDTO.getStatus() != MessageSendStatus.INIT.getCode() &&
                        smsMessageDTO.getStatus() != MessageSendStatus.SENDING.getCode() &&
                        smsMessageDTO.getStatus() != MessageSendStatus.SUBMITFAIL.getCode())) {
            logger.warn(String.format("待发送消息状态不正确,MessageId:%s", messageId));
            return false;
        } else {
            smsMessageMapper.updateMessageStatus(messageId, MessageSendStatus.SENDING.getCode(), startTime, endTime);
        }
        return true;
    }


    /**
     * 路由选择供应商
     *
     * @param businessId
     * @return
     */
    private List<ProviderChannels> routeProvider(int businessId,int messageKind) {
        String cacheKey = String.format(BUSINESS_PROVIDER, businessId,messageKind);
        List<BusinessProviderDTO> bussinessProviderRelations = redisUtil.get(cacheKey);
        if (bussinessProviderRelations == null) {
            bussinessProviderRelations = businessProviderBiz.findBusinessProviderByBusinessIdAndMessageKind(businessId,messageKind);
            if (bussinessProviderRelations.size() > 0) {
                //业务 服务商路由 缓存 1 min
                //redisUtil.set(cacheKey, JSON.toJSONString(providerChannels), duration.getSeconds());
                redisUtil.set(cacheKey, bussinessProviderRelations, 60L);
            }
        }
        List<ProviderChannels> providerChannels = Lists.newArrayList();
        for (BusinessProviderDTO relation : computeProvider(bussinessProviderRelations)) {
            ProviderChannels channeles = new ProviderChannels();
            channeles.setLine(relation.getLine());
            channeles.setProviderId(relation.getProviderId());
            channeles.setWeight(relation.getWeight());
           /* List<ProviderConfigDTO> providerConfigs = providerConfigMapper.findProviderConfigByProviderId(relation.getProviderId());
            List<ProviderAccount> providerAccounts = Lists.newArrayList();
            for (ProviderConfigDTO config : providerConfigs) {
                ProviderAccount providerAccount = new ProviderAccount();
                providerAccount.setConfigKey(config.getConfigKey());
                providerAccount.setConfigValue(config.getConfigValue());
                providerAccounts.add(providerAccount);
            }
            channeles.setProviderAccounts(providerAccounts);*/
            providerChannels.add(channeles);
        }
        return providerChannels;
    }

    /**
     * 按权重计算渠道供应商
     * 选中供应商之后,剩余供应商降序候选
     * 考虑供应商权重之和不为100
     * @param relations
     * @return
     */
    private List<BusinessProviderDTO> computeProvider(List<BusinessProviderDTO> relations) {
        List<BusinessProviderDTO> routeProvider = Lists.newArrayList();
        Collections.sort(relations);//升序
        Collections.reverse(relations);//降序
        Random r = new Random();
        int rand = r.nextInt(100);
        int weight = 0;
        for (int i = 0; i < relations.size(); i++) {
            weight += relations.get(i).getWeight();
            if (rand <weight) {
                routeProvider.add(relations.get(i));
                for(int j=0;j<relations.size();j++){//余下服务商降序获取
                    if(i!=j){
                        routeProvider.add(relations.get(j));
                    }
                }
                break;
            }
        }
        if(CollectionUtils.isEmpty(routeProvider)){//防止,服务商配置的权重之和不为100
            routeProvider=relations;
        }
        return routeProvider;
    }


}
