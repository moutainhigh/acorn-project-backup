package com.chinadrtv.erp.ic.util;

import com.chinadrtv.erp.ic.service.ItemInventoryTransactionService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.concurrent.Semaphore;

public class InventoryQuartzJobBean extends QuartzJobBean implements StatefulJob {

    private static final Logger LOG = LoggerFactory.getLogger(InventoryQuartzJobBean.class);

	private ItemInventoryTransactionService simpleService;

	public void setSimpleService(ItemInventoryTransactionService simpleService) {
		this.simpleService = simpleService;
	}

	@Override
	protected void executeInternal(JobExecutionContext jobexecutioncontext)
			throws JobExecutionException {
        LOG.info("库存事务启动...");
        simpleService.processUnhandledTransactionsEx("SYS");
	}

}
