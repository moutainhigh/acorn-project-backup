package com.chinadrtv.erp.oms.dao;

import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.trade.ShipmentHeader;

/**
 * 发运单-数据访问类
 * User: gaodejian
 * Date: 13-3-7
 * Time: 下午1:17
 * To change this template use File | Settings | File Templates.
 */
public interface ShipmentHeaderDao  extends GenericDao<ShipmentHeader,Long>{
    /**
     * 获取发运单号的发运单
     * @param id
     * @return
     */
    ShipmentHeader getShipmentById(Long id);
    Long getShipmentCount(String shipmentId, String orderId);
    List<ShipmentHeader> getShipments(String shipmentId, String orderId, int index, int size);
    /**
     * 获取指定发运单号或订单号的发运单
     * @param shipmentId
     * @return
     */
    List<ShipmentHeader> getShipments(String shipmentId, String orderId);


    Long getLogisticsShipmentCount(Map<String, Object> params);

    /**
     * 物流发运单(物流变更承运商)
     * @param params
     * @param index
     * @param size
     * @return
     */
    List getLogisticsShipments(Map<String, Object> params, int index, int size);
    
    /**
	 * 根据shipmentId 获取 ShipmentHeader
	* @Description: 
	* @param shipmentId
	* @return
	* @return ShipmentHeader
	* @throws
	*/ 
	ShipmentHeader queryByShipmentId(String shipmentId);


    //start by xzk
    ShipmentHeader getShipmentFromShipmentId(String shipmentId);

    ShipmentHeader getShipmentFromOrderId(String orderId);
    
    List<Object[]> getReceiptPayments(Map<String, Object> params, int index, int size);
    
	Long getReceiptPaymentCount(Map<String, Object> params);
	List<ShipmentHeader> getShipmentByAssociatedId(String id);
	List<Object[]> accAble(String shipmentHeaderId);
    
}
