/*
 * @(#)ObContactDao.java 1.0 2013-5-9下午3:00:01
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.uc.dao;

import java.util.List;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.agent.ObContact;
import com.chinadrtv.erp.uc.dto.ObCustomerDto;

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
 * @since 2013-5-9 下午3:00:01 
 * 
 */
public interface ObContactDao extends GenericDao<ObContact, String> {

	/**
	 * <p>分页查询行数</p>
	 * @param obCustomerDto
	 * @return
	 */
	int queryPageCount(ObCustomerDto obCustomerDto);

	/**
	 * <p>分页查询</p>
	 * @param dataGridModel
	 * @param obCustomerDto
	 * @return List<ObCustomerDto>
	 */
	List<ObCustomerDto> queryPageList(DataGridModel dataGridModel, ObCustomerDto obCustomerDto);

	/**
	 * <p>API-UC-37.查询客户取数的座席</p>
	 * @param contactId
	 * @return String
	 */
	String queryAgentUser(String contactId);
	

}
