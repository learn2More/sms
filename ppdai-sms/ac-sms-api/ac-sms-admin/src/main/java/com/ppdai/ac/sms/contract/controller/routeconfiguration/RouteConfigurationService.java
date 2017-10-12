package com.ppdai.ac.sms.contract.controller.routeconfiguration;

import com.ppdai.ac.sms.contract.request.routeconfiguration.RouteConfigurationRequest;
import com.ppdai.ac.sms.contract.response.RouteConfigurationResponse;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

/**
 * Created by wangxiaomei02 on 2017/5/19.
 * 路由配置服务
 */
@Api(value = "routeConfigurationService", description = "the RouteConfigurationService API")
@RequestMapping("/api")
public interface RouteConfigurationService {
    @ApiOperation(value = "查询路由配置", notes = "routeConfiguration", response = RouteConfigurationResponse.class, tags = {"routeConfigurationService"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "查询结果", response = RouteConfigurationResponse.class)})
    @RequestMapping(value = {"/routeConfiguration"},
            produces = {"application/json"},
            method = RequestMethod.GET)
    RouteConfigurationResponse getRouteConfiguration(@RequestParam(value = "businessId",required = false)Integer businessId);

    @ApiOperation(value = "添加路由配置", notes = "routeConfiguration", response = RouteConfigurationResponse.class, tags = {"routeConfigurationService"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "保存结果", response = RouteConfigurationResponse.class)})
    @RequestMapping(value = {"/routeConfiguration"},
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    RouteConfigurationResponse saveRouteConfiguration(@ApiParam(value = "request", required = true) @RequestBody RouteConfigurationRequest request);

    @ApiOperation(value = "编辑路由配置", notes = "routeConfiguration/{channelId}", response = RouteConfigurationResponse.class, tags = {"routeConfigurationService"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "保存结果", response = RouteConfigurationResponse.class)})
    @PutMapping(value = {"/routeConfiguration/{channelId}"},
            produces = {"application/json"})
    RouteConfigurationResponse editRouteConfiguration(@PathVariable(value = "channelId", required = true)Integer channelId,@ApiParam(value = "request", required = true) @RequestBody RouteConfigurationRequest request);

    @ApiOperation(value = "删除路由配置", notes = "routeConfiguration/{bpId}", response = RouteConfigurationResponse.class, tags = {"routeConfigurationService"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "保存结果", response = RouteConfigurationResponse.class)})
    @DeleteMapping(value = {"/routeConfiguration/{channelId}"},
            produces = {"application/json"})
    RouteConfigurationResponse delRouteConfiguration(@PathVariable(value = "channelId", required = true)Integer channelId);
}
