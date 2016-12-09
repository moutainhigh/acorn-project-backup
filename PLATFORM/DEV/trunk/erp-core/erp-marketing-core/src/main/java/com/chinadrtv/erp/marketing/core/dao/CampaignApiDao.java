/*
 * @(#)CampaignApiDao.java 1.0 2013-5-10上午10:28:24
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.dao;

import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.marketing.core.dto.CampaignDto;
import com.chinadrtv.erp.model.marketing.Campaign;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-5-10 上午10:28:24
 * 
 */
public interface CampaignApiDao extends GenericDao<Campaign, java.lang.Long> {
	
	/**
	 * 
	* @Description: 根据落地号查询inbound进线营销计划
	* @param tollFreeNum
	* @param callTime
	* @return
	* @return List<CampaignDto>
	* @throws
	 */
	public List<CampaignDto> queryInboundCampaign(String tollFreeNum,
			String callTime);

	/**
	 * 
	* @Description: 根据执行部门查询老客户
	* @param execDepartment
	* @return
	* @return List<CampaignDto>
	* @throws
	 */
	public List<CampaignDto> queryOldCustomerCampaign(String execDepartment);

	/**
	 * 根据落地号查询 媒体名称
	 * 
	 * @Description: TODO
	 * @param dnis
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */
	public Map<String, Object> queryMedia(String dnis);
	
	/**
	 * 
	* @Description: 查询自建任务的营销计划
	* @return
	* @return List<CampaignDto>
	* @throws
	 */
	public List<CampaignDto> queryCampaignForTask();

}
