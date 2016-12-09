/*
 * @(#)CustomerGroupDto.java 1.0 2013-1-24下午2:48:12
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.dto;

import java.io.Serializable;

import org.activiti.engine.impl.util.json.JSONObject;

import com.chinadrtv.erp.model.marketing.CampaignType;
import com.chinadrtv.erp.model.marketing.LeadType;

/**
 * <dl>
 * <dt><b>Title:营销计划查询dto</b></dt>
 * <dd>
 * none</dd>
 * <dt><b>Description:营销计划查询dto</b></dt>
 * <dd>
 * <p>
 * none</dd>
 * </dl>
 * 
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-1-24 下午2:48:12
 * 
 */
public class CampaignDto implements Serializable{

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	// map.put("id", campaign.getId());
	// map.put("campaignType", campaign.getCampaignType());
	// map.put("leadType", campaign.getLeadType());
	// map.put("execDepartment", campaign.getExecDepartment());
	// map.put("name", campaign.getName());
	// map.put("startDate",
	// DateTimeUtil.sim3.format(campaign.getStartDate()));
	// map.put("endDate",
	// DateTimeUtil.sim3.format(campaign.getEndDate()));
	// map.put("owner", campaign.getOwner());
	// map.put("tollFreeNum", campaign.getTollFreeNum());
	// map.put("dnis", campaign.getDnis());
	// map.put("description", campaign.getDescription());
	// map.put("createUser", campaign.getCreateUser());
	private String name;
	private Long type;
	private String status;
	private String startDate;
	private String endDate;
	private String user;
	private String callTime;
	private String tollFreeNum;
	private String execDepartment;
	private CampaignType campaignType;
	private LeadType leadType;
	private String owner;
	private String dnis;
	private String createUser;
	private String description;
	private Long id;
	private String department;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the type
	 */
	public Long getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(Long type) {
		this.type = type;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * @return the callTime
	 */
	public String getCallTime() {
		return callTime;
	}

	/**
	 * @param callTime
	 *            the callTime to set
	 */
	public void setCallTime(String callTime) {
		this.callTime = callTime;
	}

	/**
	 * @return the tollFreeNum
	 */
	public String getTollFreeNum() {
		return tollFreeNum;
	}

	/**
	 * @param tollFreeNum
	 *            the tollFreeNum to set
	 */
	public void setTollFreeNum(String tollFreeNum) {
		this.tollFreeNum = tollFreeNum;
	}

	/**
	 * @return the execDepartment
	 */
	public String getExecDepartment() {
		return execDepartment;
	}

	/**
	 * @param execDepartment
	 *            the execDepartment to set
	 */
	public void setExecDepartment(String execDepartment) {
		this.execDepartment = execDepartment;
	}

	/**
	 * @return the campaignType
	 */
	public CampaignType getCampaignType() {
		return campaignType;
	}

	/**
	 * @param campaignType
	 *            the campaignType to set
	 */
	public void setCampaignType(CampaignType campaignType) {
		this.campaignType = campaignType;
	}

	/**
	 * @return the leadType
	 */
	public LeadType getLeadType() {
		return leadType;
	}

	/**
	 * @param leadType
	 *            the leadType to set
	 */
	public void setLeadType(LeadType leadType) {
		this.leadType = leadType;
	}

	/**
	 * @return the owner
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * @param owner
	 *            the owner to set
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}

	/**
	 * @return the dnis
	 */
	public String getDnis() {
		return dnis;
	}

	/**
	 * @param dnis
	 *            the dnis to set
	 */
	public void setDnis(String dnis) {
		this.dnis = dnis;
	}

	/**
	 * @return the createUser
	 */
	public String getCreateUser() {
		return createUser;
	}

	/**
	 * @param createUser
	 *            the createUser to set
	 */
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the department
	 */
	public String getDepartment() {
		return department;
	}

	/**
	 * @param department
	 *            the department to set
	 */
	public void setDepartment(String department) {
		this.department = department;
	}

	
	public String getJson(){
		
		JSONObject jsoObject = new JSONObject();
		jsoObject.put("name", this.name);
		jsoObject.put("type", this.type);
		jsoObject.put("startDate", this.startDate);
		jsoObject.put("endDate", this.endDate);
		jsoObject.put("callTime", this.callTime);
		jsoObject.put("tollFreeNum", this.tollFreeNum);
		jsoObject.put("execDepartment", this.execDepartment);
		jsoObject.put("campaignType", this.campaignType.getId());
		jsoObject.put("leadType", this.leadType.getId());
		jsoObject.put("owner", this.owner);
		jsoObject.put("dnis", this.dnis);
		jsoObject.put("createUser", this.createUser);
		jsoObject.put("description", this.description);
		jsoObject.put("id", this.id);
		jsoObject.put("department", this.department);
		
		return jsoObject.toString();
	}
}
