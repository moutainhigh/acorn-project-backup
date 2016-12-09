/*
 * @(#)UUidUtil.java 1.0 2013-2-21下午2:08:08
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
		// 计算出前面有几个0
		String uuid = "";
		int wei = 9 - ("" + generateCount).length();
		for (int i = 0; i < wei; i++) {
			uuid = uuid + "0";
		}
		uuid = uuid + generateCount;
		if (generateCount > 999999999) {
			generateCount = 0;
		}
		String uniqueNumber = DateTimeUtil.sim.format(new Date()) + uuid;

		generateCount++;

		return uniqueNumber;

	}

	private static int generateCount = 1;

}
