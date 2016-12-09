/*
 * @(#)PojoUtils.java 1.0 2013-2-25上午10:06:16
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.chinadrtv.erp.core.exception.BizException;

/**
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description: Bean 工具类，命名PojoUtils, 不易与  Apache BeanUtils 和 Spring BeanUtils 混淆</b></dt>
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
	 * 将POJO转换为DTO
	* @param obj
	* @param class_
	* @return Object
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
	 * <p>将DTO转换为POJO</p>
	 * @param obj
	 * @return Object
	 */
	public static Object convertDto2Pojo(Object obj, Class class_) {
		return convertPojo2Dto(obj, class_);
	}
	

	/**
	 * 将POJO List转换为DTO List
	* @param pojoList
	* @param class_
	* @return List
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
	 * 将POJO转换为HashMap
	 * @param obj
	 * @return Map<String,Object>
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


	/**
	 * <p>比较两个对象是否相同， 并返回当前对象与比较对象差异的字段的PropertyDescriptor
	 * 要调用该方法，您必需用重写PO或VO的hashcode() 方法
	 * </p>
	 * @param obj1
	 * @param obj2
	 * @return List<PropertyDescriptor>
	 */
	public static List<PropertyDescriptor> compare(Object original, Object present) {
		if(null==original || null==present){
			throw new IllegalArgumentException("比较对象不能为null");
		}
		
		List<PropertyDescriptor> propDescList = new ArrayList<PropertyDescriptor>();
		
		PropertyDescriptor[] oriPropsDescriptors = BeanUtils.getPropertyDescriptors(original.getClass());
		PropertyDescriptor[] prePropsDescriptors = BeanUtils.getPropertyDescriptors(present.getClass());
		
		for(PropertyDescriptor oriPropDescriptor : oriPropsDescriptors){
			for(PropertyDescriptor prePropsDescriptor : prePropsDescriptors){
				//oriPropDescriptor.equals(obj)
				//如果两个字段的名称、getter、setter方法相同
				Method oriReadMethod = oriPropDescriptor.getReadMethod();
				Method oriWriteMethod = oriPropDescriptor.getWriteMethod();
				Method preReadMethod = prePropsDescriptor.getReadMethod();
				Method preWriteMethod = prePropsDescriptor.getWriteMethod();
				
				if(oriPropDescriptor.getDisplayName().equals(prePropsDescriptor.getDisplayName()) &&
						compareMethods(oriReadMethod, preReadMethod) &&
						compareMethods(oriWriteMethod, preWriteMethod)){
					
					Object oriValue = null;
					Object preValue = null;
					try {
						oriValue = oriPropDescriptor.getReadMethod().invoke(original, null);
						preValue = prePropsDescriptor.getReadMethod().invoke(present, null);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
					
					if(null==oriValue && null==preValue){
						continue;
					}else if(null==oriValue && null!=preValue){
						propDescList.add(prePropsDescriptor);
					}else if(null!=oriValue && null==preValue){
						throw new BizException("传入数据有误，不能将值擦除");
					}else if(null!=oriValue && null!=preValue){
						if(!equals(oriValue, preValue)){
							propDescList.add(prePropsDescriptor);
						}
					}
				}
			}
		}
		
		return propDescList;
	}


	/**
	 * <p>判断两个对象是否相等</p>
	 * @param oriValue
	 * @param preValue
	 * @return Boolean
	 */
	private static boolean equals(Object oriValue, Object preValue) {
		if(null==oriValue && null!=preValue){
			return false;
		}
		if(null!=oriValue && null==preValue){
			return false;
		}
		if(null==oriValue && null==preValue){
			return true;
		}
		
		if(oriValue.hashCode()== preValue.hashCode()){
			return true;
		}
		
		return false;
	}


	/**
	 * <p>比较两个方法是否是同一个方法</p>
	 * @param a
	 * @param b
	 * @return Boolean
	 */
    static boolean compareMethods(Method a, Method b) {
        // Note: perhaps this should be a protected method in FeatureDescriptor
        if ((a == null) != (b == null)) {
            return false;
        }

        if (a != null && b != null) {
            if (!a.equals(b)) {
                return false;
            }
        }
        return true;
    }


	/**
	 * <p>动态调用</p>
	 * @param contactChange
	 * @param propDescList
	 */
	public static void invoke(Object source, Object target, List<PropertyDescriptor> propDescList) {
		if(null==source || null==target || null==propDescList){
			throw new IllegalArgumentException("输入参数不能为null");
		}
		
		for(PropertyDescriptor propDesc : propDescList){
			String fieldName = propDesc.getName();
			Object fieldValue = null;
			try {
				fieldValue = propDesc.getReadMethod().invoke(source, null);
				PropertyDescriptor targetPropDesc = BeanUtils.getPropertyDescriptor(target.getClass(), fieldName);
				/*if(null==targetPropDesc){
					throw new BizException("目标对象不存在输入对象的方法");
				}*/
				if(null!=targetPropDesc){
					targetPropDesc.getWriteMethod().invoke(target, fieldValue);
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			
		}
		
	}


	/**
	 * <p>获取非NULL字段的PropertyDescriptor</p>
	 * @param contactChange
	 * @return List<PropertyDescriptor>
	 */
	public static List<PropertyDescriptor> getNotNullPropertyDescriptor(Object obj) {
		if(null==obj){
			throw new IllegalArgumentException("参数不能为null");
		}
		
		List<PropertyDescriptor> propDescList = new ArrayList<PropertyDescriptor>();
		
		PropertyDescriptor[] propDescs = BeanUtils.getPropertyDescriptors(obj.getClass());
		for(PropertyDescriptor propDesc : propDescs){
			String name = propDesc.getName();
			if(null!=name && name.equals("class")){
				continue;
			}
			Method readMethod = propDesc.getReadMethod();
			//Method writeMethod = propDesc.getWriteMethod();
			try {
				Object filedValue = readMethod.invoke(obj, null);
				if(null!=filedValue){
					propDescList.add(propDesc);
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		
		return propDescList;
	}


	/**
	 * <p></p>
	 * @param rsMap
	 * @param class1
	 * @return
	 */
	public static Object convertMapToPojo(Map<String, Object> rsMap, Class<?> class1) {
		Object rsObject = null;
		try {
			rsObject = class1.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		PropertyDescriptor[] propDescs = BeanUtils.getPropertyDescriptors(class1);
		for(PropertyDescriptor pd : propDescs) {
			String filedName = pd.getName();
			Object fieldValue = rsMap.get(filedName);
			
			if(null != fieldValue) {
				try {
					pd.getWriteMethod().invoke(rsObject, fieldValue);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		
		return rsObject;
	}


	/**
	 * <p></p>
	 * @param userDtoList
	 * @param class1
	 * @return
	 */
	public static List convertDtoList2PojoList(Object[] objects, Class<?> class_) {
		List dtoList = new ArrayList();
		for(Object obj : objects) {
			Object dto = convertDto2Pojo(obj, class_);
			dtoList.add(dto);
		}
		return dtoList;
	}


	/**
	 * <p></p>
	 * @param userDto
	 * @param attribute
	 * @return
	 */
	public static String getObjectAttr(Object obj, String attribute) {
		PropertyDescriptor[] properties = BeanUtils.getPropertyDescriptors(obj.getClass());
		Object val = null;
		for(PropertyDescriptor pd : properties) {
			if(pd.getName().equals(attribute)) {
				try {
					val = pd.getReadMethod().invoke(obj, null);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				break;
			}
		}
		return null == val ? null : val.toString();
	}

}
