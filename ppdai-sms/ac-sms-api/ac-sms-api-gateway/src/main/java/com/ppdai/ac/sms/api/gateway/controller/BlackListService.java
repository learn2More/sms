package com.ppdai.ac.sms.api.gateway.controller;

import com.ppdai.ac.sms.api.gateway.request.ImportBlackListRequest;
import com.ppdai.ac.sms.api.gateway.response.ImportBlackListResponse;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by kiekiyang on 2017/5/18.
 */
@Api(value = "BlackListService", description = "the BlackListService API")
@RequestMapping("/api/blacklist")
public interface BlackListService {
    @ApiOperation(value = "导入黑名单", notes = "importBlackList", response = ImportBlackListResponse.class, tags = {"BlackListService"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "校验验证码", response = ImportBlackListResponse.class)})
    @RequestMapping(value = {"/import"},
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ImportBlackListResponse importBlackList(@ApiParam(value = "request", required = true) @RequestBody ImportBlackListRequest request);
}
