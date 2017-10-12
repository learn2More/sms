package com.ppdai.ac.sms.contract.controller.permission;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by wangxiaomei02 on 2017/5/11.
 */
@FeignClient(name="ppdai-ac-sms-admin",url ="http://server.cas.ppdaicorp.com")
public interface PermissionService {

    @RequestMapping(value="/api/Permission/getuserrights",method= RequestMethod.GET)
    String load(@RequestParam("appId")String appId,@RequestParam("userId") String userId,@RequestParam("isProduct")String isProduct);
}
