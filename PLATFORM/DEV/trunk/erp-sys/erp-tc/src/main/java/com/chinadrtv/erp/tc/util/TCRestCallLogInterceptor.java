package com.chinadrtv.erp.tc.util;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-2-26
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class TCRestCallLogInterceptor extends HandlerInterceptorAdapter {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger( TCRestCallLogInterceptor.class);
    private final String headerToken="TC_CALL_STAMP";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        try
        {
            //记录调用日志
            logger.info(this.getIp(request) +":"+request.getRequestURI());

            String token=request.getHeader(headerToken);
            if(token!=null)
            {
                logger.info(token);
                response.addHeader(headerToken,token);
            }
        }
        catch (Exception exp)
        {

        }
        return true;
    }

    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
        /*String token=request.getHeader(headerToken);
        if(token!=null)
        {
            response.setHeader(headerToken,token);
        }*/
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        /*String token=request.getHeader(headerToken);
        if(token!=null)
        {
            response.setHeader(headerToken,token);
        } */
    }

    private String getIp(HttpServletRequest request)
    {
        String strIp="";
        String forwarded=request.getHeader("x-forwarded-for");
        if(StringUtils.isNotEmpty(forwarded))
        {
            strIp+="forward ip:";
            strIp+=forwarded;
            strIp+=";";
        }

        String real=request.getHeader("x-real-ip");
        if(StringUtils.isNotEmpty(real))
        {
            strIp+="real ip:";
            strIp+=real;
            strIp+=";";
        }
        strIp+= request.getRemoteAddr();
        return strIp;
    }
}
