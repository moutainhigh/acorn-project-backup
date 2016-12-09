package com.chinadrtv.scheduler.service;

import com.chinadrtv.common.pagination.Page;
import com.chinadrtv.common.pagination.PaginationBean;
import com.chinadrtv.scheduler.service.model.JobHistory;

/**
 * 
 * @author xieen
 * @version $Id: JobHistoryService.java, v 0.1 2013-8-1 上午8:58:27 xieen Exp $
 */
public interface JobHistoryService {

    public JobHistory getObjectByPK(Integer pkid);

    public int addData(JobHistory jobHistory);

    public PaginationBean<JobHistory> getListByPagination(JobHistory jobHistory, Page page);

}
