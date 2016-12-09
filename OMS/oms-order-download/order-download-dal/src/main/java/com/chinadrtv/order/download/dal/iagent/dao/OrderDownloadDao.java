package com.chinadrtv.order.download.dal.iagent.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.trade.ShipmentDownControl;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liyu
 * Date: 13-3-13
 * Time: 下午6:45
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 * To change this template use File | Settings | File Templates.
 */
public interface OrderDownloadDao extends GenericDao<ShipmentDownControl, Long> {

    List<ShipmentDownControl> getShipmentDownControl();
}
