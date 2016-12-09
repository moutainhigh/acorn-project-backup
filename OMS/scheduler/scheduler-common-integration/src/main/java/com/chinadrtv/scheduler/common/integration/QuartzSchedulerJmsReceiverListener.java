/**
 * 
 *	平安付
 * Copyright (c) 2013-2013 PingAnFu,Inc.All Rights Reserved.
 */
package com.chinadrtv.scheduler.common.integration;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinadrtv.common.log.LOG_TYPE;
import com.chinadrtv.runtime.jms.receive.JmsListener;
import com.chinadrtv.scheduler.service.quartz.QuartzSchedulerService;


public class QuartzSchedulerJmsReceiverListener extends JmsListener<HashMap<String, Object>> {

    private Logger                 logger = LoggerFactory.getLogger("LOG_TYPE.PAFF_SERVICE.val");

    private QuartzSchedulerService quartzSchedulerService;

    public void setQuartzSchedulerService(QuartzSchedulerService quartzSchedulerService) {
        this.quartzSchedulerService = quartzSchedulerService;
    }


    @Override
    public void messageHandler(HashMap<String, Object> msg) {
        Integer id = (Integer) msg.get("id");
        Integer status = (Integer) msg.get("status");

        this.quartzSchedulerService.operateMqSchedulerMassege(id, status);
    }

}
