/*
 * @(#)LeadInteractionOrderServiceImpl.java 1.0 2013-7-3下午3:46:40
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.exception.BizException;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.marketing.core.dao.LeadInterActionDao;
import com.chinadrtv.erp.marketing.core.dao.LeadInteractionOrderDao;
import com.chinadrtv.erp.marketing.core.dto.SmsQueryDto;
import com.chinadrtv.erp.marketing.core.dto.SmsSendDto;
import com.chinadrtv.erp.marketing.core.service.LeadInteractionOrderService;
import com.chinadrtv.erp.marketing.core.service.SmsApiService;
import com.chinadrtv.erp.model.marketing.LeadInteraction;
import com.chinadrtv.erp.model.marketing.LeadInteractionOrder;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.util.SecurityHelper;
import com.chinadrtv.erp.util.PojoUtils;

/**
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author andrew
 * @version 1.0
 * @since 2013-7-3 下午3:46:40 
 * 
 */
@Service
public class LeadInteractionOrderServiceImpl extends GenericServiceImpl<LeadInteractionOrder, Long> implements
		LeadInteractionOrderService {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(LeadInteractionOrderServiceImpl.class);
	@Autowired
	private LeadInteractionOrderDao leadInteractionOrderDao;
	@Autowired
	private LeadInterActionDao leadInterActionDao;
	@Autowired
	private SmsApiService smsApiService;
	
	@Override
	protected GenericDao<LeadInteractionOrder, Long> getGenericDao() {
		return leadInteractionOrderDao;
	}

	/** 
	 * <p>Title: save</p>
	 * <p>Description: </p>
	 * @param model
	 * @see com.chinadrtv.erp.core.service.impl.GenericServiceImpl#save(java.lang.Object)
	 */ 
	@Override
	public void save(LeadInteractionOrder model) {
		Long interactionId = model.getLeadInteraction().getId();
		if(null==interactionId){
			throw new BizException("LeadInteraction.id 不能为null");
		}
		LeadInteraction li = leadInterActionDao.get(interactionId);
		model.setLeadInteraction(li);
		AgentUser user = SecurityHelper.getLoginUser();
		model.setCreateDate(new Date());
		model.setCreateUser(user.getUserId());
		super.save(model);
	}

	/** 
	 * <p>Title: querySendHistory</p>
	 * <p>Description: </p>
	 * @param contactId
	 * @return Map<String, Object>
	 * @see com.chinadrtv.erp.marketing.core.service.LeadInteractionOrderService#querySendHistory(java.lang.String)
	 */ 
	@Override
	public Map<String, Object> querySendHistory(String contactId, DataGridModel dataGridModel) {
		Map<String, Object> pageMap = new HashMap<String, Object>();
		if(null==contactId || "".equals(contactId)){
			pageMap.put("total", 0);
			pageMap.put("rows", new ArrayList<LeadInteraction>());
			return pageMap;
		}
		
		Integer total = leadInterActionDao.querySendHistoryCount(contactId);
		List<LeadInteraction> pageList = leadInterActionDao.querySendHistory(contactId, dataGridModel);
		List<SmsQueryDto> dtoList = new ArrayList<SmsQueryDto>();
		for(LeadInteraction li : pageList){
			SmsQueryDto dto = (SmsQueryDto) PojoUtils.convertPojo2Dto(li, SmsQueryDto.class);
			String uuid = li.getAppResponseCode();
			dto.setUuid(uuid);
			try {
				SmsSendDto ssDto = smsApiService.getSmsByUuid(uuid);
				if(null!=ssDto){
					dto.setMobile(ssDto.getMobile());
					dto.setMobileUnMask(ssDto.getMobile());
					dto.setTemplateTheme(ssDto.getTemplateTheme());
					dto.setAppResponseCode(ssDto.getSmsStatus());
					dto.setAppResponseStatus(ssDto.getSmsStatus());
					dto.setSmsName(ssDto.getSmsName());
					dto.setSmsErrorCode(ssDto.getSmsErrorCode());
				}
			} catch (ServiceException e) {
                logger.error(e.getMessage(),e); //e.printStackTrace();
			}
			Set<LeadInteractionOrder> lioSet = li.getLeadInteractionOrders();
			StringBuffer sb = new StringBuffer();
			for(LeadInteractionOrder lio : lioSet){
				sb.append(lio.getOrderId() + "、");
			}
			if(sb.toString().length()>2){
				String orderId = sb.toString().substring(0, sb.toString().length()-1);
				dto.setOrderIds(orderId);
			}
			
			dtoList.add(dto);
		}
		pageMap.put("total", total);
		pageMap.put("rows", dtoList);
		return pageMap;
	}
	
	public String queryCampaignByOrderId(String orderId) {
		return leadInteractionOrderDao.queryCampaignByOrderId(orderId);
	}
	
	public List<Map<String, Object>> queryLeadCampaignByOrderId(String orderId) {
		return leadInteractionOrderDao.queryLeadCampaignByOrderId(orderId);
	}

}
