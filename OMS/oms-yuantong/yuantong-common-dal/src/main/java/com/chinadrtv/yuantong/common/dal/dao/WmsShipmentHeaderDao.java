package com.chinadrtv.yuantong.common.dal.dao;

import com.chinadrtv.yuantong.common.dal.model.WmsShipmentDetail;
import com.chinadrtv.yuantong.common.dal.model.WmsShipmentHeader;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 14-2-13
 * Time: 下午3:50
 * To change this template use File | Settings | File Templates.
 * 圆通数据反馈
 */
public interface WmsShipmentHeaderDao {
    //从wms库获取圆通反馈数据
    List<WmsShipmentHeader> findShipmentHeader();
    //更新标记
    int updateShipmentHeader(String shipmentId);
    //获取商品明细
    List<WmsShipmentDetail> findDetails(String shipmentId);
}
