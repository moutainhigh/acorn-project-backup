package com.chinadrtv.erp.ic.util;

import com.chinadrtv.erp.ic.service.ShipmentHeaderHisService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-2-19
 * Time: 上午11:37
 * To change this template use File | Settings | File Templates.
 */
public class ScmShipmentQuartzJobBean extends QuartzJobBean implements StatefulJob {
    private static final Logger LOG = LoggerFactory.getLogger(ScmShipmentQuartzJobBean.class);

    private ShipmentHeaderHisService simpleService;

    public void setSimpleService(ShipmentHeaderHisService simpleService) {
        this.simpleService = simpleService;
    }

    @Override
    protected void executeInternal(JobExecutionContext context)
            throws JobExecutionException {

        try {
            //simpleService.createShipmentHeaderHis();
            simpleService.processShipments("SYS");
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
        }
    }
}
