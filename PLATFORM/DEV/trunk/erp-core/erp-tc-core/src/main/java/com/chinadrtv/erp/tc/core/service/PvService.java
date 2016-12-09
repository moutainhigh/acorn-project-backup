package com.chinadrtv.erp.tc.core.service;

import java.sql.SQLException;
import java.util.Map;

import com.chinadrtv.erp.core.service.GenericService;
import com.chinadrtv.erp.model.agent.OrderDetail;

public interface PvService extends GenericService<OrderDetail, String> {
	
	public void getJifenproc(Map<String, Object> mapval) throws SQLException;

    public void getMemberType(Map<String, Object> mapval) throws SQLException;
	
	/**
	 * 新增订单时调用积分服务
	* @Description: 
	* @param params
	* @return void
	* @throws
	 */
	String addPvByOrder(Map<String, Object> params);
}