/*
 * @(#)CompanyPaymentDto.java 1.0 2013-4-8下午2:54:08
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.oms.dto;

import java.util.Date;

import com.chinadrtv.erp.model.CompanyPayment;

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
 * @since 2013-4-8 下午2:54:08 
 * 
 */
public class CompanyPaymentDto extends CompanyPayment {

	private static final long serialVersionUID = 1L;
	
	private boolean initLoad = false;
	private Date beginPaymentDate;
	private Date endPaymentDate;
	
	public Date getBeginPaymentDate() {
		return beginPaymentDate;
	}
	public void setBeginPaymentDate(Date beginPaymentDate) {
		this.beginPaymentDate = beginPaymentDate;
	}
	public Date getEndPaymentDate() {
		return endPaymentDate;
	}
	public void setEndPaymentDate(Date endPaymentDate) {
		this.endPaymentDate = endPaymentDate;
	}
	public boolean isInitLoad() {
		return initLoad;
	}
	public void setInitLoad(boolean initLoad) {
		this.initLoad = initLoad;
	}

}
