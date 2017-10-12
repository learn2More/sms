package com.ppdai.ac.sms.api.gateway.controller;

import com.ppdai.ac.sms.api.gateway.request.QueryProviderConfigRequest;
import com.ppdai.ac.sms.api.gateway.response.ChangeMessageStatusResponse;
import com.ppdai.ac.sms.api.gateway.response.QueryProviderConfigResponse;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by kiekiyang on 2017/5/18.
 */
@Api(value = "ProviderService", description = "the ProviderService API")
@RequestMapping("/api/provider")
public interface ProviderService {
    @ApiOperation(value = "获取消息配置", notes = "queryProviderConfig", response = QueryProviderConfigResponse.class, tags = {"ProviderService"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "获取消息配置", response = QueryProviderConfigResponse.class)})
    @RequestMapping(value = {"/query"},
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    QueryProviderConfigResponse queryProviderConfig(@ApiParam(value = "request", required = true) @RequestBody QueryProviderConfigRequest request);
}
