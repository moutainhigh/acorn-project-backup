/*
 * @(#)OrderDeliveryServiceImpl.java 1.0 2013-6-13下午2:23:23
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.sales.core.service.impl;

import java.util.*;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.exception.ErpException;
import com.chinadrtv.erp.ic.model.CompanyDeliverySpan;
import com.chinadrtv.erp.ic.service.CompanyDeliverySpanService;
import com.chinadrtv.erp.marketing.core.service.UserBpmTaskService;
import com.chinadrtv.erp.model.AddressExt;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.model.agent.OrderDetail;
import com.chinadrtv.erp.model.marketing.UserBpmTask;
import com.chinadrtv.erp.sales.core.service.OrderDeliveryService;
import com.chinadrtv.erp.sales.core.service.OrderhistService;
import com.chinadrtv.erp.tc.core.constant.model.OrderhistAssignInfo;
import com.chinadrtv.erp.tc.core.service.OrderhistCompanyAssignService;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.util.SecurityHelper;

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
 * @since 2013-6-13 下午2:23:23 
 * 
 */
@Service
public class OrderDeliveryServiceImpl implements OrderDeliveryService {
	
	@Autowired
	private OrderhistCompanyAssignService orderhistCompanyAssignService;
	@Autowired
	private CompanyDeliverySpanService companyDeliverySpanService;
	@Autowired
	private OrderhistService orderhistService;
	@Autowired
	private UserBpmTaskService userBpmTaskService;
	@Autowired
	private SessionFactory sessionFactory;
	
	/** 
	 * <p>Title: matchDelivery</p>
	 * <p>Description: 匹配承运商、仓库、配送时效</p>
	 * @param orderId
	 * @return Map<String, Object>
	 * @see com.chinadrtv.erp.uc.service.ShoppingCartService#matchDelivery(java.lang.String)
	 */ 
	@Override
	public Map<String, Object> matchDelivery(String orderId) {
		Order order = orderhistService.getOrderHistByOrderid(orderId);
		AddressExt address = order.getAddress();
		Long orderType = Long.parseLong(order.getOrdertype());
		Long provinceId = Long.parseLong(address.getProvince().getProvinceid());
		Long payType = Long.parseLong(order.getPaytype());
		Long cityId = null==address.getCity() ? null : new Long(address.getCity().getCityid());
		Long countyId = null==address.getCounty() ? null : new Long(address.getCounty().getCountyid());
		Long areaId = null==address.getArea() ? null : new Long(address.getArea().getAreaid());
		
		OrderhistAssignInfo oai = orderhistCompanyAssignService.queryDefaultAssignInfo(order);
		
		CompanyDeliverySpan cds = companyDeliverySpanService
				.getDeliverySpan(oai.getEntityId(), orderType, payType, provinceId, cityId, countyId, areaId);
		
		Map<String, Object> rsMap = new HashMap<String, Object>();
		
		rsMap.put("entityId", oai.getEntityId());
		rsMap.put("entityName", cds.getCompanyName());
		rsMap.put("warehouseId", oai.getWarehouseId());
		rsMap.put("warehouseName", oai.getWarehouseName());
		rsMap.put("deliveryDays", cds.getUserDef1());
		
		return rsMap;
	}

	
	/** 
	 * <p>Title: assignEms</p>
	 * <p>Description: 订单指定EMS送货</p>
	 * @param orderId
	 * @param remark
	 * @throws ErpException 
	 * @see com.chinadrtv.erp.sales.core.service.OrderDeliveryService#assignEms(java.lang.String, java.lang.String)
	 */ 
	@SuppressWarnings("unused")
	@Override
	public Integer assignEms(String orderId, String remark) throws ErpException {
		
		List<UserBpmTask> tasks = userBpmTaskService.queryUnterminatedOrderChangeTask(orderId);
		if(null!=tasks && tasks.size()>0){
			return -1;
		}
		
		Order order = orderhistService.getOrderHistByOrderid(orderId);
		//请勿删除级联删除方法，用于迟延加载
		Set<OrderDetail> orderDets = order.getOrderdets();
		for(OrderDetail od : orderDets){
			Integer money = od.getBackmoney();
		}
		AddressExt addressExt = order.getAddress();
		
		sessionFactory.getCurrentSession().evict(order);
        order.setOrderdets(new HashSet<OrderDetail>());
		order.setReqEMS("Y");
		
		AgentUser user = SecurityHelper.getLoginUser();
		order.setMdusr(user.getUserId());
		
		orderhistService.saveOrderModifyRequest(order, null, null,null,null, null, null, remark);
		
		return 0;
	}
}
