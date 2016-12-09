/**
 * Copyright (c) 2013, Acorn and/or its affiliates. All rights reserved.
 * ACORN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.chinadrtv.erp.marketing.core.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.marketing.core.dto.ApprovingTaskDto;
import com.chinadrtv.erp.marketing.core.exception.MarketingException;
import com.chinadrtv.erp.model.marketing.UserBpm;

/**
 * 2013-5-13 下午5:49:01
 * @version 1.0.0
 * @author yangfei
 * 封装BPM服务，提供多维度查询和审批任务内容服务
 */
public interface ChangeRequestService {
	/**
	 * 查询修改审批任务
	 * @param aTaskDto 修改审批任务查询条件，其中department为必须条件
	 * @param dataModel
	 * @return 
	 * Map<String,Object>
	 * @throws MarketingException 
	 * @exception 
	 * @since  1.0.0
	 */
	Map<String, Object> queryChangeReqeust(ApprovingTaskDto aTaskDto, DataGridModel dataModel) throws MarketingException;
	
	/**
	 * 查询取消订单审批任务，面向座席
	 * @param aTaskDto 其中订单创建人为座席
	 * @param dataModel
	 * @return 
	 * Map<String,Object>
	 * @throws MarketingException 
	 * @exception 
	 * @since  1.0.0
	 */
	Map<String, Object> queryOrderCancelReqeust(ApprovingTaskDto aTaskDto,
			DataGridModel dataModel) throws MarketingException;
	
	/**
	 * queryApprovingTaskByInstId
	 * @param batchId
	 * @return 
	 * UserBpm
	 * @exception 
	 * @since  1.0.0
	 */
	UserBpm queryApprovingTaskById(String batchId);
	
	/**
	 * 创建修改审批
	 * @param userBpm 审批内容
	 * @return tchID, 用于修改提交者关联修改记录
	 * @exception 
	 * @since  1.0.0
	 */
	String createChangeRequest(UserBpm userBpm);
	
	/**
	 * 客户/订单修改座席取消修改请求
	 * @param batchID 修改批次
	 * @param remark 备注
	 * @return 是否取消成功
	 * boolean
	 * @exception 
	 * @since  1.0.0
	 */
	boolean cancelChangeRequestByBatchID(String batchId, String remark);
	
	void updateChangeRequestStatus(String batchId, String status);
	
	void closeChangeRequestStatus(String batchId);
	
	void syncBatchStatus(String batchId);
	
	int updateChangeRequestUser(String batchID, String approveUser, Date approveDate);
	
	String getChangeRequestStatus(String batchId);
	
	List<UserBpm> queryUnCompletedOrderChangeRequest(String orderId);
	
	List<UserBpm> cancelUnCompletedOrderChangeRequest(String orderId);
	
	/**
	 * 批量查询申请是否已经被处理
	 * isRequestProcessed
	 * @param batchIds
	 * @return 
	 * Map<String,Boolean>
	 * @exception 
	 * @since  1.0.0
	 */
	Map<String, Boolean> isRequestProcessed(List<String> batchIds);
	
	/**
	 * 查询申请是否已经被处理
	 * isRequestProcessed
	 * @param batchId
	 * @return 
	 * boolean
	 * @exception 
	 * @since  1.0.0
	 */
	boolean isRequestProcessed(String batchId);

}
