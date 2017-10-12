package com.ppdai.ac.sms.contract.utils;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.ppdai.ac.sms.contract.controller.permission.PermissionService;
import com.ppdai.ac.sms.contract.dao.mapper.smsbase.ActionLogMapper;
import com.ppdai.ac.sms.contract.model.entity.ActionLogDTO;
import com.ppdai.ac.sms.contract.model.vo.SecurityAuthorityVo;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Created by wangxiaomei02 on 2017/6/27.
 * 切点类
 */
@Aspect
@Component
public class LogAspect {

    private String paramStr="userid";

    Config config= ConfigService.getAppConfig();
    String system_appId=config.getProperty("system_appId","false");
    String system_IsProduct=config.getProperty("system_IsProduct","false");

//    @Value("${system_IsProduct}")
//    String system_IsProduct;

    @Autowired
    ActionLogMapper actionLogMapper;

    @Autowired
    PermissionService permissionService;


    private  static  final Logger logger = LoggerFactory.getLogger(LogAspect. class);

    //Controller层切点
    @Pointcut("execution (* com.ppdai.ac.sms.contract.service..*.*(..))")
    public  void controllerAspect() {}

    /**
     * 获取头信息
     *
     * @param request
     * @return
     * @throws IOException
     */
    @SuppressWarnings("rawtypes")
    private String getHeaders(HttpServletRequest request)
            throws AdmissionException {
             String userid="";
            Enumeration headerNames = request.getHeaderNames();
            if (headerNames.hasMoreElements()) {
                while (headerNames.hasMoreElements()) {
                    String name = (String) headerNames.nextElement();
                    /**
                     * 只输出关注的header中userId信息
                     */
                    if (paramStr.contains(name)) {
                        userid=request.getHeader(name);
                        break;
                    }
                }
            }
        return userid;
    }

    /**
     * 根据userId获取CAS登录用户信息
     * @return
     */
    private  SecurityAuthorityVo getUserInfo(String userId) throws AdmissionException {
        SecurityAuthorityVo vo=new SecurityAuthorityVo();
        logger.info("CAS 拉取用户数据入参 userid："+userId+" || system_appId:"+system_appId+" || system_IsProduct:"+system_IsProduct);
        try {
            String result = permissionService.load(system_appId, userId, system_IsProduct);
            logger.info("CAS 拉去用户数据返回result："+result);
            if ("0".equals(result)) {
                throw  new AdmissionException("拉取用户数据失败");
            } else {
                /*
                result=="{\"isAdmin\":false,\"realName\":\"王小梅\",\"rightIds\":[\"170511101612\",\"170511101841\"]}"
                转义前需要去除头尾的“” 和 中间的\符号
                 */
                String str = result.substring(1, result.length() - 1);
                String result2 = str.replace("\\\"", "\"");
                /**
                 * json字符串转对象--字符串格式||{"isAdmin":false,"realName":"王小梅","rightIds":["170511101612","170511101841"]}
                 */
                JSONObject jsonobject = JSONObject.fromObject(result2);
                vo = (SecurityAuthorityVo) JSONObject.toBean(jsonobject, SecurityAuthorityVo.class);
                if(vo.getUserId() ==null || StringUtils.isBlank(vo.getRealName())){
                    throw new AdmissionException("根据userId查询CAS用户信息失败！");
                }
            }
        } catch (Exception e) {
            logger.error("根据userId查询CAS用户信息失败！"+e);
            throw  new AdmissionException("拉取用户数据失败");
        }

        return  vo;
    }

    /**
     * 前置通知 用于拦截Controller层记录用户的操作
     *
     * @param joinPoint 切点
     */
    @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint) throws AdmissionException {
        MethodSignature sign = (MethodSignature) joinPoint.getSignature();
        Method method = sign.getMethod();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        if (!"Hs".equals(method.getName())) {
            String userId = this.getHeaders(request);
                if (StringUtils.isBlank(userId)) {
                    logger.info("userId不能为空");
                    throw new AdmissionException("userId不能为空");
                } else {
                    if (!method.getName().contains("get")) {//查询操作不做日志记录
                        try {
                            String targetName = joinPoint.getTarget().getClass().getName();
                            String methodName = joinPoint.getSignature().getName();
                            Object[] arguments = joinPoint.getArgs();
                            Class targetClass = Class.forName(targetName);
                            Method[] methods = targetClass.getMethods();

                            for (Method method2 : methods) {
                                if (method.getName().equals(methodName)) {
                                    Class[] clazzs = method2.getParameterTypes();
                                    if (clazzs.length == arguments.length && method2.getAnnotation(Log.class) != null) {
                                        ActionLogDTO dto = new ActionLogDTO();
                                        dto.setAction(method2.getAnnotation(Log.class).operationType());
                                        dto.setActionContent(method2.getAnnotation(Log.class).operationName());
                                        SecurityAuthorityVo vo = this.getUserInfo(userId);
                                        dto.setUserId(vo.getJobNumber() == null ?0:Integer.valueOf(vo.getJobNumber()));
                                        dto.setUserName(vo.getRealName());
                                        actionLogMapper.saveActionlog(dto);
                                        logger.info("日志记录成功！");
                                        break;
                                    }
                                }
                            }

                        } catch (Exception e) {
                            logger.error("登陆验证失败,异常,接口返回：" + e);
                            throw new AdmissionException("登陆校验失败");
                        }
                    }
                }
        }
    }


}
