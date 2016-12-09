/*
 * @(#)OrderUrgentApplicationServiceImpl.java 1.0 2013-7-10下午4:22:38
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.sales.core.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.model.cntrpbank.OrderUrgentApplication;
import com.chinadrtv.erp.sales.core.dao.OrderUrgentApplicationDao;
import com.chinadrtv.erp.sales.core.service.OrderDeliveryService;
import com.chinadrtv.erp.sales.core.service.OrderUrgentApplicationService;
import com.chinadrtv.erp.tc.core.dao.OrderhistDao;
import com.chinadrtv.erp.uc.dto.UrgentOrderDto;
import com.chinadrtv.erp.user.aop.RowAuth;
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
 * @since 2013-7-10 下午4:22:38 
 * 
 */
@Service
public class OrderUrgentApplicationServiceImpl extends GenericServiceImpl<OrderUrgentApplication, Long> implements
		OrderUrgentApplicationService {

	@Autowired
	private OrderDeliveryService orderDeliveryService;
	@Autowired
	private OrderhistDao orderhistDao;
	@Autowired
	private OrderUrgentApplicationDao orderUrgentApplicationDao;
	
	@Override
	protected GenericDao<OrderUrgentApplication, Long> getGenericDao() {
		return orderUrgentApplicationDao;
	}

	/** 
	 * <p>Title: queryPageList</p>
	 * <p>Description: </p>
	 * @param dto
	 * @param dataModel
	 * @return
	 * @see com.chinadrtv.erp.uc.service.OrderUrgentApplicationService#queryPageList(com.chinadrtv.erp.uc.dto.UrgentOrderDto, com.chinadrtv.erp.constant.DataGridModel)
	 */ 
	@Override
	@RowAuth
	public Map<String, Object> queryPageList(UrgentOrderDto dto, DataGridModel dataModel) {
		Integer total = orderUrgentApplicationDao.queryPageCount(dto, dataModel);
		List<Map<String, Object>> pageList = orderUrgentApplicationDao.queryPageList(dto, dataModel);
		List<UrgentOrderDto> dtoList = new ArrayList<UrgentOrderDto>();
		for(Map<String, Object> rowMap : pageList){
			UrgentOrderDto urgentOrderDto = orderUrgentApplicationDao.dtoAdaptor(rowMap);
			boolean couldReApply = this.couldReApply(urgentOrderDto.getOrderid(), urgentOrderDto.getAppdate());
			urgentOrderDto.setCouldReApply(couldReApply);
			dtoList.add(urgentOrderDto);
		}
		
		Map<String, Object> pageMap = new HashMap<String, Object>();
		pageMap.put("total", total);
		pageMap.put("rows", dtoList);
		return pageMap;
	}

	/** 
	 * <p>Title: apply</p>
	 * <p>Description: </p>
	 * @param model
	 * @throws Exception
	 * @see com.chinadrtv.erp.uc.service.OrderUrgentApplicationService#apply(com.chinadrtv.erp.model.cntrpbank.OrderUrgentApplication)
	 */ 
	@Override
	public void apply(OrderUrgentApplication model) throws Exception {
		AgentUser user = SecurityHelper.getLoginUser();
		model.setAppdate(new Date());
		model.setApppsn(user.getUserId());
		model.setStatus(1);
		
		orderUrgentApplicationDao.save(model);
	}
	
	/**
	 * <p>判断订单是否可以催送货</p>
	 * @param orderId
	 * @return Boolean
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Boolean couldReApply(String orderId, Date appDate) {
		/**
		 * 逻辑详见SQL
		 * SELECT a.orderid
			FROM iagent.orderhist a
			WHERE a.orderid = ''
			      AND a.rfoutdat IS NULL
			UNION ALL
			SELECT a.orderid
			FROM iagent.orderhist a
			WHERE a.orderid = ''
			      AND a.status IN ('5', '6')
			UNION ALL
			SELECT a.orderid
			FROM iagent.orderhist a
			LEFT JOIN iagent.address_ext b
			ON a.addressid = b.addressid
			LEFT JOIN iagent.delivery_company_area c
			ON b.provinceid = c.provinceid
			   AND b.cityid = c.cityid
			   AND b.countyid = c.countyid
			   AND b.areaid = c.areaid
			WHERE a.orderid = ''
			      AND a.rfoutdat IS NOT NULL
			      AND trunc(SYSDATE) <= trunc(a.rfoutdat) + nvl(c.senddt,0)
			union all
			SELECT orderid
			FROM acoapp_cntrpbank.orderurgentapplication a
			WHERE a.orderid = ''
			      AND a.appdate >= TRUNC(SYSDATE);

		 */
		Order order = orderhistDao.getOrderHistByOrderid(orderId);
		
		String status = order.getStatus();
		if(null!=status){
			if("5".equals(status)){
				return false;
			}else if("6".equals(status)){
				return false;
			}
		}
		Date rfoutdt = order.getRfoutdat();
		if(null==rfoutdt){
			return false;
		}else{
			//获取配送时效
			Map<String, Object> rsMap = new HashMap<String, Object>();
			try {
				rsMap = orderDeliveryService.matchDelivery(orderId);
			} catch (Exception e) {
				e.printStackTrace();
			}
			String deliveryDaysStr = null==rsMap.get("deliveryDays") ? "0" : rsMap.get("deliveryDays").toString();
			Integer deliverDays = Integer.parseInt(deliveryDaysStr);
			Calendar sendCalendar = Calendar.getInstance();
			sendCalendar.setTime(rfoutdt);
			sendCalendar.add(Calendar.DATE, deliverDays);
			
			Calendar todayCalendar = Calendar.getInstance();
			
			Calendar truncToday = DateUtils.truncate(todayCalendar, Calendar.DATE);
			Calendar truncSend =  DateUtils.truncate(sendCalendar, Calendar.DATE);
			int days = truncToday.compareTo(truncSend);
			if(days<=0){
				return false;
			}
		}
		
		if(null!=appDate){
			Date truncAppDate = DateUtils.truncate(appDate, Calendar.DATE);
			Date truncTodayDate = DateUtils.truncate(new Date(), Calendar.DATE);
			int diff = truncAppDate.compareTo(truncTodayDate);
			if(diff>=0){
				return false;
			}
		}
		
		return true;
	}

	/** 
	 * <p>Title: couldReApply</p>
	 * <p>Description: </p>
	 * @param orderId
	 * @return Boolean
	 * @see com.chinadrtv.erp.sales.core.service.OrderUrgentApplicationService#couldReApply(java.lang.String)
	 */ 
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Boolean couldReApply(String orderId) {
		OrderUrgentApplication oua = orderUrgentApplicationDao.queryLatest(orderId);
		return this.couldReApply(orderId, null==oua ? null : oua.getAppdate());
	}

	/** 
	 * <p>Title: queryLatest</p>
	 * <p>Description: </p>
	 * @param orderId
	 * @return OrderUrgentApplication
	 * @see com.chinadrtv.erp.sales.core.service.OrderUrgentApplicationService#queryLatest(java.lang.String)
	 */ 
	@Override
	public OrderUrgentApplication queryLatest(String orderId) {
		return orderUrgentApplicationDao.queryLatest(orderId);
	}

	/** 
	 * <p>Title: queryFinishedUrgentOrder</p>
	 * <p>Description: 按坐席和时间查询已完成的催送货订单</p>
	 * @param agentId
	 * @param beginDate
	 * @param endDate
	 * @return Model list
	 * @throws ServiceException 
	 * @see com.chinadrtv.erp.sales.core.service.OrderUrgentApplicationService#queryFinishedUrgentOrder(java.lang.String, java.util.Date, java.util.Date)
	 */ 
	@Override
	public List<OrderUrgentApplication> queryFinishedUrgentOrder(String agentId, Date beginDate, Date endDate)
			throws Exception {
		if(null==beginDate || null==endDate){
			endDate = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH, -3);
			beginDate = calendar.getTime();
		}else{
			if(endDate.compareTo(beginDate)<0){
				throw new ServiceException("Invalid_Parameter", "开始日期不能大于结束日期");
			}
		}
		return orderUrgentApplicationDao.queryFinishedUrgentOrder(agentId, beginDate, endDate);
	}

}
