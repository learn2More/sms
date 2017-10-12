package com.ppdai.ac.sms.api.provider.jslt.service;

import com.alibaba.fastjson.JSONObject;
import com.dianping.cat.Cat;
import com.dianping.cat.message.Message;
import com.dianping.cat.message.Transaction;
import com.ppdai.ac.sms.api.provider.jslt.domain.biz.JSLTProviderBiz;
import com.ppdai.ac.sms.api.provider.jslt.domain.model.JSLTSignDTO;
import com.ppdai.ac.sms.api.provider.jslt.protocal.JsltService;
import com.ppdai.ac.sms.api.provider.jslt.protocal.response.SendDetail;
import com.ppdai.ac.sms.provider.core.api.SmsProviderService;
import com.ppdai.ac.sms.provider.core.dao.domain.SmsMessageRecordBiz;
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

import java.time.LocalDateTime;
import java.util.Map;

/**
 * author cash
 * create 2017-05-04-16:57
 **/
@Api(value = "jsltProviderService", description = "the jsltProviderService")
@Controller
@RequestMapping(value = "/jsltProviderService")
public class JSLTProviderServiceImpl implements SmsProviderService{

    private final static Logger logger = LoggerFactory.getLogger(JSLTProviderBiz.class);

    @Autowired
    JsltService jsltService;

    @Autowired
    JSLTProviderBiz jsltProviderBiz;

    @Autowired
    SmsMessageRecordBiz smsMessageRecordBiz;


    @ApiOperation(value = "江苏联通短信发送", notes = "jsltProviderService.send", response = SmsResponse.class, tags = {"jsltProviderService.send"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "江苏联通短信发送", response = SmsResponse.class)})
    @RequestMapping(value = "/send",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    SmsResponse send(@RequestBody SmsRequest request) {
        SmsResponse sendResponse=new SmsResponse();
        Map<String,String> params = request.getParams();

        logger.info("江苏联通短信发送,入参："+ JSONObject.toJSONString(request));
        MultiValueMap<String, String> sendRequest = new LinkedMultiValueMap<>();
        if(null!=params){
            String jslt_userid = params.get("userid");
            String jslt_account = params.get("account");
            String jslt_password = params.get("password");
            JSLTSignDTO jslt_sign = new JSLTSignDTO(jslt_account,jslt_password);
            if(StringUtils.isEmpty(jslt_userid)||StringUtils.isEmpty(jslt_account)||StringUtils.isEmpty(jslt_password)){
                logger.error("江苏联通发送短信,配置异常");
                sendResponse.setResultCode(InvokeResult.FAIL.getCode());
                sendResponse.setResultMessage("江苏联通发送短信,配置异常");
                return sendResponse;
            }
            Transaction transaction = Cat.newTransaction("JsltSmsSend ", params.get("recordId"));
            long startMillis=System.currentTimeMillis();
            try {
                sendRequest.add("userid", String.valueOf(jslt_userid));
                sendRequest.add("timestamp", jslt_sign.getTimestamp());
                sendRequest.add("sign", jslt_sign.getSign());
                if(null!=params.get("mobile")){
                    sendRequest.add("mobile", params.get("mobile"));
                }
                if(null!=params.get("content")){
                    sendRequest.add("content", params.get("content"));
                }
                sendRequest.add("action","send");
                // 下行短信发送
                SendDetail msg=jsltService.send(sendRequest);

                if(null==msg){
                    logger.error("江苏联通发送短信,异常,接口返回："+msg);
                    sendResponse.setResultCode(InvokeResult.FAIL.getCode());
                    sendResponse.setResultMessage("江苏联通发送短信,异常：" + InvokeResult.FAIL.getDescribe());
                    return sendResponse;
                }else if(!msg.getReturnstatus().equals("Success")){
                    logger.error("江苏联通发送短信,失败,接口返回："+msg);
                    sendResponse.setResultCode(InvokeResult.FAIL.getCode());
                    sendResponse.setResultMessage("江苏联通发送短信,失败：");
                    return sendResponse;
                }
                String recordExt = msg.getTaskID();
                LocalDateTime endTime=LocalDateTime.now();
                LocalDateTime startTime=endTime.minusDays(3);
                smsMessageRecordBiz.updateRecordExtByRecordId(params.get("recordId"),recordExt,startTime,endTime);
                sendResponse.setResultCode(InvokeResult.SUCCESS.getCode());
                sendResponse.setResultMessage("发送成功");
                sendResponse.setResultObject("taskid:"+ recordExt);

                long endMillis=System.currentTimeMillis();
                Cat.logMetricForDuration("服务商接口调用耗时,JsltSmsSend",
                        endMillis-startMillis);
                transaction.setStatus(Message.SUCCESS);

            } catch (Exception e) {
                logger.info("江苏联通短信发送异常",e);
            } finally {
                transaction.complete();
            }

        }else{
            logger.error("江苏联通发送短信,参数为空");
            sendResponse.setResultCode(InvokeResult.FAIL.getCode());
            sendResponse.setResultMessage("江苏联通发送短信,参数为空");
            return sendResponse;

        }

        return sendResponse;
    }

}
