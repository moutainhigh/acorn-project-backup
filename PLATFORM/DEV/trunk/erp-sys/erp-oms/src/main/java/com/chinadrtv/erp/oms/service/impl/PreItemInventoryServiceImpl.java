package com.chinadrtv.erp.oms.service.impl;


import com.chinadrtv.erp.model.PreItemInventory;
import com.chinadrtv.erp.oms.dao.PreItemInventoryDao;
import com.chinadrtv.erp.oms.service.PreItemInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("preItemInventoryService")
public class PreItemInventoryServiceImpl implements PreItemInventoryService {

    @Autowired
    private PreItemInventoryDao preItemInventoryDao;

    public Long getInventoryItemCount(String sourceId, String skuId, String outSkuId, String startDate, String endDate){
        return preItemInventoryDao.getInventoryItemCount(sourceId, skuId, outSkuId, startDate, endDate);
    }
    public List<PreItemInventory> getAllInventoryItems(String sourceId, String skuId, String outSkuId, String startDate, String endDate, int index, int size){
        return preItemInventoryDao.getAllInventoryItems(sourceId, skuId, outSkuId, startDate, endDate, index, size);
    }
}
