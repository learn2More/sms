package com.ppdai.ac.sms.api.provider.techown.domain.biz;

import com.alibaba.fastjson.JSON;
import com.dianping.cat.Cat;
import com.dianping.cat.message.Message;
import com.dianping.cat.message.Transaction;
import com.ppdai.ac.sms.api.provider.techown.configuration.TechownProviderProperties;
import com.ppdai.ac.sms.api.provider.techown.domain.model.TechownReturnCode;
import com.ppdai.ac.sms.api.provider.techown.protocal.TechownService;
import com.ppdai.ac.sms.api.provider.techown.protocal.response.TcGetReportResponse;
import com.ppdai.ac.sms.api.provider.techown.protocal.response.TcReportResponse;
import com.ppdai.ac.sms.api.provider.techown.protocal.response.TcSendResponse;
import com.ppdai.ac.sms.provider.core.dao.domain.SmsMessageMoRecordBiz;
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
import com.ppdai.ac.sms.provider.core.request.SmsRequest;
import com.ppdai.ac.sms.provider.core.response.SmsResponse;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 *author cash
 *create 2017/7/11-11:20
**/


@Service
public class TechownProviderBiz {

    private static final Logger logger= LoggerFactory.getLogger(TechownProviderBiz.class);

    @Autowired
    TechownService techownService;

    @Autowired
    SmsMessageRecordBiz smsMessageRecordBiz;

    @Autowired
    SmsMessageMoRecordBiz smsMessageMoRecordBiz;

    @Autowired
    SmsMessageReportBiz smsMessageReportBiz;

    @Autowired
    MessageService messageService;

    @Autowired
    ProviderService providerService;

    @Autowired
    TechownProviderProperties techownProviderProperties;

    private  final SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");


    private static final int dataCoding = 15; //UNICODE 编码   0 表示英文,8 表示UCS2,15 表示中文
    private static final int transferEncoding = 0;//3; //URLEncode+UTF8  短信内容的传输编码  0：HEX 编码格式 1：Base64 编码格式 2：URLEncode 编码 (即针对字节进行URLEncode 编码) 3：URLEncode+UTF8 (即原始文本用UTF8 转字节后进行URLEncode 编码)
    private static final int responseFormat = 2; //返回格式为Json 格式   0：默认格式,即文本格式。 1：xml  2：json 格式
    private static final int registeredDelivery=1;////1需要状态报告,0不需要


    /**
     *techown 发送
     *author cash
     *create 2017/7/12-11:38
    **/

    public SmsResponse send(SmsRequest request){
        SmsResponse smsResponse=new SmsResponse();
        smsResponse.setResultCode(InvokeResult.SUCCESS.getCode());
        smsResponse.setResultMessage("");

        Map<String,String> params = request.getParams();
        MultiValueMap<String, Object> sendRequest = new LinkedMultiValueMap<>();
        if(null!=params){
            if(StringUtils.isEmpty(params.get("name"))||StringUtils.isEmpty(params.get("password"))){
                logger.error("天畅发送短信,配置异常");
                smsResponse.setResultCode(InvokeResult.FAIL.getCode());
                smsResponse.setResultMessage("天畅发送短信,配置异常");
                return smsResponse;
            }

            String username=params.get("name");
            String pasw0rd=params.get("password");
            String message =params.get("content");
            String mobile=params.get("mobile");
            String extendNo=params.get("extendNo");

            String recordId=params.get("recordId");

            //扩展码,用于上行匹配
            if(null!=params.get("extendNo")){
                extendNo=params.get("extendNo");
            }

            //计算密码摘要
            String timestamp = sdf.format(new Date() );
            String sign;
            byte[] messageBytes;
            try {
                messageBytes = message.getBytes("GB18030");
                sign=calculateSign(username,pasw0rd,timestamp,messageBytes);
            } catch (Exception e) {
                logger.error("天畅短信发送计算签名异常",e);
                smsResponse.setResultCode(InvokeResult.FAIL.getCode());
                smsResponse.setResultMessage("天畅短信发送计算签名异常");
                return smsResponse;
            }

            sendRequest.add("un",username);
            try {
                sendRequest.add("pw",URLEncoder.encode(sign, "utf8"));
            } catch (UnsupportedEncodingException e) {
                logger.error("天畅短信发送账号信息加密异常",e);
                smsResponse.setResultCode(InvokeResult.FAIL.getCode());
                smsResponse.setResultMessage("天畅账号信息加密异常");
            }
            sendRequest.add("ts",timestamp);
            sendRequest.add("da",mobile);
            sendRequest.add("ex",extendNo);
            sendRequest.add("rd",registeredDelivery);//1需要状态报告,0不需要
            sendRequest.add("dc",dataCoding);
            sendRequest.add("tf",transferEncoding);
            sendRequest.add("rf",responseFormat);
            sendRequest.add("sm",Hex.encodeHexString(messageBytes));

            Transaction transaction = Cat.newTransaction("TechownSmsSend ", recordId);
            long startMillis=System.currentTimeMillis();

            String tcSendResponseStr=techownService.send(sendRequest);
            logger.info(String.format("%s 天畅短信发送,返回:%s",recordId,tcSendResponseStr));
            TcSendResponse tcSendResponse=JSON.parseObject(tcSendResponseStr, TcSendResponse.class);

            if(null!=tcSendResponse&&tcSendResponse.isSuccess()){
                smsResponse.setResultCode(InvokeResult.SUCCESS.getCode());
                smsResponse.setResultMessage("发送成功");
                //返回消息id,反向设置
                String recordExt=tcSendResponse.getId();
                smsResponse.setResultObject(recordExt);

                if(StringUtils.isNotEmpty(recordExt) &&
                        StringUtils.isNotEmpty(recordId)){
                        LocalDateTime endTime=LocalDateTime.now();
                        //前n天
                        LocalDateTime startTime=endTime.minusDays(techownProviderProperties.getRecordSeveralDaysAgo());
                        smsMessageRecordBiz.updateRecordExtByRecordId(recordId,recordExt,startTime,endTime);
                }

                long endMillis=System.currentTimeMillis();
                Cat.logMetricForDuration("服务商接口调用耗时,TechownSmsSend",
                        endMillis-startMillis);
                transaction.setStatus(Message.SUCCESS);

            }else{
                smsResponse.setResultCode(InvokeResult.FAIL.getCode());
                smsResponse.setResultMessage("发送异常");
                if(null!=tcSendResponse&&null!=tcSendResponse.getR()){
                    String errorMsg=TechownReturnCode.getDescribet(tcSendResponse.getR());
                    errorMsg=StringUtils.isEmpty(errorMsg)?tcSendResponse.getR()+"":errorMsg;
                    logger.error(String.format("techown %s send fail errormsg:%s",recordId,errorMsg));
                    smsResponse.setResultMessage(errorMsg);
                }
            }
        }else{
            logger.error("天畅发送短信,参数为空");
            smsResponse.setResultCode(InvokeResult.FAIL.getCode());
            smsResponse.setResultMessage("天畅发送短信,参数为空");
            return smsResponse;
        }

        return smsResponse;
    }

    /**
     *techown 获取回执、上行
     *author cash
     *create 2017/7/12-11:39
    **/

    public SmsResponse getReport(String providerId){
        logger.info("techown 根据providerId get report begin,providerId: "+providerId);
        SmsResponse smsResponse=new SmsResponse();
        smsResponse.setResultCode(InvokeResult.SUCCESS.getCode());


        ProviderConfigResponse providerConfigResponse=providerService.getProviderConfigByProviderId(Integer.parseInt(providerId));
        if(InvokeResult.SUCCESS.getCode()==providerConfigResponse.getResultCode()){
            String providerName;
            String username="";
            String pasw0rd="";
            if(null!=providerConfigResponse.getResultObject()){
                List<LinkedHashMap<String,String>> listConfig= (List<LinkedHashMap<String, String>>) providerConfigResponse.getResultObject();
                if(CollectionUtils.isNotEmpty(listConfig)){
                    providerName=listConfig.get(0).get("ProviderName");

                    for(LinkedHashMap<String,String> map :listConfig){
                        if(null!=map.get("ConfigKey")){
                            if("name".equals(map.get("ConfigKey")) &&
                                    (null!=map.get("ConfigValue")&&!"".equals(map.get("ConfigValue")))){
                                    username=map.get("ConfigValue");
                                    continue;

                            }
                            if("password".equals(map.get("ConfigKey")) &&
                                    (null!=map.get("ConfigValue")&&!"".equals(map.get("ConfigValue")))){
                                    pasw0rd=map.get("ConfigValue");
                                    continue;
                            }
                        }
                    }
                    if(StringUtils.isEmpty(username)||StringUtils.isEmpty(pasw0rd)){
                        logger.error("techown getReport,provider: "+providerId+" 参数配置错误");
                        smsResponse.setResultCode(InvokeResult.FAIL.getCode());
                        smsResponse.setResultMessage("天畅拉取报告参数配置错误");
                        return smsResponse;
                    }

                    //计算密码摘要
                    String timestamp = sdf.format(new Date() );
                    try {
                        MessageDigest md5 = MessageDigest.getInstance("MD5");
                        md5.update( username.getBytes("utf8") );
                        md5.update( pasw0rd.getBytes("utf8") );
                        md5.update( timestamp.getBytes("utf8") );
                        pasw0rd = Base64.encodeBase64String(md5.digest() );
                    } catch (Exception e) {
                        logger.error("天畅拉取报告账号信息加密异常",e);
                        smsResponse.setResultCode(InvokeResult.FAIL.getCode());
                        smsResponse.setResultMessage("天畅账号信息加密异常");
                        return smsResponse;
                    }

                    MultiValueMap<String, Object> sendRequest = new LinkedMultiValueMap<>();
                    sendRequest.add("un",username);
                    try {
                        sendRequest.add("pw",URLEncoder.encode(pasw0rd, "utf8"));
                    } catch (UnsupportedEncodingException e) {
                        logger.error("天畅拉取报告账号信息编码异常",e);
                        smsResponse.setResultCode(InvokeResult.FAIL.getCode());
                        smsResponse.setResultMessage("天畅账号信息编码异常");
                        return smsResponse;
                    }
                    sendRequest.add("ts",timestamp);
                    sendRequest.add("fs",techownProviderProperties.getPerGetReportNumber());
                    sendRequest.add("rf",responseFormat);

                    String  tcGetReportResponseStr=techownService.getReport(sendRequest);
                    TcGetReportResponse tcGetReportResponse=JSON.parseObject(tcGetReportResponseStr, TcGetReportResponse.class);
                    if(null!=tcGetReportResponse&&tcGetReportResponse.isSuccess()){
                        if(null!=tcGetReportResponse.getData()&&CollectionUtils.isNotEmpty(tcGetReportResponse.getData())){
                            for(TcReportResponse tcReportResponse:tcGetReportResponse.getData()){
                                //回执与上行时同一接口,参数op区分
                                 if(null!=tcReportResponse.getOp()){
                                    logger.info("------------techown get report,complete return----------------------："+tcReportResponse.toString());
                                    if("mo".equals(tcReportResponse.getOp())){//上行
                                        String channelNo=providerId;
                                        String sender=tcReportResponse.getSa();
                                        String content=tcReportResponse.getSm();
                                        try {
                                            byte[] bytes= Hex.decodeHex(content.toCharArray());
                                            content=new String(bytes, "UTF-16BE");
                                        } catch (DecoderException | UnsupportedEncodingException  e) {
                                            logger.error("techown 拉取上行,解码异常",e);
                                        }
                                        String extendNo="";

                                        //上行接收时间
                                        String  time=tcReportResponse.getSd();
                                        Date d=new Date();
                                        if(StringUtils.isNotEmpty(time)){
                                            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
                                            try {
                                                d = format.parse(time);
                                            } catch (Exception e) {
                                                logger.error("techown extendNo: "+extendNo+" 拉取上行,日期转换异常",e);
                                            }
                                        }

                                        Timestamp timeStamp = new Timestamp(d.getTime());
                                        BizResult bizResult=smsMessageMoRecordBiz.saveMoRecord(channelNo,providerName,sender,content,extendNo,timeStamp);
                                        if(InvokeResult.SUCCESS.getCode()!=bizResult.getResultCode()){
                                            logger.error("techown extendNo: "+extendNo+" mobile: "+sender+" 保存上行记录异常:"+ bizResult.getResultMessage());
                                        }
                                    }else if("dr".equals(tcReportResponse.getOp())){//回执
                                        String recordExt=tcReportResponse.getId();
                                        int reportStatus= ReportStatus.ERROR_REPORT.getCode();
                                        String reportResult=tcReportResponse.getSu();
                                        if(null!=tcReportResponse.getRp() && (ReportStatus.SUCCESS_REPORT.getCode()==tcReportResponse.getRp()) ){
                                            //rp错误码,0表示成功接收
                                                reportStatus=tcReportResponse.getRp();
                                        }
                                        //完整报告
                                        String providerMessage=tcReportResponse.toString();

                                        String doneTime=tcReportResponse.getDd();
                                        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
                                        Date d=new Date();
                                        if(StringUtils.isNotEmpty(doneTime)){
                                            try {
                                                d = format.parse(doneTime);
                                            } catch (Exception e) {
                                                logger.error("techown recordExt："+recordExt+" 拉取回执,日期转换异常",e);
                                            }
                                        }
                                        Timestamp reportTime = new Timestamp(d.getTime());

                                        String messageId="";
                                        Timestamp sendTime=null;

                                        LocalDateTime endTime=LocalDateTime.now();
                                        //前n天
                                        LocalDateTime startTime=endTime.minusDays(techownProviderProperties.getRecordSeveralDaysAgo());

                                        //根据recordExt 查询record
                                        SMSMessageRecordDTO smsMessageRecordDTO=smsMessageRecordBiz.getRecordByRecordExt(recordExt,startTime,endTime);
                                        if(null!=smsMessageRecordDTO){
                                            messageId=smsMessageRecordDTO.getMessageId();
                                            sendTime=smsMessageRecordDTO.getSendTime();
                                        }

                                        String recipient="";

                                        //查询recipient
                                        if(StringUtils.isNotEmpty(messageId)){
                                            MessageQueryRequest messageQueryRequest=new MessageQueryRequest();
                                            messageQueryRequest.setMessageId(messageId);

                                            messageQueryRequest.setStartTime(startTime+"");
                                            messageQueryRequest.setEndTime(endTime+"");

                                            MessageQueryResponse messageQueryResponse=messageService.query(messageQueryRequest);
                                            if(InvokeResult.FAIL.getCode()==messageQueryResponse.getResultCode()){
                                                logger.error("techown 根据messageId: "+messageId+" 查询message信息异常："+messageQueryResponse.getResultMessage());
                                            }else if(InvokeResult.SUCCESS.getCode()==messageQueryResponse.getResultCode() &&
                                                    (null!=messageQueryResponse.getResultObject())){
                                                    LinkedHashMap linkedHashMap= (LinkedHashMap) messageQueryResponse.getResultObject();
                                                    recipient= (String) linkedHashMap.get("recipient");
                                            }
                                        }

                                        BizResult bizResult=smsMessageReportBiz.saveMessageReport(recordExt,messageId,providerId,providerName,recipient,reportResult,reportStatus,reportTime,sendTime,providerMessage);
                                        if(InvokeResult.SUCCESS.getCode()!=bizResult.getResultCode()){
                                            logger.error("techown recordExt："+recordExt+" 保存回执报告异常："+bizResult.getResultMessage());
                                            continue;
                                        }

                                        //更新record记录的状态
                                        int status;
                                        if(ReportStatus.SUCCESS_REPORT.getCode()==reportStatus){
                                            status=RecordStatus.SEND_SUCCESS.getCode();
                                        }else{
                                            status=RecordStatus.SEND_FAIL.getCode();
                                        }
                                        //前n天
                                        smsMessageRecordBiz.updateStatusByRecordExt(recordExt,status,startTime,endTime);
                                    }
                                }
                            }
                        }else{
                            logger.info(String.format("techown  %s get reports,the report is null",providerId));
                        }

                    }else{
                        smsResponse.setResultCode(InvokeResult.FAIL.getCode());
                        smsResponse.setResultMessage("获取异常");
                        if(null!=tcGetReportResponse&&null!=tcGetReportResponse.getR()){
                            String errorMsg=TechownReturnCode.getDescribet(tcGetReportResponse.getR());
                            errorMsg=StringUtils.isEmpty(errorMsg)?tcGetReportResponse.getR()+"":errorMsg;
                            logger.error(String.format("techown %s getReprot fail errormsg:%s",providerId,errorMsg));
                            smsResponse.setResultMessage(errorMsg);
                        }
                    }
                }
            }
        }else{
            logger.error(String.format("techown getProviderConfigError when getReprot ,provider: %s errorMsg:%s",providerId,providerConfigResponse.getResultMessage()));
            smsResponse.setResultCode(InvokeResult.FAIL.getCode());
            smsResponse.setResultMessage("echown getProviderConfigError when getReprot");
        }

        logger.info("techown 根据providerId get report end,providerId: "+providerId);

        return smsResponse;
    }

    @Async
    public void getReportAsync(String providerId){
        getReport(providerId);
    }

    public static String calculateSign(String username, String password, String timestamp, byte[] message) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update( username.getBytes("UTF8") );
        md.update( password.getBytes("UTF8") );
        md.update( timestamp.getBytes("UTF8") );
        md.update( message );
        byte[] md5result = md.digest();
        return Base64.encodeBase64String( md5result );
    }







}
