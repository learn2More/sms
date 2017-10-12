package com.ppdai.ac.sms.api.gateway.dao.domain;

import com.ppdai.ac.sms.api.gateway.enums.SensitiveWordOperation;
import com.ppdai.ac.sms.api.gateway.dao.mapper.smsbase.SensitiveWordMapper;
import com.ppdai.ac.sms.api.gateway.model.entity.SensitiveWordDTO;
import com.ppdai.ac.sms.api.gateway.response.ProviderConfig;
import com.ppdai.ac.sms.common.redis.RedisUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by kiekiyang on 2017/4/26.
 */
@Component
public class SensitiveWordBiz {
    private static final Logger logger = LoggerFactory.getLogger(SensitiveWordBiz.class);

    @Autowired
    SensitiveWordMapper sensitiveWordMapper;

    @Autowired
    RedisUtil<String,List<String>> redisUtil;

    private final String SENSITIVE_WORD = "SMS:SENSITIVE";

    /**
     * 过滤敏感词
     *
     * @param content
     * @param filterRule
     * @return
     */
    public String filterSensitiveWord(String content, int filterRule) {
        String tempCot=content;
        if (filterRule == SensitiveWordOperation.IGNORE.getCode())
            return tempCot;

        String cacheKey =SENSITIVE_WORD;
        List<String> sensitiveWordList=redisUtil.get(cacheKey);
        if(CollectionUtils.isEmpty(sensitiveWordList)){
            try {
                sensitiveWordList = sensitiveWordMapper.finAllWord();
            } catch (SQLException e) {
                logger.error("查询敏感词信息异常", e);
            }

            if(CollectionUtils.isNotEmpty(sensitiveWordList)){
                //敏感词 缓存 3min
                Duration duration = Duration.between(LocalDateTime.now(), LocalDateTime.now().plusMinutes(3));
                redisUtil.set(cacheKey,sensitiveWordList,duration.getSeconds());
            }
        }

        if(CollectionUtils.isNotEmpty(sensitiveWordList)){
            for (String s : sensitiveWordList) {
                if (StringUtils.isNotEmpty(s) && content.contains(s)) {
                    if (filterRule == SensitiveWordOperation.BLOCK.getCode()) {
                        logger.info(content+" 敏感词过滤-阻止:"+s);
                        return "";
                    }
                    if (filterRule == SensitiveWordOperation.REPLACE.getCode()) {
                        logger.info(content+" 敏感词过滤-替换:"+s);
                        tempCot = content.replaceAll(s, "");
                    }
                }
            }
        }

        return tempCot;
    }
}
