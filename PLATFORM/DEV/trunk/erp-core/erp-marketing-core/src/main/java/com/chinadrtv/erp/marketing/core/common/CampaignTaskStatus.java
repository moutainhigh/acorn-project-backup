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
public enum CampaignTaskStatus {
	
	INITIALIZED("初始化",0),
	ACTIVE("开始",1),
	CANCEL("取消",2),
	STOP("结束",3),
	SYSTEMSTOP("系统结束",4);
	
	private String name;
	private int index;
	
	CampaignTaskStatus(String name, int index) {
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
		for(CampaignTaskStatus cts : CampaignTaskStatus.values()) {
			contents.add(cts.name);
		}
		return contents;
	}
	
	static public String fromOrdinal(int num) {
		return CampaignTaskStatus.values()[num].name;
	}
	
	static public List<String> toStringIndexes() {
		List<String> indices = new ArrayList<String>();
		for(CampaignTaskStatus cts : CampaignTaskStatus.values()) {
			indices.add(String.valueOf(cts.index));
		}
		return indices;
	}
	
	
	public static void main(String[] args) {
//		CampaignTaskStatus cts = CampaignTaskStatus.ACTIVE;
		for(CampaignTaskStatus cts : CampaignTaskStatus.values()) {
			System.out.println(cts.toString());
		}
		System.out.println(CampaignTaskStatus.toList());
		System.out.println(CampaignTaskStatus.fromOrdinal(1));
		System.out.println(CampaignTaskStatus.CANCEL.getIndex());
		System.out.println(CampaignTaskStatus.toStringIndexes());
	}
	
}
