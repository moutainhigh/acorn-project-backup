/*
 * @(#)LeadTypeValueServiceImpl.java 1.0 2013-6-4上午10:51:01
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.marketing.core.dao.CampaignDao;
import com.chinadrtv.erp.marketing.core.dao.LeadTypeParamsDao;
import com.chinadrtv.erp.marketing.core.dao.LeadTypeValueDao;
import com.chinadrtv.erp.marketing.core.dto.ScmSetDto;
import com.chinadrtv.erp.marketing.core.service.LeadTypeValueService;
import com.chinadrtv.erp.model.marketing.Campaign;
import com.chinadrtv.erp.model.marketing.LeadTypeParams;
import com.chinadrtv.erp.model.marketing.LeadTypeValue;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-6-4 上午10:51:01
 * 
 */
@Service("leadTypeValueService")
public class LeadTypeValueServiceImpl implements LeadTypeValueService {
	@Autowired
	private LeadTypeValueDao leadTypeValueDao;
	
	@Autowired
	private LeadTypeParamsDao leadTypeParamsDao;
	
	@Autowired
	private CampaignDao campaignDao;

	/**
	 * (非 Javadoc)
	 * <p>
	 * Title: insertLeadTypeValue
	 * </p>
	 * <p>
	 * Description: 插入实体
	 * </p>
	 * 
	 * @param leadTypeValue
	 * 
	 * @see com.chinadrtv.erp.marketing.core.service.LeadTypeValueService#
	 *      insertLeadTypeValue(com.chinadrtv.erp.model.marketing.LeadTypeValue)
	 */
	public void insertLeadTypeValue(LeadTypeValue leadTypeValue) {
		// TODO Auto-generated method stub
		leadTypeValueDao.saveOrUpdate(leadTypeValue);
	}
	
	public Map<String, String> queryParamTypeValue(Long campaignId) {

		List<LeadTypeValue> paramsList = leadTypeValueDao.queryList(campaignId);
		Map<String, String> paramsMap = new HashMap<String, String>();
		for (LeadTypeValue typeValue : paramsList) {
			paramsMap.put(typeValue.getCode(), typeValue.getValue());
		}

		return paramsMap;
	}

	public void saveLeadParamTypeValue(Long campaignId, String user,
			ScmSetDto scmSetDto) {

		leadTypeValueDao.removeAll(campaignId);

		Campaign campaign = campaignDao.get(campaignId);
		List<LeadTypeParams> paramsList = leadTypeParamsDao.getParamsList(campaign.getLeadTypeId());
		LeadTypeValue typeValue = null;
		for (LeadTypeParams params : paramsList) {
			typeValue = new LeadTypeValue();
			typeValue.setCampaignId(campaignId);
			typeValue.setCreateDate(new Date());
			typeValue.setUpdateDate(new Date());
			typeValue.setCreateUser(user);
			typeValue.setUpdateUser(user);
			typeValue.setLeadTypeParamsId(params.getId());
			typeValue.setCode(params.getCode());
			typeValue.setValue(scmSetDto.genParamValues(params.getCode()));
			leadTypeValueDao.saveOrUpdate(typeValue);
		}
	}
}
