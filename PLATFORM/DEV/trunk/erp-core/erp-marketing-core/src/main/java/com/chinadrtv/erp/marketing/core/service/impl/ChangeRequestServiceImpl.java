/**
 * Copyright (c) 2013, Acorn and/or its affiliates. All rights reserved.
 * ACORN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.chinadrtv.erp.marketing.core.service.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.marketing.core.common.AuditTaskStatus;
import com.chinadrtv.erp.marketing.core.common.Constants;
import com.chinadrtv.erp.marketing.core.dao.UserBpmDao;
import com.chinadrtv.erp.marketing.core.dto.ApprovingTaskDto;
import com.chinadrtv.erp.marketing.core.dto.ApprovingTaskVO;
import com.chinadrtv.erp.marketing.core.exception.ErrorCode;
import com.chinadrtv.erp.marketing.core.exception.MarketingException;
import com.chinadrtv.erp.marketing.core.service.ChangeRequestService;
import com.chinadrtv.erp.marketing.core.service.UserBpmTaskService;
import com.chinadrtv.erp.model.marketing.UserBpm;
import com.chinadrtv.erp.model.marketing.UserBpmTask;
import com.chinadrtv.erp.user.service.UserService;
import com.chinadrtv.erp.user.util.SecurityHelper;
import com.chinadrtv.erp.util.DateUtil;

/**
 * 2013-5-6 上午10:17:37
 * @version 1.0.0
 * @author yangfei
 *
 */
@Service("changeRequestService")
public class ChangeRequestServiceImpl implements ChangeRequestService {
	
	private static final Logger logger = LoggerFactory.getLogger(ChangeRequestServiceImpl.class);
	
	@Autowired
	private UserBpmTaskService userBpmTaskService;
	
	@Autowired
	private UserBpmDao userBpmDao;
	
	@Autowired
	private UserService userService;

	public Map<String, Object> queryChangeReqeust(ApprovingTaskDto aTaskDto,
			DataGridModel dataModel) throws MarketingException {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			//部门必须有值，最小查询条件
			if (StringUtils.isBlank(aTaskDto.getDepartment())) {
				logger.error("Department can't be null when query the change request task.");
				throw new MarketingException("部门ID不能为空",
						ErrorCode.DepartmentNull);
			}
			if (DateUtil.checkDateRange(aTaskDto.getStartDate(),
					aTaskDto.getEndDate(), Constants.maxQueryTimeRange)) {
				throw new MarketingException("查询区段过大", ErrorCode.OutOfTimeRange);
			}
			if (StringUtils.isNotBlank(aTaskDto.getEndDate())) {
				String endDate = DateUtil.addDays(aTaskDto.getEndDate(), 1);
				aTaskDto.setEndDate(endDate);
			}
			List<ApprovingTaskVO> vos = userBpmDao.queryChangeRequest(aTaskDto,
					dataModel);
			try {
				for(ApprovingTaskVO avo : vos) {
					if(StringUtils.isNotBlank(avo.getOrCrUsrGrpName())) {
						avo.setOrCrUsrGrpName((userService.getGroupDisplayName(avo.getOrCrUsrGrpName())));
					}
				}
			} catch(Exception e) {
				logger.error("查询组信息出错", e);
			}
			Integer total = userBpmDao.queryCount(aTaskDto);
			result.put("rows", vos);
			result.put("total", total);
		} catch (MarketingException e) {
			throw e;
		} catch (ParseException e) {
			throw new MarketingException(e, "date parse errors!",ErrorCode.System);
		} catch (Exception e) {
			throw new MarketingException(e, "System errors!",ErrorCode.Unknown);
		}
		return result;
	}
	
	public Map<String, Object> queryOrderCancelReqeust(ApprovingTaskDto aTaskDto,
			DataGridModel dataModel) throws MarketingException {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (DateUtil.checkDateRange(aTaskDto.getStartDate(),
					aTaskDto.getEndDate(), Constants.maxQueryTimeRange)) {
				throw new MarketingException("查询区段过大", ErrorCode.OutOfTimeRange);
			}
			if (StringUtils.isNotBlank(aTaskDto.getEndDate())) {
				String endDate = DateUtil.addDays(aTaskDto.getEndDate(), 1);
				aTaskDto.setEndDate(endDate);
			}
			List<ApprovingTaskVO> vos = userBpmDao.queryChangeRequest(aTaskDto,
					dataModel);
			Integer total = userBpmDao.queryCount(aTaskDto);
			result.put("rows", vos);
			result.put("total", total);
		} catch (MarketingException e) {
			throw e;
		} catch (ParseException e) {
			throw new MarketingException(e, "date parse errors!",ErrorCode.System);
		} catch (Exception e) {
			throw new MarketingException(e, "System errors!",ErrorCode.Unknown);
		}
		return result;
	}
	
	public UserBpm queryApprovingTaskById(String batchId) {
		return userBpmDao.queryApprovingTaskById(batchId);
	}
	
	public String createChangeRequest(UserBpm userBpm) {
		if(StringUtils.isBlank(userBpm.getStatus())) {
			userBpm.setStatus(String.valueOf(AuditTaskStatus.UNASSIGNED.getIndex()));
		}
		UserBpm userBpmResult = userBpmDao.save(userBpm);
		return String.valueOf(userBpmResult.getId());
	}
	
	public boolean cancelChangeRequestByBatchID(String batchID, String remark) {
		boolean isCanceled = false;
		userBpmTaskService.cancelTask(batchID, remark);
		userBpmDao.cancelChangeRequestByBatchID(batchID);
		isCanceled = true;
		return isCanceled;
	}
	
	public Map<String, Boolean> isRequestProcessed(List<String> batchIds) {
		Map<String, Boolean> results = new HashMap<String, Boolean>();
		for(String batchId : batchIds) {
			results.put(batchId, isRequestProcessed(batchId));
		}
		return results;
	}
	
	public boolean isRequestProcessed(String batchId) {
		boolean isProcessed = false;
		int number = userBpmTaskService.queryNumOfProcessedTaskByBatchID(batchId);
		if(number > 0) {
			isProcessed = true;
		}
		return isProcessed;
	}
	
	public List<UserBpm> queryUnCompletedOrderChangeRequest(String orderId) {
		return userBpmDao.queryUnCompletedOrderChangeRequest(orderId);
	}
	
	public List<UserBpm> cancelUnCompletedOrderChangeRequest(String orderId) {
		List<UserBpm> userBpms = userBpmDao.queryUnCompletedOrderChangeRequest(orderId);
		for(UserBpm userBpm : userBpms) {
			cancelChangeRequestByBatchID(String.valueOf(userBpm.getId()),"订单已转咨询,流程取消");
		}
		return userBpms;
	}
	
	public String getChangeRequestStatus(String batchID) {
		return userBpmDao.getChangeRequestStatus(batchID);
	}
	
	public void updateChangeRequestStatus(String batchID, String status) {
		userBpmDao.updateChangeRequestStatus(batchID, status);
	}
	
	public void closeChangeRequestStatus(String batchID) {
		List<UserBpmTask> tasks = userBpmTaskService.queryApprovingTaskByBatchID(batchID);
		//关闭实例状态
		for(UserBpmTask ubt : tasks) {
			if(String.valueOf(AuditTaskStatus.REJECTED.getIndex()).equals(ubt.getStatus())) {
				userBpmTaskService.updateStatusDBDirectly(ubt.getBpmInstID(), 
						String.valueOf(AuditTaskStatus.CLOSED.getIndex()), "");
			}
		}
	}
	
	public void syncBatchStatus(String batchId) {
		UserBpm userBpm = this.queryApprovingTaskById(batchId);
		if(!userBpm.getStatus().equals(String.valueOf(AuditTaskStatus.CANCELED.getIndex())) && 
				!userBpm.getStatus().equals(String.valueOf(AuditTaskStatus.CLOSED.getIndex()))) {
			String status = userBpmTaskService.checkChangeRequestStatus(batchId);
			if(!status.equals(userBpm.getStatus())) {
				userBpmDao.updateChangeRequestStatus(batchId,status);
				if(SecurityHelper.getLoginUser() != null && SecurityHelper.getLoginUser().getUserId() != null) {
					userBpmDao.updateChangeRequestUser(batchId, 
							SecurityHelper.getLoginUser().getUserId(), new Date());
				}
			}
		}
	}
	
	public int updateChangeRequestUser(String batchID, String approveUser, Date approveDate) {
		return userBpmDao.updateChangeRequestUser(batchID, approveUser, approveDate);
	}
	
}
