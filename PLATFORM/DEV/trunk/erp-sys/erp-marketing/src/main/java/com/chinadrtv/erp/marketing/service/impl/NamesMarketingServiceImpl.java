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

import com.chinadrtv.erp.marketing.dao.NamesMarketingDao;
import com.chinadrtv.erp.marketing.service.NamesMarketingService;
import com.chinadrtv.erp.model.Names;

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
 * @since 2013-1-22 下午2:20:50 
 * 
 */
@Service("namesMarketingService")
public class NamesMarketingServiceImpl implements NamesMarketingService {
	
	@Autowired
	private NamesMarketingDao namesMarketingDao;


	/* (非 Javadoc)
	* <p>Title: queryNamesList</p>
	* <p>Description: </p>
	* @return
	* @see com.chinadrtv.erp.marketing.service.NamesService#queryNamesList()
	*/ 
	public List<Names> queryNamesList() {
		return namesMarketingDao.queryNamesList();
	}
	
	public NamesMarketingDao getNamesDao() {
		return namesMarketingDao;
	}
	public void setNamesDao(NamesMarketingDao namesMarketingDao) {
		this.namesMarketingDao = namesMarketingDao;
	}
}
