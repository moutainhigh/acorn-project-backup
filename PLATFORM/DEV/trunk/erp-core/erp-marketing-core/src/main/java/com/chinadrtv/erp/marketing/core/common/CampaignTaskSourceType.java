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
public enum CampaignTaskSourceType {

	SELFCREATE("自建任务",0),
	FETCH("取数任务",1),
	CALLBACK("预约任务",2),
    PUSH("推送任务",3),
    INCOMING("回呼任务",4),
	ORDERTRACE("跟单任务",5);

	private String name;
	private int index;

	CampaignTaskSourceType(String name, int index) {
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
		for(CampaignTaskSourceType cts : CampaignTaskSourceType.values()) {
			contents.add(cts.name);
		}
		return contents;
	}
	
	static public String fromOrdinal(int num) {
		return CampaignTaskSourceType.values()[num].name;
	}
	
	
	public static void main(String[] args) {
//		CampaignTaskStatus cts = CampaignTaskStatus.ACTIVE;
		for(CampaignTaskSourceType cts : CampaignTaskSourceType.values()) {
			System.out.println(cts.name()+" "+cts.getName());
		}
		System.out.println(CampaignTaskSourceType.toList());
		System.out.println(CampaignTaskSourceType.fromOrdinal(1));
	}
	
}
