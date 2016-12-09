/**
 * Copyright (c) 2013, Acorn and/or its affiliates. All rights reserved.
 * ACORN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.chinadrtv.erp.marketing.core.exception;

/**
 * 2013-5-16 下午4:07:44
 * @version 1.0.0
 * @author yangfei
 *
 */
public class MarketingException extends Exception{

	private static final long serialVersionUID = 1L;
	
	private ErrorCode ec;
	
	public MarketingException(String message, ErrorCode ec) {
		super(message);
		this.ec = ec;
	}
	
	public MarketingException(Exception e) {
		super(e);
		this.ec = ErrorCode.Unknown;
	}
	
	public MarketingException(Exception e, String message) {
		super(message, e);
		this.ec = ErrorCode.Unknown;
	}
	
	public MarketingException(Exception e, ErrorCode ec) {
		super(e);
		this.ec = ec;
	}
	
	public MarketingException(Exception e, String message, ErrorCode ec) {
		super(message, e);
		this.ec = ec;
	}

	public ErrorCode getEc() {
		return ec;
	}

	public void setEc(ErrorCode ec) {
		this.ec = ec;
	}
	
}
