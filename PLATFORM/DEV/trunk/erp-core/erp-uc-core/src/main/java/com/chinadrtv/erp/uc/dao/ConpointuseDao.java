package com.chinadrtv.erp.uc.dao;

import java.util.List;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.agent.Conpointuse;

/**
 * 
 * 消费积分Dao
 *  
 * @author haoleitao
 * @date 2013-5-6 下午1:26:09
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface ConpointuseDao extends GenericDao<Conpointuse, String>{
	/**
	 * API-UC-27.	查询积分消费历史
	* <p>Title: </p>
	* <p>Description: </p>
	* @param contactId
	* @param index
	* @param size
	* @return
	 */
	public List<Conpointuse> getAllConpointuseByContactId(String contactId,int index, int size);
	/**
	 * API-UC-27.	查询积分消费历史的数量
	* <p>Title: </p>
	* <p>Description: </p>
	* @param contactId
	* @return
	 */
	public int getAllConpointuseByContactIdCount(String contactId);
	/**
	 * 获取用户消费积分
	 * @param contactId
	 * @return
	 */
	public Double getUseIntegralByContactId(String contactId);
}
