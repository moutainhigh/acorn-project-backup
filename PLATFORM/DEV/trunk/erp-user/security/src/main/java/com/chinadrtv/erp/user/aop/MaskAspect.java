/*
 * @(#)CacheAspect.java 1.0 2013-4-12下午4:06:26
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.user.aop;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.commons.beanutils.BeanUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import com.chinadrtv.erp.core.aop.BaseAspect;
import com.chinadrtv.erp.model.security.DataViewPermission;
import com.chinadrtv.erp.model.security.FieldPermission;
import com.chinadrtv.erp.user.util.PermissionHelper;
import com.chinadrtv.erp.util.StringUtil;

/**
 * <dl>
 *    <dt><b>Title:行级数据权限annotation AOP</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:行级数据权限annotation AOP</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-4-12 下午4:06:26 
 * 
 */
@Aspect
@Singleton
@Named
public class MaskAspect extends BaseAspect {

	private static final Logger logger = LoggerFactory.getLogger(MaskAspect.class);
	
	public MaskAspect() {
	}

	//&& @target(org.springframework.stereotype.Controller)
	@Around("execution(public * *(..))  && @annotation(mask)")
	public Object get(ProceedingJoinPoint jp,Mask mask)
			throws Throwable {
		
		Class target = null;
		if(!StringUtil.isNullOrBank(mask.target())){
			target = Class.forName(mask.target());
		}
		
		logger.info("执行行级拦截");
//		PermissionHelper.setWhereCondition();
		
		Object result = jp.proceed();
		
//		Object copyResult = BeanUtils.cloneBean(result);
		
		Set<FieldPermission> fieldPermissionsSet =null; 
		DataViewPermission dataViewPermission = PermissionHelper.getDataViewPermission(mask.depth());
		
		if(dataViewPermission!=null){
			if(target == null){
				target = Class.forName(dataViewPermission.getDataView().getEntity_name());
			}
			fieldPermissionsSet = dataViewPermission.getFieldPermissions();
		}
		
		if(target == null){
			logger.info("target Class is not null");
		}
		
		if(fieldPermissionsSet != null && !fieldPermissionsSet.isEmpty()){
			result = processMask(result,target,fieldPermissionsSet);
		}
		
		logger.info("======"+result.toString());
		return result;
	}
	
	
	public Object processMask(Object result,Class target,Set<FieldPermission> fieldPermissionsSet){
		
		if(result instanceof Map){
			Set resultSet = ((Map) result).keySet();
			Iterator it = resultSet.iterator();
			Object key = null;
			Object r = null;
			Map copyTemp = null;
			if(result instanceof ModelMap) {
				copyTemp = new ModelMap();
			} else {
				copyTemp = new HashMap();
			}
			
			while(it.hasNext()){
				key = it.next();
				r = processMask(((Map) result).get(key),target,fieldPermissionsSet);
				//((Map) result).put(key, r);
				copyTemp.put(key, r);
			}
			
			return (Object)copyTemp;
		}else if(result instanceof List){
			List tempList = (List) result;
			List copyTemp = new ArrayList();
			if(!tempList.isEmpty()){
				if(tempList.get(0).getClass() == target){
					for(int i=0;i<tempList.size();i++){
						copyTemp.add(processMask(tempList.get(i),target,fieldPermissionsSet));
					}
				}else{
					return result;
				}
			}
			return (Object)copyTemp;
		} else if(result instanceof ModelAndView) {
			try {
				Map mapTemp = ((ModelAndView)result).getModelMap();
				Object processedResult = processMask(mapTemp,target,fieldPermissionsSet);
				Field f = ModelAndView.class.getDeclaredField("model");
				f.setAccessible(true);
				f.set(result, processedResult);
			} catch(Exception e) {
				logger.debug("error",e);
			}
			return result;
		}
		else if(result!=null && result.getClass() == target){
			Object copyObj = null;
			try {
				copyObj = BeanUtils.cloneBean(result);
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}
			logger.info("得到需要掩码的对象");
			setMask(copyObj,fieldPermissionsSet);
			
			return copyObj;
		}
		
		return result;
	}
	
	public void setMask(Object obj,Set<FieldPermission> fieldPermissionsSet){
		
		Iterator<FieldPermission> it = fieldPermissionsSet.iterator();
		FieldPermission fieldPermission = null;
		String fieldName = null;
		String fieldRegx = null;
		String replaceCahr = null;
		while(it.hasNext()){
			fieldPermission = it.next();
			fieldName = fieldPermission.getField().getField_name();
			fieldRegx = fieldPermission.getRegex_replace();
			replaceCahr = fieldPermission.getReplace_char();
			if(!setObjectField(obj,fieldName,fieldRegx,replaceCahr))
            {
                logger.error("FieldPermission id:"+fieldPermission.getId()+"-"+fieldPermission.getRegex_replace()+"-"+fieldPermission.getReplace_char());
            }
		}
	}
	
	public boolean setObjectField(Object obj,String property,String regx,String replaceChar){
		
		String getMethodName = null;
		String setMethodName = null;
		Method getMethod = null;  
        Method setMethod = null;  
		Field[] fields = obj.getClass().getDeclaredFields();
		try {
			for(Field f :fields){
				if(f.getName().equals(property)){
					getMethodName = "get"  
	                        + property.substring(0, 1).toUpperCase()  
	                        + property.substring(1);  
					
					getMethod = obj.getClass().getDeclaredMethod(getMethodName,  
                            new Class[] {});
					
	                setMethodName = "set"  
	                        + property.substring(0, 1).toUpperCase()  
	                        + property.substring(1); 
	                
	                setMethod = obj.getClass().getDeclaredMethod(setMethodName,  
                            f.getType()); 
	                // 调用source对象的getMethod方法  
	                
                    String value = (String)getMethod.invoke(obj, new Object[] {});  
                    
                    if(value!=null){
                    	value = value.replaceAll(regx, replaceChar);
                    }
                    logger.debug(value);
                    // 调用target对象的setMethod方法  
                    setMethod.invoke(obj, value);  
						break;
				}
			}
		} catch (Exception e) {
            logger.error("",e);
            return false;
		}

        return true;
	}
	
	public static void main(String arg[]){
//		String a = "27427";
//		String res = "([1-9]{1})([\\d]{2})([1-9]{1}$)";
//		String rchar = "$1****$3";
//		a = a.replaceAll(res, rchar);
//		System.out.println(a);
		
		List<String> list = new ArrayList<String>();
		list.add("111111");
		FieldPermission ss = new FieldPermission();
		ss.setId(1111l);
		try {
			Object list1 = BeanUtils.cloneBean(ss);
			//System.out.println("-");
		} catch (Exception e) {
			// TODO Auto-generated catch block
            logger.error("",e);
		} 
	}

}