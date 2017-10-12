package com.ppdai.ac.sms.api.gateway.controller;

import com.ppdai.ac.sms.api.gateway.request.ChangeMessageStatusRequest;
import com.ppdai.ac.sms.api.gateway.request.QueryMessageByIdRequest;
import com.ppdai.ac.sms.api.gateway.response.ChangeMessageStatusResponse;
import com.ppdai.ac.sms.api.gateway.response.QueryMessageByIdResponse;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by kiekiyang on 2017/5/18.
 */
@Api(value = "MessageService", description = "the MessageService API")
@RequestMapping("/api/message")
public interface MessageService {

        @ApiOperation(value = "修改消息状态", notes = "changeMessageStatus", response = ChangeMessageStatusResponse.class, tags = {"MessageService"})
        @ApiResponses(value = {
                @ApiResponse(code = 200, message = "修改消息状态", response = ChangeMessageStatusResponse.class)})
        @RequestMapping(value = {"/changestatus"},
                produces = {"application/json"},
                consumes = {"application/json"},
                method = RequestMethod.POST)
    ChangeMessageStatusResponse changeMessageStatus(@ApiParam(value = "request", required = true) @RequestBody ChangeMessageStatusRequest request);


    @ApiOperation(value = "根据消息编号查询消息信息", notes = "queryMessageById", response = QueryMessageByIdResponse.class, tags = {"MessageService"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "根据消息编号查询消息信息", response = QueryMessageByIdResponse.class)})
    @RequestMapping(value = {"/query"},
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    QueryMessageByIdResponse queryMessageById(@ApiParam(value = "request", required = true) @RequestBody QueryMessageByIdRequest request);
}
