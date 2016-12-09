/*
 * @(#)Payment.java 1.0 2014-5-13上午10:20:37
 *
 * 橡果国际-平台开发组
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
 * @since 2014-5-13 上午10:20:37 
 * 
 */
public class Payment implements Serializable {

	private static final long serialVersionUID = 2112589384520205627L;

	private Date orderPayTime;
	private BigDecimal orderPayAmount;
	private String orderPaySys;
	private BigDecimal orderDiscountAmount;
	private String paySerial;
	
	public Date getOrderPayTime() {
		return orderPayTime;
	}
	public void setOrderPayTime(Date orderPayTime) {
		this.orderPayTime = orderPayTime;
	}
	public BigDecimal getOrderPayAmount() {
		return orderPayAmount;
	}
	public void setOrderPayAmount(BigDecimal orderPayAmount) {
		this.orderPayAmount = orderPayAmount;
	}
	public String getOrderPaySys() {
		return orderPaySys;
	}
	public void setOrderPaySys(String orderPaySys) {
		this.orderPaySys = orderPaySys;
	}
	public BigDecimal getOrderDiscountAmount() {
		return orderDiscountAmount;
	}
	public void setOrderDiscountAmount(BigDecimal orderDiscountAmount) {
		this.orderDiscountAmount = orderDiscountAmount;
	}
	public String getPaySerial() {
		return paySerial;
	}
	public void setPaySerial(String paySerial) {
		this.paySerial = paySerial;
	}
	
}
