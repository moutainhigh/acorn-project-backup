/**
 * Copyright (c) 2013, Acorn and/or its affiliates. All rights reserved.
 * ACORN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.chinadrtv.erp.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * 2013-5-16 下午4:01:00
 * @version 1.0.0
 * @author yangfei
 *
 */
public enum LeadInteractionType {
	
	INBOUND_IN("inbound进线",0),
	INBOUND_OUT("inbound外拔",1),
	OUTBOUND_IN("outbound进线",2),
	OUTBOUND_OUT("outbound外拔",3),
	SMS("短消息",4),
    CALLBACK_IN("callback模式进线",5);
	
	private String name;
	private int index;
	
	LeadInteractionType(String name, int index) {
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
	public String getIndexString() {
		return String.valueOf(index);
	}
	public void setIndex(int index) {
		this.index = index;
	}
	
	static public List<String> toList() {
		List<String> contents = new ArrayList<String>();
		for(LeadInteractionType cts : LeadInteractionType.values()) {
			contents.add(cts.name);
		}
		return contents;
	}
	
	static public String fromOrdinal(int num) {
		return LeadInteractionType.values()[num].name;
	}
	
	
	public static void main(String[] args) {
//		CampaignTaskStatus cts = CampaignTaskStatus.ACTIVE;
		for(LeadInteractionType cts : LeadInteractionType.values()) {
			System.out.println(cts.toString());
		}
		
		for(LeadInteractionType cts : LeadInteractionType.values()) {
			System.out.println(cts.getName());
		}
		System.out.println(LeadInteractionType.toList());
		System.out.println(LeadInteractionType.fromOrdinal(1));
	}
	
}
