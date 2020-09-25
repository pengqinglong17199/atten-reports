package com.quanroon.atten.reports.api.common;

import com.alibaba.fastjson.JSONObject;
import com.quanroon.atten.commons.base.AjaxResult;
import com.quanroon.atten.commons.enums.RepReturnCodeEnum;
import com.quanroon.atten.commons.exception.ArgNotValidException;
import com.quanroon.atten.commons.exception.BusinessException;
import com.quanroon.atten.commons.exception.TokenValidException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @content Controller层全局异常处理
 * @date 2020/6/24 17:04
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理所有不可知的异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public AjaxResult exceptionHandler(Exception e) {
        log.error("发生未知错误，原因：{}",e.toString());
        e.printStackTrace();//打印出stackTrace
        AjaxResult ajaxResult = new AjaxResult();
        if(e instanceof org.springframework.http.converter.HttpMessageNotReadableException){
            ajaxResult.put("message", RepReturnCodeEnum.REPORT_RETURN_100004.getMessage());
            ajaxResult.put("retCode", RepReturnCodeEnum.REPORT_RETURN_100004.getRetCode());
        }else {
            ajaxResult.put("message", RepReturnCodeEnum.REPORT_RETURN_999999.getMessage());
            ajaxResult.put("retCode", RepReturnCodeEnum.REPORT_RETURN_999999.getRetCode());
        }
        return ajaxResult;
    }

    /**
     * 处理所有业务异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public AjaxResult handleBusinessException(BusinessException e){
        log.error("业务处理错误，原因：{}",e.getMessage());
        AjaxResult ajaxResult = new AjaxResult();
        if(e.getCodeEnum() == null){
            ajaxResult.put("message", RepReturnCodeEnum.REPORT_RETURN_777777.getMessage());
            ajaxResult.put("retCode", RepReturnCodeEnum.REPORT_RETURN_777777.getRetCode());
        }else{
            ajaxResult.put("message", e.getCodeEnum().getMessage());
            ajaxResult.put("retCode", e.getCodeEnum().getRetCode());
        }
        return ajaxResult;
    }

    /**
     * 处理自定义数据验证异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = {ArgNotValidException.class})
    @ResponseBody
    public AjaxResult handleMethodArgumentNotValidException(ArgNotValidException e){
        log.error("参数验证错误，原因：{}",e.getMessage());
        AjaxResult ajaxResult = new AjaxResult();
        if(e.getCodeEnum() == null){
            ajaxResult.put("message", RepReturnCodeEnum.REPORT_RETURN_100001.getMessage());
            ajaxResult.put("retCode", RepReturnCodeEnum.REPORT_RETURN_100001.getRetCode());
        }else {
            ajaxResult.put("message", e.getMessage());//自定义消息
            ajaxResult.put("retCode", e.getCodeEnum().getRetCode());
        }
        return ajaxResult;
    }

    /**
     * 处理所有接口数据校验异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = {BindException.class
            ,MethodArgumentNotValidException.class})
    @ResponseBody
    public AjaxResult handleValidException(Exception e){
        log.error("参数校验错误，原因：{}",e.getMessage());
        AjaxResult ajaxResult = new AjaxResult();
        BindingResult result = null;
        if (e instanceof BindException) {//请求参数为form表单
            BindException exception = (BindException) e;
            result = exception.getBindingResult();
        }else if(e instanceof MethodArgumentNotValidException) {//请求参数为json
            MethodArgumentNotValidException exception = (MethodArgumentNotValidException) e;
            result = exception.getBindingResult();
        }
        Map<String, Object> maps;
        if ( result != null && result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            maps = new HashMap<>(fieldErrors.size());
            fieldErrors.forEach(error -> {
                maps.put(error.getField(), error.getDefaultMessage());
            });
        } else {
            maps = Collections.EMPTY_MAP;
        }
        ajaxResult.put("result", maps);
        ajaxResult.put("message", RepReturnCodeEnum.REPORT_RETURN_100003.getMessage());
        ajaxResult.put("retCode", RepReturnCodeEnum.REPORT_RETURN_100003.getRetCode());
        return ajaxResult;
    }

    /**
     * 处理token校验异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = TokenValidException.class)
    @ResponseBody
    public AjaxResult handleTokenValidException(TokenValidException e){
        log.error("请求token校验处理错误，原因：{}",e.getMessage());
        AjaxResult ajaxResult = new AjaxResult();
        if(e.getCodeEnum() == null){
            ajaxResult.put("message", RepReturnCodeEnum.REPORT_RETURN_200011.getMessage());
            ajaxResult.put("retCode", RepReturnCodeEnum.REPORT_RETURN_200011.getRetCode());
        }else{
            ajaxResult.put("message", e.getCodeEnum().getMessage());
            ajaxResult.put("retCode", e.getCodeEnum().getRetCode());
        }
        return ajaxResult;
    }

    /**
     * token异常响应
     * @param response
     * @param tokenValid
     */
    public static void tokenResponseException(HttpServletResponse response, TokenValidException tokenValid){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null ;
        try{
            JSONObject res = new JSONObject();
            res.put("retCode", tokenValid.getCodeEnum().getRetCode());
            res.put("message", tokenValid.getCodeEnum().getMessage());
            out = response.getWriter();
            out.append(res.toString());
        }catch (Exception e){
            e.printStackTrace();
            try {
                response.sendError(500,"未知错误");
            }catch (Exception e2){
                e2.printStackTrace();
            }
        }
    }
}
