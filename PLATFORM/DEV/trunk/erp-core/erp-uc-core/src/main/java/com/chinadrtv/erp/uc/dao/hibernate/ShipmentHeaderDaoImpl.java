/*
 * @(#)ShipmentHeaderDaoImpl.java 1.0 2013-6-17下午3:05:22
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.uc.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.model.trade.ShipmentHeader;
import com.chinadrtv.erp.uc.dao.ShipmentHeaderDao;

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
 * @since 2013-6-17 下午3:05:22 
 * 
 */
@Repository
public class ShipmentHeaderDaoImpl extends GenericDaoHibernate<ShipmentHeader, Long> implements ShipmentHeaderDao {

	public ShipmentHeaderDaoImpl() {
		super(ShipmentHeader.class);
	}

	/** 
	 * <p>Title: queryShipmentHeaderByOrderId</p>
	 * <p>Description: </p>
	 * @param orderId
	 * @return ShipmentHeader
	 * @see com.chinadrtv.erp.uc.dao.ShipmentHeaderDao#queryShipmentHeaderByOrderId(java.lang.String)
	 */ 
	@Override
	public ShipmentHeader queryShipmentHeaderByOrderId(String orderId) {
		List<ShipmentHeader> shipmentHeaderList = this.findList("from ShipmentHeader where accountType='1' and orderId=:orderId", new ParameterString("orderId", orderId));
        if (shipmentHeaderList != null && shipmentHeaderList.size() > 0) {
            //找到没有取消的
            for (ShipmentHeader shipmentHeader : shipmentHeaderList) {
                if (!shipmentHeader.getLogisticsStatusId().equals("1")) {
                    return shipmentHeader;
                }
            }
        }

        return null;
	}

}
