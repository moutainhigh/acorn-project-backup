/*
 * @(#)LeadDao.java 1.0 2013-5-9上午10:41:37
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.dao;

import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.marketing.core.dto.LeadQueryDto;
import com.chinadrtv.erp.model.marketing.Lead;

/**
 * 
 * @author cuiming
 * @author yangfei
 * @version 1.0
 * @since 2013-5-9 上午10:41:37
 * 
 */
public interface LeadDao extends GenericDao<Lead, java.lang.Long> {

	public void insertLead(Lead lead);
	
	Lead getLastestAliveLead(String agentId, String contactId, String campaignId);

    Lead getLastestAliveLead(String agentId, String contactId);

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

    int updateStatus4OverdueLead(long leadId);
    
    List<Object> queryOverdueLead();
    
    List<Map<String, Object>> query(LeadQueryDto leadQueryDto, DataGridModel dataModel);

}
