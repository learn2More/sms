package com.ppdai.ac.sms.api.provider.techown.protocal;

import com.ppdai.ac.sms.api.provider.techown.protocal.fallback.TechownServiceFallback;
import com.ppdai.ac.sms.api.provider.techown.protocal.response.TcGetReportResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *techownService
 *author cash
 *create 2017/7/11-11:24
**/


@FeignClient(name="${spring.application.name}",url ="${techown.provider.url}",fallback = TechownServiceFallback.class)
public interface TechownService {

    @RequestMapping(value="/mt",method= RequestMethod.GET)
    String send(@RequestParam MultiValueMap<String, Object> reportRequest);


    @RequestMapping(value="/mo",method= RequestMethod.GET)
    String getReport(@RequestParam MultiValueMap<String, Object> reportRequest);

}
