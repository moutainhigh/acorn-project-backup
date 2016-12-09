/*
 * @(#)CampaignReceiverDto.java 1.0 2013年8月28日上午11:19:22
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.dto;

import java.util.ArrayList;
import java.util.List;

import com.chinadrtv.erp.model.marketing.CampaignReceiver;

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
 * @since 2013年8月28日 上午11:19:22 
 * 
 */
public class CampaignReceiverDto extends CampaignReceiver {

	private static final long serialVersionUID = -5848343041506729860L;
	
	private String customerBatch;
	private String jobNum;
	private String dataState;
	private String groupId;
	private List<String> agentGroupList = new ArrayList<String>();
	private List<String> statuses = new ArrayList<String>(); 

	public List<String> getAgentGroupList() {
		return agentGroupList;
	}

	public void setAgentGroupList(List<String> agentGroupList) {
		this.agentGroupList = agentGroupList;
	}

	public String getCustomerBatch() {
		return customerBatch;
	}

	public void setCustomerBatch(String customerBatch) {
		this.customerBatch = customerBatch;
	}

	public String getJobNum() {
		return jobNum;
	}

	public void setJobNum(String jobNum) {
		this.jobNum = jobNum;
	}

	public String getDataState() {
		return dataState;
	}

	public void setDataState(String dataState) {
		this.dataState = dataState;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public List<String> getStatuses() {
		return statuses;
	}

	public void setStatuses(List<String> statuses) {
		this.statuses = statuses;
	}
	
}
