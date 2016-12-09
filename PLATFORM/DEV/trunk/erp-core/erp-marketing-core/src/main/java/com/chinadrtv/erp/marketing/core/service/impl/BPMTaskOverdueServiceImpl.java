/**
 * Copyright (c) 2013, Acorn and/or its affiliates. All rights reserved.
 * ACORN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.chinadrtv.erp.marketing.core.service.impl;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.marketing.core.common.CampaignTaskSourceType;
import com.chinadrtv.erp.marketing.core.common.CampaignTaskStatus;
import com.chinadrtv.erp.marketing.core.dao.LeadTaskDao;
import com.chinadrtv.erp.marketing.core.dto.CampaignTaskVO;
import com.chinadrtv.erp.marketing.core.service.CampaignBPMTaskService;
import com.chinadrtv.erp.marketing.core.service.CampaignReceiverService;
import com.chinadrtv.erp.marketing.core.service.JobRelationexService;

/**
 * 2013-5-7 上午11:11:50
 * @version 1.0.0
 * @author yangfei
 * 同步状态
 */
@Service("bpmTaskOverdueService")
public class BPMTaskOverdueServiceImpl implements JavaDelegate{
	private static final Logger logger = LoggerFactory.getLogger(BPMTaskOverdueServiceImpl.class);
	@Autowired
	private LeadTaskDao leadTaskDao;
	@Autowired
	private JobRelationexService jobRelationexService;
	@Autowired
	private CampaignBPMTaskService campaignBPMTaskService;
	@Autowired
	private CampaignReceiverService campaignReceiverService;
	
	private static String remark = "任务到期, 自动结束"; 
	
	private void syncStatus(String instID, String taskID,String status) {
		CampaignTaskVO cvo =campaignBPMTaskService.queryMarketingTask(instID);
		//鉴于activity不能处理延长timer的方法，所以对于延长的任务不处理
		if(cvo != null && cvo.getEndDate().getTime() < System.currentTimeMillis()) {
			if(String.valueOf(CampaignTaskStatus.INITIALIZED.getIndex()).equals(cvo.getStatus()) ||
					String.valueOf(CampaignTaskStatus.ACTIVE.getIndex()).equals(cvo.getStatus())) {
				logger.warn(instID+" overdue, starting to sync.");
				leadTaskDao.updateInstStatus(instID, status, remark, taskID);
				//更新取数状态
				if(StringUtils.isNotBlank(cvo.getPdCustomerId())) {
					try {
						jobRelationexService.dialContact(cvo.getPdCustomerId(), null);
					} catch (ServiceException e) {
						e.printStackTrace();
					}
				}
			}
			if(String.valueOf(CampaignTaskSourceType.PUSH.getIndex()).equals(cvo.getSourceType())) {
				try {
					campaignReceiverService.signStatus(Long.valueOf(instID));
				} catch (Exception e) {
					logger.error("更新回收标志错误",e);
				}
			}
		}
	}

	public void execute(DelegateExecution execContext) throws Exception {
		String instID = execContext.getProcessInstanceId();
		syncStatus(instID,null,String.valueOf(CampaignTaskStatus.SYSTEMSTOP.getIndex()));
	}
}
