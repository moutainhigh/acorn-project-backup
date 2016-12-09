package com.chinadrtv.erp.model.agent;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Names entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "PD_SYS_JOBS_RELATION", schema = "IAGENT")
public class JobsRelation implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 8032729651244025950L;
	
	private String jobId;
	private String jobLevel;
	private Integer top;
	private Integer days;
	/**
	 * @return the jobId
	 */

	@Id
	@Column(name = "JOBID")
	public String getJobId() {
		return jobId;
	}
	/**
	 * @param jobId the jobId to set
	 */
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	/**
	 * @return the jobLevel
	 */
	@Column(name = "JOBLEVEL")
	public String getJobLevel() {
		return jobLevel;
	}
	/**
	 * @param jobLevel the jobLevel to set
	 */
	public void setJobLevel(String jobLevel) {
		this.jobLevel = jobLevel;
	}
	/**
	 * @return the top
	 */
	@Column(name = "TOP")
	public Integer getTop() {
		return top;
	}
	/**
	 * @param top the top to set
	 */
	public void setTop(Integer top) {
		this.top = top;
	}
	/**
	 * @return the days
	 */
	@Column(name = "DAYS")
	public Integer getDays() {
		return days;
	}
	/**
	 * @param days the days to set
	 */
	public void setDays(Integer days) {
		this.days = days;
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
		result = prime * result + ((days == null) ? 0 : days.hashCode());
		result = prime * result + ((jobId == null) ? 0 : jobId.hashCode());
		result = prime * result
				+ ((jobLevel == null) ? 0 : jobLevel.hashCode());
		result = prime * result + ((top == null) ? 0 : top.hashCode());
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
		JobsRelation other = (JobsRelation) obj;
		if (days == null) {
			if (other.days != null)
				return false;
		} else if (!days.equals(other.days))
			return false;
		if (jobId == null) {
			if (other.jobId != null)
				return false;
		} else if (!jobId.equals(other.jobId))
			return false;
		if (jobLevel == null) {
			if (other.jobLevel != null)
				return false;
		} else if (!jobLevel.equals(other.jobLevel))
			return false;
		if (top == null) {
			if (other.top != null)
				return false;
		} else if (!top.equals(other.top))
			return false;
		return true;
	}


}