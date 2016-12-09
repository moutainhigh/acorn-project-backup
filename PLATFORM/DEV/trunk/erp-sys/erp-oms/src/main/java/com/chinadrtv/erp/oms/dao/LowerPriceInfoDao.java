package com.chinadrtv.erp.oms.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.trade.ShipmentHeader;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-12-18
 * Time: 上午11:29
 * To change this template use File | Settings | File Templates.
 */
public interface LowerPriceInfoDao extends GenericDao<ShipmentHeader,Long> {

    //分页显示数据
    List getShipmentHeader(String orderId,String mailId,int index,int size);
    //获取信息总条数
    Long getShipmentHeaderCount(String orderId,String mailId);

    //折扣商品订单明细
    List getOrderDetails(String orderId);

    //订单折扣后金额
    String getAmountByShipmentTotalPriceAdj(String orderId);
}
