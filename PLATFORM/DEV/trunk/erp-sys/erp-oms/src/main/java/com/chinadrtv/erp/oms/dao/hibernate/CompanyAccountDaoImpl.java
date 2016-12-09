/*
 * @(#)CompanyAccountDao.java 1.0 2013-4-8下午2:41:06
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.oms.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.ParameterLong;
import com.chinadrtv.erp.model.CompanyAccount;
import com.chinadrtv.erp.oms.dao.CompanyAccountDao;

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
 * @since 2013-4-8 下午2:41:06 
 * 
 */
@Repository
public class CompanyAccountDaoImpl extends GenericDaoHibernate<CompanyAccount, Long> implements CompanyAccountDao {

	/**
	* <p>Title: </p>
	* <p>Description: </p>
	* @param persistentClass
	*/ 
	public CompanyAccountDaoImpl() {
		super(CompanyAccount.class);
	}

	/* 
	* <p>Title: queryAccountListByContract</p>
	* <p>Description: </p>
	* @param companyContractId
	* @return
	* @see com.chinadrtv.erp.oms.dao.CompanyAccountDao#queryAccountListByContract(java.lang.String)
	*/ 
	public List<CompanyAccount> queryAccountListByContract(Long companyContractId) {
		String hql = "select ca from CompanyAccount ca where ca.companyContract.id=:companyContractId";
		return this.findList(hql, new ParameterLong("companyContractId", companyContractId));
	}

}
