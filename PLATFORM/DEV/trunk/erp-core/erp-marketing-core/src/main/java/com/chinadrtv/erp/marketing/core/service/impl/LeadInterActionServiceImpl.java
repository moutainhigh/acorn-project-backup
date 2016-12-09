/*
 * @(#)LeadInterAction.java 1.0 2013-5-21下午1:24:48
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.service.impl;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.service.NamesService;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.marketing.core.dao.LeadInterActionDao;
import com.chinadrtv.erp.marketing.core.dto.LeadInteractionSearchDto;
import com.chinadrtv.erp.marketing.core.service.LeadInterActionService;
import com.chinadrtv.erp.marketing.core.util.InteractionMapping;
import com.chinadrtv.erp.model.Names;
import com.chinadrtv.erp.model.marketing.Lead;
import com.chinadrtv.erp.model.marketing.LeadInteraction;
import com.chinadrtv.erp.user.dto.AgentUserInfo4TeleDist;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.util.SecurityHelper;
import com.chinadrtv.erp.util.DateUtil;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-5-21 下午1:24:48
 * 
 */
@Service("leadInterActionService")
public class LeadInterActionServiceImpl extends GenericServiceImpl<LeadInteraction, Long> implements LeadInterActionService {
	
	@Autowired
	private LeadInterActionDao leadInterActionDao;
    @Autowired
    private NamesService namesService;

	private static final Logger logger = LoggerFactory
			.getLogger(LeadInterActionServiceImpl.class);

	/**
	 * 
	 * @Description 新增交互记录
	 * @param leadInteraction
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.marketing.core.service.LeadInterActionService#
	 *      insertLeadInterAction(com.chinadrtv.erp.model.marketing.LeadInteraction)
	 */
	public Boolean insertLeadInterAction(LeadInteraction leadInteraction) {
		Boolean flag = true;
		try {
			leadInteraction.setPriority(-1L);
			leadInterActionDao.insertLeadInterAction(leadInteraction);
		} catch (Exception e) {
			logger.error("新增交互记录失败" + e);
			flag = false;
		}
		return flag;
	}

	@Override
	public LeadInteraction getLatestPhoneInterationByInstId(String instId) {
		return leadInterActionDao.getLatestPhoneInterationByInstId(instId);
	}
	
	public LeadInteraction getLatestPhoneInterationByLeadId(Long leadId) {
		return leadInterActionDao.getLatestPhoneInterationByLeadId(leadId);
	}
	
	public boolean updateLeadInteraction(long leadId, String resultCode) {
		boolean result = false;
		Long leadInterPriority = InteractionMapping.InteractionResultCode2priority.get(resultCode);
		if(leadInterPriority == null) {
			return result;
		}
		LeadInteraction leadInteraction = leadInterActionDao.getLatestPhoneInterationByLeadId(leadId);
		if(leadInteraction == null) {
			return result;
		}
		if(leadInteraction.getPriority() == null || leadInterPriority > leadInteraction.getPriority()) {
			//优先级高,更新resultcode
			String resultDescription = InteractionMapping.InteractionResultCode2description.get(resultCode);
			leadInteraction.setResultCode(resultCode);
			leadInteraction.setResultDescriptiong(resultDescription);
			leadInteraction.setPriority(new Long(leadInterPriority));
			leadInterActionDao.saveOrUpdate(leadInteraction);
			result = true;
		}
		return result;
	}
	
	public Lead getLead2Updated(long leadId) {
		LeadInteraction leadInteraction = leadInterActionDao.getLatestPhoneInterationByLeadId(leadId);
		Lead lead = null;
		try {
			if (leadInteraction != null && StringUtils.isNotBlank(leadInteraction.getResultCode())) {
				lead = new Lead();
				lead.setId(leadId);
				if(leadInteraction.getResultCode() != null &&
						InteractionMapping.InteractionResultCode2leadStatus.get(leadInteraction.getResultCode()) != null) {
					long status = InteractionMapping.InteractionResultCode2leadStatus.get(leadInteraction.getResultCode());
					lead.setStatus(status);
				}
				if(leadInteraction.getResultCode() != null &&
						InteractionMapping.InteractionResultCode2leadPriority.get(leadInteraction.getResultCode()) != null) {
					long priority = InteractionMapping.InteractionResultCode2leadPriority.get(leadInteraction.getResultCode());
					lead.setPriority(priority);
				}
			}
		} catch(Exception e) {
			logger.warn("更新线索重要性失败",e);
		}
		return lead;
	}

	public int updatePotential2Normal(String contactId, String potentialContactId) {
		return leadInterActionDao.updatePotential2Normal(contactId, potentialContactId);
	}
	
	public int queryLeadInteractionCount(LeadInteractionSearchDto leadInteractionSearchDto) {
		return leadInterActionDao.queryLeadInteractionCount(leadInteractionSearchDto);
	}
	
	public Map<String, AgentUserInfo4TeleDist> queryLeadInteractionCountInBatch(LeadInteractionSearchDto leadInteractionSearchDto) {
		return leadInterActionDao.queryLeadInteractionCountInBatch(leadInteractionSearchDto);
	}
	
	public int queryAllocatableLeadInteractionCount(LeadInteractionSearchDto leadInteractionSearchDto) {
		leadInteractionSearchDto.setManagerGrp(getLoginUser().getWorkGrp());
		return leadInterActionDao.queryAllocatableLeadInteractionCount(leadInteractionSearchDto);
	}
	
	public long queryTotalIntradayOutcallTime(String agentId) {
		String lowDate, highDate = null;
		Calendar  calendar=Calendar.getInstance();  
		int year=calendar.get(Calendar.YEAR);  
		int month=calendar.get(Calendar.MONTH);  
		month++; 
		int date=calendar.get(Calendar.DATE);
		lowDate=year+"-"+month+"-"+date;
		try {
			lowDate=DateUtil.dateToString(DateUtil.string2Date(lowDate, "yyyy-MM-dd"));
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {
			highDate = DateUtil.addDays(lowDate, 1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<String> blackPhoneList = LookupNames("BLACKPHONELIST");
		return leadInterActionDao.queryTotalOutcallDuration(blackPhoneList, agentId, lowDate, highDate);
	}
	
	public List<String> LookupNames(String tid) {
		List<Names> orderTypeList = null;
		orderTypeList = namesService.getAllNames(tid);
		List<String> blackPhoneList = new ArrayList<String>();
		for (Names orderType : orderTypeList) {
			blackPhoneList.add(orderType.getDsc());
		}
		return blackPhoneList;
	}
	
	public List<Object> queryAllocatableLeadInteraction(LeadInteractionSearchDto leadInteractionSearchDto) {
		leadInteractionSearchDto.setManagerGrp(getLoginUser().getWorkGrp());
		return leadInterActionDao.queryAllocatableLeadInteraction(leadInteractionSearchDto);
	}
	
	public List<Object> queryObsoleteInteraction(LeadInteractionSearchDto leadInteractionSearchDto) {
		leadInteractionSearchDto.setManagerGrp(getLoginUser().getWorkGrp());
		return leadInterActionDao.queryObsoleteInteraction(leadInteractionSearchDto);
	}
	
	@Override
	protected GenericDao<LeadInteraction, Long> getGenericDao() {
		return leadInterActionDao;
	}

	/** 
	 * <p>Title: get</p>
	 * <p>Description: </p>
	 * @param id
	 * @return LeadInteraction
	 * @see com.chinadrtv.erp.marketing.core.service.LeadInterActionService#get(java.lang.Long)
	 */ 
	@Override
	public LeadInteraction get(Long id) {
		return leadInterActionDao.get(id);
	}

	@Override
	public LeadInteraction queryByResponseCode(String uuid) {
		return leadInterActionDao.queryByResponseCode(uuid);
	}
	
	public AgentUser getLoginUser() {
		AgentUser user = SecurityHelper.getLoginUser();
		return user;
	}

}
