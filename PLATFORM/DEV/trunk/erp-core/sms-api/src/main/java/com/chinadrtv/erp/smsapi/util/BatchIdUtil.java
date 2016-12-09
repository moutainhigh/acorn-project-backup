/*
 * @(#)BatchIdUtil.java 1.0 2013-2-21下午2:14:24
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.smsapi.util;

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
	 * 生成BatchId码
	 * 
	 * @Description: TODO
	 * @return
	 * @return String
	 * @throws
	 */
	public static synchronized String getBatchId()

	{// 计算出前面有几个0
		String batchId = "";
		int wei = 9 - ("" + batchCount).length();
		for (int i = 0; i < wei; i++) {
			batchId = batchId + "0";
		}
		batchId = batchId + batchCount;
		if (batchCount > 99999999) {
			batchCount = 0;
		}
		String uniqueNumber = DateTimeUtil.sim.format(new Date()) + batchId;

		batchCount++;

		return uniqueNumber;

	}

	private static int batchCount = 1;

}
