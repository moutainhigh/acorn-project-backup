/*
 * @(#)BizException.java 1.0 2013-3-20下午11:11:01
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.core.exception;

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
 * @since 2013-3-20 下午11:11:01 
 * 
 */
public class BizException extends BaseException{

	private static final long serialVersionUID = 1L;
	
	public BizException(String message){
		super(message);
	}

}
