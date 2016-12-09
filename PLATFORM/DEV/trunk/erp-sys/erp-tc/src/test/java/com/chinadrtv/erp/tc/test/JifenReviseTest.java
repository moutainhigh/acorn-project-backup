/*
 * @(#)ShipmentTest.java 1.0 2013-2-20上午9:27:08
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.tc.test;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.chinadrtv.erp.tc.core.service.PvService;

/**
 * @author taoyawei
 * @version 1.0
 * @since 2013-2-20 上午9:27:08
 * 
 */
public class JifenReviseTest extends TCTest {

	@Autowired
	private PvService pvService;

	/**
	 * 积分校正
	 */
	// @Test
	public void conponRevise() {
		try {
			// 参数{"sorderid":"9913498969","scrusr":"sa"}
			Map<String, Object> integarl = new HashMap<String, Object>();
			integarl.put("sorderid", "49996573744");
			integarl.put("scrusr", "sa");
			pvService.getJifenproc(integarl);
			System.out.print("积分校正成功");
		} catch (Exception e) {
			System.out.print("积分校正失败" + e.getMessage());
		}
	}

}
