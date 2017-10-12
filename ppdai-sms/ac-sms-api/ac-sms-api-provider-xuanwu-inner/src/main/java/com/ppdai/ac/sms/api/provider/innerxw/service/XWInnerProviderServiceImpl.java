package com.ppdai.ac.sms.api.provider.innerxw.service;

import com.alibaba.fastjson.JSONObject;
import com.dianping.cat.Cat;
import com.dianping.cat.message.Message;
import com.dianping.cat.message.Transaction;
import com.ppdai.ac.sms.api.provider.innerxw.domain.biz.XWProviderBiz;
import com.ppdai.ac.sms.api.provider.innerxw.request.SendMsg;
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
 * create 2017-05-04-16:57
 **/
@Api(value = "xwInnerProviderService", description = "the xwInnerProviderService")
@Controller
@RequestMapping(value = "/xwInnerProviderService")
public class XWInnerProviderServiceImpl implements SmsProviderService {

    private final static Logger logger = LoggerFactory.getLogger(XWInnerProviderServiceImpl.class);

    @Autowired
    XWProviderBiz xwProviderBiz;

    @ApiOperation(value = "玄武内部平台短信发送", notes = "xwProviderService.send", response = SmsResponse.class, tags = {"xwProviderService.send"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "玄武内部平台短信发送", response = SmsResponse.class)})

    @Override
    @RequestMapping(value = "/send",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody SmsResponse send(@ApiParam(value = "request", required = true) @RequestBody SmsRequest request) {
        SmsResponse sendResponse=new SmsResponse();
        SendMsg sendMsg=new SendMsg();
        logger.info("玄武短信发送,入参："+ JSONObject.toJSONString(request));
        Map<String,String> params=request.getParams();
        if(null!=params){
            if(StringUtils.isEmpty(params.get("name"))||StringUtils.isEmpty(params.get("password"))){
                logger.error("玄武短信发送,参数配置错误");
                sendResponse.setResultCode(InvokeResult.FAIL.getCode());
                sendResponse.setResultMessage("玄武短信发送,参数配置错误");
                return sendResponse;
            }

            if(null!=params.get("mobile")){
                String mobiles=params.get("mobile");
                String[] arr;
                arr = mobiles.split(",");
                sendMsg.setMobiles(java.util.Arrays.asList(arr));
            }
            if(null!=params.get("content")){
                sendMsg.setContent( params.get("content"));
            }
            if(null!=params.get("name")){
                sendMsg.setName( params.get("name"));
            }
            if(null!=params.get("password")){
                sendMsg.setPassword( params.get("password"));
            }
            if(null!=params.get("recordId")){
                sendMsg.setMessageId(params.get("recordId"));
            }
            if(null!=params.get("extendNo")){
                sendMsg.setExtendNo(params.get("extendNo"));
            }

        }else{
            logger.error("玄武短信发送,参数为空");
            sendResponse.setResultCode(InvokeResult.FAIL.getCode());
            sendResponse.setResultMessage("玄武短信发送,参数为空");
            return sendResponse;

        }
        Transaction transaction = Cat.newTransaction("XuanwuSmsSend ", params.get("recordId"));
        long startMillis=System.currentTimeMillis();
        try {
            sendResponse=xwProviderBiz.send(sendMsg);

            long endMillis=System.currentTimeMillis();
            Cat.logMetricForDuration("服务商接口调用耗时,XuanwuInnerSmsSend",
                    endMillis-startMillis);
            transaction.setStatus(Message.SUCCESS);
        } catch (Exception e) {
            Cat.logError(e);
        } finally {
            transaction.complete();
        }
        return sendResponse;
    }
}
