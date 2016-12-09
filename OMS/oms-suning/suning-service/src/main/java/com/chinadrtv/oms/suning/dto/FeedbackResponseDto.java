/*
 * @(#)FeedbackResponseDto.java 1.0 2014-11-13下午2:23:02
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
 * @since 2014-11-13 下午2:23:02 
 * 
 */
public class FeedbackResponseDto {

	private String sendresult;
	private String orderCode;
	private String productCode;
	private String orderLineNumber;
	
	public String getSendresult() {
		return sendresult;
	}
	public void setSendresult(String sendresult) {
		this.sendresult = sendresult;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getOrderLineNumber() {
		return orderLineNumber;
	}
	public void setOrderLineNumber(String orderLineNumber) {
		this.orderLineNumber = orderLineNumber;
	}
}
