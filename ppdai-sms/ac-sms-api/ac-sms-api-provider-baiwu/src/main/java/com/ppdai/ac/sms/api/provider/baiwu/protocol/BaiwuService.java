package com.ppdai.ac.sms.api.provider.baiwu.protocol;

import com.ppdai.ac.sms.api.provider.baiwu.protocol.response.Delivers;
import com.ppdai.ac.sms.api.provider.baiwu.protocol.response.JDelivers;
import com.ppdai.ac.sms.api.provider.baiwu.protocol.response.JReports;
import com.ppdai.ac.sms.api.provider.baiwu.protocol.response.Reports;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by cash on 2017/4/26.
 */

@FeignClient(name="${spring.application.name}",url ="${provider.baiwu.url}")
public interface BaiwuService {


    @RequestMapping(value="/sms_send2.do",method= RequestMethod.POST,
            headers = {"Content-Type=application/x-www-form-urlencoded;charset=gbk"})
    String send(MultiValueMap<String, String> sendRequest);


    @RequestMapping(value="/post_report.do",method= RequestMethod.POST,
            headers = {"Content-Type=application/x-www-form-urlencoded"})
    Reports  getReports(MultiValueMap<String, String> reportRequest);


    @RequestMapping(value="/post_deliverMsg.do",method= RequestMethod.POST,
            headers = {"Content-Type=application/x-www-form-urlencoded"})
    Delivers getDelivers(MultiValueMap<String, String> deliverRequest);


    @RequestMapping(value="/post_report.do",method= RequestMethod.POST,
            headers = {"Content-Type=application/x-www-form-urlencoded"})
    @ResponseBody  Reports  getReports(@RequestParam("timestamp") String timestamp,
                        @RequestParam("sign") String sign,
                        MultiValueMap<String, String> reportRequest);


    @RequestMapping(value="/post_deliverMsg.do",method= RequestMethod.POST,
            headers = {"Content-Type=application/x-www-form-urlencoded"})
    @ResponseBody Delivers getDelivers(@RequestParam("timestamp") String timestamp,
                         @RequestParam("sign") String sign,
                         MultiValueMap<String, String> deliverRequest);


    @RequestMapping(value="/json/sms-report",method= RequestMethod.POST,
            headers = {"Content-Type=application/x-www-form-urlencoded"})
    @ResponseBody
    JReports getJsonReports(MultiValueMap<String, String> reportRequest);


    @RequestMapping(value="/json/sms-deliver",method= RequestMethod.POST,
            headers = {"Content-Type=application/x-www-form-urlencoded"})
    @ResponseBody
    JDelivers getJsonDelivers(MultiValueMap<String, String> deliverRequest);


}
