/*
 * @(#)TCallhistDao.java 1.0 2014年1月14日上午11:12:52
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.uc.dao;

import java.util.List;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.marketing.core.dto.LeadInteractionSearchDto;
import com.chinadrtv.erp.model.agent.TCallhist;
import com.chinadrtv.erp.uc.dto.TCallHistDto;

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
 * @since 2014年1月14日 上午11:12:52 
 * 
 */
public interface TCallhistDao extends GenericDao<TCallhist, String> {

	/**
	 * <p>查询IVR接通可分配数量</p>
	 * @param searchDto
	 * @return Integer
	 */
	Integer queryIvrConnectedAvaliableQty(LeadInteractionSearchDto searchDto);

	/**
	 * <p>查询IVR接通可分配列表</p>
	 * @param liDto
	 * @return List<CallHist>
	 */
	List<TCallHistDto> queryIvrConnectedAvaliableList(LeadInteractionSearchDto liDto);
}
