/*
 * @(#)CollectionUtil.java 1.0 2013-3-22上午10:55:49
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.uc.util;

import java.util.List;

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
 * @author andrew
 * @version 1.0
 * @since 2013-3-22 上午10:55:49 
 * 
 */
public class CollectionUtil {
	
	/**
	 * 逗号分隔 List
	* @Description: 
	* @param list
	* @return
	* @return String
	* @throws
	 */
	public static String printStringList(List<?> list) {
		if (null == list) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (Object obj : list) {
			sb.append(null==obj ? "null" : obj.toString() + ",");
		}

		String str = sb.toString();
		if(str.endsWith(",")){
			str = str.substring(0, str.length()-1);
		}
		
		return str;
	}
}
