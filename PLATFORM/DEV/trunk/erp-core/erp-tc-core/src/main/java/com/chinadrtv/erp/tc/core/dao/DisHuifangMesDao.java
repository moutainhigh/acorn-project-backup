package com.chinadrtv.erp.tc.core.dao;

import java.util.Map;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.agent.Order;

/**
 * User: taoyawei
 * Date: 13-2-19
 * Time: 下午3:28
 * To change this template use File | Settings | File Templates.
 */
@Deprecated
public interface DisHuifangMesDao extends GenericDao<Order, String>  {
	boolean upOrderHistByfive(Map<String, Object> mapval) throws Exception;
}

