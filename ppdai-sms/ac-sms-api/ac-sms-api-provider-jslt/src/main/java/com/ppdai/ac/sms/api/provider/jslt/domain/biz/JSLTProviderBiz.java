package com.ppdai.ac.sms.api.provider.jslt.domain.biz;

import com.alibaba.fastjson.JSONObject;
import com.ppdai.ac.sms.api.provider.jslt.domain.model.JSLTReportStatus;
import com.ppdai.ac.sms.api.provider.jslt.domain.model.JSLTSignDTO;
import com.ppdai.ac.sms.api.provider.jslt.protocal.JsltService;
import com.ppdai.ac.sms.api.provider.jslt.protocal.response.DeliverDetail;
import com.ppdai.ac.sms.api.provider.jslt.protocal.response.Delivers;
import com.ppdai.ac.sms.api.provider.jslt.protocal.response.ReportDetail;
import com.ppdai.ac.sms.api.provider.jslt.protocal.response.Reports;
import com.ppdai.ac.sms.provider.core.dao.domain.SmsMessageMoRecordBiz;
import com.ppdai.ac.sms.provider.core.dao.domain.SmsMessageRecordBiz;
import com.ppdai.ac.sms.provider.core.dao.domain.SmsMessageReportBiz;
import com.ppdai.ac.sms.provider.core.enums.InvokeResult;
import com.ppdai.ac.sms.provider.core.enums.RecordStatus;
import com.ppdai.ac.sms.provider.core.enums.ReportStatus;
import com.ppdai.ac.sms.provider.core.model.bo.BizResult;
import com.ppdai.ac.sms.provider.core.model.entity.SMSMessageRecordDTO;
import com.ppdai.ac.sms.provider.core.protocol.ProviderService;
import com.ppdai.ac.sms.provider.core.protocol.response.ProviderConfigResponse;
import com.ppdai.ac.sms.provider.core.response.SmsResponse;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;


/**
 * author cash
 * create 2017-05-04-16:59
 **/

@Service
public class JSLTProviderBiz {

    private final static Logger logger = LoggerFactory.getLogger(JSLTProviderBiz.class);


    private String REPORT_API_URL = "/v2statusApi.aspx";
    private String MO_API_URL = "/v2callApi.aspx";

    @Value("${provider.jslt.url}")
    private String hostUrl;

    @Value("${jslt.report.statusNum}")
    private String statusNum;

    @Autowired
    JsltService jsltService;

    @Autowired
    SmsMessageRecordBiz smsMessageRecordBiz;

    @Autowired
    ProviderService providerService;

    @Autowired
    SmsMessageMoRecordBiz smsMessageMoRecordBiz;

    @Autowired
    SmsMessageReportBiz smsMessageReportBiz;

    public SmsResponse getDelivers(String providerId) {

        //  配置需缓存
        logger.info("jslt 根据providerId getDelivers begin,providerId: " + providerId);

        SmsResponse smsResponse = new SmsResponse();

        smsResponse.setResultCode(InvokeResult.SUCCESS.getCode());
        smsResponse.setResultMessage("");

        String corp_userid = "";
        String corp_pass = "";
        String corp_account = "";

        String providerName = "";

        ProviderConfigResponse providerConfigResponse = providerService.getProviderConfigByProviderId(Integer.parseInt(providerId));
        if (InvokeResult.SUCCESS.getCode() == providerConfigResponse.getResultCode()) {

            if (null != providerConfigResponse.getResultObject()) {
                List<LinkedHashMap<String, String>> listConfig = (List<LinkedHashMap<String, String>>) providerConfigResponse.getResultObject();
                if (CollectionUtils.isNotEmpty(listConfig)) {
                    providerName = listConfig.get(0).get("ProviderName");
                }
                for (LinkedHashMap<String, String> map : listConfig) {
                    if (null != map.get("ConfigKey")) {
                        if ("userid".equals(map.get("ConfigKey"))) {
                            if (null != map.get("ConfigValue") && !"".equals(map.get("ConfigValue"))) {
                                corp_userid = map.get("ConfigValue");
                                continue;
                            }
                        }
                        if ("password".equals(map.get("ConfigKey"))) {
                            if (null != map.get("ConfigValue") && !"".equals(map.get("ConfigValue"))) {
                                corp_pass = map.get("ConfigValue");
                                continue;
                            }
                        }
                        if ("account".equals(map.get("ConfigKey"))) {
                            if (null != map.get("ConfigValue") && !"".equals(map.get("ConfigValue"))) {
                                corp_account = map.get("ConfigValue");
                                continue;
                            }
                        }
                    }
                }
            }
            if (StringUtils.isEmpty(corp_userid) || StringUtils.isEmpty(corp_pass) || StringUtils.isEmpty(corp_account)) {
                logger.error("jslt getDelivers,provider: " + providerId + " 参数配置错误");
                smsResponse.setResultCode(InvokeResult.FAIL.getCode());
                smsResponse.setResultMessage("江苏联通拉取上行参数配置错误");
                return smsResponse;
            }

            MultiValueMap<String, String> deliverRequest = new LinkedMultiValueMap<>();

            JSLTSignDTO jsltSignBiz = new JSLTSignDTO(corp_account, corp_pass);
            deliverRequest.add("userid", corp_userid);
            deliverRequest.add("timestamp", jsltSignBiz.getTimestamp());
            deliverRequest.add("sign", jsltSignBiz.getSign());
            deliverRequest.add("action", "query");
            // 拉取上行记录
            Delivers delivers = jsltService.getDelivers(deliverRequest);
            if (null != delivers) {
                if (null != delivers.getErrorDelivers() && CollectionUtils.isNotEmpty(delivers.getErrorDelivers())) {
                    logger.error("江苏联通拉取上行记录异常:" + delivers.getErrorDelivers());
                    smsResponse.setResultCode(InvokeResult.FAIL.getCode());
                    smsResponse.setResultMessage("江苏联通拉取上行记录异常：" + delivers.getErrorDelivers());
                    return smsResponse;
                }
                if (null != delivers.getDelivers() && CollectionUtils.isNotEmpty(delivers.getDelivers())) {
                    logger.info("------------jslt getDelives,complete return----------------------:" + JSONObject.toJSONString(delivers.getDelivers()));
                    for (DeliverDetail deliverDetail : delivers.getDelivers()) {
                        System.out.println("------------jslt getDelives,返回：" + deliverDetail.toString());
                        String channelNo = providerId;
                        String sender = deliverDetail.getMobile();
                        String content = deliverDetail.getContent();
                        String extno = deliverDetail.getExtno();
                        String receivetime = deliverDetail.getReceivetime();// 2011-12-02 22:12:11
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date d;
                        try {
                            d = format.parse(receivetime);
                        } catch (Exception e) {
                            logger.error("江苏联通extno: " + extno + " 拉取上行,日期转换异常" + e);
                            continue;
                        }
                        Timestamp timestamp = new Timestamp(d.getTime());
                        BizResult bizResult = smsMessageMoRecordBiz.saveMoRecord(channelNo, providerName, sender, content, extno, timestamp);
                        if (InvokeResult.SUCCESS.getCode() != bizResult.getResultCode()) {
                            logger.error("江苏联通extendNo: " + extno + " 保存上行记录异常:" + bizResult.getResultMessage());
                            continue;
                        }
                    }
                }

            }
        } else {
            logger.error("根据providerId: " + providerId + " 查询配置异常：" + providerConfigResponse.getResultMessage());
        }
        return smsResponse;
    }

    public SmsResponse getReports(String providerId) {
        // 配置需缓存
        logger.info("jslt 根据providerId get report begin,providerId: " + providerId);
        SmsResponse smsResponse = new SmsResponse();
        smsResponse.setResultCode(InvokeResult.SUCCESS.getCode());
        smsResponse.setResultMessage("");

        String corp_userid = "";
        String corp_pass = "";
        String corp_account = "";

        String providerName = "";

        ProviderConfigResponse providerConfigResponse = providerService.getProviderConfigByProviderId(Integer.parseInt(providerId));
        if (InvokeResult.SUCCESS.getCode() == providerConfigResponse.getResultCode()) {
            if (null != providerConfigResponse.getResultObject()) {
                List<LinkedHashMap<String, String>> listConfig = (List<LinkedHashMap<String, String>>) providerConfigResponse.getResultObject();
                if (CollectionUtils.isNotEmpty(listConfig)) {
                    providerName = listConfig.get(0).get("ProviderName");
                }
                for (LinkedHashMap<String, String> map : listConfig) {
                    if (null != map.get("ConfigKey")) {
                        if ("userid".equals(map.get("ConfigKey"))) {
                            if (null != map.get("ConfigValue") && !"".equals(map.get("ConfigValue"))) {
                                corp_userid = map.get("ConfigValue");
                                continue;
                            }
                        }
                        if ("password".equals(map.get("ConfigKey"))) {
                            if (null != map.get("ConfigValue") && !"".equals(map.get("ConfigValue"))) {
                                corp_pass = map.get("ConfigValue");
                                continue;
                            }
                        }
                        if ("account".equals(map.get("ConfigKey"))) {
                            if (null != map.get("ConfigValue") && !"".equals(map.get("ConfigValue"))) {
                                corp_account = map.get("ConfigValue");
                                continue;
                            }
                        }
                    }
                }
            }
        }
        if (StringUtils.isEmpty(corp_userid) || StringUtils.isEmpty(corp_pass) || StringUtils.isEmpty(corp_account)) {
            logger.error("jslt getDelivers,provider: " + providerId + " 参数配置错误");
            smsResponse.setResultCode(InvokeResult.FAIL.getCode());
            smsResponse.setResultMessage("江苏联通拉取回执参数配置错误");
            return smsResponse;
        }

                MultiValueMap<String, String> reportRequest = new LinkedMultiValueMap<>();

                JSLTSignDTO jsltSignBiz = new JSLTSignDTO(corp_account, corp_pass);
                reportRequest.add("userid", corp_userid);
                reportRequest.add("timestamp", jsltSignBiz.getTimestamp());
                reportRequest.add("sign", jsltSignBiz.getSign());
                reportRequest.add("statusNum",statusNum);
                reportRequest.add("action", "query");

                Reports reports = jsltService.getReports(reportRequest);
                logger.info("------------jslt get report,complete return----------------------:" + JSONObject.toJSONString(reports));
                if (null != reports) {
                    if (null != reports.getErrorReports() && CollectionUtils.isNotEmpty(reports.getErrorReports())) {
                        logger.info("拉取江苏联通回执错误: " + reports.getErrorReports());
                        smsResponse.setResultCode(InvokeResult.FAIL.getCode());
                        smsResponse.setResultMessage("拉取江苏联通回执异常：" + reports.getErrorReports());
                        return smsResponse;
                    }
                    if (null != reports.getReports() && CollectionUtils.isNotEmpty(reports.getReports())) {
                        for (ReportDetail reportDetail : reports.getReports()) {
                            logger.info("------------jslt get report,返回：" + reportDetail.toString());

                            String recordExt = reportDetail.getTaskid();
                            //根据recordExt查询记录信息
                            LocalDateTime endTime=LocalDateTime.now();
                            LocalDateTime startTime=endTime.minusDays(3);
                            SMSMessageRecordDTO smsMessageRecordDTO=smsMessageRecordBiz.getRecordByRecordExt(recordExt,startTime,endTime);
                            String messageId = "";
                            if(null != smsMessageRecordDTO){
                                messageId=smsMessageRecordDTO.getMessageId();
                            }

                            String recipient = reportDetail.getMobile();

                            String reportResult = reportDetail.getErrorcode();

                            int reportStatus = ReportStatus.ERROR_REPORT.getCode();

                            if (reportDetail.getStatus().equals(String.valueOf(JSLTReportStatus.SUCCESS.getCode()))) {
                                reportStatus = ReportStatus.SUCCESS_REPORT.getCode();
                            }
                            //完整报告
                            String providerMessage = reportDetail.toString();
                            String time = reportDetail.getReceivetime();// 2011-12-02 22:12:11
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            Date d;
                            try {
                                d = format.parse(time);
                            } catch (Exception e) {
                                logger.error("江苏联通recordExt: " + recordExt + " 拉取回执,日期转换异常" + e);
                                continue;
                            }
                            Timestamp reportTime = new Timestamp(d.getTime());

                            BizResult bizResult = smsMessageReportBiz.saveMessageReport(recordExt,messageId,providerId,providerName,recipient,reportResult,reportStatus,reportTime,null,providerMessage);
                            if (InvokeResult.SUCCESS.getCode() != bizResult.getResultCode()) {
                                logger.error("江苏联通recordExt: " + recordExt + " 保存回执报告异常：" + bizResult.getResultMessage());
                                continue;
                            }

                            //更新record记录的状态
                            int status;
                            if(ReportStatus.SUCCESS_REPORT.getCode()==reportStatus){
                                status= RecordStatus.SEND_SUCCESS.getCode();
                            }else{
                                status=RecordStatus.SEND_FAIL.getCode();
                            }
                            LocalDateTime localDate=LocalDateTime.now();
                            smsMessageRecordBiz.updateStatusByRecordExt(recordExt,status,localDate.minusDays(3),localDate);
                        }
                    }
                }


        return smsResponse;
    }
}
