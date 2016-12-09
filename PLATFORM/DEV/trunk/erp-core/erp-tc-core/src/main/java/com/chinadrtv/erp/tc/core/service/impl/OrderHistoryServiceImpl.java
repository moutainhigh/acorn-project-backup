/*
 * @(#)OrderHistoryServiceImpl.java 1.0 2013-1-30上午10:52:52
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.tc.core.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.chinadrtv.erp.tc.core.service.OrderHistoryService;
import org.hibernate.SessionFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.model.AddressExt;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.model.agent.OrderDetail;
import com.chinadrtv.erp.model.agent.OrderHistory;
import com.chinadrtv.erp.model.agent.OrderdetHistory;
import com.chinadrtv.erp.tc.core.dao.OrderHistoryDao;
import com.chinadrtv.erp.tc.core.dao.ShipmentHeaderDao;

/**
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author andrew
 * @version 1.0
 * @since 2013-1-30 上午10:52:52 
 * 
 */
@Service("orderHistoryService")
public class OrderHistoryServiceImpl extends GenericServiceImpl<OrderHistory, Long> implements OrderHistoryService {

	@Autowired
	private OrderHistoryDao orderHistoryDao;
	@Autowired
	private ShipmentHeaderDao shipmentHeaderDao;
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	protected GenericDao<OrderHistory, Long> getGenericDao() {
		return orderHistoryDao;
	}
	

	/* 
	 * 插入快照表
	* <p>Title: 插入快照表</p>
	* <p>Description: </p>
	* @param oh
	* @see com.chinadrtv.erp.shipment.service.OrderHistoryService#insertTcHistory(com.chinadrtv.erp.model.agent.Order)
	*/ 
	public OrderHistory insertTcHistory(Order oh) {
		Set<OrderDetail> odSet = oh.getOrderdets();
		List<OrderDetail> odList = new ArrayList<OrderDetail>();
		for(OrderDetail od : odSet){
			odList.add(od);
		}
		
		return this.insertTcHistory(oh, odList);
	}

	/*
	 * 插入快照表
	* <p>Title: 插入快照表</p>
	* <p>Description: </p>
	* @param orderhist
	* @param orderdetList
	* @see com.chinadrtv.erp.shipment.service.OrderHistoryService#insertTcHistory(com.chinadrtv.erp.model.agent.Order, java.util.List)
	 */
    public OrderHistory insertTcHistory(Order orderhist, List<OrderDetail> orderdetList) {
    	//TODO 有待后期观察
    	//先 flush 缓存中的 Order、OrderDetail 
    	//sessionFactory.getCurrentSession().flush();
        
    	OrderHistory tcOrderHistory = new OrderHistory();
        AddressExt address = orderhist.getAddress();
        BeanUtils.copyProperties(orderhist, tcOrderHistory, OrderHistory.class);
        tcOrderHistory.setId(null);
        tcOrderHistory.setOrderhist(orderhist);
        if(null!=address){
        	tcOrderHistory.setAddressid(address.getAddressId());
        }
        
        Set<OrderdetHistory> odHisSet = new HashSet<OrderdetHistory>();
        for(OrderDetail od: orderdetList){
            OrderdetHistory tcOrderdetHistory = new OrderdetHistory();
            BeanUtils.copyProperties(od, tcOrderdetHistory, OrderdetHistory.class);
            tcOrderdetHistory.setOrderHistory(tcOrderHistory);
            tcOrderdetHistory.setId(null);
            if(null==tcOrderdetHistory.getRevision()){
            	tcOrderdetHistory.setRevision(1);
            }
            tcOrderdetHistory.setOrderdet(od);
            odHisSet.add(tcOrderdetHistory);
        }

        tcOrderHistory.setOrderdets(odHisSet);
        if(null==tcOrderHistory.getRevision()){
        	tcOrderHistory.setRevision(1);
        }
        tcOrderHistory = orderHistoryDao.save(tcOrderHistory);
        return tcOrderHistory;
    }

	/* 
	 * 根据订单业务号和业务版本号获取订单历史
	* <p>Title: getByOrderIdAndVersion</p>
	* <p>Description: </p>
	* @param orderid
	* @param revision
	* @return
	* @see com.chinadrtv.erp.shipment.service.OrderHistoryService#getByOrderIdAndVersion(java.lang.String, java.lang.Integer)
	*/ 
	public OrderHistory getByOrderIdAndVersion(String orderid, Integer revision) {
		return orderHistoryDao.getByOrderIdAndVersion(orderid, revision);
	}

}
