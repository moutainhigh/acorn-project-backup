package com.chinadrtv.erp.oms.service;


import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.oms.dto.LogisticsDto;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.chinadrtv.erp.core.service.GenericService;
import com.chinadrtv.erp.model.trade.ShipmentHeader;
import com.chinadrtv.erp.oms.dto.ReceiptPaymentDto;

public interface ShipmentHeaderService extends GenericService<ShipmentHeader, Long>{
    /**
     * 获取发运单号的发运单
     * @param id
     * @return
     */
    ShipmentHeader getShipmentById(Long id);
    Long getShipmentCount(String shipmentId, String orderId);
    List<ShipmentHeader> getShipments(String shipmentId, String orderId, int index, int size);
    /**
     * 获取制定发运单号或订单的发运单
     * @param shipmentId
     * @return
     */
    List<ShipmentHeader> getShipments(String shipmentId, String orderId);

    /**
     * 获取物流发运单总量
     * @param params
     * @return
     */
    Long getLogisticsShipmentCount(Map<String, Object> params);
    /**
     * 获取物流发运单列表
     * @param params
     * @param index
     * @param size
     * @return
     */
    List<LogisticsDto> getLogisticsShipments(Map<String, Object> params, int index, int size);

    /**
     * 修改出货单仓库、送货公司(有送货单)
     */
    void alternate(Long shipmentId, String warehouse, String entityId);
    /**
     * 修改出货单仓库、送货公司(无送货单)
     */
    void alternate(String orderId, String warehouseId, String entityId);

    //start by xzk
    ShipmentHeader getShipmentFromShipmentId(String shipmentId);

    ShipmentHeader getShipmentFromOrderId(String orderId);
    
    /**
     * 应收应付款核对查询
     * @param shipmentId 发运单号
     * @param accountNo 结算批号
     * @param mailId 邮件号
     * @param entityId 承运商
     * @param warehouse 仓库
     * @param accDtS 结账反馈日期-开始
     * @param accDtE 结账反馈日期-结束
     * @return
     */
    List<ReceiptPaymentDto> getReceiptPayment(Map<String, Object> params,int index, int size );
    
    /**
     * 获取应收应付款记录数
     * @param params
     * @return
     */
	Long getReceiptPaymentCount(Map<String, Object> params);
	
	/**
	 * 导出excel
	 * @param params
	 * @return
	 */
	public HSSFWorkbook export(Map<String, Object> params);
	
	/**
	 * 通过AssociatedId查找ShipmentHeader
	 * @param id
	 * @return
	 */
	List<ShipmentHeader> getShipmentByAssociatedId(String id);
	
	/**
	 * 检查是否可以结算
	 * @param shipmentHeaderId
	 * @return
	 */
	String accAble(String shipmentHeaderId);
	
	/**
	 * 生成一个结算
	 * @param id
	 * @param userId
	 * @return
	 */
	String paymentCheck(String id, String userId);

}
