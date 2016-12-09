/*
 * @(#)NamesDao.java 1.0 2013-1-22下午2:21:44
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.agent.ObAssignHist;

/**
 * <dl>
 *    <dt><b>Title:取数或分配记录操作DAO</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:取数或分配记录操作DAO</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author zhaizy
 * @version 1.0
 * @since 2013-1-22 下午2:21:44 
 * 
 */
public interface ObAssignHistDao extends GenericDao<ObAssignHist, java.lang.String>{

	/**
	 * 
	* @Description: 返回下一个序列值
	* @return
	* @return Long
	* @throws
	 */
	public Long getSeqNextValue();
}
