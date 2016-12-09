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
public enum UserBpmTaskType {
	
	CONTACT_BASE_CHANGE("修改客户基本信息",0),
	CONTACT_PHONE_CHANGE("修改客户电话",1),
	CONTACT_ADDRESS_CHANGE("修改客户地址",2),
	
	ORDER_CART_CHANGE("修改订单购物车",3),
	ORDER_REMARK_CHANGE("修改订单备注",4),
	ORDER_CARD_CHANGE("修改订单信用卡信息",5),
	ORDER_BILL_CHANGE("修改订单发票信息",6),
	ORDER_MAILPRICE_CHANGE("修改订单运费",7),
	ORDER_EMS_CHANGE("修改订单承运商",8),
	ORDER_TYPE_CHANGE("修改订单类型",9),
	
	ORDER_SUBSCRIBER_BLACKLIST("订购人黑名单",10),
	//新增订单时修改运费产生和修改订单时修改运费一样的批次和流程，但前者需要发送短信，故新加类型区分
	ORDER_MAILPRICE_CHANGE_IN_ORDERADD("新增订单时修改订单运费",11),
	
	ORDER_RECEIVER_CHANGE("新增订单时换收货人",12);
	
	private String name;
	private int index;
	
	UserBpmTaskType(String name, int index) {
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
		for(UserBpmTaskType cts : UserBpmTaskType.values()) {
			contents.add(cts.name);
		}
		return contents;
	}
	
	static public String fromOrdinal(int num) {
		return UserBpmTaskType.values()[num].name;
	}
	
	static public UserBpmTaskType fromNumber(int num) {
		return UserBpmTaskType.values()[num];
	}
	
	public static void main(String[] args) {
//		CampaignTaskStatus cts = CampaignTaskStatus.ACTIVE;
		for(UserBpmTaskType cts : UserBpmTaskType.values()) {
			System.out.println(cts.name()+" "+cts.getName());
		}
		System.out.println(UserBpmTaskType.toList());
		System.out.println(UserBpmTaskType.fromOrdinal(1));
	}
	
}
