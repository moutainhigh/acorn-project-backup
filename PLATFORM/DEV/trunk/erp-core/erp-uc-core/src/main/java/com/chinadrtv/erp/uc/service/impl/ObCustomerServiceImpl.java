/*
 * @(#)ObCustomerServiceImpl.java 1.0 2013-5-9下午3:21:38
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.uc.service.impl;

import static com.chinadrtv.erp.uc.constants.CustomerConstant.OB_CUSTOMER_TYPE_OLD;
import static com.chinadrtv.erp.uc.constants.CustomerConstant.OB_CUSTOMER_TYPE_ORDER;
import static com.chinadrtv.erp.uc.constants.CustomerConstant.OB_CUSTOMER_TYPE_SELF;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.core.exception.BizException;
import com.chinadrtv.erp.marketing.core.dto.CampaignTaskDto;
import com.chinadrtv.erp.marketing.core.service.CampaignBPMTaskService;
import com.chinadrtv.erp.uc.dao.ContactDao;
import com.chinadrtv.erp.uc.dao.ObContactDao;
import com.chinadrtv.erp.uc.dao.OldContactexDao;
import com.chinadrtv.erp.uc.dto.ObCustomerDto;
import com.chinadrtv.erp.uc.service.ObCustomerService;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.util.SecurityHelper;

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
 * @since 2013-5-9 下午3:21:38 
 * 
 */
@Service
public class ObCustomerServiceImpl implements ObCustomerService {

	@Autowired
	private OldContactexDao oldContactexDao;
	@Autowired
	private ObContactDao obContactDao;
	@Autowired
	private ContactDao contactDao;
	@Autowired
	private CampaignBPMTaskService campaignBPMTaskService;
    
	/** 
	 * <p>Title: API-UC-33.查询我的客户（OUTBOND版本）</p>
	 * <p>Description: </p>
	 * @param gataGridModel
	 * @param ObCustomerDto
	 * @return Map<String, Object>
	 * @see com.chinadrtv.erp.uc.service.ContactService#queryObCustomer(com.chinadrtv.erp.uc.common.DataGridModel, com.chinadrtv.erp.uc.dto.CustomerDto)
	 */ 
	@SuppressWarnings("unchecked")
	public Map<String, Object> queryObCustomer(DataGridModel dataGridModel, ObCustomerDto obCustomerDto) {
		
		Integer customerFrom = obCustomerDto.getCustomerFrom();
		Map<String, Object> pageMap = new HashMap<String, Object>();
	
		Date beginDate = obCustomerDto.getBeginDate();
		Date endDate = obCustomerDto.getEndDate();
		
		long diff = 0;
		if(customerFrom == OB_CUSTOMER_TYPE_SELF || customerFrom == OB_CUSTOMER_TYPE_ORDER) {
			if(null==beginDate || null==endDate){
				throw new BizException("起始时间不能为空");
			}
			if(endDate.compareTo(beginDate)<0){
				throw new BizException("结束时间必需大于开始时间");
			}
			
			diff = endDate.getTime()-beginDate.getTime();
		}
		
		//取数时间可跨3个月
		if(customerFrom == OB_CUSTOMER_TYPE_SELF){
			if(diff/1000/3600/24/31>=3){
				throw new BizException("起始时间跨度不能大于3个月");
			}
		//成单时间可跨7天	
		}else if(customerFrom == OB_CUSTOMER_TYPE_ORDER){
			if(diff/1000/3600/24>7){
				throw new BizException("起始时间跨度不能大于7天");
			}
		}
		
		//绑定老客户
		if(customerFrom==OB_CUSTOMER_TYPE_OLD){
			Integer totalCount = oldContactexDao.queryPageCount(obCustomerDto);
			List<ObCustomerDto> pageList = oldContactexDao.queryPageList(dataGridModel, obCustomerDto);
			pageMap.put("total", totalCount);
			pageMap.put("rows", pageList);
		//自主取数	
		}else if(customerFrom==OB_CUSTOMER_TYPE_SELF){
			//TODO pre0.1之后切换到COMPAIGN_RECEIVER--暂不修改      查询取数和我的客户查询
			int totalCount = obContactDao.queryPageCount(obCustomerDto);
			List<ObCustomerDto> pageList = obContactDao.queryPageList(dataGridModel, obCustomerDto);
			pageMap.put("total", totalCount);
			pageMap.put("rows", pageList);
		//成单客户	
		}else if(customerFrom==OB_CUSTOMER_TYPE_ORDER){
			int totalCount = contactDao.queryPageCount(obCustomerDto);
			List<ObCustomerDto> pageList = contactDao.queryPageList(dataGridModel, obCustomerDto);
			pageMap.put("total", totalCount);
			pageMap.put("rows", pageList);
		}
		List<ObCustomerDto> pageList = (List<ObCustomerDto>) pageMap.get("rows");
		
		AgentUser user = SecurityHelper.getLoginUser();
		obCustomerDto.setAgentId(user.getUserId());
		for(ObCustomerDto _dto: pageList){
			CampaignTaskDto campaignTaskDto = new CampaignTaskDto();
			campaignTaskDto.setCustomerID(_dto.getContactid());
			campaignTaskDto.setUserID(user.getUserId());
			Integer taskQty = campaignBPMTaskService.queryUnStartedCampaignTaskCount(campaignTaskDto);
			taskQty = null==taskQty? 0 : taskQty;
			_dto.setTaskQty(taskQty);
			_dto.setCustomerFrom(customerFrom);
		}
		
		return pageMap;
	}
}
