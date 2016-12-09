/*
 * @(#)BatchIdUtil.java 1.0 2013-2-21下午2:14:24
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
 * @since 2013-2-21 下午2:14:24
 * 
 */
public class BatchIdUtil {
	private BatchIdUtil() {
	}

	/***
	 * 生成uuid码
	 * 
	 * @Description: TODO
	 * @return
	 * @return String
	 * @throws
	 */
	public static synchronized String getBatchId()

	{
		if (batchCount > 99999999)

			batchCount = 0;

		String uniqueNumber = DateTimeUtil.sim.format(new Date())
				+ Integer.toString(batchCount);

		batchCount++;

		return uniqueNumber;

	}

	private static final int MAX_BATCH_COUNT = 99999999;

	private static int batchCount = 0;

}
