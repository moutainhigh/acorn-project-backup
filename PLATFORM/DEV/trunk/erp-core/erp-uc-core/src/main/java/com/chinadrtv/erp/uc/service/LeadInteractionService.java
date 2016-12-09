package com.chinadrtv.erp.uc.service;

import java.util.Date;
import java.util.List;

import com.chinadrtv.erp.core.service.GenericService;
import com.chinadrtv.erp.model.marketing.LeadInteraction;
/** 
 *  API-UC-30.	查询用户交互历史
 *  
 * @author haoleitao
 * @date 2013-5-14 下午1:47:49
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface LeadInteractionService extends GenericService<LeadInteraction,String> {
	    List<LeadInteraction> getAllLeadInteractionByContactId(String contactId,int index, int size);
	    int getAllLeadInteractionByContactIdCount(String contactId);
	    
	    /**
	     * 通过坐席id查找通话数量
	     * @param agentId
	     * @param begin
	     * @param end
	     * @return
	     */
	    int countLeadInteractionByAgent(String agentId, Date begin, Date end);
}
