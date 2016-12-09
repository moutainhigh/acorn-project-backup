package com.chinadrtv.scheduler.service.impl;

import java.util.List;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.chinadrtv.common.pagination.Page;
import com.chinadrtv.common.pagination.PaginationBean;
import com.chinadrtv.scheduler.common.dal.dao.JobHistoryDODao;
import com.chinadrtv.scheduler.common.dal.model.JobHistoryDO;
import com.chinadrtv.scheduler.service.JobHistoryService;
import com.chinadrtv.scheduler.service.converter.JobHistoryConverter;
import com.chinadrtv.scheduler.service.model.JobHistory;

/**
 * 
 * @author xieen
 * @version $Id: JobHistoryServiceImpl.java, v 0.1 2013-8-1 上午8:59:29 xieen Exp $
 */
public class JobHistoryServiceImpl implements JobHistoryService {
    private TransactionTemplate transactionTemplate;
    private JobHistoryDODao     jobHistoryDao;

    public JobHistoryDODao getJobHistoryDao() {
        return jobHistoryDao;
    }

    public void setJobHistoryDao(JobHistoryDODao jobHistoryDao) {
        this.jobHistoryDao = jobHistoryDao;
    }

    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }

    public JobHistory getObjectByPK(Integer pkid) {
        Object object = this.jobHistoryDao.queryObjectByPK(pkid);
        if (object != null) {
            JobHistoryDO jobHistoryDO = (JobHistoryDO) object;
            return JobHistoryConverter.jobHistoryDO2JobHistory(jobHistoryDO);
        }
        return null;
    }

    public int addData(JobHistory jobHistory) {
        final JobHistoryDO jobHistoryDO = JobHistoryConverter.jobHistory2JobHistoryDO(jobHistory);
        return transactionTemplate.execute(new TransactionCallback<Integer>() {
            @Override
            public Integer doInTransaction(TransactionStatus status) {
                return getJobHistoryDao().insertData(jobHistoryDO);
            }
        });
    }

    public PaginationBean<JobHistory> getListByPagination(JobHistory jobHistory, Page page) {
        JobHistoryDO jobHistoryDO = JobHistoryConverter.jobHistory2JobHistoryDO(jobHistory);
        PaginationBean<?> paginationBeanDO = jobHistoryDao.queryForListByPagination(jobHistoryDO,
            page);

        PaginationBean<JobHistory> paginationBean = new PaginationBean<JobHistory>();
        paginationBean.setBeginIndex(paginationBeanDO.getBeginIndex());
        paginationBean.setCurrentPage(paginationBeanDO.getCurrentPage());
        paginationBean.setHasNextPage(paginationBeanDO.isHasNextPage());
        paginationBean.setHasPrePage(paginationBeanDO.isHasPrePage());
        paginationBean.setId(paginationBeanDO.getId());
        List<JobHistoryDO> jobHistoryDOList = (List<JobHistoryDO>) paginationBeanDO.getPageList();
        paginationBean.setPageList(JobHistoryConverter
            .jobHistoryDOList2JobHistoryList(jobHistoryDOList));
        paginationBean.setPageSize(paginationBeanDO.getPageSize());
        paginationBean.setTotalPage(paginationBeanDO.getTotalPage());
        paginationBean.setTotalRecords(paginationBeanDO.getTotalRecords());
        return paginationBean;
    }
}
