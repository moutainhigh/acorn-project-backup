package com.chinadrtv.scheduler.service.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.chinadrtv.scheduler.common.dal.model.SchedulerDO;
import com.chinadrtv.scheduler.service.model.Scheduler;

/**
 * 
 * @author xieen
 * @version $Id: SchedulerConverter.java, v 0.1 2013-8-7 下午5:02:28 xieen Exp $
 */
public class SchedulerConverter {
    public static Scheduler schedulerDO2Scheduler(SchedulerDO schedulerDO) {
        if (schedulerDO == null) {
            return null;
        }
        Scheduler scheduler = new Scheduler();
        BeanUtils.copyProperties(schedulerDO, scheduler);
        return scheduler;
    }

    public static SchedulerDO scheduler2SchedulerDO(Scheduler scheduler) {
        if (scheduler == null) {
            return null;
        }
        SchedulerDO schedulerDO = new SchedulerDO();
        BeanUtils.copyProperties(scheduler, schedulerDO);
        return schedulerDO;
    }

    public static List<Scheduler> schedulerListDO2SchedulerList(List<SchedulerDO> list) {
        List<Scheduler> l = new ArrayList<Scheduler>();
        if (list != null) {
            for (SchedulerDO schedulerDO : list) {
                l.add(schedulerDO2Scheduler(schedulerDO));
            }
        }
        return l;
    }

}
