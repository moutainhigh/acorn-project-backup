/*
 * @(#)CompanyAccountDao.java 1.0 2013-4-8下午2:40:31
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.oms.dao;

import java.util.List;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.CompanyAccount;

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
 * @since 2013-4-8 下午2:40:31 
 * 
 */
public interface CompanyAccountDao extends GenericDao<CompanyAccount, Long> {

	/**
	* @Description: 
	* @param companyContractId
	* @return
	* @return List<CompanyAccount>
	* @throws
	*/ 
	List<CompanyAccount> queryAccountListByContract(Long companyContractId);

}
