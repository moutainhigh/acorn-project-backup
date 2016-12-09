/*
 * @(#)OldContactDao.java 1.0 2013-5-9下午3:19:07
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.uc.dao;

import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.agent.OldContactex;
import com.chinadrtv.erp.uc.dto.ObCustomerDto;
import com.chinadrtv.erp.user.model.AgentUser;

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
 * @since 2013-5-9 下午3:19:07 
 * 
 */
public interface OldContactexDao extends GenericDao<OldContactex, String> {

	/**
	 * <p>分页查询行数</p>
	 * @param obCustomerDto
	 * @return
	 */
	Integer queryPageCount(ObCustomerDto obCustomerDto);

	/**
	 * <p>分页查询</p>
	 * @param dataGridModel
	 * @param obCustomerDto
	 * @return
	 */
	List<ObCustomerDto> queryPageList(DataGridModel dataGridModel, ObCustomerDto obCustomerDto);
	
	/**
	 * <p>Contact Map 转换为 ObCustomerDto</p>
	 * @param map
	 * @return ObCustomerDto
	 */
	public ObCustomerDto convert2ObCustomerDto(Map<String, Object> map);
	
	/**
	 * <p>查询客户归属</p>
	 * @param dto
	 * @param userId
	 * @return Integer
	 */
	public Integer queryCustomerOwner(ObCustomerDto dto, String userId);

	/**
	 * <p>查询老客户绑定坐席</p>
	 * @param contactId
	 * @return AgentUser
	 */
	AgentUser queryBindAgentUser(String contactId);

}
