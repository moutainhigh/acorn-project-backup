/*
 * @(#)LeadTaskDao.java 1.0 2013-5-10上午11:34:22
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.dao;

import java.util.List;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.OrderChange;
import com.chinadrtv.erp.model.marketing.UserBpmTask;

/**
 * 
 * @author yangfei
 * @version 1.0
 * @since 2013-5-10 上午11:34:22
 * 
 */
public interface UserBpmTaskDao extends GenericDao<UserBpmTask, java.lang.Long> {

	List<UserBpmTask> queryApprovingTaskByBatchID(String batchID);
    List<UserBpmTask> queryTasksByParentInstId(String instId);
	UserBpmTask queryTaskByInstId(String instId);
	
	OrderChange queryOrderChangeByInstId(String instId);
	
	List<UserBpmTask> queryUnProcessedContactBaseChangeTask(String contactId);
	
	List<UserBpmTask> queryUnterminatedOrderChangeTask(String orderId);
	
	int updateUserTaskStatus(String instID, String status, String remark);
	
	List<Object> filterPendingInstances(List<String> instances);

	List<Object> getChangeObjIdByBatchIdOrInstanceId(String batchId, String instanceId);
	
	int queryNumOfProcessedTaskByBatchID(String batchID);
	
	List<UserBpmTask> queryManagerApprovedEmsChangeProcess(String startDate, String endDate, boolean isEms, int start, int end);
	
	int queryManagerApprovedEmsChangeProcessCount(String startDate, String endDate, boolean isEms, int start, int end);
}
