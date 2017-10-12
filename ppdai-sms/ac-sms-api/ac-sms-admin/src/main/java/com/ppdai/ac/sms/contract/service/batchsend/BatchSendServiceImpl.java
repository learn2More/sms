package com.ppdai.ac.sms.contract.service.batchsend;

import com.alibaba.fastjson.JSONObject;
import com.ppdai.ac.sms.contract.controller.batchsend.BatchSendService;
import com.ppdai.ac.sms.contract.dao.mapper.smsbase.MessageBusinessMapper;
import com.ppdai.ac.sms.contract.model.vo.MessageBusinessVo;
import com.ppdai.ac.sms.contract.protocol.GatewayService;
import com.ppdai.ac.sms.contract.protocol.request.BSendRequest;
import com.ppdai.ac.sms.contract.protocol.response.BSendResponse;
import com.ppdai.ac.sms.contract.request.batchsend.BatchSendRequest;
import com.ppdai.ac.sms.contract.response.CallerResponse;
import com.ppdai.ac.sms.contract.utils.IpUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.util.Enumeration;

/**
 * Created by wangxiaomei02 on 2017/5/19.
 */
@RestController
public class BatchSendServiceImpl implements BatchSendService{
    private static final Logger logger = LoggerFactory.getLogger(BatchSendServiceImpl.class);

    public static InetAddress netAddress = IpUtil.getInetAddress();

    private String USER_ID="userid";

    @Autowired
    GatewayService gatewayService;

    @Autowired
    MessageBusinessMapper messageBusinessMapper;

    @Override
    public CallerResponse batchSend(@ApiParam(value = "request", required = true) @RequestBody BatchSendRequest request, HttpServletRequest httpServletRequest) {

        logger.info("batchSendService the complete params of invoke batchSend  are: "+ JSONObject.toJSONString(request));
        CallerResponse callerResponse=new CallerResponse();
        BSendRequest bSendRequest=new BSendRequest();

        BeanUtils.copyProperties(request, bSendRequest);

        /**根据businessId查询business信息**/
        MessageBusinessVo messageBusinessVo=messageBusinessMapper.findMessageBusinessById(request.getBusinessId());
        if(null!=messageBusinessVo){
            bSendRequest.setBusinessAlias(messageBusinessVo.getBusinessAlias());
        }

        String callerIp=IpUtil.getHostIp(netAddress);
        String hostName=IpUtil.getHostName(netAddress);
        String directory=IpUtil.getDirectory();
        String requestUrl= IpUtil.getRequest();

        bSendRequest.setCallerIp(callerIp);
        bSendRequest.setHostName(hostName);
        bSendRequest.setDirectory(directory);
        bSendRequest.setRequestUrl(requestUrl);

        /**request中获取userid信息**/
        String userId="";
        Enumeration headerNames = httpServletRequest.getHeaderNames();
        if (headerNames.hasMoreElements()) {
            while (headerNames.hasMoreElements()) {
                String name = (String) headerNames.nextElement();
                if (USER_ID.equalsIgnoreCase(name)) {
                    userId=httpServletRequest.getHeader(name);
                    break;
                }
            }
        }
        bSendRequest.setUserId(userId);

        logger.info("batchSendService to request the gateway,complete params are:"+JSONObject.toJSONString(bSendRequest));

        BSendResponse bSendResponse=gatewayService.batchSend(bSendRequest);

        logger.info("batchSendService request the gateway,complete return are:"+JSONObject.toJSONString(bSendResponse));

        if(null!=bSendResponse){
            callerResponse.setCode(bSendResponse.getResultCode());
            callerResponse.setMessage(bSendResponse.getResultMessage());
            callerResponse.setResult(bSendResponse.getResultObject());
        }

        return callerResponse;
    }
}
