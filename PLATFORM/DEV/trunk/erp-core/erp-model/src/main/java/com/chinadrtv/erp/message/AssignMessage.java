package com.chinadrtv.erp.message;

import java.io.Serializable;
import java.util.Date;

public class AssignMessage implements Serializable {
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	private String jobId;
	private String contactId;
	private int status;
	private String messge;
	private Date startDate;
	private Date endDate;
	private String pdCustomerId;
	private String batchId;
	private String queueId;
	private Long campaignId;
	private String source;

	/**
	 * @return the jobId
	 */
	public String getJobId() {
		return jobId;
	}

	/**
	 * @param jobId
	 *            the jobId to set
	 */
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	/**
	 * @return the contactId
	 */
	public String getContactId() {
		return contactId;
	}

	/**
	 * @param contactId
	 *            the contactId to set
	 */
	public void setContactId(String contactId) {
		this.contactId = contactId;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return the messge
	 */
	public String getMessge() {
		return messge;
	}

	/**
	 * @param messge
	 *            the messge to set
	 */
	public void setMessge(String messge) {
		this.messge = messge;
	}

	/**
	 * @return the startDate
	 */
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
	 * @return the pdCustomerId
	 */
	public String getPdCustomerId() {
		return pdCustomerId;
	}

	/**
	 * @param pdCustomerId
	 *            the pdCustomerId to set
	 */
	public void setPdCustomerId(String pdCustomerId) {
		this.pdCustomerId = pdCustomerId;
	}

	/**
	 * @return the batchId
	 */
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
	 * @return the queueId
	 */
	public String getQueueId() {
		return queueId;
	}

	/**
	 * @param queueId
	 *            the queueId to set
	 */
	public void setQueueId(String queueId) {
		this.queueId = queueId;
	}

	/**
	 * @return the campaignId
	 */
	public Long getCampaignId() {
		return campaignId;
	}

	/**
	 * @param campaignId the campaignId to set
	 */
	public void setCampaignId(Long campaignId) {
		this.campaignId = campaignId;
	}

	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}

}
