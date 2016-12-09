/*
 * @(#)NamesDao.java 1.0 2013-1-22下午2:21:44
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.tc.core.dao;

import java.util.List;

import com.chinadrtv.erp.core.dao.GenericDao;
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
 * @since 2013-1-22 下午2:21:44 
 * 
 */
public interface NamesDao extends GenericDao<Names, java.lang.String>{

	/**
	* @Description: TODO
	* @return
	* @return List<Names>
	* @throws
	*/ 
	List<Names> queryNamesList();

	/**
	* @Description: TODO
	* @param mailTypeNames
	* @param mailtype
	* @return
	* @return String
	* @throws
	*/ 
	String getValueByTidAndId(String tid, String id);

    Names getNamesByTidAndId(String tid, String id);

    void addNames(Names names);

}
