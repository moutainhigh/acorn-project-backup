package com.chinadrtv.order.download.service.impl;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.model.trade.ShipmentDownControl;
import com.chinadrtv.erp.tc.core.dao.ShipmentHeaderDao;
import com.chinadrtv.order.download.dal.iagent.dao.OrderDownloadDao;
import com.chinadrtv.order.download.service.OrderFilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liyu
 * Date: 13-4-6
 * Time: 上午10:26
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 * To change this template use File | Settings | File Templates.
 */
@Service
public class OrderFilterServiceImpl extends GenericServiceImpl<ShipmentDownControl, Long>
        implements OrderFilterService {
    @Autowired
    private OrderDownloadDao orderDownControlDao;

    @Autowired
    private ShipmentHeaderDao shipmentHeaderDao;


    @Override
    protected GenericDao<ShipmentDownControl, Long> getGenericDao() {
        return orderDownControlDao;
    }

    public List<ShipmentDownControl> getShipmentDown() {
        //获取所有需要下传的订单
        List<ShipmentDownControl> list = orderDownControlDao.getShipmentDownControl();
        return list;
    }

    public Integer getTaskNo(){
        Integer taskNo = shipmentHeaderDao.generateTaskNo();
        return taskNo;
    }
}
