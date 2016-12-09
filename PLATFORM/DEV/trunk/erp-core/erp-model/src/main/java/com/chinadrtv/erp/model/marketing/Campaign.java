/*
 * @(#)Campaign.java 1.0 2013-4-17涓婂崍9:52:35
 *
 * 姗℃灉鍥介檯-erp寮�彂缁�
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.model.marketing;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-4-17 涓婂崍9:52:35 钀ラ攢璁″垝
 */
@Table(name = "CAMPAIGN", schema = "ACOAPP_MARKETING")
@Entity
public class Campaign implements Serializable {

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

	private CustomerGroup group;
	private CampaignType campaignType;

	private LeadType leadType;

	private String execDepartment;
	private Date lastExecDate;
	private String createDepartment;

	private Long couponConfigId;

	public Campaign() {
		super();
	}

	/**
	 * @return the id
	 */
	@Id
	@Column(name = "ID")
	@SequenceGenerator(name = "generator", sequenceName = "ACOAPP_MARKETING.SEQ_CAMPAIGN", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
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
	@Column(name = "NAME")
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
	@Column(name = "TYPE")
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
	@Column(name = "OBJECTIVE")
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
	@Column(name = "AUDIENCE")
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
	@Column(name = "OFFER")
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
	@Column(name = "START_DATE")
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
	@Column(name = "END_DATE")
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
	 * @return the customerGroup
	 */
	@OneToOne(optional = false, fetch = FetchType.LAZY)
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "AUDIENCE", referencedColumnName = "group_Code", unique = true, insertable = false, updatable = false)
	public CustomerGroup getGroup() {
		return group;
	}

	/**
	 * @param customerGroup
	 *            the customerGroup to set
	 */
	public void setGroup(CustomerGroup group) {
		this.group = group;
	}

	/**
	 * @return the campaignType
	 */
	@OneToOne
	@JoinColumn(name = "CAMPAIGN_TYPE_ID", referencedColumnName = "id", insertable = false, updatable = false)
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
	@OneToOne
	@JoinColumn(name = "LEAD_TYPE_ID", referencedColumnName = "id", insertable = false, updatable = false)
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

	/*
	 * (闈�Javadoc) <p>Title: hashCode</p> <p>Description: </p>
	 * 
	 * @return
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((actualCost == null) ? 0 : actualCost.hashCode());
		result = prime * result
				+ ((audience == null) ? 0 : audience.hashCode());
		result = prime * result
				+ ((budgetedCost == null) ? 0 : budgetedCost.hashCode());
		result = prime * result
				+ ((campaignTypeId == null) ? 0 : campaignTypeId.hashCode());
		result = prime * result
				+ ((createDate == null) ? 0 : createDate.hashCode());
		result = prime * result
				+ ((createUser == null) ? 0 : createUser.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((leadTargeted == null) ? 0 : leadTargeted.hashCode());
		result = prime * result
				+ ((leadTypeId == null) ? 0 : leadTypeId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((objective == null) ? 0 : objective.hashCode());
		result = prime * result + ((offer == null) ? 0 : offer.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		result = prime * result
				+ ((revenueTarget == null) ? 0 : revenueTarget.hashCode());
		result = prime * result
				+ ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result
				+ ((updateDate == null) ? 0 : updateDate.hashCode());
		result = prime * result
				+ ((updateUser == null) ? 0 : updateUser.hashCode());
		return result;
	}

	/*
	 * (闈�Javadoc) <p>Title: equals</p> <p>Description: </p>
	 * 
	 * @param obj
	 * 
	 * @return
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Campaign other = (Campaign) obj;
		if (actualCost == null) {
			if (other.actualCost != null)
				return false;
		} else if (!actualCost.equals(other.actualCost))
			return false;
		if (audience == null) {
			if (other.audience != null)
				return false;
		} else if (!audience.equals(other.audience))
			return false;
		if (budgetedCost == null) {
			if (other.budgetedCost != null)
				return false;
		} else if (!budgetedCost.equals(other.budgetedCost))
			return false;
		if (campaignTypeId == null) {
			if (other.campaignTypeId != null)
				return false;
		} else if (!campaignTypeId.equals(other.campaignTypeId))
			return false;
		if (createDate == null) {
			if (other.createDate != null)
				return false;
		} else if (!createDate.equals(other.createDate))
			return false;
		if (createUser == null) {
			if (other.createUser != null)
				return false;
		} else if (!createUser.equals(other.createUser))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (leadTargeted == null) {
			if (other.leadTargeted != null)
				return false;
		} else if (!leadTargeted.equals(other.leadTargeted))
			return false;
		if (leadTypeId == null) {
			if (other.leadTypeId != null)
				return false;
		} else if (!leadTypeId.equals(other.leadTypeId))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (objective == null) {
			if (other.objective != null)
				return false;
		} else if (!objective.equals(other.objective))
			return false;
		if (offer == null) {
			if (other.offer != null)
				return false;
		} else if (!offer.equals(other.offer))
			return false;
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))
			return false;
		if (revenueTarget == null) {
			if (other.revenueTarget != null)
				return false;
		} else if (!revenueTarget.equals(other.revenueTarget))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (updateDate == null) {
			if (other.updateDate != null)
				return false;
		} else if (!updateDate.equals(other.updateDate))
			return false;
		if (updateUser == null) {
			if (other.updateUser != null)
				return false;
		} else if (!updateUser.equals(other.updateUser))
			return false;
		return true;
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
	 * @return the lastExecDate
	 */
	@Column(name = "LAST_EXEC_DATE")
	public Date getLastExecDate() {
		return lastExecDate;
	}

	/**
	 * @param lastExecDate
	 *            the lastExecDate to set
	 */
	public void setLastExecDate(Date lastExecDate) {
		this.lastExecDate = lastExecDate;
	}

	/**
	 * @return the createDepartment
	 */
	@Column(name = "create_Department")
	public String getCreateDepartment() {
		return createDepartment;
	}

	/**
	 * @param createDepartment
	 *            the createDepartment to set
	 */
	public void setCreateDepartment(String createDepartment) {
		this.createDepartment = createDepartment;
	}

	/**
	 * @return the couponConfigId
	 */
	@Column(name = "COUPON_CONFIG_ID")
	public Long getCouponConfigId() {
		return couponConfigId;
	}

	/**
	 * @param couponConfigId
	 *            the couponConfigId to set
	 */
	public void setCouponConfigId(Long couponConfigId) {
		this.couponConfigId = couponConfigId;
	}

}
