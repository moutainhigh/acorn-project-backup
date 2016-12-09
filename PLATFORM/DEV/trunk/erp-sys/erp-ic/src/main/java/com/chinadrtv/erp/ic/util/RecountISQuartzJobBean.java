package com.chinadrtv.erp.ic.util;

import com.chinadrtv.erp.ic.service.ItemInventoryTransactionService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class RecountISQuartzJobBean extends QuartzJobBean implements StatefulJob {

    private static final Logger LOG = LoggerFactory.getLogger(RecountISQuartzJobBean.class);

	private ItemInventoryTransactionService simpleService;

	public void setSimpleService(ItemInventoryTransactionService simpleService) {
		this.simpleService = simpleService;
	}

	@Override
	protected void executeInternal(JobExecutionContext jobexecutioncontext)
			throws JobExecutionException {
        try
        {
            simpleService.recountItemStatus("SYS");
        }
        catch (Exception ex){
            LOG.error(ex.getMessage());
        }
	}

}
