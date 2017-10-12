package com.ppdai.ac.sms.contract.service.black;

import com.alibaba.fastjson.JSONObject;
import com.ctrip.framework.apollo.ConfigService;
import com.ppdai.ac.sms.contract.controller.black.BlackListService;
import com.ppdai.ac.sms.contract.dao.mapper.smsbase.BlackListMapper;
import com.ppdai.ac.sms.contract.enums.InvokeResult;
import com.ppdai.ac.sms.contract.model.vo.BlackListVo;
import com.ppdai.ac.sms.contract.request.black.BlackListRequest;
import com.ppdai.ac.sms.contract.response.BlackListResponse;
import com.ppdai.ac.sms.contract.response.CallerResponse;
import com.ppdai.ac.sms.contract.utils.Log;
import com.ppdai.ac.sms.contract.utils.Page;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by wangxiaomei02 on 2017/5/19.
 */
@RestController
public class BlackListServiceImpl implements BlackListService{
    private static final Logger logger = LoggerFactory.getLogger(BlackListServiceImpl.class);

    @Autowired
    BlackListMapper blackListMapper;

    @Autowired
    private HttpServletRequest request;

    @Override
    public BlackListResponse getBlackList(@RequestParam(value = "mobile" ,required = false) String mobile,
                                          @RequestParam(value = "beginTime",required = false) Long beginTime,
                                          @RequestParam(value = "endTime",required = false) Long endTime,
                                          @RequestParam(value = "createType",required = false) Integer createType,
                                          @RequestParam("pageNum") Integer pageNum,
                                          @RequestParam("pageSize") Integer pageSize) {
        BlackListResponse response = new BlackListResponse();
        logger.info("查询黑名单,入参：mobile=" + mobile + ",beginTime=" + beginTime+ ",endTime=" + endTime+ ",createType=" + createType+ ",pageNum=" + pageNum+ ",pageSize=" + pageSize);
        Page page=new Page(pageNum,pageSize);

        try {
            Timestamp bTime=beginTime == null?null :new Timestamp(beginTime);
            Timestamp eTime=beginTime == null?null :new Timestamp(endTime);
            List<BlackListVo> list = blackListMapper.getBlackListByparam(mobile,bTime,eTime,createType,page);
            if (list != null) {

                response.setCode(InvokeResult.SELECT_SUCCESS.getCode());
                response.setMessage(InvokeResult.SELECT_SUCCESS.getMessage());
                response.setResult(list);

                int total = blackListMapper.getBlackListCount(mobile,bTime,eTime,createType);
                response.setTotal(total);
            } else {
                response.setCode(InvokeResult.BUSINESS_ERROR.getCode());
                response.setMessage(InvokeResult.BUSINESS_ERROR.getMessage());
                response.setResult("没有符合条件的数据");
            }
        } catch (Exception e) {
            logger.error("查询黑名单异常，返回：", e);
            response.setCode(InvokeResult.SYS_ERROR.getCode());
            response.setMessage(InvokeResult.SYS_ERROR.getMessage());
        }
        return response;
    }

    @Override
    @Log(operationType="添加操作",operationName="添加黑名单")
    public CallerResponse saveBlackList(@ApiParam(value = "request", required = true) @RequestBody BlackListRequest blackListRequest) {
        CallerResponse response=new CallerResponse();

        logger.info("添加黑名单,入参："+ JSONObject.toJSONString(blackListRequest));
        if (StringUtils.isBlank(blackListRequest.getMobile())) {
            response.setCode(InvokeResult.PARAM_ERROR.getCode());
            response.setMessage(InvokeResult.PARAM_ERROR.getMessage());
            return response;
        }

        try {
            BlackListVo vo=new BlackListVo();
            vo.setMobile(blackListRequest.getMobile());
            vo.setRemark(blackListRequest.getRemark());
            int saveResult= blackListMapper.saveBlack(vo);
            if(saveResult>0){
                vo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                response.setCode(InvokeResult.SUCCESS.getCode());
                response.setMessage(InvokeResult.SUCCESS.getMessage());
                response.setResult(vo);
            } else{
                response.setCode(InvokeResult.BUSINESS_ERROR.getCode());
                response.setMessage(InvokeResult.BUSINESS_ERROR.getMessage());
                response.setResult("保存失败");
            }
        } catch (Exception e) {
            logger.error("添加黑名单异常，返回：", e);
            response.setCode(InvokeResult.SYS_ERROR.getCode());
            response.setMessage(InvokeResult.SYS_ERROR.getMessage());
        }

        return response;
    }

    @Override
    @Log(operationType="添加操作",operationName="批量添加黑名单")
    public CallerResponse handleFileUpload(@RequestParam("file") MultipartFile file) {
        CallerResponse response = new CallerResponse();
        logger.info("批量添加黑名单 file ：" + file);
        if (file.isEmpty()) {
            response.setCode(InvokeResult.PARAM_ERROR.getCode());
            response.setMessage(InvokeResult.PARAM_ERROR.getMessage());
            return response;
        }
        // 判断文件是否为空
        if (!file.isEmpty()) {
            //判断文件大小小于5M,--返回的是字节长度，1M=1024k=1048576字节
            long fileSize = file.getSize();
            if (fileSize > 5 * 1048576) {
                logger.info("批量添加黑名单失败，文件大小超过限制：文件大小：" + file.getSize() + "");
                response.setCode(InvokeResult.BUSINESS_ERROR.getCode());
                response.setMessage(InvokeResult.BUSINESS_ERROR.getMessage());
                return response;
            } else {
                List<BlackListVo> blackList = new ArrayList<>();
                try {
                    // 文件保存路径
                    String filePath = request.getSession().getServletContext().getRealPath("/") + file.getOriginalFilename();
                    // 转存文件
                    file.transferTo(new File(filePath));

                    InputStreamReader isr = new InputStreamReader(new FileInputStream(filePath), "gb2312");
                    BufferedReader reader = new BufferedReader(isr);
                    String lineTxt= reader.readLine();//第一行信息，为标题信息，不用,如果需要，注释掉

                    while ((lineTxt = reader.readLine()) != null) {
                        String item2[] = lineTxt.split(",");
                        BlackListVo vo = new BlackListVo();
                        vo.setMobile(item2[0]);
                        vo.setRemark(item2[1]);
                        logger.info("批量添加黑名单 mobile：" + vo.getMobile() + ",remark:" + vo.getRemark());
                        blackList.add(vo);
                    }
                    isr.close();
                    if ( !blackList.isEmpty()) {
                        int saveResult = blackListMapper.batchSaveBlack(blackList);
                        if (saveResult > 0) {
                            response.setCode(InvokeResult.SUCCESS.getCode());
                            response.setMessage(InvokeResult.SUCCESS.getMessage());
                            response.setResult("保存成功!");
                            logger.info("批量保存黑名单成功！");
                        } else {
                            response.setCode(InvokeResult.BUSINESS_ERROR.getCode());
                            response.setMessage(InvokeResult.BUSINESS_ERROR.getMessage());
                            response.setResult("保存失败");
                            logger.info("批量保存黑名单异常失败！");
                        }
                    }

                } catch (Exception e) {
                    logger.error("批量保存黑名单异常，返回：", e);
                    response.setCode(InvokeResult.SYS_ERROR.getCode());
                    response.setMessage(InvokeResult.SYS_ERROR.getMessage());

                }
            }
        }
        return response;
    }

    @Override
    @Log(operationType="修改操作",operationName="修改黑名单")
    public CallerResponse editBlackList(@PathVariable("listId") Integer listId,@ApiParam(value = "request", required = true) @RequestBody BlackListRequest request) {
        CallerResponse response=new CallerResponse();
        logger.info("修改黑名单入参 ："+ JSONObject.toJSONString(request));
        if (listId ==null || StringUtils.isBlank(request.getMobile())) {
            response.setCode(InvokeResult.PARAM_ERROR.getCode());
            response.setMessage(InvokeResult.PARAM_ERROR.getMessage());
            return response;
        }
        try {
            BlackListVo blackListVo=blackListMapper.findBlackListByMobile(request.getMobile(),listId);
            if(blackListVo !=null){
                response.setCode(InvokeResult.MOBILE_EXISTS.getCode());
                response.setMessage(InvokeResult.MOBILE_EXISTS.getMessage());
                response.setResult("修改失败");
                logger.info("手机号已存在，不能做修改！");
            }else {
                BlackListVo vo = new BlackListVo();
                vo.setListId(listId);
                vo.setMobile(request.getMobile());
                vo.setRemark(request.getRemark());
                int editResult = blackListMapper.editBlackList(vo);
                if (editResult > 0) {
                    response.setCode(InvokeResult.SUCCESS.getCode());
                    response.setMessage(InvokeResult.SUCCESS.getMessage());
                    response.setResult("修改成功!");
                } else {
                    response.setCode(InvokeResult.BUSINESS_ERROR.getCode());
                    response.setMessage(InvokeResult.BUSINESS_ERROR.getMessage());
                    response.setResult("修改失败");
                    logger.info("修改黑名单异常失败！");
                }
            }
        } catch (Exception e) {
            logger.error("修改黑名单异常，返回：", e);
            response.setCode(InvokeResult.FAIL.getCode());
            response.setMessage(InvokeResult.FAIL.getMessage());
            response.setResult("修改失败");
        }
        return response;
    }

    @Override
    @Log(operationType="删除操作",operationName="删除黑名单")
    public CallerResponse delBlackList(@PathVariable("listId") Integer listId) {
        CallerResponse response=new CallerResponse();
        logger.info("删除黑名单入参 listId=："+ listId);
        if (listId ==null) {
            response.setCode(InvokeResult.PARAM_ERROR.getCode());
            response.setMessage(InvokeResult.PARAM_ERROR.getMessage());
            return response;
        }
        try {
            int delResult=blackListMapper.delBlackList(listId);
            if(delResult>0){
                response.setCode(InvokeResult.SUCCESS.getCode());
                response.setMessage(InvokeResult.SUCCESS.getMessage());
                response.setResult("删除成功!");
            }else{
                response.setCode(InvokeResult.BUSINESS_ERROR.getCode());
                response.setMessage(InvokeResult.BUSINESS_ERROR.getMessage());
                response.setResult("修改失败");
                logger.info("删除黑名单异常失败！");
            }
        } catch (Exception e) {
            logger.error("删除黑名单异常，返回：", e);
            response.setCode(InvokeResult.SYS_ERROR.getCode());
            response.setMessage(InvokeResult.SYS_ERROR.getMessage());
        }
        return response;
    }


}
