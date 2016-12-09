/*
 * @(#)OldContactServiceImpl.java 1.0 2013-5-31上午9:53:37
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.uc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.model.agent.OldContactex;
import com.chinadrtv.erp.uc.dao.OldContactexDao;
import com.chinadrtv.erp.uc.service.OldContactexService;
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
 * @since 2013-5-31 上午9:53:37 
 * 
 */
@Service
public class OldContactexServiceImpl extends GenericServiceImpl<OldContactex, String> implements OldContactexService {

	@Autowired
	private OldContactexDao oldContactexDao;
	
	@Override
	protected GenericDao<OldContactex, String> getGenericDao() {
		return oldContactexDao;
	}

	/** 
	 * <p>Title: queryBindAgentUser</p>
	 * <p>Description: 查询老客户绑定坐席</p>
	 * @param contactId
	 * @return AgentUser
	 * @see com.chinadrtv.erp.uc.service.OldContactService#queryBindAgentUser(java.lang.String)
	 */ 
	public AgentUser queryBindAgentUser(String contactId) {
		return oldContactexDao.queryBindAgentUser(contactId);
	}

	
}
