package com.ppdai.ac.sms.contract.service.messagebusiness;

import com.alibaba.fastjson.JSONObject;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.ppdai.ac.sms.contract.controller.messagebusiness.MessageBusinessService;
import com.ppdai.ac.sms.contract.dao.mapper.smsbase.MessageBusinessMapper;
import com.ppdai.ac.sms.contract.dao.mapper.smsbase.TemplateWorkFlowMapper;
import com.ppdai.ac.sms.contract.enums.InvokeResult;
import com.ppdai.ac.sms.contract.model.entity.MessageBusinessDTO;
import com.ppdai.ac.sms.contract.model.vo.MessageBusinessVo;
import com.ppdai.ac.sms.contract.request.messagebusiness.MessageBusinessRequest;
import com.ppdai.ac.sms.contract.response.MessageBusinessResponse;
import com.ppdai.ac.sms.contract.utils.Log;
import io.swagger.annotations.ApiParam;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by wangxiaomei02 on 2017/5/25.
 */
@RestController
public class MessageBusinessServiceImpl implements MessageBusinessService {

    private static final Logger logger = LoggerFactory.getLogger(MessageBusinessServiceImpl.class);

    @Autowired
    MessageBusinessMapper messageBusinessMapper;

    @Autowired
    TemplateWorkFlowMapper templateWorkFlowMapper;

    @ApolloConfig
    private transient Config apolloConfig;

    public String getBusinessList() {
        return apolloConfig.getProperty("businessList","");
    }

    @Override
    public MessageBusinessResponse getMessageBusinessForConfig() {
        MessageBusinessResponse response=new MessageBusinessResponse();
        try {
            String businessList=this.getBusinessList();
            if(StringUtils.isNotBlank(businessList)){
                JSONArray jsonArray = JSONArray.fromObject(businessList);//把String转换为json
                response.setCode(InvokeResult.SELECT_SUCCESS.getCode());
                response.setMessage(InvokeResult.SELECT_SUCCESS.getMessage());
                response.setResult(jsonArray);
                logger.info("查询配置业务成功："+jsonArray);
            }else{
                logger.info("查询配置业务失败");
                response.setCode(InvokeResult.SYS_ERROR.getCode());
                response.setMessage(InvokeResult.SYS_ERROR.getMessage());
                response.setResult("查询业务失败");
            }
        } catch (Exception e) {
            logger.error("查询配置业务异常，返回：", e);
            response.setCode(InvokeResult.SYS_ERROR.getCode());
            response.setMessage(InvokeResult.SYS_ERROR.getMessage());
        }

        return response;
    }


    @Override
    public MessageBusinessResponse getMessageBusinessForDB() {
        MessageBusinessResponse response=new MessageBusinessResponse();
        try {
            List<MessageBusinessVo> list=messageBusinessMapper.getMessageBusinessList();
            if(list != null){
                response.setCode(InvokeResult.SELECT_SUCCESS.getCode());
                response.setMessage(InvokeResult.SELECT_SUCCESS.getMessage());
                response.setResult(list);
                logger.info("查询数据库业务成功："+list);
            }else{
                logger.info("查询数据库业务失败");
                response.setCode(InvokeResult.SYS_ERROR.getCode());
                response.setMessage(InvokeResult.SYS_ERROR.getMessage());
                response.setResult("查询数据库业务失败");
            }
        } catch (Exception e) {
            logger.error("查询数据库业务异常，返回：", e);
            response.setCode(InvokeResult.SYS_ERROR.getCode());
            response.setMessage(InvokeResult.SYS_ERROR.getMessage());
        }
        return response;
    }


    @Override
    @Log(operationType="添加操作",operationName="添加业务")
    public MessageBusinessResponse saveMessageBusiness(@ApiParam(value = "request", required = true) @RequestBody MessageBusinessRequest request) {
        MessageBusinessResponse response=new MessageBusinessResponse();

        logger.info("添加业务类型,入参："+ JSONObject.toJSONString(request));
        if(StringUtils.isBlank(request.getBusinessAlias()) || StringUtils.isBlank(request.getBusinessName())
                || request.getTotalMaxCount() == null ||request.getMessageType()==null
                || request.getExpireSecond() == null || request.getVerifyMaxCount()==null){
            response.setCode(InvokeResult.PARAM_ERROR.getCode());
            response.setMessage(InvokeResult.PARAM_ERROR.getMessage());
            return response;
        }
        MessageBusinessDTO dto=new MessageBusinessDTO();
        dto.setBusinessName(request.getBusinessName());
        dto.setBusinessAlias(request.getBusinessAlias());
        dto.setExpireSecond(request.getExpireSecond());
        dto.setMessageType(request.getMessageType());
        dto.setTotalMaxCount(request.getTotalMaxCount());
        dto.setVerifyMaxCount(request.getVerifyMaxCount());
        dto.setExpireSecond(request.getExpireSecond());
        int saveResult=messageBusinessMapper.saveMessageBusiness(dto);
        Integer bId=dto.getBusinessId();

        if(saveResult !=0 ){
            dto.setBusinessId(bId);
            dto.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            response.setCode(InvokeResult.SUCCESS.getCode());
            response.setMessage(InvokeResult.SUCCESS.getMessage());
            response.setResult(dto);
        } else{
            response.setCode(InvokeResult.BUSINESS_ERROR.getCode());
            response.setMessage(InvokeResult.BUSINESS_ERROR.getMessage());
            response.setResult("保存失败");
        }

        return response;
    }

    @Override
    @Log(operationType="修改操作",operationName="修改业务")
    public MessageBusinessResponse editMessageBusiness(@PathVariable("businessId") Integer businessId,@ApiParam(value = "request", required = true) @RequestBody MessageBusinessRequest request) {
        MessageBusinessResponse response=new MessageBusinessResponse();

        logger.info("修改业务类型,入参："+JSONObject.toJSONString(request)+",businessId="+ businessId);
        if(businessId ==null || StringUtils.isBlank(request.getBusinessAlias()) || StringUtils.isBlank(request.getBusinessName())
                || request.getTotalMaxCount() == null ||request.getMessageType()==null || request.getExpireSecond() == null || request.getVerifyMaxCount()==null){
            response.setCode(InvokeResult.PARAM_ERROR.getCode());
            response.setMessage(InvokeResult.PARAM_ERROR.getMessage());
            return response;
        }
        try {
            MessageBusinessDTO dto=new MessageBusinessDTO();
            dto.setBusinessId(businessId);
            dto.setVerifyMaxCount(request.getVerifyMaxCount());
            dto.setTotalMaxCount(request.getTotalMaxCount());
            dto.setMessageType(request.getMessageType());
            dto.setBusinessAlias(request.getBusinessAlias());
            dto.setBusinessName(request.getBusinessName());
            dto.setExpireSecond(request.getExpireSecond());
            int editResult=messageBusinessMapper.editMessageBusiness(dto);
            if(editResult >0){
                response.setCode(InvokeResult.SUCCESS.getCode());
                response.setMessage(InvokeResult.SUCCESS.getMessage());
                response.setResult("修改成功");
            }
        } catch (Exception e) {
            logger.error("修改业务类异常，返回：", e);
            response.setCode(InvokeResult.SYS_ERROR.getCode());
            response.setMessage(InvokeResult.SYS_ERROR.getMessage());
        }
        return response;
    }

    @Override
    public MessageBusinessResponse getMessageBusinessById(@PathVariable("businessId") Integer businessId) {
        MessageBusinessResponse response=new MessageBusinessResponse();

        logger.info("查询业务类型,入参：businessId="+ JSONObject.toJSONString(businessId));
        if(businessId == null){
            response.setCode(InvokeResult.PARAM_ERROR.getCode());
            response.setMessage(InvokeResult.PARAM_ERROR.getMessage());
            return response;
        }
        MessageBusinessVo vo=messageBusinessMapper.findMessageBusinessById(businessId);
        if(vo != null){
            response.setCode(InvokeResult.SELECT_SUCCESS.getCode());
            response.setMessage(InvokeResult.SELECT_SUCCESS.getMessage());
            response.setResult(vo);
        }else {
            response.setCode(InvokeResult.SYS_ERROR.getCode());
            response.setMessage(InvokeResult.SYS_ERROR.getMessage());
            response.setResult("查询业务类型失败");
            logger.info("查询业务类型失败！");
        }
        return response;
    }

    @Override
    @Log(operationType="删除操作",operationName="删除业务")
    public MessageBusinessResponse delMessageBusiness(@PathVariable("businessId") Integer businessId) {
        MessageBusinessResponse response=new MessageBusinessResponse();
        logger.info("删除业务类型,入参：businessId="+ JSONObject.toJSONString(businessId));
        if(businessId ==null){
            response.setCode(InvokeResult.PARAM_ERROR.getCode());
            response.setMessage(InvokeResult.PARAM_ERROR.getMessage());
            return response;
        }

        try {
            int selectResult=templateWorkFlowMapper.getTemplateByparam(null,businessId);
            if(selectResult == 0){
                int delResult=messageBusinessMapper.delMessageBusiness(businessId);
                if(delResult >0){
                    response.setCode(InvokeResult.SUCCESS.getCode());
                    response.setMessage(InvokeResult.SUCCESS.getMessage());
                    response.setResult("删除成功");
                }else{
                    response.setCode(InvokeResult.SYS_ERROR.getCode());
                    response.setMessage(InvokeResult.SYS_ERROR.getMessage());
                    response.setResult("删除失败");
                }
            }else{
                response.setCode(InvokeResult.SUCCESS.getCode());
                response.setMessage(InvokeResult.SUCCESS.getMessage());
                response.setResult("该业务类型不可删除");
            }

        } catch (Exception e) {
            logger.error("删除业务类型异常，返回：", e);
            response.setCode(InvokeResult.SYS_ERROR.getCode());
            response.setMessage(InvokeResult.SYS_ERROR.getMessage());
        }
        return response;
    }


}
