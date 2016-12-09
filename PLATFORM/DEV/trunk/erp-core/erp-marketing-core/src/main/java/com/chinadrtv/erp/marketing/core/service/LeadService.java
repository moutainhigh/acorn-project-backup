/*
 * @(#)LeadService.java 1.0 2013-5-9下午1:19:33
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.service;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.marketing.core.dto.LeadQueryDto;
import com.chinadrtv.erp.model.marketing.Lead;
import com.chinadrtv.erp.model.marketing.LeadTask;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-5-9 下午1:19:33
 * 
 */
public interface LeadService{

	/**
	 * 
	 * @Description: 新增销售线索
	 * @param lead
	 * @return
	 * @return Lead
	 * @throws
	 */
	public LeadTask saveLead(Lead lead) throws ServiceException;
	
	/**
	 * 完整插入线索对象，不做处理
	 * insertLead 
	 * void
	 * @throws ServiceException 
	 * @exception 
	 * @since  1.0.0
	 */
	void insertLead(Lead lead) throws ServiceException;

	/**
	 * 
	 * @Description: 更新销售线索
	 * @param leadId
	 * @param status
	 * @param statusReason
	 * @param updateUser
	 * @return
	 * @return Boolean
	 * @throws
	 */
	public Boolean updateLead(Long leadId, Long status, String statusReason,
			String updateUser) throws ServiceException;


    /**
     * @Description: 更新销售线索
     * @param leadId
     * @param status
     * @param statusReason
     * @param updateUser
     * @param updateDate
     * @param dnis
     * @param ani
     * @param userGroup
     * @return
     * @throws ServiceException
     */
    public Boolean updateLead(Long leadId, Long status, String statusReason,
                              String updateUser,Date updateDate,String dnis,String ani,String userGroup) throws ServiceException;


    public boolean updateLead(Lead lead);

	/**
	 * 
	 * @Description: 重新分配销售线索
	 * @param leadId
	 * @param owner
	 * @param updateUser
	 *            分配人
	 * @return
	 * @return Boolean
	 * @throws
	 */
	public Boolean reapportionLead(Long leadId, String owner, String updateUser)
			throws ServiceException;
	
	/**
	 * 查询最新的活跃的当前坐席对特定营销任务的客户的线索
	 * getLastestAliveLead
	 * @param agentId
	 * @param contactId
	 * @param campaignId
	 * @return 
	 * 存在，则返回Lead，不存在返回null
	 * @exception 
	 * @since  1.0.0
	 */
	Lead getLastestAliveLead(String agentId, String contactId, String campaignId);
	
	/**
	 * 查询最新的活跃的当前坐席的客户的线索
	 * getLastestAliveLead
	 * @param agentId
	 * @param contactId
	 * @param campaignId
	 * @return 
	 * 存在，则返回Lead，不存在返回null
	 * @exception 
	 * @since  1.0.0
	 */
	Lead getLastestAliveLead(String agentId, String contactId);
	
	/**
	 * 查询过期lead
	 * queryOverdueLead
	 * @return 
	 * List<Object>
	 * @exception 
	 * @since  1.0.0
	 */
	List<Object> queryOverdueLead();
	
	/**
	 * 更新过期线索状态
	 * updateStatus4OverdueLead
	 * @param leadId
	 * @return 
	 * int
	 * @exception 
	 * @since  1.0.0
	 */
	int updateStatus4OverdueLead(long leadId);
	
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
	 * 更新任务及线索关联客户
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
	 * 线索管理查询
	 * query
	 * @param leadQueryDto
	 * @param dataModel
	 * @return 
	 * List<Map<String,Object>>
	 * @exception 
	 * @since  1.0.0
	 */
	List<Map<String, Object>> query(LeadQueryDto leadQueryDto, DataGridModel dataModel);

    Lead get(Long id);
    
    /**
     * 切换线索至指定的座席:关闭老的线索，创建新的线索；关闭老的任务，创建新的任务
     * transferLeadAndSubsidiaries
     * @param oldLeadId
     * @param instId
     * @param newAgentId
     * @param endDate
     * @param comment
     * @return 
     * Long
     * @throws ServiceException 
     * @throws Exception 
     * @exception 
     * @since  1.0.0
     */
    Long transferLeadAndSubsidiaries(String instId, String newAgentId, Date endDate, String comment) throws ServiceException, Exception;
    
    /**
     * 判断 线索是否有效
     * isAlive
     * @param lead
     * @return 
     * boolean
     * @exception 
     * @since  1.0.0
     */
    boolean isAlive(Lead lead);
}
