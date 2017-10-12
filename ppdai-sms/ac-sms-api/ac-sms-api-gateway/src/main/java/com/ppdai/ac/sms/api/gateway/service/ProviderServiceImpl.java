package com.ppdai.ac.sms.api.gateway.service;

import com.google.common.collect.Lists;
import com.ppdai.ac.sms.api.gateway.controller.ProviderService;
import com.ppdai.ac.sms.api.gateway.dao.mapper.smsbase.ProviderConfigMapper;
import com.ppdai.ac.sms.api.gateway.enums.InvokeResult;
import com.ppdai.ac.sms.api.gateway.model.entity.ProviderConfigDTO;
import com.ppdai.ac.sms.api.gateway.request.QueryProviderConfigRequest;
import com.ppdai.ac.sms.api.gateway.response.ProviderConfig;
import com.ppdai.ac.sms.api.gateway.response.QueryProviderConfigResponse;
import com.ppdai.ac.sms.common.redis.RedisUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by kiekiyang on 2017/5/18.
 */
@RestController
public class ProviderServiceImpl implements ProviderService {
    private static final Logger logger = LoggerFactory.getLogger(ProviderServiceImpl.class);

    @Autowired
    ProviderConfigMapper providerConfigMapper;

    @Autowired
    RedisUtil<String,List<ProviderConfig>> redisUtil;

    private final String PROVIDER_CONFIG = "SMS:GATEWAY_PROVIDER_CONFIG:%s";


    @Override
    public QueryProviderConfigResponse queryProviderConfig(QueryProviderConfigRequest request) {
        QueryProviderConfigResponse response = new QueryProviderConfigResponse();
        int providerId=request.getProviderId();
        String cacheKey = String.format(PROVIDER_CONFIG, providerId);
        List<ProviderConfig> beanList=redisUtil.get(cacheKey);
        if(null==beanList){
            try {
                List<ProviderConfigDTO> configlist = providerConfigMapper.findProviderConfigByProviderId(providerId);
                beanList= Lists.newArrayList();
                for (ProviderConfigDTO providerConfig : configlist) {
                    ProviderConfig config = new ProviderConfig();
                    BeanUtils.copyProperties(providerConfig, config);
                    beanList.add(config);
                }
            } catch (Exception ex) {
                logger.error("查询服务商配置异常", ex);
                response.setResultCode(InvokeResult.SYS_ERROR.getCode());
                response.setResultMessage(InvokeResult.SYS_ERROR.getMessage());
            }
            if(CollectionUtils.isNotEmpty(beanList)){
                //服务商配置信息 缓存 1min
                Duration duration = Duration.between(LocalDateTime.now(), LocalDateTime.now().plusMinutes(1));
                redisUtil.set(cacheKey,beanList,duration.getSeconds());
            }

        }
        response.setResultCode(InvokeResult.SUCCESS.getCode());
        response.setResultMessage(InvokeResult.SUCCESS.getMessage());
        response.setResultObject(beanList);

        return response;
    }
}
