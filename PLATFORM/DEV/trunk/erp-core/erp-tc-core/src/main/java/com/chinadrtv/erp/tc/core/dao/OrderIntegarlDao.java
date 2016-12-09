/*
 * @(#)OrderdetDao.java 1.0 2013-1-28下午2:46:21
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.tc.core.dao;

import java.sql.SQLException;
import java.util.Map;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.agent.Order;

/**
 * 新增积分
 * @author taoyawei
 * @version 1.0
 * @since 2013-1-28 下午2:46:21 
 * 
 */
@Deprecated
public interface OrderIntegarlDao extends GenericDao<Order, String>{

    String getIntegarlproc(Map<String, Object> mapval)throws SQLException;
}
