package com.chinadrtv.erp.oms.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.trade.ShipmentDetail;
import com.chinadrtv.erp.model.trade.ShipmentHeader;
import com.chinadrtv.erp.oms.common.DataGridModel;
import com.chinadrtv.erp.oms.dto.AvoidFreightDto;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-3-27
 * Time: 上午11:08
 * To change this template use File | Settings | File Templates.
 * 免运费登记
 */
public interface AvoidFreightDao extends GenericDao<ShipmentHeader,Long>{

    //分页显示数据
    List getFreightList(AvoidFreightDto avoidFreightDto, DataGridModel dataModel);
    //获取分页信息条数
    Integer queryCount(AvoidFreightDto avoidFreightDto);
    //根据id查询数据
    ShipmentHeader getShipmentById(Long id);
    //数据保存
    void saveShipmentHeader(ShipmentHeader shipmentHeader);
    //获取明细
    ShipmentHeader searchShipmentById(Long id);
    //删除标记数据
    void deleteByShipmentId(String shipmentId);
    //获取逆向发运单
    public ShipmentHeader searchShipmentByShipmentId(String  shipmentId);


}
