/**
 * Copyright (c) 2013, Acorn and/or its affiliates. All rights reserved.
 * ACORN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.chinadrtv.erp.sales.dto;

import java.util.ArrayList;
import java.util.List;

import com.chinadrtv.erp.sales.common.JsonUtil;

/**
 * 2013-8-21 下午4:21:48
 * @version 1.0.0
 * @author yangfei
 *
 */
public class OrderInfo4AssistantReview {
	/**
	 * 审批工作流编号
	 */
	private String instId;
	
	/**
	 * 申请人
	 */
	private String appliedUser;
	
	/**
	 * 订单编号
	 */
	private String orderId;
	
	/**
	 * 订单创建时间
	 */
	private String orderCrDate;
	
	/**
	 * 客户所在省
	 */
	private String province;
	
	/**
	 * 客户所在市
	 */
	private String city;
	
	/**
	 * 客户所在县
	 */
	private String country;
	
	private String addressDsc;
	
	private String allAddress;
	
	/**
	 * 仓库
	 */
	private String defaultWarehouse;
	
	private String designedWarehouse;
	
	private String designedWarehouseId;
	
	/**
	 * 默认送货公司
	 */
	private String defaultEntityName;
	
	/**
	 * 指定送货公司
	 */
	private String designedEntityName;
	
	private String designedEntityId;
	
	/**
	 * 申请物流修改备注
	 */
	private String comment;
	

	public String getInstId() {
		return instId;
	}

	public void setInstId(String instId) {
		this.instId = instId;
	}
	
	public String getAppliedUser() {
		return appliedUser;
	}

	public void setAppliedUser(String appliedUser) {
		this.appliedUser = appliedUser;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderCrDate() {
		return orderCrDate;
	}

	public void setOrderCrDate(String orderCrDate) {
		this.orderCrDate = orderCrDate;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getAddressDsc() {
		return addressDsc;
	}

	public void setAddressDsc(String addressDsc) {
		this.addressDsc = addressDsc;
	}
	
	public String getDefaultWarehouse() {
		return defaultWarehouse;
	}

	public void setDefaultWarehouse(String defaultWarehouse) {
		this.defaultWarehouse = defaultWarehouse;
	}

	public String getDesignedWarehouse() {
		return designedWarehouse;
	}

	public void setDesignedWarehouse(String designedWarehouse) {
		this.designedWarehouse = designedWarehouse;
	}

	public String getDefaultEntityName() {
		return defaultEntityName;
	}

	public void setDefaultEntityName(String defaultEntityName) {
		this.defaultEntityName = defaultEntityName;
	}

	public String getDesignedEntityName() {
		return designedEntityName;
	}

	public void setDesignedEntityName(String designedEntityName) {
		this.designedEntityName = designedEntityName;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getDesignedWarehouseId() {
		return designedWarehouseId;
	}

	public void setDesignedWarehouseId(String designedWarehouseId) {
		this.designedWarehouseId = designedWarehouseId;
	}

	public String getDesignedEntityId() {
		return designedEntityId;
	}

	public void setDesignedEntityId(String designedEntityId) {
		this.designedEntityId = designedEntityId;
	}

	public String getAllAddress() {
		return allAddress;
	}

	public void setAllAddress(String allAddress) {
		this.allAddress = allAddress;
	}

	public static void main(String[] args) {
		List<OrderInfo4AssistantReview> ordersInReviews = new ArrayList<OrderInfo4AssistantReview>();
		OrderInfo4AssistantReview ordersInReview = new OrderInfo4AssistantReview();
		ordersInReview.setAddressDsc("小城路88号");
		ordersInReview.setCity("安庆市");
		ordersInReview.setCountry("宜秀区");
		ordersInReview.setProvince("安徽省");
		ordersInReview.setDefaultEntityName("申通快运");
		ordersInReview.setComment("客户指定ems");
		ordersInReview.setDefaultWarehouse("华新仓");
		ordersInReview.setInstId("1234214");
		ordersInReview.setOrderId("5325232");
		ordersInReview.setOrderCrDate("2013-08-23 12:03:34");
		ordersInReviews.add(ordersInReview);
		
		OrderInfo4AssistantReview ordersInReview2 = new OrderInfo4AssistantReview();
		ordersInReview2.setAddressDsc("新理港路348号");
		ordersInReview2.setCity("台中市");
		ordersInReview2.setCountry("大观区");
		ordersInReview2.setProvince("浙江省");
		ordersInReview2.setDefaultEntityName("便捷快运");
		ordersInReview2.setComment("客户指定ems");
		ordersInReview2.setDefaultWarehouse("华新仓");
		ordersInReview2.setInstId("1234214");
		ordersInReview2.setOrderId("5325238");
		ordersInReview2.setOrderCrDate("2013-08-23 12:03:34");
		ordersInReviews.add(ordersInReview2);
		
		System.out.println(JsonUtil.jsonArrayWithFilterObject(ordersInReviews, null));
	}
}
