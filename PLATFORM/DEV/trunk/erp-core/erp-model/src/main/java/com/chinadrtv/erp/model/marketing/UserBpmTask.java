/**
 * Copyright (c) 2013, Acorn and/or its affiliates. All rights reserved.
 * ACORN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.chinadrtv.erp.model.marketing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.apache.commons.lang.StringUtils;

/**
 * 2013-5-16 上午9:59:33
 * 
 * @version 1.0.0
 * @author yangfei
 * 
 */
@Table(name = "USER_BPM_TASK", schema = "ACOAPP_MARKETING")
@Entity
public class UserBpmTask implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String busiType;
	private String bpmInstID;
	private String bpmTaskID;
	private String changeObjID;
	private String status;
	private String updateUser;
	private Date updateDate;
	private String approveUser;
	private Date approveDate;
	private String remark;
	private String approverRemark = "无";
	private String batchID;
	private String parentInsId;
    private List<UserBpmTask> subTasks = new ArrayList<UserBpmTask>();

	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USER_BPM_TASK")
	@SequenceGenerator(name = "SEQ_USER_BPM_TASK", sequenceName = "ACOAPP_MARKETING.SEQ_USER_BPM_TASK", allocationSize = 1)
	@Column(name = "ID")
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "BUSI_TYPE")
	public String getBusiType() {
		return busiType;
	}

	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}

	@Column(name = "BPM_INS_ID")
	public String getBpmInstID() {
		return bpmInstID;
	}

	public void setBpmInstID(String bpmInstID) {
		this.bpmInstID = bpmInstID;
	}

	@Column(name = "BPM_TASK_ID")
	public String getBpmTaskID() {
		return bpmTaskID;
	}

	public void setBpmTaskID(String bpmTaskID) {
		this.bpmTaskID = bpmTaskID;
	}

	@Column(name = "CHANGE_OBJ_ID")
	public String getChangeObjID() {
		return changeObjID;
	}

	public void setChangeObjID(String changeObjID) {
		this.changeObjID = changeObjID;
	}

	@Column(name = "STATUS")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "UPDATE_USER")
	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	@Column(name = "UPDATE_DATE")
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Column(name = "APPROVE_USER")
	public String getApproveUser() {
		return approveUser;
	}

	public void setApproveUser(String approveUser) {
		this.approveUser = approveUser;
	}

	@Column(name = "APPROVE_DATE")
	public Date getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

	/**
	 * 座席备注
	 */
	@Column(name = "REMARK")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 主管批注
	 */
	@Column(name = "APPROVE_REMARK")
	public String getApproverRemark() {
		return approverRemark;
	}

	public void setApproverRemark(String approverRemark) {
		if(StringUtils.isNotBlank(approverRemark)) {
			this.approverRemark = approverRemark;
		}
	}

	@Column(name = "USER_BPM_ID")
	public String getBatchID() {
		return batchID;
	}

	public void setBatchID(String batchID) {
		this.batchID = batchID;
	}

	@Column(name = "PARENT_INS_ID")
	public String getParentInsId() {
		return parentInsId;
	}

	public void setParentInsId(String parentInsId) {
		this.parentInsId = parentInsId;
	}

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="PARENT_INS_ID", referencedColumnName="BPM_INS_ID", insertable=false, updatable=false)
    public List<UserBpmTask> getSubTasks() {
        return subTasks;
    }

    public void setSubTasks(List<UserBpmTask> subTasks) {
        this.subTasks = subTasks;
    }
}
