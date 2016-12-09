/*
 * @(#)BaseAspect.java 1.0 2013-4-12下午3:57:33
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.core.aop;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.security.core.userdetails.UserDetails;

import com.chinadrtv.erp.util.ReflectionUtils;

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
 * @since 2013-4-12 下午3:57:33 
 * 
 */
public class BaseAspect {

	protected Logger log = LoggerFactory.getLogger(getClass());

	protected int order;

	private static boolean warnNoDebugSymbolInformation;

	protected boolean isBypass() {
		return AopContext.isBypass(this.getClass());
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	protected Map<String, Object> buildContext(JoinPoint jp) {
		Map<String, Object> context = new HashMap<String, Object>();
		Object[] args = jp.getArgs();
		String[] paramNames = ReflectionUtils.getParameterNames(jp);
		if (paramNames == null) {
			if (!warnNoDebugSymbolInformation) {
				warnNoDebugSymbolInformation = true;
				log.warn("Unable to resolve method parameter names for method: "
						+ jp.getStaticPart().getSignature()
						+ ". Debug symbol information is required if you are using parameter names in expressions.");
			}
		} else {
			for (int i = 0; i < args.length; i++)
				context.put(paramNames[i], args[i]);
		}
		if (!context.containsKey("_this"))
			context.put("_this", jp.getThis());
		if (!context.containsKey("target"))
			context.put("target", jp.getTarget());
		if (!context.containsKey("aspect"))
			context.put("aspect", this);
		if (!context.containsKey("args"))
			context.put("args", jp.getArgs());
//		if (!context.containsKey("user"))
//			context.put("user", AuthzUtils.getUserDetails(UserDetails.class));
		return context;
	}

	protected void putReturnValueIntoContext(Map<String, Object> context,
			Object value) {
		context.put("retval", value);
	}
}
