package com.ppdai.ac.sms.api.provider.sendcloud.domain.biz;

import com.alibaba.fastjson.JSONObject;
import com.ppdai.ac.sms.api.provider.sendcloud.configuration.SendCloudProviderProperties;
import com.ppdai.ac.sms.api.provider.sendcloud.domain.model.SendMailInput;
import com.ppdai.ac.sms.api.provider.sendcloud.protocol.SendCloudService;
import com.ppdai.ac.sms.api.provider.sendcloud.protocol.response.ReportResponseData;
import com.ppdai.ac.sms.api.provider.sendcloud.protocol.response.ReportVoList;
import com.ppdai.ac.sms.api.provider.sendcloud.protocol.response.SendResponseData;
import com.ppdai.ac.sms.provider.core.dao.domain.SmsMessageRecordBiz;
import com.ppdai.ac.sms.provider.core.dao.domain.SmsMessageReportBiz;
import com.ppdai.ac.sms.provider.core.enums.InvokeResult;
import com.ppdai.ac.sms.provider.core.enums.ReportStatus;
import com.ppdai.ac.sms.provider.core.model.bo.BizResult;
import com.ppdai.ac.sms.provider.core.model.entity.SMSMessageRecordDTO;
import com.ppdai.ac.sms.provider.core.protocol.ProviderService;
import com.ppdai.ac.sms.provider.core.protocol.response.ProviderConfigResponse;
import com.ppdai.ac.sms.provider.core.response.SmsResponse;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class SendCloudBiz {
    @Autowired
    SmsMessageRecordBiz smsMessageRecordBiz;
    private final static Logger logger = LoggerFactory.getLogger(SendCloudBiz.class);

    @Autowired
    ProviderService providerService;

    @Autowired
    SendCloudService sendCloudService;

    @Autowired
    SmsMessageReportBiz smsMessageReportBiz;

    @Autowired
    SendCloudProviderProperties sendCloudProviderProperties;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 发送短信
     * @param sendMailInput
     * @return
     * @throws Exception
     */
    public SmsResponse send(SendMailInput sendMailInput) throws  Exception{
        SmsResponse  smsResponse=new SmsResponse();

        //发送短信
        MultiValueMap<String, String> sendRequest = new LinkedMultiValueMap<>();
        sendRequest.add("apiUser", sendMailInput.getApiUser());
        sendRequest.add("apiKey", sendMailInput.getApiKey());
        sendRequest.add("from", sendMailInput.getFrom());
        sendRequest.add("fromname",sendMailInput.getFromName());
        sendRequest.add("to", sendMailInput.getToAddress());
        sendRequest.add("subject", sendMailInput.getSubject());
        sendRequest.add("html", sendMailInput.getHtml());
        sendRequest.add("respEmailId", "true");
        sendRequest.add("useNotification", "true");
        SendResponseData sendResponseData=sendCloudService.send(sendRequest);

        //发送成功
        if(sendResponseData.getResult()==true && sendResponseData.getStatusCode()==200){
            //SendCloud返回消息ID，反向设置
            LocalDateTime endTime=LocalDateTime.now();
            LocalDateTime startTime=endTime.minusDays(3);     //前3天
            if(sendResponseData.getInfo()!=null && sendResponseData.getInfo().getEmailIdList()!=null) {
                for(String  emailId:sendResponseData.getInfo().getEmailIdList())
                    smsMessageRecordBiz.updateRecordExtByRecordId(sendMailInput.getRecordId(), emailId, startTime, endTime);
            }
            smsResponse.setResultCode(InvokeResult.SUCCESS.getCode());
            smsResponse.setResultMessage("SendCloud发送邮件成功");
        }else{
            smsResponse.setResultCode(InvokeResult.FAIL.getCode());
            smsResponse.setResultMessage("SendCloud发送邮件失败");
            logger.error(String.format("SendCloudBiz send fail,email:%s,response:%s", String.valueOf(sendMailInput.getToAddress()), JSONObject.toJSONString(sendResponseData)));
        }
        return smsResponse;
    }

    /**
     * 获取回执报告
     * @param providerId
     * @return
     * @throws Exception
     */
    public SmsResponse getReports(String providerId) throws  Exception{
        logger.info("SendCloud start getReports,providerId: "+providerId);
        SmsResponse smsResponse=new SmsResponse();

        //获取三天之前的邮件记录
        LocalDateTime endTime=LocalDateTime.now();
        LocalDateTime startTime=endTime.minusDays(3L);
        List<SMSMessageRecordDTO> listDto=smsMessageRecordBiz.getNeedToSolvedRecord(providerId,startTime,endTime);
        if(CollectionUtils.isEmpty(listDto)) {
            logger.info("SendCloud getReports is null,providerId:"+providerId);
            return  smsResponse;
        }

        logger.info(String.format("SendCloud  getReports,providerId:%s,SMSMessageRecord count:%s ",providerId,listDto.size()));

        //获取SendCloud配置
        ProviderConfigResponse providerConfigResponse=providerService.getProviderConfigByProviderId(Integer.parseInt(providerId));
        if(InvokeResult.SUCCESS.getCode()!=providerConfigResponse.getResultCode() || null==providerConfigResponse.getResultObject()) {
            logger.error("SendCloud getReports  getProviderConfig error,providerId: "+providerId);
            smsResponse.setResultCode(InvokeResult.FAIL.getCode());
            smsResponse.setResultMessage("SendCloud拉取回执,配置异常");
            return smsResponse;
        }

        //处理SendCloud配置
        String providerName="",apiUser="",apiKey="";
        List<LinkedHashMap<String,String>> listConfig= (List<LinkedHashMap<String, String>>) providerConfigResponse.getResultObject();
        if(CollectionUtils.isNotEmpty(listConfig)) {
            providerName = listConfig.get(0).get("ProviderName");
            for (LinkedHashMap<String, String> map : listConfig) {
                if (null != map.get("ConfigKey")) {
                    if ("apiUser".equals(map.get("ConfigKey"))) {
                        if (null != map.get("ConfigValue") && !"".equals(map.get("ConfigValue"))) {
                            apiUser=map.get("ConfigValue");
                            continue;
                        }
                    }
                    if ("apiKey".equals(map.get("ConfigKey"))) {
                        if (null != map.get("ConfigValue") && !"".equals(map.get("ConfigValue"))) {
                            apiKey=map.get("ConfigValue");
                            continue;
                        }
                    }
                }
            }
        }

        //根据邮件记录拉取对应的回执报告
        List<String>   recordExtList=new ArrayList<String>();
        Map<String,SMSMessageRecordDTO>  map=new HashMap<String,SMSMessageRecordDTO>();
        for(SMSMessageRecordDTO dto:listDto) {
            if(StringUtils.isNotBlank(dto.getRecordExt()) && !map.containsKey(dto.getRecordExt()))
                map.put(dto.getRecordExt(),dto);
        }
        int i=0;
        for(SMSMessageRecordDTO dto:listDto){
            i++;
            if(StringUtils.isNotBlank(dto.getRecordExt()))
                 recordExtList.add(dto.getRecordExt());
            //一次拉取一百条回执
            if(i==listDto.size() || recordExtList.size()==100){
                if(recordExtList==null ||recordExtList.size()==0){
                    continue;
                }
                String emailIds=org.apache.commons.lang.StringUtils.join(recordExtList,";");
                MultiValueMap<String, String> reportRequest = new LinkedMultiValueMap<>();
                reportRequest.add("apiUser", apiUser);
                reportRequest.add("apiKey", apiKey); 
                reportRequest.add("days", "3");
                reportRequest.add("emailIds",emailIds);

                ReportResponseData reportResponseData=sendCloudService.getReports(reportRequest);
                if(reportResponseData.isResult()==true && reportResponseData.getStatusCode()==200 && reportResponseData.getInfo()!=null && reportResponseData.getInfo().getVoList()!=null){
                    List<ReportVoList> reportVoListList=reportResponseData.getInfo().getVoList();
                    logger.info("SendCloud getReports reportVoListList count:%s"+reportVoListList.size());
                    for(ReportVoList reportVoList:reportVoListList){
                        try {
                            SMSMessageRecordDTO tempDto = map.get(reportVoList.getEmailId());
                            if(tempDto==null)
                                continue;
                            String providerMessage = com.alibaba.fastjson.JSONObject.toJSONString(reportVoList);   //完整报告
                            String messageId = tempDto.getMessageId();
                            java.sql.Timestamp sendTime = tempDto.getSendTime();
                            String recipient = reportVoList.getRecipients();
                            Date modifiedTime=dateFormat.parse(reportVoList.getModifiedTime());
                            Timestamp reportTime = new Timestamp(modifiedTime.getTime());
                            int reportStatus = ReportStatus.ERROR_REPORT.getCode();
                            if(reportVoList.getStatus().equals(sendCloudProviderProperties.getReportSendingStatusKey())){  //请求中
                                logger.info(String.format("SendCloud getReports sending info,reportVoList:%s", JSONObject.toJSONString(reportVoList)));
                                continue;
                            }
                            if (reportVoList.getStatus().equals(sendCloudProviderProperties.getReportSuccessStatusKey())) {   //送达
                                reportStatus = ReportStatus.SUCCESS_REPORT.getCode();
                            }
                            String reportResult = reportVoList.getSendLog();
                            if(StringUtils.isNotBlank(reportResult) && reportResult.length()>20){
                                reportResult=reportResult.substring(0,19);
                            }

                            BizResult bizResult = smsMessageReportBiz.saveMessageReport(tempDto.getRecordId(), messageId, providerId, providerName, recipient, reportResult, reportStatus, reportTime, sendTime, providerMessage);
                            if (InvokeResult.SUCCESS.getCode() != bizResult.getResultCode()) {
                                logger.error("SendCloud recordId: " + tempDto.getMessageId() + " 保存回执报告异常：" + bizResult.getResultMessage());
                            }
                        }catch (Exception ex){
                            logger.error(String.format("SendCloud getReports error,reportVoList:%s", JSONObject.toJSONString(reportVoList)),ex);
                        }
                    }
                }
                recordExtList=new ArrayList<String>();
            }
        }
        return smsResponse;
    }
}