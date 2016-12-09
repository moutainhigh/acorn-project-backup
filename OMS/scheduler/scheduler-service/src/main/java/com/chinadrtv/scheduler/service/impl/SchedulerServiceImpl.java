package com.chinadrtv.scheduler.service.impl;

import java.util.List;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.chinadrtv.common.pagination.Page;
import com.chinadrtv.common.pagination.PaginationBean;
import com.chinadrtv.scheduler.common.dal.dao.SchedulerDODao;
import com.chinadrtv.scheduler.common.dal.model.SchedulerDO;
import com.chinadrtv.scheduler.service.SchedulerService;
import com.chinadrtv.scheduler.service.converter.SchedulerConverter;
import com.chinadrtv.scheduler.service.model.Scheduler;

/**
 * 
 * @author xieen
 * @version $Id: SchedulerServiceImpl.java, v 0.1 2013-7-24 下午12:42:20 xieen Exp $
 */
public class SchedulerServiceImpl implements SchedulerService {
    private TransactionTemplate transactionTemplate;
    private SchedulerDODao      schedulerDao;

    public SchedulerDODao getSchedulerDao() {
        return schedulerDao;
    }

    public void setSchedulerDao(SchedulerDODao schedulerDao) {
        this.schedulerDao = schedulerDao;
    }

    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }

    public Scheduler getObjectByPK(Integer pkid) {
        Object object = this.schedulerDao.queryObjectByPK(pkid);
        if (object != null) {
            SchedulerDO schedulerDO = (SchedulerDO) object;
            return SchedulerConverter.schedulerDO2Scheduler(schedulerDO);
        }
        return null;
    }

    public int addData(Scheduler scheduler) {
        final SchedulerDO schedulerDO = SchedulerConverter.scheduler2SchedulerDO(scheduler);
        return transactionTemplate.execute(new TransactionCallback<Integer>() {
            @Override
            public Integer doInTransaction(TransactionStatus status) {
                return getSchedulerDao().insertData(schedulerDO);
            }
        });
    }

    public int modifyData(Scheduler scheduler) {
        final SchedulerDO schedulerDO = SchedulerConverter.scheduler2SchedulerDO(scheduler);
        return transactionTemplate.execute(new TransactionCallback<Integer>() {
            @Override
            public Integer doInTransaction(TransactionStatus status) {
                return getSchedulerDao().updateData(schedulerDO);
            }
        });
    }

    public PaginationBean<Scheduler> getListByPagination(Scheduler scheduler, Page page) {
        SchedulerDO schedulerDO = SchedulerConverter.scheduler2SchedulerDO(scheduler);
        PaginationBean<?> paginationBeanDO = schedulerDao.queryForListByPagination(schedulerDO,
            page);

        PaginationBean<Scheduler> paginationBean = new PaginationBean<Scheduler>();
        paginationBean.setBeginIndex(paginationBeanDO.getBeginIndex());
        paginationBean.setCurrentPage(paginationBeanDO.getCurrentPage());
        paginationBean.setHasNextPage(paginationBeanDO.isHasNextPage());
        paginationBean.setHasPrePage(paginationBeanDO.isHasPrePage());
        paginationBean.setId(paginationBeanDO.getId());
        List<SchedulerDO> SchedulerDOList = (List<SchedulerDO>) paginationBeanDO.getPageList();
        paginationBean.setPageList(SchedulerConverter
            .schedulerListDO2SchedulerList(SchedulerDOList));
        paginationBean.setPageSize(paginationBeanDO.getPageSize());
        paginationBean.setTotalPage(paginationBeanDO.getTotalPage());
        paginationBean.setTotalRecords(paginationBeanDO.getTotalRecords());
        return paginationBean;
    }

    public List<Scheduler> getList(Scheduler scheduler) {
        SchedulerDO schedulerDO = SchedulerConverter.scheduler2SchedulerDO(scheduler);
        List<SchedulerDO> list = this.schedulerDao.queryForList(schedulerDO);
        return SchedulerConverter.schedulerListDO2SchedulerList(list);
    }

}
