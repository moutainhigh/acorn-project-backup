/**
 * Copyright (c) 2013, Acorn and/or its affiliates. All rights reserved.
 * ACORN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.chinadrtv.erp.marketing.core.common;

import java.util.ArrayList;
import java.util.List;

/**
 * 2013-5-16 下午4:01:00
 * @version 1.0.0
 * @author yangfei
 *
 */
public enum AuditTaskType {
	
	CONTACTCHANGE("修改客户",0),
	ORDERCHANGE("修改订单",1),
	ORDERCANCEL("取消订单",2),
	ORDERADD("新增订单",3),
	CONTACTADD("新增客户",4);
	
	private String name;
	private int index;
	
	AuditTaskType(String name, int index) {
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
	
	static public List<String> toList() {
		List<String> contents = new ArrayList<String>();
		for(AuditTaskType cts : AuditTaskType.values()) {
			contents.add(cts.name);
		}
		return contents;
	}
	
	static public String fromOrdinal(int num) {
		return AuditTaskType.values()[num].name;
	}
	
	
	public static void main(String[] args) {
//		CampaignTaskStatus cts = CampaignTaskStatus.ACTIVE;
		for(AuditTaskType cts : AuditTaskType.values()) {
			System.out.println(cts.name()+" "+cts.getName());
		}
		System.out.println(AuditTaskType.toList());
		System.out.println(AuditTaskType.fromOrdinal(1));
		System.out.println(AuditTaskType.CONTACTCHANGE.getName());
	}
	
}
