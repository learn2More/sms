package com.ppdai.ac.sms.contract.dao.mapper.smsbase;

import com.ppdai.ac.sms.contract.model.entity.DepartmentDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by wangxiaomei02 on 2017/5/25.
 */
@Component
public interface DepartmentMapper {
    DepartmentDTO findDepartmentById(int dId);

    int saveDepartment(DepartmentDTO dto);

    List<DepartmentDTO> getDepartment(@Param("parentId")Integer parentId);

    int delDepartment(@Param("departmentId")int departmentId);

    int editDepartment(DepartmentDTO dto);
}
