package com.chinadrtv.scheduler.service.dboperate;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinadrtv.common.log.LOG_TYPE;
import com.chinadrtv.common.pagination.Page;
import com.chinadrtv.common.pagination.PaginationBean;
import com.chinadrtv.scheduler.service.JobHistoryService;
import com.chinadrtv.scheduler.service.SchedulerService;
import com.chinadrtv.scheduler.service.model.JobHistory;
import com.chinadrtv.scheduler.service.model.Scheduler;
import com.chinadrtv.util.XmlUtil;

/**
 * 
 * @author xieen
 * @version $Id: DatabaseOperateService.java, v 0.1 2013-7-31 上午11:27:58 xieen Exp $
 */
public class DatabaseOperateService {
    //private Logger            logger = LoggerFactory.getLogger(LOG_TYPE.PAFF_SERVICE.val);

    private SchedulerService  schedulerService;

    private JobHistoryService jobHistoryService;

    public void setSchedulerService(SchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

    public void setJobHistoryService(JobHistoryService jobHistoryService) {
        this.jobHistoryService = jobHistoryService;
    }

    public String databaseOperate(HashMap<String, Object> param) throws Exception {
        String operateType = (String) param.get("operate");
        if ("add".equals(operateType)) {
            return this.addScheduler(param);
        } else if ("modify".equals(operateType)) {
            return this.modifyScheduler(param);
        } else if ("list".equals(operateType)) {
            return this.listScheduler(param);
        } else if ("history".equals(operateType)) {
            return this.listJobHistory(param);
        }
        return null;
    }

    private String addScheduler(HashMap<String, Object> param) throws Exception {
        Scheduler scheduler = new Scheduler();
        scheduler.setJobSystem((String) param.get("jobSystem"));
        scheduler.setJobTopic((String) param.get("jobTopic"));
        scheduler.setJobName((String) param.get("jobName"));
        scheduler.setJobCronExpression((String) param.get("jobCronExpression"));
        scheduler.setJobDescription((String) param.get("jobDescription"));
        scheduler.setJobStatus((Integer) param.get("jobStatus"));
        //scheduler.setJobStyle((String)param.get("jobStyle"));
        scheduler.setJobStyle("0");
        this.schedulerService.addData(scheduler);
        return null;
    }

    private String modifyScheduler(HashMap<String, Object> param) throws Exception {
        int id = (Integer) param.get("id");
        int jobStatus = (Integer) param.get("jobStatus");

        Scheduler scheduler = new Scheduler();
        scheduler.setId(id);
        scheduler.setJobStatus(jobStatus);

        this.schedulerService.modifyData(scheduler);
        return null;
    }

    private String listScheduler(HashMap<String, Object> param) throws Exception {
        Page page = (Page) param.get("page");
        Scheduler scheduler = (Scheduler) param.get("scheduler");
        PaginationBean<?> pageinatine = this.schedulerService.getListByPagination(scheduler, page);

        return XmlUtil.toXml(pageinatine);
    }

    private String listJobHistory(HashMap<String, Object> param) throws Exception {
        Page page = (Page) param.get("page");
        JobHistory jobHistory = (JobHistory) param.get("jobHistory");
        PaginationBean<?> pageinatine = this.jobHistoryService
            .getListByPagination(jobHistory, page);

        return XmlUtil.toXml(pageinatine);
    }

}
