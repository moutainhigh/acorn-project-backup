/*
 * @(#)OrderhistServiceTest.java 1.0 2013-2-27下午1:44:54
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.tc.test;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.chinadrtv.erp.model.trade.ShipmentHeader;
import com.chinadrtv.erp.tc.core.constant.model.OrderhistAssignInfo;
import com.chinadrtv.erp.tc.core.service.OrderhistCompanyAssignService;
import com.chinadrtv.erp.tc.service.InventoryAgentService;
import org.junit.Assert;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinadrtv.erp.model.agent.OrderDetail;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.tc.core.dao.AddressExtDao;
import com.chinadrtv.erp.tc.core.dao.OrderhistDao;
import com.chinadrtv.erp.tc.service.OrderhistService;
import com.chinadrtv.erp.tc.service.ShipmentHeaderService;

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
 * @since 2013-2-27 下午1:44:54 
 * 
 */
public class OrderhistServiceTest extends TCTest {
	
	@Autowired
	private OrderhistDao orderhistDao;
	@Autowired
	private OrderhistService orderhistService;
	@Autowired
	private AddressExtDao addressExtDao;
	@Autowired
	private ShipmentHeaderService shipmentHeaderService;
    @Autowired
    private InventoryAgentService inventoryAgentService;
    @Autowired
    private OrderhistCompanyAssignService orderhistCompanyAssignService;
	
	/**
	 * 逻辑删除订单
	* @Description: 
	* @throws Exception
	* @return void
	* @throws
	 */
	//@Test
	public void testDeleteOrderLogic() throws Exception{
		Order orderhist = new Order();
		orderhist.setOrderid("936259297");
		orderhist.setCallbackid("3412");
		orderhist.setMdusr("A001");
		orderhistService.deleteOrderLogic(orderhist);
	}
	
	/**
	 * 物理删除订单
	* @Description: 
	* @throws Exception
	* @return void
	* @throws
	 */
	//@Test
	public void testDeleteOrderPhysical() throws Exception{
		Order orderhist = new Order();
		orderhist.setOrderid("101010");
		orderhistService.deleteOrderPhysical(orderhist);
	}
	
	/**
	 * 更新订单索权号
	* @Description: 
	* @throws Exception
	* @return void
	* @throws
	 */
	//@Test
	public void testUpdateOrderRightNum() throws Exception{
		Order orderhist = new Order();
		orderhist.setOrderid("101010");
		orderhist.setCardrightnum("654321");
		orderhist.setMdusr("1123");
		orderhistService.updateOrderRightNum(orderhist);
	}
	
	/**
	 * 更新订单子订单号 
	* @Description: 
	* @throws Exception
	* @return void
	* @throws
	 */
	//@Test
	public void testUpdateOrderChild() throws Exception{
		Order orderhist = new Order();
		orderhist.setOrderid("101010");
		orderhist.setChildid("997700252");
		orderhist.setMdusr("AACCD");
		orderhistService.updateOrderChild(orderhist);
	}
	
	/**
	 * 取消分拣状态
	* @Description: 
	* @throws Exception
	* @return void
	* @throws
	 */
	//@Test
	public void testCancelSortStatus() throws Exception{
		/*Order orderhist = new Order();
		orderhist.setOrderid("101010");
		orderhist.setMdusr("USR001");
		orderhistService.cancelSortStatus(orderhist);*/
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderid", "920706272");
		params.put("companyid", "42");
		params.put("mdusr", "U003");
		orderhistService.cancelSortStatus(params);
	}
	
	/**
	 * 修改订单订购类型
	* @Description: 
	* @throws Exception
	* @return void
	* @throws
	 */
	//@Test
	public void testUpdateOrderType() throws Exception{
		Order orderhist = new Order();
		orderhist.setOrderid("101010");
		orderhist.setMailtype("9");
		orderhist.setMdusr("USR110");
		orderhistService.updateOrderType(orderhist);
	}
	
	/**
	 * SR3.5_1.13 更新订购方式和送货公司
	* @Description: 
	* @throws Exception
	* @return void
	* @throws
	 */
	//@Test
	public void testUpdateOrderTypeAndDelivery() throws Exception{
		Order orderhist = new Order();
		orderhist.setOrderid("101010");
		orderhist.setMdusr("Use001");
		orderhist.setCompanyid("9899");
		orderhist.setSpellid(2233);
		orderhist.setProvinceid("5544");
		orderhist.setCityid("1233");
		orderhistService.updateOrderTypeAndDelivery(orderhist);
	}
	
	/**
	 * 修改订单的收货人/付款人
	* @Description: 
	* @throws Exception
	* @return void
	* @throws
	 */
	//@Test
	/*public void testUpdateOrderOwner() throws Exception{
		Order orderhist = new Order();
		orderhist.setPaycontactid("PAY002");
		orderhist.setGetcontactid("GET002");
		orderhist.setMdusr("US001");
		orderhistService.updateOrderOwner(orderhist);
	}*/
	
	/**
	 * 修改订单的收货人
	* @Description: 
	* @throws Exception
	* @return void
	* @throws
	 */
	//@Test
	public void testUpdateOrderConsignee() throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderid", "101010");
		params.put("getcontactid", "GET003");
		params.put("addressid", "28094162");
		params.put("mdusr", "USR119");
		orderhistService.updateOrderConsignee(params);
	}
	
	/**
	 * 修改订单的付款人
	* @Description: 
	* @throws Exception
	* @return void
	* @throws
	 */
	//@Test
	public void testUpdateOrderPayer() throws Exception{
		Order orderhist = new Order();
		orderhist.setOrderid("101010");
		orderhist.setPaycontactid("PAY002");
		orderhist.setMdusr("US002");
		
		orderhistService.updateOrderPayer(orderhist);
	}
	
	/**
	 * 订单回访取消订单
	* @Description: 
	* @throws Exception
	* @return void
	* @throws
	 */
	//@Test
	public void testCallbackCancelOrder() throws Exception{
		Order orderhist = new Order();
		orderhist.setOrderid("101010");
		orderhist.setMdusr("USR009");
		orderhistService.callbackCancelOrder(orderhist);
	}
	
	/**
	 * 订单回访保存时更新订单信息
	* @Description: 
	* @throws Exception
	* @return void
	* @throws
	 */
	//@Test
	public void testCallbackUpdateOrder() throws Exception{
		Order orderhist = new Order();
		orderhist.setOrderid("101010");
		orderhist.setRemark("mark");
		orderhist.setErrcode("ERR001");
		orderhist.setMdusr("USR008");
		orderhist.setAdusr("USR002");
		orderhist.setAddt(new Date());
		orderhistService.callbackUpdateOrder(orderhist);
		
	}
	
	/**
	 * SR3.5_1.5更新订单明细 
	* @Description: 
	* @throws Exception
	* @return void
	* @throws
	 */
	//@Test
	public void testUpdateOrderDetail() throws Exception{
		OrderDetail orderdet = new OrderDetail();
		orderdet.setOrderid("101010");
		orderdet.setProvinceid("0048");
		orderdet.setState("OK");
		orderdet.setCity("0012");
		orderdet.setMdusr("USR007");
		orderhistService.updateOrderDetail(orderdet);
	}
	
	/**
	 * 分配库存
	* @Description: 
	* @throws Exception
	* @return void
	* @throws
	 */
	//@Test
	public void testAssignInventory() throws Exception{
		//非套装
		//String orderId = "997700222";
		
		//套装
		String orderId = "14204596";
		
		String mdusr = "USR001";
		Order orderhist = orderhistService.getOrderHistByOrderid(orderId);
		Set<OrderDetail> orderdetSet = orderhist.getOrderdets();
		Order newOrderhist = new Order();
		
		BeanUtils.copyProperties(orderhist, newOrderhist, Order.class);
		
		newOrderhist.setId(null);
		
		Set<OrderDetail> newOrderdetSet = new HashSet<OrderDetail>();
		for(Iterator<OrderDetail> iter = orderdetSet.iterator(); iter.hasNext();){
			OrderDetail oldOd = iter.next();
			OrderDetail newOd = new OrderDetail();
			BeanUtils.copyProperties(oldOd, newOd);
			newOd.setId(null);
			newOd.setOrderhist(newOrderhist);
			newOrderdetSet.add(newOd);
		}
		
		newOrderhist.setOrderdets(newOrderdetSet);
		Order savedOrderhist = orderhistDao.save(newOrderhist);

		//shipmentHeaderService.assignInventory(savedOrderhist, mdusr);
        OrderhistAssignInfo ohai = orderhistCompanyAssignService.queryDefaultAssignInfo(orderhist);
        inventoryAgentService.reserveInventory(savedOrderhist,null,ohai.getWarehouseId().toString(),mdusr);
		System.out.println(savedOrderhist.getOrderid());
		Assert.assertNotNull(savedOrderhist);
	}
	
	/**
	 * 取消分配库存
	* @Description: 
	* @throws Exception
	* @return void
	* @throws
	 */
	//@Test
	public void testUnassignInventory() throws Exception{
		//非套装
		//String orderId = "997700222";
		
		//套装
		String orderId = "14204596";
		String mdusr = "USR001";
		Order orderhist = orderhistDao.getOrderHistByOrderid(orderId);
		orderhistService.deleteOrderLogic(orderhist);
        ShipmentHeader shipmentHeader=shipmentHeaderService.getShipmentHeaderFromOrderId(orderhist.getOrderid());
        if(shipmentHeader!=null)
            inventoryAgentService.unreserveInventory(orderhist,shipmentHeader, mdusr);
	}
}
