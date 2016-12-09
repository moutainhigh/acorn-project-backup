/**
 * Copyright (c) 2013, Acorn and/or its affiliates. All rights reserved.
 * ACORN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.chinadrtv.erp.marketing.core.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.chinadrtv.erp.bpm.service.BpmProcessService;
import com.chinadrtv.erp.marketing.core.common.AuditTaskStatus;
import com.chinadrtv.erp.marketing.core.common.Constants;
import com.chinadrtv.erp.marketing.core.common.UserBpmTaskType;
import com.chinadrtv.erp.marketing.core.dao.UserBpmTaskDao;
import com.chinadrtv.erp.marketing.core.exception.ErrorCode;
import com.chinadrtv.erp.marketing.core.exception.MarketingException;
import com.chinadrtv.erp.marketing.core.service.UserBpmTaskService;
import com.chinadrtv.erp.model.OrderChange;
import com.chinadrtv.erp.model.marketing.UserBpmTask;

/**
 * 2013-5-6 上午10:17:37
 * @version 1.0.0
 * @author yangfei
 *
 */
@Service("userBpmTaskService")
public class UserBpmTaskServiceImpl implements UserBpmTaskService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserBpmTaskServiceImpl.class);
	
	@Autowired
	private BpmProcessService bpmProcessService;
	
	@Autowired
	private UserBpmTaskDao userBpmTaskDao;
	
	public List<UserBpmTask> queryApprovingTaskByBatchID(String batchID) {
		return userBpmTaskDao.queryApprovingTaskByBatchID(batchID);
	}

	public List<UserBpmTask> queryUnProcessedContactBaseChangeTask(String contactId) {
		return userBpmTaskDao.queryUnProcessedContactBaseChangeTask(contactId);
	}
	
	public List<UserBpmTask> queryUnterminatedOrderChangeTask(String orderId) {
		return userBpmTaskDao.queryUnterminatedOrderChangeTask(orderId);
	}

	public String createApprovingTask(UserBpmTask userBpmTask) {
		String instID = bpmProcessService.startProcessInstance(Constants.CHNAGE_PROCESS_DEF_NAME);
		List<String> tasks = bpmProcessService.queryTaskByAssigneeAndInstance("", instID);
		for(String tid : tasks) {
			userBpmTask.setBpmInstID(instID);
			userBpmTask.setBpmTaskID(tid);
			userBpmTask.setStatus(String.valueOf(AuditTaskStatus.UNASSIGNED.getIndex()));
			userBpmTaskDao.save(userBpmTask);
		}
		return instID;
	}
	
	//@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public String checkChangeRequestStatus(String batchID) {
		String status = String.valueOf(AuditTaskStatus.APPROEED.getIndex());
		List<UserBpmTask> tasks = userBpmTaskDao.queryApprovingTaskByBatchID(batchID);
		for(UserBpmTask ubt : tasks) {
			if(String.valueOf(AuditTaskStatus.REJECTED.getIndex()).equals(ubt.getStatus())) {
				status = String.valueOf(AuditTaskStatus.REJECTED.getIndex());
				return status;
			}
		}
		for(UserBpmTask ubt : tasks) {
			if(String.valueOf(AuditTaskStatus.UNASSIGNED.getIndex()).equals(ubt.getStatus())) {
				status = String.valueOf(AuditTaskStatus.UNASSIGNED.getIndex());
				return status;
			}
		}
		for(UserBpmTask ubt : tasks) {
			if(String.valueOf(AuditTaskStatus.ASSITANTWAITING.getIndex()).equals(ubt.getStatus())) {
				status = String.valueOf(AuditTaskStatus.ASSITANTWAITING.getIndex());
				return status;
			}
		}
		for(UserBpmTask ubt : tasks) {
			if(String.valueOf(AuditTaskStatus.ASSITANTPROCESSED.getIndex()).equals(ubt.getStatus())) {
				status = String.valueOf(AuditTaskStatus.ASSITANTPROCESSED.getIndex());
				return status;
			}
		}
		for(UserBpmTask ubt : tasks) {
			if(String.valueOf(AuditTaskStatus.CLOSED.getIndex()).equals(ubt.getStatus())) {
				status = String.valueOf(AuditTaskStatus.CLOSED.getIndex());
				return status;
			}
		}
		return status;
	}
	
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public boolean cancelTask(String batchID, String remark) {
		boolean isCanceled = false;
		List<UserBpmTask> tasks = userBpmTaskDao.queryApprovingTaskByBatchID(batchID);
		for(UserBpmTask ubt : tasks) {
			try {
				bpmProcessService.cancelInstance(ubt.getBpmInstID(), remark);
			} catch (Exception e) {
				logger.warn("Failed to cancel activiti instance"+ubt.getBpmInstID());
			}
			userBpmTaskDao.updateUserTaskStatus(ubt.getBpmInstID(), 
					String.valueOf(AuditTaskStatus.CANCELED.getIndex()), remark);
		}
		isCanceled = true;
		return isCanceled;
	}
	
	/**
	 * TODOX outbound 1. 不能同一个人判断  2. 管理组统一批 department
	 *      inbound  
	 * @throws MarketingException 
	 */
	public boolean approveChangeRequest(String appliedUser, String approvedUser, String instID, 
			String approveremark) throws MarketingException {
		if(appliedUser != null && approvedUser != null && approvedUser.equals(appliedUser)) {
			logger.error("Can't approve the change request submitted by someone self!");
			throw new MarketingException("不能审批自己的修改请求",
					ErrorCode.NotProperApprover);
		}
		boolean isApproved = false;
		updateStatus(appliedUser, instID, approveremark, String.valueOf(AuditTaskStatus.APPROEED.getIndex()), true);
		isApproved=true;
		return isApproved;
	}
	
	public boolean rejectChangeRequest(String appliedUser, String approvedUser, 
			String instID, String approveremark, boolean isAdded) throws MarketingException {
		if(appliedUser != null && approvedUser != null && approvedUser.equals(appliedUser)) {
			logger.error("Can't approve the change request submitted by someone self!");
			throw new MarketingException("不能审批自己的修改请求", ErrorCode.NotProperApprover);
		}
		boolean isRejected = false;
		String status = String.valueOf(AuditTaskStatus.REJECTED.getIndex());
		if(isAdded) {
			status = String.valueOf(AuditTaskStatus.CLOSED.getIndex());
		}
		updateStatus(appliedUser, instID, approveremark, status, false);
		isRejected = true;
		return isRejected;
	}
	
	public void updateStatus(String userId, String instID, String approveremark, String status, boolean changeApproved) {
		Map<String,Object> v = new HashMap<String, Object>();
		UserBpmTask userBpmTask = userBpmTaskDao.queryTaskByInstId(instID);
		boolean isChange2Ems = false;
		if(String.valueOf(UserBpmTaskType.ORDER_EMS_CHANGE.getIndex()).equals(userBpmTask.getBusiType())) {
			OrderChange oChange = userBpmTaskDao.queryOrderChangeByInstId(instID);
			if("Y".equals(oChange.getIsreqems())) {
				isChange2Ems = true;
			}
		}
		changeApproved = changeApproved && isChange2Ems;
		v.put("changeApproved", changeApproved);
//		v.put("busiType", userBpmTask.getBusiType());
		if(String.valueOf(AuditTaskStatus.UNASSIGNED.getIndex()).equals(userBpmTask.getStatus()) && 
				String.valueOf(AuditTaskStatus.APPROEED.getIndex()).equals(status) && isChange2Ems) {
			status = String.valueOf(AuditTaskStatus.ASSITANTWAITING.getIndex());
		} else if(String.valueOf(AuditTaskStatus.ASSITANTWAITING.getIndex()).equals(userBpmTask.getStatus()) && 
				String.valueOf(AuditTaskStatus.APPROEED.getIndex()).equals(status) && isChange2Ems) {
			status = String.valueOf(AuditTaskStatus.ASSITANTPROCESSED.getIndex());
		}
		bpmProcessService.completeTaskByInstID(instID, v, userId);
		userBpmTaskDao.updateUserTaskStatus(instID, status, approveremark);
	}
	
	public void updateStatusDBDirectly(String instID, String status, String approveremark) {
		userBpmTaskDao.updateUserTaskStatus(instID, status, approveremark);
	}
	
	public boolean acceptRequestRejection(String instID) {
		boolean isSuc = false;
		bpmProcessService.completeTaskByInstID(instID, null, null);
		return isSuc;
	}
	
	public List<Object> filterPendingInstances(List<String> instances) {
		List<Object> objs = userBpmTaskDao.filterPendingInstances(instances);
		return objs;
	}
	
	public List<Object> getChangeObjIdByBatchIdOrInstanceId(String batchId, String instanceId) throws MarketingException {
		if(StringUtils.isBlank(batchId) && StringUtils.isBlank(instanceId)) {
			throw new MarketingException("请求条件不满足", ErrorCode.CRITERIAWRONG);
		}
		List<Object> objs = userBpmTaskDao.getChangeObjIdByBatchIdOrInstanceId(batchId, instanceId);
		return objs;
	}
	
	public int queryNumOfProcessedTaskByBatchID(String batchID) {
		return userBpmTaskDao.queryNumOfProcessedTaskByBatchID(batchID);
	}
	
	public List<UserBpmTask> queryManagerApprovedEmsChangeProcess(String startDate, String endDate, boolean isEms, int start, int end) {
		return userBpmTaskDao.queryManagerApprovedEmsChangeProcess(startDate, endDate, isEms, start, end);
	}
	
	public int queryManagerApprovedEmsChangeProcessCount(String startDate, String endDate, boolean isEms, int start, int end) {
		return userBpmTaskDao.queryManagerApprovedEmsChangeProcessCount(startDate, endDate, isEms, start, end);
	}
	
	public UserBpmTask queryTaskByInstId(String instId) {
		return userBpmTaskDao.queryTaskByInstId(instId);
	}

    public List<UserBpmTask> queryTasksByParentInstId(String instId){
        return userBpmTaskDao.queryTasksByParentInstId(instId);
    }
}
