/**
 * Copyright (c) 2013, Acorn and/or its affiliates. All rights reserved.
 * ACORN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.chinadrtv.erp.sales.service.impl;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.marketing.core.service.LeadInteractionOrderService;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.model.agent.ProblematicOrder;
import com.chinadrtv.erp.sales.core.service.OrderhistService;
import com.chinadrtv.erp.sales.service.ProblematicOrderService;
import com.chinadrtv.erp.tc.core.dao.ProblematicOrderDao;
import com.chinadrtv.erp.tc.core.dto.ProblematicOrderDto;
import com.chinadrtv.erp.tc.core.dto.ProblematicOrderVO;
import com.chinadrtv.erp.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * 2013-7-25 上午10:22:40
 * @version 1.0.0
 * @author yangfei
 *
 */
@Service
public class ProblematicOrderServiceImpl extends GenericServiceImpl<ProblematicOrder, String> implements ProblematicOrderService{
	@Autowired
	private ProblematicOrderDao problematicOrderDao;
	
	@Autowired
	private LeadInteractionOrderService leadInteractionOrderService;
	
	@Autowired
	private OrderhistService orderhistService;
	
	public Map<String, Object> query(ProblematicOrderDto problematicOrderDto, DataGridModel dataModel) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(problematicOrderDto.getEndCrDate())) {
			String endDate = DateUtil.addDays(problematicOrderDto.getEndCrDate(), 1);
			problematicOrderDto.setEndCrDate(endDate);
		}
		List<Map<String, Object>> mapResults = problematicOrderDao.query(problematicOrderDto, dataModel);
		List<ProblematicOrderVO> vos = convertDBResult2Vo(mapResults);
		Integer total = problematicOrderDao.queryCount(problematicOrderDto);
		result.put("rows", vos);
		result.put("total", total);
		return result;
	}
	
	public boolean replyProblematicOrder(ProblematicOrderDto problematicOrderDto) {
		boolean result = false;
		int number = problematicOrderDao.replyProblematicOrder(problematicOrderDto);
		if(number > 0) {
			result = true;
		}
		return result;
	}
	
	public boolean markProblematicOrder(String userId, String orderId, String problemType, String problemDsc) {
		boolean isSuc = false;
		ProblematicOrder po = new ProblematicOrder();
		po.setOrderId(orderId);
		po.setProblemType(problemType);
		po.setProblemDsc(problemDsc);
		po.setCreatedUsr(userId);
		po.setCreatedDate(new Date());
		po.setProblemStatus("1");
		Order order = orderhistService.getOrderHistByOrderid(orderId);
		po.setContact(order.getContactid());
		po.setOrderUsr(order.getCrusr());
		po.setOrderDate(order.getCrdt());
		ProblematicOrder result = problematicOrderDao.save(po);
		if(result != null) {
			isSuc = true;
		}
		return isSuc;
	}
	
	private List<ProblematicOrderVO> convertDBResult2Vo(List<Map<String, Object>> mapResults) {
		List<ProblematicOrderVO> vos = new ArrayList<ProblematicOrderVO>();
		for(Map<String, Object> records : mapResults) {
			ProblematicOrderVO povo = new ProblematicOrderVO();
			String problemId = (String)records.get("PROBLEMID");
			if(StringUtils.isNotBlank(problemId)) {
				povo.setProblemId(problemId);
			}
			String orderId = (String)records.get("ORDERID");
			if(StringUtils.isNotBlank(orderId)) {
				povo.setOrderid(orderId);
				String campaignId = leadInteractionOrderService.queryCampaignByOrderId(orderId);
				povo.setCampaignId(campaignId);
			}
			Date crDate = (Date)records.get("CRDATE");
			if(crDate != null) {
				povo.setCrdate(crDate);
			}
			String status = (String)records.get("STATUS");
			if(StringUtils.isNotBlank(status)) {
				povo.setStatus(status);
			}
			String statusDsc = (String)records.get("STATUSDSC");
			if(StringUtils.isNotBlank(statusDsc)) {
				povo.setStatusDsc(statusDsc);
			}
			double mailPrice = ((BigDecimal)records.get("MAILPRICE")).doubleValue();
			if(mailPrice != 0) {
				povo.setMailprice(mailPrice);
			}
			double totalPrice = ((BigDecimal)records.get("TOTALPRICE")).doubleValue();
			if(totalPrice != 0) {
				povo.setTotalprice(totalPrice);
			}
			String crUsr = (String)records.get("CRUSR");
			if(StringUtils.isNotBlank(crUsr)) {
				povo.setCrusr(crUsr);
			}
			String contactId = (String)records.get("CONTACTID");
			if(StringUtils.isNotBlank(contactId)) {
				povo.setContactId(contactId);
			}
			String contactName = (String)records.get("CONTACTNAME");
			if(StringUtils.isNotBlank(contactName)) {
				povo.setContactName(contactName);
			}
			Date sentDate = (Date)records.get("SENTDATE");
			if(sentDate != null) {
				povo.setSentDate(sentDate);
			}
			Date problemDate = (Date)records.get("PROBLEMDATE");
			if(problemDate != null) {
				povo.setProblemDate(problemDate);
			}
			String problemUser = (String)records.get("PROBLEMUSER");
			if(StringUtils.isNotBlank(problemUser)) {
				povo.setProblemUser(problemUser);
			}
			String problemtype = (String)records.get("PROBLEMTYPE");
			if(StringUtils.isNotBlank(problemtype)) {
				povo.setProblemtype(problemtype);
			}
			String problemTypeDsc = (String)records.get("PROBLEMTYPEDSC");
			if(StringUtils.isNotBlank(problemTypeDsc)) {
				povo.setProblemTypeDsc(problemTypeDsc);
			}
			String problemDsc = (String)records.get("PROBLEMDSC");
			if(StringUtils.isNotBlank(problemDsc)) {
				povo.setProblemDsc(problemDsc);
			}
			String problemStatus = (String)records.get("PROBLEMSTATUS");
			if(StringUtils.isNotBlank(problemStatus)) {
				povo.setProblemStatus(problemStatus);
			}
			String problemStatusDsc = (String)records.get("PROBLEMSTATUSDSC");
			if(StringUtils.isNotBlank(problemStatusDsc)) {
				povo.setProblemStatusDsc(problemStatusDsc);
			}
			vos.add(povo);
		}
		return vos;
	}

	@Override
	protected GenericDao<ProblematicOrder, String> getGenericDao() {
		return problematicOrderDao;
	}
	
	@Override
	public ProblematicOrder get(String id) {
		return problematicOrderDao.get(id);
	}
}
