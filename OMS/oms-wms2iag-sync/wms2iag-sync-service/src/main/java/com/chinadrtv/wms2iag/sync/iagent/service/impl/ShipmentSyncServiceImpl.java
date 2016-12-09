package com.chinadrtv.wms2iag.sync.iagent.service.impl;

import com.chinadrtv.dal.oms.dao.ShipmentHeaderDao;
import com.chinadrtv.model.constant.LogisticsStatusConstants;
import com.chinadrtv.model.oms.ShipmentChangeHis;
import com.chinadrtv.model.oms.ShipmentHeader;
import com.chinadrtv.service.oms.ShipmentChangeHisService;
import com.chinadrtv.wms2iag.sync.dal.model.OrderExt;
import com.chinadrtv.wms2iag.sync.iagent.service.ShipmentSyncService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class ShipmentSyncServiceImpl implements ShipmentSyncService {

    private static final Logger logger = LoggerFactory.getLogger(ShipmentSyncServiceImpl.class);
    private static final String procName="wmsSync";

    @Autowired
    private ShipmentHeaderDao shipmentHeaderDao;

    @Autowired
    private ShipmentChangeHisService shipmentChangeHisService;

    public void updateShipmentFromWms(List<OrderExt> orderList) {

        if(orderList==null||orderList.size()==0)
            return;
        List<ShipmentHeader> shipmentHeaderList = findShipmentFromOrders(orderList);

        //表示已经存在的历史记录
        List<ShipmentChangeHis> exitShipmentChangeHisList=this.findShipmentChangeHisFromHeaders(shipmentHeaderList);
        List<ShipmentChangeHis> addShipmentChangeHisList=new ArrayList<ShipmentChangeHis>();

        //注意处理同一批中存在两个相同订单号的记录
        for (OrderExt orderExt : orderList) {
            ShipmentHeader shipmentHeader = null;
            for(ShipmentHeader shipmentHeader1:shipmentHeaderList)
            {
                if(orderExt.getOrderId().equals(shipmentHeader1.getOrderId()))
                {
                    shipmentHeader=shipmentHeader1;
                    break;
                }
            }



            if (shipmentHeader != null) {
                ShipmentChangeHis shipmentChangeHis = this.findLastFromShipmentChangeHisList(shipmentHeader.getShipmentId(),exitShipmentChangeHisList, addShipmentChangeHisList);

                logger.debug("shipment header id:" + shipmentHeader.getId());
                String wmsStatus = orderExt.getWmsStatus();
                String shipmentStatus = "";
                if (StringUtils.isNotEmpty(wmsStatus)) {
                    if ("1".equals(wmsStatus)) {
                        shipmentStatus = LogisticsStatusConstants.LOGISTICS_WMS_WAIT;
                    } else if ("2".equals(wmsStatus)) {
                        shipmentStatus = LogisticsStatusConstants.LOGISTICS_WMS_HANDLING;
                    } else if ("3".equals(wmsStatus)) {
                        shipmentStatus = LogisticsStatusConstants.LOGISTICS_WMS_DISTRIBUTION;
                    } else if ("4".equals(wmsStatus)) {
                        shipmentStatus = LogisticsStatusConstants.LOGISTICS_WMS_PICKING;
                    } else if ("5".equals(wmsStatus)) {
                        shipmentStatus = LogisticsStatusConstants.LOGISTICS_WMS_PACKING;
                    } else if ("7".equals(wmsStatus)) {
                        shipmentStatus = LogisticsStatusConstants.LOGISTICS_WMS_SCANNING;
                    } else if ("8".equals(wmsStatus)) {
                        shipmentStatus = LogisticsStatusConstants.LOGISTICS_WMS_POSTING;
                    } else if ("9".equals(wmsStatus)) {
                        shipmentStatus = LogisticsStatusConstants.LOGISTICS_WMS_OUT;
                    } else {
                        shipmentStatus = LogisticsStatusConstants.LOGISTICS_WMS_WAIT;
                    }

                    logger.debug("shipment header id:" + shipmentHeader.getId() + " status:" + shipmentStatus);
                    //先从历史中获取最后的，如果没有，那么直接使用运单里面的值
                    //判断是否相等，如果不相等，那么就记录
                    //如果一致，那么忽略
                    ShipmentChangeHis shipmentChangeHisAdd=null;
                    if(shipmentChangeHis!=null)
                    {
                        if(!orderExt.getWmsStatus().equals(shipmentChangeHis.getAfterLogisticsStatusId()))
                        {
                            shipmentChangeHisAdd=this.getShipmentChangeHisFromHis(orderExt,shipmentChangeHis,shipmentHeader, shipmentStatus);
                        }
                    }
                    else
                    {
                        shipmentChangeHisAdd=this.getShipmentChangeHisFromHeader(orderExt, shipmentHeader, shipmentStatus);
                    }
                    if(shipmentChangeHisAdd!=null)
                    {
                        addShipmentChangeHisList.add(shipmentChangeHisAdd);
                    }
                }
            } else {
                //直接修改历史
                logger.error("no shipment header from order id:" + orderExt.getOrderId());
            }
        }

        if(addShipmentChangeHisList.size()>0)
        {
            shipmentChangeHisService.saveShipmentChangeHisList(addShipmentChangeHisList);
        }
    }

    private List<ShipmentHeader> findShipmentFromOrders(List<OrderExt> orderList) {
        List<String> orderIdList=new ArrayList<String>();
        for(OrderExt orderExt:orderList)
        {
            if(!orderIdList.contains(orderExt.getOrderId()))
            {
                orderIdList.add(orderExt.getOrderId());
            }
        }

        List<ShipmentHeader> shipmentHeaderList = shipmentHeaderDao.findShipments(orderIdList);
        if(shipmentHeaderList==null)
            shipmentHeaderList=new ArrayList<ShipmentHeader>();

        return shipmentHeaderList;
    }

    private List<ShipmentChangeHis> findShipmentChangeHisFromHeaders(List<ShipmentHeader> shipmentHeaderList)
    {
        if(shipmentHeaderList==null||shipmentHeaderList.size()==0)
            return new ArrayList<ShipmentChangeHis>();

        List<String> shipmentIdList=new ArrayList<String>();
        for(ShipmentHeader shipmentHeader:shipmentHeaderList)
        {
            if(!shipmentIdList.contains(shipmentHeader.getShipmentId()))
            {
                shipmentIdList.add(shipmentHeader.getShipmentId());
            }
        }

        List<ShipmentChangeHis> shipmentChangeHisList = shipmentChangeHisService.findHisFromShipments(shipmentIdList);
        if(shipmentChangeHisList==null)
            shipmentChangeHisList=new ArrayList<ShipmentChangeHis>();

        return shipmentChangeHisList;
    }


    private ShipmentChangeHis findLastFromShipmentChangeHisList(String shipmentId, List<ShipmentChangeHis> exitShipmentChangeHisList, List<ShipmentChangeHis> addShipmentChangeHisList)
    {
         //
        for(ShipmentChangeHis shipmentChangeHis:addShipmentChangeHisList)
        {
             if(shipmentId.equals(shipmentChangeHis.getShipmentId()))
                 return shipmentChangeHis;
        }

        for(ShipmentChangeHis shipmentChangeHis:exitShipmentChangeHisList)
        {
            if(shipmentId.equals(shipmentChangeHis.getShipmentId()))
                return shipmentChangeHis;
        }
        return null;
    }

    private ShipmentChangeHis getShipmentChangeHisFromHis(OrderExt orderExt, ShipmentChangeHis shipmentChangeHis, ShipmentHeader shipmentHeader, String shipmentStatus)
    {
        ShipmentChangeHis shipmentChangeHisNew=new ShipmentChangeHis();
        shipmentChangeHisNew.setShipmentRefId(shipmentChangeHis.getShipmentRefId());
        shipmentChangeHisNew.setShipmentId(shipmentChangeHis.getShipmentId());
        shipmentChangeHisNew.setBeforeMailId(shipmentHeader.getMailId());
        shipmentChangeHisNew.setAfterMailId(shipmentHeader.getMailId());
        shipmentChangeHisNew.setBeforeEntityId(shipmentHeader.getEntityId());
        shipmentChangeHisNew.setAfterEntityId(shipmentHeader.getEntityId());
        shipmentChangeHisNew.setBeforeWarehouseId(shipmentHeader.getWarehouseId());
        shipmentChangeHisNew.setAfterWarehouseId(shipmentHeader.getWarehouseId());
        shipmentChangeHisNew.setBeforeAccountStatusId(shipmentChangeHis.getAfterAccountStatusId());
        shipmentChangeHisNew.setAfterAccountStatusId(shipmentChangeHis.getAfterAccountStatusId());
        shipmentChangeHisNew.setBeforeAccountStatusRemark(shipmentChangeHis.getAfterAccountStatusRemark());
        shipmentChangeHisNew.setAfterAccountStatusRemark(shipmentChangeHis.getAfterAccountStatusRemark());

        shipmentChangeHisNew.setBeforeLogisticsStatusId(shipmentChangeHis.getAfterLogisticsStatusId());
        shipmentChangeHisNew.setBeforeLogisticsStatusRemark(shipmentChangeHis.getAfterLogisticsStatusRemark());
        shipmentChangeHisNew.setAfterLogisticsStatusId(shipmentStatus);
        shipmentChangeHisNew.setAfterLogisticsStatusRemark(orderExt.getWmsDesc());

        shipmentChangeHisNew.setUserStamp(procName);
        shipmentChangeHisNew.setProcessStamp(procName);

        shipmentChangeHisNew.setDateTimeStamp(orderExt.getUpDate());

        return shipmentChangeHisNew;
    }

    private ShipmentChangeHis getShipmentChangeHisFromHeader(OrderExt orderExt, ShipmentHeader shipmentHeader, String shipmentStatus)
    {
        ShipmentChangeHis shipmentChangeHisNew=new ShipmentChangeHis();

        shipmentChangeHisNew.setShipmentRefId(shipmentHeader.getId());
        shipmentChangeHisNew.setShipmentId(shipmentHeader.getShipmentId());
        shipmentChangeHisNew.setBeforeMailId(shipmentHeader.getMailId());
        shipmentChangeHisNew.setAfterMailId(shipmentHeader.getMailId());
        shipmentChangeHisNew.setBeforeEntityId(shipmentHeader.getEntityId());
        shipmentChangeHisNew.setAfterEntityId(shipmentHeader.getEntityId());
        shipmentChangeHisNew.setBeforeWarehouseId(shipmentHeader.getWarehouseId());
        shipmentChangeHisNew.setAfterWarehouseId(shipmentHeader.getWarehouseId());
        shipmentChangeHisNew.setBeforeAccountStatusId(shipmentHeader.getAccountStatusId());
        shipmentChangeHisNew.setAfterAccountStatusId(shipmentHeader.getAccountStatusId());
        shipmentChangeHisNew.setBeforeAccountStatusRemark(shipmentHeader.getAccountStatusRemark());
        shipmentChangeHisNew.setAfterAccountStatusRemark(shipmentHeader.getAccountStatusRemark());

        shipmentChangeHisNew.setBeforeLogisticsStatusId(shipmentHeader.getLogisticsStatusId());
        shipmentChangeHisNew.setBeforeLogisticsStatusRemark(shipmentHeader.getLogisticsStatusRemark());
        shipmentChangeHisNew.setAfterLogisticsStatusId(shipmentStatus);
        shipmentChangeHisNew.setAfterLogisticsStatusRemark(orderExt.getWmsDesc());

        shipmentChangeHisNew.setUserStamp(procName);
        shipmentChangeHisNew.setProcessStamp(procName);

        shipmentChangeHisNew.setDateTimeStamp(orderExt.getUpDate());

        return shipmentChangeHisNew;

    }

}

