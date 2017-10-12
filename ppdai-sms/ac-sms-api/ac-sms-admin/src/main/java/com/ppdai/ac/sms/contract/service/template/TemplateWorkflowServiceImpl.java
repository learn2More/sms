package com.ppdai.ac.sms.contract.service.template;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.ppdai.ac.sms.common.redis.RedisUtil;
import com.ppdai.ac.sms.common.service.SendMailService;
import com.ppdai.ac.sms.contract.controller.template.TemplateWorkflowService;
import com.ppdai.ac.sms.contract.dao.mapper.smsbase.CallerBusinessMapper;
import com.ppdai.ac.sms.contract.dao.mapper.smsbase.DepartmentMapper;
import com.ppdai.ac.sms.contract.dao.mapper.smsbase.MessageTemplateMapper;
import com.ppdai.ac.sms.contract.enums.InvokeResult;
import com.ppdai.ac.sms.contract.model.entity.CallerBusinessDTO;
import com.ppdai.ac.sms.contract.model.entity.MessageApplyDTO;
import com.ppdai.ac.sms.contract.model.entity.MessageTemplateDTO;
import com.ppdai.ac.sms.contract.model.vo.TemplateApproveVo;
import com.ppdai.ac.sms.contract.model.vo.TemplateCheckVo;
import com.ppdai.ac.sms.contract.request.template.TemplateApplyRequest;
import com.ppdai.ac.sms.contract.request.template.TemplateApproveListRequest;
import com.ppdai.ac.sms.contract.request.template.TemplateManagementRequest;
import com.ppdai.ac.sms.contract.response.TemplateApplyResponse;
import com.ppdai.ac.sms.contract.response.TemplateApproveEditResponse;
import com.ppdai.ac.sms.contract.response.TemplateApproveListResponse;
import com.ppdai.ac.sms.contract.response.TemplateCheckListResponse;
import com.ppdai.ac.sms.contract.utils.FirstLetterUtil;
import com.ppdai.ac.sms.contract.utils.Log;
import com.ppdai.ac.sms.contract.utils.Page;
import io.swagger.annotations.ApiParam;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ppdai.ac.sms.contract.dao.mapper.smsbase.TemplateWorkFlowMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangxiaomei02 on 2017/5/16.
 */
@RestController
public class TemplateWorkflowServiceImpl implements TemplateWorkflowService {
    private static final Logger logger = LoggerFactory.getLogger(TemplateWorkflowServiceImpl.class);

    @Autowired
    private SendMailService sendmail;

    @Autowired
    TemplateWorkFlowMapper templateWorkFlowMapper;

    @Autowired
    DepartmentMapper departmentMapper;

    @Autowired
    CallerBusinessMapper callerBusinessMapper;

    @Autowired
    MessageTemplateMapper messageTemplateMapper;

    Config config= ConfigService.getAppConfig();
    String approveUrl=config.getProperty("approveUrl","false");
//    @Value("${approveUrl}")
//    String approveUrl;
    String addresser=config.getProperty("spring.mail.username","false");
//    @Value("${spring.mail.username}")
//    String addresser;

    @Autowired
    RedisUtil<Integer,String> redisUtil;

    @Override
    @Log(operationType="模板申请",operationName="模板申请操作")
    public TemplateApplyResponse saveTemplateApply(@ApiParam(value = "request", required = true) @RequestBody TemplateApplyRequest request) {

        TemplateApplyResponse response=new TemplateApplyResponse();

        logger.info("模板申请操作,入参：="+ JSONObject.toJSONString(request));
        if (StringUtils.isBlank(request.getApplyTitle()) || request.getApplyDepartment() == null
                ||StringUtils.isBlank(request.getApplicantName()) ||StringUtils.isBlank(request.getApplicantEmail())
                ||request.getBusinessId()== null ||request.getMessageKind()== null || request.getCaller()== null
                ||StringUtils.isBlank(request.getContent()) || request.getIntervalTime()== null || request.getMaxCount()== null
                ||request.getJobId()== null) {
            response.setCode(InvokeResult.PARAM_ERROR.getCode());
            response.setMessage(InvokeResult.PARAM_ERROR.getMessage());
            return response;
        }
        if(request.getApplicantEmail().equals(request.getApproverEmail())){
            response.setCode(InvokeResult.PARAM_APPLICANT_ERROR.getCode());
            response.setMessage(InvokeResult.PARAM_APPLICANT_ERROR.getMessage());
            return response;
        }
        if(request.getTotalMaxCount()< 1 || request.getTotalMaxCount()>100 || request.getMaxCount() > request.getTotalMaxCount()){
            response.setCode(InvokeResult.PARAM_RESTRICTCOUNTBYDAY_ERROR.getCode());
            response.setMessage(InvokeResult.PARAM_RESTRICTCOUNTBYDAY_ERROR.getMessage());
            return response;
        }
        try {
            MessageApplyDTO dto=new MessageApplyDTO();
            dto.setApplyTitle(request.getApplyTitle());
            dto.setApplyDepartmentId(request.getApplyDepartment());
            dto.setApplicant(request.getApplicantName());
            dto.setApplicantEmail(request.getApplicantEmail());
            dto.setBusinessId(request.getBusinessId());
            dto.setMessageKind(request.getMessageKind());
            dto.setTemplateContent(request.getContent());
            dto.setUserId(request.getJobId());
            dto.setCallerId(request.getCaller());
            dto.setIntervalTime(request.getIntervalTime()==null?10:request.getIntervalTime());
            dto.setTotalMaxCount(request.getTotalMaxCount()==null?5:request.getTotalMaxCount());
            dto.setMaxCount(request.getMaxCount()==null?5:request.getMaxCount());
            dto.setApproverJobId(request.getApproverJobId());
            int result= templateWorkFlowMapper.saveTemplateApply(dto);
            //先保存模板申请数据到数据库
            if(result > 0){
                //发送给审批人----您好，***（申请人姓名）通过短信平台申请建立短信模板****（模板名称）。请您审核**********************（审批页面url）
                sendmail.sendSimpleMail(addresser,request.getApproverEmail(),request.getApplyTitle(),"您好，"+request.getApplicantName()+"通过短信中台系统申请建立模板"+request.getApplyTitle()+"。请您审核\n" +
                        "\n" +approveUrl+"/template/approve");

                //发送给申请人----您好，您提交的短信模板*****（模板名称）已提交至直接短信部门负责人****（审批人姓名），请等待审核结果。
                sendmail.sendSimpleMail(addresser,request.getApplicantEmail(),request.getApplyTitle(),"您好，您提交的短信模板 "+request.getApplyTitle()+" 已提交至直接短信部门负责人"+request.getApproverEmail()+",请等待审核结果。");
                response.setCode(InvokeResult.SUCCESS.getCode());
                response.setMessage(InvokeResult.SUCCESS.getMessage());
                response.setResult("保存成功");
            }
        } catch (Exception e) {
            logger.error("模板申请异常，返回：", e);
            response.setCode(InvokeResult.FAIL.getCode());
            response.setMessage(InvokeResult.FAIL.getMessage());
        }
        return response;
    }

    @Override
    public TemplateApproveListResponse getTemplateApproveList(@RequestParam(value = "approveStatus",required = false)Integer approveStatus,
                                                              @RequestParam("jobId")String jobId,
                                                              @RequestParam("pageNum")Integer pageNum,
                                                              @RequestParam("pageSize")Integer pageSize) {
        TemplateApproveListResponse response=new TemplateApproveListResponse();

        logger.info("获取模板审批列表,入参：approveStatus="+ approveStatus+",jobId="+ jobId);

        Page page=new Page(pageNum,pageSize);

        try {
            List<Integer> departmentIds=templateWorkFlowMapper.findDepartmentsByJobId(jobId);
            if(departmentIds !=null && !departmentIds.isEmpty()) {
                List<TemplateApproveVo> list = templateWorkFlowMapper.getTemplateApproveList(approveStatus,departmentIds, page);
                if (list != null) {
                    int total = templateWorkFlowMapper.getTemplateApproveCount(approveStatus,departmentIds);
                    response.setCode(InvokeResult.SELECT_SUCCESS.getCode());
                    response.setMessage(InvokeResult.SELECT_SUCCESS.getMessage());
                    response.setTotal(total);
                    response.setResult(list);
                }
            }
            else {
                response.setCode(InvokeResult.SELECT_SUCCESS.getCode());
                response.setMessage(InvokeResult.SELECT_SUCCESS.getMessage());
                response.setResult(new ArrayList<>());
                response.setTotal(0);
                logger.info("没有查到待审批列表数据");
            }

        }catch (Exception e){
            response.setCode(InvokeResult.SYS_ERROR.getCode());
            response.setMessage(InvokeResult.SYS_ERROR.getMessage());
            response.setResult("系统异常");
            logger.error("获取模板审批列表,异常,接口返回："+e);
        }

        return response;
    }

    @Override
    @Log(operationType="修改操作",operationName="修改模板审批状态")
    public TemplateApproveEditResponse editTemplateApprove(@ApiParam(value = "request", required = true) @RequestBody TemplateApproveListRequest request){
        TemplateApproveEditResponse response=new TemplateApproveEditResponse();
        logger.info("修改模板审批状态,入参："+JSONObject.toJSONString(request));
        if (request.getTemplateId()==null || request.getApproveStatus() == null ){
            response.setCode(InvokeResult.PARAM_ERROR.getCode());
            response.setMessage(InvokeResult.PARAM_ERROR.getMessage());
            return response;
        }
        //审批拒绝时拒绝原因不能为空
        if(request.getApproveStatus()==1){
            if (StringUtils.isBlank(request.getReason())){
                response.setCode(InvokeResult.PARAM_ERROR.getCode());
                response.setMessage(InvokeResult.PARAM_ERROR.getMessage());
                return response;
            }
        }
        /**
         * 修改申请模板状态
         */
        try {
            int result= templateWorkFlowMapper.editMessageApply(request.getTemplateId(), request.getReason(),request.getApproveStatus());
            if(result > 0){

                Integer templateId=request.getTemplateId();
                //根据模板id查询模板详情
                MessageApplyDTO maDTO=templateWorkFlowMapper.findTemplateApplyById(templateId);
                if(maDTO !=null) {
                if(request.getApproveStatus().equals(2)){

                        //关联数据唯一性，不能重复关联 只有当模板审批通过后才会关联【添加技术接入方和业务类型关联关系】，【创建模板】
                        CallerBusinessDTO cbDTO = callerBusinessMapper.findCallerBusinessByParam(maDTO.getCallerId(), maDTO.getBusinessId());
                        if (cbDTO == null) {
                            cbDTO = new CallerBusinessDTO();
                            /**
                             * 添加技术接入方和业务类型关联关系
                             */
                            cbDTO.setBusinessId(maDTO.getBusinessId());
                            cbDTO.setCallerId(maDTO.getCallerId());
                            callerBusinessMapper.saveCallerBusiness(cbDTO);
                        }
                        /**
                         * 审批通过时才会创建模板
                         */
                        try {
                            if (request.getApproveStatus().equals(2)) {
                                MessageTemplateDTO mtDTO = new MessageTemplateDTO();
                                mtDTO.setBusinessId(maDTO.getBusinessId());
                                mtDTO.setDepartmentId(maDTO.getApplyDepartmentId());
                                mtDTO.setTemplateName(maDTO.getApplyTitle());
                                String cn2py = FirstLetterUtil.cn2py(maDTO.getApplyTitle());//获取模板名称的首字母缩写
                                mtDTO.setTemplateAlias("tpl_" + cn2py + "_" + maDTO.getApplyID()); //模板别名组成：tpl_模板名称拼音首字母缩写_applyId
                                mtDTO.setMessageKind(maDTO.getMessageKind());
                                mtDTO.setContent(maDTO.getTemplateContent());
                                mtDTO.setIntervalTime(maDTO.getIntervalTime());
                                mtDTO.setTotalMaxCount(maDTO.getTotalMaxCount());
                                mtDTO.setMaxCount(maDTO.getMaxCount());
                                mtDTO.setFilterRule(3);//敏感词过滤规则 1:忽略 2：阻止 3：替换
                                mtDTO.setCallerId(maDTO.getCallerId());
                                /**
                                 *   创建模板
                                 */
                                int createResult = messageTemplateMapper.saveMessageTemplate(mtDTO);
                                if (createResult == 0) {
                                    response.setCode(InvokeResult.BUSINESS_ERROR.getCode());
                                    response.setMessage(InvokeResult.BUSINESS_ERROR.getMessage());
                                    response.setResult("保存失败");
                                    logger.info("创建模板异常");
                                }
                            }
                        } catch (Exception e) {
                            logger.error("创建模板异常,接口返回：" + e);
                        }
                    }


                    /**
                     * 模板审批后给申请人，审批人发送邮件通知
                     */

                        try {//ApproveStatus:1：已拒绝，2：已通过
                            String sendForProposer="";
                            String sendForApprover="";
                            if(request.getApproveStatus().equals(1)){
                                //拒绝时发送给申请人
                                 sendForApprover= sendmail.sendSimpleMail(addresser,maDTO.getApplicantEmail(),maDTO.getApplyTitle(),"您好，您申请的模板: "+maDTO.getApplyTitle()+" 已被拒绝。");
                                if( "success".equalsIgnoreCase(sendForApprover)){
                                    logger.info("模板审批拒绝，发送邮件成功！");
                                }
                                else{
                                    logger.info("模板审批拒绝，发送邮件失败！");
                                }
                            }else if(request.getApproveStatus().equals(2)){
                                //通过时发送给申请人，审批人
                                 sendForApprover= sendmail.sendSimpleMail(addresser,request.getApproveEmail(),maDTO.getApplyTitle(),"您好，您审批的模板:"+maDTO.getApplyTitle()+" 审核已通过.");

                                //发送给申请人
                                 sendForProposer=  sendmail.sendSimpleMail(addresser,maDTO.getApplicantEmail(),maDTO.getApplyTitle(),"您好，您申请的模板:"+maDTO.getApplyTitle()+" 已通过审核。");
                                if("success".equalsIgnoreCase(sendForProposer) && "success".equalsIgnoreCase(sendForApprover)){
                                    logger.info("模板审批通过，发送邮件成功！");
                                }
                                else{
                                    logger.info("模板审批通过，发送邮件失败！");
                                }
                            }

                        } catch (MailException e) {
                            response.setCode(InvokeResult.SYS_ERROR.getCode());
                            response.setMessage(InvokeResult.SYS_ERROR.getMessage());
                            response.setResult("邮件发送失败!");
                            logger.error("模板审批通过，发送邮件失败,接口返回："+e);
                        }
                    }
                    response.setCode(InvokeResult.SUCCESS.getCode());
                    response.setMessage(InvokeResult.SUCCESS.getMessage());
                    response.setResult("修改成功");
                }
                else{
                    response.setCode(InvokeResult.BUSINESS_ERROR.getCode());
                    response.setMessage(InvokeResult.BUSINESS_ERROR.getMessage());
                    response.setResult("更改审批状态失败");
                    logger.info("修改模板审批状态失败！");
                }
        } catch (Exception e) {
            response.setCode(InvokeResult.SYS_ERROR.getCode());
            response.setMessage(InvokeResult.SYS_ERROR.getMessage());
            response.setResult("保存失败");
            logger.error("模板审批接口调用失败,接口返回："+e);
        }
        return response;
    }

    @Override
    @Log(operationType="修改操作",operationName="修改模板信息")
    public TemplateApproveEditResponse editTemplateStatus(@PathVariable("templateId") Integer templateId,@ApiParam(value = "request", required = true) @RequestBody TemplateManagementRequest request){
        TemplateApproveEditResponse response=new TemplateApproveEditResponse();
        logger.info("修改模板信息,入参："+ JSONObject.toJSONString(request));
        if (templateId==null){
            response.setCode(InvokeResult.PARAM_ERROR.getCode());
            response.setMessage(InvokeResult.PARAM_ERROR.getMessage());
            return response;
        }
        try {
            TemplateCheckVo vo=new TemplateCheckVo();
            vo.setTemplateId(templateId);
            vo.setBusinessId(request.getBusinessId());
            vo.setDepartmentId(request.getDepartmentId());
            vo.setMaxCount(request.getMaxCount());
            vo.setMessageKind(request.getMessageKind());
            vo.setIntervalTime(request.getIntervalTime());
            vo.setTemplateContent(request.getTemplateContent());
            vo.setTotalMaxCount(request.getTotalMaxCount());
            vo.setTemplateStatus(request.getStatus());
            vo.setCallerId(request.getCallerId());
            int saveResult= messageTemplateMapper.editTemplateInfo(vo);
            if (saveResult > 0) {
                response.setCode(InvokeResult.SUCCESS.getCode());
                response.setMessage(InvokeResult.SUCCESS.getMessage());
                response.setResult("保存成功");
            } else {
                response.setCode(InvokeResult.BUSINESS_ERROR.getCode());
                response.setMessage(InvokeResult.BUSINESS_ERROR.getMessage());
                response.setResult("保存失败");
                logger.info("修改模板启用状态失败！");
            }
        } catch (Exception e) {
            logger.error("修改模板信息异常,接口返回："+e);
        }
        return  response;
    }

    @Override
    public TemplateCheckListResponse getTemplateCheckList(@RequestParam(value = "templateStatus",required = false)Integer templateStatus,
                                                          @RequestParam(value = "businessId",required = false) Integer businessId,
                                                          @RequestParam(value = "messageKind",required = false) Integer messageKind,
                                                          @RequestParam(value = "departmentId",required = false)Integer department,
                                                          @RequestParam("pageNum")Integer pageNum,
                                                          @RequestParam("pageSize")Integer pageSize,
                                                          @RequestParam(value = "templateAlias",required = false)String templateAlias) {
        TemplateCheckListResponse response=new TemplateCheckListResponse();
        logger.info("获取查看模板列表,入参：templateStatus="+ templateStatus+",businessId="+ businessId+",messageKind="+ messageKind+",department="+ department+",templateAlias="+templateAlias);

        Page page=new Page(pageNum,pageSize);

        try {
            List<TemplateCheckVo> list=messageTemplateMapper.getTemplateCheckList(templateStatus,businessId,messageKind,department,page,templateAlias);

            if (list !=null && !list.isEmpty()){
                int total=messageTemplateMapper.getTemplateCheckCount(templateStatus,businessId,messageKind,department);
                response.setCode(InvokeResult.SELECT_SUCCESS.getCode());
                response.setMessage(InvokeResult.SELECT_SUCCESS.getMessage());
                response.setTotal(total);
                response.setResult(list);

            }else {
                response.setCode(InvokeResult.SELECT_SUCCESS.getCode());
                response.setMessage(InvokeResult.SELECT_SUCCESS.getMessage());
                response.setResult(new ArrayList<>());
                response.setTotal(0);
                logger.info("没有查到符合条件的数据");
            }
        } catch (Exception e) {
            logger.error("查看模板列表异常,接口返回："+e);
        }
        return response;
    }
}
