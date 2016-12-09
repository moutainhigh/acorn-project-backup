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
public enum CampaignTaskType {
	
	MARKETING("销售",0);
/*	MAINTENANCE("客户维护",1),
	SURVEY("市场调查",2);*/
	
	private String name;
	private int index;
	
	CampaignTaskType(String name, int index) {
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
		for(CampaignTaskType cts : CampaignTaskType.values()) {
			contents.add(cts.name);
		}
		return contents;
	}
	
	static public String fromOrdinal(int num) {
		return CampaignTaskType.values()[num].name;
	}
	
	
	public static void main(String[] args) {
//		CampaignTaskStatus cts = CampaignTaskStatus.ACTIVE;
		for(CampaignTaskType cts : CampaignTaskType.values()) {
			System.out.println(cts.name());
		}
		System.out.println(CampaignTaskType.toList());
		System.out.println(CampaignTaskType.fromOrdinal(1));
	}
	
}
