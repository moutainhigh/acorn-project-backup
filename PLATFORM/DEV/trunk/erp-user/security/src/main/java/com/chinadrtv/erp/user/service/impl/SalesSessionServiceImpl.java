package com.chinadrtv.erp.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.chinadrtv.erp.user.handle.SalesSessionRegistry;
import com.chinadrtv.erp.user.service.SalesSessionService;
import com.google.code.ssm.api.InvalidateSingleCache;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.google.code.ssm.api.ReadThroughSingleCache;

/**
 * Created with IntelliJ IDEA. User: haoleitao Date: 13-12-30 Time: 上午10:44 To
 * change this template use File | Settings | File Templates.
 */

public class SalesSessionServiceImpl implements SalesSessionService {
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory
			.getLogger(SalesSessionServiceImpl.class);

	@Autowired
	private SalesSessionRegistry sessionRegistry;

	@ReadThroughSingleCache(namespace = "com.chinadrtv.erp.sales.service.SalesSessionService", expiration = 600000)
	public Object getSalesSession(@ParameterValueKeyProvider String sessionId) {
		logger.info("添加SalesSession......." + sessionId);
		Object obj = sessionRegistry.getSessionInformation(sessionId)
				.getPrincipal();

		return obj;
	}

	@InvalidateSingleCache(namespace = "com.chinadrtv.erp.sales.service.SalesSessionService")
	@Override
	public void removeSalessession(@ParameterValueKeyProvider String sessionId) {
		logger.info("删除SalesSession......." + sessionId);
	}

}
