package com.ppdai.ac.sms.contract.service.provider;


import com.alibaba.fastjson.JSONObject;
import com.ppdai.ac.sms.common.redis.RedisUtil;
import com.ppdai.ac.sms.contract.controller.provider.ServiceProviderService;
import com.ppdai.ac.sms.contract.dao.mapper.smsbase.ProviderConfigMapper;
import com.ppdai.ac.sms.contract.dao.mapper.smsbase.ProviderMapper;
import com.ppdai.ac.sms.contract.enums.InvokeResult;
import com.ppdai.ac.sms.contract.model.entity.ProviderConfigDTO;
import com.ppdai.ac.sms.contract.model.vo.ProviderConfigVo;
import com.ppdai.ac.sms.contract.model.vo.ProviderVo;
import com.ppdai.ac.sms.contract.request.provider.ProviderConfigRequest;
import com.ppdai.ac.sms.contract.request.provider.ProviderInfoRequest;
import com.ppdai.ac.sms.contract.request.provider.ServiceProviderRequest;
import com.ppdai.ac.sms.contract.response.ProviderConfigurationResponse;
import com.ppdai.ac.sms.contract.response.ServiceProviderResponse;
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
public class ServiceProviderServiceImpl implements ServiceProviderService {

    private static final Logger logger = LoggerFactory.getLogger(ServiceProviderServiceImpl.class);

    @Autowired
    ProviderMapper providerMapper;

    @Autowired
    ProviderConfigMapper providerConfigMapper;

    @Autowired
    RedisUtil<Integer,String> redisUtil_provider;

    @Override
    public ServiceProviderResponse getProvider(@RequestParam(value = "businessType",required = false) Integer businessType,
                                                   @RequestParam(value = "messageType",required = false) Integer messageType) {

        ServiceProviderResponse response=new ServiceProviderResponse();
        logger.info("查询服务商,入参：businessType="+ businessType+",messageType="+messageType);

        try {
            List<ProviderVo> list=providerMapper.getProviders(businessType,messageType);
            if (list !=null && !list.isEmpty()){

                for(ProviderVo providerVo:list){
                    List<ProviderConfigVo> configList=providerConfigMapper.getConfigByProviderId(providerVo.getProviderId());
                    providerVo.setConfigList(configList);
                }
                response.setCode(InvokeResult.SELECT_SUCCESS.getCode());
                response.setMessage(InvokeResult.SELECT_SUCCESS.getMessage());
                response.setResult(list);
                int total=providerMapper.getProviderCount(businessType,messageType);
                response.setTotal(total);
            }
            else{
                response.setCode(InvokeResult.BUSINESS_ERROR.getCode());
                response.setMessage(InvokeResult.BUSINESS_ERROR.getMessage());
                response.setResult("没有符合条件的数据");
            }
        } catch (Exception e) {
            logger.error("查询服务商异常，返回：", e);
            response.setCode(InvokeResult.SYS_ERROR.getCode());
            response.setMessage(InvokeResult.SYS_ERROR.getMessage());
        }
        return response;
    }

    @Override
    @Log(operationType="添加操作",operationName="添加单个服务商配置")
    public ProviderConfigurationResponse saveProvider(@ApiParam(value = "request", required = true) @RequestBody ServiceProviderRequest request) {
        logger.info("添加单个服务商配置,入参："+ JSONObject.toJSONString(request));

        ProviderConfigurationResponse response=new ProviderConfigurationResponse();
        if (request.getProviderName()==null || request.getMessageKind() == null || request.getMessageType()==null ){
            response.setCode(InvokeResult.PARAM_ERROR.getCode());
            response.setMessage(InvokeResult.PARAM_ERROR.getMessage());
            return response;
        }

        ProviderVo providerVo=new ProviderVo();
        providerVo.setProviderName(request.getProviderName());
        providerVo.setMessageType(request.getMessageType());
        providerVo.setMessageKind(request.getMessageKind());
        providerVo.setProviderProtocol(request.getProviderProtocol());
        providerVo.setInsertTime(new Timestamp(System.currentTimeMillis()));
        try {
            int saveResult=  providerMapper.saveProvider(providerVo);
            if(saveResult > 0){
                Integer id=providerVo.getProviderId();
                providerVo.setProviderId(id);
                providerVo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                /**
                 * 添加成功后继续添加服务商配置 key,value;
                 */
                if(id > 0){
                    List<ProviderConfigRequest> configList=request.getConfigList();
                    List<ProviderConfigDTO> configs=new ArrayList<>();
                    if(configList !=null && !configList.isEmpty()) {
                        for(ProviderConfigRequest configVo:configList){
                            ProviderConfigDTO dto=new ProviderConfigDTO();
                            dto.setConfigKey(configVo.getConfigKey());
                            dto.setConfigValue(configVo.getConfigValue());
                            dto.setProviderId(id);
                            configs.add(dto);
                        }
                        int saveConfigResult=providerConfigMapper.batchSaveProviderConfig(configs);
                            if(saveConfigResult == 0){
                                response.setCode(InvokeResult.SYS_ERROR.getCode());
                                response.setMessage(InvokeResult.SYS_ERROR.getMessage());
                                response.setResult("服务商配置保存失败!");
                            }

                    }
                }
                List<ProviderConfigVo> configList=providerConfigMapper.getConfigByProviderId(id);
                providerVo.setConfigList(configList);
                response.setCode(InvokeResult.SUCCESS.getCode());
                response.setMessage(InvokeResult.SUCCESS.getMessage());
                response.setResult(providerVo);

            }else{
                response.setCode(InvokeResult.SYS_ERROR.getCode());
                response.setMessage(InvokeResult.SYS_ERROR.getMessage());
                response.setResult("保存失败");
            }
        } catch (Exception e) {
            logger.error("添加服务商异常，返回：", e);
            response.setCode(InvokeResult.SYS_ERROR.getCode());
            response.setMessage(InvokeResult.SYS_ERROR.getMessage());
        }
        return response;
    }

    @Override
    @Log(operationType="添加操作",operationName="添加服务商配置信息")
    public ProviderConfigurationResponse saveProviderConfig(@PathVariable("providerId") Integer providerId,@ApiParam(value = "request", required = true) @RequestBody ProviderConfigRequest request) {
        logger.info("添加单条服务商配置,入参："+ JSONObject.toJSONString(request)+",providerId="+providerId);

        ProviderConfigurationResponse response=new ProviderConfigurationResponse();
        if (providerId ==null || request.getConfigKey() == null ||request.getConfigValue() ==null ){
            response.setCode(InvokeResult.PARAM_ERROR.getCode());
            response.setMessage(InvokeResult.PARAM_ERROR.getMessage());
            return response;
        }

        try {
            ProviderConfigDTO pcVo = new ProviderConfigDTO();
            pcVo.setProviderId(providerId);
            pcVo.setConfigKey(request.getConfigKey());
            pcVo.setConfigValue(request.getConfigValue());

            int saveConfigResult=providerConfigMapper.saveProviderConfig(pcVo);
            if(saveConfigResult == 0) {
                response.setCode(InvokeResult.SYS_ERROR.getCode());
                response.setMessage(InvokeResult.SYS_ERROR.getMessage());
                response.setResult("添加失败");
            }else{
                ProviderConfigVo vo=new ProviderConfigVo();
                vo.setConfigId(pcVo.getProviderId());
                vo.setProviderId(providerId);
                vo.setConfigKey(pcVo.getConfigKey());
                vo.setConfigValue(pcVo.getConfigValue());
                vo.setUpdateTime(new Timestamp(System.currentTimeMillis()));

                response.setCode(InvokeResult.SUCCESS.getCode());
                response.setMessage(InvokeResult.SUCCESS.getMessage());
                response.setResult(vo);
            }
        } catch (Exception e) {
            logger.error("添加服务商配置异常，返回：", e);
            response.setCode(InvokeResult.SYS_ERROR.getCode());
            response.setMessage(InvokeResult.SYS_ERROR.getMessage());
        }
        return response;
    }

    @Override
    @Log(operationType="修改操作",operationName="修改服务商")
    public ServiceProviderResponse editProvider(@PathVariable("providerId") Integer providerId,@ApiParam(value = "request", required = true) @RequestBody ProviderInfoRequest request) {
        logger.info("修改服务商,入参："+ JSONObject.toJSONString(request));

        ServiceProviderResponse response=new ServiceProviderResponse();
        if (StringUtils.isBlank(request.getProviderName()) || request.getMessageKind() == null ||request.getMessageType() ==null || request.getProviderProtocol()==null || providerId ==null){
            response.setCode(InvokeResult.PARAM_ERROR.getCode());
            response.setMessage(InvokeResult.PARAM_ERROR.getMessage());
            return response;
        }

        ProviderVo vo=new ProviderVo();
        vo.setProviderId(providerId);
        vo.setProviderName(request.getProviderName());
        vo.setMessageType(request.getMessageType());
        vo.setMessageKind(request.getMessageKind());
        vo.setProviderProtocol(request.getProviderProtocol());

        try {
            int saveResult=  providerMapper.editProvider(vo);
            if(saveResult >0){
                response.setCode(InvokeResult.SUCCESS.getCode());
                response.setMessage(InvokeResult.SUCCESS.getMessage());
                response.setResult("修改成功");
            }else{
                response.setCode(InvokeResult.SYS_ERROR.getCode());
                response.setMessage(InvokeResult.SYS_ERROR.getMessage());
                response.setResult("修改失败");
            }
        } catch (Exception e) {
            logger.error("修改服务商异常，返回：", e);
            response.setCode(InvokeResult.SYS_ERROR.getCode());
            response.setMessage(InvokeResult.SYS_ERROR.getMessage());
        }
        return response;
    }

    @Override
    @Log(operationType="修改操作",operationName="修改服务商配置")
    public ServiceProviderResponse editProviderConfig(@PathVariable(value = "providerId", required = false) Integer providerId, @PathVariable("configId") Integer configId, @ApiParam(value = "request", required = true) @RequestBody ProviderConfigRequest request) {
        logger.info("修改服务商配置,入参："+ JSONObject.toJSONString(request)+",providerId="+providerId+",configId="+configId);

        ServiceProviderResponse response=new ServiceProviderResponse();
        if (providerId ==null || configId==null || request.getConfigKey() == null ||request.getConfigValue() ==null ){
            response.setCode(InvokeResult.PARAM_ERROR.getCode());
            response.setMessage(InvokeResult.PARAM_ERROR.getMessage());
            return response;
        }

        ProviderConfigVo configVo=new ProviderConfigVo();
        configVo.setProviderId(providerId);
        configVo.setConfigKey(request.getConfigKey());
        configVo.setConfigValue(request.getConfigValue());
        configVo.setConfigId(configId);

        try {
            int editProviderConfigResult=  providerConfigMapper.editProviderConfig(configVo);
            if(editProviderConfigResult > 0){
                response.setCode(InvokeResult.SUCCESS.getCode());
                response.setMessage(InvokeResult.SUCCESS.getMessage());
                response.setResult("服务商配置修改成功");
            }else{
                response.setCode(InvokeResult.SYS_ERROR.getCode());
                response.setMessage(InvokeResult.SYS_ERROR.getMessage());
                response.setResult("服务商配置修改失败");
            }
        } catch (Exception e) {
            logger.error("修改服务商配置异常，返回：", e);
            response.setCode(InvokeResult.SYS_ERROR.getCode());
            response.setMessage(InvokeResult.SYS_ERROR.getMessage());

        }
        return response;
    }

    @Override
    @Log(operationType="删除操作",operationName="删除服务商信息")
    public ProviderConfigurationResponse delProvider(@PathVariable("providerId") Integer providerId) {
        logger.info("删除服务商,入参：providerId="+ providerId);

        ProviderConfigurationResponse response=new ProviderConfigurationResponse();
        if (providerId ==null){
            response.setCode(InvokeResult.PARAM_ERROR.getCode());
            response.setMessage(InvokeResult.PARAM_ERROR.getMessage());
            return response;
        }

        try {
            /**
             * 先删除服务商编号关联的服务商配置信息
             */
            List<ProviderConfigVo> configList=providerConfigMapper.getConfigByProviderId(providerId);
            if(configList !=null  && !configList.isEmpty()){
                int delConfigResult=providerConfigMapper.delProviderConfig(configList);
                if(delConfigResult == 0){

                    response.setCode(InvokeResult.SYS_ERROR.getCode());
                    response.setMessage(InvokeResult.SYS_ERROR.getMessage());
                    response.setResult("删除服务商配置失败");
                    logger.info("删除服务商配置失败");
                }
            }
            /**
             * 再删除服务商信息
             */
            int delProviderResult=providerMapper.delProvider(providerId);
            if(delProviderResult >0){
                response.setCode(InvokeResult.SUCCESS.getCode());
                response.setMessage(InvokeResult.SUCCESS.getMessage());
                response.setResult("删除服务商成功");
            }else{
                response.setCode(InvokeResult.SYS_ERROR.getCode());
                response.setMessage(InvokeResult.SYS_ERROR.getMessage());
                response.setResult("删除服务商失败");
            }

        } catch (Exception e) {
            logger.error("删除服务商异常，返回：", e);
            response.setCode(InvokeResult.SYS_ERROR.getCode());
            response.setMessage(InvokeResult.SYS_ERROR.getMessage());
        }
        return response;
    }

    @Override
    @Log(operationType="删除操作",operationName="删除服务商配置信息")
    public ProviderConfigurationResponse delProviderConfig(@PathVariable(value = "providerId",required = false) Integer providerId, @PathVariable("configId") Integer configId) {
        logger.info("删除服务商配置,入参：providerId="+ providerId+",configId="+configId);

        ProviderConfigurationResponse response=new ProviderConfigurationResponse();
        if ( configId ==null ){
            response.setCode(InvokeResult.PARAM_ERROR.getCode());
            response.setMessage(InvokeResult.PARAM_ERROR.getMessage());
            return response;
        }

        try {
            /**
             * 删除服务商配置信息
             */
            ProviderConfigVo pcVo=new ProviderConfigVo();
            pcVo.setConfigId(configId);
            List<ProviderConfigVo> list=new ArrayList<>();
            list.add(pcVo);
            if (!list.isEmpty()) {
                int delConfigResult = providerConfigMapper.delProviderConfig(list);
                if(delConfigResult > 0){
                    response.setCode(InvokeResult.SUCCESS.getCode());
                    response.setMessage(InvokeResult.SUCCESS.getMessage());
                    response.setResult("删除服务商配置成功");
                }else{
                    response.setCode(InvokeResult.SYS_ERROR.getCode());
                    response.setMessage(InvokeResult.SYS_ERROR.getMessage());
                    response.setResult("删除服务商配置失败");
                }
            }

        }catch(Exception e){
                logger.error("删除服务商配置异常，返回：", e);
                response.setCode(InvokeResult.SYS_ERROR.getCode());
                response.setMessage(InvokeResult.SYS_ERROR.getMessage());
            }
            return response;
        }
}
