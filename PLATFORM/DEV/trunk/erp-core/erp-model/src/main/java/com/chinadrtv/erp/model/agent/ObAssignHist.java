package com.chinadrtv.erp.model.agent;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 取数分配记录 Names entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ob_assign_hist", schema = "IAGENT")
public class ObAssignHist implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 8032729651244025950L;

	private Long id;
	private String contactId;
	private String jobId;
	private String batchId;
	private String queueId;
	private String fromQueueId;
	private String agent;
	private Date assignTime;
	private String operator;
	private String pdCustomerId;
	private String defGrp;
	private String dept;

	/**
	 * @return the id
	 */
	@Id
	@javax.persistence.Column(name = "ID")
	@SequenceGenerator(name = "seqob_assign_hist", sequenceName = "iagent.seqob_assign_hist", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqob_assign_hist")
	//@GenericGenerator(name = "idGenerator", strategy = "assigned")
	//@GeneratedValue(generator = "idGenerator")
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
	 * @return the contactId
	 */
	@Column(name = "contactId")
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
	 * @return the jobId
	 */
	@Column(name = "jobId")
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
	 * @return the batchId
	 */
	@Column(name = "batchId")
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
	@Column(name = "queueId")
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
	 * @return the fromQueueId
	 */
	@Column(name = "fromQueueId")
	public String getFromQueueId() {
		return fromQueueId;
	}

	/**
	 * @param fromQueueId
	 *            the fromQueueId to set
	 */
	public void setFromQueueId(String fromQueueId) {
		this.fromQueueId = fromQueueId;
	}

	/**
	 * @return the agent
	 */
	@Column(name = "agent")
	public String getAgent() {
		return agent;
	}

	/**
	 * @param agent
	 *            the agent to set
	 */
	public void setAgent(String agent) {
		this.agent = agent;
	}

	/**
	 * @return the assignTime
	 */
	@Column(name = "assignTime")
	public Date getAssignTime() {
		return assignTime;
	}

	/**
	 * @param assignTime
	 *            the assignTime to set
	 */
	public void setAssignTime(Date assignTime) {
		this.assignTime = assignTime;
	}

	/**
	 * @return the operator
	 */
	@Column(name = "operator")
	public String getOperator() {
		return operator;
	}

	/**
	 * @param operator
	 *            the operator to set
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * @return the defGrp
	 */
	@Column(name = "defGrp")
	public String getDefGrp() {
		return defGrp;
	}

	/**
	 * @return the pdCustomerId
	 */
	@Column(name = "pd_customerid")
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
	 * @param defGrp
	 *            the defGrp to set
	 */
	public void setDefGrp(String defGrp) {
		this.defGrp = defGrp;
	}

	/**
	 * @return the dept
	 */
	@Column(name = "dept")
	public String getDept() {
		return dept;
	}

	/**
	 * @param dept
	 *            the dept to set
	 */
	public void setDept(String dept) {
		this.dept = dept;
	}

}