package com.ppdai.ac.sms.contract.controller.caller;

import com.ppdai.ac.sms.contract.request.caller.CallerRequest;
import com.ppdai.ac.sms.contract.response.CallerResponse;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

/**
 * Created by wangxiaomei02 on 2017/5/12.
 */
@Api(value = "callerService", description = "the callerService API")
@RequestMapping("/api")
public interface CallerService {
    @ApiOperation(value = "查询技术接入方", notes = "caller", response = CallerResponse.class, tags = {"callerService"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "查询结果", response = CallerResponse.class)})
    @RequestMapping(value = {"/caller"},
            produces = {"application/json"},
            method = RequestMethod.GET)
    CallerResponse getCallerList();

    @ApiOperation(value = "添加接入方", notes = "caller", response = CallerResponse.class, tags = {"callerService"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "保存结果", response = CallerResponse.class)})
    @RequestMapping(value = {"/caller"},
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    CallerResponse saveCaller(@ApiParam(value = "request", required = true)@PathVariable @RequestBody CallerRequest request);

    @ApiOperation(value = "编辑接入方", notes = "caller/{callerId}", response = CallerResponse.class, tags = {"callerService"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "保存结果", response = CallerResponse.class)})
    @PutMapping(value = {"/caller/{callerId}"},
            produces = {"application/json"})
    CallerResponse editCaller(@PathVariable("callerId") Integer callerId,@ApiParam(value = "request", required = true) @RequestBody CallerRequest request);

    @ApiOperation(value = "查询接入方详情", notes = "caller/{callerId}", response = CallerResponse.class, tags = {"callerService"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "查询结果", response = CallerResponse.class)})
    @RequestMapping(value = {"/caller/{callerId}"},
            produces = {"application/json"},
            method = RequestMethod.GET)
    CallerResponse getCallerById(@RequestParam(value = "callerId") Integer callerId);

    @ApiOperation(value = "删除技术接入方", notes = "caller/{callerId}", response = CallerResponse.class, tags = {"callerService"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "保存结果", response = CallerResponse.class)})
    @DeleteMapping(value = {"/caller/{callerId}"},
            produces = {"application/json"})
    CallerResponse delCallerById(@PathVariable("callerId") Integer callerId);
}
