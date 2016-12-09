package com.chinadrtv.erp.ic.util;

import com.chinadrtv.erp.ic.service.WmsOutOrderService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-2-26
 * Time: 下午2:01
 * To change this template use File | Settings | File Templates.
 * 定时WMS出库单
 */
public class WmsOutOrderQuartzJobBean extends QuartzJobBean implements StatefulJob {
    private WmsOutOrderService simpleService;

    public void setSimpleService(WmsOutOrderService simpleService)
    {
        this.simpleService = simpleService;
    }

    @Override
    protected void executeInternal(JobExecutionContext jobexecutioncontext)
            throws JobExecutionException {
        simpleService.shipmentsWMS("SYS");
    }
}
