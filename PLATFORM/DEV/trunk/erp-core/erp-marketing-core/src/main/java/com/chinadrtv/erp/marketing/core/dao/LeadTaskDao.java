/*
 * @(#)LeadTaskDao.java 1.0 2013-5-10上午11:34:22
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.marketing.core.dto.CampaignTaskDto;
import com.chinadrtv.erp.model.marketing.LeadTask;

/**
 * @author cuiming
 * @author yangfei
 * @version 1.0
 * @since 2013-5-10 上午11:34:22
 * 
 */
public interface LeadTaskDao extends GenericDao<LeadTask, java.lang.Long> {

	public void saveLeadTask(LeadTask leadTask);
	
	void evict(LeadTask leadTask);

	public void updateLeadTask(LeadTask leadTask);

	public List<LeadTask> getLeadTaskByHql(Long leadId);

	/**
	 * @param mTask
	 * @param dataModel
	 * @return List<MarketingTask>
	 * @exception
	 * @since 1.0.0
	 */
	List<Object> query(CampaignTaskDto mTask, DataGridModel dataModel);

	/**
	 * queryCount
	 * 
	 * @param mTask
	 * @return Integer
	 * @exception
	 * @since 1.0.0
	 */
	Integer queryCount(CampaignTaskDto mTask);
	
	List<Object> queryAgentAllocatedCount( );
	
	List<Object> queryAgentUndialCount( );
	
	/**
	 * 查询客户分配 历史
	 * queryContactTaskHistory
	 * @param contactId
	 * @return 
	 * List<Map<String,Object>>
	 * @exception 
	 * @since  1.0.0
	 */
	List<Map<String, Object>> queryContactTaskHistory(String contactId);
	
	Integer queryUnStartedCampaignTaskCount(CampaignTaskDto mTaskDto);

	/**
	 * updateInstStatus
	 * 
	 * @param processID
	 * @param instID
	 * @param status
	 * @param remark
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	int updateInstStatus(String instID, String status, String remark, String taskID);

	/**
	 * 根据lead更新task所有者 updateTaskOwnerByLead
	 * 
	 * @param leadID
	 * @param newOwner
	 * @return 所有的被更新记录ID List<Object>
	 * @exception
	 * @since 1.0.0
	 */
	int updateTaskOwnerByLead(long leadID, String newOwner);
	
	/**
	 * 根据线索更新任务状态
	 * updateTaskStatusByLead
	 * @param leadID
	 * @param status
	 * @return 
	 * int
	 * @exception 
	 * @since  1.0.0
	 */
	int updateTaskStatusByLead(long leadID, String status);

	/**
	 * 查询lead相关的所有task queryTasksByLead
	 * 
	 * @param leadID
	 * @return List<Object>
	 * @exception
	 * @since 1.0.0
	 */
	List<Object> queryTasksByLead(long leadID);
	
	List<Object> queryInstsByLead(long leadID);
	
	List<Object> queryAliveInstsByLead(long leadID);
	
	List<LeadTask> queryInvalidPushInst();
	
	int updatePotential2Normal(String contactId, String potentialContactId);
	
	/**
	 * 更新任务关联客户
	 * updateContact
	 * @param instId
	 * @param contactId
	 * @param isPotential
	 * @return 
	 * int
	 * @exception 
	 * @since  1.0.0
	 */
	int updateContact(String instId, String contactId, boolean isPotential);

	/**
	 * @param leadID
	 * @return String
	 * @exception
	 * @since 1.0.0
	 */
	String queryBPMProcessID(String leadID);

	Date queryEndDate(String leadID);
	
	int updateInstIsDone(String instID, long isDone);
	
	LeadTask queryInst(String instID);
	
	int updateInstAppointDate(String instID, Date appointDate);
	
	Object queryInstsIsDone(String instID);
}
