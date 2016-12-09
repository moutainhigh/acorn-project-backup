/**
 * Copyright (c) 2013, Acorn and/or its affiliates. All rights reserved.
 * ACORN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.chinadrtv.erp.marketing.core.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.marketing.core.dto.CampaignTaskDto;
import com.chinadrtv.erp.marketing.core.dto.CampaignTaskVO;
import com.chinadrtv.erp.marketing.core.exception.MarketingException;
import com.chinadrtv.erp.model.marketing.LeadTask;
import com.chinadrtv.erp.model.marketing.task.TaskFormItem;

/**
 * 2013-5-3 下午5:49:01
 * 
 * @version 1.0.0
 * @author yangfei 封装BPM服务，提供多维度查询和任务内容服务
 */
public interface CampaignBPMTaskService {
	
	/**
	 * 根据instId查询campaign task
	 * queryMarketingTask
	 * @param instId 任务实例Id
	 * @return 
	 * CampaignTaskVO
	 * @exception 
	 * @since  1.0.0
	 */
	CampaignTaskVO queryMarketingTask(String instId);
	/**
	 * 查询营销任务
	 * @param mTaskDto 业务查询条件,其中userID为必输条件
	 * @param dataModel 分页信息
	 * @return Map<String,Object>
	 * @throws MarketingException 
	 * @exception 
	 * @since  1.0.0
	 */
	Map<String, Object> queryMarketingTask(CampaignTaskDto mTaskDto,
			DataGridModel dataModel) throws MarketingException;
	
	/**
	 * 查询客户分配历史
	 * queryContactTaskHistory
	 * @param contactId
	 * @return 
	 * List<Map<String,Object>>
	 * @exception 
	 * @since  1.0.0
	 */
	List<Map<String, Object>> queryContactTaskHistory(String contactId);
	
	/**
	 * 查询未结束任务
	 * queryUnStartedCampaignTask
	 * @param mTaskDto
	 * @param dataModel
	 * @return
	 * @throws MarketingException 
	 * Map<String,Object>
	 * @exception 
	 * @since  1.0.0
	 */
	Map<String, Object> queryUnStartedCampaignTask(CampaignTaskDto mTaskDto, DataGridModel dataModel) throws MarketingException;

	/**
	 * 查询营销任务数量
	 * @param mTaskDto 业务查询条件,其中userID为必输条件
	 * @return Integer
	 * @exception 
	 * @since  1.0.0
	 */
	Integer queryCount(CampaignTaskDto mTaskDto);
	
	/**
	 * 实现同queryCount,基于缓存考虑,拆分api
	 * queryAllocatedCount
	 * @param mTaskDto
	 * @return 
	 * Integer
	 * @exception 
	 * @since  1.0.0
	 */
	Integer queryAllocatedCount(CampaignTaskDto mTaskDto);
	
	/**
	 * 实现同queryCount,基于缓存考虑,拆分api
	 * queryUndialCount
	 * @param mTaskDto
	 * @return 
	 * Integer
	 * @exception 
	 * @since  1.0.0
	 */
	Integer queryUndialCount(CampaignTaskDto mTaskDto);
	/**
	 * 
	 * createMarketingTask
	 * @param leadTask
	 * @return
	 * @throws MarketingException 
	 * String
	 * @exception 
	 * @since  1.0.0
	 */
	String createMarketingTask(LeadTask leadTask) throws MarketingException;
	/**
	 * 创建推荐任务或者预约任务，任务时效为endDate
	 * @param leadID 线索编号
	 * @param campaignID 营销计划编号
	 * @param contactID 客户编号
	 * @param isPotential 是否潜客
	 * @param pdCustomerId 取数ID
	 * @param usrID 座席编号
	 * @param endDate 任务结束时间,与线索结束时间保持一致
	 * @param remark 预约批注
	 * @return 创建的实例ID
	 * boolean
	 * @throws MarketingException 
	 * @exception 
	 * @since  1.0.0
	 */
	String createMarketingTask(String leadID, String campaignID, String contactID, long isPotential, String pdCustomerId,
			String usrID, Date endDate, Date appointDate,long sourceType,long sourceType2, long sourceType3, 
			String remark) throws MarketingException;
	
	/**
	 * 数据分配任务,但没有有效关联campaign receiver数据，需要查出清理
	 * queryInvalidPushTask
	 * @return 
	 * List<LeadTask>
	 * @exception 
	 * @since  1.0.0
	 */
	List<String> clearInvalidPushTask();
	
	/**
	 * 系统关闭营销任务
	 * closeTaskAndUpdateStatus
	 * @param remark
	 * @param processInsId
	 * @param taskID
	 * @param userID 
	 * void
	 * @exception 
	 * @since  1.0.0
	 */
	void closeTaskAndUpdateStatus(String remark, String processInsId, String taskID, String userID);
	
	/**
	 * 结束任务并更新任务状态
	 * @param remark 备注
	 * @param processInsId BPM流程实例编号
	 * @param taskID BPM任务编号
	 * @param userID 座席编号
	 * @return boolean 是否取消成功
	 * @exception
	 * @since 1.0.0
	 */
	boolean cancelTask(String remark, String processInsId, String taskID, String userID);
	
	/**
	 * 更新任务状态
	 * @param instID 实例编号
	 * @param status 待更新任务状态
	 * @param taskID 任务编号
	 * @return 是否更新成功
	 * boolean
	 * @exception 
	 * @since  1.0.0
	 */
	boolean updateTaskStatus(String instID, String status, String remark, String taskID);

	/**
	 * 根据线索编号，更改任务所有者
	 * @param leadID 线索编号
	 * @param newOwner 新任务所有座席编号
	 * @return 更新任务数量
	 * int
	 * @exception 
	 * @since  1.0.0
	 */
	int changeTaskOwnerByLead(long leadID, String newOwner);
	
	List<Object> queryInstsByLead(long leadID);
	
	LeadTask queryInst(String instID);
	
	/**
	 * 根据线索,更新task状态
	 * updateTaskStatusByLead
	 * @param leadID
	 * @param status
	 * @return 
	 * int
	 * @exception 
	 * @since  1.0.0
	 */
	int updateTaskStatusByLead(long leadID, String status);

	String queryBPMProcessID(String leadID);
	
	/**
	 * 结束任务并更新任务状态
	 * @param remark 备注
	 * @param processInsId BPM流程实例编号
	 * @param taskID BPM任务编号
	 * @param userID 座席编号
	 * @exception
	 * @since 1.0.0
	 */
	void completeTaskAndUpdateStatus(String remark, String processInsId,
			String taskID, String userID);

	/**
	 * 获取营销任务表单
	 * @param campaignID 营销任务编号
	 * @return 营销任务表单内容
	 * List<TaskFormItem>
	 * @exception 
	 * @since  1.0.0
	 */
	List<TaskFormItem> getTaskForm(String campaignID);
	
	/**
	 * 提交营销任务表单
	 * @param instID BPM流程实例编号
	 * @param leadTaskId 营销任务实例编号，关联lead_task表
	 * @param taskItems　营销任务表单内容
	 * @return 
	 * boolean
	 * @exception 
	 * @since  1.0.0
	 */
	boolean submitTaskForm(String instID, String leadTaskId, List<TaskFormItem> taskItems);
	
	/**
	 * 更新线索的潜客为正式客户
	 * updatePotential2Normal
	 * @param contactId
	 * @param potentialContactId
	 * @return 
	 * int
	 * @exception 
	 * @since  1.0.0
	 */
	int updatePotential2Normal(String contactId, String potentialContactId);
	
	/**
	 * 查询待处理任务
	 * queryUnStartedCampaignTask
	 * @return 
	 * Integer
	 * @exception 
	 * @since  1.0.0
	 */
	Integer queryUnStartedCampaignTaskCount(CampaignTaskDto mTaskDto);
	
	/**
	 * 查询任务是否结束
	 * isTaskFinished
	 * @param instId
	 * @return 
	 * boolean
	 * @exception 
	 * @since  1.0.0
	 */
	boolean isTaskFinished(String instId);
	
	/**
	 * 更新预约时间
	 * updateInstAppointDate
	 * @param instID
	 * @param appointDate
	 * @return 
	 * int
	 * @exception 
	 * @since  1.0.0
	 */
	int updateInstAppointDate(String instID, Date appointDate);
	
	LeadTask get(Long id);
	
	void saveOrUpdate(LeadTask leadTask);
	
	/**
	 * 任务是否推荐过
	 * queryInstsIsDone
	 * @param instID
	 * @return 
	 * boolean
	 * @exception 
	 * @since  1.0.0
	 */
	boolean queryInstsIsDone(String instID);

}
