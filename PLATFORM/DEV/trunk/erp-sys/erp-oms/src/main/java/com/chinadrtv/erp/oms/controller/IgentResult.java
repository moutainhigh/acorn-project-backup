/**
 * 
 */
package com.chinadrtv.erp.oms.controller;

/**
 * 
 * 返回对象封装
 *  
 * @author haoleitao
 * @date 2013-3-7 下午4:33:56
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class IgentResult {
	
	boolean success;
	String code;
	String desc;
	String orderId;
	String impState;
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getImpState() {
		return impState;
	}
	public void setImpState(String impState) {
		this.impState = impState;
	}
	
	

}
