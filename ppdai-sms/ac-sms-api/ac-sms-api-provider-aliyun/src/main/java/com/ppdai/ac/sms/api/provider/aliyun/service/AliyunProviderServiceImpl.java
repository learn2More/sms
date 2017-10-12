package com.ppdai.ac.sms.api.provider.aliyun.service;

import com.alibaba.fastjson.JSONObject;
import com.dianping.cat.Cat;
import com.dianping.cat.message.Message;
import com.dianping.cat.message.Transaction;
import com.ppdai.ac.sms.api.provider.aliyun.domain.biz.AliyunProviderBiz;
import com.ppdai.ac.sms.api.provider.aliyun.request.SendMsgRequest;
import com.ppdai.ac.sms.api.provider.aliyun.utils.AliyunUtil;
import com.ppdai.ac.sms.provider.core.api.SmsProviderService;
import com.ppdai.ac.sms.provider.core.enums.InvokeResult;

import com.ppdai.ac.sms.provider.core.request.SmsRequest;
import com.ppdai.ac.sms.provider.core.response.SmsResponse;
import io.swagger.annotations.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * author cash
 * create 2017-05-05-15:09
 **/
@Api(value = "aliyunProviderService", description = "the aliyunProviderService")
@Controller
@RequestMapping(value = "/aliyunProviderService")
public class AliyunProviderServiceImpl implements SmsProviderService {

    private static final Logger logger = LoggerFactory.getLogger(AliyunProviderServiceImpl.class);

    @Autowired
    AliyunProviderBiz aliyunProviderBiz;


    @ApiOperation(value = "阿里云短信发送", notes = "aliyunProviderService.send", response = SmsResponse.class, tags = {"aliyunProviderService.send"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "阿里云短信发送", response = SmsResponse.class)})
    @Override
    @RequestMapping(value = "/send",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody SmsResponse send(@ApiParam(value = "request", required = true) @RequestBody SmsRequest request) {
        SmsResponse sendResponse=new SmsResponse();
        logger.info("阿里云短信发送,入参："+ JSONObject.toJSONString(request));
        Map<String,String> params=request.getParams();
        SendMsgRequest sendMsgRequest=new SendMsgRequest();
        if(null!=params){

            Transaction transaction = Cat.newTransaction("AliyunSmsSend ", params.get("recordId"));
            long startMillis=System.currentTimeMillis();
            try {
                if(StringUtils.isEmpty(params.get("accessKey"))||
                        StringUtils.isEmpty(params.get("accessSecret"))||
                        StringUtils.isEmpty(params.get("endpoint"))||
                        StringUtils.isEmpty(params.get("topicName"))){

                    logger.error("阿里云发送短信,配置参数有误");
                    sendResponse.setResultCode(InvokeResult.FAIL.getCode());
                    sendResponse.setResultMessage("阿里云发送短信,配置参数有误");
                    return sendResponse;

                }
                if(null!=params.get("mobile")){
                    String mobiles=params.get("mobile");
                    String[] arr;
                    arr = mobiles.split(",");
                    sendMsgRequest.setMobiles(java.util.Arrays.asList(arr));
                }
                //解析发送内容,按阿里格式拼装
                if(null!=params.get("content")){//templateCode==SMS_46165066||signName==Radius服务||msg==xx||code==99
                    String contentstr=params.get("content");
                    AliyunUtil.parseContent(contentstr,sendMsgRequest);
                }
                if(null!=params.get("extendNo")){
                    sendMsgRequest.setExtendNo(params.get("extendNo"));
                }
                if(null!=params.get("recordId")){
                    sendMsgRequest.setRecordId(params.get("recordId"));
                }
                if(null!=params.get("accessKey")){
                    sendMsgRequest.setAccessKey(params.get("accessKey"));
                }
                if(null!=params.get("accessSecret")){
                    sendMsgRequest.setAccessSecret(params.get("accessSecret"));
                }
                if(null!= params.get("endpoint")){
                    sendMsgRequest.setEndpoint(params.get("endpoint"));
                }
                if(null!=params.get("topicName")){
                    sendMsgRequest.setTopicName(params.get("topicName"));
                }
                sendResponse=aliyunProviderBiz.send(sendMsgRequest);

                long endMillis=System.currentTimeMillis();
                Cat.logMetricForDuration("服务商接口调用耗时,aliyunSmsSend",
                        endMillis-startMillis);
                transaction.setStatus(Message.SUCCESS);

            } catch (Exception e) {
                Cat.logError(e);
            } finally {
                transaction.complete();
            }


        }else{
            logger.error("阿里云发送短信,参数为空");
            sendResponse.setResultCode(InvokeResult.FAIL.getCode());
            sendResponse.setResultMessage("阿里云发送短信,参数为空");
            return sendResponse;
        }

        return sendResponse;
    }
}
