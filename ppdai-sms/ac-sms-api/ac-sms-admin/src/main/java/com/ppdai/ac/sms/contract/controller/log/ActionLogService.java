package com.ppdai.ac.sms.contract.controller.log;

import com.ppdai.ac.sms.contract.response.BlackListResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by wangxiaomei02 on 2017/6/27.
 */
@Api(value = "actionLogService", description = "the ActionLogService API")
@RequestMapping("/api")
public interface ActionLogService {

    @ApiOperation(value = "查询操作日志", notes = "logs", response = BlackListResponse.class, tags = {"actionLogService"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "查询结果", response = BlackListResponse.class)})
    @RequestMapping(value = {"/logs"},
            produces = {"application/json"},
            method = RequestMethod.GET)
    BlackListResponse getBlackList(@RequestParam(value = "userId",required = false) Integer userId,
                                   @RequestParam("beginTime") long beginTime,
                                   @RequestParam("endTime") long endTime,
                                   @RequestParam("pageNum")Integer pageNum,
                                   @RequestParam("pageSize")Integer pageSize);
}
