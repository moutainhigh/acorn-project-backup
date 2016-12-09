package com.chinadrtv.erp.ic.service;

import com.chinadrtv.erp.model.inventory.ItemInventoryTransaction;

/**
 * @author cuiming
 * @version 1.0
 * @since 2013-1-28 下午3:39:14
 * 
 */
public interface ItemInventoryTransactionService {

	public Boolean insertItemInventoryTransaction(
            ItemInventoryTransaction itemInventoryTransaction);

    @Deprecated
    void processUnhandledTransactions(String batchUser);
    void processUnhandledTransactionsEx(String batchUser);
    void receiptSCM(String batchUser);
    void receiptWMS(String batchUser);
    void recountItemStatus(String batchUser);
    void recountItemAdjustment(String batchUser);
}
