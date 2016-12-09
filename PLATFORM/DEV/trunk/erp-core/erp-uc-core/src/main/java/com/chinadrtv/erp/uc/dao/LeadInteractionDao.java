package com.chinadrtv.erp.uc.dao;

import java.util.Date;
import java.util.List;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.marketing.LeadInteraction;
import com.chinadrtv.erp.model.agent.Cases;
import com.chinadrtv.erp.model.agent.Conpointuse;

/**
 *  API-UC-30.	查询用户交互历史
 *  
 * @author haoleitao
 * @date 2013-5-14 下午1:36:27
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface LeadInteractionDao extends GenericDao<LeadInteraction, String> {
    List<LeadInteraction> getAllLeadInteractionByContactId(String contactId,int index, int size);
    int getAllLeadInteractionByContactIdCount(String contactId);
	int countLeadInteractionByAgent(String agentId, Date begin, Date end);
}
