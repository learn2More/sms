package com.ppdai.ac.sms.contract.controller.batchsend;

import com.ppdai.ac.sms.contract.request.batchsend.BatchSendRequest;
import com.ppdai.ac.sms.contract.response.CallerResponse;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 *短信批量(定时)发送服务
 *author cash
 *create 2017/8/3-17:06
**/

@Api(value = "batchSendService", description = "the batchSendService API")
@RequestMapping("/api")
public interface BatchSendService {


    @ApiOperation(value = "短信批量(定时)发送接口", notes = "batchSend", response = CallerResponse.class, tags = {"batchSendService"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "批量(定时)发送", response = CallerResponse.class)})
    @RequestMapping(value = {"/batchSend"},
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    CallerResponse batchSend(@ApiParam(value = "request", required = true) @RequestBody BatchSendRequest request, HttpServletRequest httpServletRequest);

    }
