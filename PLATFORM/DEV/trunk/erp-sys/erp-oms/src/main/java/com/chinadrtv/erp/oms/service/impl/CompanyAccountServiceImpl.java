/*
 * @(#)CompanyAccountServiceImpl.java 1.0 2013-4-8下午2:44:05
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.oms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.model.CompanyAccount;
import com.chinadrtv.erp.oms.dao.CompanyAccountDao;
import com.chinadrtv.erp.oms.service.CompanyAccountService;

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
 * @since 2013-4-8 下午2:44:05 
 * 
 */
@Service
public class CompanyAccountServiceImpl extends GenericServiceImpl<CompanyAccount, Long> implements
		CompanyAccountService {

	@Autowired
	private CompanyAccountDao companyAccountDao;
	
	@Override
	protected GenericDao<CompanyAccount, Long> getGenericDao() {
		return companyAccountDao;
	}

	/* 
	* <p>Title: queryAccountListByContract</p>
	* <p>Description: </p>
	* @param companyContractId
	* @return
	* @see com.chinadrtv.erp.oms.service.CompanyAccountService#queryAccountListByContract(java.lang.String)
	*/ 
	public List<CompanyAccount> queryAccountListByContract(Long companyContractId) {
		return companyAccountDao.queryAccountListByContract(companyContractId);
	}

}
