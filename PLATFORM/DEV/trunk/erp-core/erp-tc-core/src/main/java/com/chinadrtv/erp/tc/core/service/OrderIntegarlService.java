/*
 * @(#)OrderdetService.java 1.0 2013-1-28下午2:49:08
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.tc.core.service;

import java.sql.SQLException;
import java.util.Map;

import com.chinadrtv.erp.core.service.GenericService;
import com.chinadrtv.erp.model.agent.Order;
/**
 * @author taoyawei
 * @version 1.0
 * @since 2013-1-29 下午2:49:08
 * 
 */
@Deprecated
public interface OrderIntegarlService extends GenericService<Order, String> {
	 String getIntegarlproc(Map<String, Object> mapval) throws SQLException;
}
