/*
 * @(#)CampaignExecuteSmsServiceImpl.java 1.0 2013-4-26上午11:27:32
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.exception.ExceptionConstant;
import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.marketing.component.CampaignComponent;
import com.chinadrtv.erp.marketing.core.common.Constants;
import com.chinadrtv.erp.marketing.core.dao.CampaignBatchDao;
import com.chinadrtv.erp.marketing.core.dao.CampaignReceiverDao;
import com.chinadrtv.erp.marketing.core.dto.SmssnedDto;
import com.chinadrtv.erp.marketing.core.service.CampaignTypeService;
import com.chinadrtv.erp.marketing.core.service.CampaignTypeValueService;
import com.chinadrtv.erp.marketing.core.util.StringUtil;
import com.chinadrtv.erp.marketing.dao.SmsSendDetailsDao;
import com.chinadrtv.erp.marketing.dao.SmsTemplateDao;
import com.chinadrtv.erp.marketing.service.CampaignExecuteService;
import com.chinadrtv.erp.marketing.service.CampaignService;
import com.chinadrtv.erp.model.marketing.Campaign;
import com.chinadrtv.erp.model.marketing.CampaignBatch;
import com.chinadrtv.erp.smsapi.model.SmsSend;
import com.chinadrtv.erp.smsapi.service.GroupSmsSendService;

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
@Service("campaignSmsExecuteService")
public class CampaignExecuteSmsServiceImpl implements CampaignExecuteService {
	private static final Logger logger = LoggerFactory
			.getLogger(CampaignExecuteSmsServiceImpl.class);
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
	private SmsSendDetailsDao smsSendDetailsDao;

	@Autowired
	private SmsTemplateDao smsTemplateDao;
	@Autowired
	private GroupSmsSendService groupSmsSendService;

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
		Boolean flag = false;
		Map<String, String> smsSet = campaignTypeValueService
				.queryParamTypeValue(campaign.getId());
		SmssnedDto smsSendDto = getSmsSendDto(smsSet);
		smsSendDto.setGroupCode(campaign.getGroup().getGroupCode());
		smsSendDto.setGroupName(campaign.getGroup().getGroupName());
		SmsSend smsSend = campaignComponent.saveByBatchId(
				campaign.getCreateUser(), smsSendDto, campaign.getDepartment());

		CampaignBatch campaignBatch = new CampaignBatch();
		campaignBatch.setBatchId(smsSend.getBatchId());
		campaignBatch.setCampaignId(campaign.getId());
		campaignBatch.setCampaignTypeId(campaign.getCampaignTypeId());
		campaignBatch.setStatus(Constants.CAMPAIGN_BATCH_STATUS_NORMAL);
		Date now = new Date();
		Map result = new HashMap<String, Object>();
		try {
			campaignBatchDao.save(campaignBatch);
			result = campaignComponent.newSmsSend(smsSend, campaign, now);
			campaignReceiverDao.insertCampaignReceiver(campaign, now);
			smsSendDetailsDao.updateForcampaign(
					Constants.SMS_STOP_STATUS_RECOVER,
					Constants.SMS_STOP_STATUS_SUCCESS, campaign.getId());
		} catch (Exception e) {
			logger.error("创建营销计划失败" + campaign.getId() + e.getMessage());
			throw new ServiceException(ExceptionConstant.SERVICE_EXCEPTION,
					e.getMessage());
			// TODO: handle exception
		}
		if (result != null && !result.isEmpty()) {
			flag = groupSmsSendService.sendXml(smsSend.getBatchId(), result);
		}

	}

	public SmssnedDto getSmsSendDto(Map<String, String> smsSet) {
		SmssnedDto smsSendDto = new SmssnedDto();

		Iterator<String> it = smsSet.keySet().iterator();
		String key = null;
		while (it.hasNext()) {
			key = it.next();
			smsSendDto.setParamValues(smsSendDto, key, smsSet.get(key));
		}
		if (!StringUtil.isNullOrBank(smsSendDto.getTemplateId())) {
			smsSendDto.setDynamicTemplate(smsTemplateDao.get(
					Long.valueOf(smsSendDto.getTemplateId()))
					.getDynamicTemplate());
		}
		return smsSendDto;
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
	public void executeCoupon(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		SmsSend smsSend = (SmsSend) map.get("smsSend");
		Campaign campaign = (Campaign) map.get("campaign");
		CampaignBatch campaignBatch = new CampaignBatch();
		campaignBatch.setBatchId(smsSend.getBatchId());
		campaignBatch.setCampaignId(campaign.getId());
		campaignBatch.setCampaignTypeId(campaign.getCampaignTypeId());
		campaignBatch.setStatus(Constants.CAMPAIGN_BATCH_STATUS_NORMAL);
		Date now = new Date();
		Map result = new HashMap<String, Object>();
		try {
			campaignBatchDao.save(campaignBatch);
			result = campaignComponent.newSmsSendByCoupon(smsSend, campaign,
					now);
			campaignReceiverDao.insertCampaignReceiver(campaign, now);
			smsSendDetailsDao.updateForcampaign(
					Constants.SMS_STOP_STATUS_RECOVER,
					Constants.SMS_STOP_STATUS_SUCCESS, campaign.getId());
		} catch (Exception e) {
			logger.error("创建营销计划失败" + campaign.getId() + e.getMessage());
			throw new ServiceException(ExceptionConstant.SERVICE_EXCEPTION,
					e.getMessage());
			// TODO: handle exception
		}
		if (result != null && !result.isEmpty()) {
			groupSmsSendService.sendXml(smsSend.getBatchId(), result);
		}

	}
}
