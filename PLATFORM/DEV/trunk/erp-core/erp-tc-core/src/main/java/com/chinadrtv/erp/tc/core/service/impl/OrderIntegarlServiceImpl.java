package com.chinadrtv.erp.tc.core.service.impl;

import java.sql.SQLException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.tc.core.dao.OrderIntegarlDao;
import com.chinadrtv.erp.tc.core.service.OrderIntegarlService;

/**
 * 积分服务 User: taoyawei 
 * Date: 13-1-24
 */
@Service
@Deprecated
public class OrderIntegarlServiceImpl extends GenericServiceImpl<Order, String> implements OrderIntegarlService {

	private static final Logger logger = LoggerFactory.getLogger(OrderIntegarlServiceImpl.class);
	
	@Autowired
	private OrderIntegarlDao orderIntegarlDao;

	@Override
	protected GenericDao<Order, String> getGenericDao() {
		return orderIntegarlDao;
	}

	public String getIntegarlproc(Map<String, Object> mapval) throws SQLException {
		logger.debug("begin getIntegarlproc");
		return orderIntegarlDao.getIntegarlproc(mapval);
	}

}
