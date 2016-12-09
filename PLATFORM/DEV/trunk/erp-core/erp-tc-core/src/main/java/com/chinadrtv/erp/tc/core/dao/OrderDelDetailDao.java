package com.chinadrtv.erp.tc.core.dao;

import java.sql.SQLException;
import java.util.List;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.model.agent.OrderDetail;

/**
 * Created with IntelliJ IDEA.
 * User: taoyawei
 * Date: 13-1-4
 * Time: 下午3:07
 * 订单删除明细
 */
public interface OrderDelDetailDao extends GenericDao<OrderDetail, String>  {
	 OrderDetail queryOrderdetbydetid(String orderdetid);
	 Order queryOrderhistbyorid(String orderid);
	 int delOrderDetail(OrderDetail orderdet) throws SQLException;
     List<OrderDetail> queryOrderdetbyorid(String orderid) throws SQLException;//订单编号查询明细
     void totalOrderHistprice(List<OrderDetail> orderdetList,String orderid) throws Exception;//重算订单总金额-实付金额
     String useconpointProc(String sa,String orderid)throws SQLException;//重算积分
     String conticketProc(String sa,String orderid)throws SQLException;//重算返券
}

