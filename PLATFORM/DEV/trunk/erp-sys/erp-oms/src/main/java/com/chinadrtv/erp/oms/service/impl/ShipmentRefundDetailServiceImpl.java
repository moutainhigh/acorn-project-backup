/*
 * @(#)ShipmentRefundDetailServiceImpl.java 1.0 2013-4-1下午1:49:06
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.oms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.exception.BizException;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.model.trade.ShipmentRefund;
import com.chinadrtv.erp.model.trade.ShipmentRefundDetail;
import com.chinadrtv.erp.oms.dao.ShipmentRefundDao;
import com.chinadrtv.erp.oms.dao.ShipmentRefundDetailDao;
import com.chinadrtv.erp.oms.service.ShipmentRefundDetailService;
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
 * @since 2013-4-1 下午1:49:06 
 * 
 */
@Service
public class ShipmentRefundDetailServiceImpl extends GenericServiceImpl<ShipmentRefundDetail, Long> implements
		ShipmentRefundDetailService {
	@Autowired
	private ShipmentRefundDao shipmentRefundDao;
	@Autowired
	private ShipmentRefundDetailDao shipmentRefundDetailDao;
 
	@Override
	protected GenericDao<ShipmentRefundDetail, Long> getGenericDao() {
		return shipmentRefundDetailDao;
	}

	/* 
	 * 批量保存
	* <p>Title: saveBatch</p>
	* <p>Description: </p>
	* @param dataList
	* @see com.chinadrtv.erp.oms.service.ShipmentRefundDetailService#saveBatch(java.util.List)
	*/ 
	public void saveBatch(List<ShipmentRefundDetail> dataList, String rejectCode, ShipmentRefund sr) {
		if(sr.getStatus() == 3){
			throw new BizException("已退包入库，不能修改");
		}
		AgentUser agentUser = SecurityHelper.getLoginUser();
		sr.setAgentUser(agentUser.getUserId());
		sr.setRejectCode(rejectCode);
		sr.setStatus(2L);
		
		shipmentRefundDao.saveOrUpdate(sr);
		for(ShipmentRefundDetail srd : dataList){
			srd.setShipmentRefund(sr);
			srd.setShipmentId(sr.getShipmentId());
			shipmentRefundDetailDao.saveOrUpdate(srd);
		}
	}

	/* 
	 * 删除退包
	* <p>Title: deleteRefund</p>
	* <p>Description: </p>
	* @param sr
	* @see com.chinadrtv.erp.oms.service.ShipmentRefundDetailService#deleteRefund(com.chinadrtv.erp.model.trade.ShipmentRefund)
	*/ 
	public void deleteRefund(ShipmentRefund sr) {
		sr.setStatus(1L);
		sr.setRejectCode("");
		shipmentRefundDao.saveOrUpdate(sr);
		List<ShipmentRefundDetail> srdList = shipmentRefundDetailDao.getDetailList(sr);
		for(ShipmentRefundDetail srd : srdList){
			srd.setWarehouseQty(0L);
			shipmentRefundDetailDao.saveOrUpdate(srd);
		}
	}

}
