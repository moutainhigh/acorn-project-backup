package com.chinadrtv.erp.model.agent;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Names entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "OB_QUEUES", schema = "IAGENT")
public class ObQueues implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 8032729651244025950L;

	private String queueId;
	private String jobId;
	private String name;
	private String helpStr;
	private String type;
	private Date startTime;
	private Date endTime;
	private String dataOrderType;
	private String invalid;
	private Date createTime;
	private Date updateTime;
	private String createUsr;
	private String updateUsr;
	private String onlyFirstQueue;

	/**
	 * @return the queueId
	 */
	@Id
	@Column(name = "QUEUEID")
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
	 * @return the jobId
	 */
	@Column(name = "JOBID")
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
	 * @return the helpStr
	 */
	@Column(name = "HELPSTR")
	public String getHelpStr() {
		return helpStr;
	}

	/**
	 * @param helpStr
	 *            the helpStr to set
	 */
	public void setHelpStr(String helpStr) {
		this.helpStr = helpStr;
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
	 * @return the startTime
	 */
	@Column(name = "STARTTIME")
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime
	 *            the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	@Column(name = "ENDTIME")
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime
	 *            the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the dataOrderType
	 */
	@Column(name = "DATAORDERTYPE")
	public String getDataOrderType() {
		return dataOrderType;
	}

	/**
	 * @param dataOrderType
	 *            the dataOrderType to set
	 */
	public void setDataOrderType(String dataOrderType) {
		this.dataOrderType = dataOrderType;
	}

	/**
	 * @return the invalid
	 */
	@Column(name = "INVALID")
	public String getInvalid() {
		return invalid;
	}

	/**
	 * @param invalid
	 *            the invalid to set
	 */
	public void setInvalid(String invalid) {
		this.invalid = invalid;
	}

	/**
	 * @return the createTime
	 */
	@Column(name = "CREATETIME")
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the updateTime
	 */
	@Column(name = "UPDATETIME")
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime
	 *            the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * @return the createUsr
	 */
	@Column(name = "CREATEUSR")
	public String getCreateUsr() {
		return createUsr;
	}

	/**
	 * @param createUsr
	 *            the createUsr to set
	 */
	public void setCreateUsr(String createUsr) {
		this.createUsr = createUsr;
	}

	/**
	 * @return the updateUsr
	 */
	@Column(name = "UPDATEUSR")
	public String getUpdateUsr() {
		return updateUsr;
	}

	/**
	 * @param updateUsr
	 *            the updateUsr to set
	 */
	public void setUpdateUsr(String updateUsr) {
		this.updateUsr = updateUsr;
	}

	/**
	 * @return the onlyFirstQueue
	 */
	@Column(name = "ONLYFIRSTQUEUE")
	public String getOnlyFirstQueue() {
		return onlyFirstQueue;
	}

	/**
	 * @param onlyFirstQueue
	 *            the onlyFirstQueue to set
	 */
	public void setOnlyFirstQueue(String onlyFirstQueue) {
		this.onlyFirstQueue = onlyFirstQueue;
	}

	/* (非 Javadoc)
	* <p>Title: hashCode</p>
	* <p>Description: </p>
	* @return
	* @see java.lang.Object#hashCode()
	*/ 
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result
				+ ((createUsr == null) ? 0 : createUsr.hashCode());
		result = prime * result
				+ ((dataOrderType == null) ? 0 : dataOrderType.hashCode());
		result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
		result = prime * result + ((helpStr == null) ? 0 : helpStr.hashCode());
		result = prime * result + ((invalid == null) ? 0 : invalid.hashCode());
		result = prime * result + ((jobId == null) ? 0 : jobId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((onlyFirstQueue == null) ? 0 : onlyFirstQueue.hashCode());
		result = prime * result + ((queueId == null) ? 0 : queueId.hashCode());
		result = prime * result
				+ ((startTime == null) ? 0 : startTime.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result
				+ ((updateTime == null) ? 0 : updateTime.hashCode());
		result = prime * result
				+ ((updateUsr == null) ? 0 : updateUsr.hashCode());
		return result;
	}

	/* (非 Javadoc)
	* <p>Title: equals</p>
	* <p>Description: </p>
	* @param obj
	* @return
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
		ObQueues other = (ObQueues) obj;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (createUsr == null) {
			if (other.createUsr != null)
				return false;
		} else if (!createUsr.equals(other.createUsr))
			return false;
		if (dataOrderType == null) {
			if (other.dataOrderType != null)
				return false;
		} else if (!dataOrderType.equals(other.dataOrderType))
			return false;
		if (endTime == null) {
			if (other.endTime != null)
				return false;
		} else if (!endTime.equals(other.endTime))
			return false;
		if (helpStr == null) {
			if (other.helpStr != null)
				return false;
		} else if (!helpStr.equals(other.helpStr))
			return false;
		if (invalid == null) {
			if (other.invalid != null)
				return false;
		} else if (!invalid.equals(other.invalid))
			return false;
		if (jobId == null) {
			if (other.jobId != null)
				return false;
		} else if (!jobId.equals(other.jobId))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (onlyFirstQueue == null) {
			if (other.onlyFirstQueue != null)
				return false;
		} else if (!onlyFirstQueue.equals(other.onlyFirstQueue))
			return false;
		if (queueId == null) {
			if (other.queueId != null)
				return false;
		} else if (!queueId.equals(other.queueId))
			return false;
		if (startTime == null) {
			if (other.startTime != null)
				return false;
		} else if (!startTime.equals(other.startTime))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (updateTime == null) {
			if (other.updateTime != null)
				return false;
		} else if (!updateTime.equals(other.updateTime))
			return false;
		if (updateUsr == null) {
			if (other.updateUsr != null)
				return false;
		} else if (!updateUsr.equals(other.updateUsr))
			return false;
		return true;
	}

}