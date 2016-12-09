/*
 * @(#)OrderDetailDto.java 1.0 2014-5-13上午10:06:20
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.gonghang.dal.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author andrew
 * @version 1.0
 * @since 2014-5-13 上午10:06:20 
 * 
 */
public class Order implements Serializable {

	private static final long serialVersionUID = -2889542292030925305L;

	private String orderId;
	private Date orderModifyTime;
	private String orderStatus;
	private String orderBuyerRemark;
	private String orderSellerRemark;
	private String orderBuyerId;
	private String orderBuyerName;
	private Date orderCreateTime;
	private BigDecimal orderAmount;
	private BigDecimal orderCreditAmount;
	private BigDecimal orderCouponAmount;
	private BigDecimal orderOtherDiscount;
	private Integer orderChannel;
	
	private Product product;
	private Invoice invoice;
	private Payment payment;
	private Consignee consignee;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Date getOrderModifyTime() {
		return orderModifyTime;
	}
	public void setOrderModifyTime(Date orderModifyTime) {
		this.orderModifyTime = orderModifyTime;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getOrderBuyerRemark() {
		return orderBuyerRemark;
	}
	public void setOrderBuyerRemark(String orderBuyerRemark) {
		this.orderBuyerRemark = orderBuyerRemark;
	}
	public String getOrderSellerRemark() {
		return orderSellerRemark;
	}
	public void setOrderSellerRemark(String orderSellerRemark) {
		this.orderSellerRemark = orderSellerRemark;
	}
	public String getOrderBuyerId() {
		return orderBuyerId;
	}
	public void setOrderBuyerId(String orderBuyerId) {
		this.orderBuyerId = orderBuyerId;
	}
	public String getOrderBuyerName() {
		return orderBuyerName;
	}
	public void setOrderBuyerName(String orderBuyerName) {
		this.orderBuyerName = orderBuyerName;
	}
	public Date getOrderCreateTime() {
		return orderCreateTime;
	}
	public void setOrderCreateTime(Date orderCreateTime) {
		this.orderCreateTime = orderCreateTime;
	}
	public BigDecimal getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}
	public BigDecimal getOrderCreditAmount() {
		return orderCreditAmount;
	}
	public void setOrderCreditAmount(BigDecimal orderCreditAmount) {
		this.orderCreditAmount = orderCreditAmount;
	}
	public BigDecimal getOrderCouponAmount() {
		return orderCouponAmount;
	}
	public void setOrderCouponAmount(BigDecimal orderCouponAmount) {
		this.orderCouponAmount = orderCouponAmount;
	}
	public BigDecimal getOrderOtherDiscount() {
		return orderOtherDiscount;
	}
	public void setOrderOtherDiscount(BigDecimal orderOtherDiscount) {
		this.orderOtherDiscount = orderOtherDiscount;
	}
	public Integer getOrderChannel() {
		return orderChannel;
	}
	public void setOrderChannel(Integer orderChannel) {
		this.orderChannel = orderChannel;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Invoice getInvoice() {
		return invoice;
	}
	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}
	public Payment getPayment() {
		return payment;
	}
	public void setPayment(Payment payment) {
		this.payment = payment;
	}
	public Consignee getConsignee() {
		return consignee;
	}
	public void setConsignee(Consignee consignee) {
		this.consignee = consignee;
	}
	
}
