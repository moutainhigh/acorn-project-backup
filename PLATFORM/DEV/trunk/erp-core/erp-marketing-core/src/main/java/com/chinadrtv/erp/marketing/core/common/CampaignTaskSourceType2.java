/**
 * Copyright (c) 2013, Acorn and/or its affiliates. All rights reserved.
 * ACORN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.chinadrtv.erp.marketing.core.common;

import java.util.ArrayList;
import java.util.List;

public enum CampaignTaskSourceType2 {

	PADDINGA("默认A",0),
	VM("VM",1),
	GIVEUP("放弃",2),
	CONNECTED("接通",3),
	PADDINGB("默认B",4),
	SNATCHIN("snatch-in",5);

	private String name;
	private int index;

	CampaignTaskSourceType2(String name, int index) {
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
		for(CampaignTaskSourceType2 cts : CampaignTaskSourceType2.values()) {
			contents.add(cts.name);
		}
		return contents;
	}
	
	static public String fromOrdinal(int num) {
		return CampaignTaskSourceType2.values()[num].name;
	}
	
	
	public static void main(String[] args) {
//		CampaignTaskStatus cts = CampaignTaskStatus.ACTIVE;
		for(CampaignTaskSourceType2 cts : CampaignTaskSourceType2.values()) {
			System.out.println(cts.name()+" "+cts.getName());
		}
		System.out.println(CampaignTaskSourceType2.toList());
		System.out.println(CampaignTaskSourceType2.fromOrdinal(1));
	}
	
}
