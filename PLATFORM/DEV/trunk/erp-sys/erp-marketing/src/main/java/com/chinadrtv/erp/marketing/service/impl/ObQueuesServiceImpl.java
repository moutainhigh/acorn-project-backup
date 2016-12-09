/*
 * @(#)NamesServiceImpl.java 1.0 2013-1-22下午2:20:50
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.marketing.core.dao.ObQueuesDao;
import com.chinadrtv.erp.marketing.service.ObQueuesService;
import com.chinadrtv.erp.model.agent.ObQueues;

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
 * @author zhaizy
 * @version 1.0
 * @since 2013-1-22 下午2:20:50 
 * 
 */
@Service("obQueuesService")
public class ObQueuesServiceImpl implements ObQueuesService {
	
	@Autowired
	private ObQueuesDao obQueuesDao;


	/* (非 Javadoc)
	* <p>Title: queryNamesList</p>
	* <p>Description: </p>
	* @return
	* @see com.chinadrtv.erp.marketing.service.NamesService#queryNamesList()
	*/ 
	public List<ObQueues> queryList(String jobId) {
		return obQueuesDao.queryList(jobId);
	}
	
}
