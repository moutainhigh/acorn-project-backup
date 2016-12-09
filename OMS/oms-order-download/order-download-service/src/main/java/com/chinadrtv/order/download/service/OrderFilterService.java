package com.chinadrtv.order.download.service;

import com.chinadrtv.erp.core.service.GenericService;
import com.chinadrtv.erp.model.trade.ShipmentDownControl;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liyu
 * Date: 13-4-6
 * Time: 上午10:24
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 * To change this template use File | Settings | File Templates.
 */
public interface OrderFilterService extends GenericService<ShipmentDownControl, Long> {

    List<ShipmentDownControl> getShipmentDown();

    Integer getTaskNo();

}
