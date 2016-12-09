/*
 * @(#)OrderhistTrackTaskServiceImpl.java 1.0 2013年12月24日下午3:58:42
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.sales.core.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.exception.BizException;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.marketing.core.common.CampaignTaskSourceType;
import com.chinadrtv.erp.marketing.core.dto.CampaignDto;
import com.chinadrtv.erp.marketing.core.service.CampaignApiService;
import com.chinadrtv.erp.marketing.core.service.CampaignBPMTaskService;
import com.chinadrtv.erp.marketing.core.service.LeadInteractionOrderService;
import com.chinadrtv.erp.marketing.core.service.LeadService;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.model.agent.OrderhistTrackLog;
import com.chinadrtv.erp.model.agent.OrderhistTrackTask;
import com.chinadrtv.erp.model.marketing.Lead;
import com.chinadrtv.erp.model.marketing.LeadTask;
import com.chinadrtv.erp.sales.core.dao.OrderhistTrackLogDao;
import com.chinadrtv.erp.sales.core.dao.OrderhistTrackTaskDao;
import com.chinadrtv.erp.sales.core.dto.OrderhistTrackTaskDto;
import com.chinadrtv.erp.sales.core.service.OrderhistService;
import com.chinadrtv.erp.sales.core.service.OrderhistTrackTaskService;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.service.UserService;
import com.chinadrtv.erp.user.util.SecurityHelper;
import com.chinadrtv.erp.util.DateUtil;
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
 * @since 2013年12月24日 下午3:58:42 
 * 
 */
@Service
public class OrderhistTrackTaskServiceImpl extends GenericServiceImpl<OrderhistTrackTask, Long> implements
		OrderhistTrackTaskService {
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(OrderhistTrackTaskServiceImpl.class);

	@Autowired
	private OrderhistTrackTaskDao orderhistTrackTaskDao;
	@Autowired
	private OrderhistTrackLogDao orderhistTrackLogDao;
	@Autowired
	private OrderhistService orderhistService;
	@Autowired
	private LeadService leadService;
	@Autowired
	private UserService userService;
	@Autowired
	private LeadInteractionOrderService leadInteractionOrderService;
	@Autowired
	private CampaignApiService campaignApiService;
	@Autowired
	private CampaignBPMTaskService campaignBPMTaskService;
	
	/** 
	 * <p>Title: getGenericDao</p>
	 * <p>Description: </p>
	 * @return
	 * @see com.chinadrtv.erp.core.service.impl.GenericServiceImpl#getGenericDao()
	 */ 
	@Override
	protected GenericDao<OrderhistTrackTask, Long> getGenericDao() {
		return orderhistTrackTaskDao;
	}

	/** 
	 * <p>Title: assignToAgent</p>
	 * <p>Description: </p>
	 * @param orderTrackTaskDto
	 * @throws Exception
	 * @see com.chinadrtv.erp.sales.core.service.OrderhistTrackTaskService#assignToAgent(com.chinadrtv.erp.sales.core.dto.OrderhistTrackTaskDto)
	 */ 
	@Override
	public Integer assignToAgent(String[] orderIdArr, String trackUser, String ownerUser) throws Exception {
		String dn = userService.getUserDN(trackUser);
    	if(null == dn || "".equals(dn)){
    		throw new BizException("跟单用户不正确");
    	}
    	
		AgentUser user = SecurityHelper.getLoginUser();
		
		Integer totalCount = 0;
		
		for(String orderId : orderIdArr) {
			Order order = orderhistService.getOrderHistByOrderid(orderId);
			
			/**
			（3）仅对订购、分拣状态的订单显示“分配跟单任务”按钮
			（4）同一员工账号下，已经分配过的订单无法进行二次分配；被分配跟单订单可以在跟单人订单列表中被再次分配
			（5）分配完成后，对跟单人ID产生一个新建的销售线索，建议线索类型为跟单分配，有效期为31天（根据订单创建时间+31天）；
			同时在跟单人任务池中产生一条跟单任务，任务来源默认跟单分配。同时在跟单人-我的订单-全部订单中新增一条订单记录，以便坐席进行跟单备注。
			 */
			OrderhistTrackTask ott = orderhistTrackTaskDao.queryByOrderId(orderId);
			
			//如果跟单人是订单创建用户则跳过
			if(trackUser.equals(order.getCrusr())) {
				throw new BizException("订单[" + order.getOrderid() + "]的跟单人不能为订单创建人，请重新选择跟单人");
			}
			
			//如果订单跟单人已经是指定的跟单人则跳过
			if(null != order.getTrackUsr() && null != ott  && order.getTrackUsr().equals(trackUser)) {
				throw new BizException("订单[" + order.getOrderid() + "]已分配给坐席[" + trackUser + "]，请在[" + trackUser + "]下分配跟单任务");
			}
			
			//只有在订单当前跟单人下才能进行二次分配
			if(null != order.getTrackUsr() && null != ott && !order.getTrackUsr().equals(ownerUser)) {
				throw new BizException("订单[" + order.getOrderid() + "] 操作权不在当前用户下");
			}
			
			if(null == ott) {
				ott = new OrderhistTrackTask();
			}else{
				//如果是二次分配则关闭前任跟单人对应的任务
				campaignBPMTaskService.completeTaskAndUpdateStatus("", String.valueOf(ott.getTaskId()), null, ott.getTrackUser());
			}
			
			//创建任务
			LeadTask leadTask = leadService.saveLead(this.leadAdapter(trackUser, order.getContactid(), order.getOrderid()));
			
			ott.setOrderId(orderId);
			ott.setAssignUser(user.getUserId());
			ott.setAssignTime(new Date());
			ott.setTrackUser(trackUser);
			ott.setTaskId(Long.parseLong(leadTask.getBpmInstanceId()));
			orderhistTrackTaskDao.saveOrUpdate(ott);
			
			//保存日志
			OrderhistTrackLog otl = (OrderhistTrackLog) PojoUtils.convertDto2Pojo(ott, OrderhistTrackLog.class);
			otl.setId(null);
			otl.setOwnerUser(trackUser);
			otl.setAssignTime(new Date());
			
			orderhistTrackLogDao.saveOrUpdate(otl);
			
			//更新订单
			order.setMddt(new Date());
			order.setMdusr(user.getUserId());
			order.setTrackUsr(trackUser);
			orderhistService.saveOrUpdate(order);
			
			totalCount ++;
		}
		
		return totalCount;
	}

	private Lead leadAdapter(String agent, String contactId, String orderId) throws ServiceException, ParseException {
		AgentUser user = SecurityHelper.getLoginUser();
		
		CampaignDto campaignDto = null;
		String campaignId = null;
		Long leadId = null;
		try {
			List<Map<String, Object>> leadCampaignIds = leadInteractionOrderService.queryLeadCampaignByOrderId(orderId);
			for(Map<String, Object> mo : leadCampaignIds) {
				String campaignIdResult = String.valueOf(((BigDecimal)mo.get("CAMID")).longValue());
				Long leadIdResult = ((BigDecimal)mo.get("LEADID")).longValue();
				if(StringUtils.isNotBlank(campaignIdResult)) {
					campaignId = campaignIdResult;
					leadId = leadIdResult;
					break;
				}
			}
		} catch(Exception e) {
			//step over
			logger.warn("营销计划查询计划失败，不影响正跟单任务继续");
		}
		if(null != campaignId && !"".equals(campaignId)) {
			campaignDto = campaignApiService.getCampaign(Long.parseLong(campaignId));
			if(null == campaignDto) {
				List<CampaignDto> tempList = campaignApiService.queryDefaultCampaign();
				if(null != tempList && tempList.size() > 0) {
					campaignDto = tempList.get(0);
				}else{
					throw new ServiceException("未找到营销计划");
				}
			}
		}
		//如果获取不到，则取默认营销计划
		if(null == campaignDto) {
			List<CampaignDto> tmpList = campaignApiService.queryDefaultCampaign();
			if(null != tmpList && tmpList.size() >0 ) {
				campaignDto = tmpList.get(0);
			}
		}
		
		Lead lead = new Lead();
        lead.setContactId(contactId);
        if(leadId != null) {
        	lead.setPreviousLeadId(leadId);
        }
        lead.setCampaignId(campaignDto.getId());
    	lead.setDnis(campaignDto.getDnis());
        lead.setAni(campaignDto.getTollFreeNum());
        lead.setCallDirection(1L);
        lead.setUserGroup(user.getWorkGrp());
        lead.setCreateUser(user.getUserId());
        lead.setStatus(0L);
        String groupType = userService.getGroupType(user.getUserId());
        if("IN".equalsIgnoreCase(groupType)){
            lead.setOutbound(false);
        }
        else if("OUT".equalsIgnoreCase(groupType)){
            lead.setOutbound(true);
        }
            
        lead.setOwner(agent);
        lead.setTaskSourcType(CampaignTaskSourceType.ORDERTRACE.getIndex());
        Date beginDate = new Date();
        lead.setBeginDate(beginDate);
        
        Date endDate = DateUtil.addDays2Date(beginDate, 31);
        endDate = DateUtil.getEndOfDate(endDate);
        lead.setEndDate(endDate);
        
		return lead;
	}
	
	/** 
	 * <p>Title: queryPageList</p>
	 * <p>Description: </p>
	 * @param dataGridModel
	 * @param orderhistTrackTaskDto
	 * @return Map<String, Object>
	 * @see com.chinadrtv.erp.sales.core.service.OrderhistTrackTaskService#queryPageList(com.chinadrtv.erp.constant.DataGridModel, com.chinadrtv.erp.sales.core.dto.OrderhistTrackTaskDto)
	 */ 
	@Override
	public Map<String, Object> queryPageList(DataGridModel dataGridModel, OrderhistTrackTaskDto orderhistTrackTaskDto) {
		Map<String, Object> rsMap = new HashMap<String, Object>();
		
		Integer totalRow = orderhistTrackTaskDao.queryPageCount(orderhistTrackTaskDto);
		List<OrderhistTrackTaskDto> dtoList = orderhistTrackTaskDao.queryPageList(dataGridModel, orderhistTrackTaskDto);
		
		rsMap.put("total", totalRow);
		rsMap.put("rows", dtoList);
		
		return rsMap;
	}


}
