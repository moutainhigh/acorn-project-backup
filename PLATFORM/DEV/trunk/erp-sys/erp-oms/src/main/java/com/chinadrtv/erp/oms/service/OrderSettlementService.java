package com.chinadrtv.erp.oms.service;


import com.chinadrtv.erp.model.OrderSettlement;

import java.util.List;

public interface OrderSettlementService {
    List<OrderSettlement> getAllSettlements();
    List<OrderSettlement> getAllSettlements(int index, int size);
    List<OrderSettlement> getAllSettlements(String sourceId, int index, int size);
    Long getSettlementCount();
    Long getSettlementCount(String sourceId);
    Long getSettlementCount(String sourceId, String tradeId, String shipmentId, String startDate, String endDate);
    List<OrderSettlement> getAllSettlements(String sourceId, String tradeId, String shipmentId, String startDate, String endDate, int index, int size);
    void removeSettlement(Long id);
    void approveSettlement(Long id);
}
