/*
 * @(#)OrderDetailDto.java 1.0 2014-6-5下午2:13:58
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.gonghang.dal.dto;

import java.io.Serializable;
import java.math.BigDecimal;

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
 * @since 2014-6-5 下午2:13:58 
 * 
 */
public class OrderDetailDto implements Serializable {

	private static final long serialVersionUID = -2889542292030925305L;
	
	private Long productId;
	private String productSkuId;
	private String productCode;
	private String productName;
	private Integer productNumber;
	private BigDecimal productPrice;
	private BigDecimal productDiscount;
	
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getProductSkuId() {
		return productSkuId;
	}
	public void setProductSkuId(String productSkuId) {
		this.productSkuId = productSkuId;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Integer getProductNumber() {
		return productNumber;
	}
	public void setProductNumber(Integer productNumber) {
		this.productNumber = productNumber;
	}
	public BigDecimal getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}
	public BigDecimal getProductDiscount() {
		return productDiscount;
	}
	public void setProductDiscount(BigDecimal productDiscount) {
		this.productDiscount = productDiscount;
	}
	
	
}
