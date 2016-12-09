/*
 * @(#)BaseException.java 1.0 2013-3-21上午11:16:21
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
 * @since 2013-3-21 上午11:16:21 
 * 
 */
public class BaseException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BaseException(String message){
		super(message);
	}
}
