/*
 * @(#)OldContactService.java 1.0 2013-5-31上午9:52:52
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.uc.service;

import com.chinadrtv.erp.core.service.GenericService;
import com.chinadrtv.erp.model.agent.OldContactex;
import com.chinadrtv.erp.user.model.AgentUser;

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
 * @since 2013-5-31 上午9:52:52 
 * 
 */
public interface OldContactexService extends GenericService<OldContactex, String> {

	/**
	 * <p>查询老客户绑定坐席</p>
	 * @param contactId
	 * @return AgentUser
	 */
	AgentUser queryBindAgentUser(String contactId);
}
