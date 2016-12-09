/*
 * @(#)SmsSendDao.java 1.0 2013-2-20上午9:43:32
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.smsapi.dao;

import java.util.List;

import com.chinadrtv.erp.smsapi.model.SmsSend;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-2-20 上午9:43:32
 * 
 */
public interface SmsSendDao {

	public void saveSmsSend(SmsSend smsSend);

	public SmsSend getByUuid(String uuid);

	public void updateSmsSend(String uuid, String status, String errorcode,
			String errorMsg);

	public Long getSeq();

	public SmsSend getByBatchid(String batchid);

	public List<SmsSend> getbyCampaign(Long campaignId);

}
