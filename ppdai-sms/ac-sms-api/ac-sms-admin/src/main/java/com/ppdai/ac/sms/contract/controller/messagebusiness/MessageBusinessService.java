package com.ppdai.ac.sms.contract.controller.messagebusiness;

import com.ppdai.ac.sms.contract.request.messagebusiness.MessageBusinessRequest;
import com.ppdai.ac.sms.contract.response.MessageBusinessResponse;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

/**
 * Created by wangxiaomei02 on 2017/5/25.
 * 业务服务
 */
@Api(value = "messageBusinessService", description = "the messageBusinessService API")
@RequestMapping("/api")
public interface MessageBusinessService {
    @ApiOperation(value = "查询数据库业务类型", notes = "messageBusiness", response = MessageBusinessResponse.class, tags = {"messageBusinessService"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "查询结果", response = MessageBusinessResponse.class)})
    @RequestMapping(value = {"/messageBusiness"},
            produces = {"application/json"},
            method = RequestMethod.GET)
    MessageBusinessResponse getMessageBusinessForDB();

    @ApiOperation(value = "查询配置业务类型", notes = "messageBusiness", response = MessageBusinessResponse.class, tags = {"messageBusinessService"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "查询结果", response = MessageBusinessResponse.class)})
    @RequestMapping(value = {"/messageBusiness/config"},
            produces = {"application/json"},
            method = RequestMethod.GET)
    MessageBusinessResponse getMessageBusinessForConfig();

    @ApiOperation(value = "添加业务类型", notes = "messageBusiness", response = MessageBusinessResponse.class, tags = {"messageBusinessService"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "保存结果", response = MessageBusinessResponse.class)})
    @RequestMapping(value = {"/messageBusiness"},
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    MessageBusinessResponse saveMessageBusiness(@ApiParam(value = "request", required = true) @RequestBody MessageBusinessRequest request);


    @ApiOperation(value = "编辑业务类型", notes = "messageBusiness/{businessId}", response = MessageBusinessResponse.class, tags = {"messageBusinessService"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "保存结果", response = MessageBusinessResponse.class)})
    @PutMapping(value = {"/messageBusiness/{businessId}"},
            produces = {"application/json"})
    MessageBusinessResponse editMessageBusiness(@PathVariable("businessId") Integer businessId,@ApiParam(value = "request", required = true) @RequestBody MessageBusinessRequest request);

    @ApiOperation(value = "查询业务类型", notes = "messageBusiness/{businessId}", response = MessageBusinessResponse.class, tags = {"messageBusinessService"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "查询结果", response = MessageBusinessResponse.class)})
    @RequestMapping(value = {"/messageBusiness/{businessId}"},
            produces = {"application/json"},
            method = RequestMethod.GET)
    MessageBusinessResponse getMessageBusinessById(@PathVariable("businessId") Integer businessId);

    @ApiOperation(value = "删除业务类型", notes = "messageBusiness/{businessId}", response = MessageBusinessResponse.class, tags = {"messageBusinessService"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "删除结果", response = MessageBusinessResponse.class)})
    @DeleteMapping(value = {"/messageBusiness/{businessId}"},
            produces = {"application/json"})
    MessageBusinessResponse delMessageBusiness(@PathVariable("businessId") Integer businessId);


}
