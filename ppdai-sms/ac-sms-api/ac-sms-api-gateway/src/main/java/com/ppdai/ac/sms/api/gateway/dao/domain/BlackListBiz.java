package com.ppdai.ac.sms.api.gateway.dao.domain;

import com.google.common.collect.Lists;
import com.ppdai.ac.sms.api.gateway.dao.mapper.smsbase.BlackListMapper;
import com.ppdai.ac.sms.api.gateway.model.entity.BlackListDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by kiekiyang on 2017/4/25.
 */
@Component
public class BlackListBiz {
    private static final Logger logger = LoggerFactory.getLogger(BlackListBiz.class);

    @Autowired
    BlackListMapper blackListMapper;

    public boolean importBlackList(String[] features, String[] remark) {

        List<BlackListDTO> blackList = Lists.newArrayList();
        for (int i = 0; i < features.length; i++) {
            if (StringUtils.isBlank(features[i])){
                continue;
            }else{
                //去重
                BlackListDTO dto = blackListMapper.getBlackListByfeature(features[i]);
                if(null!=dto)continue;

                BlackListDTO blackListDTO = new BlackListDTO();
                blackListDTO.setCreateType(2);
                blackListDTO.setFeature(features[i]);
                blackListDTO.setRemark(remark[i]);
                blackList.add(blackListDTO);
            }
        }
//        Map<String, List<BlackListDTO>> tmp = new HashMap<String, List<BlackListDTO>>();
//        tmp.put("blacklist", blackList);
        if(CollectionUtils.isNotEmpty(blackList)){
            int execResult = blackListMapper.importBlackList(blackList);
            return execResult > 0 ? true : false;
        }
        return true;

    }

    /**
     * 检查特征是否在黑名单中
     *
     * @param feature
     * @return
     */
    public boolean checkBlackList(String feature) {
        if (StringUtils.isBlank(feature))
            return false;
        BlackListDTO blackListDTO = blackListMapper.getBlackListByfeature(feature);
        if (blackListDTO == null)
            return false;
        return true;
    }
}
