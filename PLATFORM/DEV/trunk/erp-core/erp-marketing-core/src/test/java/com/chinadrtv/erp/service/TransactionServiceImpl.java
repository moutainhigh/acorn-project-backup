/**
 * Copyright (c) 2013, Acorn and/or its affiliates. All rights reserved.
 * ACORN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.chinadrtv.erp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.marketing.core.dto.CampaignTaskVO;
import com.chinadrtv.erp.marketing.core.service.CampaignBPMTaskService;
import com.chinadrtv.erp.marketing.core.service.LeadService;
import com.chinadrtv.erp.model.marketing.Lead;
import com.chinadrtv.erp.model.marketing.LeadTask;

/**
 * 2013-11-19 下午1:03:38
 * @version 1.0.0
 * @author yangfei
 *
 */
@Service("transactionService")
public class TransactionServiceImpl implements TransactionService{
	@Autowired
	private LeadService leadService;
	@Autowired
	private CampaignBPMTaskService campaignBPMTaskService;
	
	@Override
	public String saveAndUpdate(Lead lead) throws ServiceException {
		LeadTask leadTask = leadService.saveLead(lead);
		CampaignTaskVO cvo = campaignBPMTaskService.queryMarketingTask(leadTask.getBpmInstanceId());
		LeadTask lt2 = campaignBPMTaskService.get(cvo.getTid());
		lt2.setRemark("for-tran");
		campaignBPMTaskService.saveOrUpdate(lt2);
//		campaignBPMTaskService.updateTaskStatus(leadTask.getBpmInstanceId(), null, "for-tran", "");
		return leadTask.getBpmInstanceId();
	}

}
