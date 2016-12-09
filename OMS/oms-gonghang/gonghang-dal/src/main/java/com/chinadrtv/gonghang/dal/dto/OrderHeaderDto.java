/*
 * @(#)OrderHeaderDto.java 1.0 2014-5-13下午2:00:02
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.gonghang.dal.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
 * @since 2014-5-13 下午2:00:02 
 * 
 */
public class OrderHeaderDto implements Serializable {

	private static final long serialVersionUID = -2745946572206227014L;

	private String orderId;
	private Date orderCreateTime;
	private Date orderModifyTime;
	private String orderStatus;
	private Date orderPayTime;
	private String orderPaySys;
	private String orderPaySn;
	private BigDecimal orderPayAmount;
	private String consigneeName;
	private String consigneeProvince;
	private String consigneeCity;
	private String consigneeDistrict;
	private String consigneeAddress;
	private String zipcode;
	private String mobile;
	private String phone;
	private String invoiceTitle;
	private String invoiceContent;
	
	private String orderBuyerRemark;
	private String orderSellerRemark;
	private String orderBuyerId;;
	private String orderBuyerName;
	
	private BigDecimal orderAmount;
	private BigDecimal orderCreditAmount;
	private BigDecimal orderCouponAmount;
	private BigDecimal orderFreight;
	private BigDecimal orderOtherDiscount;
	
	private String orderChannel;
	private String discounts;
	private String discount;
	private String discountType;
	private String discountAmount;
	
	private List<OrderDetailDto> orderDetails = new ArrayList<OrderDetailDto>();
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Date getOrderCreateTime() {
		return orderCreateTime;
	}
	public void setOrderCreateTime(Date orderCreateTime) {
		this.orderCreateTime = orderCreateTime;
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
	public Date getOrderPayTime() {
		return orderPayTime;
	}
	public void setOrderPayTime(Date orderPayTime) {
		this.orderPayTime = orderPayTime;
	}
	public String getOrderPaySys() {
		return orderPaySys;
	}
	public void setOrderPaySys(String orderPaySys) {
		this.orderPaySys = orderPaySys;
	}
	public String getOrderPaySn() {
		return orderPaySn;
	}
	public void setOrderPaySn(String orderPaySn) {
		this.orderPaySn = orderPaySn;
	}
	public BigDecimal getOrderPayAmount() {
		return orderPayAmount;
	}
	public void setOrderPayAmount(BigDecimal orderPayAmount) {
		this.orderPayAmount = orderPayAmount;
	}
	public String getConsigneeName() {
		return consigneeName;
	}
	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}
	public String getConsigneeProvince() {
		return consigneeProvince;
	}
	public void setConsigneeProvince(String consigneeProvince) {
		this.consigneeProvince = consigneeProvince;
	}
	public String getConsigneeCity() {
		return consigneeCity;
	}
	public void setConsigneeCity(String consigneeCity) {
		this.consigneeCity = consigneeCity;
	}
	public String getConsigneeDistrict() {
		return consigneeDistrict;
	}
	public void setConsigneeDistrict(String consigneeDistrict) {
		this.consigneeDistrict = consigneeDistrict;
	}
	public String getConsigneeAddress() {
		return consigneeAddress;
	}
	public void setConsigneeAddress(String consigneeAddress) {
		this.consigneeAddress = consigneeAddress;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getInvoiceTitle() {
		return invoiceTitle;
	}
	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}
	public String getInvoiceContent() {
		return invoiceContent;
	}
	public void setInvoiceContent(String invoiceContent) {
		this.invoiceContent = invoiceContent;
	}
	public List<OrderDetailDto> getOrderDetails() {
		return orderDetails;
	}
	public void setOrderDetails(List<OrderDetailDto> orderDetails) {
		this.orderDetails = orderDetails;
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
	public String getOrderChannel() {
		return orderChannel;
	}
	public void setOrderChannel(String orderChannel) {
		this.orderChannel = orderChannel;
	}
	public String getDiscounts() {
		return discounts;
	}
	public void setDiscounts(String discounts) {
		this.discounts = discounts;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public String getDiscountType() {
		return discountType;
	}
	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}
	public String getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(String discountAmount) {
		this.discountAmount = discountAmount;
	}
	public BigDecimal getOrderFreight() {
		return orderFreight;
	}
	public void setOrderFreight(BigDecimal orderFreight) {
		this.orderFreight = orderFreight;
	}
	public BigDecimal getOrderOtherDiscount() {
		return orderOtherDiscount;
	}
	public void setOrderOtherDiscount(BigDecimal orderOtherDiscount) {
		this.orderOtherDiscount = orderOtherDiscount;
	}	
}
