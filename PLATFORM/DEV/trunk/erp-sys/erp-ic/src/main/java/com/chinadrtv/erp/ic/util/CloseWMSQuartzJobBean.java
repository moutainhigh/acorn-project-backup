package com.chinadrtv.erp.ic.util;

import com.chinadrtv.erp.ic.service.ScmCloseShipmentService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-2-20
 * Time: 上午11:31
 * To change this template use File | Settings | File Templates.
 * wms出库取消
 */
public class CloseWMSQuartzJobBean extends QuartzJobBean implements StatefulJob {
    private ScmCloseShipmentService simpleService;

    public void setSimpleService(ScmCloseShipmentService simpleService)
    {
         this.simpleService = simpleService;
    }

    @Override
    protected void executeInternal(JobExecutionContext jobexecutioncontext)
            throws JobExecutionException {
        simpleService.insertScmCloseShipmentService();
    }
}
