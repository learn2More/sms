package com.ppdai.ac.sms.provider.core.protocol;

import com.ppdai.ac.sms.provider.core.protocol.request.ChangeMessageStatusRequest;
import com.ppdai.ac.sms.provider.core.protocol.request.MessageQueryRequest;
import com.ppdai.ac.sms.provider.core.protocol.response.ChangeMessageStatueResponse;
import com.ppdai.ac.sms.provider.core.protocol.response.MessageQueryResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * author cash
 * create 2017-05-19-16:15
 **/

@FeignClient(name="${ppdai.ac.sms.gateway.name}",url="")
/*@FeignClient(name="ppdai-ac-sms-gateway",url="http://192.168.211.58:8081")*/
@RequestMapping("/api/message")
public interface MessageService {
    @RequestMapping(value="/changestatus",
            method= RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    ChangeMessageStatueResponse changeMessageStatue(@RequestBody ChangeMessageStatusRequest request);

    @RequestMapping(value="/query",
            method= RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    MessageQueryResponse query(@RequestBody MessageQueryRequest messageQueryRequest);
}
