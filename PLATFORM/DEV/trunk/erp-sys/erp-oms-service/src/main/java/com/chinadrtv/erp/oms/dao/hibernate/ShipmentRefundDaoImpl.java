package com.chinadrtv.erp.oms.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.model.trade.ShipmentRefund;
import com.chinadrtv.erp.oms.dao.ShipmentRefundDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-11-29
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Repository
public class ShipmentRefundDaoImpl extends GenericDaoHibernate<ShipmentRefund, Long> implements ShipmentRefundDao {

    public ShipmentRefundDaoImpl() {
        super(ShipmentRefund.class);
    }

    @Override
    public List<ShipmentRefund> getShipmentRefundFromOrderId(String orderId) {
        return this.findList("from ShipmentRefund where orderId=:orderId",new ParameterString("orderId",orderId));
    }
}
