package com.chinadrtv.erp.ic.dao;

import com.chinadrtv.erp.model.inventory.ItemInventoryTransaction;

import java.util.List;

/**
 * @author cuiming
 * @version 1.0
 * @since 2013-1-28 下午3:29:10
 * 
 */
public interface ItemInventoryTransactionDao {

	List<ItemInventoryTransaction> getUnhandledBatchTransactions();
	void saveOrUpdateItemTransaction(ItemInventoryTransaction transaction);
    void executeItemTransactions(Long startId, Long endId, String batchId, String batchUser);

}
