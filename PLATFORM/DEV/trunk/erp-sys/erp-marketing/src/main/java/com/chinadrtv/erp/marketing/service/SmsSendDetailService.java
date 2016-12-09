/*
 * @(#)SmsSendDetailService.java 1.0 2013-4-3上午9:53:46
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.service;

import java.util.Map;

import com.chinadrtv.erp.marketing.dto.SmsSendDetailDto;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-4-3 上午9:53:46
 * 
 */
public interface SmsSendDetailService {

	public Map<String, Object> getSmsSendDetailList(
			SmsSendDetailDto smsSendDetailDto, DataGridModel dataModel);

	public Map<String, Object> getSmsDetailListForcampaign(
			SmsSendDetailDto smsSendDetailDto, DataGridModel dataModel);

	public Long queryCountByBatchid(String batchid);

}
