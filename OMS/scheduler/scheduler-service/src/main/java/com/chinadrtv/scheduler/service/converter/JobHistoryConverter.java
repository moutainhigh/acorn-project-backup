package com.chinadrtv.scheduler.service.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.chinadrtv.scheduler.common.dal.model.JobHistoryDO;
import com.chinadrtv.scheduler.service.model.JobHistory;

/**
 * 
 * @author xieen
 * @version $Id: JobHistoryConverter.java, v 0.1 2013-8-7 下午4:49:45 xieen Exp $
 */
public class JobHistoryConverter {
    public static JobHistory jobHistoryDO2JobHistory(JobHistoryDO jobHistoryDO) {
        if (jobHistoryDO == null) {
            return null;
        }
        JobHistory jobHistory = new JobHistory();
        BeanUtils.copyProperties(jobHistoryDO, jobHistory);
        return jobHistory;
    }

    public static JobHistoryDO jobHistory2JobHistoryDO(JobHistory jobHistory) {
        if (jobHistory == null) {
            return null;
        }
        JobHistoryDO jobHistoryDO = new JobHistoryDO();
        BeanUtils.copyProperties(jobHistory, jobHistoryDO);
        return jobHistoryDO;
    }

    public static List<JobHistory> jobHistoryDOList2JobHistoryList(List<JobHistoryDO> list) {
        List<JobHistory> l = new ArrayList<JobHistory>();
        if (list != null) {
            for (JobHistoryDO jobHistoryDO : list) {
                l.add(jobHistoryDO2JobHistory(jobHistoryDO));
            }
        }
        return l;
    }

}
