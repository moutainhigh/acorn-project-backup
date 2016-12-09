package com.chinadrtv.erp.oms.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.OrderSettlement;

import java.util.List;

/**
 * 结算单数据访问接口
 * User: Administrator
 * Date: 13-1-11
 * Time: 上午11:22
 * To change this template use File | Settings | File Templates.
 */
public interface OrderSettlementDao extends GenericDao<OrderSettlement,Long> {
    List<OrderSettlement> getAllSettlements();
    List<OrderSettlement> getAllSettlements(int index, int size);
    List<OrderSettlement> getAllSettlements(String sourceId, int index, int size);

    Long getSettlementCount();
    Long getSettlementCount(String sourceId);
    Long getSettlementCount(String sourceId, String tradeId, String shipmentId, String startDate, String endDate);
    List<OrderSettlement> getAllSettlements(String sourceId, String tradeId, String shipmentId, String startDate, String endDate, int index, int size);
    void approveSettlement(Long id);
}
