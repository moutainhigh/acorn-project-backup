/*
 * @(#)MpZoneServiceImpl.java 1.0 2013-6-5上午9:51:15
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.uc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.model.agent.MpZone;
import com.chinadrtv.erp.uc.dao.MpZoneDao;
import com.chinadrtv.erp.uc.service.MpZoneService;

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
 * @since 2013-6-5 上午9:51:15 
 * 
 */
@Service
public class MpZoneServiceImpl extends GenericServiceImpl<MpZone, String> implements MpZoneService {

	@Autowired
	private MpZoneDao mpZoneDao;
	
	@Override
	protected GenericDao<MpZone, String> getGenericDao() {
		return mpZoneDao;
	}

	/** 
	 * <p>Title: queryByPhoneNo</p>
	 * <p>Description: API-UC-40.查询手机归属地</p>
	 * @param phoneNo
	 * @return MpZone
	 * @see com.chinadrtv.erp.uc.service.MpZoneService#queryByPhoneNo(java.lang.String)
	 */ 
	public MpZone queryByPhoneNo(String phoneNo) {
		return mpZoneDao.get(phoneNo);
	}

}
