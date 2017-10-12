package com.ppdai.ac.sms.contract.controller.provider;

import com.ppdai.ac.sms.contract.request.provider.ProviderConfigRequest;
import com.ppdai.ac.sms.contract.request.provider.ProviderInfoRequest;
import com.ppdai.ac.sms.contract.request.provider.ServiceProviderRequest;
import com.ppdai.ac.sms.contract.response.DepartmentResponse;
import com.ppdai.ac.sms.contract.response.ProviderConfigurationResponse;
import com.ppdai.ac.sms.contract.response.ServiceProviderResponse;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

/**
 * Created by wangxiaomei02 on 2017/5/12.
 * 服务商相关接口
 */
@Api(value = "serviceProviderService", description = "the ServiceProviderService API")
@RequestMapping("/api")
public interface ServiceProviderService {
    @ApiOperation(value = "查询服务商", notes = "provider", response = ServiceProviderResponse.class, tags = {"serviceProviderService"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "查询结果", response = ServiceProviderResponse.class)})
    @RequestMapping(value = {"/provider"},
            produces = {"application/json"},
            method = RequestMethod.GET)
    ServiceProviderResponse getProvider(@RequestParam(value = "businessType",required = false) Integer businessType,
                                            @RequestParam(value = "messageType",required = false) Integer messageType);

    @ApiOperation(value = "添加服务商+批量添加服务商配置", notes = "provider", response = ServiceProviderResponse.class, tags = {"serviceProviderService"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "保存结果", response = ServiceProviderResponse.class)})
    @RequestMapping(value = {"/provider"},
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ProviderConfigurationResponse saveProvider(@ApiParam(value = "request", required = true) @RequestBody ServiceProviderRequest request);

    @ApiOperation(value = "添加单个服务商配置", notes = "/provider/{providerId}/config", response = ServiceProviderResponse.class, tags = {"serviceProviderService"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "保存结果", response = ServiceProviderResponse.class)})
    @RequestMapping(value = {"/provider/{providerId}/config"},
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ProviderConfigurationResponse saveProviderConfig(@PathVariable("providerId") Integer providerId, @ApiParam(value = "request", required = true) @RequestBody ProviderConfigRequest request);

    @ApiOperation(value = "编辑服务商", notes = "/provider/{providerId}", response = ServiceProviderResponse.class, tags = {"serviceProviderService"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "保存结果", response = DepartmentResponse.class)})
    @PutMapping(value = {"/provider/{providerId}"},
            produces = {"application/json"},
            consumes = {"application/json"})
    ServiceProviderResponse editProvider(@PathVariable("providerId") Integer providerId,@ApiParam(value = "request", required = true) @RequestBody ProviderInfoRequest request);

    @ApiOperation(value = "编辑服务商配置", notes = "/provider/{providerId}/config/{configId}", response = ServiceProviderResponse.class, tags = {"serviceProviderService"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "保存结果", response = DepartmentResponse.class)})
    @PutMapping(value = {"/provider/{providerId}/config/{configId}"},
            produces = {"application/json"})
    ServiceProviderResponse editProviderConfig(@PathVariable(value = "providerId",required = false) Integer providerId,@PathVariable("configId") Integer configId,@ApiParam(value = "request", required = true) @RequestBody ProviderConfigRequest request);

    @ApiOperation(value = "删除服务商", notes = "provider/{providerId}", response = ServiceProviderResponse.class, tags = {"serviceProviderService"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "保存结果", response = DepartmentResponse.class)})
    @DeleteMapping(value = {"/provider/{providerId}"},
            produces = {"application/json"})
    ProviderConfigurationResponse delProvider(@PathVariable("providerId") Integer providerId);

    @ApiOperation(value = "删除服务商配置", notes = "/provider/{providerId}/config/{configId}", response = ServiceProviderResponse.class, tags = {"serviceProviderService"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "保存结果", response = DepartmentResponse.class)})
    @DeleteMapping(value = {"/provider/{providerId}/config/{configId}"},
            produces = {"application/json"})
    ProviderConfigurationResponse delProviderConfig(@PathVariable(value = "providerId",required = false) Integer providerId,@PathVariable("configId") Integer configId);

}
