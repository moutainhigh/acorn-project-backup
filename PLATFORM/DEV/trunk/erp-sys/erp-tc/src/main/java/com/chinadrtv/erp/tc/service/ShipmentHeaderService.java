package com.chinadrtv.erp.tc.service;

import java.util.Map;
import com.chinadrtv.erp.core.service.GenericService;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.model.trade.ShipmentHeader;
import com.chinadrtv.erp.tc.core.constant.model.shipment.RequestScanOutInfo;
import com.chinadrtv.erp.tc.core.dto.ShipmentSenderDto;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 13-2-5
 */

public interface ShipmentHeaderService extends GenericService<ShipmentHeader,Long> {
	
	/**
	 * 取消旧的运单
	* @Description:
	* @return void
	* @throws
	 */
    ShipmentHeader cancelShipmentHeader(Order oh, String modifyUser);

	/**
	 * 产生运单 
	* @Description:
	* @param orderhist
	* @param isCancel	是不是取消订单生成运单
	* @throws Exception
	* @return int
	* @throws
	 */
	ShipmentHeader generateShipmentHeader(Order orderhist, boolean isCancel) throws Exception;

	/**
	 * 分配库存
	 * @Description:
	 * @param orderhist
	 * @return boolean
	 * @throws
	 */
	//boolean assignInventory(Order orderhist, String mdusr);
	
	/**
	 * 取消分配库存
	* @Description: 
	* @param oldSh
	* @param modifyUser
	* @return void
	* @throws
	 */
	//boolean unassignInventory(ShipmentHeader oldSh, String modifyUser);
	
	/**
	 * 分配库存
	 * @Description:
	 * @param orderhist
	 * @param warehouseId
	 * @param mdusr
	 * @return boolean
	 * @throws
	 */
	//boolean assignInventory(Order orderhist, String warehouseId, String mdusr);

	/**
	 * 取消分配库存
	 * @Description:
	 * @param orderhist
	 * @return boolean
	 * @throws
	 */
	//boolean unasignInventory(Order orderhist, String mdusr);
	
	
    /**
     * 同步取消运单
	* @Description: 同步取消运单
	* @param params
	* @return int
	* @throws
	*/ 
	int cancelWayBill(Map<String, Object> params) throws Exception;
	
	/**
	 * 运单重发生成新的运单
	* @Description:
	* @param orderhist
	* @throws Exception
	* @return int
	 */
	//ShipmentHeader resendGenerateShipmentHeader(Order orderhist, String entityId, String warehouseId) throws Exception ;
	
	/**
	 * 根据送货公司和仓库地址产生运单 
	* @Description: 
	* @param orderhist
	* @param entityId
	* @param warehouseId
	* @throws Exception
	* @return int
	 */
	ShipmentHeader generateShipmentHeader(Order orderhist, String entityId, String warehouseId) throws Exception;

	/**
	 * 运单重发
	* @Description: 
	* @param orderhist
	* @param entityId
	* @param warehouseId
	* @param isInStock		是否是库内改单重发运单
	* @throws Exception
	* @return int
	 */
	ShipmentHeader generateShipmentHeader(Order orderhist, String entityId, String warehouseId, boolean isInStock) throws Exception;

	/**
	 * 
	* @Description: 
	* @param orderid
	* @return
	* @throws Exception
	* @return ShipmentSenderDto
	* @throws
	 */
    ShipmentSenderDto queryWaybill(String orderid) throws Exception;

	/**
	 * 重发运单
	* @Description: 
	* @param shipmentSenderDto
	* @return void
	* @throws
	*/ 
	int resendWaybill(ShipmentSenderDto shipmentSenderDto) throws Exception;

	/**
	 * SR3.6_1.5 更新运单的配送公司
	* @Description: 
	* @param params
	* @throws Exception
	* @return int
	 */
	int updateShipmentEntity(Map<String, Object> params) throws Exception;
	
    /**
     * 添加发运单、包括发运单明细
     * @param shipmentHeader
     */
	ShipmentHeader addShipmentHeader(ShipmentHeader shipmentHeader) throws Exception;

    /**
     * 更新发运单
     * @param shipmentHeader
     */
	ShipmentHeader updateShipmentHeader(ShipmentHeader shipmentHeader);
	
	/**
	 * 保存或更新发运单
	* @Description: 
	* @param shipmentHeader
	* @return
	* @throws Exception
	* @return ShipmentHeader
	* @throws
	 */
	ShipmentHeader addOrUpdateShipmentHeader(ShipmentHeader shipmentHeader) throws Exception;

    /**
     * 根据Id获取发运单信息
     * @param shipmentHeaderId
     * @return
     */
    ShipmentHeader getShipmentHeader(Long shipmentHeaderId) throws Exception;

    /**
     * 根据订单Id来获取最新的发运单信息
     * @param orderId
     * @return
     */
    ShipmentHeader getShipmentHeaderFromOrderId(String orderId);

	/**
	 * 根据shipmentId 获取 ShipmentHeader
	* @Description: 
	* @param shipmentId
	* @return
	* @return ShipmentHeader
	* @throws
	*/ 
	ShipmentHeader getByShipmentId(String shipmentId);

	/**
	* @Description: 
	* @param orderhist
	* @param b
	* @return void
	* @throws
	*/ 
	void generateWaybill(Order orderhist, boolean b) throws Exception;

    /**
     * 运单出库扫描
     * @param requestScanOutInfo
     * @throws Exception
     */
    void scanOutShipment(RequestScanOutInfo requestScanOutInfo) throws Exception;

    /**
     * 运单结算
     * @param params
     * @throws Exception
     */
    boolean settleAccountsShipment(Map<String, Object> params) throws Exception;

}
