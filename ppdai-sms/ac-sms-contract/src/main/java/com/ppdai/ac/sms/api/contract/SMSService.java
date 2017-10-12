package com.ppdai.ac.sms.api.contract;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by kiekiyang on 2017/4/10.
 */
@Api(value = "MailApiService", description = "提供短信服务的Api客户端")
@RequestMapping(value = "/Api")
public interface SMSService {

}
