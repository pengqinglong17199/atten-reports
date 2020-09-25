package com.quanroon.atten.reports.api.common;

import com.alibaba.fastjson.JSONObject;
import com.quanroon.atten.commons.base.BaseApiController;
import com.quanroon.atten.commons.config.JWTConfiguration;
import com.quanroon.atten.commons.enums.RepReturnCodeEnum;
import com.quanroon.atten.commons.exception.TokenValidException;
import com.quanroon.atten.commons.jwt.IJWTInfo;
import com.quanroon.atten.commons.jwt.JWTHelper;
import com.quanroon.atten.commons.utils.StringUtils;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;


/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @content 独立服务API鉴权
 * @date 2020/6/24 11:15
 */
@Slf4j
public class ReportApiInterceptor implements HandlerInterceptor {
    // 要鉴定企业token的url
    private String[] COMPANY_TOKEN = {"/sys/token/proj", "/sys/city/params", "/sys/result/query", "/proj/upload", "/proj/config"};
    @Autowired
    private JWTConfiguration jwtConfiguration;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //默认项目key和token
        String tokenHeader = jwtConfiguration.getProjTokenHeader();
        String key = BaseApiController.PROJ_INFO;

        //根据url来鉴定是企业token的url
        String url = request.getRequestURI();
        for (String patten: COMPANY_TOKEN) {
            if(url.contains(patten)){
                tokenHeader = jwtConfiguration.getCompanyTokenHeader();
                key = BaseApiController.COMPANY_INFO;
                break;
            }
        }
        // 获取token
        String token = request.getHeader(tokenHeader);
        if(StringUtils.isEmpty(token)){
            GlobalExceptionHandler.tokenResponseException(response, new TokenValidException(RepReturnCodeEnum.REPORT_RETURN_100099));
            return false;
        }
        try {
            // 解析token
            IJWTInfo info = JWTHelper.getInfoFromToken(token, jwtConfiguration.getPubKey());
            request.setAttribute(key, info);
        }catch (ExpiredJwtException e){
            log.error("token过期", e);
            GlobalExceptionHandler.tokenResponseException(response, new TokenValidException(RepReturnCodeEnum.REPORT_RETURN_100098));
            return false;
        }catch (Exception e){
            log.error("token异常", e);
            GlobalExceptionHandler.tokenResponseException(response, new TokenValidException(RepReturnCodeEnum.REPORT_RETURN_100097));
            return false;
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
