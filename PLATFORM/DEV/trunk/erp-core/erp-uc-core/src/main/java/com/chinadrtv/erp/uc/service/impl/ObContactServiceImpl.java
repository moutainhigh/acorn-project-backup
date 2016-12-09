/*
 * @(#)ObContactServiceImpl.java 1.0 2013-5-31下午4:11:42
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.uc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.model.agent.ObContact;
import com.chinadrtv.erp.uc.dao.ObContactDao;
import com.chinadrtv.erp.uc.service.ObContactService;

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
 * @since 2013-5-31 下午4:11:42 
 * 
 */
@Service
public class ObContactServiceImpl extends GenericServiceImpl<ObContact, String> implements ObContactService {

	@Autowired
	@Qualifier("ucObContactDao")
	private ObContactDao obContactDao;
	
	@Override
	protected GenericDao<ObContact, String> getGenericDao() {
		return obContactDao;
	}

	/** 
	 * <p>Title: queryAgentUser</p>
	 * <p>Description: API-UC-37.查询客户取数的座席</p>
	 * @param contactId
	 * @return String
	 * @see com.chinadrtv.erp.uc.service.ObContactService#queryAgentUser(java.lang.String)
	 */ 
	public String queryAgentUser(String contactId) {
		return obContactDao.queryAgentUser(contactId);
	}

	
}
