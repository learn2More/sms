package com.ppdai.ac.sms.api.gateway.service;

import com.ppdai.ac.sms.api.gateway.controller.BlackListService;
import com.ppdai.ac.sms.api.gateway.dao.domain.BlackListBiz;
import com.ppdai.ac.sms.api.gateway.enums.InvokeResult;
import com.ppdai.ac.sms.api.gateway.request.ImportBlackListRequest;
import com.ppdai.ac.sms.api.gateway.response.ImportBlackListResponse;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by kiekiyang on 2017/5/18.
 */
@RestController
public class BlackListServiceImpl implements BlackListService {
    private static final Logger logger = LoggerFactory.getLogger(BlackListServiceImpl.class);

    @Autowired
    BlackListBiz blackListBiz;

    @Override
    public ImportBlackListResponse importBlackList(@ApiParam(value = "request", required = true) @RequestBody ImportBlackListRequest request) {
        ImportBlackListResponse response = new ImportBlackListResponse();
        if (request.getBlackLists().size() <= 0) {
            response.setResultCode(InvokeResult.PARAM_ERROR.getCode());
            response.setResultMessage(InvokeResult.PARAM_ERROR.getMessage());
            return response;
        }
        try {
            String[] features = new String[request.getBlackLists().size()];
            String[] remarks = new String[request.getBlackLists().size()];
            for (int i = 0; i < request.getBlackLists().size(); i++) {
                features[i] = request.getBlackLists().get(i).getFeatures();
                remarks[i] = request.getBlackLists().get(i).getRemark();
            }
            boolean execResult = blackListBiz.importBlackList(features, remarks);
            response.setResultCode(InvokeResult.SUCCESS.getCode());
            response.setResultMessage(InvokeResult.SUCCESS.getMessage());
            response.setResultObject(execResult);
        } catch (Exception ex) {
            logger.error("修改消息状态异常", ex);
            response.setResultCode(InvokeResult.SYS_ERROR.getCode());
            response.setResultMessage(InvokeResult.SYS_ERROR.getMessage());
        }
        return response;
    }
}
