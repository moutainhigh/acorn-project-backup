/*
 * @(#)SmsSendDetailServiceImpl.java 1.0 2013-4-3上午9:57:44
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.marketing.dao.SmsSendDetailsDao;
import com.chinadrtv.erp.marketing.dto.SmsSendDetailDto;
import com.chinadrtv.erp.marketing.service.SmsSendDetailService;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-4-3 上午9:57:44
 * 
 */
@Service
public class SmsSendDetailServiceImpl implements SmsSendDetailService {

	@Autowired
	private SmsSendDetailsDao smsSendDetailsDao;

	/**
	 * 短信详情 分页查询
	 * 
	 * @param smsSendDetail
	 * 
	 * @param dataModel
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.marketing.service.SmsSendDetailService#getSmsSendDetailList
	 *      (com.chinadrtv.erp.smsapi.module.SmsSendDetail,
	 *      com.chinadrtv.erp.marketing.common.DataGridModel)
	 */
	public Map<String, Object> getSmsSendDetailList(
			SmsSendDetailDto smsSendDetailDto, DataGridModel dataModel) {
		// TODO Auto-generated method stub
		Map<String, Object> result = new HashMap<String, Object>();
		List<SmsSendDetailDto> list = smsSendDetailsDao.query(smsSendDetailDto,
				dataModel);
		int count = smsSendDetailsDao.queryCounts(smsSendDetailDto);
		result.put("total", count);
		result.put("rows", list);
		return result;

	}

	/**
	 * 短信详情 分页查询
	 * 
	 * @param smsSendDetail
	 * 
	 * @param dataModel
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.marketing.service.SmsSendDetailService#getSmsSendDetailList
	 *      (com.chinadrtv.erp.smsapi.module.SmsSendDetail,
	 *      com.chinadrtv.erp.marketing.common.DataGridModel)
	 */
	public Map<String, Object> getSmsDetailListForcampaign(
			SmsSendDetailDto smsSendDetailDto, DataGridModel dataModel) {
		// TODO Auto-generated method stub
		Map<String, Object> result = new HashMap<String, Object>();
		List<SmsSendDetailDto> list = smsSendDetailsDao.queryForcampaign(
				smsSendDetailDto, dataModel);
		int count = smsSendDetailsDao.queryCountsForcampaign(smsSendDetailDto);
		result.put("total", count);
		result.put("rows", list);
		return result;

	}

	/*
	 * (非 Javadoc) <p>Title: queryCountByBatchid</p> <p>Description: </p>
	 * 
	 * @param batchid
	 * 
	 * @return
	 * 
	 * @see
	 * com.chinadrtv.erp.marketing.service.SmsSendDetailService#queryCountByBatchid
	 * (java.lang.String)
	 */
	public Long queryCountByBatchid(String batchid) {
		// TODO Auto-generated method stub
		return smsSendDetailsDao.queryCountByBatchid(batchid);
	}

}
