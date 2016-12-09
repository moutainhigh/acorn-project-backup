/*
 * @(#)Discourse.java 1.0 2013-5-20上午10:46:31
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.marketing.core.dao.ObContactDao;
import com.chinadrtv.erp.marketing.core.service.ObContactService;
import com.chinadrtv.erp.model.agent.ObContact;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-5-20 上午10:46:31
 * 
 */
@Service("obContactService")
public class ObContactServiceImpl implements ObContactService {
	private static final Logger logger = LoggerFactory
			.getLogger(ObContactServiceImpl.class);
	@Autowired
	private ObContactDao obContactDao;
	
	/* (非 Javadoc)
	* <p>Title: query</p>
	* <p>Description: </p>
	* @param jobId
	* @param limit
	* @return
	* @see com.chinadrtv.erp.marketing.core.service.ObContactService#query(java.lang.String, int)
	*/ 
	@Override
	public List<ObContact> query(String jobId, int limit) {
		obContactDao.updateStatus(jobId, limit);
		return obContactDao.query(jobId, limit);
	}


}
