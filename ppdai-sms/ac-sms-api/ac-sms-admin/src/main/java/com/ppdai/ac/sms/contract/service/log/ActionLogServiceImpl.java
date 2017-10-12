package com.ppdai.ac.sms.contract.service.log;

import com.ppdai.ac.sms.contract.controller.log.ActionLogService;
import com.ppdai.ac.sms.contract.dao.mapper.smsbase.ActionLogMapper;
import com.ppdai.ac.sms.contract.enums.InvokeResult;
import com.ppdai.ac.sms.contract.model.entity.ActionLogDTO;
import com.ppdai.ac.sms.contract.response.BlackListResponse;
import com.ppdai.ac.sms.contract.utils.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by wangxiaomei02 on 2017/6/27.
 */
@RestController
public class ActionLogServiceImpl implements ActionLogService {

    private static final Logger logger = LoggerFactory.getLogger(ActionLogServiceImpl.class);

    @Autowired
    ActionLogMapper actionLogMapper;

    @Override
    public BlackListResponse getBlackList(@RequestParam(value = "userId", required = false) Integer userId,
                                          @RequestParam("beginTime") long beginTime,
                                          @RequestParam("endTime") long endTime,
                                          @RequestParam("pageNum") Integer pageNum,
                                          @RequestParam("pageSize") Integer pageSize) {
        logger.info("查询操作日志,入参：mobile=" + userId + ",beginTime=" + beginTime+ ",endTime=" + endTime+ ",pageNum=" + pageNum+ ",pageSize=" + pageSize);

        BlackListResponse response=new BlackListResponse();
        Page page=new Page(pageNum,pageSize);

        try {
            List<ActionLogDTO> list = actionLogMapper.getActionLogList(userId,new Timestamp(beginTime),new Timestamp(endTime),page);
            if (list != null) {

                response.setCode(InvokeResult.SELECT_SUCCESS.getCode());
                response.setMessage(InvokeResult.SELECT_SUCCESS.getMessage());
                response.setResult(list);
                int total = actionLogMapper.getActionLogCount(userId,new Timestamp(beginTime),new Timestamp(endTime));
                response.setTotal(total);
            } else {
                response.setCode(InvokeResult.BUSINESS_ERROR.getCode());
                response.setMessage(InvokeResult.BUSINESS_ERROR.getMessage());
                response.setResult("没有符合条件的数据");
            }
        } catch (Exception e) {
            logger.error("查询操作日志异常，返回：", e);
            response.setCode(InvokeResult.SYS_ERROR.getCode());
            response.setMessage(InvokeResult.SYS_ERROR.getMessage());
        }
        return response;
    }
}
