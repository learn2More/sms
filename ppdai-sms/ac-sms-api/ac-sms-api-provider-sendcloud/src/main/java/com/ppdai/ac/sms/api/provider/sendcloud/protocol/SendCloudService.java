package com.ppdai.ac.sms.api.provider.sendcloud.protocol;

import com.ppdai.ac.sms.api.provider.sendcloud.protocol.response.ReportResponseData;
import com.ppdai.ac.sms.api.provider.sendcloud.protocol.response.SendResponseData;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by cash on 2017/4/26.
 */

@FeignClient(name="${spring.application.name}",url ="${provider.sendcloud.url}")
public interface SendCloudService {


    @RequestMapping(value="/apiv2/mail/send",method= RequestMethod.POST,
            headers = {"Content-Type=application/x-www-form-urlencoded;charset=UTF-8"})
    SendResponseData send(MultiValueMap<String, String> sendRequest);


    @RequestMapping(value="/apiv2/data/emailStatus",method= RequestMethod.POST,
            headers = {"Content-Type=application/x-www-form-urlencoded;charset=UTF-8"})
    ReportResponseData getReports(MultiValueMap<String, String> reportRequest);

}
