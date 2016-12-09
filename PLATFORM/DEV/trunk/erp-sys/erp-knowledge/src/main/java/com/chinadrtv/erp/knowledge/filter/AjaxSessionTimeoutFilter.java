package com.chinadrtv.erp.knowledge.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AjaxSessionTimeoutFilter implements Filter {

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		// 判断session里是否有用户信息
		if (req.getSession().getAttribute("SPRING_SECURITY_CONTEXT") == null){
			// 如果是ajax请求响应头会有，x-requested-with；
			if (req.getHeader("x-requested-with") != null && req.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){
				res.setStatus(911);//表示session timeout
			}else{
				chain.doFilter(req, res);
			}
		}else{
			chain.doFilter(req, res);
		}
	}

	public void init(FilterConfig chain) throws ServletException {

	}
}
