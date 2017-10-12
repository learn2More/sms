package com.ppdai.ac.sms.contract.controller.messagerecord;

import com.ppdai.ac.sms.contract.response.MessageRecordResponse;
import com.ppdai.ac.sms.contract.response.MessageReportResponse;
import com.ppdai.ac.sms.contract.response.SmsMessageMoRecordResponse;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;

/**
 * Created by wangxiaomei02 on 2017/5/19.
 * 消息记录服务
 */
@Api(value = "messageRecordService", description = "the messageRecordService API")
@RequestMapping("/api")
public interface MessageRecordService {
    @ApiOperation(value = "查询消息记录", notes = "/messageSend", response = MessageRecordResponse.class, tags = {"messageRecordService"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "查询结果", response = MessageRecordResponse.class)})
    @RequestMapping(value = {"/messageSend"},
            produces = {"application/json"},
            method = RequestMethod.GET)
    MessageRecordResponse getMessageRecordList(@RequestParam(value = "mobile",required = false)String mobile,
                                               @RequestParam(value ="status",required = false)Integer status,
                                               @RequestParam("messageType")Integer messageType,
                                               @RequestParam(value = "callerId",required = false)Integer callerId,
                                               @RequestParam(value = "businessId",required = false)Integer businessId,
                                               @RequestParam("beginTime")long beginTime,
                                               @RequestParam("endTime")long endTime,
                                               @RequestParam("pageNum")Integer pageNum,
                                               @RequestParam("pageSize")Integer pageSize);

    @ApiOperation(value = "查询短信上行记录", notes = "/queryReceive", response = SmsMessageMoRecordResponse.class, tags = {"messageRecordService"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "查询结果", response = SmsMessageMoRecordResponse.class)})
    @RequestMapping(value = {"/messageReceive"},
            produces = {"application/json"},
            method = RequestMethod.GET)
    SmsMessageMoRecordResponse getSmsMessageMoRecord(@RequestParam(value = "mobile",required = false)String mobile,
                                                     @RequestParam(value = "providerId",required = false) Integer providerId,
                                                     @RequestParam("beginTime")long beginTime,
                                                     @RequestParam("endTime")long endTime,
                                                     @RequestParam("pageNum")Integer pageNum,
                                                     @RequestParam("pageSize")Integer pageSize);

    @ApiOperation(value = "查询消息回执记录", notes = "/queryRecord", response = MessageReportResponse.class, tags = {"messageRecordService"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "查询结果", response = MessageReportResponse.class)})
    @RequestMapping(value = {"/messageRecord"},
            produces = {"application/json"},
            method = RequestMethod.GET)
    MessageReportResponse getSmsMessageReport(@RequestParam("beginTime")Long beginTime,
                                              @RequestParam("endTime")Long endTime,
                                              @RequestParam("messageId")String messageId);

    @ApiOperation(value = "查询发送消息总条数", notes = "/messageSendTotal", response = MessageReportResponse.class, tags = {"messageRecordService"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "查询结果", response = MessageReportResponse.class)})
    @RequestMapping(value = {"/messageSendTotal"},
            produces = {"application/json"},
            method = RequestMethod.GET)
    MessageRecordResponse getMessageSendTotal(@RequestParam(value = "mobile",required = false)String mobile,
                            @RequestParam(value ="status",required = false)Integer status,
                            @RequestParam("messageType")Integer messageType,
                            @RequestParam(value = "callerId",required = false)Integer callerId,
                            @RequestParam(value = "businessId",required = false)Integer businessId,
                            @RequestParam("beginTime")long beginTime,
                            @RequestParam("endTime")long endTime);
}
