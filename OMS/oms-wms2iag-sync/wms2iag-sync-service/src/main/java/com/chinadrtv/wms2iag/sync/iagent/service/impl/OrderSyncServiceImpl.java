package com.chinadrtv.wms2iag.sync.iagent.service.impl;

import com.chinadrtv.wms2iag.sync.dal.iagent.dao.OrderExtDao;
import com.chinadrtv.wms2iag.sync.dal.model.OrderExt;
import com.chinadrtv.wms2iag.sync.iagent.service.OrderSyncService;
import com.chinadrtv.wms2iag.sync.iagent.service.ShipmentSyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-11-20
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class OrderSyncServiceImpl implements OrderSyncService {

    @Autowired
    private OrderExtDao orderExtDao;

    @Autowired
    private ShipmentSyncService shipmentSyncService;

    @Override
    public void syncOrders(List<OrderExt> orderExtList) {
        //找到插入和更新的
        List<OrderExt> insertList=new ArrayList<OrderExt>();
        List<OrderExt> updateList=new ArrayList<OrderExt>();

        //过滤掉已经同步过的
        this.getOrderExtOpers(orderExtList,insertList,updateList);

        if(insertList.size()>0)
        {
            for(OrderExt orderExt:insertList)
            {
                orderExtDao.insertData(orderExt);
            }
        }
        if(updateList.size()>0)
        {
            for(OrderExt orderExt:updateList)
            {
                orderExtDao.updateData(orderExt);
            }
        }

        //过滤掉已经同步过的
        int size=insertList.size()+updateList.size();
        if(size==0)
            return;

        if(size<orderExtList.size())
        {
            orderExtList=insertList;
            orderExtList.addAll(updateList);
        }
        shipmentSyncService.updateShipmentFromWms(orderExtList);
    }

    private void getOrderExtOpers(List<OrderExt> orderExtList, List<OrderExt> insertList,List<OrderExt> updateList)
    {
        List<String> list=new ArrayList<String>();
        for(OrderExt orderExt:orderExtList)
        {
            if(!list.contains(orderExt.getOrderId()))
            {
                list.add(orderExt.getOrderId());
            }
        }

        List<OrderExt> orderIdList = orderExtDao.findOrderIds(list);
        if(orderIdList==null)
            orderIdList=new ArrayList<OrderExt>();

        //
        for(OrderExt orderExt:orderExtList)
        {
            int nFlag= this.orderExtExistFlag(orderExt,orderIdList);
            if(nFlag>0)
            {
                updateList.add(orderExt);
            }
            else if(nFlag<0)
            {
                insertList.add(orderExt);
                orderIdList.add(orderExt);
            }
        }
    }

    private int orderExtExistFlag(OrderExt orderExt,List<OrderExt> orderExtList)
    {
        for(OrderExt orderExtExist:orderExtList)
        {
            if(orderExt.getOrderId().equals(orderExtExist.getOrderId()))
            {
                if(orderExt.getWmsStatus().equals(orderExtExist.getWmsStatus()))
                {
                    return 0;
                }
                else
                {
                    return 1;
                }
            }
        }
        return -1;
    }
}
