package com.ppdai.ac.sms.contract.service.caller;

import com.alibaba.fastjson.JSONObject;
import com.ppdai.ac.sms.contract.controller.caller.CallerService;
import com.ppdai.ac.sms.contract.dao.mapper.smsbase.CallerMapper;
import com.ppdai.ac.sms.contract.dao.mapper.smsbase.TemplateWorkFlowMapper;
import com.ppdai.ac.sms.contract.enums.InvokeResult;
import com.ppdai.ac.sms.contract.model.vo.CallerVo;
import com.ppdai.ac.sms.contract.request.caller.CallerRequest;
import com.ppdai.ac.sms.contract.response.CallerResponse;
import com.ppdai.ac.sms.contract.utils.Log;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangxiaomei02 on 2017/5/12.
 */
@RestController
public class CallerServiceImpl  implements CallerService{

    private static final Logger logger = LoggerFactory.getLogger(CallerServiceImpl.class);

    @Autowired
    CallerMapper callerMapper;

    @Autowired
    TemplateWorkFlowMapper templateWorkFlowMapper;

    @Override
    @Log(operationType="查询操作",operationName="查询技术接入方")
    public CallerResponse getCallerList() {
        CallerResponse response=new CallerResponse();
        try {
            List<CallerVo> list= callerMapper.getCallerList();
            if(list != null){
                response.setCode(InvokeResult.SELECT_SUCCESS.getCode());
                response.setMessage(InvokeResult.SELECT_SUCCESS.getMessage());
                response.setResult(list);
            }else{
                response.setCode(InvokeResult.SELECT_SUCCESS.getCode());
                response.setMessage(InvokeResult.SELECT_SUCCESS.getMessage());
                response.setResult(new ArrayList<>());
            }
        } catch (Exception e) {
            logger.error("查询技术接入方异常，返回：", e);
            response.setCode(InvokeResult.SYS_ERROR.getCode());
            response.setMessage(InvokeResult.SYS_ERROR.getMessage());
        }
        return response;
    }

    @Override
    @Log(operationType="添加操作",operationName="添加技术接入方")
    public CallerResponse saveCaller(@ApiParam(value = "request", required = true) @RequestBody CallerRequest request) {
        CallerResponse response=new CallerResponse();

        logger.info("添加技术接入方,入参："+ JSONObject.toJSONString(request));
        if (StringUtils.isBlank(request.getCallerName()) || StringUtils.isBlank(request.getIpList()) ) {
            response.setCode(InvokeResult.PARAM_ERROR.getCode());
            response.setMessage(InvokeResult.PARAM_ERROR.getMessage());
            return response;
        }
        CallerVo vo=new CallerVo();
        vo.setCallerName(request.getCallerName());
        vo.setIpList(request.getIpList());

        try {
            int saveResult=  callerMapper.saveCaller(vo);
            if(saveResult >0){
                vo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                response.setCode(InvokeResult.SUCCESS.getCode());
                response.setMessage(InvokeResult.SUCCESS.getMessage());
                response.setResult(vo);
            }else{
                response.setCode(InvokeResult.SYS_ERROR.getCode());
                response.setMessage(InvokeResult.SYS_ERROR.getMessage());
                response.setResult("保存失败");
            }
        } catch (Exception e) {
            logger.error("添加技术接入方异常，返回：", e);
            response.setCode(InvokeResult.SYS_ERROR.getCode());
            response.setMessage(InvokeResult.SYS_ERROR.getMessage());
        }
        return response;
    }

    @Override
    @Log(operationType="修改操作",operationName="修改技术接入方")
    public CallerResponse editCaller(@PathVariable("callerId") Integer callerId, @ApiParam(value = "request", required = true) @RequestBody CallerRequest request) {
        CallerResponse response=new CallerResponse();
        logger.info("修改技术接入方,入参："+ JSONObject.toJSONString(request));
        if (StringUtils.isBlank(request.getCallerName()) || StringUtils.isBlank(request.getIpList()) || callerId ==null ) {
            response.setCode(InvokeResult.PARAM_ERROR.getCode());
            response.setMessage(InvokeResult.PARAM_ERROR.getMessage());
            return response;
        }

        try {
            int saveResult= callerMapper.editCaller(request.getCallerName(),request.getIpList(),callerId);
            if(saveResult >0){
                response.setCode(InvokeResult.SUCCESS.getCode());
                response.setMessage(InvokeResult.SUCCESS.getMessage());
                response.setResult("保存成功");
            }else{
                response.setCode(InvokeResult.SYS_ERROR.getCode());
                response.setMessage(InvokeResult.SYS_ERROR.getMessage());
                response.setResult("保存失败");
            }
        } catch (Exception e) {
            logger.error("修改技术接入方异常，返回：", e);
            response.setCode(InvokeResult.SYS_ERROR.getCode());
            response.setMessage(InvokeResult.SYS_ERROR.getMessage());

        }
        return response;
    }

    @Override
    @Log(operationType="查询操作",operationName="查询单条技术接入方")
    public CallerResponse getCallerById(@RequestParam("callerId") Integer callerId) {
        CallerResponse response=new CallerResponse();
        try {
            CallerVo vo=callerMapper.findCallerById(callerId);
            if(vo != null){
                response.setCode(InvokeResult.SELECT_SUCCESS.getCode());
                response.setMessage(InvokeResult.SELECT_SUCCESS.getMessage());
                response.setResult(vo);
            }
            else{
                response.setCode(InvokeResult.SELECT_SUCCESS.getCode());
                response.setMessage(InvokeResult.SELECT_SUCCESS.getMessage());
                response.setResult(null);
            }
        } catch (Exception e) {
            logger.error("查询技术接入方异常，返回：", e);
            response.setCode(InvokeResult.SYS_ERROR.getCode());
            response.setMessage(InvokeResult.SYS_ERROR.getMessage());
        }
        return response;
    }

    @Override
    @Log(operationType="删除操作",operationName="删除技术接入方")
    public CallerResponse delCallerById(@PathVariable("callerId") Integer callerId) {
        CallerResponse response=new CallerResponse();
        logger.info("删除技术接入方,入参 callerId=："+ callerId);
        if(callerId ==null){
            response.setCode(InvokeResult.PARAM_ERROR.getCode());
            response.setMessage(InvokeResult.PARAM_ERROR.getMessage());
            return response;
        }
        try {
            /**
             * 查询模板申请中是否含有技术接入方，没有则删除
             */
           int selectResult= templateWorkFlowMapper.getTemplateByparam(callerId,null);
            if(selectResult == 0){
                //删除技术接入方
                int delResult=callerMapper.delCaller(callerId);
                if(delResult >0){
                    response.setCode(InvokeResult.SUCCESS.getCode());
                    response.setMessage(InvokeResult.SUCCESS.getMessage());
                    response.setResult("删除成功");
                }else{
                    response.setCode(InvokeResult.SYS_ERROR.getCode());
                    response.setMessage(InvokeResult.SYS_ERROR.getMessage());
                    response.setResult("删除失败");
                }
            }else{
                response.setCode(InvokeResult.SUCCESS.getCode());
                response.setMessage(InvokeResult.SUCCESS.getMessage());
                response.setResult("该技术接入方不可删除");
            }

        } catch (Exception e) {
            logger.error("删除技术接入方异常，返回：", e);
            response.setCode(InvokeResult.SYS_ERROR.getCode());
            response.setMessage(InvokeResult.SYS_ERROR.getMessage());
        }
        return response;
    }
}
