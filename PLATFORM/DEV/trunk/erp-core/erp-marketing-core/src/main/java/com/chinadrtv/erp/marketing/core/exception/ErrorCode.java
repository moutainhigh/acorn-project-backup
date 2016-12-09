/**
 * Copyright (c) 2013, Acorn and/or its affiliates. All rights reserved.
 * ACORN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.chinadrtv.erp.marketing.core.exception;

/**
 * 2013-5-16 下午4:01:00
 * @version 1.0.0
 * @author yangfei
 *
 */
public enum ErrorCode {
	
	UserNull("座席名空",1),
	DepartmentNull("部门名空",2),
	OutOfTimeRange("查询区段过大",3),
	NotProperApprover("不能审批自己的修改请求",4),
	CRITERIAWRONG("查询条件不满足", 5),
	CUSTOMERNOTALLOWED("不允许的客户",6),
	LEADOVERDUE("线索过期",7),
	StatusNotSynced("流程已被其它主管处理",8),
	System("系统异常",9),
	Unknown("未清楚异常",10);

	
	private String name;
	private int index;
	
	ErrorCode(String name, int index) {
		this.name = name;
		this.index = index;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	
}
