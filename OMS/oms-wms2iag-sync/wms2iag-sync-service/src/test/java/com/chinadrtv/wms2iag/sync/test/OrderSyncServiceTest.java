package com.chinadrtv.wms2iag.sync.test;

import com.chinadrtv.dal.oms.dao.ShipmentChangeHisDao;
import com.chinadrtv.model.oms.ShipmentChangeHis;
import com.chinadrtv.model.oms.dto.SequenceDto;
import com.chinadrtv.service.oms.ShipmentChangeHisService;
import com.chinadrtv.wms2iag.sync.dal.model.OrderExt;
import com.chinadrtv.wms2iag.sync.iagent.service.OrderSyncService;
import com.chinadrtv.wms2iag.sync.iagent.service.ShipmentSyncService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-11-21
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:/applicationContext*.xml")
@TransactionConfiguration(defaultRollback =false)
@Transactional("iagentTransactionManager")
public class OrderSyncServiceTest {

    @Autowired
    private ShipmentSyncService shipmentSyncService;

    @Autowired
    private ShipmentChangeHisService shipmentChangeHisService;

    @Autowired
    private ShipmentChangeHisDao shipmentChangeHisDao;

    @Autowired
    private OrderSyncService orderSyncService;

    //@Test
    public void testShipment()
    {
        shipmentSyncService.updateShipmentFromWms(new ArrayList<OrderExt>());
        List<String> list=new ArrayList<String>();
        list.add("959613272V002");
        list.add("928700202V001");
        List<ShipmentChangeHis> shipmentChangeHisList =shipmentChangeHisService.findHisFromShipments(list);
        System.out.println(shipmentChangeHisList.size());
    }

    @Test
    public void testShipmentChangeSequence()
    {
        Double a=1.23D;
        Double b=1.567D;
        Double dd=Math.ceil(b/a);
        System.out.println(dd);
        List<SequenceDto> sequenceDtoList =shipmentChangeHisDao.fecthSequence(3);
        System.out.println(sequenceDtoList.size());
    }

    //@Test
    public void testSync()
    {
        List<OrderExt> orderExtList=new ArrayList<OrderExt>();
        OrderExt orderExt1=new OrderExt();
        orderExt1.setUpDate(new Date());
        orderExt1.setOrderId("959617242");
        orderExt1.setWmsStatus("2");
        orderExt1.setWmsDesc("hhh");
        orderExtList.add(orderExt1);

        OrderExt orderExt2=new OrderExt();
        orderExt2.setUpDate(new Date());
        orderExt2.setOrderId("959617222");
        orderExt2.setWmsStatus("1");
        orderExt2.setWmsDesc("xxx");
        orderExtList.add(orderExt2);

       orderSyncService.syncOrders(orderExtList);
    }
}
