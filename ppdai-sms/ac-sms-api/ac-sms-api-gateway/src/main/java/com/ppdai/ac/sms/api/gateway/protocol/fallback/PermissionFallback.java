package com.ppdai.ac.sms.api.gateway.protocol.fallback;

import com.ppdai.ac.sms.api.gateway.protocol.PermissionService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * cas降级服务
 * author cash
 * create 2017-08-02-13:09
 **/

@Component
public class PermissionFallback implements PermissionService {
    @Override
    public String load(@RequestParam("appId") String appId, @RequestParam("userId") String userId, @RequestParam("isProduct") String isProduct) {
        return "error";
    }
}
