package com.ppdai.ac.sms.api.provider.ccp.service;

import com.alibaba.fastjson.JSONObject;
import com.dianping.cat.Cat;
import com.dianping.cat.message.Message;
import com.dianping.cat.message.Transaction;
import com.ppdai.ac.sms.api.provider.ccp.domain.biz.CcpProviderBiz;
import com.ppdai.ac.sms.api.provider.ccp.request.CcpCallNotifyRequest;
import com.ppdai.ac.sms.api.provider.ccp.request.CcpCallVerifyCodeRequest;
import com.ppdai.ac.sms.api.provider.ccp.request.ReceiveReportRequest;
import com.ppdai.ac.sms.api.provider.ccp.response.ReceiveReportResponse;
import com.ppdai.ac.sms.provider.core.api.CallProviderService;
import com.ppdai.ac.sms.provider.core.enums.InvokeResult;
import com.ppdai.ac.sms.provider.core.request.CallRequest;
import com.ppdai.ac.sms.provider.core.response.CallResponse;
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
 * create 2017-05-15-15:28
 **/

@Api(value = "ccpProviderService", description = "the ccpProviderService")
@Controller
@RequestMapping(value = "/ccpProviderService")
public class CcpProviderServiceImpl implements CallProviderService {

    private static final Logger logger= LoggerFactory.getLogger(CcpProviderServiceImpl.class);

    @Autowired
    CcpProviderBiz ccpProviderBiz;



    @ApiOperation(value = "容联语音验证码", notes = "ccpProviderService.callVerifyCode", response = CallResponse.class, tags = {"ccpProviderService.callVerifyCode"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "容联语音验证码", response = CallResponse.class)})

    @Override
    @RequestMapping(value = "/callVerifyCode",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public CallResponse callVerifyCode(@RequestBody CallRequest request){
        CallResponse callResponse=new CallResponse();
        callResponse.setResultMessage("");

        CcpCallVerifyCodeRequest ccpCallRequest=new CcpCallVerifyCodeRequest();

        logger.info("容联语音验证码,入参："+ JSONObject.toJSONString(request));

        long startMillis=System.currentTimeMillis();

        Map<String,String> params = request.getParams();
        if(null!=params){
            if(StringUtils.isEmpty(params.get("softversion"))||
                    StringUtils.isEmpty(params.get("accountSid"))||
                    StringUtils.isEmpty(params.get("mainToken"))||
                    StringUtils.isEmpty(params.get("appId"))){
                logger.error("ccp语音验证码,配置异常");
                callResponse.setResultCode(InvokeResult.FAIL.getCode());
                callResponse.setResultMessage("ccp语音验证码,配置异常");
                return callResponse;
            }


            //传入的记录id
            if(StringUtils.isNotEmpty(params.get("recordId"))){
                ccpCallRequest.setRecordId(params.get("recordId"));
            }

            if(StringUtils.isNotEmpty(params.get("softversion"))){
                ccpCallRequest.setSoftversion(params.get("softversion"));
            }
            if(StringUtils.isNotEmpty(params.get("accountSid"))){
                ccpCallRequest.setAccountSid(params.get("accountSid"));
            }
            if(StringUtils.isNotEmpty(params.get("mainToken"))){
                ccpCallRequest.setMainToken(params.get("mainToken"));
            }
            if(StringUtils.isNotEmpty(params.get("appId"))){
                ccpCallRequest.setAppId(params.get("appId"));
            }
            if(StringUtils.isNotEmpty(params.get("mobile"))){
                ccpCallRequest.setTo(params.get("mobile"));
            }
            if(StringUtils.isNotEmpty(params.get("playTimes"))){
                ccpCallRequest.setPlayTimes(params.get("playTimes"));
            }
            if(StringUtils.isNotEmpty(params.get("respUrl"))){
                ccpCallRequest.setRespUrl(params.get("respUrl"));
            }
            if(StringUtils.isNotEmpty(params.get("displayNum"))){
                ccpCallRequest.setDisplayNum(params.get("displayNum"));
            }
            if(StringUtils.isNotEmpty(params.get("content"))){
                String content=params.get("content").trim();
                if(content.indexOf(".wav")>=0){
                    ccpCallRequest.setPlayVerifyCode(content);
                }else if(content.length()<=6){//验证码
                    ccpCallRequest.setVerifyCode(content);
                }else{
                    ccpCallRequest.setTxtVerifyCode(content);
                }
            }else{
                logger.error("容联语音验证码,验证码内容为空");
                callResponse.setResultCode(InvokeResult.FAIL.getCode());
                callResponse.setResultMessage("容联语音验证码,验证码内容为空");
                return callResponse;
            }
        }else{
            logger.error("容联语音验证码,参数为空");
            callResponse.setResultCode(InvokeResult.FAIL.getCode());
            callResponse.setResultMessage("容联语音验证码,参数为空");
            return callResponse;

        }

        logger.info("调用容联语音验证码接口,传入参数："+JSONObject.toJSONString(ccpCallRequest));

        Transaction transaction = Cat.newTransaction("CcpVerifyCodeCall ", ccpCallRequest.getRecordId());

        try {
            callResponse=ccpProviderBiz.callVerifyCode(ccpCallRequest);

            long endMillis=System.currentTimeMillis();
            logger.info("调用容联语音验证码接口,耗时： "+(endMillis-startMillis)+" 返回："+JSONObject.toJSONString(callResponse));
            Cat.logMetricForDuration("服务商接口调用耗时,CcpVerifyCodeCall",
                    endMillis-startMillis);
            transaction.setStatus(Message.SUCCESS);
        } catch (Exception e) {
            Cat.logError(e);
        } finally {
            transaction.complete();
        }
        return callResponse;
    }

    @ApiOperation(value = "容联语音通知", notes = "ccpProviderService.callNotify", response = CallResponse.class, tags = {"ccpProviderService.callNotify"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "容联语音通知", response = CallResponse.class)})

    @Override
    @RequestMapping(value = "/callNotify",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public CallResponse callNotify(@RequestBody CallRequest request) {

        CallResponse callResponse=new CallResponse();
        callResponse.setResultMessage("");

        CcpCallNotifyRequest ccpCallRequest=new CcpCallNotifyRequest();

        logger.info("容联语音通知,入参："+ JSONObject.toJSONString(request));

        long startMillis=System.currentTimeMillis();

        Map<String,String> params = request.getParams();
        if(null!=params){
            if(StringUtils.isEmpty(params.get("softversion"))||
                    StringUtils.isEmpty(params.get("accountSid"))||
                    StringUtils.isEmpty(params.get("mainToken"))||
                    StringUtils.isEmpty(params.get("appId"))){
                logger.error("容联语音通知,配置异常");
                callResponse.setResultCode(InvokeResult.FAIL.getCode());
                callResponse.setResultMessage("容联语音通知,配置异常");
                return callResponse;
            }

            //传入的记录id
            if(StringUtils.isNotEmpty(params.get("recordId"))){
                ccpCallRequest.setRecordId(params.get("recordId"));
            }

            if(StringUtils.isNotEmpty(params.get("softversion"))){
                ccpCallRequest.setSoftversion(params.get("softversion"));
            }
            if(StringUtils.isNotEmpty(params.get("accountSid"))){
                ccpCallRequest.setAccountSid(params.get("accountSid"));
            }
            if(StringUtils.isNotEmpty(params.get("mainToken"))){
                ccpCallRequest.setMainToken(params.get("mainToken"));
            }
            if(StringUtils.isNotEmpty(params.get("appId"))){
                ccpCallRequest.setAppId(params.get("appId"));
            }
            if(StringUtils.isNotEmpty(params.get("mobile"))){
                ccpCallRequest.setTo(params.get("mobile"));
            }
            if(StringUtils.isNotEmpty(params.get("playTimes"))){
                ccpCallRequest.setPlayTimes(params.get("playTimes"));
            }
            if(StringUtils.isNotEmpty(params.get("respUrl"))){
                ccpCallRequest.setRespUrl(params.get("respUrl"));
            }
            if(StringUtils.isNotEmpty(params.get("displayNum"))){
                ccpCallRequest.setDisplayNum(params.get("displayNum"));
            }
            if(StringUtils.isNotEmpty(params.get("content"))){
                String content=params.get("content").trim();
                if(content.indexOf(".wav")>=0){
                    ccpCallRequest.setMediaName(content);
                }else{
                    ccpCallRequest.setMediaTxt(content);
                }
            }else{
                logger.error("容联语音通知,内容为空");
                callResponse.setResultCode(InvokeResult.FAIL.getCode());
                callResponse.setResultMessage("容联语音通知,内容为空");
                return callResponse;
            }
        }else{
            logger.error("容联语音通知,参数为空");
            callResponse.setResultCode(InvokeResult.FAIL.getCode());
            callResponse.setResultMessage("容联语音通知,参数为空");
            return callResponse;

        }

        logger.info("调用容联语音通知接口,传入参数："+JSONObject.toJSONString(ccpCallRequest));
        Transaction transaction = Cat.newTransaction("CcpNotifyCall ", ccpCallRequest.getRecordId());
        try {
            callResponse=ccpProviderBiz.callNotify(ccpCallRequest);

            long endMillis=System.currentTimeMillis();
            logger.info("调用容联语音通知接口,耗时： "+(endMillis-startMillis)+" 返回："+JSONObject.toJSONString(callResponse));
            Cat.logMetricForDuration("服务商接口调用耗时,CcpNotifyCall",
                    endMillis-startMillis);
            transaction.setStatus(Message.SUCCESS);
        } catch (Exception e) {
            Cat.logError(e);
        } finally {
            transaction.complete();
        }
        return callResponse;
    }


    @ApiOperation(value = "容联语音通知结果报告回调", notes = "ccpProviderService.receiveReport", response = CallResponse.class, tags = {"ccpProviderService.receiveReport"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "容联语音通知结果报告回调", response = CallResponse.class)})
    @RequestMapping(value = "/receiveReport",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_XML_VALUE,
            produces = MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody
    ReceiveReportResponse receiveReport(@ApiParam(value = "request", required = true) @RequestBody ReceiveReportRequest request){
        logger.info("接收容联语音报告,参数："+ JSONObject.toJSONString(request));
        ReceiveReportResponse response=new ReceiveReportResponse();
        response.setStatuscode("-1");
        if("VoiceCode".equals(request.getAction())){//语音验证码
            response=ccpProviderBiz.receiveReport(request);
        }else{
            logger.info("接收容联语音报告,报告不是语音验证码类型");
        }
        return response;
    }
}
