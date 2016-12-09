package com.chinadrtv.oms.sfexpress.dal.dao;

import com.chinadrtv.oms.sfexpress.dal.model.WmsShipmentDetail;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 14-2-17
 * Time: 下午5:31
 * To change this template use File | Settings | File Templates.
 */
public interface WmsShipmentDetailsDao {
    //获取商品明细
    List<WmsShipmentDetail> findDetails(String shipmentId);
}
