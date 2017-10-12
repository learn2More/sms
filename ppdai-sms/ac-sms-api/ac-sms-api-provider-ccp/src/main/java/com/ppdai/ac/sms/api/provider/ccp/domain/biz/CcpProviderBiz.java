package com.ppdai.ac.sms.api.provider.ccp.domain.biz;

import com.alibaba.fastjson.JSONObject;
import com.ppdai.ac.sms.api.provider.ccp.domain.model.CallResultState;
import com.ppdai.ac.sms.api.provider.ccp.protocal.CcpService;
import com.ppdai.ac.sms.api.provider.ccp.protocal.request.CallNotifyRequest;
import com.ppdai.ac.sms.api.provider.ccp.protocal.request.CallVerifyCodeRequest;
import com.ppdai.ac.sms.api.provider.ccp.protocal.response.CallNotifyResponse;
import com.ppdai.ac.sms.api.provider.ccp.protocal.response.CallReportResponse;
import com.ppdai.ac.sms.api.provider.ccp.protocal.response.CallResult;
import com.ppdai.ac.sms.api.provider.ccp.protocal.response.CallVerifyCodeResponse;
import com.ppdai.ac.sms.api.provider.ccp.request.CcpCallNotifyRequest;
import com.ppdai.ac.sms.api.provider.ccp.request.CcpCallVerifyCodeRequest;
import com.ppdai.ac.sms.api.provider.ccp.request.CcpResultRequest;
import com.ppdai.ac.sms.api.provider.ccp.request.ReceiveReportRequest;
import com.ppdai.ac.sms.api.provider.ccp.response.ReceiveReportResponse;
import com.ppdai.ac.sms.provider.core.dao.domain.SmsMessageBiz;
import com.ppdai.ac.sms.provider.core.dao.domain.SmsMessageRecordBiz;
import com.ppdai.ac.sms.provider.core.dao.domain.SmsMessageReportBiz;
import com.ppdai.ac.sms.provider.core.enums.InvokeResult;
import com.ppdai.ac.sms.provider.core.enums.RecordStatus;
import com.ppdai.ac.sms.provider.core.enums.ReportStatus;
import com.ppdai.ac.sms.provider.core.model.bo.BizResult;
import com.ppdai.ac.sms.provider.core.model.entity.SMSMessageRecordDTO;
import com.ppdai.ac.sms.provider.core.protocol.MessageService;
import com.ppdai.ac.sms.provider.core.protocol.ProviderService;
import com.ppdai.ac.sms.provider.core.protocol.request.MessageQueryRequest;
import com.ppdai.ac.sms.provider.core.protocol.response.MessageQueryResponse;
import com.ppdai.ac.sms.provider.core.protocol.response.ProviderConfigResponse;
import com.ppdai.ac.sms.provider.core.response.CallResponse;
import com.ppdai.ac.sms.provider.core.utils.MD5Util;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * author cash
 * create 2017-05-15-15:25
 **/

@Service
public class CcpProviderBiz {

    private static final Logger logger= LoggerFactory.getLogger(CcpProviderBiz.class);

    private  final SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");


    @Autowired
    CcpService ccpService;

    @Autowired
    ProviderService providerService;


    @Autowired
    MessageService messageService;

    @Autowired
    SmsMessageRecordBiz smsMessageRecordBiz;

    @Autowired
    SmsMessageBiz smsMessageBiz;


    @Autowired
    SmsMessageReportBiz smsMessageReportBiz;

    public CallResponse callVerifyCode(CcpCallVerifyCodeRequest ccpCallRequest) {

        CallResponse callResponse=new CallResponse();
        callResponse.setResultMessage("");

        String softversion="";
        String accountSid="";
        String mainToken="";

        if(StringUtils.isNotEmpty(ccpCallRequest.getSoftversion())){
            softversion=ccpCallRequest.getSoftversion();
        }
        if(StringUtils.isNotEmpty(ccpCallRequest.getAccountSid())){
            accountSid=ccpCallRequest.getAccountSid();
        }
        if(StringUtils.isNotEmpty(ccpCallRequest.getMainToken())){
            mainToken=ccpCallRequest.getMainToken();
        }

        String dateStr=sdf.format(new Date());
        String recordId=ccpCallRequest.getRecordId();


        CallVerifyCodeRequest callMsgRequest=new CallVerifyCodeRequest();
        BeanUtils.copyProperties(ccpCallRequest,callMsgRequest);


        //sig： md5(账户Id + 账户授权令牌 + 时间戳)
        String sigParameter= MD5Util.MD5(accountSid+mainToken+dateStr);
        //请求头Authorization： Base64编码（账户Id + 冒号 + 时间戳）
        String authorization= Base64.encodeBase64String((accountSid+":"+dateStr).getBytes());

        logger.info("recordId："+recordId+" 容联语音验证码接口调用start");
        CallVerifyCodeResponse callMsgResponse=ccpService.callVerifyCode(softversion,accountSid,sigParameter,authorization,callMsgRequest);
        logger.info("recordId："+recordId+" 容联语音验证码接口返回 ："+JSONObject.toJSONString(callMsgResponse));

        if(null!=callMsgResponse && "000000".equals(callMsgResponse.getStatusCode())){
            String callSid=callMsgResponse.getVoiceVerify().getCallSid();
            //容联返回消息id,反向设置
            if(StringUtils.isNotEmpty(callSid) && StringUtils.isNotEmpty(recordId)){
                    LocalDateTime endTime=LocalDateTime.now();
                    //前3天
                    LocalDateTime startTime=endTime.minusDays(3);
                    smsMessageRecordBiz.updateRecordExtByRecordId(recordId,callSid,startTime,endTime);
            }
            callResponse.setResultMessage("通知成功");
            callResponse.setResultCode(InvokeResult.SUCCESS.getCode());
            callResponse.setResultObject(callSid);
        }else{
            if(null!=callMsgResponse){
                logger.error("容联语音验证码异常:recordId: "+recordId+" 返回状态值："+callMsgResponse.getStatusCode());
            }else logger.error("容联语音验证码异常:recordId: "+recordId+" 返回结果为null");
            callResponse.setResultCode(InvokeResult.FAIL.getCode());
            callResponse.setResultMessage("容联语音验证码接口调用异常");
        }

        logger.info("recordId："+recordId+" 容联语音验证码biz方法返回 ："+JSONObject.toJSONString(callResponse));
        return callResponse;
    }

    public CallResponse callNotify(CcpCallNotifyRequest ccpCallRequest) {

        CallResponse callResponse=new CallResponse();
        callResponse.setResultMessage("");

        String softversion="";
        String accountSid="";
        String mainToken="";

        if(StringUtils.isNotEmpty(ccpCallRequest.getSoftversion())){
            softversion=ccpCallRequest.getSoftversion();
        }
        if(StringUtils.isNotEmpty(ccpCallRequest.getAccountSid())){
            accountSid=ccpCallRequest.getAccountSid();
        }
        if(StringUtils.isNotEmpty(ccpCallRequest.getMainToken())){
            mainToken=ccpCallRequest.getMainToken();
        }

        String dateStr=sdf.format(new Date());
        String recordId=ccpCallRequest.getRecordId();


        CallNotifyRequest callMsgRequest=new CallNotifyRequest();
        BeanUtils.copyProperties(ccpCallRequest,callMsgRequest);

        //sig： md5(账户Id + 账户授权令牌 + 时间戳)
        String sigParameter= MD5Util.MD5(accountSid+mainToken+dateStr);
        //请求头Authorization： Base64编码（账户Id + 冒号 + 时间戳）
        String authorization= Base64.encodeBase64String((accountSid+":"+dateStr).getBytes());

        logger.info("recordId："+recordId+" 容联语音通知接口调用start");
        CallNotifyResponse callMsgResponse=ccpService.callNotify(softversion,accountSid,sigParameter,authorization,callMsgRequest);
        logger.info("recordId："+recordId+" 容联语音通知接口返回 ："+JSONObject.toJSONString(callMsgResponse));
        if(null!=callMsgResponse && "000000".equals(callMsgResponse.getStatusCode())){
            String callSid=callMsgResponse.getLandingCall().getCallSid();
            //容联返回消息id,反向设置
            if(StringUtils.isNotEmpty(callSid)&&StringUtils.isNotEmpty(recordId)){
                LocalDateTime endTime=LocalDateTime.now();
                //前3天
                LocalDateTime startTime=endTime.minusDays(3);
                smsMessageRecordBiz.updateRecordExtByRecordId(recordId,callSid,startTime,endTime);
            }
            callResponse.setResultMessage("通知成功");
            callResponse.setResultCode(InvokeResult.SUCCESS.getCode());
            callResponse.setResultObject(callSid);
        }else{
            if(null!=callMsgResponse){
                logger.error("容联语音通知异常:recordId: "+recordId+" 返回状态值："+callMsgResponse.getStatusCode());
            }else logger.error("容联语音通知异常:recordId: "+recordId+" 返回结果为null");
            callResponse.setResultCode(InvokeResult.FAIL.getCode());
            callResponse.setResultMessage("容联语音通知接口调用异常");
        }

        logger.info("recordId："+recordId+" 容联语音通知biz方法返回 ："+JSONObject.toJSONString(callResponse));
        return callResponse;
    }



    public ReceiveReportResponse receiveReport(ReceiveReportRequest request){
        ReceiveReportResponse response=new ReceiveReportResponse();

        String callsid=request.getCallSid();
        int reportStatus= ReportStatus.ERROR_REPORT.getCode();
        String reportResult;

        if(CallResultState.SUCCESS.getCode().equals(request.getState())){
            reportStatus=ReportStatus.SUCCESS_REPORT.getCode();
        }else if(CallResultState.FAIL.getCode().equals(request.getState())||CallResultState.NOBODY_IS_ANSWERING.getCode().equals(request.getState())){
            reportStatus=ReportStatus.ERROR_REPORT.getCode();
        }

        reportResult=StringUtils.isNotEmpty(request.getCallstate())?request.getCallstate():Integer.toString(reportStatus);

        //完整报告
        String providerMessage= JSONObject.toJSONString(request);

        String recordExt=callsid;
        //根据recordExt查询记录信息
        LocalDateTime endTime=LocalDateTime.now();
        //前3天
        LocalDateTime startTime=endTime.minusDays(3);
        SMSMessageRecordDTO smsMessageRecordDTO=smsMessageRecordBiz.getRecordByRecordExt(recordExt,startTime,endTime);
        if(null!=smsMessageRecordDTO){
            Timestamp sendTime=smsMessageRecordDTO.getSendTime();
            String messageId=smsMessageRecordDTO.getMessageId();
            String providerId=smsMessageRecordDTO.getProviderId()+"";
            String recipient=request.getNumber();
            Timestamp time=new Timestamp((new Date()).getTime());
            BizResult bizResult=smsMessageReportBiz.saveMessageReport(callsid,messageId,providerId,"",recipient,reportResult,reportStatus,time,sendTime,providerMessage);
            if(InvokeResult.SUCCESS.getCode()!=bizResult.getResultCode()){
                logger.error("容联保存回执报告异常："+bizResult.getResultMessage());
                response.setStatuscode("-1");
                return response;
            }

            //更新record记录的状态
            int status;
            if(ReportStatus.SUCCESS_REPORT.getCode()==reportStatus){
                status=RecordStatus.SEND_SUCCESS.getCode();
            }else{
                status=RecordStatus.SEND_FAIL.getCode();
            }
            smsMessageRecordBiz.updateStatusByRecordExt(callsid,status,startTime,endTime);
            response.setStatuscode("000000");
        }
        return response;
    }

    public CallResponse getReport(String  providerId){
        logger.info("ccp 根据providerId获取回执开始,providerId: "+providerId);
        CallResponse callResponse=new CallResponse();
        callResponse.setResultCode(InvokeResult.SUCCESS.getCode());
        callResponse.setResultMessage("");

        String softversion="";
        String accountSid="";
        String mainToken="";

        String providerName="";

        //三天之前的记录
        LocalDateTime endTime=LocalDateTime.now();
        LocalDateTime startTime=endTime.minusDays(3L);

        List<SMSMessageRecordDTO> listDto=smsMessageRecordBiz.getNeedToSolvedRecord(providerId,startTime,endTime);
        if(CollectionUtils.isNotEmpty(listDto)){

            ProviderConfigResponse providerConfigResponse=providerService.getProviderConfigByProviderId(Integer.parseInt(providerId));
            if(InvokeResult.SUCCESS.getCode()==providerConfigResponse.getResultCode()){
                if(null!=providerConfigResponse.getResultObject()){
                    List<LinkedHashMap<String,String>> listConfig= (List<LinkedHashMap<String, String>>) providerConfigResponse.getResultObject();
                    if(CollectionUtils.isNotEmpty(listConfig)){
                        providerName=listConfig.get(0).get("ProviderName");
                        for(LinkedHashMap<String,String> map :listConfig){
                            if(null!=map.get("ConfigKey")){
                                if("softversion".equals(map.get("ConfigKey")) &&
                                        (null!=map.get("ConfigValue")&&!"".equals(map.get("ConfigValue")))){
                                        softversion=map.get("ConfigValue");
                                        continue;
                                }
                                if("accountSid".equals(map.get("ConfigKey")) &&
                                        (null!=map.get("ConfigValue")&&!"".equals(map.get("ConfigValue")))){
                                        accountSid=map.get("ConfigValue");
                                        continue;
                                }
                                if("mainToken".equals(map.get("ConfigKey")) &&
                                        (null!=map.get("ConfigValue")&&!"".equals(map.get("ConfigValue")))){
                                        mainToken=map.get("ConfigValue");
                                        continue;
                                }
                            }
                        }
                    }

                }
            }
            if(StringUtils.isEmpty(softversion)||StringUtils.isEmpty(accountSid)||StringUtils.isEmpty(mainToken)){
                logger.error("容联拉取回执,providerId: "+providerId+" 配置异常");
                callResponse.setResultCode(InvokeResult.FAIL.getCode());
                callResponse.setResultMessage("容联拉取回执,配置异常");
                return callResponse;
            }
            for(SMSMessageRecordDTO o:listDto){
                CcpResultRequest ccpResultRequest=new CcpResultRequest();
                ccpResultRequest.setCallsid(o.getRecordExt());


                String dateStr=sdf.format(new Date());

                if(StringUtils.isNotEmpty(ccpResultRequest.getSoftversion())){
                    softversion=ccpResultRequest.getSoftversion();
                }
                if(StringUtils.isNotEmpty(ccpResultRequest.getAccountSid())){
                    accountSid=ccpResultRequest.getAccountSid();
                }
                if(StringUtils.isNotEmpty(ccpResultRequest.getMainToken())){
                    mainToken=ccpResultRequest.getMainToken();
                }

                String callsid=o.getRecordExt();

                if(StringUtils.isNotEmpty(callsid)){
                    //sig： md5(账户Id + 账户授权令牌 + 时间戳)
                    String sig= MD5Util.MD5(accountSid+mainToken+dateStr);
                    //请求头Authorization： Base64编码（账户Id + 冒号 + 时间戳）
                    String authorization= Base64.encodeBase64String((accountSid+":"+dateStr).getBytes());

                    CallReportResponse callReportResponse=ccpService.getCallResult(softversion,accountSid,sig,callsid,authorization);
                    logger.info("callsid :"+callsid+" 获取回执报告,完整返回："+JSONObject.toJSONString(callReportResponse));
                    if(null!=callReportResponse && "000000".equals(callReportResponse.getStatusCode())){
                        int reportStatus= ReportStatus.ERROR_REPORT.getCode();
                        String reportResult="";
                        if(null!=callReportResponse.getCallResult()){
                            CallResult callResult=callReportResponse.getCallResult();
                            if(CallResultState.SUCCESS.getCode().equals(callResult.getState())){
                                reportStatus=ReportStatus.SUCCESS_REPORT.getCode();
                            }else if(CallResultState.FAIL.getCode().equals(callResult.getState())||CallResultState.NOBODY_IS_ANSWERING.getCode().equals(callResult.getState())){
                                reportStatus=ReportStatus.ERROR_REPORT.getCode();
                            }

                            reportResult=StringUtils.isNotEmpty(callResult.getCallstate())?callResult.getCallstate():Integer.toString(reportStatus);
                        }

                        //完整报告
                        String providerMessage= JSONObject.toJSONString(callReportResponse);

                        String messageId=o.getMessageId();
                        //发送时间
                        Timestamp sendTime=o.getSendTime();
                        String recipient="";

                        //查询recipient
                        MessageQueryRequest messageQueryRequest=new MessageQueryRequest();
                        messageQueryRequest.setMessageId(messageId);

                        LocalDateTime localDate=LocalDateTime.now();
                        //前3天
                        messageQueryRequest.setStartTime(localDate.minusDays(3)+"");
                        messageQueryRequest.setEndTime(localDate+"");

                        MessageQueryResponse messageQueryResponse=messageService.query(messageQueryRequest);
                        if(InvokeResult.FAIL.getCode()==messageQueryResponse.getResultCode()){
                            logger.error("容联 根据messageId: "+messageId+" 查询message信息异常："+messageQueryResponse.getResultMessage());
                        }else if(InvokeResult.SUCCESS.getCode()==messageQueryResponse.getResultCode() &&
                                (null!=messageQueryResponse.getResultObject())){
                                LinkedHashMap linkedHashMap= (LinkedHashMap) messageQueryResponse.getResultObject();
                                recipient= (String) linkedHashMap.get("recipient");
                        }

                        Timestamp reportTime=new Timestamp((new Date()).getTime());

                        BizResult bizResult=smsMessageReportBiz.saveMessageReport(callsid,messageId,providerId,providerName,recipient,reportResult,reportStatus,reportTime,sendTime,providerMessage);
                        if(InvokeResult.SUCCESS.getCode()!=bizResult.getResultCode()){
                            logger.error("容联 callsid: "+callsid+" 保存回执报告异常："+bizResult.getResultMessage());
                        }

                        //更新record记录的状态
                        int status;
                        if(ReportStatus.SUCCESS_REPORT.getCode()==reportStatus){
                            status=RecordStatus.SEND_SUCCESS.getCode();
                        }else{
                            status=RecordStatus.SEND_FAIL.getCode();
                        }
                        smsMessageRecordBiz.updateStatusByRecordExt(o.getRecordExt(),status,localDate.minusDays(3),localDate);
                    }
                }
            }
        }else{
            logger.info("ccp 根据providerId: "+providerId+" 需要拉取的回执记录条数为空");
        }

        return callResponse;
    }


}
