package com.chinadrtv.erp.tc.core.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.trade.ShipmentHeader;
import com.chinadrtv.erp.model.trade.WmsShipmentHeader;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 13-2-5
 */
public interface ShipmentHeaderDao extends GenericDao<ShipmentHeader,Long> {

    ShipmentHeader addShipmentHeader(ShipmentHeader shipmentHeader);

    ShipmentHeader updateShipmentHeader(ShipmentHeader shipmentHeader);

    void deleteShipmentHeader(ShipmentHeader shipmentHeader);
    
    /**
	* @Description: 
	* @param orderId
	* @return
	* @return ShipmentHeader
	* @throws
	*/
    List<ShipmentHeader> getByOrderId(String orderId);

    ShipmentHeader getByShipmentId(String shipmentId);
	
    /**
	* @Description: 
	* @param params
	* @return
	* @return List<ShipmentHeader>
	* @throws
	*/ 
    @Deprecated
	List<ShipmentHeader> queryShipmentList(Map<String, Object> params);

    /**
     * 获取流水号
     * @Description: 
     * @return
     * @return String
     * @throws
     */
    Integer generateTaskNo();

    /**
     * @Description:
     * @param shipmentId
     * @return WmsShipmentHeader
     * @throws
     */
    WmsShipmentHeader queryByShipmentId(String shipmentId);

    ShipmentHeader getByMailId(String mailId, String orderId);

    List<ShipmentHeader> queryShipmentFromOrderIds(Map<String,Long> orderIdList);

}
