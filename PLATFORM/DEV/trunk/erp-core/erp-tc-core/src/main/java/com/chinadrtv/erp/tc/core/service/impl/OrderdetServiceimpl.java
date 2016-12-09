/*
 * @(#)OrderdetServiceimpl.java 1.0 2013-1-28下午3:06:36
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.tc.core.service.impl;

import java.util.List;

import com.chinadrtv.erp.tc.core.dto.OrderTopItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.model.agent.OrderDetail;
import com.chinadrtv.erp.tc.core.dao.OrderdetDao;
import com.chinadrtv.erp.tc.core.service.OrderdetService;
import com.chinadrtv.erp.tc.core.utils.OrderdetUtil;

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
 * @since 2013-1-28 下午3:06:36 
 * 
 */
@Service
public class OrderdetServiceimpl extends GenericServiceImpl<OrderDetail, java.lang.Long> implements OrderdetService {

    public OrderdetServiceimpl()
    {
         synchronized (OrderdetServiceimpl.class)
         {
             orderdetUtil.init();
         }
    }

    private OrderdetUtil orderdetUtil=new OrderdetUtil();

	@Autowired
	private OrderdetDao orderdetDao;

	/* (非 Javadoc)
	* <p>Title: getGenericDao</p>
	* <p>Description: </p>
	* @return
	* @see com.chinadrtv.erp.core.service.impl.GenericServiceImpl#getGenericDao()
	*/ 
	@Override
	protected GenericDao<OrderDetail, Long> getGenericDao() {
		return orderdetDao;
	}

	/* (非 Javadoc)
	* <p>Title: getOrderDetList</p>
	* <p>Description: </p>
	* @param orderhist
	* @return
	* @see com.chinadrtv.erp.tc.service.OrderdetService#getOrderDetList(com.chinadrtv.erp.model.agent.Orderhist)
	*/
	public List<OrderDetail> getOrderDetList(Order orderhist) {
		return orderdetDao.getOrderDetList(orderhist);
	}

    /**
     * 单笔订单明细的更新
     * @param orderdet
     */
    public void updateOrderdet(OrderDetail orderdet)  throws Exception
    {
        //目前只更新需要更新的字段
        //orderdetUtil.CopyNotNullValue();
        orderdetDao.saveOrUpdate(orderdet);
    }
    /**
     * 获取最近指定时间的销量(昨日/一周)
     * @param prodid
     * @param ncfreeName
     * @return
     */
    @Override
    public Double getOrderSoldQty(String prodid, String ncfreeName, Integer days){
       return orderdetDao.getOrderSoldQty(prodid, ncfreeName, days);
    }

    @Override
    public List<OrderTopItem> getOrderTopItems(Integer days) {
        return orderdetDao.getOrderTopItems(days);
    }

    @Override
    public List<OrderTopItem> getCacheOrderTopItems(Integer days) {
        return orderdetDao.getCacheOrderTopItems(days);
    }

    @Override
    public void updateCacheOrderTopItems(Integer days){
        orderdetDao.updateCacheOrderTopItems(days);
    }
}
