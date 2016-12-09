/*
 * @(#)Campaign.java 1.0 2013-4-17上午9:52:35
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.dto;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-4-17 上午9:52:35 营销计划
 */
public class CampaignRequest implements Serializable {

	private Long id;
	private String name;
	private String type;
	private String objective;
	private String audience;
	private String offer;
	private String status;
	private Date startDate;
	private Date endDate;
	private Long revenueTarget;
	private Long leadTargeted;
	private Long budgetedCost;
	private Long actualCost;
	private String owner;
	private String description;
	private Long leadTypeId;
	private Long campaignTypeId;

	private Date createDate;
	private String createUser;
	private Date updateDate;
	private String updateUser;

	private String department;

	private String tollFreeNum;// TOLL_FREE_NUM
	private String dnis;

	private String execDepartment;

	private String product1;
	
	private String product2;
	
	private String product3;

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
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the objective
	 */
	public String getObjective() {
		return objective;
	}

	/**
	 * @param objective
	 *            the objective to set
	 */
	public void setObjective(String objective) {
		this.objective = objective;
	}

	/**
	 * @return the audience
	 */
	public String getAudience() {
		return audience;
	}

	/**
	 * @param audience
	 *            the audience to set
	 */
	public void setAudience(String audience) {
		this.audience = audience;
	}

	/**
	 * @return the offer
	 */
	public String getOffer() {
		return offer;
	}

	/**
	 * @param offer
	 *            the offer to set
	 */
	public void setOffer(String offer) {
		this.offer = offer;
	}

	/**
	 * @return the status
	 */
	@Column(name = "STATUS")
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
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the revenueTarget
	 */
	@Column(name = "REVENUE_TARGET")
	public Long getRevenueTarget() {
		return revenueTarget;
	}

	/**
	 * @param revenueTarget
	 *            the revenueTarget to set
	 */
	public void setRevenueTarget(Long revenueTarget) {
		this.revenueTarget = revenueTarget;
	}

	/**
	 * @return the leadTargeted
	 */
	@Column(name = "LEADS_TARGETED")
	public Long getLeadTargeted() {
		return leadTargeted;
	}

	/**
	 * @param leadTargeted
	 *            the leadTargeted to set
	 */
	public void setLeadTargeted(Long leadTargeted) {
		this.leadTargeted = leadTargeted;
	}

	/**
	 * @return the budgetedCost
	 */
	@Column(name = "BUDGETED_COST")
	public Long getBudgetedCost() {
		return budgetedCost;
	}

	/**
	 * @param budgetedCost
	 *            the budgetedCost to set
	 */
	public void setBudgetedCost(Long budgetedCost) {
		this.budgetedCost = budgetedCost;
	}

	/**
	 * @return the actualCost
	 */
	@Column(name = "ACTUAL_COST")
	public Long getActualCost() {
		return actualCost;
	}

	/**
	 * @param actualCost
	 *            the actualCost to set
	 */
	public void setActualCost(Long actualCost) {
		this.actualCost = actualCost;
	}

	/**
	 * @return the owner
	 */
	@Column(name = "OWNER")
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
	 * @return the description
	 */
	@Column(name = "DESCRIPTION")
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
	 * @return the leadTypeId
	 */
	@Column(name = "LEAD_TYPE_ID")
	public Long getLeadTypeId() {
		return leadTypeId;
	}

	/**
	 * @param leadTypeId
	 *            the leadTypeId to set
	 */
	public void setLeadTypeId(Long leadTypeId) {
		this.leadTypeId = leadTypeId;
	}

	/**
	 * @return the campaignTypeId
	 */
	@Column(name = "CAMPAIGN_TYPE_ID")
	public Long getCampaignTypeId() {
		return campaignTypeId;
	}

	/**
	 * @param campaignTypeId
	 *            the campaignTypeId to set
	 */
	public void setCampaignTypeId(Long campaignTypeId) {
		this.campaignTypeId = campaignTypeId;
	}

	/**
	 * @return the createDate
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	@Column(name = "create_date")
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate
	 *            the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the createUser
	 */
	@Column(name = "create_user")
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
	 * @return the updateDate
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	@Column(name = "update_Date")
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDate
	 *            the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @return the updateUser
	 */
	@Column(name = "update_User")
	public String getUpdateUser() {
		return updateUser;
	}

	/**
	 * @param updateUser
	 *            the updateUser to set
	 */
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	/**
	 * @return the department
	 */
	@Column(name = "department")
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

	/**
	 * @return the tollFreeNum
	 */
	@Column(name = "TOLL_FREE_NUM")
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
	 * @return the dnis
	 */
	@Column(name = "dnis")
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
	 * @return the execDepartment
	 */
	@Column(name = "EXEC_DEPARTMENT")
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
	 * 获取属性值
	 * 
	 * @Description: TODO
	 * @param property
	 * @return
	 * @return String
	 * @throws
	 */
	public String genParamValues(String property) {
		String result = "";
		Field[] fields = this.getClass().getDeclaredFields();
		try {
			for (Field f : fields) {
				if (f.getName().equals(property)) {
					result = f.get(this).toString();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @return the product1
	 */
	public String getProduct1() {
		return product1;
	}

	/**
	 * @param product1
	 *            the product1 to set
	 */
	public void setProduct1(String product1) {
		this.product1 = product1;
	}

	/**
	 * @return the product2
	 */
	public String getProduct2() {
		return product2;
	}

	/**
	 * @param product2 the product2 to set
	 */
	public void setProduct2(String product2) {
		this.product2 = product2;
	}

	/**
	 * @return the product3
	 */
	public String getProduct3() {
		return product3;
	}

	/**
	 * @param product3 the product3 to set
	 */
	public void setProduct3(String product3) {
		this.product3 = product3;
	}

}
