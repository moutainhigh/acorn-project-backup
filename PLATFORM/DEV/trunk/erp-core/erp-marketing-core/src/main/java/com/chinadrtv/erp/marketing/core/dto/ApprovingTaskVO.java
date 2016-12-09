/**
 * Copyright (c) 2013, Acorn and/or its affiliates. All rights reserved.
 * ACORN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.chinadrtv.erp.marketing.core.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

import com.chinadrtv.erp.marketing.core.common.AuditTaskStatus;
import com.chinadrtv.erp.marketing.core.common.AuditTaskType;

/**
 * 2013-5-13 下午5:57:50
 * @version 1.0.0
 * @author yangfei
 * 
 */
@Table(name = "USER_BPM", schema = "ACOAPP_MARKETING")
@Entity
public class ApprovingTaskVO{
	//ubt.busi_type busiType, ubt.bpm_ins_id bpmInstID, ubt.create_date crDate,ubt.contact_id cID, con.name cName, 
	//ubt.order_id orderID,ubt.status status,ubt.create_user crUser,grp.grpname gName,oh.crusr orCrUsrID, grp2.grpname orCrUsrGrpName
	/**
	 * 记录ID
	 */
	
	private String batchID;
	
	/**
	 * 任务业务类型，1-修改客户 2-4 订单修改
	 */
	private String busiType;

	/**
	 * 申请时间
	 */
	private Date crDate;
	
	/**
	 * 客户ID
	 */
	private String cID;
	
	private String cName;
	
	private String orderID;
	
	private String status = "-";
	
	/**
	 * （订单/客户）修改申请座席ID
	 */
	private String crUser;

	/**
	 * 订单修改座席ID，客户修改该值为空
	 */
	private String orCrUsrID;
	
	/**
	 * 订单修改座席所在组，客户修改该值为空
	 */
	private String orCrUsrGrpName;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USER_BPM")
	@SequenceGenerator(name = "SEQ_USER_BPM", sequenceName = "SEQ_USER_BPM", allocationSize = 1)
	@Column(name = "id")
	public String getBatchID() {
		return batchID;
	}

	public void setBatchID(String batchID) {
		this.batchID = batchID;
	}

	@Column(name = "busiType", updatable=false)
	public String getBusiType() {
		return busiType;
	}

	public void setBusiType(String busiType) {
		if(StringUtils.isNotBlank(busiType)) {
			this.busiType = AuditTaskType.fromOrdinal(Integer.valueOf(busiType));
		}
	}

	@Column(name = "crDate", updatable=false)
	public Date getCrDate() {
		return crDate;
	}

	public void setCrDate(Date crDate) {
		this.crDate = crDate;
	}

	@Column(name = "cID", updatable=false)
	public String getcID() {
		return cID;
	}

	public void setcID(String cID) {
		this.cID = cID;
	}

	@Column(name = "cName", updatable=false)
	public String getcName() {
		return cName;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}

	@Column(name = "orderID", updatable=false)
	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	@Column(name = "status", updatable=false)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		if(StringUtils.isNotBlank(status)) {
			this.status = AuditTaskStatus.fromOrdinal(Integer.valueOf(status));
		}
	}

	@Column(name = "crUser", updatable=false)
	public String getCrUser() {
		return crUser;
	}

	public void setCrUser(String crUser) {
		this.crUser = crUser;
	}

	@Column(name = "orCrUsrID", updatable=false)
	public String getOrCrUsrID() {
		return orCrUsrID;
	}

	public void setOrCrUsrID(String orCrUsrID) {
		this.orCrUsrID = orCrUsrID;
	}

	@Column(name = "orCrUsrGrpName", updatable=false)
	public String getOrCrUsrGrpName() {
		return orCrUsrGrpName;
	}

	public void setOrCrUsrGrpName(String orCrUsrGrpName) {
		this.orCrUsrGrpName = orCrUsrGrpName;
	}
	
}
