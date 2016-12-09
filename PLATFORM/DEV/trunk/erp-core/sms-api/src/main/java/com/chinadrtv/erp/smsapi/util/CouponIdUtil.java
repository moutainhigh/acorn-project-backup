/*
 * @(#)CouponIdUtil.java 1.0 2013-8-14下午2:56:18
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.smsapi.util;

import java.security.SecureRandom;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-8-14 下午2:56:18
 * 
 */
public class CouponIdUtil {
	private CouponIdUtil() {
	}

	/***
	 * 生成uuid码
	 * 
	 * @Description: TODO
	 * @return
	 * @return String
	 * @throws
	 */
	public static synchronized String getCouponId()

	{
		SecureRandom random;
		try {
			random = SecureRandom.getInstance("SHA1PRNG", "SUN");
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException(
					"the secure random algorithm is not supported:SHA1PRNG");
		}
		int val = Math.abs(random.nextInt());
		return "" + val;

	}

	public static void main(String[] args) {
		System.out.println(CouponIdUtil.getCouponId());

	}

}
