package com.chinadrtv.order.download.biz.impl;

import com.chinadrtv.erp.model.trade.ShipmentDownControl;
import com.chinadrtv.erp.tc.core.constant.AccountStatusConstants;
import com.chinadrtv.erp.tc.core.constant.LogisticsStatusConstants;
import com.chinadrtv.order.download.biz.OrderDownloadBizHandler;
import com.chinadrtv.order.download.service.OrderDownWmsService;
import com.chinadrtv.order.download.service.OrderFilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-11-21
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class OrderDownloadBizHandlerImpl implements OrderDownloadBizHandler {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(OrderDownloadBizHandlerImpl.class);

    @Autowired
    private OrderFilterService orderFilterService;
    @Autowired
    private OrderDownWmsService orderDownWmsService;

    private AtomicBoolean isRun=new AtomicBoolean(false);

    @Override
    public boolean orderDownload(Date startDate) {
        //如果有在处理，那么直接忽略此消息
        if(isRun.compareAndSet(false,true))
        {
            logger.info("begin order download wms");
            try
            {
                Integer taskNo = orderFilterService.getTaskNo();

                List<ShipmentDownControl> list = orderFilterService.getShipmentDown();

                //优先处理正常下传订单
                for (int i = 0; i < list.size(); i++) {

                    ShipmentDownControl shipment = list.get(i);

                    logger.info("order download wms shipment id: " + shipment.getShipmentId());

                    //处理每一笔订单进行下传
                    try {

                        //判断是否符合要求
                        if (shipment.getLogisticsStatus().equals(LogisticsStatusConstants.LOGISTICS_NORMAL)
                                && shipment.getOrderStatus().equals(AccountStatusConstants.ACCOUNT_TRANS)) {
                            //赋予任务号
                            shipment.setTaskNo(taskNo.toString());

                            //下传订单并保存到接口库
                            orderDownWmsService.shipmentDownToWms(shipment);

                            logger.info("order download wms shipment id: " + shipment.getShipmentId() + " succ!!!");
                        }
                        else{
                            logger.info("order download wms shipment id: " + shipment.getShipmentId() + " - order status:"+ shipment.getOrderStatus());
                        }

                    } catch (Exception ex) {
                        logger.error("order download wms shipment id: " + shipment.getShipmentId() + " error: ", ex);
                        throw ex;
                    }
                }

                //处理取消订单
                for (int i = 0; i < list.size(); i++) {

                    ShipmentDownControl shipment = list.get(i);

                    logger.info("cancel order download wms shipment id: " + shipment.getShipmentId());

                    //处理每一笔订单进行下传
                    try {

                        //判断订单取消（订单状态：取消/物流取消。 物流状态：未出库）
                        if (shipment.getLogisticsStatus().equals(LogisticsStatusConstants.LOGISTICS_CANCEL)) {
                            if (shipment.getOrderStatus().equals(AccountStatusConstants.ACCOUNT_CANCEL)
                                    || shipment.getOrderStatus().equals(AccountStatusConstants.ACCOUNT_WMSCANCEL)) {
                                //赋予任务号
                                shipment.setTaskNo(taskNo.toString());
                                orderDownWmsService.cancelShipmentDownToWms(shipment);
                            }
                        }
                        else{
                            logger.info("cancel order download wms shipment id: " + shipment.getShipmentId() + " : order status:"+shipment.getOrderStatus());
                        }

                    } catch (Exception ex) {
                        logger.error("cancel order download wms shipment id: " + shipment.getShipmentId() + " error:", ex.toString());
                        throw ex;
                    }
                }
            }
            catch (Exception exp)
            {
                logger.error("order downlaod error:", exp);
                throw new RuntimeException(exp.getMessage());
            }
            finally {
                isRun.set(false);
                logger.info("end order download wms");
            }
            return true;
        }
        else
        {
            logger.error("order download wms is running!!!");
            return false;
        }
    }
}
