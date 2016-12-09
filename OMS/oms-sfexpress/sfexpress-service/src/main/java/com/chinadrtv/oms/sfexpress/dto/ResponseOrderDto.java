/*
 * @(#)ResponseOrderDto.java 1.0 2014-6-11下午1:50:34
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.oms.sfexpress.dto;

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
 * @since 2014-6-11 下午1:50:34 
 * 
 */
public class ResponseOrderDto {

	private String orderId;
	private String mailNo;
	private String originCode;
	private String destCode;
	private String filterResult;
	private String remark;
	private String returnTrackingNo;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getMailNo() {
		return mailNo;
	}
	public void setMailNo(String mailNo) {
		this.mailNo = mailNo;
	}
	public String getOriginCode() {
		return originCode;
	}
	public void setOriginCode(String originCode) {
		this.originCode = originCode;
	}
	public String getDestCode() {
		return destCode;
	}
	public void setDestCode(String destCode) {
		this.destCode = destCode;
	}
	public String getFilterResult() {
		return filterResult;
	}
	public void setFilterResult(String filterResult) {
		this.filterResult = filterResult;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getReturnTrackingNo() {
		return returnTrackingNo;
	}
	public void setReturnTrackingNo(String returnTrackingNo) {
		this.returnTrackingNo = returnTrackingNo;
	}
	
}
