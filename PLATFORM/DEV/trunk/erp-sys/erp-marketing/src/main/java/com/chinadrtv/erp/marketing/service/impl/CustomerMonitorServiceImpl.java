/*
 * @(#)CustomerMonitorServiceImpl.java 1.0 2014-2-26下午2:31:10
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.marketing.dao.CustomerMonitorDao;
import com.chinadrtv.erp.marketing.service.CustomerMonitorService;
import com.chinadrtv.erp.model.marketing.CustomerMonitor;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2014-2-26 下午2:31:10
 * 
 */
@Service("customerMonitorService")
public class CustomerMonitorServiceImpl implements CustomerMonitorService {
	@Autowired
	private CustomerMonitorDao customerMonitorDao;
	private static final Logger logger = LoggerFactory
			.getLogger(CustomerMonitorServiceImpl.class);

	public Map<String, Object> list(CustomerMonitor customerMonitor,
			DataGridModel dataModel) {
		// TODO Auto-generated method stub
		Map<String, Object> result = new HashMap<String, Object>();
		List<CustomerMonitor> list = customerMonitorDao.query(customerMonitor,
				dataModel);
		Integer total = customerMonitorDao.queryCount(customerMonitor);
		result.put("rows", list);
		result.put("total", total);
		return result;
	}

	public Boolean saves(CustomerMonitor customerMonitor) {
		// TODO Auto-generated method stub
		Boolean result = false;
		try {
			customerMonitorDao.saves(customerMonitor);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("保存customerMonitor 失败 批次号"
					+ customerMonitor.getBatchCode() + e.getMessage());
			// TODO: handle exception
		}
		return result;
	}

	public Long getSeq() {
		// TODO Auto-generated method stub
		return customerMonitorDao.getSeq();
	}

	public Boolean updates(CustomerMonitor customerMonitor) {
		// TODO Auto-generated method stub
		Boolean result = false;
		try {
			customerMonitorDao.updates(customerMonitor);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("更新customerMonitor 失败 批次号"
					+ customerMonitor.getBatchCode() + e.getMessage());
			// TODO: handle exception
		}
		return result;
	}

}
