package com.ppdai.ac.sms.api.gateway.protocol;

import com.ppdai.ac.sms.api.gateway.protocol.fallback.PermissionFallback;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * cas接口
 * author cash
 * create 2017-08-02-11:31
 **/
@FeignClient(name="${spring.application.name}",url ="http://server.cas.ppdaicorp.com",fallback = PermissionFallback.class)
public interface PermissionService {

    @RequestMapping(value="/api/Permission/getuserrights",method= RequestMethod.GET)
    String load(@RequestParam("appId")String appId, @RequestParam("userId") String userId, @RequestParam("isProduct")String isProduct);
}
