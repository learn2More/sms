package com.ppdai.ac.sms.api.provider.aliyun.utils;

import com.ppdai.ac.sms.api.provider.aliyun.request.SendMsgRequest;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * aliyun工具类
 * author cash
 * create 2017-05-08-10:42
 **/

public class AliyunUtil {
    public static Map<String, String> parseReportMsg(String returnMsg) {
        String tempMsg=returnMsg;
        //成功：messageID=23B500FASF59CA1B-1-15B5567FC70-200000009&receiver=12345678901&state=1&biz_id=103245234562^1324561234567&template_code=SMS_12346789&sms_count=1&receive_time=2017-03-31 11:28:19&ver=1.0&event=SendSuccessfully
        //失败：messageID=23B522F13F59CA1B-1-15B887C54B3-20000000A&receiver=11234567890&state=2&err_code=InvalidTemplateCode.Malformed|The specified templateCode is wrongly formed.&event=SendFailed
        if (StringUtils.isEmpty(tempMsg)) {
            tempMsg = "";
        }
        String[] str = tempMsg.split("&");
        Map<String, String> map = new HashMap<>();
        String phone = "";
        String errorCode = "";
        String messageId="";
        String state="";
        String receiveTime="";
        for (String temp : str) {
            if (temp.startsWith("messageID")) {
                messageId = temp.replace("messageID=", "");
            }
            if (temp.startsWith("receiver")) {
                phone = temp.replace("receiver=", "");
            }
            if (temp.startsWith("err_code")) {
                errorCode = temp.replace("err_code=", "");
            }
            if(temp.startsWith("state")){
                state=temp.replace("state=","");
            }
            if(temp.startsWith("receive_time")){
                receiveTime=temp.replace("receive_time=","");
            }
        }
        map.put("messageId",messageId);
        map.put("phone", phone);
        map.put("errorCode", errorCode);
        map.put("state",state);
        map.put("receiveTime",receiveTime);
        return map;
    }

    public static Map<String, String> parseMotMsg(String returnMsg) {
        String tempMsg=returnMsg;
        //上行：sender=8611234567890&content=刚才没开回信&receive_time=20170331132401&extend_code=111&ver=1.0&event=ReplyMessage
        if (StringUtils.isEmpty(tempMsg)) {
            tempMsg = "";
        }
        String[] str = tempMsg.split("&");
        Map<String, String> map = new HashMap<>();
        String sender = "";
        String content = "";
        String receiveTime="";
        String extendNo="";
        for (String temp : str) {
            if (temp.startsWith("sender")) {
                sender = temp.replace("sender=", "");
            }
            if (temp.startsWith("content")) {
                content = temp.replace("content=", "");
            }
            if(temp.startsWith("receive_time")){
                receiveTime=temp.replace("receive_time=","");
            }
            if(temp.startsWith("extend_code")){
                extendNo=temp.replace("extend_code=","");
            }
        }
        map.put("sender",sender);
        map.put("content", content);
        map.put("receiveTime",receiveTime);
        map.put("extendNo",extendNo);
        return map;
    }


    public static void  parseContent(String contentStr,SendMsgRequest sendMsgRequest){
        String tempCot=contentStr;
        //templateCode==SMS_46165066&&signName==Radius服务&&msg==xx||code==99
        if (StringUtils.isEmpty(tempCot)) {
            tempCot = "";
        }
        String[] str = tempCot.split("&&");
        Map<String, String> map = new HashMap<>();
        for (String s : str) {
            String[] inStr = s.split("==");
            if(inStr.length>0){
                if(inStr[0].equals("templateCode")){
                    sendMsgRequest.setTemplateCode(inStr[1]);
                }else if(inStr[0].equals("signName")){
                    sendMsgRequest.setSignName(inStr[1]);
                }else{
                    map.put(inStr[0],(inStr.length>1?inStr[1]:""));
                }
            }
        }
        sendMsgRequest.setParamMap(map);

    }


}
