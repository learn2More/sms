package com.ppdai.ac.sms.provider.core.protocol;

import com.ppdai.ac.sms.provider.core.protocol.request.ImportBlackListRequest;
import com.ppdai.ac.sms.provider.core.protocol.response.ImportBlackListResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * author cash
 * create 2017-05-19-11:20
 **/

/*@FeignClient(name="ppdai-ac-sms-gateway",url="http://192.168.211.58:8081")*/
@FeignClient(name="${ppdai.ac.sms.gateway.name}",url="")
@RequestMapping("/api/blacklist")
public interface BlackListService {

    @RequestMapping(value="/import",
            method= RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    ImportBlackListResponse importBlackList(@RequestBody ImportBlackListRequest request);
}
