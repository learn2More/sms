package com.ppdai.ac.sms.contract.controller.template;


import com.ppdai.ac.sms.contract.request.template.TemplateApplyRequest;
import com.ppdai.ac.sms.contract.request.template.TemplateApproveListRequest;
import com.ppdai.ac.sms.contract.request.template.TemplateEditRequest;
import com.ppdai.ac.sms.contract.request.template.TemplateManagementRequest;
import com.ppdai.ac.sms.contract.response.TemplateApplyResponse;
import com.ppdai.ac.sms.contract.response.TemplateApproveEditResponse;
import com.ppdai.ac.sms.contract.response.TemplateApproveListResponse;
import com.ppdai.ac.sms.contract.response.TemplateCheckListResponse;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

/**
 * Created by wangxiaomei02 on 2017/5/9.
 * 模板流程管理
 */
@Api(value = "templateWorkflowService", description = "the templateWorkflowService API")
@RequestMapping("/api")
public interface TemplateWorkflowService {
    @ApiOperation(value = "模板申请", notes = "template/apply", response = TemplateApplyResponse.class, tags = {"templateWorkflowService"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "保存结果", response = TemplateApplyResponse.class)})
    @RequestMapping(value = {"/template/apply"},
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    TemplateApplyResponse saveTemplateApply(@ApiParam(value = "request", required = true) @RequestBody TemplateApplyRequest request);

    @ApiOperation(value = "模板审批列表", notes = "template/approve", response = TemplateApproveListResponse.class, tags = {"templateWorkflowService"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "查询结果", response = TemplateApproveListResponse.class)})
    @RequestMapping(value = {"/template/approve"},
            produces = {"application/json"},
            method = RequestMethod.GET)
    TemplateApproveListResponse getTemplateApproveList(@RequestParam(value = "approveStatus",required = false)Integer approveStatus,
                                                       @RequestParam("approverJobId")String approverJobId,
                                                       @RequestParam("pageNum")Integer pageNum,
                                                       @RequestParam("pageSize")Integer pageSize);

    @ApiOperation(value = "模板流程变更", notes = "template/approve", response = TemplateApproveEditResponse.class, tags = {"templateWorkflowService"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "保存结果", response = TemplateApproveEditResponse.class)})
    @PutMapping(value = {"/template/approve"},
            produces = {"application/json"})
    TemplateApproveEditResponse editTemplateApprove(@RequestBody TemplateApproveListRequest request);

    @ApiOperation(value = "修改模板信息", notes = "template/check", response = TemplateApproveEditResponse.class, tags = {"templateWorkflowService"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "保存结果", response = TemplateApproveEditResponse.class)})
    @PutMapping(value = {"/template/check/{templateId}"},
            produces = {"application/json"},
            consumes = {"application/json"})
     TemplateApproveEditResponse editTemplateStatus(@PathVariable("templateId") Integer templateId,@ApiParam(value = "request", required = true) @RequestBody TemplateManagementRequest request);

    @ApiOperation(value = "模板查看列表", notes = "template/check", response = TemplateCheckListResponse.class, tags = {"templateWorkflowService"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "查询结果", response = TemplateCheckListResponse.class)})
    @RequestMapping(value = {"/template/check"},
            produces = {"application/json"},
            method = RequestMethod.GET)
    TemplateCheckListResponse getTemplateCheckList(@RequestParam(value = "departmentId",required = false)Integer department,
                                                   @RequestParam(value = "templateStatus",required = false)Integer templateStatus,
                                                   @RequestParam(value = "businessId",required = false) Integer businessId,
                                                   @RequestParam(value = "messageKind",required = false) Integer messageKind,
                                                   @RequestParam("pageNum")Integer pageNum,
                                                   @RequestParam("pageSize")Integer pageSize,
                                                   @RequestParam(value = "templateAlias",required = false)String templateAlias);

//    @ApiOperation(value = "修改模板信息", notes = "template/check", response = TemplateApproveEditResponse.class, tags = {"templateWorkflowService"})
//    @ApiResponses(value = {@ApiResponse(code = 200, message = "保存结果", response = TemplateApproveEditResponse.class)})
//    @PutMapping(value = {"/template/info"},
//            produces = {"application/json"},
//            consumes = {"application/json"})
//    TemplateApproveEditResponse editTemplateInfo(@PathVariable("templateId") Integer templateId,@ApiParam(value = "request", required = true) @RequestBody TemplateManagementRequest request);

}
