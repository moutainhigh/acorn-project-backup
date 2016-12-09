/*
 * @(#)SecurityFilter.java 1.0 2013-5-8上午10:08:16
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.user.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.chinadrtv.erp.user.util.PermissionHelper;

/**
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-5-8 上午10:08:16 
 * 
 */
public class SecurityFilter implements Filter{

	/**
	* <p>Title: destroy</p>
	* <p>Description: </p>
	* @see javax.servlet.Filter#destroy()
	*/ 
	public void destroy() {
	}

	/**
	* <p>Title: doFilter</p>
	* <p>Description: </p>
	* @param arg0
	* @param arg1
	* @param arg2
	* @throws IOException
	* @throws ServletException
	* @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	*/ 
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		PermissionHelper.setRequest((HttpServletRequest) request);
//		 PermissionHelper.setResponse((HttpServletResponse) response);
		filterChain.doFilter(request, response);
	}

	/**
	* <p>Title: init</p>
	* <p>Description: </p>
	* @param arg0
	* @throws ServletException
	* @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	*/ 
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
