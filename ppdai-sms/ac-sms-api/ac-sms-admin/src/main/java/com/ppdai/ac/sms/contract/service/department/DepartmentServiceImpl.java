package com.ppdai.ac.sms.contract.service.department;

import com.alibaba.fastjson.JSONObject;
import com.ppdai.ac.sms.contract.controller.department.DepartmentService;
import com.ppdai.ac.sms.contract.dao.mapper.smsbase.DepartmentMapper;
import com.ppdai.ac.sms.contract.enums.InvokeResult;
import com.ppdai.ac.sms.contract.model.entity.DepartmentDTO;
import com.ppdai.ac.sms.contract.request.department.DepartmentRequest;
import com.ppdai.ac.sms.contract.response.DepartmentResponse;
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
import java.util.List;

/**
 * Created by wangxiaomei02 on 2017/5/12.
 */
@RestController
public class DepartmentServiceImpl implements DepartmentService {
    private static final Logger logger = LoggerFactory.getLogger(DepartmentServiceImpl.class);

    @Autowired
    DepartmentMapper departmentMapper;


    @Override
    public DepartmentResponse getDepartmentList(@RequestParam(value = "parentId",required = false)Integer parentId) {
        logger.info("查询部门,入参：parentId"+ parentId);
            DepartmentResponse response=new DepartmentResponse();
        try {
            List<DepartmentDTO> list=departmentMapper.getDepartment(parentId);
            if (list !=null){
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
            logger.error("查询部门异常，返回：", e);
            response.setCode(InvokeResult.SYS_ERROR.getCode());
            response.setMessage(InvokeResult.SYS_ERROR.getMessage());
        }
        return response;
    }

    @Override
    public DepartmentResponse saveDepartment(@ApiParam(value = "request", required = true) @RequestBody DepartmentRequest request) {
        DepartmentResponse response=new DepartmentResponse();

        logger.info("添加部门,入参："+ JSONObject.toJSONString(request));
        if (StringUtils.isBlank(request.getDepartmentName()) || StringUtils.isBlank(request.getOwnerJobId())
                || request.getLevel() ==null || request.getParentId() ==null) {
            response.setCode(InvokeResult.PARAM_ERROR.getCode());
            response.setMessage(InvokeResult.PARAM_ERROR.getMessage());
            return response;
        }

        try {
            DepartmentDTO dto=new DepartmentDTO();
            dto.setDepartmentName(request.getDepartmentName());
            dto.setLevel(request.getLevel());
            dto.setOwnerName(request.getOwnerName());
            dto.setOwnerEmail(request.getOwnerEmail());
            dto.setParentId(request.getParentId());
            dto.setOwnerJobId(request.getOwnerJobId());
            dto.setInsertTime(new Timestamp(System.currentTimeMillis()));
            int saveResult=departmentMapper.saveDepartment(dto);


            if(saveResult>0){
                int dId=dto.getDepartmentId(); //获得保存后的部门自增id
                dto.setDepartmentId(dId);
                dto.setUpdateTime(new Timestamp(System.currentTimeMillis()));

                response.setCode(InvokeResult.SUCCESS.getCode());
                response.setMessage(InvokeResult.SUCCESS.getMessage());
                response.setResult(dto);
            } else{
                response.setCode(InvokeResult.BUSINESS_ERROR.getCode());
                response.setMessage(InvokeResult.BUSINESS_ERROR.getMessage());
                response.setResult("保存失败");
            }
        } catch (Exception e) {
            logger.error("添加分摊部门异常，返回：", e);
            response.setCode(InvokeResult.SYS_ERROR.getCode());
            response.setMessage(InvokeResult.SYS_ERROR.getMessage());
        }

        return response;
    }

    @Override
    public DepartmentResponse editDepartment(@PathVariable("departmentId") Integer departmentId, @ApiParam(value = "request", required = true) @RequestBody DepartmentRequest request) {
        DepartmentResponse response=new DepartmentResponse();
        logger.info("修改部门,入参："+ JSONObject.toJSONString(request));

        if (StringUtils.isBlank(request.getDepartmentName()) || StringUtils.isBlank(request.getOwnerJobId())
                || request.getLevel() ==null || request.getParentId() ==null || departmentId ==null) {
            response.setCode(InvokeResult.PARAM_ERROR.getCode());
            response.setMessage(InvokeResult.PARAM_ERROR.getMessage());
            return response;
        }
        try {
            DepartmentDTO dto=new DepartmentDTO();
            dto.setDepartmentName(request.getDepartmentName());
            dto.setLevel(request.getLevel());
            dto.setOwnerName(request.getOwnerName());
            dto.setOwnerEmail(request.getOwnerEmail());
            dto.setParentId(request.getParentId());
            dto.setOwnerJobId(request.getOwnerJobId());
            dto.setDepartmentId(departmentId);
            int editResult=departmentMapper.editDepartment(dto);

            if(editResult>0){
                dto.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                response.setCode(InvokeResult.SUCCESS.getCode());
                response.setMessage(InvokeResult.SUCCESS.getMessage());
                response.setResult(dto);
            } else{
                response.setCode(InvokeResult.BUSINESS_ERROR.getCode());
                response.setMessage(InvokeResult.BUSINESS_ERROR.getMessage());
                response.setResult("修改失败");
            }
        } catch (Exception e) {
            logger.error("修改分摊部门异常，返回：", e);
            response.setCode(InvokeResult.SYS_ERROR.getCode());
            response.setMessage(InvokeResult.SYS_ERROR.getMessage());
        }
            return response;
    }

    @Override
    @Log(operationType="删除操作",operationName="删除部门")
    public DepartmentResponse delDepartment(@PathVariable("departmentId") Integer departmentId) {
        DepartmentResponse response=new DepartmentResponse();
        logger.info("删除部门,入参 departmentId=："+ departmentId);
        if (departmentId ==null) {
            response.setCode(InvokeResult.PARAM_ERROR.getCode());
            response.setMessage(InvokeResult.PARAM_ERROR.getMessage());
            return response;
        }
        try {
            int delResult=departmentMapper.delDepartment(departmentId);
            if(delResult>0){
                response.setCode(InvokeResult.SUCCESS.getCode());
                response.setMessage(InvokeResult.SUCCESS.getMessage());
                response.setResult("删除成功!");
            }else{
                response.setCode(InvokeResult.BUSINESS_ERROR.getCode());
                response.setMessage(InvokeResult.BUSINESS_ERROR.getMessage());
                response.setResult("修改失败");
                logger.info("删除分摊部门失败！");
            }
        } catch (Exception e) {
            logger.error("删除分摊部门异常，返回：", e);
            response.setCode(InvokeResult.SYS_ERROR.getCode());
            response.setMessage(InvokeResult.SYS_ERROR.getMessage());
        }
        return response;
    }


}
