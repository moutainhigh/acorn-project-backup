/*
 * @(#)OrderdetService.java 1.0 2013-1-28下午2:49:08
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.tc.core.service;

import java.util.List;

import com.chinadrtv.erp.core.service.GenericService;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.model.agent.OrderDetail;
import com.chinadrtv.erp.tc.core.dto.OrderTopItem;

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
 * @since 2013-1-28 下午2:49:08 
 * 
 */
public interface OrderdetService extends GenericService<OrderDetail, java.lang.Long> {

	/**
	 * 获取订单详细
	* @Description: 根据订单头获取订单详细列表
	* @param orderhist
	* @return
	* @return List<Orderdet>
	* @throws
	*/ 
	List<OrderDetail> getOrderDetList(Order orderhist);


    void updateOrderdet(OrderDetail orderdet) throws Exception;


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
