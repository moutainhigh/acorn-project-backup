/*
 * @(#)MemberTypeServiceImpl.java 1.0 2013-5-23下午1:42:34
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.sales.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.model.agent.MemberType;
import com.chinadrtv.erp.sales.dao.MemberTypeDao;
import com.chinadrtv.erp.sales.service.MemberTypeService;

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
 * @since 2013-5-23 下午1:42:34 
 * 
 */
@Service 
public class MemberTypeServiceImpl extends GenericServiceImpl<MemberType, String> implements MemberTypeService {

	@Autowired
	private MemberTypeDao memberTypeDao;
	
	@Override
	protected GenericDao<MemberType, String> getGenericDao() {
		return memberTypeDao;
	}

	/** 
	 * <p>Title: queryAll</p>
	 * <p>Description: </p>
	 * @return List<MemberType>
	 * @see com.chinadrtv.erp.sales.service.MemberTypeService#queryAll()
	 */ 
	public List<MemberType> queryAll() {
		return memberTypeDao.queryAll();
	}

}
