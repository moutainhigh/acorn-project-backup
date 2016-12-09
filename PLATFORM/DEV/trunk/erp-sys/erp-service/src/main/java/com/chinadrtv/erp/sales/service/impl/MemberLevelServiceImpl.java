/*
 * @(#)MemberLevelServiceImpl.java 1.0 2013-5-23下午1:44:52
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.sales.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.model.agent.MemberLevel;
import com.chinadrtv.erp.sales.dao.MemberLevelDao;
import com.chinadrtv.erp.sales.service.MemberLevelService;

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
 * @since 2013-5-23 下午1:44:52 
 * 
 */
@Service
public class MemberLevelServiceImpl extends GenericServiceImpl<MemberLevel, String> implements MemberLevelService {

	@Autowired
	private MemberLevelDao memberLevelDao;
	
	@Override
	protected GenericDao<MemberLevel, String> getGenericDao() {
		return memberLevelDao;
	}

	/** 
	 * <p>Title: queryAll</p>
	 * <p>Description: </p>
	 * @return List<MemberLevel>
	 * @see com.chinadrtv.erp.sales.service.MemberLevelService#queryAll()
	 */ 
	public List<MemberLevel> queryAll() {
		return memberLevelDao.queryAll();
	}

	public Map<String, MemberLevel> queryAlltoMap() {
		// TODO Auto-generated method stub
		List<MemberLevel> list = queryAll();
		Map<String, MemberLevel> map = new HashMap();
		for(MemberLevel ml :list){
		 map.put(ml.getMemberlevelid(), ml);
		}
		
		return map;
	}

}
