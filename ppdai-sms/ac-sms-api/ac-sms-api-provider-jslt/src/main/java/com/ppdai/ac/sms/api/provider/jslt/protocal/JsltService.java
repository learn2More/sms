package com.ppdai.ac.sms.api.provider.jslt.protocal;

import com.ppdai.ac.sms.api.provider.jslt.protocal.response.Delivers;
import com.ppdai.ac.sms.api.provider.jslt.protocal.response.Reports;
import com.ppdai.ac.sms.api.provider.jslt.protocal.response.SendDetail;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * author cash
 * create 2017-06-30-15:25
 **/

@FeignClient(name="${spring.application.name}",url ="${provider.jslt.url}")
public interface JsltService {

    @RequestMapping(value="/v2sms.aspx",method= RequestMethod.POST,
            headers = {"Content-Type=application/x-www-form-urlencoded;charset=gbk"})
    SendDetail send(MultiValueMap<String, String> sendRequest);


    @RequestMapping(value="/v2callApi.aspx",method= RequestMethod.POST,
            headers = {"Content-Type=application/x-www-form-urlencoded"})
    Delivers getDelivers(MultiValueMap<String, String> deliverRequest);


    @RequestMapping(value="/v2statusApi.aspx",method= RequestMethod.POST,
            headers = {"Content-Type=application/x-www-form-urlencoded"})
    @ResponseBody
    Reports  getReports(MultiValueMap<String, String> reportRequest);


}
