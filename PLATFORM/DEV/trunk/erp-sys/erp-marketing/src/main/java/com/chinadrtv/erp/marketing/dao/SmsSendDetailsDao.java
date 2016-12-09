/*
 * @(#)SmsSendDetailsDao.java 1.0 2013-4-3上午10:08:42
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.dao;

import java.util.List;

import com.chinadrtv.erp.marketing.dto.SmsSendDetailDto;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-4-3 上午10:08:42
 * 
 */
public interface SmsSendDetailsDao {
	public List<SmsSendDetailDto> query(SmsSendDetailDto smsSendDetailDto,
			DataGridModel dataModel);

	public Integer queryCounts(SmsSendDetailDto smsSendDetailDto);

	public Long queryCountByBatchid(String batchid);

	public List<SmsSendDetailDto> queryForcampaign(
			SmsSendDetailDto smsSendDetailDto, DataGridModel dataModel);

	public Integer queryCountsForcampaign(SmsSendDetailDto smsSendDetailDto);

	public Integer updateForcampaign(String feedbackStatus,
			String oldfeedbackStatus, Long campaignId);

}
