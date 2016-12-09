package com.chinadrtv.erp.oms.service.impl;


import com.chinadrtv.erp.model.OrderSettlement;
import com.chinadrtv.erp.oms.dao.OrderSettlementDao;
import com.chinadrtv.erp.oms.service.OrderSettlementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("orderSettlemenService")
public class OrderSettlementServiceImpl implements OrderSettlementService {

    @Autowired
    private OrderSettlementDao orderSettlementDao;

    public List<OrderSettlement> getAllSettlements()
    {
        return orderSettlementDao.getAllSettlements();
    }

    public List<OrderSettlement> getAllSettlements(int index, int size){
        return orderSettlementDao.getAllSettlements(index, size);
    }

    public List<OrderSettlement> getAllSettlements(String sourceId, int index, int size)
    {
        return orderSettlementDao.getAllSettlements(sourceId, index, size);
    }

    public Long getSettlementCount(){
        return orderSettlementDao.getSettlementCount();
    }

    public Long getSettlementCount(String sourceId){
        return orderSettlementDao.getSettlementCount(sourceId);
    }

    public Long getSettlementCount(String sourceId, String tradeId, String shipmentId, String startDate, String endDate)
    {
        return orderSettlementDao.getSettlementCount(sourceId, tradeId, shipmentId, startDate, endDate);
    }

    public List<OrderSettlement> getAllSettlements(String sourceId, String tradeId, String shipmentId, String startDate, String endDate, int index, int size)
    {
        return orderSettlementDao.getAllSettlements(sourceId, tradeId, shipmentId, startDate, endDate, index, size);
    }

    public void removeSettlement(Long id){
        orderSettlementDao.remove(id);
    }

    public void approveSettlement(Long id){
        orderSettlementDao.approveSettlement(id);
    }
}
