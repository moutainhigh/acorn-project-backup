package com.chinadrtv.erp.uc.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.model.Address;
import com.chinadrtv.erp.model.marketing.LeadInteraction;
import com.chinadrtv.erp.uc.dao.AddressDao;
import com.chinadrtv.erp.uc.dao.LeadInteractionDao;
import com.chinadrtv.erp.uc.service.LeadInteractionService;

/**
 * API-UC-30.	查询用户交互历史
 * @author haoleitao
 * @date 2013-5-14 下午1:54:00
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service("leadInteractionService")
public class LeadInteractionServiceImpl  extends GenericServiceImpl<LeadInteraction, String>  implements LeadInteractionService {
	 @Autowired
	 private LeadInteractionDao leadInteractionDao;
	 
	 @Override
	 protected GenericDao<LeadInteraction, String> getGenericDao() {
		 return leadInteractionDao;
	 } 
	 
	public List<LeadInteraction> getAllLeadInteractionByContactId(
			String contactId, int index, int size) {
		return leadInteractionDao.getAllLeadInteractionByContactId(contactId, index, size);
	}

	public int getAllLeadInteractionByContactIdCount(String contactId) {
		return leadInteractionDao.getAllLeadInteractionByContactIdCount(contactId);
	}

	@Override
	public int countLeadInteractionByAgent(String agentId, Date begin, Date end) {
		return leadInteractionDao.countLeadInteractionByAgent(agentId, begin, end);
	}



}
