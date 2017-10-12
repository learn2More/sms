package com.ppdai.ac.sms.api.provider.techown.service;

import com.alibaba.fastjson.JSONObject;
import com.ppdai.ac.sms.api.provider.techown.domain.biz.TechownProviderBiz;
import com.ppdai.ac.sms.provider.core.api.SmsProviderService;
import com.ppdai.ac.sms.provider.core.request.SmsRequest;
import com.ppdai.ac.sms.provider.core.response.SmsResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *天畅短信服务
 *author cash
 *create 2017/7/11-11:31
**/


@Api(value = "TechownProviderService", description = "the TechownProviderService")
@Controller
@RequestMapping(value = "/TechownProviderService")
public class TechownProviderServiceImpl implements SmsProviderService {


    @Autowired
    TechownProviderBiz techownProviderBiz;

    private static final Logger logger= LoggerFactory.getLogger(TechownProviderServiceImpl.class);


    @ApiOperation(value = "天畅短信发送", notes = "TechownProviderService.send", response = SmsResponse.class, tags = {"TechownProviderService.send"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "天畅短信发送", response = SmsResponse.class)})
    @RequestMapping(value = "/send",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    SmsResponse send(@RequestBody SmsRequest request) {

        logger.info("天畅短信发送,入参："+ JSONObject.toJSONString(request));
        SmsResponse sendResponse=techownProviderBiz.send(request);

        return sendResponse;
    }
}
