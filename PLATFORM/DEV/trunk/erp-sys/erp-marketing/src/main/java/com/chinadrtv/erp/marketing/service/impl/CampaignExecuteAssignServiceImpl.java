/*
 * @(#)CampaignExecuteSmsServiceImpl.java 1.0 2013-4-26上午11:27:32
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.service.impl;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.marketing.component.CampaignComponent;
import com.chinadrtv.erp.marketing.core.dao.CampaignBatchDao;
import com.chinadrtv.erp.marketing.core.dao.CampaignReceiverDao;
import com.chinadrtv.erp.marketing.core.dao.CustomerBatchDao;
import com.chinadrtv.erp.marketing.core.service.CampaignTypeService;
import com.chinadrtv.erp.marketing.core.service.CampaignTypeValueService;
import com.chinadrtv.erp.marketing.dao.CustomerGroupDao;
import com.chinadrtv.erp.marketing.service.CampaignExecuteService;
import com.chinadrtv.erp.marketing.service.CampaignService;
import com.chinadrtv.erp.model.marketing.Campaign;

/**
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * none</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * none</dd>
 * </dl>
 * 
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-4-26 上午11:27:32
 * 
 */
@Service("campaignAssignExecuteService")
public class CampaignExecuteAssignServiceImpl implements CampaignExecuteService {

	private static final Logger logger = LoggerFactory
			.getLogger(CampaignExecuteAssignServiceImpl.class);

	@Autowired
	private CampaignService campaignService;

	@Autowired
	private CampaignTypeService campaignTypeService;

	@Autowired
	private CampaignTypeValueService campaignTypeValueService;

	@Autowired
	private CampaignComponent campaignComponent;

	@Autowired
	private CampaignBatchDao campaignBatchDao;

	@Autowired
	private CampaignReceiverDao campaignReceiverDao;

	@Autowired
	private CustomerGroupDao customerGroupDao;

	@Autowired
	private CustomerBatchDao customerBatchDao;

	/**
	 * <p>
	 * Title: execute 短信营销计划任务
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param campaignId
	 * @throws Exception
	 * @see com.chinadrtv.erp.marketing.service.CampaignExecuteService#execute(java.lang.Long)
	 */
	public void execute(Campaign campaign) throws Exception {
		Date now = new Date();
		campaignReceiverDao.insertCampaignReceiver(campaign, now);
	}

	/*
	 * (非 Javadoc) <p>Title: executeCoupon</p> <p>Description: </p>
	 * 
	 * @param map
	 * 
	 * @throws Exception
	 * 
	 * @see
	 * com.chinadrtv.erp.marketing.service.CampaignExecuteService#executeCoupon
	 * (java.util.Map)
	 */
	@Override
	public void executeCoupon(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub

	}

}
