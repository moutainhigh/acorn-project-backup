/*
 * @(#)WilcomConnectedServiceImpl.java 1.0 2014年1月13日下午2:11:44
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.distribution.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.chinadrtv.erp.marketing.core.common.CampaignTaskSourceType;
import com.chinadrtv.erp.marketing.core.common.CampaignTaskSourceType2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.distribution.service.CallInitService;
import com.chinadrtv.erp.distribution.service.WilcomConnectedService;
import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.marketing.core.dto.LeadInteractionSearchDto;
import com.chinadrtv.erp.marketing.core.service.CampaignBPMTaskService;
import com.chinadrtv.erp.marketing.core.service.LeadService;
import com.chinadrtv.erp.model.Contact;
import com.chinadrtv.erp.model.PotentialContact;
import com.chinadrtv.erp.model.agent.TCallhist;
import com.chinadrtv.erp.model.marketing.Callback;
import com.chinadrtv.erp.model.marketing.CallbackLog;
import com.chinadrtv.erp.model.marketing.CallbackSpecification;
import com.chinadrtv.erp.model.marketing.Lead;
import com.chinadrtv.erp.model.marketing.LeadInteraction;
import com.chinadrtv.erp.model.marketing.LeadInteractionOrder;
import com.chinadrtv.erp.uc.constants.CallbackStatus;
import com.chinadrtv.erp.uc.constants.CallbackType;
import com.chinadrtv.erp.uc.dao.CallBackDao;
import com.chinadrtv.erp.uc.dao.CallHistDao;
import com.chinadrtv.erp.uc.dao.CallbackLogDao;
import com.chinadrtv.erp.uc.dao.TCallhistDao;
import com.chinadrtv.erp.uc.dto.CustomerDto;
import com.chinadrtv.erp.uc.dto.PhoneAddressDto;
import com.chinadrtv.erp.uc.dto.TCallHistDto;
import com.chinadrtv.erp.uc.service.ContactService;
import com.chinadrtv.erp.uc.service.PhoneService;
import com.chinadrtv.erp.uc.service.PotentialContactService;
import com.chinadrtv.erp.uc.service.impl.CallHistServiceImpl;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.model.GroupInfo;
import com.chinadrtv.erp.user.service.UserService;
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
 * @since 2014年1月13日 下午2:11:44 
 * 
 */
@Service
public class WilcomConnectedServiceImpl implements WilcomConnectedService {
	
	private static final Logger logger = LoggerFactory.getLogger(CallHistServiceImpl.class);
	
	@Autowired
	private CallHistDao callHistDao;
	@Autowired
	private CallBackDao callBackDao;
	@Autowired
	private CallbackLogDao callbackLogDao;
	@Autowired
	private CallInitService wilcomCallDetailService;
	@Autowired
	private UserService userService;
	@Autowired
	private ContactService contactService;
	@Autowired
	private PotentialContactService potentialContactService;
	@Autowired
	private PhoneService phoneService;
	@Autowired
	private LeadService leadService;
	@Autowired
	private TCallhistDao tCallhistDao;
	@Autowired
	private CampaignBPMTaskService campaignBPMTaskService;


	/** 
	 * <p>Title: queryIvrConnectedAvaliableQty</p>
	 * <p>Description: </p>
	 * @param searchDto
	 * @return Integer
	 * @see com.chinadrtv.erp.uc.service.CallHistService#queryIvrConnectedAvaliableQty(com.chinadrtv.erp.marketing.core.dto.LeadInteractionSearchDto)
	 */ 
	@Override
	public Integer queryIvrConnectedAvaliableQty(LeadInteractionSearchDto searchDto) {
		return tCallhistDao.queryIvrConnectedAvaliableQty(searchDto);
	}

	/** 
	 * <p>Title: assignToAgent</p>
	 * <p>Description: </p>
	 * @param liDto
	 * @param param
	 * @return
	 * @see com.chinadrtv.erp.uc.service.CallHistService#assignToAgent(com.chinadrtv.erp.marketing.core.dto.LeadInteractionSearchDto, java.util.List)
	 */ 
	@Override
	public synchronized Map<String, Object> assignToAgent(LeadInteractionSearchDto liDto, List<Map<String, Object>> agentUsers) throws Exception {
			
		Map<String, Object> rsMap = new HashMap<String, Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String batchId = sdf.format(new Date())+"0";

		List<TCallHistDto> dataList = tCallhistDao.queryIvrConnectedAvaliableList(liDto);
		
		//校验
		Integer products = dataList.size();
		Integer consumers = agentUsers.size();
		if(products == 0){
			throw new ServiceException("Invalid_Parameter", "没有可分配的数量");
		}
		if(consumers == 0){
			throw new ServiceException("Invalid_Parameter", "没有待分配的坐席");
		}
		
		int total = 0;
		for(Map<String, Object> userMap : agentUsers){
			Integer assignCount = Integer.parseInt(null==userMap.get("assignCount")? "0" : userMap.get("assignCount").toString());
			total += assignCount;
		}
		
		if(total>products){
			throw new ServiceException("Invalid_Parameter", "分配的总行数不能大于可分配量");
		}
		
		Integer assinedCount = 0;
		for(Map<String, Object> userMap : agentUsers){
			assinedCount += consumeCustomCount(userMap, dataList, batchId, liDto);
		}
		
		rsMap.put("assinedCount", assinedCount);
		rsMap.put("batchId", batchId);

		return rsMap;
	}
	
	
	private Integer consumeCustomCount(Map<String, Object> groupMap, List<TCallHistDto> dataList, 
			String batchId, LeadInteractionSearchDto liDto) throws ServiceException, ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		//分配数量
		Integer assignCount = Integer.parseInt(null == groupMap.get("assignCount") ? "0" : groupMap.get("assignCount").toString());
		//已分配数量
		Integer assignedCount = 0;
		//执行分配的次数，包括某个客户有任务跳过的
		Integer operatedCount = 0;
		
		for(int i=dataList.size()-1; i>=0; i--){
			TCallHistDto callHist = dataList.get(i);
			operatedCount ++;
			
			if(assignedCount < assignCount && operatedCount <= assignCount){
				String agentId = groupMap.get("userId").toString();
				String userGroup = groupMap.get("userGroup").toString();
				
				//数据去重
				CallbackSpecification sp = new CallbackSpecification();
				sp.setAcdGroup(callHist.getEcallGrp());
				sp.setDnis(callHist.getDnis());
				sp.setAni(callHist.getAni());
				sp.setCallDuration((liDto.getHighCallDuration() - liDto.getLowCallDuration()) + "");
				sp.setCallType(CallbackType.CONNECTED_WILCOM + "");
				sp.setStartDate(sdf.parse(liDto.getIncomingLowDate()));
				sp.setEndDate(sdf.parse(liDto.getIncomingHighDate()));
				
				Long callbackCount = callBackDao.findAllocatedCount(sp);
				if(callbackCount > 0) {
					continue;
				}
				 
				//保存数据
				this.assignSaveData(callHist, agentId, userGroup, batchId, liDto);
				
				assignedCount ++;
				dataList.remove(i);
			}else{
				break;
			}
		}
		
		return assignedCount;
	}
	
	
	private void assignSaveData(TCallHistDto callHistDto, String agentId, String userGroup, String batchId,
			LeadInteractionSearchDto liDto) throws ServiceException {
		AgentUser user = SecurityHelper.getLoginUser();
		String userId = user.getUserId();
		
		//创建客户
		CustomerDto customerDto = wilcomCallDetailService.createCustomer(user, callHistDto.getAni());
		
		Date ctiCreateDate = callHistDto.getCreateTime();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(ctiCreateDate);
		calendar.add(Calendar.MILLISECOND, Integer.valueOf(callHistDto.getCallLen()));
		
		//创建leadInteraction
		LeadInteraction li = wilcomCallDetailService.createLi(user, customerDto, callHistDto.getAni(), callHistDto.getDnis(), 
				callHistDto.getCallId(), callHistDto.getEcallGrp(), ctiCreateDate, calendar.getTime(),
                agentId, CampaignTaskSourceType.INCOMING.getIndex(),  CampaignTaskSourceType2.CONNECTED.getIndex(), -1);
		
		if(null == li.getLead()) {
			Lead lead = leadService.get(li.getLeadId());
			li.setLead(lead);
		}
		
		//创建Task、Callback
		Callback callback = this.adapter(li, customerDto, callHistDto);
		
		String assignee = agentId;
		callback.setUsrId(assignee);
		callback.setUserAssigner(userId);
		callback.setRdbusrId(assignee);
		
		callback.setUsrGrp(userGroup);
		callback.setGroupAssigner(userId);
		
		callback.setDbusrId(userId);
		callback.setDbdt(new Date());
		callback.setFirstusrId(agentId);
		callback.setFirstdt(new Date());
		callback.setOpusr(userId);
        
        callback.setTaskId(Long.parseLong(li.getBpmInstId()));
		callback.setAllocateCount(0L);
		callback.setBatchId(batchId);
		callback.setCaseId(callHistDto.getCaseId());
		callback.setAllocatedManual(true);
		
		callBackDao.saveOrUpdate(callback);
		
		CallbackLog cl = (CallbackLog) PojoUtils.convertDto2Pojo(callback, CallbackLog.class);
		cl.setBatchId(batchId);
		
		callbackLogDao.save(cl);
		
		//回填t_callhist表bpm_inst_id
		TCallhist tCallhist = tCallhistDao.get(callHistDto.getCaseId());
		tCallhist.setBpmInstId(li.getBpmInstId());
		tCallhistDao.saveOrUpdate(tCallhist);
	}

	/**
	 * <p></p>
	 * @param callHist
	 * @return
	 * @throws ServiceException 
	 */
	private Callback adapter(LeadInteraction li, CustomerDto customerDto, TCallHistDto callHist) throws ServiceException {
		AgentUser user = SecurityHelper.getLoginUser();
		String grpId = user.getWorkGrp();
		GroupInfo grp = userService.getGroupInfo(grpId);
		
		Callback cb = new Callback();
		String contactId = customerDto.getContactId();
		Lead lead = li.getLead();
		
		cb.setLeadInteractionId(li.getId());

		if(lead.getIspotential()==0 && null != contactId && !"".equals(contactId)){
			Contact contact = contactService.findById(contactId);
			cb.setName(contact.getName());
		}else{
			String potentialContactId = lead.getPotentialContactId();
			PotentialContact pc = potentialContactService.findById(new Long(potentialContactId));
			if(null != pc){
				cb.setName(pc.getName());
			}
			cb.setLatentcontactId(potentialContactId);
		}
		
		Set<LeadInteractionOrder> lioSet = li.getLeadInteractionOrders();
		String orderIds = "";
		for(LeadInteractionOrder lio : lioSet){
			StringBuffer sb = new StringBuffer();
			sb.append(lio.getOrderId() + ",");
			orderIds = sb.toString();
			if(orderIds.length()>2){
				orderIds = orderIds.substring(0, orderIds.length()-1);
			}
		}
		
		cb.setContactId(contactId);
		cb.setOrderId(orderIds);
		cb.setStatus(String.valueOf(CallbackStatus.NOT_HANDLE.getIndex()));
		cb.setRequiredt(li.getReserveDate());
		cb.setPriority("100");
		cb.setFlag("0");
		
		String ani = li.getAni();
		if(null!=ani && !"".equals(ani)){
			PhoneAddressDto paDto = phoneService.splicePhone(ani);
			cb.setPhn1(paDto.getPhn1());
			cb.setPhn2(paDto.getPhn2());
			cb.setPhn3(paDto.getPhn3());
		}
		
		cb.setRemark(li.getComments());
		cb.setMediaplanId(String.valueOf(lead.getCampaignId()));
		cb.setCrdt(new Date());
		cb.setDnis(li.getDnis());
		cb.setAreacode(grp.getAreaCode());
		cb.setType(String.valueOf(CallbackType.CONNECTED_WILCOM.getIndex()));
		cb.setAni(li.getAni());
		cb.setCaseId(callHist.getCaseId());
		cb.setAcdGroup(li.getAcdGroup());
		
		return cb;
	}

}
