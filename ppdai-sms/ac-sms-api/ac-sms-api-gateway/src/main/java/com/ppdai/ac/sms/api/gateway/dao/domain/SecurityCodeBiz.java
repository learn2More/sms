package com.ppdai.ac.sms.api.gateway.dao.domain;

import com.ppdai.ac.sms.api.gateway.model.entity.SecurityCodeDTO;
import com.ppdai.ac.sms.api.gateway.dao.mapper.securitycode.SecurityCodeMapper;
import com.ppdai.ac.sms.api.common.SecurityCodeHelper;
import com.ppdai.ac.sms.common.redis.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.cache.CacheKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Created by kiekiyang on 2017/4/28.
 */
@Component
public class SecurityCodeBiz {
    private static final Logger logger = LoggerFactory.getLogger(SecurityCodeBiz.class);
    @Autowired
    SecurityCodeMapper securityCodeMapper;

    @Autowired
    RedisUtil redisUtil;

    /**
     * 创建验证码
     *
     * @param appId
     * @param codeKey
     * @param expire
     * @return
     */
    public String createSecurityCode(int appId, String codeKey, LocalDateTime expire) {
        String securityCode = "";
        if (appId <= 0 || StringUtils.isBlank(codeKey)) {
            return securityCode;
        }
        SecurityCodeDTO securityCodeDTO = new SecurityCodeDTO();
        securityCode = SecurityCodeHelper.generateVerifyCode(6);
        securityCodeDTO.setAppId(appId);
        securityCodeDTO.setCodeKey(codeKey);
        securityCodeDTO.setCodeValue(securityCode);
        Timestamp expireTs = expire == null ? Timestamp.from(LocalDateTime.now().plusYears(1).atZone(ZoneId.systemDefault()).toInstant())
                : Timestamp.from(expire.atZone(ZoneId.systemDefault()).toInstant());
        securityCodeDTO.setExpireTimeStamp(expireTs);
        int executeResult = securityCodeMapper.createSecurityCode(securityCodeDTO);
        Duration duration = Duration.between(LocalDateTime.now(),expireTs.toLocalDateTime());
        if (executeResult > 0) {
            String cacheKey = String.format("MESSAGE:SC-%s-%s", appId, codeKey);
            logger.info("cacheKey: "+ cacheKey+" start to set value in redis");
            redisUtil.set(cacheKey, securityCode, duration.getSeconds());
            logger.info("cacheKey: "+ cacheKey+" end to set value in redis");
        }
        return securityCode;
    }

    /**
     * 校验验证码
     *
     * @param appId
     * @param codeKey
     * @param verifyValue
     * @return
     */
    public boolean verifySecurityCode(int appId, String codeKey, String verifyValue, boolean checkOnly) {
        boolean verifyResult = false;
        if (appId <= 0 || StringUtils.isBlank(codeKey)) {
            return verifyResult;
        }

        String cacheKey = String.format("MESSAGE:SC-%s-%s", appId, codeKey);
        logger.info("cacheKey: "+ cacheKey+" start to check value in redis");
        verifyResult = redisUtil.check(cacheKey, verifyValue);
        logger.info("cacheKey: "+ cacheKey+" check value in redis:"+ verifyValue);
        // FIXME: 2017/6/15 老系统兼容修改 checkOnly 只校验验证码格式
        //if (!checkOnly && verifyResult) {
        if (verifyResult) {//验证通过之后,清除缓存
            redisUtil.del(cacheKey);
            //securityCodeMapper.delSecurityCode(appId, codeKey, verifyValue);
        }
        return verifyResult;
    }




}
