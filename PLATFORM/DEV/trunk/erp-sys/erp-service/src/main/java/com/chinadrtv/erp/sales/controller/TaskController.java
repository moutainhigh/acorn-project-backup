/*
 * @(#)CustomerController.java 1.0 2013-5-22下午2:20:53
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.sales.controller;

import com.chinadrtv.erp.bpm.service.BpmProcessService;
import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.constant.SecurityConstants;
import com.chinadrtv.erp.core.service.NamesService;
import com.chinadrtv.erp.exception.ErpException;
import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.marketing.core.common.*;
import com.chinadrtv.erp.marketing.core.dto.*;
import com.chinadrtv.erp.marketing.core.exception.ErrorCode;
import com.chinadrtv.erp.marketing.core.exception.MarketingException;
import com.chinadrtv.erp.marketing.core.service.*;
import com.chinadrtv.erp.marketing.core.util.HttpUtil;
import com.chinadrtv.erp.model.SysMessage;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.model.marketing.Campaign;
import com.chinadrtv.erp.model.marketing.Lead;
import com.chinadrtv.erp.model.marketing.UserBpm;
import com.chinadrtv.erp.model.marketing.UserBpmTask;
import com.chinadrtv.erp.sales.core.constant.MessageType;
import com.chinadrtv.erp.sales.core.service.OrderhistService;
import com.chinadrtv.erp.sales.core.service.SysMessageService;
import com.chinadrtv.erp.sales.dto.AssignHistTask;
import com.chinadrtv.erp.sales.dto.AuditInfoDto;
import com.chinadrtv.erp.sales.dto.OrderInfo4AssistantReview;
import com.chinadrtv.erp.sales.service.AuditTaskProcessService;
import com.chinadrtv.erp.sales.service.LeadSyncWithTaskService;
import com.chinadrtv.erp.sales.service.OMSInteractionService;
import com.chinadrtv.erp.sales.service.OrderComparasionDetailService;
import com.chinadrtv.erp.sales.service.SalesTaskService;
import com.chinadrtv.erp.uc.service.CallbackService;
import com.chinadrtv.erp.uc.service.SmsService;
import com.chinadrtv.erp.user.aop.Mask;
import com.chinadrtv.erp.user.dao.AgentUserDao;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.service.UserService;
import com.chinadrtv.erp.user.util.SecurityHelper;
import com.chinadrtv.erp.util.DateUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.activiti.engine.ActivitiException;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import junit.framework.Assert;

@Controller
@RequestMapping(value="/task")
public class TaskController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
	
    @Autowired
    private CampaignBPMTaskService campaignBPMTaskService;
    
    @Autowired
    private ChangeRequestService changeRequestService;
    
    @Autowired
    private UserBpmTaskService userBpmTaskService;
    
    @Autowired
    private JobRelationexService jobRelationexService;
    
    @Autowired
    private SalesTaskService salesTaskService;
   
    @Autowired
    private AuditTaskProcessService auditTaskProcessService;
    
    @Autowired
    private OrderComparasionDetailService orderComparasionDetailService;
    
    @Autowired
    private AgentUserDao agentUserDao;
    
	@Autowired
	private BpmProcessService bpmProcessService;
	
	@Autowired
	private SysMessageService sysMessageService;
	
	@Autowired
	private SmsService smsService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CallbackService callbackService;
	
	@Autowired
	private LeadService leadService;
	
	@Autowired
	private OMSInteractionService omsInteractionService;

    @Autowired
    private ContactAssignHistService contactAssignHistService;
    
    @Autowired
    private OrderhistService orderhistService;
    
    @Autowired
    private LeadSyncWithTaskService leadSyncWithTaskService;

    @Autowired
    private NamesService namesService;
	
    @InitBinder
    public void InitBinder(ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
    }

    
    /***************************************营销任务***********************************************/
	@RequestMapping(value="/mytask")
	public ModelAndView myTask(@RequestParam(required = true, defaultValue = "") String contactId){
		ModelAndView mav = new ModelAndView("task/mytask");

		//initialize the campaign task related queries
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		String endDate = sdf.format(calendar.getTime());
		calendar.add(Calendar.MONTH, -3);
		String startDate = sdf.format(calendar.getTime());
		mav.addObject("campaignTaskTypes", CampaignTaskType.toList());
		mav.addObject("campaignTaskStatus", CampaignTaskStatus.toList());
		mav.addObject("campaignTaskSourceTypes", CampaignTaskSourceType.toList());
		mav.addObject("startDate", startDate);
		mav.addObject("endDate", endDate);

        mav.addObject("taskTaskTypes", JSONSerializer.toJSON(namesService.getAllNames("TSRTASKTYPE")));
		//TODOX fetch the user id from the session !
		AgentUser user = SecurityHelper.getLoginUser();
		String userId = user.getUserId();
		mav.addObject("userID", userId);
		if(StringUtils.isNotBlank(contactId)) {
			mav.addObject("customerID", contactId);
		}
		
		//initialize the audit task related queries
		mav.addObject("auditTaskTypes", AuditTaskType.toList());
		mav.addObject("auditTaskStatus", AuditTaskStatus.toList());
		mav.addObject("auditStartDate", startDate);
		mav.addObject("auditEndDate", endDate);
		mav.addObject("campaigns", findCampaigns());
		//TODOX fetch the user id from the session !
		String departmentNumber = user.getDepartment();
		String department = departmentNumber;
		try {
			department = userService.getDepartmentInfo(departmentNumber).getName();
		} catch (Exception e) {
			e.printStackTrace();
		}
		mav.addObject("department", department);
		
		//主管权限
		boolean isSupervisor = false;
/*		isSupervisor |= user.hasRole(SecurityConstants.ROLE_OUTBOUND_GROUP_MANAGER);
		isSupervisor |= user.hasRole(SecurityConstants.ROLE_INBOUND_GROUP_MANAGER);*/
		isSupervisor |= user.hasRole(SecurityConstants.ROLE_ASSIGN_TO_AGENT);
		mav.addObject("isSupervisor", isSupervisor);
		
		//部门经理权限
		boolean isDepartmentManager = false;
		isDepartmentManager |= user.hasRole(SecurityConstants.ROLE_INBOUND_MANAGER);
		isDepartmentManager |= user.hasRole(SecurityConstants.ROLE_OUTBOUND_MANAGER);
		isDepartmentManager |= user.hasRole(SecurityConstants.ROLE_COMPLAIN_MANAGER);
		if(isSupervisor) {
			isDepartmentManager = false;
		}
		mav.addObject("isDepartmentManager", isDepartmentManager);
		
		//数据分配权限
		boolean assignRights = isSupervisor | isDepartmentManager;
		mav.addObject("assignRights", assignRights);
		
		return mav;
	}

    @SuppressWarnings({ "rawtypes", "unchecked" })
	private List findCampaigns() {
        List campaigns = new ArrayList();
        Campaign campaign1 = new Campaign();
        campaign1.setId(1L);
        campaign1.setName("test1");
        Campaign campaign2 = new Campaign();
        campaign2.setId(2L);
        campaign2.setName("test2");
        campaigns.add(campaign1);
        campaigns.add(campaign2);
        return campaigns;
    }

    @RequestMapping(value="/myCampaignTask")
	public ModelAndView myCampaignTask(@RequestParam(required = true, defaultValue = "") String contactId){
		ModelAndView mav = new ModelAndView("task/myCampaignTask");

		//initialize the campaign task related queries
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		String endDate = sdf.format(calendar.getTime());
		calendar.add(Calendar.MONTH, -3);
		String startDate = sdf.format(calendar.getTime());
		mav.addObject("campaignTaskTypes", CampaignTaskType.toList());
		mav.addObject("campaignTaskStatus", CampaignTaskStatus.toList());
		mav.addObject("startDate", startDate);
		mav.addObject("endDate", endDate);
		//TODOX fetch the user id from the session !
		AgentUser user = SecurityHelper.getLoginUser();
		String userId = user.getUserId();
		mav.addObject("userID", userId);
		if(StringUtils.isNotBlank(contactId)) {
			mav.addObject("customerID", contactId);
		}
		
		return mav;
	}
	
	/**
	 * <p>查询我的任务</p>
	 * @param dataGrid
	 * @param obCustomerDto
	 * @return Map<String, Object>
	 */
	@RequestMapping(value = "/queryCampaignTask")
	@ResponseBody
	public Map<String, Object> queryCampaignTask(DataGridModel dataGrid, CampaignTaskDto campaignTaskDto){
		Map<String, Object> pageMap = null;
		try {
			pageMap = campaignBPMTaskService.queryMarketingTask(campaignTaskDto, dataGrid);
		} catch (MarketingException e) {
			logger.error("error occurs!",e);
		}
		return pageMap;
	}
	
	@RequestMapping(value = "/queryUnstartedCampaignTask")
	@ResponseBody
	public Map<String, Object> queryUnstartedCampaignTask(DataGridModel dataGrid, CampaignTaskDto campaignTaskDto){
		Map<String, Object> pageMap = null;
		try {
			pageMap = campaignBPMTaskService.queryUnStartedCampaignTask(campaignTaskDto, dataGrid);
		} catch (MarketingException e) {
			logger.error("error occurs!",e);
		}
		return pageMap;
	}
	
	/**
	 * <p>取消我的营销任务</p>
	 * @param tid 待取消的任务编号
	 * @param remark 备注
	 * @return Map<String, Object>
	 */
	@RequestMapping(value = "/cacelCampaignTask")
	@ResponseBody
	public String cancelCampaignTask(
			@RequestParam(required = true, defaultValue = "") String remark,
			@RequestParam(required = true, defaultValue = "") String instId
			){
		
		AgentUser user = SecurityHelper.getLoginUser();
		String userID = user.getUserId();
		try {
			bpmProcessService.cancelInstance(instId, remark);
		} catch (ActivitiException ae) {
			logger.warn("Activiti errors!", ae);
		}
		CampaignTaskVO cvo = campaignBPMTaskService.queryMarketingTask(instId);
		if(cvo != null) {
			campaignBPMTaskService.cancelTask(remark, instId, null, userID);
		}
		
		return "success";
	}
	
	@RequestMapping(value = "/isTaskFinished")
	@ResponseBody
	public void isTaskFinished(
			@RequestParam(required = true, defaultValue = "0") String instId,
			HttpServletResponse response
			) throws MarketingException {
		boolean isFinished = false;
		isFinished = campaignBPMTaskService.isTaskFinished(instId);
		HttpUtil.ajaxReturn(response, jsonBinder.toJson(isFinished), "application/json");
	}
	
	/***************************************审批任务***********************************************/
	
	/**
	 * <p>查询审批任务</p>
	 * @param dataGrid
	 * @param obCustomerDto
	 * @return Map<String, Object>
	 */
	@RequestMapping(value = "/queryAuditTask")
	@ResponseBody
	public Map<String, Object> queryAuditTask(DataGridModel dataGrid, ApprovingTaskDto approvingTaskDto){
		Map<String, Object> pageMap = new HashMap<String, Object>();
		//TODOX 从ldap检查为普通座席登录还是主管登录
		AgentUser user = null;
		try{
			user = SecurityHelper.getLoginUser();
			if(user == null){
				logger.error("queryAuditTask user is null");
			}
		}catch (Exception e) {
			logger.error("queryAuditTask SecurityHelper.getLoginUser()");
			e.printStackTrace();
		}
		
//		String userId = user.getUserId();
		boolean isOutboundDepartmentManager = user.hasRole(SecurityConstants.ROLE_OUTBOUND_MANAGER);
		boolean isInboundDepartmentManager = user.hasRole(SecurityConstants.ROLE_INBOUND_MANAGER);
		boolean isOutboundGroupManager = user.hasRole(SecurityConstants.ROLE_OUTBOUND_GROUP_MANAGER);
		boolean isInboundGroupManager = user.hasRole(SecurityConstants.ROLE_INBOUND_GROUP_MANAGER);
		boolean isComplainManager = user.hasRole(SecurityConstants.ROLE_COMPLAIN_MANAGER);
		if(isOutboundDepartmentManager || isInboundDepartmentManager || isComplainManager) {
			approvingTaskDto.setDepartment(user.getDepartment());
		} else if(isOutboundGroupManager || isInboundGroupManager) {
			approvingTaskDto.setDepartment(user.getDepartment());
			approvingTaskDto.setWorkGrp(user.getWorkGrp());
		}
		try {
			if(isOutboundDepartmentManager || isInboundDepartmentManager 
					|| isOutboundGroupManager ||isInboundGroupManager || isComplainManager) {
				pageMap = changeRequestService.queryChangeReqeust(approvingTaskDto, dataGrid);
			} else {
/*				approvingTaskDto.setDepartment(user.getDepartment());
				approvingTaskDto.setOrderCreatedUserID(userId);
				approvingTaskDto.setTaskType(String.valueOf(AuditTaskType.ORDERCANCEL.getIndex()));
				pageMap = changeRequestService.queryOrderCancelReqeust(approvingTaskDto, dataGrid);*/
				pageMap.put("rows", new ArrayList<Object>());
				pageMap.put("total", 0);
			}
		} catch (MarketingException e) {
			
		}
		return pageMap;
	}
	
	@RequestMapping(value = "/orderExist")
	@ResponseBody
	public void orderExist(
			@RequestParam(required = true, defaultValue = "0") String id,
			HttpServletResponse response
			) throws MarketingException {
		boolean isExist = true;
		isExist = orderComparasionDetailService.isOrderExist(id);
		HttpUtil.ajaxReturn(response, jsonBinder.toJson(isExist), "application/json");
	}
	
	@RequestMapping(value = "/cancelOrderRelatedProcess")
	@ResponseBody
	public void cancelOrderRelatedProcess(
			@RequestParam(required = true, defaultValue = "0") String id,
			@RequestParam(required = false, defaultValue = "") String remark,
			HttpServletResponse response
			) throws MarketingException {
		changeRequestService.cancelUnCompletedOrderChangeRequest(id);
		sysMessageService.cancelOrderMessage(id);
	}
	
	/**
	 * 处理审批任务,三种不同的任务类型分别对应三种处理逻辑
	 * processAuditTask
	 * @param auditTaskType 审批任务类型
	 * @param batchId 修改批次
	 * @return 
	 * Map<String,Object>
	 * @throws MarketingException 
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping(value = "/processAuditTaskAjax")
	@ResponseBody
	@Mask
	public ModelAndView processAuditTask(
			@RequestParam(required = true, defaultValue = "0") String auditTaskType,
			@RequestParam(required = true, defaultValue = "0") String batchId,
			@RequestParam(required = true, defaultValue = "0") String crUser,
			@RequestParam(required = true, defaultValue = "0") String id,
			@RequestParam(required = true, defaultValue = "0") String isConfirmAudit,
			@RequestParam(required = true, defaultValue = "0") String source,
			HttpServletResponse response
			) throws MarketingException {
		ModelAndView mav = new ModelAndView();
		AgentUser user = SecurityHelper.getLoginUser();
		boolean isManager = false;
		boolean isOutboundDepartmentManager = user.hasRole(SecurityConstants.ROLE_OUTBOUND_MANAGER);
		boolean isInboundDepartmentManager = user.hasRole(SecurityConstants.ROLE_INBOUND_MANAGER);
		boolean isOutboundGroupManager = user.hasRole(SecurityConstants.ROLE_OUTBOUND_GROUP_MANAGER);
		boolean isInboundGroupManager = user.hasRole(SecurityConstants.ROLE_INBOUND_GROUP_MANAGER);
		boolean isComplainManager = user.hasRole(SecurityConstants.ROLE_COMPLAIN_MANAGER);
		if(isOutboundDepartmentManager || isOutboundGroupManager
				|| isInboundDepartmentManager || isInboundGroupManager || isComplainManager) {
			isManager = true;
		}
		List<UserBpmTask> userBpmTasks = userBpmTaskService.queryApprovingTaskByBatchID(batchId);
		mav.addObject("isManager", isManager);
		mav.addObject("auditTaskType", auditTaskType);
		mav.addObject("crUser", crUser);
		mav.addObject("id", id);
		mav.addObject("batchId", batchId);
		mav.addObject("source", source);

		if(String.valueOf(AuditTaskType.CONTACTCHANGE.getName()).equals(auditTaskType)) {
			mav.setViewName("inbound/bpmView");
			mav.addObject("isConfirmAudit", isConfirmAudit);
		} else if(String.valueOf(AuditTaskType.CONTACTADD.getName()).equals(auditTaskType)) {
			mav.setViewName("inbound/addContactBpmView");
			mav.addObject("isConfirmAudit", isConfirmAudit);
		} else if(String.valueOf(AuditTaskType.ORDERCHANGE.getName()).equals(auditTaskType)) {
			mav.setViewName("myorder/viewaudit");
			mav.addObject("isConfirmAudit", isConfirmAudit);
	        orderComparasionDetailService.instancializeOrderChange(mav, batchId, false);
		} else if(String.valueOf(AuditTaskType.ORDERADD.getName()).equals(auditTaskType)) {
			mav.setViewName("myorder/addOrderViewaudit");
			mav.addObject("isConfirmAudit", isConfirmAudit);
	        orderComparasionDetailService.instancializeOrderChange(mav, batchId, true);
		} else if(String.valueOf(AuditTaskType.ORDERCANCEL.getName()).equals(auditTaskType)) {
			boolean isUnassigned = false;
			for(UserBpmTask ubt : userBpmTasks) {
				if(String.valueOf(AuditTaskStatus.UNASSIGNED.getIndex()).equals(ubt.getStatus())) {
					isUnassigned = true;
					break;
				}
			}
			mav.setViewName("task/orderCancelAudit");
			mav.addObject("isUnassigned", isUnassigned);
			mav.addObject("userBpmTasks", userBpmTasks);
		}

        mav.addObject("saleTypes", JSONSerializer.toJSON(namesService.getAllNames("SALETYPE")));

		response.setContentType("text/html;charset=UTF-8");
		return mav;
	}
	
	@RequestMapping(value = "/updateAuditTaskByBatchId")
	@ResponseBody
	public void updateAuditTaskByBatchId(HttpServletResponse response,
			@RequestParam(required = true, defaultValue = "0") String batchId,
			@RequestParam(required = true, defaultValue = "0") String comment,
			@RequestParam(required = true, defaultValue = "0") String action,
			@RequestParam(required = true, defaultValue = "0") String id,
			@RequestParam(required = true, defaultValue = "0") String appliedUser,
			@RequestParam(required = true, defaultValue = "0") String busiType
			) {
		List<UserBpmTask> userBpmTasks = userBpmTaskService.queryApprovingTaskByBatchID(batchId);
		List<AuditInfoDto> auditInfoList = new ArrayList<AuditInfoDto>();
		for(UserBpmTask ubt : userBpmTasks) {
			AuditInfoDto aInfo = new AuditInfoDto();
			aInfo.setAppliedUser(appliedUser);
			aInfo.setComment(comment);
			aInfo.setId(id);
			aInfo.setStatus(action);
			aInfo.setBusiType(busiType);
			aInfo.setInstId(ubt.getBpmInstID());
			auditInfoList.add(aInfo);
		}
		AuditInfoDto[] auditInfos = new AuditInfoDto[auditInfoList.size()];
		auditInfos = (AuditInfoDto[])auditInfoList.toArray(auditInfos);
		updateAuditTaskBatch(response,auditInfos);
	}
	
	@RequestMapping(value = "/updateAuditTaskBatch", method = RequestMethod.POST)
	@ResponseBody
	public void updateAuditTaskBatch(HttpServletResponse response,
			@RequestBody AuditInfoDto[] auditInfos) {
		String message = "fail";
		UserBpmTask uTask = null;
		if(auditInfos == null || auditInfos.length==0) {
			message="没有流程可审批";
		} else {
			boolean result = false;
			boolean isOrderNewAdded = false;
			try {
				try {
					if(String.valueOf(AuditTaskType.ORDERCHANGE.getName()).equals(auditInfos[0].getBusiType())
							|| String.valueOf(AuditTaskType.ORDERADD.getName()).equals(auditInfos[0].getBusiType())) {
						Order order = orderhistService.getOrderHistByOrderid(auditInfos[0].getId());
						if(order.getCheckResult() != null && order.getCheckResult()==1) {
							isOrderNewAdded = true;
						}
					}
				} catch(Exception e) {
					logger.error("判断订单是否新增失败", e);
				}
				
				//检测流程状态，如果已批，则提示重新加载
				if(auditInfos != null && auditInfos.length > 0) {
					for(AuditInfoDto auditInfoDto : auditInfos) {
						uTask = userBpmTaskService.queryTaskByInstId(auditInfoDto.getInstId());
						if(!"0".equals(uTask.getStatus())) {
							throw new MarketingException("流程已被其它主管处理",ErrorCode.StatusNotSynced);
						}
					}
				}
				
				result = auditTaskProcessService.updateAuditTaskBatch(auditInfos);
				//更新修改批次状态
				if(auditInfos != null && auditInfos.length > 0) {
						UserBpmTask task = userBpmTaskService.queryTaskByInstId(auditInfos[0].getInstId());
						String batchId = task.getBatchID();
						changeRequestService.syncBatchStatus(batchId);
				}
				//发送消息及通知
				boolean isAllApproved = true;
				boolean isAllProcessed = true;
				StringBuilder comments = new StringBuilder();
				if(auditInfos != null && auditInfos.length > 0) {
					for(AuditInfoDto auditInfoDto : auditInfos) {
						if("2".equals(auditInfoDto.getStatus())) {
							isAllApproved = false;
							comments.append(auditInfoDto.getComment()).append("-");
						}
						if(StringUtils.isBlank(auditInfoDto.getStatus())) {
							isAllProcessed = false;
						}
					}
				}
				uTask = userBpmTaskService.queryTaskByInstId(auditInfos[0].getInstId());
				UserBpm userBpm = changeRequestService.queryApprovingTaskById(uTask.getBatchID());
				String id = "";
				if(!isAllApproved) {
					//没有全部批准，发送驳回消息通知座席
					SysMessage sMessage = new SysMessage();
					sMessage.setReceiverId(userBpm.getCreateUser());
					sMessage.setCreateUser(userBpm.getCreateUser());
					sMessage.setRecivierGroup(userService.getUserGroup(userBpm.getCreateUser()));
					sMessage.setCreateDate(new Date());
					sMessage.setDepartmentId(userService.getDepartment(userBpm.getCreateUser()));
					if(String.valueOf(AuditTaskType.CONTACTCHANGE.getIndex()).equals(userBpm.getBusiType())) {
						sMessage.setSourceTypeId(String.valueOf(MessageType.MODIFY_CONTACT_REJECT.getIndex()));
						id = userBpm.getContactID();
					} else if(String.valueOf(AuditTaskType.ORDERCHANGE.getIndex()).equals(userBpm.getBusiType())) {
						id = userBpm.getOrderID();
						sMessage.setSourceTypeId(String.valueOf(MessageType.MODIFY_ORDER_REJECT.getIndex()));
					} else if(String.valueOf(AuditTaskType.ORDERCANCEL.getIndex()).equals(userBpm.getBusiType())) {
						id = userBpm.getOrderID();
						sMessage.setSourceTypeId(String.valueOf(MessageType.CANCEL_ORDER_REJECT.getIndex()));
					} else if(String.valueOf(AuditTaskType.ORDERADD.getIndex()).equals(userBpm.getBusiType())) {
						id = userBpm.getOrderID();
						sMessage.setSourceTypeId(String.valueOf(MessageType.ADD_ORDER_REJECT.getIndex()));
					} else if(String.valueOf(AuditTaskType.CONTACTADD.getIndex()).equals(userBpm.getBusiType())) {
						id = userBpm.getContactID();
						sMessage.setSourceTypeId(String.valueOf(MessageType.ADD_CONTACT_REJECT.getIndex()));
					}
					sMessage.setContent(id);
					sysMessageService.addMessage(sMessage);
				} else {
					//取消订单，若主管批准通过，取消所有消息提醒
					if(String.valueOf(AuditTaskType.ORDERCANCEL.getIndex()).equals(userBpm.getBusiType())) {
						sysMessageService.cancelOrderMessage(userBpm.getOrderID());
					}
				}
				//如果全部处理，通知消息发送端，不要再发送定时消息通知
				if(isAllProcessed) {
					MessageType mType = MessageType.MODIFY_ORDER;
					if(String.valueOf(AuditTaskType.CONTACTCHANGE.getIndex()).equals(userBpm.getBusiType())) {
						id = userBpm.getContactID();
						mType = MessageType.MODIFY_CONTACT;
					} else if(String.valueOf(AuditTaskType.ORDERCHANGE.getIndex()).equals(userBpm.getBusiType())) {
						id = userBpm.getOrderID();
						mType = MessageType.MODIFY_ORDER;
					} else if(String.valueOf(AuditTaskType.ORDERCANCEL.getIndex()).equals(userBpm.getBusiType())) {
						id = userBpm.getOrderID();
						mType = MessageType.CANCEL_ORDER;
					} else if(String.valueOf(AuditTaskType.ORDERADD.getIndex()).equals(userBpm.getBusiType())) {
						id = userBpm.getOrderID();
						mType = MessageType.ADD_ORDER;
					} else if(String.valueOf(AuditTaskType.CONTACTADD.getIndex()).equals(userBpm.getBusiType())) {
						id = userBpm.getContactID();
						mType = MessageType.ADD_CONTACT;
					}
					sysMessageService.handleMessage(mType, id, userBpm.getCreateDate());
				}
				if(isAllApproved && isOrderNewAdded) {
					List<UserBpm> uncompletedBatches = changeRequestService.queryUnCompletedOrderChangeRequest(userBpm.getOrderID());
					if(uncompletedBatches == null || uncompletedBatches.size() < 1) {
						//新增订单批准通过时才发送短信
						Order order = new Order();
						order.setOrderid(userBpm.getOrderID());
                        smsService.sendOrderMessage(SecurityHelper.getLoginUser(), order);
						if(String.valueOf(AuditTaskType.ORDERCHANGE.getName()).equals(auditInfos[0].getBusiType())) {
							orderhistService.auditResultOnOrderCreate(auditInfos[0].getId(), true, 
									SecurityHelper.getLoginUser().getUserId());
						}
					}

				}
				
			} catch (MarketingException e) {
				if(e.getEc()==ErrorCode.StatusNotSynced) {
					message=e.getEc().getName();
				} else {
					message = "审批错误."+e.getEc().getName();
				}
			} catch (ErpException e) {
				message = "审批错误. "+(e.getMessage()!=null?e.getMessage():"");
				try {
					if (String.valueOf(AuditTaskType.ORDERCANCEL.getName()).equals(auditInfos[0].getBusiType())) {
//						tasks = userBpmTaskService.queryApprovingTaskByInstId(auditInfos[0].getInstId());
						changeRequestService.updateChangeRequestStatus(uTask.getBatchID(), String.valueOf(AuditTaskStatus.CLOSED.getIndex()));
						userBpmTaskService.updateStatus(SecurityHelper.getLoginUser().getUserId(), auditInfos[0].getInstId(), "", 
								String.valueOf(AuditTaskStatus.CLOSED.getIndex()), true);
					}
				} catch(Exception ei) {
					logger.error("",ei);
				}
			} catch (Exception e) {
				message = "系统异常，更新失败，请联系技术人员排查";
			}
			if(result) {
				message = "操作成功";
			}
		}
		HttpUtil.ajaxReturn(response, jsonBinder.toJson(message), "application/json");
	}
	
	@RequestMapping(value = "/closeAuditTask")
	public void closeAuditTask(HttpServletResponse response,
			@RequestParam(required = true) String batchId) {
		String message = "fail";
		try {
			//关闭流程
			changeRequestService.closeChangeRequestStatus(batchId);
			//同步批次
			changeRequestService.syncBatchStatus(batchId);
			//处理消息
			UserBpm ub = changeRequestService.queryApprovingTaskById(batchId);
			String id = "";
			MessageType mType = MessageType.MODIFY_ORDER_REJECT;
			if(String.valueOf(AuditTaskType.CONTACTCHANGE.getIndex()).equals(ub.getBusiType())) {
				id=ub.getContactID();
				mType = MessageType.MODIFY_CONTACT_REJECT;
			} else if(String.valueOf(AuditTaskType.ORDERCHANGE.getIndex()).equals(ub.getBusiType())) {
				id=ub.getOrderID();
				mType = MessageType.MODIFY_ORDER_REJECT;
			} else if(String.valueOf(AuditTaskType.ORDERCANCEL.getIndex()).equals(ub.getBusiType())) {
				id=ub.getOrderID();
				mType = MessageType.CANCEL_ORDER_REJECT;
			} else if(String.valueOf(AuditTaskType.ORDERADD.getIndex()).equals(ub.getBusiType())) {
				id=ub.getOrderID();
				mType = MessageType.ADD_ORDER_REJECT;
			} else if(String.valueOf(AuditTaskType.CONTACTADD.getIndex()).equals(ub.getBusiType())) {
				id=ub.getContactID();
				mType = MessageType.ADD_CONTACT_REJECT;
			}
			sysMessageService.handleMessage(mType, id, ub.getCreateDate());

			if(String.valueOf(AuditTaskType.ORDERCHANGE.getIndex()).equals(ub.getBusiType())) {
				Order order = orderhistService.getOrderHistByOrderid(ub.getOrderID());
				boolean isOrderNewAdded = false;
				if (order.getCheckResult() != null && order.getCheckResult() == 1) {
					isOrderNewAdded = true;
				}
				if(isOrderNewAdded) {
					List<UserBpm> uncompletedBatches = changeRequestService.queryUnCompletedOrderChangeRequest(ub.getOrderID());
					if(uncompletedBatches == null || uncompletedBatches.size() < 1) {
						orderhistService.auditFinishOnOrderModify(ub.getOrderID(), false, SecurityHelper.getLoginUser().getUserId());
						Order orderTmp = new Order();
						orderTmp.setOrderid(ub.getOrderID());
                        smsService.sendOrderMessage(SecurityHelper.getLoginUser(), orderTmp);
					}
				}
			}
			message = "操作成功";
		} catch (Exception e) {
			logger.error("关闭流程失败", e);
		}
		HttpUtil.ajaxReturn(response, jsonBinder.toJson(message), "application/json");
	}
	
/***************************************取数***********************************************/
	
	/**
	 * <p>查询取数</p>
	 * @return Map<String, Object>
	 */
	@RequestMapping(value = "/queryjobRelationex")
	@ResponseBody
	public List<JobsRelationex> queryjobRelationex(){
		List<JobsRelationex> jobList = null;
		AgentUser user = SecurityHelper.getLoginUser();
		String userId = user.getUserId();
		String userType = agentUserDao.queryAgentUserType(userId);
		jobList = jobRelationexService.queryJobRelationex(userId, userType, "");
		
		return jobList;
	}
	
	/**
	 * <p>查询取数</p>
	 * @return Map<String, Object>
	 * @throws MarketingException 
	 */
	@RequestMapping(value = "/assignHist")
	@ResponseBody
	public Object assignHist(@RequestParam(required = true, defaultValue = "") String jobId) {
		AgentUser user = SecurityHelper.getLoginUser();
		AssignHistTask assignHistTask = new AssignHistTask();
		try {
			salesTaskService.tryAssignHist(user, jobId, assignHistTask);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return assignHistTask;
	}
	
	/**
	 * <p>查询取数</p>
	 * @return Map<String, Object>
	 * @throws MarketingException 
	 */
	@RequestMapping(value = "/assignHist4PerfTest")
	@ResponseBody
	public Object assignHist4PerfTest(@RequestParam(required = true, defaultValue = "") String jobId) {
		AgentUser user = SecurityHelper.getLoginUser();
		AssignHistTask assignHistTask = new AssignHistTask();
		try {
			salesTaskService.assignHist(user, jobId, assignHistTask);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return assignHistTask;
	}
	
	/**
	 * <p>查询电话,仅限没有客户id的任务，需要先根据电话定位客户</p>
	 * @return Map<String, Object>
	 * @throws MarketingException 
	 */
	@RequestMapping(value = "/queryPhone")
	@ResponseBody
	public String queryPhone(@RequestParam(required = true, defaultValue = "") String instId) {
		String phoneNo = callbackService.queryPhoneByTaskId(instId);
		return phoneNo;
	}
	
	/**
	 * <p>更新任务关联客户</p>
	 * @return Map<String, Object>
	 * @throws MarketingException 
	 */
	@RequestMapping(value = "/updateContact")
	@ResponseBody
	public String updateContact(@RequestParam(required = false) String instId,
	                         @RequestParam(required = false) String contactId,
	                         @RequestParam(required = false, defaultValue = "false") boolean isPotential) {
		String result = "success";
		if(StringUtils.isNotBlank(instId) && StringUtils.isNotBlank(contactId)) {
			int number = leadService.updateContact(instId, contactId, isPotential);
			if(number == 0) {
				result = "error";
			}
		}
		return result;
	}
	
	@RequestMapping(value = "/querySourceType")
	@ResponseBody
	public void querySourceType(@RequestParam(required = true) String instId,
            HttpServletResponse response) throws IOException, ParseException {
		Long sourceType = campaignBPMTaskService.queryInst(instId).getSourceType();
		response.setContentType("text/json;charset=UTF-8");
		response.getWriter().print(sourceType);
	}
	/**
	 * <p>更新任务关联客户</p>
	 * @return Map<String, Object>
	 * @throws IOException 
	 * @throws ParseException 
	 * @throws MarketingException 
	 */
	@RequestMapping(value = "/queryDistributionChangeQuery")
	@ResponseBody
	public void queryDistributionChangeQuery(@RequestParam(required = true) String startDate,
	                         @RequestParam(required = true) String endDate,
	                         @RequestParam(required = false, defaultValue = "true") boolean isEms,
	                         @RequestParam(required = true) int pageNo,
	                         @RequestParam(required = true) int pageSize,
	                         HttpServletResponse response) throws IOException, ParseException {
		String result = "";
		int start = pageNo * pageSize;
		int end = (pageNo + 1) * pageSize;
		if (StringUtils.isNotBlank(endDate)) {
			endDate = DateUtil.addDays(endDate, 1);
		}
		List<OrderInfo4AssistantReview> insts = omsInteractionService.viewDistributionChangeQuery(startDate, endDate, isEms, start, end);
		int total = userBpmTaskService.queryManagerApprovedEmsChangeProcessCount(startDate, endDate, isEms, start, end);
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("rows", insts);
		jsonObj.put("total", total);
		result = jsonObj.toString();
		response.setContentType("text/json;charset=UTF-8");
		response.getWriter().print(result);
	}
	
	/**
	 * <p>物流 助理 返回处理结果</p>
	 * @return Map<String, Object>
	 * @throws Exception 
	 * @throws MarketingException 
	 */
	@RequestMapping(value = "/action")
	@ResponseBody
	public boolean action(@RequestParam(required = true) int actionType,
	                         @RequestParam(required = true) String instId,
	                         @RequestParam(required = true) String orderId) throws Exception {
		boolean result = false;
		try {
			result = omsInteractionService.action(actionType, instId, orderId, null);
		} catch (Exception e) {
			throw e;
		}
		return result;
	}
	
	/**
	 * <p>物流 助理 批量返回处理结果</p>
	 * @return Map<String, Object>
	 * @throws IOException 
	 * @throws MarketingException 
	 * @return 1.正常处理    2.部分错误  3.全部错误   4.输入错误
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/batchAction",method = RequestMethod.POST)
	@ResponseBody
	public void batchAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
		JSONObject bigJsonObj = new JSONObject();

		boolean result = true;
		int numOfProcessingSuc = 0;
		String[] ids = null;
		String idsString = request.getParameter("ids");
		if(StringUtils.isNotBlank(idsString)) {
			ids = idsString.split(",");
		}
		String op = request.getParameter("op");
		String[] orderIds = null;
		String orderIdsString = request.getParameter("orderIds");
		if(StringUtils.isNotBlank(orderIdsString)) {
			orderIds = orderIdsString.split(",");
		}
		
		String cpString = request.getParameter("designedEntityIds");
		String[] cps = null;
		if(StringUtils.isNotBlank(cpString)) {
			cps = cpString.split(",");
		}
		
		String whString = request.getParameter("designedWarehouseIds");
		String[] whs = null;
		if(StringUtils.isNotBlank(whString)) {
			whs = whString.split(",");
		}
		
		String userId = request.getParameter("userId");
		
		if(ids != null && orderIds != null && cps != null && ids.length > 0 && ids.length == orderIds.length 
				&& cps.length == orderIds.length) {
			JSONArray jsonArray = new JSONArray();
			for(int i = 0; i < ids.length; i++) {
				JSONObject smallJsonObj = new JSONObject();
				try {
					result = omsInteractionService.action(Integer.valueOf(op), ids[i], orderIds[i], cps[i]);
					try {
						//同步批次状态
						UserBpmTask task = userBpmTaskService.queryTaskByInstId(ids[i]);
						String batchId = task.getBatchID();
						UserBpm userBpm = changeRequestService.queryApprovingTaskById(batchId);
						changeRequestService.syncBatchStatus(batchId);
						Order order = orderhistService.getOrderHistByOrderid(userBpm.getOrderID());
						boolean isOrderNewAdded = false;
						if (order.getCheckResult() != null && order.getCheckResult() == 1) {
							isOrderNewAdded = true;
						}
						if(isOrderNewAdded) {
							AgentUser aUser = new AgentUser();
							aUser.setUserId(userBpm.getCreateUser());
							aUser.setName(userBpm.getCreateUser());
							List<UserBpm> uncompletedBatches = changeRequestService.queryUnCompletedOrderChangeRequest(orderIds[i]);
							if(uncompletedBatches == null || uncompletedBatches.size() < 1) {
								orderhistService.auditFinishOnOrderModify(orderIds[i], true, userId);
								Order orderTmp = new Order();
								orderTmp.setOrderid(orderIds[i]);
                                smsService.sendOrderMessage(aUser, orderTmp);
							}
						}
					} catch(Exception e) {
						logger.error("批次或者发送短信失败", e);
					}
					smallJsonObj.put("msg", "处理成功");
					smallJsonObj.put("status", "1");
					numOfProcessingSuc++;
				} catch (Exception e) {
					smallJsonObj.put("msg", e.getMessage());
					smallJsonObj.put("status", "2");
					result = false;
				} finally {
					smallJsonObj.put("id", ids[i]);
					smallJsonObj.put("orderId", orderIds[i]);
					jsonArray.add(smallJsonObj);
				}
			}
			if(result) {
				bigJsonObj.put("status", "1");
				bigJsonObj.put("msg", "正常处理");
			} else if(!result && numOfProcessingSuc > 0){
				bigJsonObj.put("status", "2");
				bigJsonObj.put("msg", "部分错误 ");
				bigJsonObj.put("detail", jsonArray);
			} else {
				bigJsonObj.put("status", "3");
				bigJsonObj.put("msg", "全部错误 ");
				bigJsonObj.put("detail", jsonArray);
			}

		} else {
			bigJsonObj.put("status", "4");
			bigJsonObj.put("msg", "输入错误");
		}
		response.setContentType("text/json;charset=UTF-8");
		response.getWriter().print(bigJsonObj.toString());
	}

    /**
     * <p>查询分配历史</p>
     */
    @SuppressWarnings("rawtypes")
	@RequestMapping(value = "/findContactAssignHist")
    public void findContactAssignHist(HttpServletResponse response,
                                      @RequestParam(required = true) String campaignId,
                                      @RequestParam(required = true) String batchCode,
                                      @RequestParam(required = true) String assignStartTime,
                                      @RequestParam(required = true) String assignEndTime,
                                      @RequestParam(required = true) String status,
                                      @RequestParam(required = true, defaultValue = "1") int page,
                                      @RequestParam Integer rows) {
        SearchContactAssignHistDto histDto = new SearchContactAssignHistDto();
        histDto.setCampaignId(campaignId);
        histDto.setBatchCode(batchCode);
        histDto.setStatus(status);
        histDto.setStrAssignStartTime(assignStartTime);
        histDto.setStrAssignEndTime(assignEndTime);

        DataGridModel dataGridModel = new DataGridModel();
        dataGridModel.setPage(page);
        dataGridModel.setRows(rows);
        Map map = contactAssignHistService.findPageList(histDto, dataGridModel);
        HttpUtil.ajaxReturn(response, jsonBinder.toJson(map), "application/json");
    }

    /**
     * <p>导出分配历史</p>
     */
    @RequestMapping(value = "/exportHist", method = RequestMethod.POST)
    public void exportHist(HttpServletResponse response,
                                      @RequestParam(required = true) String campaignId,
                                      @RequestParam(required = true) String batchCode,
                                      @RequestParam(required = true) String assignStartTime,
                                      @RequestParam(required = true) String assignEndTime,
                                      @RequestParam(required = true) String status) {
        SearchContactAssignHistDto histDto = new SearchContactAssignHistDto();
        histDto.setCampaignId(campaignId);
        histDto.setBatchCode(batchCode);
        histDto.setStatus(status);
        histDto.setStrAssignStartTime(assignStartTime);
        histDto.setStrAssignStartTime(assignEndTime);

        HSSFWorkbook book  = contactAssignHistService.generateExportFile(histDto);
        response.setContentType("application/ms-excel");
        try {
            response.setHeader( "Content-Disposition" ,
                    "attachment;filename="+new String("导出Excel.xls".getBytes(),"utf-8")) ;
            OutputStream ouputStream = response.getOutputStream();
            book.write(ouputStream);
            ouputStream.flush();
            ouputStream.close();
        } catch (Exception e) {
            logger.error("导出任务分配历史出错", e);
        }
    }
    
    @RequestMapping(value = "/syncLeadWithTask")
	@ResponseBody
	public void syncLeadWithTask(HttpServletResponse response) throws IOException, ParseException {
    	JSONObject jsonObj = new JSONObject();
    	try {
	    	leadSyncWithTaskService.syncLeadWithTask();
    	} catch(Exception e) {
	    	jsonObj.put("status", "0");
	        jsonObj.put("msg", "任务线索状态更新失败");
    	}
	    	jsonObj.put("status", "1");
	        jsonObj.put("msg", "任务线索状态更新成功");
	}
    
    @RequestMapping(value = "/clearInvalidPushTask")
	@ResponseBody
	public void clearInvalidPushTask(HttpServletResponse response) throws IOException, ParseException {
    	JSONObject jsonObj = new JSONObject();
    	try {
	    	List<String> clearedInsts = campaignBPMTaskService.clearInvalidPushTask();
	    	jsonObj.put("Cleared-Tasks", clearedInsts.toString());
    	} catch(Exception e) {
	    	jsonObj.put("status", "0");
	        jsonObj.put("msg", "清理失败");
    	}
		response.setContentType("text/json;charset=UTF-8");
		response.getWriter().print(jsonObj.toString());
	}
    
    @RequestMapping(value = "/queryTaskCount")
	@ResponseBody
	public void queryTaskCount(@RequestParam(required = true) String agentId, 
			HttpServletResponse response) throws IOException, ParseException {
    	JSONObject jsonObj = new JSONObject();
    	jsonObj.put("agentId", agentId);
    	try {
    		CampaignTaskDto taskDto = new CampaignTaskDto();
			taskDto.setTaskSourceType(CampaignTaskSourceType.PUSH.getIndex());
			taskDto.setOwner(agentId);
			taskDto.setStatuses(CampaignTaskStatus.toStringIndexes());
			//已分配数量
			int obtainQty = campaignBPMTaskService.queryCount(taskDto);
			jsonObj.put("obtainQty", obtainQty);
			
     		taskDto = new CampaignTaskDto();
 			taskDto.setTaskSourceType(CampaignTaskSourceType.PUSH.getIndex());
 			taskDto.setOwner(agentId);
 			taskDto.setStatus("0");
 			//已分配数量
 			int obtainUnDialQty = campaignBPMTaskService.queryCount(taskDto);
 			jsonObj.put("obtainUnDialQty", obtainUnDialQty);
 			
			response.setContentType("text/json;charset=UTF-8");
			response.getWriter().print(jsonObj.toString());
    	} catch(Exception e) {
	    	jsonObj.put("status", "0");
    	}
	}
    
    private static long totalTime = 0l;
    private static int taskNum = 0; 
    @RequestMapping(value = "/saveLead")
	@ResponseBody
    public void saveLead(HttpServletResponse response) throws IOException {
    	JSONObject jsonObj = new JSONObject();
    	Lead lead = new Lead();
		lead.setCreateUser("cmtest");
		lead.setCampaignId(1001l);
		lead.setContactId("918181224");
		lead.setOutbound(true);
		try {
			long startTime = System.currentTimeMillis();
			String instID = leadService.saveLead(lead).getBpmInstanceId();
			jsonObj.put("instId", instID);
			long duration = System.currentTimeMillis() - startTime;
			if(StringUtils.isNotBlank(instID)) {
				totalTime+=duration;
				taskNum++;
			}
		} catch (ServiceException e) {
			logger.error("",e);
		}
		response.setContentType("text/json;charset=UTF-8");
		response.getWriter().print(jsonObj.toString());
    }
    
    @RequestMapping(value = "/getTaskInfo")
	@ResponseBody
    public void getTaskInfo(HttpServletResponse response) throws IOException {
    	JSONObject jsonObj = new JSONObject();
    	jsonObj.put("TaskNum", taskNum);
    	jsonObj.put("totalTime", totalTime);
		response.setContentType("text/json;charset=UTF-8");
		response.getWriter().print(jsonObj.toString());
    }

}
