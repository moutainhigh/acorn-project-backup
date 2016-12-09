/*
 * @(#)DiscourseApiService.java 1.0 2013-5-20上午10:41:31
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.service;

import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.model.agent.ObContact;
import com.chinadrtv.erp.model.marketing.Discourse;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-5-20 上午10:41:31
 * 
 */
public interface ObContactService {


	/**
	 * 
	* @Description: 根据jobid查询取数
	* @param jobId
	* @param limit
	* @return
	* @return List<ObContact>
	* @throws
	 */
	public List<ObContact> query(String jobId,int limit);

}
