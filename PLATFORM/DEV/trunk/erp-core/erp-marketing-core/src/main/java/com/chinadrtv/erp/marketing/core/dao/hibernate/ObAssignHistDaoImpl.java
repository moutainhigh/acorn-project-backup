/*
 * @(#)NamesDaoImpl.java 1.0 2013-1-22下午2:24:24
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.dao.hibernate;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.marketing.core.dao.ObAssignHistDao;
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
 * @since 2013-1-22 下午2:24:24 
 * 
 */
@Repository
public class ObAssignHistDaoImpl extends GenericDaoHibernate<ObAssignHist, java.lang.String> implements ObAssignHistDao {

	/**
	* <p>Title: </p>
	* <p>Description: </p>
	* @param persistentClass
	*/ 
	public ObAssignHistDaoImpl() {
		super(ObAssignHist.class);
		// TODO Auto-generated constructor stub
	}

	/**
	* <p>Title: getSeqNextValue</p>
	* <p>Description: </p>
	* @return
	*/ 
	public Long getSeqNextValue() {
		StringBuffer sql = new StringBuffer(
				"select iagent.seqob_assign_hist.Nextval from dual ");
		SQLQuery query = getSession().createSQLQuery(sql.toString());

		List list = query.list();
		BigDecimal nextVal = (BigDecimal) list.get(0);
		return Long.valueOf(nextVal.toString());
	}
}
