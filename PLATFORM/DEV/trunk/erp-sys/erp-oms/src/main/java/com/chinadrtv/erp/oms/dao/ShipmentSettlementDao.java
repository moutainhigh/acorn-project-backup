package com.chinadrtv.erp.oms.dao;

import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.trade.ShipmentHeader;
import com.chinadrtv.erp.model.trade.ShipmentSettlement;

/**
 * 结算单-数据访问类
 * User: gaodejian
 * Date: 13-3-7
 * Time: 下午1:17
 * To change this template use File | Settings | File Templates.
 */
public interface ShipmentSettlementDao extends GenericDao<ShipmentSettlement,Long> {
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
     * 结算单
     * @param settlementIds
     * @return
     */
    List<ShipmentSettlement> getSettlements(Long[] settlementIds);
    
    List<ShipmentSettlement> findByShipmentHeader(ShipmentHeader shipmentHeader);

    /**
     * 获取结算单信息
     * @param params
     * @param index
     * @param size
     * @return
     */
    List getShipmentSettlementInfo(Map<String, Object> params, int index, int size);

}
