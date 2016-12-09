package com.chinadrtv.erp.tc.core.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.model.agent.OrderDetail;
import com.chinadrtv.erp.tc.core.dao.OrderDelDetailDao;
import com.chinadrtv.erp.tc.core.dao.OrderhistDao;
import com.chinadrtv.erp.tc.core.service.OrderDelDetailService;
import com.chinadrtv.erp.tc.core.service.OrderHistoryService;

/**
 * 删除订单明细
 * @author taoyawei
 *
 */
@Service("orderDelDetailService")
public class OrderDelDetailServiceImpl extends GenericServiceImpl<OrderDetail, String>
        implements OrderDelDetailService
{
	private static final Logger logger = LoggerFactory.getLogger(CouponReviseServiceImpl.class);
    @Autowired
    private OrderDelDetailDao orderDelDetailDao;
    @Autowired
    private OrderhistDao orderhistDao;
    
    
    //TC 快照Dao
    /*@Autowired
    private OrderHistoryDao orderHistoryDao;
    @Autowired
    private OrderdetHistoryDao orderdetHistoryDao;*/
    @Autowired
    private OrderHistoryService orderHistoryService;

    protected GenericDao<OrderDetail, String> getGenericDao()
    {
        return this.orderDelDetailDao;
    }
    /**
     * 获得单个明细信息
     */
    public OrderDetail queryOrderdetbydetid(String orderdetid){
    	logger.debug("begin queryOrderdetbydetid");
    	return this.orderDelDetailDao.queryOrderdetbydetid(orderdetid);
    }
    /**
     * 
     */
    public Order queryOrderhistbyorid(String orderid){
    	logger.debug("begin queryOrderhistbyorid");
    	return this.orderDelDetailDao.queryOrderhistbyorid(orderid);
    }
    
    
    /**
     * 删除订单明细信息
     */
    public int delOrderDetail(OrderDetail orderdet) throws SQLException
    {
    	logger.debug("begin delOrderDetail");
        return this.orderDelDetailDao.delOrderDetail(orderdet);
    }
    
    /**
     * 订单编号获得订单明细
     */
    public List<OrderDetail> queryOrderdetbyorid(String orderid) throws SQLException
    {
    	logger.debug("begin queryOrderdetbyorid");
    	return this.orderDelDetailDao.queryOrderdetbyorid(orderid);
    }

    /**
     * 重算金额
     * @param orderdetList
     * @param orderid
     * @throws Exception 
     */
	public void totalOrderHistprice(List<OrderDetail> orderdetList,String orderid) throws Exception {
		logger.debug("begin totalOrderHistprice");
		this.orderDelDetailDao.totalOrderHistprice(orderdetList,orderid);
		logger.debug("end totalOrderHistprice");
	}

	/**
	 * 重算积分
	 * @param sa
	 * @param orderid
	 * @return
	 */
	public String useconpointProc(String sa, String orderid)throws SQLException {
		logger.debug("begin useconpointProc");
		return this.orderDelDetailDao.useconpointProc(sa, orderid);
	}

	/**
	 * 重算赠券
	 * @param sa
	 * @param orderid
	 * @return
	 */
	public String conticketProc(String sa, String orderid)throws SQLException{
		logger.debug("begin conticketProc");
		return this.orderDelDetailDao.conticketProc(sa, orderid);
	}
	
	/**
	 * 向TC快照表插数据
	 * @throws SQLException 
	 */
	 @Deprecated
	 public void insertTcHistory(String orderid) throws SQLException{
		 Order orderhist=orderhistDao.getOrderHistByOrderid(orderid);
         //List<OrderDetail> orderdetList=this.queryOrderdetbyorid(orderid);
         orderHistoryService.insertTcHistory(orderhist);
	}

}