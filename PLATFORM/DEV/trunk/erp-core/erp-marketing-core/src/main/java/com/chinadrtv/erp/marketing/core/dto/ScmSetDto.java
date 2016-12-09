/*
 * @(#)ScmSetDto.java 1.0 2013-5-9下午2:13:25
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.dto;

import java.lang.reflect.Field;

/**
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * none</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * none</dd>
 * </dl>
 * 
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-5-9 下午2:13:25
 * 
 */
public class ScmSetDto {

	private String product1;
	private String product2;
	private String product3;

	/**
	 * @return the product1
	 */
	public String getProduct1() {
		return product1;
	}

	/**
	 * @param product1
	 *            the product1 to set
	 */
	public void setProduct1(String product1) {
		this.product1 = product1;
	}

	/**
	 * @return the product2
	 */
	public String getProduct2() {
		return product2;
	}

	/**
	 * @param product2
	 *            the product2 to set
	 */
	public void setProduct2(String product2) {
		this.product2 = product2;
	}

	/**
	 * @return the product3
	 */
	public String getProduct3() {
		return product3;
	}

	/**
	 * @param product3
	 *            the product3 to set
	 */
	public void setProduct3(String product3) {
		this.product3 = product3;
	}

	/**
	 * 获取属性值
	* @Description: TODO
	* @param property
	* @return
	* @return String
	* @throws
	 */
	public String genParamValues(String property){
		String result = "";
		Field[] fields = this.getClass().getDeclaredFields();
		try {
		for(Field f :fields){
			if(f.getName().equals(property)){
					result = f.get(this).toString();
					break;
			}
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
