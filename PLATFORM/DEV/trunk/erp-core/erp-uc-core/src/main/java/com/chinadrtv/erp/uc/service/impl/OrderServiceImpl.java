/*
 * @(#)OrderServiceImpl.java 1.0 2013-5-7下午1:22:53
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.uc.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tools.ant.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.model.Address;
import com.chinadrtv.erp.model.agent.Grp;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.model.trade.ShipmentHeader;
import com.chinadrtv.erp.uc.dao.AddressDao;
import com.chinadrtv.erp.uc.dao.OrderDao;
import com.chinadrtv.erp.uc.dao.ShipmentHeaderDao;
import com.chinadrtv.erp.uc.dto.OrderDetailDto;
import com.chinadrtv.erp.uc.dto.OrderDto;
import com.chinadrtv.erp.uc.service.OrderService;
import com.chinadrtv.erp.uc.service.OrderSkuSplitService;
import com.chinadrtv.erp.user.dao.GrpDao;
import com.chinadrtv.erp.util.PojoUtils;

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
 * @since 2013-5-7 下午1:22:53 
 * 
 */
@Service
public class OrderServiceImpl extends GenericServiceImpl<Order, Long> implements OrderService {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(OrderServiceImpl.class);
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private AddressDao addressDao;
	@Autowired
	private OrderSkuSplitService orderSkuSplitService;
	@Autowired
	private ShipmentHeaderDao shipmentHeaderDao;
	@Autowired
	private GrpDao grpDao;
	
	@Override
	protected GenericDao<Order, Long> getGenericDao() {
		return orderDao;
	}

	/** 
	 * <p>Title: API-UC-12.查询客户主地址关联订单</p>
	 * <p>Description: </p>
	 * @param contactId
	 * @return boolean
	 * @see com.chinadrtv.erp.uc.service.OrderService#queryOrderByAddressId(java.lang.String)
	 */ 
	public boolean haveExWarehouseOrder(String contactId) {
		List<Order> orderList = orderDao.querySpecialOrder(contactId);
		if(null==orderList || orderList.size()==0){
			return false;
		}
		return true;
	}
	

	/** 
	 * <p>Title: haveExWarehouseOrderByAddress</p>
	 * <p>Description: </p>
	 * @param addressId
	 * @return
	 * @see com.chinadrtv.erp.uc.service.OrderService#haveExWarehouseOrderByAddress(java.lang.String)
	 */ 
	@Override
	public boolean haveExWarehouseOrderByAddress(String addressId) {
		List<Order> orderList = orderDao.haveExWarehouseOrderByAddress(addressId);
		if(null==orderList || orderList.size()==0){
			return false;
		}
		return true;
	}

	/** 
	 * <p>Title: querySpecialOrder</p>
	 * <p>Description: </p>
	 * @param contactId
	 * @return
	 * @see com.chinadrtv.erp.uc.service.OrderService#querySpecialOrder(java.lang.String)
	 */ 
	public List<Order> queryExWarehouseOrder(String contactId) {
		//查询客户主地址
		Address address = addressDao.getAddressByContactId(contactId);
		return orderDao.querySpecialOrder(address.getAddressid());
	}

	/** 
	 * <p>Title: queryOrderListByContactId</p>
	 * <p>Description: </p>
	 * @param dataGridModel
	 * @param contactId
	 * @return Map<String, Object>
	 * @see com.chinadrtv.erp.uc.service.OrderService#queryOrderListByContactId(com.chinadrtv.erp.uc.common.DataGridModel, java.lang.String)
	 */ 
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map<String, Object> queryOrderListByContactId(DataGridModel dataGridModel, String contactId) {
		Map<String, Object> pageMap = new HashMap<String, Object>();
		
		if(null==contactId || "".equals(contactId)){
			pageMap.put("total", 0);
			pageMap.put("rows", new ArrayList());
		}
		
		int totalCount = orderDao.queryOrderCountByContactId(contactId);
		List<Order> orderList = orderDao.queryOrderListByContactId(dataGridModel, contactId);
		List<OrderDto> orderDtoList = PojoUtils.convertPojoList2DtoList(orderList, OrderDto.class);
		for(OrderDto dto : orderDtoList){
			ShipmentHeader sh = shipmentHeaderDao.queryShipmentHeaderByOrderId(dto.getOrderid());
			if(null!=sh){
				dto.setShipmentId(sh.getShipmentId());
				dto.setLogisticsStatusId(sh.getLogisticsStatusId());
			}
			if(null!=dto.getGrpid()){
				Grp grp = grpDao.get(dto.getGrpid());
				dto.setGrpName(grp.getGrpname());
			}
		}
		
		pageMap.put("total", totalCount);
		pageMap.put("rows", orderDtoList);
		
		return pageMap;
	}

	/** 
	 * <p>Title: 根据订单号查询订单</p>
	 * <p>Description: </p>
	 * @param orderId
	 * @return List<OrderDetailDto>
	 * @see com.chinadrtv.erp.uc.service.OrderService#queryOrderDetailByOrderId(java.lang.String)
	 */ 
	public List<OrderDetailDto> queryOrderDetail(String orderId) {
		Order order = orderDao.queryByOrderId(orderId);
		List<Map<String, Object>> rsList = null;
		try {
			rsList = orderSkuSplitService.orderSkuSplit(order);
		} catch (Exception e) {
            logger.error(e.getMessage(),e); //e.printStackTrace();
		}
		
		List<OrderDetailDto> odDtoList = new ArrayList<OrderDetailDto>();
		for(Map<String, Object> rsMap : rsList){
			OrderDetailDto odDto = new OrderDetailDto();
			odDto.setProdname(null==rsMap.get("pluname") ? "" : rsMap.get("pluname").toString());
			odDto.setProdscode(null==rsMap.get("plucode") ? "" : rsMap.get("plucode").toString());
			odDto.setTotalQty((Long)rsMap.get("qty"));
			odDto.setUnitPrice((Double) rsMap.get("unitprice"));
			odDto.setNcfreeName(null==rsMap.get("ncfreename") ? "" : rsMap.get("ncfreename").toString());
			//销售方式，是否为赠品
			odDto.setSaleType(null==rsMap.get("freeflag") ? "" : rsMap.get("freeflag").toString());
			odDtoList.add(odDto);
		}
		return odDtoList;
	}

	/** 
	 * <p>Title: queryOrderCreaterByContact</p>
	 * <p>Description: </p>
	 * @param contactId
	 * @return List<String>
	 * @see com.chinadrtv.erp.uc.service.OrderService#queryOrderCreaterByContact(java.lang.String)
	 */ 
	public List<String> queryOrderCreaterByContact(String contactId) {
		return orderDao.queryOrderCreaterByContact(contactId);
	}

	/** 
	 * <p>Title: isOrderCreatorByContact</p>
	 * <p>Description: </p>
	 * @param userId
	 * @param contactId
	 * @return Boolean
	 * @see com.chinadrtv.erp.uc.service.OrderService#isOrderCreatorByContact(java.lang.String)
	 */ 
	public Boolean isOrderCreator(String userId, String contactId) {
		List<String> creatorList = queryOrderCreaterByContact(contactId);
		if(creatorList.size()>0){
			if(creatorList.contains(userId)){
				return true;
			}else{
				return false;
			}
		}else{
			return null;
		}
	}

    @Override
    public BigDecimal queryTotalAmountByContactId(String customerId) {
        return orderDao.queryTotalAmountByContactId(customerId);
    }

//    @Override
//    public List<Order> findByCreateDateRange(Date minOrderCreateDate, Date maxOrderCreateDate) {
//        return orderDao.findByCreateDateRange(DateUtils.format(minOrderCreateDate, "yyyy-MM-dd hh:mm:ss"), DateUtils.format(maxOrderCreateDate, "yyyy-MM-dd hh:mm:ss"));
//    }

//    @Override
//    public BigDecimal calculateOrderTransferBlackList(String contactId) {
//        return orderDao.calculateOrderTransferBlackList(contactId);
//    }

//    @Override
//    public Integer queryOrderCountByContactId(String contactid) {
//        return orderDao.queryOrderCountByContactId(contactid);
//    }

}
