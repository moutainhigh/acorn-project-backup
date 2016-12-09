/*
 * @(#)CountyAllServiceImpl.java 1.0 2013-6-7上午10:33:00
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.uc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.model.agent.CountyAll;
import com.chinadrtv.erp.uc.dao.CountyAllDao;
import com.chinadrtv.erp.uc.service.CountyAllService;

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
 * @since 2013-6-7 上午10:33:00 
 * 
 */
@Service
public class CountyAllServiceImpl extends GenericServiceImpl<CountyAll, Integer> implements CountyAllService {
	
	@Autowired
	private CountyAllDao countyAllDao;
	
	@Override
	protected GenericDao<CountyAll, Integer> getGenericDao() {
		return countyAllDao;
	}

	/** 
	 * <p>Title: queryPostcode</p>
	 * <p>Description: </p>
	 * @param countyId
	 * @return String
	 * @see com.chinadrtv.erp.uc.service.CountyAllService#queryPostcode(java.lang.String)
	 */ 
	@Override
	public String queryPostcode(Integer countyId) {
		CountyAll countyAll = countyAllDao.get(countyId);
		if(null!=countyAll){
			return countyAll.getZipcode();
		}
		return null;
	}

	/** 
	 * <p>Title: queryAreaList</p>
	 * <p>Description: API-UC-44.查询区简码对应第三级地址</p>
	 * @param countyCode
	 * @param countyName
	 * @param areaCode
	 * @return List<CountyAll>
	 * @see com.chinadrtv.erp.uc.service.CountyAllService#queryAreaList(java.lang.String, java.lang.String, java.lang.String)
	 */ 
	@Override
	public List<CountyAll> queryAreaList(String countyCode, String countyName, String areaCode) {
		return countyAllDao.queryAreaList(countyCode, countyName, areaCode);
	}

	/** 
	 * <p>Title: queryByCountyId</p>
	 * <p>Description: API-UC-45.查询区地址对应省和市</p>
	 * @param countyId
	 * @return CountyAll
	 * @see com.chinadrtv.erp.uc.service.CountyAllService#queryByCountyId(java.lang.Integer)
	 */ 
	@Override
	public CountyAll queryByCountyId(Integer countyId) {
		return countyAllDao.get(countyId);
	}

}
