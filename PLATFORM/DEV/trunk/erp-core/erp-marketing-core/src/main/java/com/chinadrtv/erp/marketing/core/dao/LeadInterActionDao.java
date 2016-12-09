/*
 * @(#)LeadInterActionDao.java 1.0 2013-5-21上午10:11:41
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.dao;

import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.marketing.core.dto.LeadInteractionSearchDto;
import com.chinadrtv.erp.model.marketing.LeadInteraction;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;
import com.chinadrtv.erp.user.dto.AgentUserInfo4TeleDist;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-5-21 上午10:11:41
 * 
 */
public interface LeadInterActionDao extends
		GenericDao<LeadInteraction, java.lang.Long> {
	/**
	 * 
	 * 
	 * @Description: 新增交互记录
	 * @param leadInteraction
	 * @return void
	 * @throws
	 */
	public void insertLeadInterAction(LeadInteraction leadInteraction);

	

	/**
	 * <p>查询总行数</p>
	 * @param contactId
	 * @return Integer
	 */
	Integer querySendHistoryCount(String contactId);

	/**
	 * <p>按客户查询短信历史记录</p>
	 * @param contactId
	 * @param dataGridModel
	 * @return List<LeadInteraction>
	 */
	List<LeadInteraction> querySendHistory(String contactId, DataGridModel dataGridModel);
	
	/**
	 * 根据instId查找最近的电话交话记录
	 * getLatestPhoneInterationByInstId
	 * @param instId
	 * @return 
	 * LeadInteraction
	 * @exception 
	 * @since  1.0.0
	 */
	LeadInteraction getLatestPhoneInterationByInstId(String instId);
	
	LeadInteraction getLatestPhoneInterationByLeadId(Long leadId);
	
	/**
	 * 更新潜客为正式客户
	 * updatePotential2Normal
	 * @param contactId
	 * @param potentialContactId
	 * @return 
	 * int
	 * @exception 
	 * @since  1.0.0
	 */
	int updatePotential2Normal(String contactId, String potentialContactId);
	
	int updateContact(long leadId, String contactId, boolean isPotential);

	int queryLeadInteractionCount(LeadInteractionSearchDto leadInteractionSearchDto);
	
	Map<String, AgentUserInfo4TeleDist> queryLeadInteractionCountInBatch(LeadInteractionSearchDto leadInteractionSearchDto);
	
	int queryAllocatableLeadInteractionCount(LeadInteractionSearchDto leadInteractionSearchDto);
	
	List<Object> queryAllocatableLeadInteraction(LeadInteractionSearchDto leadInteractionSearchDto);
	
	List<Object> queryObsoleteInteraction(LeadInteractionSearchDto leadInteractionSearchDto);
	
	long queryTotalOutcallDuration(List<String> blackPhoneList, String agentId, String lowDate, String highDate);

	/**
	 * 根据responseCode 查询
	 * @param uuid
	 * @return LeadInteraction
	 */
	LeadInteraction queryByResponseCode(String uuid);



	/**
	 * <p>根据配额更新LeadInteraction 部门</p>
	 * @param liDto
	 * @param deptId
	 */
	Integer updateDeptByIndex(String leadInterationId, String deptId);
}
