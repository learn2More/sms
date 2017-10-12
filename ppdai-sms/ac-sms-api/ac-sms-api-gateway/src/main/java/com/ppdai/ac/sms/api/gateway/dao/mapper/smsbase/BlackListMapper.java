package com.ppdai.ac.sms.api.gateway.dao.mapper.smsbase;

import com.ppdai.ac.sms.api.gateway.model.entity.BlackListDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by kiekiyang on 2017/4/25.
 */
@Component
public interface BlackListMapper {
    BlackListDTO getBlackListByfeature(@Param("feature") String feature);

    int importBlackList(@Param("blacklist") List<BlackListDTO> blacklist);
}
