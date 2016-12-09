/*
 * @(#)DateTimeUtil.java 1.0 2013-2-19下午4:54:02
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.bpm.util;

import java.text.SimpleDateFormat;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-2-19 下午4:54:02
 * 
 */
public class DateTimeUtil {

	public static SimpleDateFormat sim = new SimpleDateFormat(
			"yyyyMMdd24HHmmss");
	public static SimpleDateFormat sim2 = new SimpleDateFormat("yyyyMMdd");
	public static SimpleDateFormat sim3 = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

}
