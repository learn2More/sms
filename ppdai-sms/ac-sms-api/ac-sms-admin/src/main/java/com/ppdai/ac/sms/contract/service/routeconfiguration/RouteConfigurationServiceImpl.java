package com.ppdai.ac.sms.contract.service.routeconfiguration;

import com.alibaba.fastjson.JSONObject;
import com.ppdai.ac.sms.contract.controller.routeconfiguration.RouteConfigurationService;
import com.ppdai.ac.sms.contract.dao.mapper.smsbase.BusinessProviderMapper;
import com.ppdai.ac.sms.contract.enums.InvokeResult;
import com.ppdai.ac.sms.contract.model.entity.BusinessProviderDTO;
import com.ppdai.ac.sms.contract.request.routeconfiguration.RouteConfigurationRequest;
import com.ppdai.ac.sms.contract.response.RouteConfigurationResponse;
import com.ppdai.ac.sms.contract.utils.Log;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by wangxiaomei02 on 2017/5/19.
 */
@RestController
public class RouteConfigurationServiceImpl implements RouteConfigurationService {

    private static final Logger logger = LoggerFactory.getLogger(RouteConfigurationServiceImpl.class);

    @Autowired
    BusinessProviderMapper businessProviderMapper;

    @Override
    public RouteConfigurationResponse getRouteConfiguration(@RequestParam(value = "businessId",required = false)Integer businessId) {
        logger.info("查询通道,入参：businessId"+ businessId);
        RouteConfigurationResponse response=new RouteConfigurationResponse();
        try {
            List<BusinessProviderDTO> list=businessProviderMapper.getBusinessProviderBybusinessId(businessId);
            if (list !=null ){
                response.setCode(InvokeResult.SELECT_SUCCESS.getCode());
                response.setMessage(InvokeResult.SELECT_SUCCESS.getMessage());
                response.setResult(list);

            }
            else{
                response.setCode(InvokeResult.BUSINESS_ERROR.getCode());
                response.setMessage(InvokeResult.BUSINESS_ERROR.getMessage());
                response.setResult("没有符合条件的数据");
            }
        } catch (Exception e) {
            logger.error("查询通道异常，返回：", e);
            response.setCode(InvokeResult.SYS_ERROR.getCode());
            response.setMessage(InvokeResult.SYS_ERROR.getMessage());
        }
        return response;
    }

    @Override
    @Log(operationType="添加操作",operationName="添加通道")
    public RouteConfigurationResponse saveRouteConfiguration(@ApiParam(value = "request", required = true) @RequestBody RouteConfigurationRequest request) {
        logger.info("添加通道,入参："+ JSONObject.toJSONString(request));

        RouteConfigurationResponse response=new RouteConfigurationResponse();
        if (request.getBusinessId()==null || request.getMessageKind() == null || request.getProviderId() ==null || request.getWeight()==null ||request.getLine()==null){
            response.setCode(InvokeResult.PARAM_ERROR.getCode());
            response.setMessage(InvokeResult.PARAM_ERROR.getMessage());
            return response;
        }

        try {
            BusinessProviderDTO dto=new BusinessProviderDTO();
            dto.setBusinessId(request.getBusinessId());
            dto.setMessageKind(request.getMessageKind());
            dto.setLine(request.getLine());
            dto.setProviderId(request.getProviderId());
            dto.setWeight(request.getWeight());

            int saveResult= businessProviderMapper.saveBusinessProvider(dto);
            if(saveResult !=0){
                dto.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                response.setCode(InvokeResult.SUCCESS.getCode());
                response.setMessage(InvokeResult.SUCCESS.getMessage());
                response.setResult(dto);

            }else{
                response.setCode(InvokeResult.SYS_ERROR.getCode());
                response.setMessage(InvokeResult.SYS_ERROR.getMessage());
                response.setResult("保存通道失败");
            }

        } catch (Exception e) {
            logger.error("保存通道异常，返回：", e);
            response.setCode(InvokeResult.SYS_ERROR.getCode());
            response.setMessage(InvokeResult.SYS_ERROR.getMessage());
        }
        return response;
    }

    @Override
    @Log(operationType="修改操作",operationName="修改通道")
    public RouteConfigurationResponse editRouteConfiguration(@PathVariable(value = "channelId", required = true)Integer channelId, @ApiParam(value = "request", required = true) @RequestBody RouteConfigurationRequest request) {
        logger.info("编辑通道,入参："+ JSONObject.toJSONString(request)+",channelId="+channelId);

        RouteConfigurationResponse response=new RouteConfigurationResponse();
        if (channelId == null ||request.getBusinessId()==null || request.getMessageKind() == null || request.getProviderId() ==null || request.getWeight()==null ||request.getLine()==null){
            response.setCode(InvokeResult.PARAM_ERROR.getCode());
            response.setMessage(InvokeResult.PARAM_ERROR.getMessage());
            return response;
        }

        try {
            BusinessProviderDTO dto=new BusinessProviderDTO();
            dto.setBPId(channelId);
            dto.setBusinessId(request.getBusinessId());
            dto.setMessageKind(request.getMessageKind());
            dto.setLine(request.getLine());
            dto.setProviderId(request.getProviderId());
            dto.setWeight(request.getWeight());

            int editResult= businessProviderMapper.editBusinessProvider(dto);
            if(editResult !=0){
                response.setCode(InvokeResult.SUCCESS.getCode());
                response.setMessage(InvokeResult.SUCCESS.getMessage());
                response.setResult("修改通道成功");

            }else{
                response.setCode(InvokeResult.SYS_ERROR.getCode());
                response.setMessage(InvokeResult.SYS_ERROR.getMessage());
                response.setResult("修改通道失败");
            }

        } catch (Exception e) {
            logger.error("编辑通道异常，返回：", e);
            response.setCode(InvokeResult.SYS_ERROR.getCode());
            response.setMessage(InvokeResult.SYS_ERROR.getMessage());
        }
        return response;
    }

    @Override
    @Log(operationType="删除操作",operationName="删除通道")
    public RouteConfigurationResponse delRouteConfiguration(@PathVariable(value = "channelId", required = true) Integer channelId) {
        logger.info("删除通道,入参：channelId="+channelId);

        RouteConfigurationResponse response=new RouteConfigurationResponse();
        if (channelId == null ){
            response.setCode(InvokeResult.PARAM_ERROR.getCode());
            response.setMessage(InvokeResult.PARAM_ERROR.getMessage());
            return response;
        }

        try {
            int delResult= businessProviderMapper.delBusinessProvider(channelId);
            if(delResult !=0){
                response.setCode(InvokeResult.SUCCESS.getCode());
                response.setMessage(InvokeResult.SUCCESS.getMessage());
                response.setResult("删除通道成功");

            }else{
                response.setCode(InvokeResult.SYS_ERROR.getCode());
                response.setMessage(InvokeResult.SYS_ERROR.getMessage());
                response.setResult("删除通道失败");
            }

        } catch (Exception e) {
            logger.error("删除通道异常，返回：", e);
            response.setCode(InvokeResult.SYS_ERROR.getCode());
            response.setMessage(InvokeResult.SYS_ERROR.getMessage());
        }
        return response;
    }

}
