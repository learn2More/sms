package com.ppdai.ac.sms.contract.controller.department;

import com.ppdai.ac.sms.contract.request.department.DepartmentRequest;
import com.ppdai.ac.sms.contract.response.DepartmentResponse;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

/**
 * Created by wangxiaomei02 on 2017/5/12.
 * 部门相关接口
 */
@Api(value = "departmentService", description = "the departmentService API")
@RequestMapping("/api")
public interface DepartmentService {
    @ApiOperation(value = "查询部门", notes = "department", response = DepartmentResponse.class, tags = {"departmentService"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "查询结果", response = DepartmentResponse.class)})
    @RequestMapping(value = {"/department"},
            produces = {"application/json"},
            method = RequestMethod.GET)
    DepartmentResponse getDepartmentList(@RequestParam(value = "parentId",required = false)Integer parentId);

    @ApiOperation(value = "添加部门", notes = "department", response = DepartmentResponse.class, tags = {"departmentService"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "保存结果", response = DepartmentResponse.class)})
    @RequestMapping(value = {"/department"},
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    DepartmentResponse saveDepartment(@ApiParam(value = "request", required = true) @RequestBody DepartmentRequest request);

    @ApiOperation(value = "编辑部门", notes = "department/{departmentId}", response = DepartmentResponse.class, tags = {"departmentService"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "保存结果", response = DepartmentResponse.class)})
    @PutMapping(value = {"/department/{departmentId}"}, consumes ={"application/json"}, produces = {"application/json"})
    DepartmentResponse editDepartment(@PathVariable("departmentId") Integer departmentId,@ApiParam(value = "request", required = true) @RequestBody DepartmentRequest request);

    @ApiOperation(value = "删除部门", notes = "department/{departmentId}", response = DepartmentResponse.class, tags = {"departmentService"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "保存结果", response = DepartmentResponse.class)})
    @DeleteMapping(value = {"/department/{departmentId}"},
            produces = {"application/json"})
    DepartmentResponse delDepartment(@PathVariable("departmentId") Integer departmentId);

}
