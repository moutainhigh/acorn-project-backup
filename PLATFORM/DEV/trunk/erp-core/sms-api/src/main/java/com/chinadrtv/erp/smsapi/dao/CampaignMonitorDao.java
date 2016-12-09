/*
 * @(#)CampaignMonitorDao.java 1.0 2013-7-8下午2:41:43
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.smsapi.dao;

import java.util.List;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;
import com.chinadrtv.erp.smsapi.dto.CampaignMonitorDto;
import com.chinadrtv.erp.smsapi.model.CampaignMonitor;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-7-8 下午2:41:43
 * 
 */
public interface CampaignMonitorDao extends
		GenericDao<CampaignMonitor, java.lang.Long> {

	public List<CampaignMonitorDto> getCampaignMonitorPageList(
			CampaignMonitorDto campaignMonitor, DataGridModel dataGridModel);

	public Integer getCampaignMonitorCount(CampaignMonitorDto campaignMonitor);

	public CampaignMonitor getByCampaignId(Long campaignId);
}
