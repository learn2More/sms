package com.ppdai.ac.sms.provider.core.protocol;

import com.ppdai.ac.sms.provider.core.protocol.response.ProviderConfigResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * author cash
 * create 2017-05-19-15:10
 **/

/*@FeignClient(name="ppdai-ac-sms-gateway",url ="http://192.168.211.58:8081")*/
@FeignClient(name="${ppdai.ac.sms.gateway.name}",url =""/*, fallback=ProviderServiceFallback.class*/)
public interface ProviderService {
    @RequestMapping(value="/api/provider/query",
            method= RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ProviderConfigResponse getProviderConfigByProviderId(@RequestParam("providerId") Integer providerId);
}
