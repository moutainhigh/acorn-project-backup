/*
 * @(#)OrderAssignRuleDto.java 1.0 2013-4-17上午9:52:56
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.oms.dto;

import com.chinadrtv.erp.model.OrderAssignRule;

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
 * @since 2013-4-17 上午9:52:56 
 * 
 */
public class OrderAssignRuleDto extends OrderAssignRule {

	private static final long serialVersionUID = 1L;

	private Double beginMinAmount;
	private Double endMinAmount;
	private Double beginMaxAmount;
	private Double endMaxAmount;
	
	public Double getBeginMinAmount() {
		return beginMinAmount;
	}
	public void setBeginMinAmount(Double beginMinAmount) {
		this.beginMinAmount = beginMinAmount;
	}
	public Double getEndMinAmount() {
		return endMinAmount;
	}
	public void setEndMinAmount(Double endMinAmount) {
		this.endMinAmount = endMinAmount;
	}
	public Double getBeginMaxAmount() {
		return beginMaxAmount;
	}
	public void setBeginMaxAmount(Double beginMaxAmount) {
		this.beginMaxAmount = beginMaxAmount;
	}
	public Double getEndMaxAmount() {
		return endMaxAmount;
	}
	public void setEndMaxAmount(Double endMaxAmount) {
		this.endMaxAmount = endMaxAmount;
	}
}
