package com.ppdai.ac.sms.api.gateway.dao.mapper.smsbase;

import com.ppdai.ac.sms.api.gateway.model.entity.SensitiveWordDTO;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by kiekiyang on 2017/4/26.
 */
@Component
public interface SensitiveWordMapper {
    List<SensitiveWordDTO> finAll();

    List<String> finAllWord() throws SQLException;
}
