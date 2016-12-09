package com.chinadrtv.erp.oms.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.trade.ShipmentDetail;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-3-27
 * Time: 下午7:05
 * To change this template use File | Settings | File Templates.
 */
public interface ShipmentDetailDao extends GenericDao<ShipmentDetail,Long> {

    //保存明细
    public void saveShipmentDetail(ShipmentDetail shipmentDetail);

    //查询减免后的运费
    public List getCarrier(String rShipmentId);

    //删除多次减免运费原有明细
    public void deleteDetailByShipmentId(String shipmentId);

}
