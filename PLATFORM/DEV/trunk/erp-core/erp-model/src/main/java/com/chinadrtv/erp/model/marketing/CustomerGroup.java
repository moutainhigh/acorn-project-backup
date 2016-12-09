package com.chinadrtv.erp.model.marketing;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonManagedReference;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * User: zhaizy Date: 12-12-28
 */

@Table(name = "CUSTOMER_GROUP", schema = "ACOAPP_MARKETING")
@Entity
// @JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class)
public class CustomerGroup {

	private String groupCode;// GROUP_CODE;
	private String groupName;// GROUP_NAME;
	private String busiCode;// BUSI_CODE;
	private String executeCycel;// EXECUTE_CYCLE;
	private String executeType;// EXECUTE_Type;
	private String status;// STATUS;
	private String assignType;// ASSIGN_TYPE;
	private Date validStartDate;// VALID_STARTDATE;
	private Date validEndDate;// VALID_ENDDATE;
	private String department;// DEPARTMENT;
	private String currBatchNum;// CURR_BATCHNUM;
	private Date batchDate;// BATCH_DATE;
	private String remark;// REMARK;
	private String crtUser;// CRT_USER;
	private Date crtDate;// CRT_DATE;
	private String upUser;// UP_USER;
	private Date upDate;// UP_DATE;
	private String ext1;// EXT1;
	private String ext2;// EXT2;
	private String ext3;// EXT3;

	private String jobId;
	private String queneId;
	private Integer rejectCycle;
	private String isRecover;
	private Integer batchStatus;
	
	private String createDepartment;

	@JsonManagedReference
	private CustomerSqlSource sqlSource;

	private String groupCodeTmp;
	
	private String type;

	/**
	 * @return the groupCode
	 */
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "assigned")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "GROUP_CODE")
	public String getGroupCode() {
		return groupCode;
	}

	/**
	 * @param groupCode
	 *            the groupCode to set
	 */
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	/**
	 * @return the groupName
	 */
	@Column(name = "GROUP_NAME")
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @param groupName
	 *            the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * @return the busiCode
	 */
	@Column(name = "BUSI_CODE")
	public String getBusiCode() {
		return busiCode;
	}

	/**
	 * @param busiCode
	 *            the busiCode to set
	 */
	public void setBusiCode(String busiCode) {
		this.busiCode = busiCode;
	}

	/**
	 * @return the executeCycel
	 */
	@Column(name = "EXECUTE_CYCLE")
	public String getExecuteCycel() {
		return executeCycel;
	}

	/**
	 * @param executeCycel
	 *            the executeCycel to set
	 */
	public void setExecuteCycel(String executeCycel) {
		this.executeCycel = executeCycel;
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
	 * @return the assignType
	 */
	@Column(name = "ASSIGN_TYPE")
	public String getAssignType() {
		return assignType;
	}

	/**
	 * @param assignType
	 *            the assignType to set
	 */
	public void setAssignType(String assignType) {
		this.assignType = assignType;
	}

	/**
	 * @return the validStartDate
	 */
	@Column(name = "VALID_STARTDATE")
	public Date getValidStartDate() {
		return validStartDate;
	}

	/**
	 * @param validStartDate
	 *            the validStartDate to set
	 */
	public void setValidStartDate(Date validStartDate) {
		this.validStartDate = validStartDate;
	}

	/**
	 * @return the validEndDate
	 */
	@Column(name = "VALID_ENDDATE")
	public Date getValidEndDate() {
		return validEndDate;
	}

	/**
	 * @param validEndDate
	 *            the validEndDate to set
	 */
	public void setValidEndDate(Date validEndDate) {
		this.validEndDate = validEndDate;
	}

	/**
	 * @return the department
	 */
	@Column(name = "DEPARTMENT")
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
	 * @return the currBatchNum
	 */
	@Column(name = "CURR_BATCHNUM")
	public String getCurrBatchNum() {
		return currBatchNum;
	}

	/**
	 * @param currBatchNum
	 *            the currBatchNum to set
	 */
	public void setCurrBatchNum(String currBatchNum) {
		this.currBatchNum = currBatchNum;
	}

	/**
	 * @return the batchDate
	 */
	@Column(name = "BATCH_DATE")
	public Date getBatchDate() {
		return batchDate;
	}

	/**
	 * @param batchDate
	 *            the batchDate to set
	 */
	public void setBatchDate(Date batchDate) {
		this.batchDate = batchDate;
	}

	/**
	 * @return the remark
	 */
	@Column(name = "REMARK")
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark
	 *            the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the crtUser
	 */
	@Column(name = "CRT_USER")
	public String getCrtUser() {
		return crtUser;
	}

	/**
	 * @param crtUser
	 *            the crtUser to set
	 */
	public void setCrtUser(String crtUser) {
		this.crtUser = crtUser;
	}

	/**
	 * @return the crtDate
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	@Column(name = "CRT_DATE")
	public Date getCrtDate() {
		return crtDate;
	}

	/**
	 * @param crtDate
	 *            the crtDate to set
	 */
	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}

	/**
	 * @return the upUser
	 */
	@Column(name = "UP_USER")
	public String getUpUser() {
		return upUser;
	}

	/**
	 * @param upUser
	 *            the upUser to set
	 */
	public void setUpUser(String upUser) {
		this.upUser = upUser;
	}

	/**
	 * @return the upDate
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	@Column(name = "UP_DATE")
	public Date getUpDate() {
		return upDate;
	}

	/**
	 * @param upDate
	 *            the upDate to set
	 */
	public void setUpDate(Date upDate) {
		this.upDate = upDate;
	}

	/**
	 * @return the ext1
	 */
	@Column(name = "EXT1")
	public String getExt1() {
		return ext1;
	}

	/**
	 * @param ext1
	 *            the ext1 to set
	 */
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	/**
	 * @return the ext2
	 */
	@Column(name = "EXT2")
	public String getExt2() {
		return ext2;
	}

	/**
	 * @param ext2
	 *            the ext2 to set
	 */
	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}

	/**
	 * @return the ext3
	 */
	@Column(name = "EXT3")
	public String getExt3() {
		return ext3;
	}

	/**
	 * @param ext3
	 *            the ext3 to set
	 */
	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}

	/**
	 * @return the executeType
	 */
	@Column(name = "EXECUTE_TYPE")
	public String getExecuteType() {
		return executeType;
	}

	/**
	 * @param executeType
	 *            the executeType to set
	 */
	public void setExecuteType(String executeType) {
		this.executeType = executeType;
	}

	/**
	 * @return the sqlSource
	 */
	//
	// @OneToOne(fetch=FetchType.EAGER,optional=true)
	// @JoinColumn(name="GROUP_CODE",referencedColumnName="GROUP_CODE",insertable=false,updatable=false,nullable=true)
	@JsonIgnore
	@OneToOne(mappedBy = "group", fetch = FetchType.LAZY)
	public CustomerSqlSource getSqlSource() {
		return sqlSource;
	}

	/**
	 * @param sqlSource
	 *            the sqlSource to set
	 */
	public void setSqlSource(CustomerSqlSource sqlSource) {
		this.sqlSource = sqlSource;
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
	 * @return the queneId
	 */
	@Column(name = "QUEUEID")
	public String getQueneId() {
		return queneId;
	}

	/**
	 * @param queneId
	 *            the queneId to set
	 */
	public void setQueneId(String queneId) {
		this.queneId = queneId;
	}

	/**
	 * @return the groupCodeTmp
	 */
	@Transient
	public String getGroupCodeTmp() {
		return groupCodeTmp;
	}

	/**
	 * @param groupCodeTmp
	 *            the groupCodeTmp to set
	 */
	public void setGroupCodeTmp(String groupCodeTmp) {
		this.groupCodeTmp = groupCodeTmp;
	}

	/**
	 * @return the rejectCycle
	 */
	@Column(name = "REJECT_CYCLE")
	public Integer getRejectCycle() {
		return rejectCycle;
	}

	/**
	 * @param rejectCycle
	 *            the rejectCycle to set
	 */
	public void setRejectCycle(Integer rejectCycle) {
		this.rejectCycle = rejectCycle;
	}

	/**
	 * @return the isRecover
	 */
	@Column(name = "is_Recover")
	public String getIsRecover() {
		return isRecover;
	}

	/**
	 * @param isRecover
	 *            the isRecover to set
	 */
	public void setIsRecover(String isRecover) {
		this.isRecover = isRecover;
	}

	/**
	 * @return the batchStatus
	 */
	@Column(name = "BATCH_STATUS")
	public Integer getBatchStatus() {
		return batchStatus;
	}

	/**
	 * @param batchStatus
	 *            the batchStatus to set
	 */
	public void setBatchStatus(Integer batchStatus) {
		this.batchStatus = batchStatus;
	}

	
	/**
	 * @return the createDepartment
	 */
	@Column(name = "crt_Department")
	public String getCreateDepartment() {
		return createDepartment;
	}

	/**
	 * @param createDepartment the createDepartment to set
	 */
	public void setCreateDepartment(String createDepartment) {
		this.createDepartment = createDepartment;
	}

	
	/**
	 * @return the type
	 */
	@Column(name = "type")
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
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
				+ ((assignType == null) ? 0 : assignType.hashCode());
		result = prime * result
				+ ((batchDate == null) ? 0 : batchDate.hashCode());
		result = prime * result
				+ ((busiCode == null) ? 0 : busiCode.hashCode());
		result = prime * result + ((crtDate == null) ? 0 : crtDate.hashCode());
		result = prime * result + ((crtUser == null) ? 0 : crtUser.hashCode());
		result = prime * result
				+ ((currBatchNum == null) ? 0 : currBatchNum.hashCode());
		result = prime * result
				+ ((department == null) ? 0 : department.hashCode());
		result = prime * result
				+ ((executeCycel == null) ? 0 : executeCycel.hashCode());
		result = prime * result
				+ ((executeType == null) ? 0 : executeType.hashCode());
		result = prime * result + ((ext1 == null) ? 0 : ext1.hashCode());
		result = prime * result + ((ext2 == null) ? 0 : ext2.hashCode());
		result = prime * result + ((ext3 == null) ? 0 : ext3.hashCode());
		result = prime * result
				+ ((groupCode == null) ? 0 : groupCode.hashCode());
		result = prime * result
				+ ((groupCodeTmp == null) ? 0 : groupCodeTmp.hashCode());
		result = prime * result
				+ ((groupName == null) ? 0 : groupName.hashCode());
		result = prime * result
				+ ((isRecover == null) ? 0 : isRecover.hashCode());
		result = prime * result + ((jobId == null) ? 0 : jobId.hashCode());
		result = prime * result + ((queneId == null) ? 0 : queneId.hashCode());
		result = prime * result
				+ ((rejectCycle == null) ? 0 : rejectCycle.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result
				+ ((sqlSource == null) ? 0 : sqlSource.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((upDate == null) ? 0 : upDate.hashCode());
		result = prime * result + ((upUser == null) ? 0 : upUser.hashCode());
		result = prime * result
				+ ((validEndDate == null) ? 0 : validEndDate.hashCode());
		result = prime * result
				+ ((validStartDate == null) ? 0 : validStartDate.hashCode());
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
		CustomerGroup other = (CustomerGroup) obj;
		if (assignType == null) {
			if (other.assignType != null)
				return false;
		} else if (!assignType.equals(other.assignType))
			return false;
		if (batchDate == null) {
			if (other.batchDate != null)
				return false;
		} else if (!batchDate.equals(other.batchDate))
			return false;
		if (busiCode == null) {
			if (other.busiCode != null)
				return false;
		} else if (!busiCode.equals(other.busiCode))
			return false;
		if (crtDate == null) {
			if (other.crtDate != null)
				return false;
		} else if (!crtDate.equals(other.crtDate))
			return false;
		if (crtUser == null) {
			if (other.crtUser != null)
				return false;
		} else if (!crtUser.equals(other.crtUser))
			return false;
		if (currBatchNum == null) {
			if (other.currBatchNum != null)
				return false;
		} else if (!currBatchNum.equals(other.currBatchNum))
			return false;
		if (department == null) {
			if (other.department != null)
				return false;
		} else if (!department.equals(other.department))
			return false;
		if (executeCycel == null) {
			if (other.executeCycel != null)
				return false;
		} else if (!executeCycel.equals(other.executeCycel))
			return false;
		if (executeType == null) {
			if (other.executeType != null)
				return false;
		} else if (!executeType.equals(other.executeType))
			return false;
		if (ext1 == null) {
			if (other.ext1 != null)
				return false;
		} else if (!ext1.equals(other.ext1))
			return false;
		if (ext2 == null) {
			if (other.ext2 != null)
				return false;
		} else if (!ext2.equals(other.ext2))
			return false;
		if (ext3 == null) {
			if (other.ext3 != null)
				return false;
		} else if (!ext3.equals(other.ext3))
			return false;
		if (groupCode == null) {
			if (other.groupCode != null)
				return false;
		} else if (!groupCode.equals(other.groupCode))
			return false;
		if (groupCodeTmp == null) {
			if (other.groupCodeTmp != null)
				return false;
		} else if (!groupCodeTmp.equals(other.groupCodeTmp))
			return false;
		if (groupName == null) {
			if (other.groupName != null)
				return false;
		} else if (!groupName.equals(other.groupName))
			return false;
		if (isRecover == null) {
			if (other.isRecover != null)
				return false;
		} else if (!isRecover.equals(other.isRecover))
			return false;
		if (jobId == null) {
			if (other.jobId != null)
				return false;
		} else if (!jobId.equals(other.jobId))
			return false;
		if (queneId == null) {
			if (other.queneId != null)
				return false;
		} else if (!queneId.equals(other.queneId))
			return false;
		if (rejectCycle == null) {
			if (other.rejectCycle != null)
				return false;
		} else if (!rejectCycle.equals(other.rejectCycle))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (sqlSource == null) {
			if (other.sqlSource != null)
				return false;
		} else if (!sqlSource.equals(other.sqlSource))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (upDate == null) {
			if (other.upDate != null)
				return false;
		} else if (!upDate.equals(other.upDate))
			return false;
		if (upUser == null) {
			if (other.upUser != null)
				return false;
		} else if (!upUser.equals(other.upUser))
			return false;
		if (validEndDate == null) {
			if (other.validEndDate != null)
				return false;
		} else if (!validEndDate.equals(other.validEndDate))
			return false;
		if (validStartDate == null) {
			if (other.validStartDate != null)
				return false;
		} else if (!validStartDate.equals(other.validStartDate))
			return false;
		return true;
	}

}
