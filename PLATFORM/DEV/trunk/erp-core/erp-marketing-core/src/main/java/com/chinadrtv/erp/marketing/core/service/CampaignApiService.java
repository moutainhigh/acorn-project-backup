/*
 * @(#)CampaignApiService.java 1.0 2013-5-10上午10:55:49
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.marketing.core.dto.CampaignDto;
import com.chinadrtv.erp.marketing.core.dto.MediaPlan;
import com.chinadrtv.erp.model.marketing.Campaign;

/**
 * 营销计划接口
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-5-10 上午10:55:49
 * 
 */
public interface CampaignApiService {

	public Campaign getCampaignById(Long id);
	
	public CampaignDto getCampaign(Long id);
	
	/**
	 * 
	 * @Description: 根据400 落地号码 呼入时间查询 营销计划
	 * @param dnis
	 * @param callTime
	 * @return
	 * @return List<CampaignDto>
	 * @throws
	 */
	public CampaignDto queryInboundCampaign(String dnis, String callTime)
			throws ServiceException;

	/**
	 * 查询默认营销活动
	* @Description: TODO
	* @return
	* @return List<CampaignDto>
	* @throws
	 */
	public List<CampaignDto> queryDefaultCampaign();
	/**
	 * 
	 * @Description: 查询老客户营销活动
	 * @param execDepartment
	 * @return
	 * @return List<CampaignDto>
	 * @throws
	 */
	public List<CampaignDto> queryOldCustomerCampaign(String execDepartment)
			throws ServiceException;

	/**
	 * 根据落地号码 查询媒体名称和400电话
	 * 
	 * @Description: TODO
	 * @param dnis
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */
    @Deprecated
	public Map<String, Object> queryMediaByDnis(String dnis)
			throws ServiceException;

	/**
	 * 
	* @Description: 查询自建任务营销计划
	* @return
	* @return List<CampaignDto>
	* @throws
	 */
	public List<CampaignDto> queryCampaignForTask();
	
	/**
	 * 
	* @Description: 提供媒体计划接口，将媒体计划按照落地号放入缓存
	* @param dnis
	* @param campaign
	* @return
	* @throws ServiceException
	* @return MediaPlan
	* @throws
	 */
	public MediaPlan getMediaPlan(String dnis,Campaign campaign) throws ServiceException;
	
	/**
	 * 
	* @Description: 在营销计划中查询媒体计划，如果400号码为空，则返回一个没有信息的媒体计划对象
	* @param dnis
	* @return
	* @throws ServiceException
	* @return MediaPlan
	* @throws
	 */
	public MediaPlan getMediaPlan(String dnis) throws ServiceException;


	/**
	 * 
	* @Description: 按营销类型查询营销计划
	* @param type
	* @return
	* @return List<Campaign>
	* @throws
	 */
    public List<Campaign> findCampaignByType(Long type);
    
    /**
	 * 
	* @Description: 根据jobId查询外呼人工分配线索营销计划列表
	* @param jobId
	* @return
	* @return List<Campaign>
	* @throws
	 */
    public List<Campaign> queryList(String jobId);

}
