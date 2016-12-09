/*
 * @(#)PlubasInfoDao.java 1.0 2013年10月31日上午10:22:20
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.dal.iagent.dao;

import com.chinadrtv.common.dal.BaseDao;
import com.chinadrtv.model.iagent.PlubasInfo;

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
 * @since 2013年10月31日 上午10:22:20 
 * 
 */
public interface PlubasInfoDao extends BaseDao<PlubasInfo> {

	/**
	 * <p></p>
	 * @param skuCode
	 * @return
	 */
	Boolean checkSkuCode(String skuCode);

	/**
	 * <p></p>
	 * @param outSkuId
	 * @return
	 */
	String getSkuTitle(String outSkuId);

}
