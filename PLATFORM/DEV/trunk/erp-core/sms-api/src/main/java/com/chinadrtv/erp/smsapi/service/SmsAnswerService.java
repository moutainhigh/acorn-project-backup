/*
 * @(#)SmsAnswerService.java 1.0 2013-2-22下午5:21:17
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.smsapi.service;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-2-22 下午5:21:17
 * 
 */
public interface SmsAnswerService {

	public void singleSmsAnswer(String xml);

	public void groupSmsAnswer(String xml);

}
