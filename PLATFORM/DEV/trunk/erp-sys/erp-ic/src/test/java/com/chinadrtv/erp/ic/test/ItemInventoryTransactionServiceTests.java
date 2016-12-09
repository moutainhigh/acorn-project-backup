package com.chinadrtv.erp.ic.test;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.chinadrtv.erp.ic.service.ItemInventoryTransactionService;
import com.chinadrtv.erp.model.inventory.ItemInventoryTransaction;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * 库存事务测试 User: gaodejian Date: 13-1-31 Time: 上午10:24 To change this template
 * use File | Settings | File Templates.
 */
public class ItemInventoryTransactionServiceTests extends ErpIcContextTests {

    private static final Logger LOG = LoggerFactory.getLogger(ItemInventoryTransactionServiceTests.class);
	@Autowired
	private ItemInventoryTransactionService itemInventoryTransactionService;

	@Test
	public void processUnhandledTransactions() {
		itemInventoryTransactionService.processUnhandledTransactionsEx("TEST");
	}

	@Test
	public void receiptWMS() {
		itemInventoryTransactionService.receiptWMS("TEST");
	}

	@Test
	public void receiptSCM() {
		itemInventoryTransactionService.receiptSCM("TEST");
	}

    @Test
    public void recountItemStatus() throws Exception {
        itemInventoryTransactionService.recountItemStatus("TEST");
        //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
    }

    @Test
    public void recountItemStatusWithError() {
        itemInventoryTransactionService
                .recountItemStatus("TEST012345678901234567890123456789012345678901234567890123456789");
        try{
            Thread.sleep(1000);
        }catch (Exception ex){
           /* do nothing */
        }
    }

    @Test
    public void recountItemAdjustment() {
        itemInventoryTransactionService.recountItemAdjustment("TEST");
    }

	@Test
	public void newItemInventoryTransaction() {
		ItemInventoryTransaction itemInventoryTransaction = new ItemInventoryTransaction();
		itemInventoryTransaction.setChannel("ACORN");
		itemInventoryTransaction.setChannelEx("ACORN");
		itemInventoryTransaction.setCreated(new Date());
		itemInventoryTransaction.setLocationType("1");
		itemInventoryTransaction.setLocationTypeEx("1");
		itemInventoryTransaction.setProductCode("112090290000");
		itemInventoryTransaction.setProductId(104422L);
		itemInventoryTransaction.setQty(0.00);
        itemInventoryTransaction.setEstimatedQty(2.00);
		itemInventoryTransaction.setWarehouse("1");
		itemInventoryTransaction.setBusinessNo("999999999");
		itemInventoryTransaction.setBusinessType("216");
        itemInventoryTransaction.setBusinessChildType("1");
        itemInventoryTransaction.setBatchDate(new Date());
		itemInventoryTransaction.setSourceId(1L);
        itemInventoryTransaction.setModified(new Date());
        itemInventoryTransaction.setCreated(new Date());
        itemInventoryTransaction.setModifiedBy("TEST1");
        itemInventoryTransaction.setCreatedBy("TEST1");
		itemInventoryTransactionService.insertItemInventoryTransaction(itemInventoryTransaction);
	}
}
