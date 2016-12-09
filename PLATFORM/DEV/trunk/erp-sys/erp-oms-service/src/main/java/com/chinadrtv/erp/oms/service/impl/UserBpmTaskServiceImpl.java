package com.chinadrtv.erp.oms.service.impl;

import com.chinadrtv.erp.marketing.core.dao.UserBpmTaskDao;
import com.chinadrtv.erp.marketing.core.exception.MarketingException;
import com.chinadrtv.erp.marketing.core.service.UserBpmTaskService;
import com.chinadrtv.erp.model.marketing.UserBpmTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-11-14
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class UserBpmTaskServiceImpl implements UserBpmTaskService {
    public List<UserBpmTask> queryApprovingTaskByBatchID(String batchID) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<UserBpmTask> queryUnProcessedContactBaseChangeTask(String contactId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Autowired
    private UserBpmTaskDao userBpmTaskDao;

    public List<UserBpmTask> queryUnterminatedOrderChangeTask(String orderId) {
        return userBpmTaskDao.queryUnterminatedOrderChangeTask(orderId);
    }

    public String createApprovingTask(UserBpmTask userBpmTask) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean cancelTask(String batchID, String remark) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String checkChangeRequestStatus(String batchID) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean approveChangeRequest(String appliedUser, String approvedUser, String taskID, String remark) throws MarketingException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean rejectChangeRequest(String appliedUser, String approvedUser, String taskID, String remark, boolean isAdded) throws MarketingException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateStatus(String userId, String instID, String approveremark, String status, boolean changeApproved) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void updateStatusDBDirectly(String instID, String status, String approveremark) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean acceptRequestRejection(String instID) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<Object> filterPendingInstances(List<String> instances) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<Object> getChangeObjIdByBatchIdOrInstanceId(String batchId, String instanceId) throws MarketingException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int queryNumOfProcessedTaskByBatchID(String batchID) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<UserBpmTask> queryTasksByParentInstId(String instId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public UserBpmTask queryTaskByInstId(String instId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<UserBpmTask> queryManagerApprovedEmsChangeProcess(String startDate, String endDate, boolean isEms, int start, int end) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int queryManagerApprovedEmsChangeProcessCount(String startDate, String endDate, boolean isEms, int start, int end) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
