package com.ppdai.ac.sms.api.provider.techown.service;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *健康检查
 *author cash
 *create 2017/7/11-11:30
**/


@RestController
@Api(value = "HsController", description = "the hs api")
@RequestMapping(value = "/")
public class HsController {

    @ApiOperation(value = "健康检查", notes = "Hs", tags = {"Hs"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "健康检查")})
    @RequestMapping(value = "/hs")
    public String Hs(){
        return "OK";
    }
}
