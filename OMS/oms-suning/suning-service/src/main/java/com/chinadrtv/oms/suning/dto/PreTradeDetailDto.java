/*
 * @(#)PreTradeDetailDto.java 1.0 2014-11-12下午4:15:50
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.oms.suning.dto;

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
 * @since 2014-11-12 下午4:15:50 
 * 
 */
public class PreTradeDetailDto {

	private String qty;
	private Double upPrice;
	private String outSkuId;
	private Double shippingFee;
	private Double payAmount;
	private String productName;
	
	public String getQty() {
		return qty;
	}
	public void setQty(String qty) {
		this.qty = qty;
	}
	public Double getUpPrice() {
		return upPrice;
	}
	public void setUpPrice(Double upPrice) {
		this.upPrice = upPrice;
	}
	public String getOutSkuId() {
		return outSkuId;
	}
	public void setOutSkuId(String outSkuId) {
		this.outSkuId = outSkuId;
	}
	public Double getShippingFee() {
		return shippingFee;
	}
	public void setShippingFee(Double shippingFee) {
		this.shippingFee = shippingFee;
	}
	public Double getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
}
