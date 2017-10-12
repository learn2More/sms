package com.ppdai.ac.sms.api.provider.baiwu.service;

import com.alibaba.fastjson.JSONObject;
import com.dianping.cat.Cat;
import com.dianping.cat.message.Message;
import com.dianping.cat.message.Transaction;
import com.ppdai.ac.sms.api.provider.baiwu.protocol.BaiwuService;
import com.ppdai.ac.sms.provider.core.api.SmsProviderService;
import com.ppdai.ac.sms.provider.core.enums.InvokeResult;
import com.ppdai.ac.sms.provider.core.request.SmsRequest;
import com.ppdai.ac.sms.provider.core.response.SmsResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * author cash
 * create 2017-05-02-13:30
 **/
@Api(value = "bwProviderService", description = "the bwProviderService")
@Controller
@RequestMapping(value = "/bwProviderService")
public class BWProviderServiceImpl implements SmsProviderService {

    private final static Logger logger = LoggerFactory.getLogger(BWProviderServiceImpl.class);

    @Autowired
    BaiwuService baiwuService;


    @ApiOperation(value = "百悟短信发送", notes = "bwProviderService.send", response = SmsResponse.class, tags = {"bwProviderService.send"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "百悟短信发送", response = SmsResponse.class)})
    @RequestMapping(value = "/send",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    SmsResponse send(@RequestBody SmsRequest request) {
        SmsResponse sendResponse=new SmsResponse();
        Map<String,String> params = request.getParams();
        logger.info("百悟短信发送,入参："+ JSONObject.toJSONString(request));
        MultiValueMap<String, String> sendRequest = new LinkedMultiValueMap<>();
        if(null!=params){
            if(StringUtils.isEmpty(params.get("corpId"))||StringUtils.isEmpty(params.get("corpPwd"))||StringUtils.isEmpty(params.get("corpService"))){
                logger.error("百悟发送短信,配置异常");
                sendResponse.setResultCode(InvokeResult.FAIL.getCode());
                sendResponse.setResultMessage("百悟发送短信,配置异常");
                return sendResponse;
            }
            Transaction transaction = Cat.newTransaction("BaiwuSmsSend ", params.get("recordId"));
            long startMillis=System.currentTimeMillis();
            try {
                if(null!=params.get("corpId")){
                    sendRequest.add("corp_id", params.get("corpId"));
                }
                if(null!=params.get("corpPwd")){
                    sendRequest.add("corp_pwd", params.get("corpPwd"));

                }
                if(null!=params.get("corpService")){
                    sendRequest.add("corp_service", params.get("corpService"));

                }
                if(null!=params.get("mobile")){
                    sendRequest.add("mobile", params.get("mobile"));

                }
                if(null!=params.get("content")){
                    sendRequest.add("msg_content", params.get("content"));

                }
                if(null!=params.get("recordId")){
                    sendRequest.add("corp_msg_id", params.get("recordId"));

                }
                //扩展码,用于上行匹配
                if(null!=params.get("extendNo")){
                    sendRequest.add("ext", params.get("extendNo"));
                }
                String  msg=baiwuService.send(sendRequest);
                if(null==msg||msg.indexOf("0#")<0){
                    logger.error("百悟发送短信,异常,接口返回："+msg);
                    sendResponse.setResultCode(InvokeResult.FAIL.getCode());
                    sendResponse.setResultMessage("百悟发送短信,异常");
                    return sendResponse;

                }
                sendResponse.setResultCode(InvokeResult.SUCCESS.getCode());
                sendResponse.setResultMessage("发送成功");
                sendResponse.setResultObject(msg);

                long endMillis=System.currentTimeMillis();
                Cat.logMetricForDuration("服务商接口调用耗时,BaiwuSmsSend",
                        endMillis-startMillis);
                transaction.setStatus(Message.SUCCESS);

            } catch (Exception e) {
                logger.info("百悟短信发送异常",e);
            } finally {
                transaction.complete();
            }

        }else{
            logger.error("百悟发送短信,参数为空");
            sendResponse.setResultCode(InvokeResult.FAIL.getCode());
            sendResponse.setResultMessage("百悟发送短信,参数为空");
            return sendResponse;

        }

        return sendResponse;
    }

}
