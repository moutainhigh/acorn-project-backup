/*
 * @(#)OrderdetDao.java 1.0 2013-1-28下午2:46:21
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.tc.core.dao;

import java.util.Date;
import java.util.List;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.model.agent.OrderDetail;
import com.chinadrtv.erp.tc.core.dto.OrderTopItem;
import com.google.code.ssm.api.ParameterValueKeyProvider;

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
 * @since 2013-1-28 下午2:46:21 
 * 
 */
public interface OrderdetDao extends GenericDao<OrderDetail, Long>{

	/**
	 * 获取订单详细
	* @Description: 获取订单详细List
	* @param orderhist
	* @return
	* @return List<com.chinadrtv.erp.model.agent.Orderdet>
	 */
	List<OrderDetail> getOrderDetList(Order orderhist);

    /**
     * 根据订单明细Id获取订单明细信息
     * @param orderdetId
     * @throws Exception
     */
	OrderDetail getOrderdetFromOrderdetId(String orderdetId) throws Exception;

    String getOrderdetId();

	/**
	 * 批量删除订单详情
	* @Description: 批量删除订单详情
	* @param orderdetList
	* @return void
	* @throws Exception
	*/
	void deleteList(List<OrderDetail> orderdetList) throws Exception;
    
    /**
     * 得到组合商品信息
     * @param prodid
     * @return
     */
    public List<OrderDetail> searchOrderByProdid(String prodid);

    /**
     * 获取最近指定时间的销量(昨日/一周)
     * @param prodid
     * @param ncfreeName
     * @return
     */
    Double getOrderSoldQty(String prodid, String ncfreeName, Integer days);

    /**
     * 获取销售最好的前20个商品
     * @param days
     * @return
     */
    List<OrderTopItem> getOrderTopItems(Integer days);
    /**
     * 获取销售最好的前20个商品(缓存)
     * @param days
     * @return
     */
    List<OrderTopItem> getCacheOrderTopItems(Integer days);

    /**
     * 更新缓存数据
     * @param days
     */
    void updateCacheOrderTopItems(Integer days);
}
