/*
 * @(#)PojoUtils.java 1.0 2013-2-25上午10:06:16
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.tc.core.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;

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
 * @since 2013-2-25 上午10:06:16 
 * 
 */
@SuppressWarnings(value={"unchecked", "rawtypes"})
public class PojoUtils {

	/**
	 * 将Bean转换为Dto
	* @Description: 
	* @param pojoList
	* @param class_
	* @return List
	* @throws
	 */
	public static List convertPojoList2DtoList(List<?> pojoList, Class class_) {
		List dtoList = new ArrayList();
		for (Object obj : pojoList) {
			Object dto = convertPojo2Dto(obj, class_);
			dtoList.add(dto);
		}
		return dtoList;
	}

	/**
	 * 将Dto转换为Bean
	* @Description: 
	* @param obj
	* @param class_
	* @return Object
	* @throws
	*/ 
	public static Object convertPojo2Dto(Object obj, Class class_) {
		Object dto = null;
		try {
			dto = class_.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		BeanUtils.copyProperties(obj, dto, class_);
		return dto;
	}

	/**
	 * 将Bean转换为HashMap
	* @Description: 
	* @param obj
	* @return Map<String,Object>
	* @throws
	 */
	public static Map<String, Object> convertPojo2Map(Object obj){
		Map<String, Object> parameter = new HashMap<String, Object>();
		Field[] fields = obj.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			String fieldName = fields[i].getName();
			Object o = null;
			String firstLetter = fieldName.substring(0, 1).toUpperCase();
			String getMethodName = "get" + firstLetter + fieldName.substring(1);
			Method getMethod;
			try {
				getMethod = obj.getClass().getMethod(getMethodName, new Class[] {});
				o = getMethod.invoke(obj, new Object[] {});
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (o != null) {
				parameter.put(fieldName, o);
			}
		}
		
		Field[] pfields = obj.getClass().getSuperclass().getDeclaredFields();
		for (int i = 0; i < pfields.length; i++) {
			String fieldName = pfields[i].getName();
			Object o = null;
			String firstLetter = fieldName.substring(0, 1).toUpperCase();
			String getMethodName = "get" + firstLetter + fieldName.substring(1);
			Method getMethod;
			try {
				getMethod = obj.getClass().getSuperclass().getMethod(getMethodName, new Class[] {});
				o = getMethod.invoke(obj, new Object[] {});
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (o != null) {
				parameter.put(fieldName, o);
			}
		}
		return parameter;
	}
}
