package com.ppdai.ac.sms.consumer.core.dao.domain;

import com.google.common.collect.Lists;
import com.ppdai.ac.sms.consumer.core.dao.mapper.smsbase.BusinessProviderMapper;
import com.ppdai.ac.sms.consumer.core.model.entity.BusinessProviderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by kiekiyang on 2017/5/4.
 */
@Component
public class BusinessProviderBiz {
    @Autowired
    BusinessProviderMapper businessProviderMapper;

    public List<BusinessProviderDTO> findBusinessProviderByBusinessId(int businessId) {
        List<BusinessProviderDTO> list = Lists.newArrayList();
        if (businessId <= 0) {
            return list;
        }
        list = businessProviderMapper.findBusinessProviderByBusinessId(businessId);
        return list;
    }

    public List<BusinessProviderDTO> findBusinessProviderByBusinessIdAndMessageKind(int businessId,int messageKind) {
        List<BusinessProviderDTO> list = Lists.newArrayList();
        if (businessId <= 0) {
            return list;
        }
        list = businessProviderMapper.findBusinessProviderByBusinessIdAndMessageKind(businessId,messageKind);
        return list;
    }
}
