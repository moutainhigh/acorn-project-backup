/*
 * @(#)Discourse.java 1.0 2013-5-20上午10:46:31
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.exception.ExceptionConstant;
import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.marketing.core.dao.DiscourseDao;
import com.chinadrtv.erp.marketing.core.dto.DiscourseDto;
import com.chinadrtv.erp.marketing.core.service.DiscourseApiService;
import com.chinadrtv.erp.marketing.core.util.StringUtil;
import com.chinadrtv.erp.model.marketing.Discourse;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-5-20 上午10:46:31
 * 
 */
@Service("discourseApiService")
public class DiscourseApiServiceImpl implements DiscourseApiService {
	private static final Logger logger = LoggerFactory
			.getLogger(DiscourseApiServiceImpl.class);
	@Autowired
	private DiscourseDao discourseDao;

	/**
	 * 
	 * @Description: 查找产品话术
	 * 
	 * @param deparmetnId
	 *            部门id
	 * 
	 * @param productCode
	 *            产品码
	 * 
	 * @param DataGridModel
	 *            分页类
	 * @return Map<String,Object>
	 * 
	 * @throws
	 */
	public Map<String, Object> findDiscoursePageList(String deparmetnId,
			String productCode, DataGridModel dataModel) {
		// TODO Auto-generated method stub
		logger.info("查询话术模板：department" + deparmetnId + "=productCode:"
				+ productCode);
		Map<String, Object> result = new HashMap<String, Object>();
		DiscourseDto discourse = new DiscourseDto();
		discourse.setDepartment(deparmetnId);
		discourse.setProductCode(productCode);
		result.put("total", discourseDao.queryCounts(discourse));
		result.put("rows", discourseDao.query(discourse, dataModel));
		return result;
	}

	/**
	 * (非 Javadoc)
	 * <p>
	 * Title: getDiscourseByProductCode
	 * </p>
	 * <p>
	 * Description: 根据商品简码获得话术
	 * </p>
	 * 
	 * @param productCode
	 * 
	 * @return
	 * @throws ServiceException
	 * 
	 * @see com.chinadrtv.erp.marketing.core.service.DiscourseApiService#
	 *      getDiscourseByProductCode(java.lang.String)
	 */
	public Discourse getDiscourseByProductCode(String productCode)
			throws ServiceException {
		// TODO Auto-generated method stub
		if (StringUtil.isMobileNO(productCode)) {
			throw new ServiceException(
					ExceptionConstant.SERVICE_PARAMETER_EXCEPTION, "201");
		}
		return discourseDao.queryByProductCode(productCode);
	}
}
