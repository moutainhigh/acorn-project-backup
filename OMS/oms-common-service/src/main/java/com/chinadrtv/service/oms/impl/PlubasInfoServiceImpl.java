/*
 * @(#)PlubasInfoServiceImpl.java 1.0 2014-10-17上午10:32:28
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.service.oms.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.dal.iagent.dao.PlubasInfoDao;
import com.chinadrtv.service.oms.PlubasInfoService;

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
 * @since 2014-10-17 上午10:32:28 
 * 
 */
@Service
public class PlubasInfoServiceImpl implements PlubasInfoService {

	@Autowired
	private PlubasInfoDao plubasInfoDao;
	/** 
	 * <p>Title: checkSkuCode</p>
	 * <p>Description: </p>
	 * @param skuCode
	 * @return
	 * @see com.chinadrtv.service.oms.PlubasInfoService#checkSkuCode(java.lang.String)
	 */ 
	@Override
	public Boolean checkSkuCode(String skuCode) {
		return plubasInfoDao.checkSkuCode(skuCode);
	}

	/** 
	 * <p>Title: getSkuTitle</p>
	 * <p>Description: </p>
	 * @param outSkuId
	 * @return
	 * @see com.chinadrtv.service.oms.PlubasInfoService#getSkuTitle(java.lang.String)
	 */ 
	@Override
	public String getSkuTitle(String outSkuId) {
		return plubasInfoDao.getSkuTitle(outSkuId);
	}

}
