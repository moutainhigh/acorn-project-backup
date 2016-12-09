/*
 * @(#)LeadInterActionService.java 1.0 2013-5-21下午1:23:10
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.service;

import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.core.service.GenericService;
import com.chinadrtv.erp.marketing.core.dto.LeadInteractionSearchDto;
import com.chinadrtv.erp.model.marketing.Lead;
import com.chinadrtv.erp.model.marketing.LeadInteraction;
import com.chinadrtv.erp.user.dto.AgentUserInfo4TeleDist;
import com.chinadrtv.erp.user.model.AgentUser;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-5-21 下午1:23:10
 * 
 */
public interface LeadInterActionService extends GenericService<LeadInteraction, Long> {
	/**
	 * 
	 * @Description: 新增交互记录
	 * @param leadInteraction
	 * @return
	 * @return Boolean
	 * @throws
	 */
	public Boolean insertLeadInterAction(LeadInteraction leadInteraction);
	
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
	
	boolean updateLeadInteraction(long leadId, String resultCode);
	
	Lead getLead2Updated(long leadId);
	
	int updatePotential2Normal(String contactId, String potentialContactId);
	
	int queryLeadInteractionCount(LeadInteractionSearchDto leadInteractionSearchDto);
	
	Map<String, AgentUserInfo4TeleDist> queryLeadInteractionCountInBatch(LeadInteractionSearchDto leadInteractionSearchDto);
	/**
	 * 从leadInteraction中查询可分配 的话务数量
	 * queryAllocatableLeadInteractionCount
	 * @param leadInteractionSearchDto
	 * @return 
	 * int
	 * @exception 
	 * @since  1.0.0
	 */
	int queryAllocatableLeadInteractionCount(LeadInteractionSearchDto leadInteractionSearchDto);
	
	/**
	 * 从leadInteraction中查询可分配 的话务
	 * queryAllocatableLeadInteraction
	 * @param leadInteractionSearchDto
	 * @return 
	 * List<Object>
	 * @exception 
	 * @since  1.0.0
	 */
	List<Object> queryAllocatableLeadInteraction(LeadInteractionSearchDto leadInteractionSearchDto);
	
	/**
	 * 查询被"重"淘汰的通话记录
	 * updateObsoleteInteraction
	 * @param leadInteractionSearchDto
	 * @return 
	 * int
	 * @exception 
	 * @since  1.0.0
	 */
	List<Object> queryObsoleteInteraction(LeadInteractionSearchDto leadInteractionSearchDto);
	
	/**
	 * 查询座席当天累计外呼计时（秒）
	 * queryTotalIntradayOutcallTime
	 * @param agentId
	 * @return 
	 * long
	 * @exception 
	 * @since  1.0.0
	 * 不计入统计的电话存放在iagent.names, 关键字BLACKPHONELIST
	 * select * from iagent.names n where n.tid='BLACKPHONELIST' for update
	 * insert into iagent.names(tid, id, tdsc, dsc, valid) values('BLACKPHONELIST', 1, '黑电话','不计入外呼时间统计的电话','0')
	 */
	long queryTotalIntradayOutcallTime(String agentId);

	LeadInteraction get(Long id);

	/**
	 * 根据responseCode 查询
	 * @param uuid
	 * @return LeadInteraction
	 */
	LeadInteraction queryByResponseCode(String uuid);
	
	AgentUser getLoginUser();
}
