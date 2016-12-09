/*
 * @(#)Campaign.java 1.0 2013-4-17上午9:52:35
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.model.marketing;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-4-17 上午9:52:35 营销计划
 */
@Table(name = "CAMPAIGN_BATCH", schema = "ACOAPP_MARKETING")
@Entity
public class CampaignBatch implements Serializable {

	private Long id;
	private Long campaignId;
	private String batchId;
	private Long campaignTypeId;
	private String status;

	public CampaignBatch() {
		super();
	}

	/**
	 * @return the id
	 */
	@Id
	@Column(name = "ID")
	@SequenceGenerator(name = "SEQ_CAMPAIGN_BATCH", sequenceName = "ACOAPP_MARKETING.SEQ_CAMPAIGN_BATCH")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CAMPAIGN_BATCH")
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
	 * @return the campaignId
	 */
	@Column(name = "campaign_Id")
	public Long getCampaignId() {
		return campaignId;
	}

	/**
	 * @param campaignId
	 *            the campaignId to set
	 */
	public void setCampaignId(Long campaignId) {
		this.campaignId = campaignId;
	}

	/**
	 * @return the batchId
	 */
	@Column(name = "batch_Id")
	public String getBatchId() {
		return batchId;
	}

	/**
	 * @param batchId
	 *            the batchId to set
	 */
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	/**
	 * @return the campaignTypeId
	 */
	@Column(name = "campaign_Type_Id")
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
	 * @return the status
	 */
	@Column(name = "status")
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

	/*
	 * (非 Javadoc) <p>Title: hashCode</p> <p>Description: </p>
	 * 
	 * @return
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((batchId == null) ? 0 : batchId.hashCode());
		result = prime * result
				+ ((campaignId == null) ? 0 : campaignId.hashCode());
		result = prime * result
				+ ((campaignTypeId == null) ? 0 : campaignTypeId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/*
	 * (非 Javadoc) <p>Title: equals</p> <p>Description: </p>
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
		CampaignBatch other = (CampaignBatch) obj;
		if (batchId == null) {
			if (other.batchId != null)
				return false;
		} else if (!batchId.equals(other.batchId))
			return false;
		if (campaignId == null) {
			if (other.campaignId != null)
				return false;
		} else if (!campaignId.equals(other.campaignId))
			return false;
		if (campaignTypeId == null) {
			if (other.campaignTypeId != null)
				return false;
		} else if (!campaignTypeId.equals(other.campaignTypeId))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
