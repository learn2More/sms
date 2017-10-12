package com.ppdai.ac.sms.api.gateway.service;

import com.ppdai.ac.sms.api.gateway.controller.MessageService;
import com.ppdai.ac.sms.api.gateway.dao.domain.MessageBiz;
import com.ppdai.ac.sms.api.gateway.enums.InvokeResult;
import com.ppdai.ac.sms.api.gateway.model.bo.BizResult;
import com.ppdai.ac.sms.api.gateway.request.ChangeMessageStatusRequest;
import com.ppdai.ac.sms.api.gateway.request.QueryMessageByIdRequest;
import com.ppdai.ac.sms.api.gateway.response.ChangeMessageStatusResponse;
import com.ppdai.ac.sms.api.gateway.response.QueryMessageByIdResponse;
import com.ppdai.ac.sms.api.gateway.response.SMSMessage;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by kiekiyang on 2017/5/18.
 */
@RestController
public class MessageServiceImpl implements MessageService {
    private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);
    @Autowired
    MessageBiz messageBiz;

    /**
     * 修改消息状态（发送成功/发送失败）
     *
     * @param request
     * @return
     */
    @Override
    public ChangeMessageStatusResponse changeMessageStatus(@ApiParam(value = "request", required = true) @RequestBody ChangeMessageStatusRequest request) {
        ChangeMessageStatusResponse response = new ChangeMessageStatusResponse();
        try {
            BizResult bizResult = messageBiz.changeStatusByMessageId(request.getMessageId(),
                    request.getMessageStatus().getCode(),
                    request.getStartTime(),
                    request.getEndTime());
            if (bizResult.getResultCode() == InvokeResult.SUCCESS.getCode()) {
                response.setResultCode(InvokeResult.SUCCESS.getCode());
                response.setResultMessage(InvokeResult.SUCCESS.getMessage());
                response.setResultObject((int) bizResult.getResultObject());

            } else {
                response.setResultCode(bizResult.getResultCode());
                response.setResultMessage(bizResult.getResultMessage());
            }
        } catch (Exception ex) {
            logger.error("修改消息状态异常", ex);
            response.setResultCode(InvokeResult.SYS_ERROR.getCode());
            response.setResultMessage(InvokeResult.SYS_ERROR.getMessage());
        }
        return response;
    }

    /**
     * 根据消息编号查询消息实体
     *
     * @param request
     * @return
     */
    @Override
    public QueryMessageByIdResponse queryMessageById(@ApiParam(value = "request", required = true) @RequestBody QueryMessageByIdRequest request) {
        QueryMessageByIdResponse response = new QueryMessageByIdResponse();
        try {
            BizResult bizResult = messageBiz.queryMessageById(request.getMessageId(),
                    request.getStartTime(),
                    request.getEndTime());
            SMSMessage message = new SMSMessage();
            if (bizResult.getResultCode() == InvokeResult.SUCCESS.getCode()) {
                response.setResultCode(InvokeResult.SUCCESS.getCode());
                response.setResultMessage(InvokeResult.SUCCESS.getMessage());

                if (bizResult.getResultObject() != null) {
                    BeanUtils.copyProperties(bizResult.getResultObject(), message);
                    response.setResultObject(message);
                }

            } else {
                response.setResultCode(bizResult.getResultCode());
                response.setResultMessage(bizResult.getResultMessage());
//                response.setResultObject(message);
            }
        } catch (Exception ex) {
            logger.error("查询消息信息异常", ex);
            response.setResultCode(InvokeResult.SYS_ERROR.getCode());
            response.setResultMessage(InvokeResult.SYS_ERROR.getMessage());
        }
        return response;
    }
}
