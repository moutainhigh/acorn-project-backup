/**
 * Copyright (c) 2013, Acorn and/or its affiliates. All rights reserved.
 * ACORN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.chinadrtv.erp.marketing.core.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 2013-7-16 下午8:23:36
 * @version 1.0.0
 * @author yangfei
 *
 */
public class InteractionMapping {
	public static Map<String, Long> InteractionResultCode2priority = new HashMap<String, Long>();
	public static Map<String, Long> InteractionResultCode2leadPriority = new HashMap<String, Long>();
	public static Map<String, Long> InteractionResultCode2leadStatus = new HashMap<String, Long>();
	public static Map<String, String> InteractionResultCode2description = new HashMap<String, String>();
	
	static {
		InteractionResultCode2priority.put("0", 0L);
		InteractionResultCode2priority.put("1", 1L);
		InteractionResultCode2priority.put("2", 2L);
		InteractionResultCode2priority.put("3", 5L);
		InteractionResultCode2priority.put("4", 6L);
		InteractionResultCode2priority.put("5", 7L);
		InteractionResultCode2priority.put("6", 7L);
		InteractionResultCode2priority.put("7", 8L);
		InteractionResultCode2priority.put("8", 9L);
		
		InteractionResultCode2leadStatus.put("0", 1L);
		InteractionResultCode2leadStatus.put("1", 3L);
		InteractionResultCode2leadStatus.put("2", 4L);
		InteractionResultCode2leadStatus.put("7", 4L);
		InteractionResultCode2leadStatus.put("8", 5L);
		
		InteractionResultCode2leadPriority.put("0", 0L);
		InteractionResultCode2leadPriority.put("1", 1L);
		InteractionResultCode2leadPriority.put("2", 2L);
		InteractionResultCode2leadPriority.put("3", 0L);
		InteractionResultCode2leadPriority.put("4", 0L);
		InteractionResultCode2leadPriority.put("5", 0L);
		InteractionResultCode2leadPriority.put("6", 0L);
		InteractionResultCode2leadPriority.put("7", 2L);
		InteractionResultCode2leadPriority.put("8", 2L);
		
		InteractionResultCode2description.put("0", "骚扰,加黑");
		InteractionResultCode2description.put("1", "断线,通话时长小于20s");
		InteractionResultCode2description.put("2", "咨询");
		InteractionResultCode2description.put("3", "订单查询");
		InteractionResultCode2description.put("4", "售后事件");
		InteractionResultCode2description.put("5", "订单修改");
		InteractionResultCode2description.put("6", "订单取消");
		InteractionResultCode2description.put("7", "预约");
		InteractionResultCode2description.put("8", "新增订单");
	};
}
