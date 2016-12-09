/*
 * @(#)ShipmentRefundDetailDaoImpl.java 1.0 2013-3-28上午10:39:10
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.oms.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.ParameterLong;
import com.chinadrtv.erp.model.trade.ShipmentRefund;
import com.chinadrtv.erp.model.trade.ShipmentRefundDetail;
import com.chinadrtv.erp.oms.dao.ShipmentRefundDetailDao;

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
 * @since 2013-3-28 上午10:39:10 
 * 
 */
@Repository
public class ShipmentRefundDetailDaoImpl extends GenericDaoHibernate<ShipmentRefundDetail, Long> implements
		ShipmentRefundDetailDao {

	/**
	* <p>Title: </p>
	* <p>Description: </p>
	* @param persistentClass
	*/ 
	public ShipmentRefundDetailDaoImpl() {
		super(ShipmentRefundDetail.class);
	}

	/* (none Javadoc)
	* <p>Title: getDetailList</p>
	* <p>Description: </p>
	* @param sr
	* @return
	* @see com.chinadrtv.erp.oms.dao.ShipmentRefundDetailDao#getDetailList(com.chinadrtv.erp.model.trade.ShipmentRefund)
	*/ 
	public List<ShipmentRefundDetail> getDetailList(ShipmentRefund sr) {
		String hql = "select srd from ShipmentRefundDetail srd where srd.shipmentRefund.id=:id";
		return this.findList(hql, new ParameterLong("id", sr.getId()));
	}
}
