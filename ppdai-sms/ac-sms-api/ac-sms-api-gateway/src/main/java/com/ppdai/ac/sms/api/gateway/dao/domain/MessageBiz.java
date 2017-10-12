package com.ppdai.ac.sms.api.gateway.dao.domain;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ppdai.ac.sms.api.common.Md5HashHelper;
import com.ppdai.ac.sms.api.common.RegexHelper;
import com.ppdai.ac.sms.api.contract.request.sms.BatchSendRequest;
import com.ppdai.ac.sms.api.gateway.configuration.GatewayProperties;
import com.ppdai.ac.sms.api.gateway.configuration.MessageQueueProperties;
import com.ppdai.ac.sms.api.gateway.dao.mapper.smsbase.CallerBusinessMapper;
import com.ppdai.ac.sms.api.gateway.dao.mapper.smsbase.MessageBusinessMapper;
import com.ppdai.ac.sms.api.gateway.dao.mapper.smsbase.MessageTemplateMapper;
import com.ppdai.ac.sms.api.gateway.dao.mapper.smsbase.TimedSendMapper;
import com.ppdai.ac.sms.api.gateway.dao.mapper.smsmessage.SMSMessageMapper;
import com.ppdai.ac.sms.api.gateway.enums.*;
import com.ppdai.ac.sms.api.gateway.model.bo.BizResult;
import com.ppdai.ac.sms.api.gateway.model.entity.*;
import com.ppdai.ac.sms.api.gateway.protocol.PermissionService;
import com.ppdai.ac.sms.api.gateway.request.TimedSendList;
import com.ppdai.ac.sms.api.gateway.request.TimedSendSave;
import com.ppdai.ac.sms.common.redis.RedisUtil;
import com.ppdai.ac.sms.common.service.SendMailService;
import com.ppdai.messagequeue.producer.ProducerData;
import com.ppdai.messagequeue.producer.sync.SyncProducer;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kiekiyang on 2017/4/25.
 */
@Component
public class MessageBiz {
    private static final Logger logger = LoggerFactory.getLogger(MessageBiz.class);

    private String batchSendEmailTitle="短信发送平台短信-发送提醒";

    @Autowired
    BlackListBiz blackListBiz;

    @Autowired
    SensitiveWordBiz sensitiveWordBiz;

    @Autowired
    SMSMessageMapper smsMessageMapper;

    @Autowired
    MessageTemplateMapper templateMapper;

    @Autowired
    MessageBusinessMapper messageBusinessMapper;

    @Autowired
    CallerBusinessMapper callerBusinessMapper;

    @Autowired
    TimedSendMapper timedSendMapper;

    @Autowired
    SecurityCodeBiz securityCodeBiz;

    @Autowired
    RedisUtil<String,String> redisUtil;

    @Autowired
    SyncProducer syncProducer;

    @Autowired
    MessageQueueProperties messageQueueProperties;

    @Autowired
    GatewayProperties gatewayProperties;

    @Autowired
    SendMailService sendmail;

    @Autowired
    PermissionService permissionService;



    /**
     * 根据编号查询消息信息
     *
     * @param messageId
     * @param start
     * @param end
     * @return
     */
    public BizResult queryMessageById(String messageId, LocalDateTime start, LocalDateTime end) {
        BizResult bizResult = new BizResult();
        try {
            if (StringUtils.isBlank(messageId) || (end.isBefore(start))) {
                bizResult.setResultCode(InvokeResult.PARAM_ERROR.getCode());
                bizResult.setResultMessage(InvokeResult.PARAM_ERROR.getMessage());
                return bizResult;
            }
            SMSMessageDTO messageDTO = smsMessageMapper.findById(messageId,
                    Timestamp.from(start.atZone(ZoneId.systemDefault()).toInstant()),
                    Timestamp.from(end.atZone(ZoneId.systemDefault()).toInstant()));
            bizResult.setResultCode(InvokeResult.SUCCESS.getCode());
            bizResult.setResultMessage(InvokeResult.SUCCESS.getMessage());
            bizResult.setResultObject(messageDTO);
        } catch (Exception ex) {
            bizResult.setResultCode(InvokeResult.SYS_ERROR.getCode());
            bizResult.setResultMessage(InvokeResult.SYS_ERROR.getMessage());
            logger.error("changeStatusByMessageId throw exception", ex);
        }
        return bizResult;
    }

    /**
     * 修改消息状态
     *
     * @param messageId
     * @param status
     * @param start
     * @param end
     * @return
     */
    public BizResult changeStatusByMessageId(String messageId, int status, LocalDateTime start, LocalDateTime end) {
        BizResult bizResult = new BizResult();
        try {
            if (StringUtils.isBlank(messageId) || (end.isBefore(start))) {
                bizResult.setResultCode(InvokeResult.PARAM_ERROR.getCode());
                bizResult.setResultMessage(InvokeResult.PARAM_ERROR.getMessage());
                return bizResult;
            }
            int execResult = smsMessageMapper.changeMessageStatus(messageId, status,
                    Timestamp.from(start.atZone(ZoneId.systemDefault()).toInstant()),
                    Timestamp.from(end.atZone(ZoneId.systemDefault()).toInstant()));
            bizResult.setResultCode(InvokeResult.SUCCESS.getCode());
            bizResult.setResultMessage(InvokeResult.SUCCESS.getMessage());
            bizResult.setResultObject(execResult);
        } catch (Exception ex) {
            bizResult.setResultCode(InvokeResult.SYS_ERROR.getCode());
            bizResult.setResultMessage(InvokeResult.SYS_ERROR.getMessage());
            logger.error("changeStatusByMessageId throw exception", ex);
        }
        return bizResult;
    }


    /**
     * 消息发送
     *
     * @param msgType
     * @param callerId
     * @param mobile
     * @param parameter
     * @param businessAlias
     * @param templateAlias
     * @param extroInfo
     * @return
     */
//    @Transactional(value = "smsmessageTransactionManager")
    public BizResult send(MessageType msgType, int callerId, String mobile, String parameter, String businessAlias, String templateAlias, Map<String, Object> extroInfo) {
        logger.info("MessageBiz send message the all paramters: MessageType: "+msgType+" callerId: "+callerId+" mobile: "+
                mobile+" parameter: "+parameter+" businessAlias: "+businessAlias+" templateAlias: "+templateAlias+" extroInfo: "+ JSONObject.toJSONString(extroInfo));
        String tempPara=parameter;
        BizResult bizResult = new BizResult();
        if (!verifySendParams(callerId, mobile, businessAlias, templateAlias,msgType)) {
            bizResult.setResultCode(InvokeResult.PARAM_ERROR.getCode());
            bizResult.setResultMessage(InvokeResult.PARAM_ERROR.getMessage());
            return bizResult;
        }
        if(StringUtils.isEmpty(mobile)||!RegexHelper.isMobile(mobile)){
            bizResult.setResultCode(InvokeResult.MOBILE_ERROR.getCode());
            bizResult.setResultMessage(InvokeResult.MOBILE_ERROR.getMessage());
            return bizResult;
        }

        //消息类型非邮箱是验证手机号
        if(msgType!=MessageType.EMAIL) {
            if (!isInConfigNumSegment(mobile)) {
                bizResult.setResultCode(InvokeResult.NUM_SEGMENT_ERROR.getCode());
                bizResult.setResultMessage(InvokeResult.NUM_SEGMENT_ERROR.getMessage());
                return bizResult;
            }
        }

        try {

            String messageId = UUID.randomUUID().toString();

            /**业务校验**/
            MessageBusinessDTO messageBusinessDTO = messageBusinessMapper.getBussinessByAliasAndMessageType(businessAlias,msgType.getCode());
            if (messageBusinessDTO == null) {
                bizResult.setResultCode(InvokeResult.BUSINESS_ERROR.getCode());
                bizResult.setResultMessage(InvokeResult.BUSINESS_ERROR.getMessage());
                return bizResult;
            }

            /**模板-业务校验**/
            MessageTemplateDTO templateDTO = templateMapper.getTemplateByAliasAndBusinessId(templateAlias,messageBusinessDTO.getBusinessId());
            if (templateDTO == null) {
                bizResult.setResultCode(InvokeResult.TEMPLATE_BUSINESS_ERROR.getCode());
                bizResult.setResultMessage(InvokeResult.TEMPLATE_BUSINESS_ERROR.getMessage());
                return bizResult;
            }

            /**接入方-业务校验 后续会去除callerBusiness关联**/
           /* CallerBusinessDTO callerBusinessDTO = callerBusinessMapper.getCallerBusinessRelation(callerId, messageBusinessDTO.getBusinessId());
            if (callerBusinessDTO == null) {
                bizResult.setResultCode(InvokeResult.CALLER_BUSINESS_ERROR.getCode());
                bizResult.setResultMessage(InvokeResult.CALLER_BUSINESS_ERROR.getMessage());
                return bizResult;
            }*/

            /**模板通知流控**/
            String sendTemplateKey=String.format("SMS:SENDMESSAGE-TEMPLATE-%s,%s,%s,%s", callerId, mobile, templateAlias, LocalDateTime.now().toLocalDate().toString());
            if (!checkTemplateSendInterval(sendTemplateKey, templateDTO.getIntervalTime(), templateDTO.getMaxCount(), templateDTO.getTotalMaxCount())) {
                bizResult.setResultCode(InvokeResult.TEMPLATE_FREQUENTLY_ERROR.getCode());
                bizResult.setResultMessage(InvokeResult.TEMPLATE_FREQUENTLY_ERROR.getMessage());
                return bizResult;
            }

            /**业务通知流控**/
            String sendBusinessKey=String.format("SMS:SENDMESSAGE-BUSINESS-%s,%s,%s,%s", callerId, mobile, businessAlias, LocalDateTime.now().toLocalDate().toString());
            if(!checkBusinessSendInterval(sendBusinessKey,messageBusinessDTO.getTotalMaxCount())){
                bizResult.setResultCode(InvokeResult.BUSINESS_FREQUENTLY_ERROR.getCode());
                bizResult.setResultMessage(InvokeResult.BUSINESS_FREQUENTLY_ERROR.getMessage());
                return bizResult;
            }

            /**生成验证码**/
            if (templateDTO.getMessageKind() == MessageKind.Code.getCode()) {
                //生成验证码，匹配到参数列表
                String tokenId = extroInfo.containsKey("tokenId") ? (String) extroInfo.get("tokenId") : mobile;
                //key 增加mobile区分
                String codeKey = Md5HashHelper.computeHash(String.format("%s-%s-%s-%s", callerId,businessAlias, tokenId,mobile));
                int expireSecond=messageBusinessDTO.getExpireSecond() <= 0 ? messageQueueProperties.getCodeExpireTime() : messageBusinessDTO.getExpireSecond();
                tempPara = securityCodeBiz.createSecurityCode(callerId, codeKey, LocalDateTime.now().plusSeconds(expireSecond));
                bizResult.setResultObject(tokenId);

                //清除缓存中的验证次数
                String verifyNumKey = String.format("verifyCode-%s-%s-%s", codeKey, businessAlias, LocalDateTime.now().toLocalDate());
                redisUtil.del(verifyNumKey);
            }
            /**模板填充**/
            String smsTemplate= templateDTO.getContent();
            String smsContent=templateDTO.getContent();
            if(StringUtils.isNotEmpty(tempPara)){
                String arr[]=tempPara.trim().split("\\|");
                //{2},{0},{1:yyyy年MM月dd日HH时} 这个格式,
                Pattern p = Pattern.compile("\\{(\\d+)(:.+?)?\\}");
                Matcher m = p.matcher(smsTemplate);
                ArrayList<String> placeHoldOrder = new ArrayList<>();
                while (m.find()) {
                    placeHoldOrder.add(m.group(1));
                }
                if(CollectionUtils.isNotEmpty(placeHoldOrder)){
                    if(placeHoldOrder.size()>arr.length){
                        bizResult.setResultCode(InvokeResult.PARAM_NUM_ERROR.getCode());
                        bizResult.setResultMessage(InvokeResult.PARAM_NUM_ERROR.getMessage());
                        return bizResult;
                    }
                    String[] orderArr=new String[placeHoldOrder.size()];
                    for(int i=0;i<placeHoldOrder.size();i++){
                        int order=Integer.parseInt(placeHoldOrder.get(i));
                        orderArr[i]=arr[order];
                    }
                    String regex = "\\{(.+?)\\}";
                    smsTemplate=smsTemplate.replaceAll(regex, "%s");
                    smsContent = String.format(smsTemplate,orderArr);
                }
            }else if(null !=extroInfo.get("paramMap")){//Map型模板参数
                Map<String,String> paramMap= (Map<String, String>) extroInfo.get("paramMap");
                for(Map.Entry<String,String> entry:paramMap.entrySet()){
                    smsTemplate=smsTemplate.replaceAll("\\{(\\s)*(?i)"+entry.getKey()+"(\\s)*\\}",entry.getValue().trim());
                }
                smsContent=smsTemplate;
            }

            if (smsContent.length() > messageQueueProperties.getMessageConatentMaxSize()) {
                bizResult.setResultCode(InvokeResult.CONTENTSIZE_ERROR.getCode());
                bizResult.setResultMessage(InvokeResult.CONTENTSIZE_ERROR.getMessage());
                return bizResult;
            }

            /**监测敏感词**/
            smsContent = sensitiveWordBiz.filterSensitiveWord(smsContent, templateDTO.getFilterRule());
            if(StringUtils.isBlank(smsContent)){
                bizResult.setResultCode(InvokeResult.SENSITIVEWORD_FORBIDEN.getCode());
                bizResult.setResultMessage(InvokeResult.SENSITIVEWORD_FORBIDEN.getMessage());
                return bizResult;
            }

            String callerIp = "";
            String recipientIp = "";
            String requestUrl = "";
            String directory = "";
            String hostName = "";
            if (extroInfo != null) {
                callerIp = substring((String) extroInfo.get("requestIp"), 0, 20);
                recipientIp = substring((String)extroInfo.get("recipientIp"), 0, 20);
                requestUrl = substring((String)extroInfo.get("requestUrl"), 0, 1000);
                directory = substring((String)extroInfo.get("directory"), 0, 1000);
                hostName = substring((String)extroInfo.get("hostName"), 0, 20);
            }


            /**
             * 入库操作
             */
            SMSMessageDTO messageDTO = new SMSMessageDTO();
            messageDTO.setMessageId(messageId);
            messageDTO.setCallerId(callerId);
            messageDTO.setActive(true);
            messageDTO.setBusinessId(messageBusinessDTO.getBusinessId());
            messageDTO.setContent(smsContent);
            //contentType 同 messageKind
            messageDTO.setContentType(templateDTO.getMessageKind());
            messageDTO.setDirectory(directory);
            messageDTO.setHostName(hostName);
            messageDTO.setRecipient(mobile);
            messageDTO.setRequestIp(callerIp);
            messageDTO.setRecipientIp(recipientIp);
            messageDTO.setRequestUrl(requestUrl);
            messageDTO.setStatus(MessageSendStatus.INIT.getCode());
            messageDTO.setTemplateId(templateDTO.getTemplateId());
            Timestamp timestamp=Timestamp.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
            messageDTO.setInsertTime(timestamp);
            messageDTO.setUpdateTime(timestamp);

            /**黑名单校验**/
            if (templateDTO.getMessageKind() != MessageKind.Code.getCode() && blackListBiz.checkBlackList(mobile)) {

                bizResult.setResultCode(InvokeResult.BLACK_ERROR.getCode());
                bizResult.setResultMessage(InvokeResult.BLACK_ERROR.getMessage());

                //黑名单记录入库
                messageDTO.setStatus(MessageSendStatus.BLACK.getCode());
                smsMessageMapper.save(messageDTO);

                return bizResult;
            }

            smsMessageMapper.save(messageDTO);

            /**发送记录缓存**/
            sendcacheRecord(sendTemplateKey, UUID.randomUUID().toString());
            sendcacheRecord(sendBusinessKey, UUID.randomUUID().toString());

            //通过消息2.0推送消息
            boolean publishResult;
            if (templateDTO.getMessageKind() == MessageKind.Code.getCode())
                publishResult = syncProducer.publish(messageQueueProperties.getMessageCodeTopic(), new ProducerData(JSON.toJSONString(messageDTO)));
            else
                publishResult = syncProducer.publish(messageQueueProperties.getMessageNomalTopic(), new ProducerData(JSON.toJSONString(messageDTO)));
            if (!publishResult) {
                //提交失败,更新message的status为  提交失败
                bizResult.setResultCode(InvokeResult.FAIL.getCode());
                bizResult.setResultMessage(InvokeResult.FAIL.getMessage());

                LocalDateTime end=LocalDateTime.now();
                //5min 之前
                LocalDateTime start=end.minusMinutes(5);
                smsMessageMapper.changeMessageStatus(messageId,MessageSendStatus.SUBMITFAIL.getCode(),Timestamp.from(start.atZone(ZoneId.systemDefault()).toInstant()),Timestamp.from(end.atZone(ZoneId.systemDefault()).toInstant()));

                return bizResult;
            }

            bizResult.setResultCode(InvokeResult.SUCCESS.getCode());
            bizResult.setResultMessage(InvokeResult.SUCCESS.getMessage());
            return bizResult;

        } catch (Exception ex) {
            bizResult.setResultCode(InvokeResult.SYS_ERROR.getCode());
            bizResult.setResultMessage(InvokeResult.SYS_ERROR.getMessage());
            logger.error("sendMessage throw exception", ex);
        }
        return bizResult;
    }

    public BizResult sendForBatchSend(TimedSendDTO timedSendDTO) {
        logger.info("MessageBiz send message for batchSend the all paramters:"+ JSONObject.toJSONString(timedSendDTO));
        BizResult bizResult = new BizResult();
        if(StringUtils.isEmpty(timedSendDTO.getRecipient())||!RegexHelper.isMobile(timedSendDTO.getRecipient())){
            bizResult.setResultCode(InvokeResult.MOBILE_ERROR.getCode());
            bizResult.setResultMessage(InvokeResult.MOBILE_ERROR.getMessage());
            return bizResult;
        }
        if (timedSendDTO.getContent().length() > messageQueueProperties.getMessageConatentMaxSize()) {
            bizResult.setResultCode(InvokeResult.CONTENTSIZE_ERROR.getCode());
            bizResult.setResultMessage(InvokeResult.CONTENTSIZE_ERROR.getMessage());
            return bizResult;
        }
        try {
            String businessAlias=timedSendDTO.getBusinessAlias();
            String templateAlias=timedSendDTO.getTemplateAlias();
            int callerId=timedSendDTO.getCallerId();
            String recipient=timedSendDTO.getRecipient();

            /**业务查询**/
            MessageBusinessDTO messageBusinessDTO = messageBusinessMapper.getBussinessByAliasAndMessageType(businessAlias,MessageType.MESSAGE.getCode());
            if (messageBusinessDTO == null) {
                bizResult.setResultCode(InvokeResult.BUSINESS_ERROR.getCode());
                bizResult.setResultMessage(InvokeResult.BUSINESS_ERROR.getMessage());
                return bizResult;
            }
            int businessId=messageBusinessDTO.getBusinessId();

            /**模板查询**/
            MessageTemplateDTO templateDTO = templateMapper.getTemplateByAliasAndBusinessId(templateAlias,businessId);
            if (templateDTO == null) {
                bizResult.setResultCode(InvokeResult.TEMPLATE_BUSINESS_ERROR.getCode());
                bizResult.setResultMessage(InvokeResult.TEMPLATE_BUSINESS_ERROR.getMessage());
                return bizResult;
            }
            int templateId=templateDTO.getTemplateId();

            /**模板通知流控**/
            String sendTemplateKey=String.format("SMS:SENDMESSAGE-TEMPLATE-%s,%s,%s,%s", callerId, recipient, templateAlias, LocalDateTime.now().toLocalDate().toString());
            if (!checkTemplateSendInterval(sendTemplateKey, templateDTO.getIntervalTime(), templateDTO.getMaxCount(), templateDTO.getTotalMaxCount())) {
                bizResult.setResultCode(InvokeResult.TEMPLATE_FREQUENTLY_ERROR.getCode());
                bizResult.setResultMessage(InvokeResult.TEMPLATE_FREQUENTLY_ERROR.getMessage());
                return bizResult;
            }

            /**业务通知流控**/
            String sendBusinessKey=String.format("SMS:SENDMESSAGE-BUSINESS-%s,%s,%s,%s", callerId, recipient, businessAlias, LocalDateTime.now().toLocalDate().toString());
            if(!checkBusinessSendInterval(sendBusinessKey,messageBusinessDTO.getTotalMaxCount())){
                bizResult.setResultCode(InvokeResult.BUSINESS_FREQUENTLY_ERROR.getCode());
                bizResult.setResultMessage(InvokeResult.BUSINESS_FREQUENTLY_ERROR.getMessage());
                return bizResult;
            }

            String messageId = UUID.randomUUID().toString();
            SMSMessageDTO messageDTO = new SMSMessageDTO();
            BeanUtils.copyProperties(timedSendDTO, messageDTO);
            /**
             * 入库操作
             */
            messageDTO.setMessageId(messageId);
            messageDTO.setActive(true);
            messageDTO.setBusinessId(businessId);
            messageDTO.setTemplateId(templateId);
            //contentType 同 messageKind
            messageDTO.setContentType(templateDTO.getMessageKind());
            messageDTO.setStatus(MessageSendStatus.INIT.getCode());
            Timestamp timestamp=Timestamp.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
            messageDTO.setInsertTime(timestamp);
            messageDTO.setUpdateTime(timestamp);

            /**黑名单校验**/
            if (blackListBiz.checkBlackList(messageDTO.getRecipient())) {
                bizResult.setResultCode(InvokeResult.BLACK_ERROR.getCode());
                bizResult.setResultMessage(InvokeResult.BLACK_ERROR.getMessage());
                //黑名单记录入库
                messageDTO.setStatus(MessageSendStatus.BLACK.getCode());
                smsMessageMapper.save(messageDTO);
                return bizResult;
            }

            smsMessageMapper.save(messageDTO);
            /**发送记录缓存**/
            sendcacheRecord(sendTemplateKey, UUID.randomUUID().toString());
            sendcacheRecord(sendBusinessKey, UUID.randomUUID().toString());

            //通过消息2.0推送消息
            boolean publishResult;
            publishResult = syncProducer.publish(messageQueueProperties.getMessageNomalTopic(), new ProducerData(JSON.toJSONString(messageDTO)));
            if (!publishResult) {
                //提交失败,更新message的status为  提交失败
                bizResult.setResultCode(InvokeResult.FAIL.getCode());
                bizResult.setResultMessage(InvokeResult.FAIL.getMessage());

                LocalDateTime end=LocalDateTime.now();
                //5min 之前
                LocalDateTime start=end.minusMinutes(5);
                smsMessageMapper.changeMessageStatus(messageId,MessageSendStatus.SUBMITFAIL.getCode(),Timestamp.from(start.atZone(ZoneId.systemDefault()).toInstant()),Timestamp.from(end.atZone(ZoneId.systemDefault()).toInstant()));
                return bizResult;
            }

            bizResult.setResultCode(InvokeResult.SUCCESS.getCode());
            bizResult.setResultMessage(InvokeResult.SUCCESS.getMessage());
            return bizResult;
        } catch (Exception ex) {
            bizResult.setResultCode(InvokeResult.SYS_ERROR.getCode());
            bizResult.setResultMessage(InvokeResult.SYS_ERROR.getMessage());
            logger.error("send message for batchSend error", ex);
        }
        return bizResult;
    }

    /**
     *批量发送接口,无论是否定时,全部入库
     * job 数据库控制流速 提交成功发送通知邮件
     *author cash
     *create 2017/7/31-16:56
    **/

    public BizResult batchSendSave(BatchSendRequest request){
        Long start=System.currentTimeMillis();
        logger.info("MessageBiz batchSend save the  message,the complete param: "+JSONObject.toJSONString(request));
        BizResult bizResult = new BizResult();
        List<String> mobiles=request.getMobiles();
        List<String> parameters=request.getParameters();

        String businessAlias=request.getBusinessAlias();
        String templateAlias=request.getTemplateAlias();

        int businessId;
        int callerId=request.getCallerId();
        /**业务、模板、callerId 非空限制**/
        if(StringUtils.isEmpty(request.getBusinessAlias())||
                StringUtils.isEmpty(request.getTemplateAlias())||
                request.getCallerId()<0||
                CollectionUtils.isEmpty(mobiles)){

            bizResult.setResultCode(InvokeResult.PARAM_ERROR.getCode());
            bizResult.setResultMessage(InvokeResult.PARAM_ERROR.getMessage());
            return bizResult;
        }

        /**业务校验**/
        MessageBusinessDTO messageBusinessDTO = messageBusinessMapper.getBussinessByAliasAndMessageType(businessAlias,MessageType.MESSAGE.getCode());
        if (messageBusinessDTO == null) {
            bizResult.setResultCode(InvokeResult.BUSINESS_ERROR.getCode());
            bizResult.setResultMessage(InvokeResult.BUSINESS_ERROR.getMessage());
            return bizResult;
        }
        businessId=messageBusinessDTO.getBusinessId();

        /**模板-业务校验**/
        MessageTemplateDTO templateDTO = templateMapper.getTemplateByAliasAndBusinessId(templateAlias,businessId);
        if (templateDTO == null) {
            bizResult.setResultCode(InvokeResult.TEMPLATE_BUSINESS_ERROR.getCode());
            bizResult.setResultMessage(InvokeResult.TEMPLATE_BUSINESS_ERROR.getMessage());
            return bizResult;
        }

        /**接入方-业务校验  后续会去除callerBusiness关联**/
        /*CallerBusinessDTO callerBusinessDTO = callerBusinessMapper.getCallerBusinessRelation(callerId, businessId);
        if (callerBusinessDTO == null) {
            bizResult.setResultCode(InvokeResult.CALLER_BUSINESS_ERROR.getCode());
            bizResult.setResultMessage(InvokeResult.CALLER_BUSINESS_ERROR.getMessage());
            return bizResult;
        }*/

        /**手机个数限制**/
        if(mobiles.size()>gatewayProperties.getBatchMaxSend()){
            bizResult.setResultCode(InvokeResult.TOO_MANY_MOBILE_ERROR.getCode());
            bizResult.setResultMessage(InvokeResult.TOO_MANY_MOBILE_ERROR.getMessage());
            return bizResult;
        }

        String smsTemplate= templateDTO.getContent();
        logger.info("MessageBiz batch send message,the sms template: "+smsTemplate);

        List<TimedSendList> timedSendLists=new ArrayList<>(mobiles.size());

        //{2},{0},{1:yyyy年MM月dd日HH时} 这个格式,
        Pattern p = Pattern.compile("\\{(\\d+)(:.+?)?\\}");
        Matcher m = p.matcher(smsTemplate);
        //模板 {参数} 计数
        int templateArgCnt=0;
        while (m.find()) {
          templateArgCnt++;
        }

        if(CollectionUtils.isNotEmpty(parameters)&& /**手机号、参数list个数一致 **/
                parameters.size()!=mobiles.size()){
            bizResult.setResultCode(InvokeResult.MOBILE_AND_PARAM_NUM_NOT_MATCH_ERROR.getCode());
            bizResult.setResultMessage(InvokeResult.MOBILE_AND_PARAM_NUM_NOT_MATCH_ERROR.getMessage());
            return bizResult;
        }else if(CollectionUtils.isEmpty(parameters)){
            if(templateArgCnt>0){
                bizResult.setResultCode(InvokeResult.PARAM_NUM_ERROR.getCode());
                bizResult.setResultMessage(InvokeResult.PARAM_NUM_ERROR.getMessage());
                return bizResult;
            }else{
                for(int i=0;i<mobiles.size();i++){
                    TimedSendList timedSend=new TimedSendList();
                    timedSend.setRecipient(mobiles.get(i));
                    timedSend.setContent(smsTemplate);
                    timedSendLists.add(timedSend);
                }
            }
        }else {
            String arr[]=parameters.get(0).trim().split("\\|");
            if(arr.length<templateArgCnt){
                bizResult.setResultCode(InvokeResult.PARAM_NUM_ERROR.getCode());
                bizResult.setResultMessage(InvokeResult.PARAM_NUM_ERROR.getMessage());
            }
            String regex = "\\{(.+?)\\}";
            String smsTemplateCont=smsTemplate.replaceAll(regex, "%s");
            for(int i=0;i<parameters.size();i++){
                String paraArr[]=parameters.get(i).trim().split("\\|");
                String content = String.format(smsTemplateCont,paraArr);

                TimedSendList timedSend=new TimedSendList();
                timedSend.setRecipient(mobiles.get(i));
                timedSend.setContent(content);
                timedSendLists.add(timedSend);
            }
        }

        TimedSendSave timedSendSave=new TimedSendSave();

        timedSendSave.setBusinessAlias(businessAlias);
        timedSendSave.setTemplateAlias(templateAlias);
        timedSendSave.setCallerId(callerId);
        timedSendSave.setTimedSendList(timedSendLists);
        timedSendSave.setDirectory(substring(request.getDirectory(),0,1000));
        timedSendSave.setHostName(request.getHostName());
        timedSendSave.setRecipientIp(request.getRecipientIp());
        timedSendSave.setRequestIp(request.getRecipientIp());
        timedSendSave.setRequestUrl(substring(request.getRequestUrl(),0,1000));

        timedSendSave.setIsSend(WhetherEnum.No.getCode());

        if(null!=request.getTiming()&&request.getTiming()>0){
            timedSendSave.setIsTimed(WhetherEnum.YES.getCode());
            Timestamp timing=new Timestamp(request.getTiming());
            timedSendSave.setTiming(timing);
        }else {
            timedSendSave.setIsTimed(WhetherEnum.No.getCode());
            timedSendSave.setTiming(Timestamp.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        }

        Long mid=System.currentTimeMillis();
        logger.info("--------------batchsend 参数处理耗时："+(mid-start));

        try {
            //批量发送入库
            int cnt=timedSendMapper.batchSaveTimedSend(timedSendSave);
            bizResult.setResultCode(InvokeResult.SUCCESS.getCode());
            bizResult.setResultMessage(InvokeResult.SUCCESS.getMessage());

            Long mid2=System.currentTimeMillis();
            logger.info("--------------batchsend 批量入库耗时："+(mid2-mid));

            /**通知邮件 增加个是否开启的开关**/
            if(cnt>0 && StringUtils.isNotEmpty(request.getNotifyEmail()) &&
                    gatewayProperties.getBatchSendNotifyEmailFlag()==WhetherEnum.YES.getCode() &&
                    StringUtils.isNotEmpty(gatewayProperties.getSendMailAddr())){
                if(!RegexHelper.isEmail(request.getNotifyEmail())){
                    bizResult.setResultMessage(InvokeResult.SUCCESS.getMessage()+",邮件地址错误");
                }else{
                    sendNotifyEmail(request.getNotifyEmail(),request.getTiming(),smsTemplate,mobiles.size(),request.getUserId());
                }
            }
        } catch (SQLException e){
            bizResult.setResultCode(InvokeResult.SYS_ERROR.getCode());
            bizResult.setResultMessage(InvokeResult.SYS_ERROR.getMessage());
            logger.error("batchSend error", e);
        }

        return  bizResult;
    }

    @Async("SimpleAsync")
    public void sendNotifyEmail(String notifyEmail,Long timing,String smsTemplate,int total,String userId){
        try {
            //您好，***（短信发送人姓名）刚才通过短信平台发送短信：******（短信内容），发送给**名用户，请知悉。
            StringBuilder emailContent=new StringBuilder("您好,");
            String operator="";
            /***根据userId获取用户信息**/
            if(StringUtils.isNotEmpty(userId) &&
                    StringUtils.isNotEmpty(gatewayProperties.getCasSystemAppId()) &&
                    StringUtils.isNotEmpty(gatewayProperties.getCasSystemIsProduct())){
                String result = permissionService.load(gatewayProperties.getCasSystemAppId(),userId, gatewayProperties.getCasSystemIsProduct());
                logger.debug(String.format("根据userId:%s,调用cas服务,获取用户信息返回：%s",userId,result));
                if("0".equals(result)||"error".equals(result)){
                    logger.error(String.format("根据userId:%s,调用cas服务,获取用户信息异常",userId));
                }else{
                    result=result.substring(1,result.length()-1);
                    result = result.replace("\\\"", "\"");
                    JSONObject jsonObject = JSON.parseObject(result);
                    operator= (String) jsonObject.get("loginName");
                }
            }else{
                logger.error(String.format("提交批量发送短信后发送通知邮件to:%s 获取操作人失败：可能未登陆或未配置cas信息",notifyEmail));
            }

            if(StringUtils.isEmpty(operator)){
                if(StringUtils.isNotEmpty(userId)){
                    operator=String.format("userId:%s ",userId);
                }else operator=" ";
            }

            emailContent.append(operator);
            if(null!=timing&&timing>0){
                emailContent.append(String.format("通过短信平台提交将于 %s 发送的短信: ",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(timing))));
            }else {
                emailContent.append("通过短信平台提交发送短信: ");
            }
            emailContent.append("【");
            emailContent.append(smsTemplate);
            emailContent.append("】");
            emailContent.append(String.format(" 给%s名用户,请知悉。",total));
            sendmail.sendSimpleMail(gatewayProperties.getSendMailAddr(),notifyEmail,batchSendEmailTitle,emailContent.toString());
        } catch (Exception e) {
            logger.error("batchSend send mail error", e);
        }
    }

    /**
     * 验证验证码
     *
     * @param callerId
     * @param mobile
     * @param businessAlias
     * @param verifyValue
     * @param checkOnly
     * @param extroInfo
     * @return
     */
    public BizResult verifyMessageSecurityCode(int callerId, String mobile, String businessAlias, String verifyValue, boolean checkOnly, Map<String, String> extroInfo) {
        BizResult bizResult = new BizResult();
        if (!verifMessageSecurityCodeParams(callerId, mobile, businessAlias, verifyValue)) {
            bizResult.setResultCode(InvokeResult.PARAM_ERROR.getCode());
            bizResult.setResultMessage(InvokeResult.PARAM_ERROR.getMessage());
            return bizResult;
        }
        //2017/6/15  老系统兼容修改 checkOnly 为true 只校验验证码是6位num格式
        if(checkOnly){
            String regex="\\d{6}";
            if(verifyValue.matches(regex)){
                bizResult.setResultCode(InvokeResult.SUCCESS.getCode());

            }else{
                bizResult.setResultCode(InvokeResult.FAIL.getCode());
                bizResult.setResultMessage("验证失败");
            }
            return bizResult;
        }

        try {
            MessageBusinessDTO messageBusinessDTO = messageBusinessMapper.getBussinessByAlias(businessAlias);
            if (messageBusinessDTO == null) {
                bizResult.setResultCode(InvokeResult.BUSINESS_ERROR.getCode());
                bizResult.setResultMessage(InvokeResult.BUSINESS_ERROR.getMessage());
                return bizResult;
            }

            /**callerBusiness 关联校验去掉**/
           /* CallerBusinessDTO callerBusinessDTO = callerBusinessMapper.getCallerBusinessRelation(callerId, messageBusinessDTO.getBusinessId());
            if (callerBusinessDTO == null) {
                bizResult.setResultCode(InvokeResult.CALLER_BUSINESS_ERROR.getCode());
                bizResult.setResultMessage(InvokeResult.CALLER_BUSINESS_ERROR.getMessage());
                return bizResult;
            }*/

            String tokenId = extroInfo.containsKey("tokenId") ? extroInfo.get("tokenId") : mobile;
            //key 增加mobile区分
            String codeKey = Md5HashHelper.computeHash(String.format("%s-%s-%s-%s", callerId, businessAlias, tokenId,mobile));

            String verifyCodeKey = String.format("verifyCode-%s-%s-%s", codeKey, businessAlias, LocalDateTime.now().toLocalDate());

            int verifyCount = redisUtil.get(verifyCodeKey) == null ? 0 : Integer.parseInt(String.valueOf(redisUtil.get(verifyCodeKey)));

            //判断验证码是否超过最大校验频次
            if (messageBusinessDTO.getVerifyMaxCount() > 0 && verifyCount >= messageBusinessDTO.getVerifyMaxCount()) {
                logger.warn(String.format("校验验证码超过最大校验频次,手机号:%s,输入验证码:%s,验证业务:%s", mobile, verifyValue, businessAlias));
                bizResult.setResultCode(InvokeResult.FREQUENCY_ERROR.getCode());
                bizResult.setResultMessage(InvokeResult.FREQUENCY_ERROR.getMessage());
                return bizResult;
            }

            boolean verifyResult = securityCodeBiz.verifySecurityCode(callerId, codeKey, verifyValue, checkOnly);
            if (!verifyResult) {
                logger.warn(String.format("校验验证码,手机号:%s,输入验证码:%s,验证结果:%s", mobile, verifyValue, false));
                redisUtil.incr(verifyCodeKey);
                bizResult.setResultCode(InvokeResult.VERIFY_ERROR.getCode());
                bizResult.setResultMessage(InvokeResult.VERIFY_ERROR.getMessage());
                return bizResult;
            }
            redisUtil.incr(verifyCodeKey);
            bizResult.setResultCode(InvokeResult.SUCCESS.getCode());
            bizResult.setResultMessage(InvokeResult.SUCCESS.getMessage());
            return bizResult;

        } catch (Exception ex) {
            bizResult.setResultCode(InvokeResult.SYS_ERROR.getCode());
            bizResult.setResultMessage(InvokeResult.SYS_ERROR.getMessage());
            logger.error("verifyMessageSecurityCode throw exception", ex);
        }

        return bizResult;
    }

    /**
     * 消息发送流控记录
     *
     * @param key
     * @param value
     */
    private void sendcacheRecord(String key, String value) {
        redisUtil.addzSet(key, value, System.currentTimeMillis());
    }

    private String substring(String str, int start, int end) {
        if (StringUtils.isBlank(str)) {
            return "";
        }
        if (str.length() > end) {
            return str.substring(start, end);
        }
        return str;
    }

    /**
     * 校验模板发送间隔
     *
     * @param key
     * @param intervalSecond
     * @param intervalMaxCount
     * @param intervalTotalMaxCount
     * @return
     */
    private boolean checkTemplateSendInterval(String key, int intervalSecond, int intervalMaxCount, int intervalTotalMaxCount) {
        if ((intervalSecond <= 0 || intervalMaxCount <= 0) && intervalTotalMaxCount <= 0)
            return true;
        /*校验时间间隔*/
        if (intervalSecond > 0 && intervalMaxCount>0) {
            Set<ZSetOperations.TypedTuple<String>> sendList = redisUtil.rangeWithScores(key, 0, -1);

            if (CollectionUtils.isEmpty(sendList))
                return true;

            LocalDateTime now = LocalDateTime.now();
            LocalDateTime before=now.minusSeconds(intervalSecond);
            long sencCount = redisUtil.zCount(key, before.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
                    now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
            if(sencCount>=intervalMaxCount){
                return false;
            }


        }
        /*校验当天总发送次数*/
        if (intervalTotalMaxCount > 0) {
            LocalTime midnight = LocalTime.MIDNIGHT;
            LocalDate today = LocalDate.now();
            LocalDateTime zeroTime = LocalDateTime.of(today, midnight);
            LocalDateTime now = LocalDateTime.now();

            long sencCount = redisUtil.zCount(key, zeroTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
                    now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
            if (sencCount >= intervalTotalMaxCount)
                return false;
        }
        return true;
    }

    /***
     * 校验业务的发送次数
     * @param key
     * @param intervalTotalMaxCount
     * @return
     */
    private boolean checkBusinessSendInterval(String key, int intervalTotalMaxCount) {
        if(intervalTotalMaxCount<=0) return true;
        /*校验当天总发送次数*/
        if (intervalTotalMaxCount > 0) {
            LocalTime midnight = LocalTime.MIDNIGHT;
            LocalDate today = LocalDate.now();
            LocalDateTime zeroTime = LocalDateTime.of(today, midnight);
            LocalDateTime now = LocalDateTime.now();

            long sencCount = redisUtil.zCount(key, zeroTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
                    now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
            if (sencCount >= intervalTotalMaxCount)
                return false;
        }

        return true;
    }


    /*
    验证验证码参数校验
     */
    private boolean verifMessageSecurityCodeParams(int callerId, String mobile, String businessAlias, String verifyValue) {
        if (callerId <= 0)
            return false;
        if (StringUtils.isBlank(mobile))
            return false;
        if (StringUtils.isBlank(businessAlias))
            return false;
        if (StringUtils.isBlank(verifyValue))
            return false;
        if (!RegexHelper.isMobile(mobile))
            return false;
        return true;
    }

    /**
     * 验证参数
     *
     * @param callerId
     * @param businessAlias
     * @param templateAlias
     * @return
     */
    private boolean verifySendParams(int callerId, String mobile, String businessAlias, String templateAlias,MessageType messageType) {
        if (callerId <= 0)
            return false;
        if (StringUtils.isBlank(businessAlias))
            return false;
        if (StringUtils.isBlank(templateAlias))
            return false;
        if(messageType==MessageType.EMAIL) {
            if (!RegexHelper.isEmail(mobile))
                return false;
        }else{
            if (!RegexHelper.isMobile(mobile))
                return false;
        }
        return true;
    }


    /**
     * 是否在配置的号段中
     * @return
     */
    private boolean isInConfigNumSegment(String mobile){
        for (String prefix : gatewayProperties.getChinaMobile()) {
            int prefixSize = prefix.length();
            if (mobile.substring(0, prefixSize).equals(prefix)) {
                return true;
            }
        }
        for (String prefix : gatewayProperties.getChinaUnicom()) {
            int prefixSize = prefix.length();
            if (mobile.substring(0, prefixSize).equals(prefix)) {
                return true;
            }
        }
        for (String prefix : gatewayProperties.getChinaTelecom()) {
            int prefixSize = prefix.length();
            if (mobile.substring(0, prefixSize).equals(prefix)) {
                return true;
            }
        }

        return false;
    }


}
