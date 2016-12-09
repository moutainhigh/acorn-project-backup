package com.chinadrtv.erp.oms.service;


import com.chinadrtv.erp.model.PreItemInventory;

import java.util.List;

public interface PreItemInventoryService {
    Long getInventoryItemCount(String sourceId, String skuId, String outSkuId, String startDate, String endDate);
    List<PreItemInventory> getAllInventoryItems(String sourceId, String skuId, String outSkuId, String startDate, String endDate, int index, int size);
}
