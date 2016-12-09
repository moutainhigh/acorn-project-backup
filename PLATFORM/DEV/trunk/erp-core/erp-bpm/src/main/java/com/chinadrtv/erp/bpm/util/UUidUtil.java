/*
 * @(#)UUidUtil.java 1.0 2013-2-21下午2:08:08
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.bpm.util;

import java.util.Date;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-2-21 下午2:08:08
 * 
 */
public class UUidUtil {

	private UUidUtil() {
	}

	/***
	 * 生成uuid码
	 * 
	 * @Description: TODO
	 * @return
	 * @return String
	 * @throws
	 */
	public static synchronized String getUUid()

	{
		if (generateCount > 999999999)

			generateCount = 0;

		String uniqueNumber = DateTimeUtil.sim.format(new Date())
				+ Integer.toString(generateCount);

		generateCount++;

		return uniqueNumber;

	}

	private static final int MAX_GENERATE_COUNT = 999999999;

	private static int generateCount = 0;

}
