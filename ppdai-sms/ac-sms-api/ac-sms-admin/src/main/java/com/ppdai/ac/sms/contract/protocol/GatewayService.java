package com.ppdai.ac.sms.contract.protocol;

import com.ppdai.ac.sms.contract.protocol.request.BSendRequest;
import com.ppdai.ac.sms.contract.protocol.response.BSendResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * sms网关服务
 * author cash
 * create 2017-08-03-17:44
 **/
@FeignClient(name="${ppdai.ac.sms.gateway.name}",url="")
public interface GatewayService {

    @RequestMapping(value="/api/sms/batchSend",
            method= RequestMethod.POST,
            produces = "application/json;charset=utf-8",
            consumes = "application/json;charset=utf-8")
    @ResponseBody
    BSendResponse batchSend(BSendRequest bSendRequest);
}


