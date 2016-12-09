/*
 * @(#)CacheAspect.java 1.0 2013-4-12下午4:06:26
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.user.aop;

import javax.inject.Named;
import javax.inject.Singleton;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinadrtv.erp.core.aop.BaseAspect;
import com.chinadrtv.erp.user.util.PermissionHelper;

/**
 * <dl>
 *    <dt><b>Title:行级数据权限annotation AOP</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:行级数据权限annotation AOP</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-4-12 下午4:06:26 
 * 
 */
@Aspect
@Singleton
@Named
public class RowAuthAspect extends BaseAspect {

	private static final Logger logger = LoggerFactory.getLogger(RowAuthAspect.class);
	
//	private final static String MUTEX = "_ROWS_";
//	private final static int DEFAULT_MUTEX_WAIT = 200;

	public RowAuthAspect() {
		order = -100;
	}

	@Around("execution(public * *(..)) and @annotation(rowAuth)")
	public Object get(ProceedingJoinPoint jp, RowAuth rowAuth)
			throws Throwable {
//		Map<String, Object> context = buildContext(jp);
//		String namespace = ExpressionUtils.evalString(rowAuth.where(),
//				context);
//		String key = ExpressionUtils.evalString(rowAuth.where(), context);
//		if (key == null || isBypass())
//			return jp.proceed();
//		String keyMutex = MUTEX + key;
		logger.info("执行行级拦截");
		PermissionHelper.setWhereCondition(rowAuth.depth());
		
		Object result = jp.proceed();
//		putReturnValueIntoContext(context, result);
		PermissionHelper.removeThreadLocal();
		return result;
	}

}