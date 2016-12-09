package com.chinadrtv.erp.ic.util;

import com.chinadrtv.erp.ic.service.ItemInventorySnapshotService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class SnapshotQuartzJobBean extends QuartzJobBean implements StatefulJob {

    private static final Logger LOG = LoggerFactory.getLogger(SnapshotQuartzJobBean.class);

	private ItemInventorySnapshotService simpleService;

	public void setSimpleService(ItemInventorySnapshotService simpleService) {
		this.simpleService = simpleService;
	}

	@Override
	protected void executeInternal(JobExecutionContext jobexecutioncontext)
			throws JobExecutionException {
        LOG.info("库存定时快照启动...");
        simpleService.captureEx("SYS");
	}

}
