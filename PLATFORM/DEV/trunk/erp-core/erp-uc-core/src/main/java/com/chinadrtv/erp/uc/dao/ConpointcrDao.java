package com.chinadrtv.erp.uc.dao;
import java.util.List;

import com.chinadrtv.erp.model.agent.Conpointcr;
import com.chinadrtv.erp.core.dao.GenericDao;

/**
 * 生成积分Dao 
 *  
 * @author haoleitao
 * @date 2013-5-6 下午1:26:42
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface ConpointcrDao extends GenericDao<Conpointcr, String>{
	/**
	* API-UC-25 ，查询积分生成历史
	* <p>Title: </p>
	* <p>Description: </p>
	* @param contactId: 客户ID
	* @param index:分页参数
	* @param size:分页参数
	* @return 
	 */
	public List<Conpointcr> getAllConpointcrByContactId(String contactId,int index, int size);
	/**
	* API-UC-25 ，查询积分生成历史的数量
	* <p>Title: </p>
	* <p>Description: </p>
	* @param contactId
	* @return
	 */
	public int getAllConpointcrByContactIdCount(String contactId);
	
}
