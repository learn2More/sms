package com.ppdai.ac.sms.api.provider.baiwu.domain.biz;

import com.alibaba.fastjson.JSONObject;
import com.ppdai.ac.sms.api.provider.baiwu.domain.model.JsonDeliverReturnCode;
import com.ppdai.ac.sms.api.provider.baiwu.domain.model.JsonReportReturnCode;
import com.ppdai.ac.sms.api.provider.baiwu.protocol.BaiwuService;
import com.ppdai.ac.sms.api.provider.baiwu.protocol.response.JDeliverDetail;
import com.ppdai.ac.sms.api.provider.baiwu.protocol.response.JDelivers;
import com.ppdai.ac.sms.api.provider.baiwu.protocol.response.JReportDetail;
import com.ppdai.ac.sms.api.provider.baiwu.protocol.response.JReports;
import com.ppdai.ac.sms.provider.core.dao.domain.SmsMessageMoRecordBiz;
import com.ppdai.ac.sms.provider.core.dao.domain.SmsMessageReportBiz;
import com.ppdai.ac.sms.provider.core.enums.InvokeResult;
import com.ppdai.ac.sms.provider.core.enums.ReportStatus;
import com.ppdai.ac.sms.provider.core.model.bo.BizResult;
import com.ppdai.ac.sms.provider.core.protocol.ProviderService;
import com.ppdai.ac.sms.provider.core.protocol.response.ProviderConfigResponse;
import com.ppdai.ac.sms.provider.core.response.SmsResponse;
import com.ppdai.ac.sms.provider.core.utils.MD5Util;
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
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * author cash
 * create 2017-05-02-13:33
 **/

@Service
public class BWProviderBiz {


    private final static Logger logger = LoggerFactory.getLogger(BWProviderBiz.class);

    private  String SEND_API_URL="/sms_send2.do";
    private  String REPORT_API_URL="/post_report.do";
    private  String MO_API_URL="/post_deliverMsg.do";

    @Value("${provider.baiwu.url}")
    private String hostUrl;


    @Autowired
    BaiwuService baiwuService;

    @Autowired
    ProviderService providerService;

    @Autowired
    SmsMessageMoRecordBiz smsMessageMoRecordBiz;

    @Autowired
    SmsMessageReportBiz smsMessageReportBiz;

   /* public String send(SendMsgRequest sendRequest){
        String msg=null;

        String content=sendRequest.getMsg_content();
        CloseableHttpClient httpclient = HttpClients.createDefault();

        try {
            HttpPost httppost = new HttpPost(hostUrl+SEND_API_URL);
            content= URLEncoder.encode(content, "GBK");

            //组装http请求体
            httppost.addHeader("Content-Type","application/x-www-form-urlencoded;charset=gbk");//在头文件中设置转码
            String receiver=String.join(",",sendRequest.getMobile());
            //String msg_id= "bw-"+ UUID.randomUUID();
            String msg_id=sendRequest.getCorp_msg_id();
            httppost.setEntity(new StringEntity("corp_id="+sendRequest.getCorp_id()
                    +"&corp_pwd="+sendRequest.getCorp_pwd()
                    +"&corp_service="+sendRequest.getCorp_service()
                    +"&mobile="+receiver
                    +"&msg_content="+content
                    +"&corp_msg_id="+msg_id
                    +"&ext="+sendRequest.getExt()));
            //执行Http请求,得到响应
            CloseableHttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            msg = EntityUtils.toString(entity, "UTF-8");

        } catch (Exception e) {
            logger.error("百悟短信发送异常",e);
        } finally {
            // 关闭连接
            try {
                httpclient.close();
            } catch (IOException e) {
                logger.error("关闭httpClient异常",e);
            }
        }

        return msg;
    }*/

/*    public Reports getReports(){

        //TODO 获取服务商配置
        //ReportRequest reportRequest=new ReportRequest("wj2536","wj2536","6588wh");
        //ReportRequest reportRequest=new ReportRequest("pp6588","pp6588","mm2289");
        ReportRequest reportRequest=new ReportRequest("","","");

        CloseableHttpClient httpclient = HttpClients.createDefault();
        Reports reports=null;


        try{
            HttpPost httppost = new HttpPost(hostUrl+REPORT_API_URL);
            //组装http请求体
            httppost.addHeader("Content-Type","application/x-www-form-urlencoded;charset=gbk");//在头文件中设置转码

            httppost.setEntity(new StringEntity("corp_id="+reportRequest.getCorp_id()
                    +"&user_id="+reportRequest.getUser_id()
                    +"&corp_pwd="+reportRequest.getCorp_pwd()));
            //执行Http请求,得到响应
            CloseableHttpResponse response;
            response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity, "UTF-8");
            if("0".equals(result)){
                // 暂时没有数据
            }else if(!result.startsWith("<")){
                logger.error("百悟短信回执拉取异常");
            }else{
                reports= XmlUtil.desrialize(result,Reports.class);
            }

        }catch (Exception e){
            logger.error("百悟短信回执拉取异常",e);
        }finally {
            // 关闭连接
            try {
                httpclient.close();
            } catch (IOException e) {
                logger.error("关闭httpClient异常",e);
            }
        }

        return reports;

    }*/

/*    public Delivers getDelivers(){

        DeliverRequest deliverRequest=new DeliverRequest("","","");

        CloseableHttpClient httpclient = HttpClients.createDefault();
        Delivers delivers=null;
        try{
            HttpPost httppost = new HttpPost(hostUrl+MO_API_URL);

            //组装http请求体
            httppost.addHeader("Content-Type","application/x-www-form-urlencoded;charset=gbk");//在头文件中设置转码

            httppost.setEntity(new StringEntity("corp_id="+deliverRequest.getCorp_id()
                    +"&user_id="+deliverRequest.getCorp_id()
                    +"&corp_pwd="+deliverRequest.getCorp_pwd()));

            //执行Http请求,得到响应
            CloseableHttpResponse response ;

            response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity, "UTF-8");
            if("0".equals(result)){
                //没有数据
            }else if(!result.startsWith("<")){
                logger.error("百悟短信上行拉取异常");
            }else{
                delivers= XmlUtil.desrialize(result,Delivers.class);
            }
        }catch (Exception e){
            logger.error("百悟短信上行拉取异常",e);
        }finally {
            // 关闭连接
            try {
                httpclient.close();
            } catch (IOException e) {
                logger.error("关闭httpClient异常",e);
            }
        }

        return delivers;
    }*/


    public SmsResponse getDelivers(String providerId){
        // TODO: 2017/5/19   配置需缓存
        logger.info("baiwu 根据providerId getDelivers begin,providerId: "+providerId);

        SmsResponse smsResponse=new SmsResponse();

        smsResponse.setResultCode(InvokeResult.SUCCESS.getCode());
        smsResponse.setResultMessage("");

        String corp_id="";
        String user_id="";
        String corp_pswOrd="";

        String providerName="";

        ProviderConfigResponse providerConfigResponse=providerService.getProviderConfigByProviderId(Integer.parseInt(providerId));
        if(InvokeResult.SUCCESS.getCode()==providerConfigResponse.getResultCode()){

            if(null!=providerConfigResponse.getResultObject()){
                List<LinkedHashMap<String,String>> listConfig= (List<LinkedHashMap<String, String>>) providerConfigResponse.getResultObject();
                if(CollectionUtils.isNotEmpty(listConfig)){
                    providerName=listConfig.get(0).get("ProviderName");
                }
                for(LinkedHashMap<String,String> map :listConfig){
                    if(null!=map.get("ConfigKey")){
                        if("corpId".equals(map.get("ConfigKey")) &&
                                (null!=map.get("ConfigValue")&&!"".equals(map.get("ConfigValue"))) ){
                                corp_id=map.get("ConfigValue");
                                user_id=map.get("ConfigValue");
                                continue;
                        }
                        if("corpPwd".equals(map.get("ConfigKey")) &&
                                (null!=map.get("ConfigValue")&&!"".equals(map.get("ConfigValue")))){
                                corp_pswOrd=map.get("ConfigValue");
                                continue;
                        }
                    }
                }
            }
            if(StringUtils.isEmpty(corp_id)||StringUtils.isEmpty(user_id)||StringUtils.isEmpty(corp_pswOrd)){
                logger.error("baiwu getDelivers,provider: "+providerId+" 参数配置错误");
                smsResponse.setResultCode(InvokeResult.FAIL.getCode());
                smsResponse.setResultMessage("根据providerId: "+providerId+" 百悟拉取上行参数配置错误");
                return smsResponse;
            }

            MultiValueMap<String, String> deliverRequest = new LinkedMultiValueMap<>();

            deliverRequest.add("corp_id", corp_id);
            deliverRequest.add("user_id", user_id);
            deliverRequest.add("corp_pwd", corp_pswOrd);

            String tstamp=Long.toString(System.currentTimeMillis());
            //签名  md5(账号+密码+时间戳)
            String sign= MD5Util.MD5(corp_id+corp_pswOrd+tstamp);
            deliverRequest.add("timestamp", tstamp);
            deliverRequest.add("sign", sign);

            JDelivers delivers = baiwuService.getJsonDelivers(deliverRequest);
            if(null!=delivers){
                if(StringUtils.isNotEmpty(delivers.getCode())){
                    if (JsonDeliverReturnCode.NO_MESSAGE.getCode().equals(delivers.getCode())) {
                        logger.info("根据providerId: "+providerId+" 百悟拉取上行记录为空");
                    } else if (!JsonDeliverReturnCode.SUCCESS.getCode().equals(delivers.getCode())) {
                        logger.error("baiwu getDeliversError:" + delivers.getMsg());
                        smsResponse.setResultCode(InvokeResult.FAIL.getCode());
                        smsResponse.setResultMessage("根据providerId: "+providerId+" 百悟拉取上行记录异常："+delivers.getMsg());
                        return smsResponse;
                    }
                }
                if(null!=delivers.getData()&&CollectionUtils.isNotEmpty(delivers.getData())){
                    logger.info("------------baiwu getDelives,complete return----------------------:"+ JSONObject.toJSONString(delivers.getData()));
                    for(JDeliverDetail deliverDetail : delivers.getData()){
                        String channelNo=providerId;
                        String sender=deliverDetail.getMobile();
                        String content=deliverDetail.getContent();
                        String extendNo=deliverDetail.getExt();
                        String time=deliverDetail.getDeliver_time();// 2010-07-02 00:00:00
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date d;
                        try {
                            d = format.parse(time);
                        } catch (Exception e) {
                            logger.error("百悟extendNo: "+extendNo+" 拉取上行,日期转换异常",e);
                            continue;
                        }
                        Timestamp timestamp = new Timestamp(d.getTime());
                        BizResult bizResult=smsMessageMoRecordBiz.saveMoRecord(channelNo,providerName,sender,content,extendNo,timestamp);
                        if(InvokeResult.SUCCESS.getCode()!=bizResult.getResultCode()){
                            logger.error("百悟extendNo: "+extendNo+" 保存上行记录异常:"+ bizResult.getResultMessage());
                            continue;
                        }
                    }
                }

            }


        }else{
            logger.error("根据providerId: "+providerId+" 查询配置异常："+providerConfigResponse.getResultMessage());
        }

        return smsResponse;
    }

    public SmsResponse getReports(String providerId){
        logger.info("baiwu 根据providerId get report begin,providerId: "+providerId);
        SmsResponse smsResponse=new SmsResponse();
        smsResponse.setResultCode(InvokeResult.SUCCESS.getCode());
        smsResponse.setResultMessage("");

        String corp_id="";
        String user_id="";
        String corp_pswOrd="";

        String providerName="";

        ProviderConfigResponse providerConfigResponse=providerService.getProviderConfigByProviderId(Integer.parseInt(providerId));
        if(InvokeResult.SUCCESS.getCode()==providerConfigResponse.getResultCode()){

            if(null!=providerConfigResponse.getResultObject()){
                List<LinkedHashMap<String,String>> listConfig= (List<LinkedHashMap<String, String>>) providerConfigResponse.getResultObject();
                if(CollectionUtils.isNotEmpty(listConfig)){
                    providerName=listConfig.get(0).get("ProviderName");
                    for(LinkedHashMap<String,String> map :listConfig){
                        if(null!=map.get("ConfigKey")){
                            if("corpId".equals(map.get("ConfigKey")) &&
                                    (null!=map.get("ConfigValue")&&!"".equals(map.get("ConfigValue")))  ){
                                    corp_id=map.get("ConfigValue");
                                    user_id=map.get("ConfigValue");
                                    continue;
                            }
                            if("corpPwd".equals(map.get("ConfigKey")) &&
                                    (null!=map.get("ConfigValue")&&!"".equals(map.get("ConfigValue")))){
                                    corp_pswOrd=map.get("ConfigValue");
                                    continue;
                            }
                        }
                    }
                }

            }

            if(StringUtils.isEmpty(corp_id)||StringUtils.isEmpty(user_id)||StringUtils.isEmpty(corp_pswOrd)){
                logger.error("baiwu getReport,provider: "+providerId+" 参数配置错误");
                smsResponse.setResultCode(InvokeResult.FAIL.getCode());
                smsResponse.setResultMessage("根据providerId: "+providerId+" 百悟拉取上行参数配置错误");
                return smsResponse;
            }

            MultiValueMap<String, String> reportRequest = new LinkedMultiValueMap<>();

            reportRequest.add("corp_id", corp_id);
            reportRequest.add("user_id", user_id);
            reportRequest.add("corp_pwd", corp_pswOrd);

            String tstamp=Long.toString(System.currentTimeMillis());
            //签名  md5(账号+密码+时间戳)
            String sign= MD5Util.MD5(corp_id+corp_pswOrd+tstamp);
            reportRequest.add("timestamp",tstamp);
            reportRequest.add("sign",sign);

            JReports reports=baiwuService.getJsonReports(reportRequest);
            logger.info("------------baiwu get report,complete return----------------------:"+ JSONObject.toJSONString(reports));
            if(null!=reports){
                if(StringUtils.isNotEmpty(reports.getCode())){
                    if(JsonReportReturnCode.NO_MESSAGE.getCode().equals(reports.getCode())){
                        logger.info("根据providerId: "+providerId+" 拉取百悟回执为空");
                    }else if(!JsonReportReturnCode.SUCCESS.getCode().equals(reports.getCode())){
                        logger.info("baiwu getReportsError: "+reports.getMsg());
                        smsResponse.setResultCode(InvokeResult.FAIL.getCode());
                        smsResponse.setResultMessage("根据providerId: "+providerId+" 拉取百悟回执异常："+reports.getMsg());
                        return smsResponse;
                    }
                }
                if(null!=reports.getData()&&CollectionUtils.isNotEmpty(reports.getData())){
                    for(JReportDetail reportDetail:reports.getData()){
                        logger.info("------------baiwu get report,返回："+reportDetail.toString());

                        String recordId=reportDetail.getMsg_id();
                        String messageId="";
                        String recipient=reportDetail.getMobile();

                        String reportResult=reportDetail.getFail_desc();

                        int reportStatus= ReportStatus.ERROR_REPORT.getCode();

                        if(StringUtils.isNotEmpty(reportDetail.getErr())){
                            if(reportDetail.getErr().startsWith("0")){
                                reportStatus= ReportStatus.SUCCESS_REPORT.getCode();
                            }
                        }
                        //完整报告
                        String providerMessage=JSONObject.toJSONString(reportDetail);
                        String  time=reportDetail.getReport_time();// 2010-07-02 00:00:00
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date d;
                        try {
                            d = format.parse(time);
                        } catch (Exception e) {
                            logger.error("百悟recordId: "+recordId+" 拉取上行,日期转换异常",e);
                            continue;
                        }
                        Timestamp reportTime = new Timestamp(d.getTime());

                        BizResult bizResult=smsMessageReportBiz.saveMessageReport(recordId,messageId,providerId,providerName,recipient,reportResult,reportStatus,reportTime,null,providerMessage);
                        if(InvokeResult.SUCCESS.getCode()!=bizResult.getResultCode()){
                            logger.error("百悟recordId: "+recordId+" 保存回执报告异常："+bizResult.getResultMessage());
                            continue;
                        }
                    }
                }

            }
        }

        return smsResponse;
    }





}
