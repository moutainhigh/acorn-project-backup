/**
 * Copyright (c) 2013, Acorn and/or its affiliates. All rights reserved.
 * ACORN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.chinadrtv.erp.sales.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.marketing.core.common.CampaignTaskSourceType;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.marketing.core.common.CampaignTaskStatus;
import com.chinadrtv.erp.marketing.core.common.Constants;
import com.chinadrtv.erp.marketing.core.common.LeadStatus;
import com.chinadrtv.erp.marketing.core.dto.CampaignTaskDto;
import com.chinadrtv.erp.marketing.core.dto.CampaignTaskVO;
import com.chinadrtv.erp.marketing.core.exception.MarketingException;
import com.chinadrtv.erp.marketing.core.service.CampaignBPMTaskService;
import com.chinadrtv.erp.marketing.core.service.JobRelationexService;
import com.chinadrtv.erp.marketing.core.service.LeadService;
import com.chinadrtv.erp.message.AssignMessage;
import com.chinadrtv.erp.model.agent.ObAssignHist;
import com.chinadrtv.erp.model.marketing.Lead;
import com.chinadrtv.erp.sales.dto.AssignHistTask;
import com.chinadrtv.erp.sales.service.SalesTaskService;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.util.DateUtil;

/**
 * 2013-6-25 上午9:31:23
 * @version 1.0.0
 * @author yangfei
 *
 */
@Service("salesTaskService")
public class SalesTaskServiceImpl implements SalesTaskService{
	private static final Logger logger = LoggerFactory.getLogger(SalesTaskServiceImpl.class);

    @Autowired
    private JobRelationexService jobRelationexService;
    
    @Autowired
    private CampaignBPMTaskService campaignBPMTaskService;
    
    @Autowired
    private LeadService leadService;
    
	@Override
	public void tryAssignHist(AgentUser user, String jobId, AssignHistTask assignHistTask) throws MarketingException {
		String message = null;
		AssignMessage aMsg = null;
		String instId = null;
		String campaignId = null;
		String isPotential = null;
		
		List<AssignMessage> assMsgs = jobRelationexService.queryUnprocessed(user.getUserId(), jobId);
		if(assMsgs!=null && assMsgs.size()>0) {
			aMsg = assMsgs.get(0);
			message = "请先处理尚未处理任务";
			CampaignTaskDto cDto = new CampaignTaskDto ();
			cDto.setCustomerID(aMsg.getContactId());
			cDto.setUserID(user.getUserId());
			cDto.setStatuses(CampaignTaskStatus.toStringIndexes());
			cDto.setEndDate(DateUtil.addDays(new Date(), 1));
			DataGridModel dataModel = new DataGridModel ();
			dataModel.setStartRow(0);
			dataModel.setPage(1);
			Map<String, Object> results = campaignBPMTaskService.queryMarketingTask(cDto, dataModel);
			List<Object> vos = (List<Object>) results.get("rows");
			CampaignTaskVO cvo = null;
			if(vos != null && vos.size() > 0) {
				for(Object obj : vos) {
					if(StringUtils.isNotBlank(((CampaignTaskVO)obj).getInstID()) 
							&& CampaignTaskStatus.INITIALIZED.getName().equals(((CampaignTaskVO)obj).getLtStatus())) {
						cvo = (CampaignTaskVO)obj;
						assignHistTask.setStartDate(cvo.getLtCreateDate());
						assignHistTask.setEndDate(cvo.getEndDate());
						instId = cvo.getInstID();
						isPotential = String.valueOf(cvo.getIsPotential());
						campaignId = cvo.getCaID();
						break;
					}
				}
			}
			if(cvo != null) {
				assignHistTask.setTip(message);
				assignHistTask.setContactId(aMsg.getContactId());
				assignHistTask.setInstId(instId);
				assignHistTask.setIsPotential(isPotential);
				assignHistTask.setCampaignId(campaignId);
			} else {
				//不可控的更新任务，但是取数数据没有同步更新
				try {
					if(vos != null && vos.size() > 0) {
						for(Object obj : vos) {
							cvo = (CampaignTaskVO)obj;
							if(StringUtils.isNotBlank(cvo.getPdCustomerId())) {
								jobRelationexService.dialContact(cvo.getPdCustomerId(), null);
							}
						}
					}
				} catch (ServiceException e) {
					logger.error("标识失败",e);
				}
				assignHist(user, jobId, assignHistTask);
			}
		} else {
			assignHist(user, jobId, assignHistTask);
		}
	}
	
	public void assignHist(AgentUser user, String jobId, AssignHistTask assignHistTask) {
		ObAssignHist assignHist = new ObAssignHist();
		assignHist.setJobId(jobId);
		assignHist.setAgent(user.getUserId());
		assignHist.setDefGrp("");
		assignHist.setDept(user.getDepartment());
		try { 
			AssignMessage aMsg = jobRelationexService.assignHist(assignHist);
			if(aMsg.getStatus()==1) {
				Lead lead = new Lead();
				lead.setContactId(aMsg.getContactId());
				if(Constants.OBCONTACT_DATASOURCE_CONTACT.equals(aMsg.getSource())) {
					lead.setIspotential(0L);
					assignHistTask.setIsPotential("0");
				} else {
					lead.setIspotential(1L);
					assignHistTask.setIsPotential("1");
				}
				lead.setCreateUser(user.getUserId());
				lead.setUserGroup(user.getWorkGrp());
				lead.setCreateDate(new Date());
				//TODOX campaign应该在取数时应该已经确定
				long campaignIdFetched = aMsg.getCampaignId();
				
				lead.setCampaignId(campaignIdFetched);
				lead.setPdCustomerId(aMsg.getPdCustomerId());
				//初始为开始状态
				lead.setStatus(new Long(LeadStatus.STARTED.getIndex()));
				//取数为outbound创建线索
				lead.setOutbound(true);
				lead.setOwner(user.getUserId());
				lead.setCallDirection(1L);
				
                lead.setTaskSourcType(CampaignTaskSourceType.FETCH.getIndex());
                String instId = leadService.saveLead(lead).getBpmInstanceId();
				Date startDate = new Date();
				assignHistTask.setStartDate(startDate);
				assignHistTask.setEndDate(DateUtil.addDays2Date(startDate, 7));
				assignHistTask.setContactId(aMsg.getContactId());
				assignHistTask.setInstId(instId);
				assignHistTask.setCampaignId(String.valueOf(campaignIdFetched));
			} else if(aMsg.getStatus()==0) {
				assignHistTask.setSuc(false);
				assignHistTask.setTip("队列"+jobId+"已无数据可取.");
			} else if(aMsg.getStatus()==-1) {
				assignHistTask.setSuc(false);
				assignHistTask.setTip("队列"+jobId+"取数失败，请联系管理员查明原因.");
			} 
		} catch (ServiceException e) {
			logger.error("取数失败",e);
			assignHistTask.setSuc(false);
			assignHistTask.setTip("队列"+jobId+"取数失败,"+e.getMessage());
		}catch (Exception e) {
			logger.error("取数失败",e);
			assignHistTask.setSuc(false);
			assignHistTask.setTip("队列"+jobId+"取数失败，请联系管理员查明原因.");
		}
	
	}
}
