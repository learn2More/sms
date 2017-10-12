package com.ppdai.ac.sms.contract.controller.black;

import com.ppdai.ac.sms.contract.request.black.BlackListRequest;
import com.ppdai.ac.sms.contract.response.BlackListResponse;
import com.ppdai.ac.sms.contract.response.CallerResponse;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by wangxiaomei02 on 2017/5/12.
 * 黑名单服务
 */
@Api(value = "blacklistService", description = "the BlackListService API")
@RequestMapping("/api")
public interface BlackListService {
    @ApiOperation(value = "查询黑名单", notes = "blacklist", response = BlackListResponse.class, tags = {"blacklistService"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "查询结果", response = BlackListResponse.class)})
    @RequestMapping(value = {"/blacklist"},
            produces = {"application/json"},
            method = RequestMethod.GET)
    BlackListResponse getBlackList(@RequestParam(value = "mobile",required = false) String mobile,
                                   @RequestParam(value = "beginTime",required = false) Long beginTime,
                                   @RequestParam(value = "endTime",required = false) Long endTime,
                                   @RequestParam(value = "createType",required = false) Integer createType,
                                   @RequestParam("pageNum")Integer pageNum,
                                   @RequestParam("pageSize")Integer pageSize);

    @ApiOperation(value = "添加黑名单", notes = "blacklist", response = CallerResponse.class, tags = {"blacklistService"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "保存结果", response = CallerResponse.class)})
    @RequestMapping(value = {"/blacklist"},
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    CallerResponse saveBlackList(@ApiParam(value = "request", required = true) @RequestBody BlackListRequest request);

    @ApiOperation(value = "批量添加黑名单", notes = "/blacklist/batch", response = BlackListResponse.class, tags = {"blacklistService"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "保存结果", response = BlackListResponse.class)})
    @RequestMapping(value = {"/blacklist/batch"},method = RequestMethod.POST)
    public CallerResponse handleFileUpload(@RequestParam("file") MultipartFile file);

    @ApiOperation(value = "编辑黑名单", notes = "blacklist/{listId}", response = BlackListResponse.class, tags = {"blacklistService"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "保存结果", response = BlackListResponse.class)})
    @PutMapping(value = {"/blacklist/{listId}"},
            produces = {"application/json"})
    CallerResponse editBlackList(@PathVariable("listId") Integer listId,@ApiParam(value = "request", required = true) @RequestBody BlackListRequest request);

    @ApiOperation(value = "删除黑名单", notes = "blacklist/{listId}", response = BlackListResponse.class, tags = {"blacklistService"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "保存结果", response = BlackListResponse.class)})
    @DeleteMapping(value = {"/blacklist/{listId}"},
            produces = {"application/json"})
    CallerResponse delBlackList(@PathVariable("listId") Integer listId);

    }
