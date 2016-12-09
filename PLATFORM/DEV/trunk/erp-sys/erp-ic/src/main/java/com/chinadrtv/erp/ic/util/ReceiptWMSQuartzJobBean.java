package com.chinadrtv.erp.ic.util;

import com.chinadrtv.erp.ic.service.ItemInventoryTransactionService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class ReceiptWMSQuartzJobBean extends QuartzJobBean implements StatefulJob {

	private ItemInventoryTransactionService simpleService;

	public void setSimpleService(ItemInventoryTransactionService simpleService) {
		this.simpleService = simpleService;
	}

	@Override
	protected void executeInternal(JobExecutionContext jobexecutioncontext)
			throws JobExecutionException {
        simpleService.receiptWMS("SYS");
	}

}
