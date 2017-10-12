package com.ppdai.ac.sms.api.provider.techown.protocal.fallback;

import com.ppdai.ac.sms.api.provider.techown.protocal.TechownService;
import com.ppdai.ac.sms.api.provider.techown.protocal.response.TcGetReportResponse;
import com.ppdai.ac.sms.api.provider.techown.protocal.response.TcSendResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *techownService  fallback
 *author cash
 *create 2017/7/11-11:24
**/

@Component
public class TechownServiceFallback implements TechownService {


    @Override
    public String send(@RequestParam MultiValueMap<String, Object> reportRequest) {
        return null;
    }

    @Override
    public String getReport(@RequestParam MultiValueMap<String, Object> reportRequest) {
        return null;
    }


}
