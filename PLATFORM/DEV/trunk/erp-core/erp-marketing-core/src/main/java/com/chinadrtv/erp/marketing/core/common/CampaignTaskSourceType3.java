/**
 * Copyright (c) 2013, Acorn and/or its affiliates. All rights reserved.
 * ACORN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.chinadrtv.erp.marketing.core.common;

import java.util.ArrayList;
import java.util.List;

public enum CampaignTaskSourceType3 {

	A("A",0),
	B("B",1),
	C("C",2);

	private String name;
	private int index;

	CampaignTaskSourceType3(String name, int index) {
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
		for(CampaignTaskSourceType3 cts : CampaignTaskSourceType3.values()) {
			contents.add(cts.name);
		}
		return contents;
	}
	
	static public String fromOrdinal(int num) {
		return CampaignTaskSourceType3.values()[num].name;
	}
	
	
	public static void main(String[] args) {
//		CampaignTaskStatus cts = CampaignTaskStatus.ACTIVE;
		for(CampaignTaskSourceType3 cts : CampaignTaskSourceType3.values()) {
			System.out.println(cts.name()+" "+cts.getName());
		}
		System.out.println(CampaignTaskSourceType3.toList());
		System.out.println(CampaignTaskSourceType3.fromOrdinal(1));
	}
	
}
