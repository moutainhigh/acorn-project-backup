/*
 * @(#)LeadServiceImpl.java 1.0 2013-5-9下午1:22:32
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.exception.ExceptionConstant;
import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.marketing.core.common.CampaignTaskStatus;
import com.chinadrtv.erp.marketing.core.common.Constants;
import com.chinadrtv.erp.marketing.core.common.LeadStatus;
import com.chinadrtv.erp.marketing.core.dao.LeadDao;
import com.chinadrtv.erp.marketing.core.dao.LeadInterActionDao;
import com.chinadrtv.erp.marketing.core.dao.LeadTaskDao;
import com.chinadrtv.erp.marketing.core.dto.CampaignTaskVO;
import com.chinadrtv.erp.marketing.core.dto.LeadQueryDto;
import com.chinadrtv.erp.marketing.core.exception.ErrorCode;
import com.chinadrtv.erp.marketing.core.exception.MarketingException;
import com.chinadrtv.erp.marketing.core.service.CampaignBPMTaskService;
import com.chinadrtv.erp.marketing.core.service.LeadInterActionService;
import com.chinadrtv.erp.marketing.core.service.LeadService;
import com.chinadrtv.erp.marketing.core.service.UserBpmTaskService;
import com.chinadrtv.erp.marketing.core.util.StringUtil;
import com.chinadrtv.erp.model.marketing.Lead;
import com.chinadrtv.erp.model.marketing.LeadInteraction;
import com.chinadrtv.erp.model.marketing.LeadTask;
import com.chinadrtv.erp.model.marketing.UserBpmTask;
import com.chinadrtv.erp.util.DateUtil;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-5-9 下午1:22:32
 * 
 */
@Service("leadService")
public class LeadServiceImpl extends GenericServiceImpl<Lead, Long> implements LeadService {
	private static final Logger logger = LoggerFactory
			.getLogger(LeadServiceImpl.class);

	@Autowired
	private LeadDao leadDao;
	@Autowired
	private LeadTaskDao leadTaskDao;
	@Autowired
	private LeadInterActionDao leadInterActionDao;
	@Autowired
	private CampaignBPMTaskService bpmTaskService;
	@Autowired
	private LeadInterActionService leadInterActionService;
	@Autowired
	private UserBpmTaskService userBpmTaskService;

	/**
	 * 新增销售线索，内置创建任务，以及计算线索有效期逻辑
	 * 销售线索对象
	 * 
	 * @return
	 * @throws ServiceException
	 * 
	 * @see com.chinadrtv.erp.marketing.core.service.LeadService#saveLead(com.chinadrtv.erp.marketing.core.model.Lead)
	 */
	public LeadTask saveLead(Lead lead) throws ServiceException {
		LeadTask leadTask = new LeadTask();
		Lead leads = null;
		String bpmid = "";
		String userid = "";
		if (lead.getCampaignId() == null) {
			throw new ServiceException(
					ExceptionConstant.SERVICE_PARAMETER_EXCEPTION,
					"campaignId 为空");
		}
		try {
			if (!StringUtil.isNullOrBank(lead.getContactId())) {
				lead.setIspotential(0l);
				userid = lead.getContactId();
			} else {
				lead.setIspotential(1l);
				userid = lead.getPotentialContactId();
			}
			Date endDate = null;
			if (lead.getId() == null || lead.getId() == 0) {        //新建线索
				//支持外部设置lead的结束时间
				if(lead.getEndDate() == null) {
					int validateDay = 7;
					if(!lead.isOutbound()) {
						validateDay = 3;
					}
					Date createDate = new Date();
					endDate = DateUtil.addDays2Date(createDate, validateDay);
					endDate = DateUtil.getEndOfDate(endDate);
					lead.setEndDate(endDate);
				} else {
					endDate = lead.getEndDate();
				}
				if(lead.getBeginDate() == null) {
					lead.setBeginDate(new Date());
				}
/*				if(StringUtils.isBlank(lead.getOwner())) {
					lead.setOwner(lead.getCreateUser());
				}*/
				lead.setCreateDate(new Date());
				
//				startTime = System.currentTimeMillis();
				leads = leadDao.save(lead);
//		        logger.debug("leadDao.save cost :"+(System.currentTimeMillis()-startTime));
				
			} else {//更新线索
				leads = leadDao.get(lead.getId());
				//如果为回访, 预约时间超出当前线索的结束时间,更新lead的结束时间
				endDate = leads.getEndDate();
				if(lead.isCallbackTask() && lead.getAppointDate() != null 
						&& (lead.getAppointDate().getTime()-leads.getEndDate().getTime()>0)) {
					endDate = lead.getAppointDate();
					leads.setEndDate(endDate);
					leadDao.saveOrUpdate(leads);
				}
			}
			if(lead.isCallbackTask() && lead.getId() != null && lead.getId() != 0) {
				//回访任务，只延续原任务时间
			} else {
				//不是callback,创建新任务
				bpmid = bpmTaskService.createMarketingTask(
						String.valueOf(leads.getId()),
						String.valueOf(leads.getCampaignId()), userid, lead.getIspotential(), lead.getPdCustomerId(),
						leads.getOwner(),endDate,lead.getAppointDate(),lead.getTaskSourcType(),
						lead.getTaskSourcType2(),lead.getTaskSourcType3(),lead.getTaskRemark());
			}
		} catch (Exception e) {
			logger.error("新增销售线索失败" + e);
			throw new ServiceException(
					ExceptionConstant.SERVICE_TRANSACTION_EXCEPTION, e);
		}
		leadTask.setBpmInstanceId(bpmid);
		leadTask.setLeadId(leads.getId());
		return leadTask;
	}

	/**
	 * 更新销售线索状态
	 * 
	 * @param leadId
	 *            线索id
	 * 
	 * @param status
	 *            状态
	 * 
	 * @param statusReason
	 *            状态更新原因
	 * 
	 * @param updateUser
	 *            座席ID
	 * 
	 * @return
	 * @throws ServiceException
	 * 
	 * @see com.chinadrtv.erp.marketing.core.service.LeadService#updateLead(java.lang.
	 *      Long, java.lang.String, java.lang.String, java.lang.String)
	 */
	
	/**
	 * 完整插入线索对象，不做处理
	 * insertLead 
	 * void
	 * @exception 
	 * @since  1.0.0
	 */
	public void insertLead(Lead lead) throws ServiceException {
		try {
			if (!StringUtil.isNullOrBank(lead.getContactId())) {
				lead.setIspotential(0l);
			} else {
				lead.setIspotential(1l);
			}
			Date endDate = null;
			if (lead.getId() == null || lead.getId() == 0) {        //新建线索
				int validateDay = 7;
				if(!lead.isOutbound()) {
					validateDay = 3;
				}
				Date createDate = new Date();
				endDate = DateUtil.addDays2Date(createDate, validateDay);
				endDate = DateUtil.getEndOfDate(endDate);
				lead.setEndDate(endDate);
				lead.setBeginDate(new Date());
				if(StringUtils.isBlank(lead.getOwner())) {
					lead.setOwner(lead.getCreateUser());
				}
				lead.setCreateDate(new Date());
			} 
			leadDao.insertLead(lead);
		} catch (Exception e) {
			logger.error("新增销售线索失败" + e);
			throw new ServiceException(
					ExceptionConstant.SERVICE_TRANSACTION_EXCEPTION, e);
		}
	}
	
	public Boolean updateLead(Long leadId, Long status, String statusReason,
			String updateUser) throws ServiceException {
		if (leadId == null) {
			throw new ServiceException(
					ExceptionConstant.SERVICE_PARAMETER_EXCEPTION, "leadId 为空");
		}
		// TODO 判断任务状态
		Boolean flag = true;
		Lead lead = null;
		try {
			lead = leadDao.get(leadId);
			lead.setStatus(status);
			lead.setStatusReason(statusReason);
			lead.setUpdateUser(updateUser);
			leadDao.saveOrUpdate(lead);
			//关闭线索，相关任务也关闭
			if(status==1) {
				List<Object> leadTasks = null;
				leadTasks = leadTaskDao.queryInstsByLead(leadId);
				for(Object leadTask : leadTasks) {
					bpmTaskService.completeTaskAndUpdateStatus(statusReason, (String)leadTask, null, updateUser);
				}
			}
		} catch (Exception e) {
			logger.error("更新销售线索失败" + e);
			flag = false;
			throw new ServiceException(
					ExceptionConstant.SERVICE_TRANSACTION_EXCEPTION, e);
		}
		return flag;
	}


    /**
     * 更新销售线索状态
     *
     * @param leadId
     *            线索id
     *
     * @param status
     *            状态
     *
     * @param statusReason
     *            状态更新原因
     *
     * @param updateUser
     *            座席ID
     *
     * @return
     * @throws ServiceException
     *
     * @see com.chinadrtv.erp.marketing.core.service.LeadService#updateLead(java.lang.
     *      Long, java.lang.String, java.lang.String, java.lang.String)
     */
    public Boolean updateLead(Long leadId, Long status, String statusReason,
                              String updateUser,Date updateDate,String dnis,String ani,String userGroup) throws ServiceException {
        if (leadId == null) {
            throw new ServiceException(
                    ExceptionConstant.SERVICE_PARAMETER_EXCEPTION, "leadId 为空");
        }
        // TODO 判断任务状态
        Boolean flag = true;
        Lead lead = null;
        try {
            lead = leadDao.get(leadId);
            lead.setStatus(status);
            lead.setStatusReason(statusReason);
            lead.setUpdateUser(updateUser);
            lead.setDnis(dnis);
            lead.setAni(ani);
            lead.setUserGroup(userGroup);
            lead.setUpdateDate(updateDate);
            leadDao.saveOrUpdate(lead);
            //关闭线索，相关任务也关闭
            if(status==1) {
                List<Object> leadTasks = null;
                leadTasks = leadTaskDao.queryInstsByLead(leadId);
                for(Object leadTask : leadTasks) {
                    bpmTaskService.completeTaskAndUpdateStatus(statusReason, (String)leadTask, null, updateUser);
                }
            }
        } catch (Exception e) {
            logger.error("更新销售线索失败" + e);
            flag = false;
            throw new ServiceException(
                    ExceptionConstant.SERVICE_TRANSACTION_EXCEPTION, e);
        }
        return flag;
    }
    

	@Override
	public boolean updateLead(Lead lead) {
		boolean result = false;
		if(lead == null) {
			return false;
		}
		Lead leads=leadDao.get(lead.getId());
		if(lead.getStatus() != null) {
			leads.setStatus(lead.getStatus());
		}
		if(lead.getPriority() != null) {
			leads.setPriority(lead.getPriority());
		}
		if(lead.getLastOrderId() != null) {
			leads.setLastOrderId(lead.getLastOrderId());
		}
        if(lead.getUpdateDate() !=null ){
             leads.setUpdateDate(lead.getUpdateDate());
        }
        if(lead.getUpdateUser() != null) {
        	leads.setUpdateUser(lead.getUpdateUser());
        }
		leadDao.saveOrUpdate(leads);
		result = true;
		return result;
	}

	/**
	 * 
	 * 重新分配销售线索
	 * 
	 * @param leadId
	 *            销售线索ID
	 * 
	 * @param owner
	 *            重分配座席ID
	 * @return
	 * @throws ServiceException
	 * 
	 * @description 重新指定OWNER. 1. LEAD_TASK终止当前任务 2.
	 *              复制当前任务，其中座席ID为重新分配座席，任务状态不变。 3. 调用BPM的API，更新任务执行人为新分配座席。
	 * 
	 *              增加 分配人 updateUser
	 * 
	 * @see com.chinadrtv.erp.marketing.core.service.LeadService#reapportionLead(java.
	 *      lang.Long, java.lang.String)
	 */
	public Boolean reapportionLead(Long leadId, String owner, String updateUser)
			throws ServiceException {
		Boolean flag = true;
		List<LeadTask> leadTask = new ArrayList<LeadTask>();
		try {
			leadTask = leadTaskDao.getLeadTaskByHql(leadId);
			for (LeadTask lt : leadTask) {
				if (lt.getStatus().equals(Constants.LEAD_TASK_ACTIVE)) {
					LeadTask leadTasks = new LeadTask();
					PropertyUtils.copyProperties(leadTasks, lt);
					leadTasks.setCreateDate(new Date());
					leadTasks.setCareateUser(owner);
					leadTasks.setId(null);
					leadTasks.setStatus(Constants.LEAD_TASK_ACTIVE);
					leadTaskDao.save(leadTasks);
				}
				lt.setStatus(Constants.LEAD_TASK_STOP);
				lt.setUpdateDate(new Date());
				lt.setUpdateUser(updateUser);
				leadTaskDao.saveOrUpdate(lt);
			}
			Lead lead = leadDao.get(leadId);
			lead.setOwner(owner);
			lead.setUpdateDate(new Date());
			leadDao.saveOrUpdate(lead);
			// 调用bpm接口
			bpmTaskService.changeTaskOwnerByLead(leadId, owner);
		} catch (Exception e) {
			logger.error("重新分配销售线索失败" + e);
			flag = false;
			throw new ServiceException(
					ExceptionConstant.SERVICE_TRANSACTION_EXCEPTION, e);
		}
		return flag;
	}
	
	public Lead getLastestAliveLead(String agentId, String contactId, String campaignId) {
		return leadDao.getLastestAliveLead(agentId, contactId, campaignId);
	}
	
	public Lead getLastestAliveLead(String agentId, String contactId) {
		return leadDao.getLastestAliveLead(agentId, contactId);
	}
	
	public List<Object> queryOverdueLead() {
		return leadDao.queryOverdueLead();
	}
	
	public int updateStatus4OverdueLead(long leadId) {
		return leadDao.updateStatus4OverdueLead(leadId);
	}

	public int updatePotential2Normal(String contactId, String potentialContactId) {
		leadInterActionService.updatePotential2Normal(contactId, potentialContactId);
		return leadDao.updatePotential2Normal(contactId, potentialContactId);
	}
	
	public int updateContact(String instId, String contactId, boolean isPotential) {
		int taskNum = leadTaskDao.updateContact(instId, contactId, isPotential);
		int leadNum = leadDao.updateContact(instId, contactId, isPotential);
		LeadTask leadTask = leadTaskDao.queryInst(instId);
		int interactionNum  = leadInterActionDao.updateContact(leadTask.getLeadId(), contactId, isPotential);
		if(logger.isInfoEnabled()) {
			logger.info(taskNum + " tasks and "+interactionNum+" interactions were updated.");
		}
		return leadNum;
	}
	
	public List<Map<String, Object>> query(LeadQueryDto leadQueryDto, DataGridModel dataModel) {
		return leadDao.query(leadQueryDto, dataModel);
	}
	
	public Long transferLeadAndSubsidiaries(String instId, String newAgentId, Date endDate, String comment) throws Exception {
		CampaignTaskVO cvo = bpmTaskService.queryMarketingTask(instId);
		//关闭老线索
		updateStatus4OverdueLead(cvo.getLeadId());
		//创建新的线索
		Lead oldLead = get(cvo.getLeadId());
		
		oldLead.setId(null);
		oldLead.setPreviousLeadId(cvo.getLeadId());
		oldLead.setCreateDate(new Date());
		oldLead.setUpdateDate(new Date());
		oldLead.setUpdateUser(newAgentId);
		oldLead.setStatus(Long.valueOf(LeadStatus.STARTED.getIndex()));
		if(endDate != null && (endDate.getTime()-oldLead.getEndDate().getTime() > 0)) {
			oldLead.setEndDate(endDate);
		}
		oldLead.setOwner(newAgentId);
		leadDao.insertLead(oldLead);
		
		//关闭老任务

		LeadTask leadTask = bpmTaskService.get(cvo.getTid());
//		bpmTaskService.updateTaskStatus(leadTask.getBpmInstanceId(), String.valueOf(CampaignTaskStatus.STOP.getIndex()), comment, "");
		bpmTaskService.completeTaskAndUpdateStatus(comment,leadTask.getBpmInstanceId(),"",leadTask.getCareateUser());
		
		//创建新任务
		leadTaskDao.evict(leadTask);
		leadTask.setId(null);
		leadTask.setStatus(String.valueOf(CampaignTaskStatus.ACTIVE.getIndex()));
		leadTask.setLeadId(oldLead.getId());
		leadTask.setUserid(newAgentId);
		leadTask.setCreateDate(new Date());
		leadTask.setRemark(comment);
		if(endDate != null) {
			leadTask.setAppointDate(endDate);
		}
		bpmTaskService.createMarketingTask(leadTask);
		return oldLead.getId();
		
	}

    public Lead get(Long id) {
        return leadDao.get(id);
    }

	@Override
	protected GenericDao<Lead, Long> getGenericDao() {
		return leadDao;
	}
	
	public boolean isAlive(Lead lead) {
		boolean isAlive = false;
		if(System.currentTimeMillis() - lead.getEndDate().getTime() < 0) {
			if(lead.getStatus() == LeadStatus.SALECHANCE.getIndex() 
					|| lead.getStatus() == LeadStatus.STARTED.getIndex() 
					|| lead.getStatus() == LeadStatus.TODO.getIndex() 
					|| lead.getStatus() == LeadStatus.BINGO.getIndex()) {
				isAlive = true;
			}
		}
		return isAlive;
	}

}
