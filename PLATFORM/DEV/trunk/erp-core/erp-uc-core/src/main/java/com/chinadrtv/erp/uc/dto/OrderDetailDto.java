/*
 * @(#)OrderDetailDto.java 1.0 2013-5-8下午3:40:16
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.uc.dto;

import com.chinadrtv.erp.model.agent.OrderDetail;

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
 * @since 2013-5-8 下午3:40:16 
 * 
 */
public class OrderDetailDto extends OrderDetail {

	private static final long serialVersionUID = 9098485061668878186L;
	
	/* 自由项  */
	private String ncfreeName;
	private Double unitPrice;
	private Long totalQty;
	private String saleType;
	
	public Double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public Long getTotalQty() {
		return totalQty;
	}
	public void setTotalQty(Long totalQty) {
		this.totalQty = totalQty;
	}
	public String getSaleType() {
		return saleType;
	}
	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}
	public String getNcfreeName() {
		return ncfreeName;
	}
	public void setNcfreeName(String ncfreeName) {
		this.ncfreeName = ncfreeName;
	}
	
}
