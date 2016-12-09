package com.chinadrtv.erp.oms.service;

import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.core.service.GenericService;
import com.chinadrtv.erp.model.CompanyContract;
import com.chinadrtv.erp.model.trade.ShipmentHeader;
import com.chinadrtv.erp.model.trade.ShipmentSettlement;
import com.chinadrtv.erp.oms.dto.ShipmentSettlementDto;

/**
 * 结算单-服务类
 * User: gaodejian
 * Date: 13-3-7
 * Time: 下午1:17
 * To change this template use File | Settings | File Templates.
 */
public interface ShipmentSettlementService extends GenericService<ShipmentSettlement, Long> {
    /**
     * 结算单数量
     * @param params
     * @return
     */
    Long getSettlementCount(Map<String, Object> params);
    /**
     * 结算单列表(可分页)
     * @param params
     * @param index
     * @param size
     * @return
     */
    List<ShipmentSettlement> getSettlements(Map<String, Object> params, int index, int size);

    /**
     * 结算单列表
     * @param settlementIds
     * @return
     */
    List<ShipmentSettlement> getSettlements(Long[] settlementIds);
    
    public ShipmentSettlement getById(Long id);

    /**
     * 通过ShipmentHeader查找ShipmentSettlement
     * @param shipmentHeader
     * @return
     */
    List<ShipmentSettlement> getShipmentHeader(ShipmentHeader shipmentHeader);
    
    /**
     * 对指定的ShipmentHeader进行结算操作
     * @param sh
     * @param cc 如果是“上海EMS则允许误差上下浮动1元”
     */
	void createShipmentSettlement(ShipmentHeader sh,CompanyContract cc);

    List<ShipmentSettlementDto> getShipmentSettlementDtoInfo(Map<String, Object> params, int index, int size);
	
}
