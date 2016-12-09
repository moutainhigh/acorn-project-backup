/*
 * @(#)MemberLevelDao.java 1.0 2013-5-23下午1:38:53
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.sales.dao;

import java.util.List;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.agent.MemberLevel;

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
 * @since 2013-5-23 下午1:38:53 
 * 
 */
public interface MemberLevelDao extends GenericDao<MemberLevel, String> {

	/**
	 * <p>查询所有MemberLevel</p>
	 * @return List<MemberLevel>
	 */
	List<MemberLevel> queryAll();

}
