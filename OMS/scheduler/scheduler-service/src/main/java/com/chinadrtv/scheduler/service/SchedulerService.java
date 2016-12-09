package com.chinadrtv.scheduler.service;

import java.util.List;

import com.chinadrtv.common.pagination.Page;
import com.chinadrtv.common.pagination.PaginationBean;
import com.chinadrtv.scheduler.service.model.Scheduler;

/**
 * 
 * @author xieen
 * @version $Id: SchedulerService.java, v 0.1 2013-7-24 下午12:41:03 xieen Exp $
 */
public interface SchedulerService {

    public Scheduler getObjectByPK(Integer pkid);

    public int addData(Scheduler scheduler);

    public int modifyData(Scheduler scheduler);

    public PaginationBean<Scheduler> getListByPagination(Scheduler scheduler, Page page);

    public List<Scheduler> getList(Scheduler scheduler);

}
