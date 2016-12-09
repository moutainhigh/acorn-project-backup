/**
 * Copyright (c) 2013, Acorn and/or its affiliates. All rights reserved.
 * ACORN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.chinadrtv.erp.sales.job;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.marketing.core.common.CampaignTaskSourceType;
import com.chinadrtv.erp.marketing.core.common.CampaignTaskStatus;
import com.chinadrtv.erp.marketing.core.dao.LeadDao;
import com.chinadrtv.erp.marketing.core.dao.LeadTaskDao;
import com.chinadrtv.erp.marketing.core.service.CampaignBPMTaskService;
import com.chinadrtv.erp.marketing.core.service.CampaignReceiverService;
import com.chinadrtv.erp.marketing.core.service.JobRelationexService;
import com.chinadrtv.erp.marketing.core.service.LeadService;
import com.chinadrtv.erp.model.marketing.LeadTask;

/**
 * 2013-7-16 下午2:53:55
 * @version 1.0.0
 * @author yangfei
 *
 */
public class LeadSyncWithTask{
	private static final Logger logger = LoggerFactory.getLogger(LeadSyncWithTask.class);

	private	LeadService leadService;

	private	CampaignBPMTaskService bpmTaskService;
	
	private CampaignReceiverService campaignReceiverService;
	private JobRelationexService jobRelationexService;
	
	public void executeInternal( ) {
		logger.warn("Job executing...");
		int number = 0;
		try {
			List<Object> leads= leadService.queryOverdueLead();
			if(leads != null) {
				number = leads.size();
			}
			for(Object obj : leads) {
				leadService.updateStatus4OverdueLead(((BigDecimal)obj).longValue());
				bpmTaskService.updateTaskStatusByLead(((BigDecimal)obj).longValue(), String.valueOf(CampaignTaskStatus.SYSTEMSTOP.getIndex()));
				List<Object> tasks = bpmTaskService.queryInstsByLead(((BigDecimal)obj).longValue());
				for(Object taskId : tasks) {
					LeadTask lt = bpmTaskService.queryInst((String)taskId);
					if(CampaignTaskSourceType.PUSH.getIndex()== lt.getSourceType() 
							&& ( lt.getStatus().equals(CampaignTaskStatus.ACTIVE) 
									|| lt.getStatus().equals(CampaignTaskStatus.INITIALIZED)
									|| lt.getStatus().equals(CampaignTaskStatus.CANCEL))) {
						try {
							campaignReceiverService.signStatus(Long.valueOf((String)taskId));
						} catch (Exception e) {
							logger.warn("更新回收标志错误",e);
						}
					}
					//更新取数状态
					if(StringUtils.isNotBlank(lt.getPdCustomerId())) {
						try {
							jobRelationexService.dialContact(lt.getPdCustomerId(), null);
						} catch (ServiceException e) {
							e.printStackTrace();
						}
					}
				}
			}
		} catch(Exception e) {
			logger.error("Failed to update the status for the over due leads.", e);
		}
		logger.warn(number +" leads were overdue, with their status updated to 2-systemstop");
	}

	public LeadService getLeadService() {
		return leadService;
	}

	public void setLeadService(LeadService leadService) {
		this.leadService = leadService;
	}

	public CampaignBPMTaskService getBpmTaskService() {
		return bpmTaskService;
	}

	public void setBpmTaskService(CampaignBPMTaskService bpmTaskService) {
		this.bpmTaskService = bpmTaskService;
	}

	public CampaignReceiverService getCampaignReceiverService() {
		return campaignReceiverService;
	}

	public void setCampaignReceiverService(
			CampaignReceiverService campaignReceiverService) {
		this.campaignReceiverService = campaignReceiverService;
	}

	public JobRelationexService getJobRelationexService() {
		return jobRelationexService;
	}

	public void setJobRelationexService(JobRelationexService jobRelationexService) {
		this.jobRelationexService = jobRelationexService;
	}
	
}
