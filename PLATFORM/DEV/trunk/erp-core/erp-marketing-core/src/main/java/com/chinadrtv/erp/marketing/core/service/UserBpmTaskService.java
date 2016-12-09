/**
 * Copyright (c) 2013, Acorn and/or its affiliates. All rights reserved.
 * ACORN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.chinadrtv.erp.marketing.core.service;

import java.util.List;

import com.chinadrtv.erp.marketing.core.exception.MarketingException;
import com.chinadrtv.erp.model.marketing.UserBpmTask;

/**
 * 2013-5-13 下午5:49:01
 * @version 1.0.0
 * @author yangfei
 * 封装BPM服务，提供多维度查询和审批任务内容服务
 */
public interface UserBpmTaskService {
	List<UserBpmTask> queryApprovingTaskByBatchID(String batchID);
	
	/**
	 * 查看因姓名被修改正在被审批中的客户
	 * queryUnProcessedContactBaseChangeTask
	 * @param contactId
	 * @return 
	 * List<ApprovingTaskVO>
	 * @exception 
	 * @since  1.0.0
	 */
	List<UserBpmTask> queryUnProcessedContactBaseChangeTask(String contactId);
	
	/**
	 * 查看正在审批中的订单
	 * queryUnterminatedOrderChangeTask
	 * @param orderId
	 * @return 
	 * List<UserBpmTask>
	 * @exception 
	 * @since  1.0.0
	 */
	List<UserBpmTask> queryUnterminatedOrderChangeTask(String orderId);
	
	/**
	 * 创建审批任务
	 * @param userBpmTask 任务内容，其中department为必须字段
	 * @return instID, 用于任务提交者同步instance状态
	 * @exception 
	 * @since  1.0.0
	 */
	String createApprovingTask(UserBpmTask userBpmTask);
	
	/**
	 * 取消审批任务
	 * @param batchID 修改批次号
	 * @return remark, 备注
	 * @exception 
	 * @since  1.0.0
	 */
	boolean cancelTask(String batchID, String remark);
	
	String checkChangeRequestStatus(String batchID);
	
	/**
	 * 主管或者订单创建座席批准修改请求
	 * @param taskID BPM任务编号
	 * @return 批准是否成功
	 * boolean
	 * @throws MarketingException 
	 * @exception 
	 * @since  1.0.0
	 */
	boolean approveChangeRequest(String appliedUser, String approvedUser, String taskID, String remark) throws MarketingException;
	
	/**
	 * 主管或者订单创建座席拒绝修改请求
	 * @param taskID BPM实例编号
	 * @return 拒绝是否成功
	 * boolean
	 * @exception 
	 * @since  1.0.0
	 */
	boolean rejectChangeRequest(String appliedUser, String approvedUser, String taskID, String remark, boolean isAdded) throws MarketingException;
	
	/**
	 * 更新任务
	 * updateStatus
	 * @param userId
	 * @param instID
	 * @param approveremark
	 * @param status
	 * @param changeApproved 
	 * void
	 * @exception 
	 * @since  1.0.0
	 */
	void updateStatus(String userId, String instID, String approveremark, String status, boolean changeApproved);
	
	/**
	 * 直接更新数据库状态
	 * updateStatusDBDirectly
	 * @param instID
	 * @param status
	 * @param approveremark 
	 * void
	 * @exception 
	 * @since  1.0.0
	 */
	void updateStatusDBDirectly(String instID, String status, String approveremark);
	
	/**
	 * 客户/订单修改座席接受拒绝请求，结束BPM流程
	 * @param instID BPM任务编号
	 * @return 
	 * boolean
	 * @exception 
	 * @since  1.0.0
	 */
	boolean acceptRequestRejection(String instID);
	
	/**
	 * 查询输入的流程实例中，是否有处在审批中的流程
	 * isPendingStatusExists
	 * @param instances
	 * @return 
	 * List<String>
	 * @exception 
	 * @since  1.0.0
	 */
	List<Object> filterPendingInstances(List<String> instances);
	
	/**
	 * 根据batchId或者实例Id查询change obj id
	 * getChangeObjIdByBatchIdOrInstanceId
	 * @param batchId
	 * @param instanceId
	 * @return
	 * @throws MarketingException 
	 * List<Object>
	 * @exception 
	 * @since  1.0.0
	 */
	List<Object> getChangeObjIdByBatchIdOrInstanceId(String batchId, String instanceId) throws MarketingException;
	
	/**
	 * 查询未处理状态的实例数量
	 * queryNumOfProcessedTaskByBatchID
	 * @param batchID
	 * @return 
	 * int
	 * @exception 
	 * @since  1.0.0
	 */
	int queryNumOfProcessedTaskByBatchID(String batchID);

    List<UserBpmTask> queryTasksByParentInstId(String instId);
	UserBpmTask queryTaskByInstId(String instId);
	
	/**
	 * 查询主管已批的ems修改流程
	 * queryManagerApprovedEmsChangeProcess
	 * @return 
	 * List<UserBpmTask>
	 * @exception 
	 * @since  1.0.0
	 */
	List<UserBpmTask> queryManagerApprovedEmsChangeProcess(String startDate, String endDate, boolean isEms, int start, int end);
	
	/**
	 * 查询数目
	 * queryManagerApprovedEmsChangeProcessCount
	 * @param startDate
	 * @param endDate
	 * @param isEms
	 * @param start
	 * @param end
	 * @return 
	 * int
	 * @exception 
	 * @since  1.0.0
	 */
	int queryManagerApprovedEmsChangeProcessCount(String startDate, String endDate, boolean isEms, int start, int end);
}
