/**
 * Copyright (c) 2013, Acorn and/or its affiliates. All rights reserved.
 * ACORN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.chinadrtv.erp.marketing.core.common;

import java.util.ArrayList;
import java.util.List;

/**
 * 2013-7-11 下午4:01:00
 * @version 1.0.0
 * @author yangfei
 *
 */
public enum LeadStatus {
	
	STARTED("开始",0),
	STOP("正常结束",1),
	SYSTEMSTOP("系统关闭",2),
	TODO("跟进",3),
	SALECHANCE("销售机会",4),
	BINGO("成单",5),
	OTHERCHANCE("其他原因结束",99);
	
	private String name;
	private int index;
	
	LeadStatus(String name, int index) {
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
		for(LeadStatus cts : LeadStatus.values()) {
			contents.add(cts.name);
		}
		return contents;
	}
	
	static public String fromOrdinal(int num) {
		return LeadStatus.values()[num].name;
	}
	
	
	public static void main(String[] args) {
//		CampaignTaskStatus cts = CampaignTaskStatus.ACTIVE;
		for(LeadStatus cts : LeadStatus.values()) {
			System.out.println(cts.toString());
		}
		
		for(LeadStatus cts : LeadStatus.values()) {
			System.out.println(cts.getName());
		}
		System.out.println(LeadStatus.toList());
		System.out.println(LeadStatus.fromOrdinal(1));
	}
	
}
