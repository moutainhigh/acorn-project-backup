package com.chinadrtv.erp.tc.core.service;

import java.sql.SQLException;
import java.util.List;

import com.chinadrtv.erp.core.service.GenericService;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.model.agent.OrderDetail;

public interface OrderDelDetailService extends GenericService<OrderDetail, String> {

	OrderDetail queryOrderdetbydetid(String orderdetid);

	Order queryOrderhistbyorid(String orderid);
	
	@Deprecated
	void insertTcHistory(String orderid) throws SQLException;

	int delOrderDetail(OrderDetail orderdet) throws SQLException;

	List<OrderDetail> queryOrderdetbyorid(String orderid) throws SQLException;

	void totalOrderHistprice(List<OrderDetail> orderdetList, String orderid) throws Exception;// 重算订单总金额-实付金额

	String useconpointProc(String sa, String orderid) throws SQLException;// 重算积分

	String conticketProc(String sa, String orderid) throws SQLException;// 重算返券
}