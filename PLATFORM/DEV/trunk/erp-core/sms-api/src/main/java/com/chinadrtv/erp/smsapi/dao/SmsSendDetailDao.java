/*
 * @(#)SmsSendDetail.java 1.0 2013-2-20上午10:09:19
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.smsapi.dao;

import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.smsapi.constant.DataGridModel;
import com.chinadrtv.erp.smsapi.model.SmsSendDetail;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-2-20 上午10:09:19
 * 
 */
public interface SmsSendDetailDao {

	public void saveDetailList(List<SmsSendDetail> smsSendDetail);

	public SmsSendDetail getByUuid(String uuid);

	public Long getCounts(String batchid, String status);

	public List<Map<String, Object>> getTimeList(String batchid, String status);

	public Long getTimeCount(String batchid, String date, String statusTime);

	public void updateDetailList(List<SmsSendDetail> smsSendDetail);

	public SmsSendDetail getSmsSendDetailsByBatchId(String batchId,
			String mobile);

	public Boolean removeByBatchid(String batchid);

	public Long getCountsByContactId(String contactId);

	public List<SmsSendDetail> getSmsSendDetailsByContactId(String contactId,
			DataGridModel dataModel);

}
