/*
 * @(#)CompanyAccountService.java 1.0 2013-4-8下午2:43:29
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.oms.service;

import java.util.List;

import com.chinadrtv.erp.core.service.GenericService;
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
 * @since 2013-4-8 下午2:43:29 
 * 
 */
public interface CompanyAccountService extends GenericService<CompanyAccount, Long> {

	/**
	* @Description: 
	* @param id
	* @return
	* @return List<CompanyAccount>
	* @throws
	*/ 
	List<CompanyAccount> queryAccountListByContract(Long companyContractId);

}
