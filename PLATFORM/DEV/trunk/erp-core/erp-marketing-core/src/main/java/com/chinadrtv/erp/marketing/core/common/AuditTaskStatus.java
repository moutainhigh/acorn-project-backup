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
public enum AuditTaskStatus {
	
	UNASSIGNED("待处理",0),
	APPROEED("已批准通过",1),
	REJECTED("已拒绝",2),
	CANCELED("已取消",3),
	CLOSED("已关闭",4),
	ASSITANTWAITING("物流助理待批",5),
	ASSITANTPROCESSED("物流助理已处理",6);
	
	private String name;
	private int index;
	
	AuditTaskStatus(String name, int index) {
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
		for(AuditTaskStatus cts : AuditTaskStatus.values()) {
			contents.add(cts.name);
		}
		return contents;
	}
	
	static public String fromOrdinal(int num) {
		return AuditTaskStatus.values()[num].name;
	}
	
	
	public static void main(String[] args) {
//		CampaignTaskStatus cts = CampaignTaskStatus.ACTIVE;
		for(AuditTaskStatus cts : AuditTaskStatus.values()) {
			System.out.println(cts.toString());
		}
		
		for(AuditTaskStatus cts : AuditTaskStatus.values()) {
			System.out.println(cts.getName());
		}
		System.out.println(AuditTaskStatus.toList());
		System.out.println(AuditTaskStatus.fromOrdinal(1));
	}
	
}
