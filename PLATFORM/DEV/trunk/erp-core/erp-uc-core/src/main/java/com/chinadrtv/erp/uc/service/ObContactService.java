/*
 * @(#)ObContactService.java 1.0 2013-5-31下午4:11:06
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.uc.service;

import com.chinadrtv.erp.core.service.GenericService;
import com.chinadrtv.erp.model.agent.ObContact;

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
 * @since 2013-5-31 下午4:11:06 
 * 
 */
public interface ObContactService extends GenericService<ObContact, String> {

	/**
	 * <p>API-UC-37.查询客户取数的座席</p>
	 * @param contactId
	 * @return String
	 */
	String queryAgentUser(String contactId);
}
