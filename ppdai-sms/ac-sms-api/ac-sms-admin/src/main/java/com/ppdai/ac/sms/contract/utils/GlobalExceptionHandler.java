package com.ppdai.ac.sms.contract.utils;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wangxiaomei02 on 2017/6/30.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 捕获自定义异常，数据响应的格式数据给前端；Response Body：ex.getMessage()，Response Code：401
     * @param ex
     * @return
     */
    @ExceptionHandler(value=AdmissionException.class)
    @ResponseBody
    public ResponseEntity<String> admExp(AdmissionException ex) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-type", "text/plain;charset=UTF-8");
        headers.add("icop-content-type", "exception");
        String message = ex.getMessage();
        return new ResponseEntity<>(message, headers, HttpStatus.UNAUTHORIZED);
    }


}
