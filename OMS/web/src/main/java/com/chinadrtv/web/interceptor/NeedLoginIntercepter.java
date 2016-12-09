package com.chinadrtv.web.interceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.chinadrtv.web.annotation.NeedLogin;


public class NeedLoginIntercepter extends HandlerInterceptorAdapter {

    /** 
     * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        Annotation a = method.getAnnotation(NeedLogin.class);
        if (a != null) {
            //需要登录验证
        }
        return super.preHandle(request, response, handler);
    }

}
