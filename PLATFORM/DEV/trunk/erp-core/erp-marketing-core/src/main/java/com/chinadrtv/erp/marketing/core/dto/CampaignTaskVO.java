/**
 * Copyright (c) 2013, Acorn and/or its affiliates. All rights reserved.
 * ACORN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.chinadrtv.erp.marketing.core.dto;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.chinadrtv.erp.marketing.core.common.CampaignTaskSourceType;
import com.chinadrtv.erp.marketing.core.common.CampaignTaskSourceType2;
import com.chinadrtv.erp.marketing.core.common.CampaignTaskSourceType3;
import com.chinadrtv.erp.marketing.core.common.CampaignTaskStatus;
import com.chinadrtv.erp.marketing.core.common.CampaignTaskType;


/**
 * 2013-5-3 下午5:57:50
 * @version 1.0.0
 * @author yangfei
 * 
 */
public class CampaignTaskVO{
	//ca.id caID, ca.name caName,ltp.name ltpName,con.contactid conid," +
	//"con.name conName,lt.create_date ltCreateDate,ca.end_date endDate, lt.status ltStatus, lt.userID uID, lt.id tid
	private long tid;
	
	private String instID;

	private String caID;

	private String caName;

	/**
	 * TODO 待业务确认
	 * 任务类型
	 */
	private String taskType = "销售";
	
	private String ltpName;
	
	private long ltpId;
	
	private String conid;
	
	private String conName;
	
	private long isPotential;
	
	private String pdCustomerId;
	
	private long leadId;

	private Date ltCreateDate;
	
	private String userID;
	
	private Date endDate;
	
	private String ltStatus = "-";
	
	private String status = "";
	
	private Date appointDate;
	
	private String remark;

    private long sourceType;
    
    private long sourceType2;
    
    private long sourceType3;
    
    private String sourceTypeDsc;
	
	private Integer exigency;

	public long getTid() {
		return tid;
	}

	public void setTid(long tid) {
		this.tid = tid;
	}

	public String getInstID() {
		return instID;
	}

	public void setInstID(String instID) {
		this.instID = instID;
	}

	public String getCaID() {
		return caID;
	}

	public void setCaID(String caID) {
		this.caID = caID;
	}

	public String getCaName() {
		return caName;
	}

	public void setCaName(String caName) {
		this.caName = caName;
	}

	public String getLtpName() {
		return ltpName;
	}

	public void setLtpName(String ltpName) {
		this.ltpName = ltpName;
	}

	public long getLtpId() {
		return ltpId;
	}

	public void setLtpId(long ltpId) {
		this.ltpId = ltpId;
	}

	public String getConid() {
		return conid;
	}

	public void setConid(String conid) {
		this.conid = conid;
	}

	public String getConName() {
		return conName;
	}

	public void setConName(String conName) {
		this.conName = conName;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = CampaignTaskType.fromOrdinal(Integer.valueOf(taskType));
	}

	public Date getLtCreateDate() {
		return ltCreateDate;
	}

	public void setLtCreateDate(Date ltCreateDate) {
		this.ltCreateDate = ltCreateDate;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getLtStatus() {
		return ltStatus;
	}

	public void setLtStatus(String ltStatus) {
		if(StringUtils.isNotBlank(ltStatus)) {
			this.status = ltStatus;
			this.ltStatus = CampaignTaskStatus.fromOrdinal(Integer.valueOf(ltStatus));
		}
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getAppointDate() {
		return appointDate;
	}

	public void setAppointDate(Date appointDate) {
		this.appointDate = appointDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public long getIsPotential() {
		return isPotential;
	}

	public void setIsPotential(long isPotential) {
		this.isPotential = isPotential;
	}

	public String getPdCustomerId() {
		return pdCustomerId;
	}

	public void setPdCustomerId(String pdCustomerId) {
		this.pdCustomerId = pdCustomerId;
	}

	public long getLeadId() {
		return leadId;
	}

	public void setLeadId(long leadId) {
		this.leadId = leadId;
	}

	public Integer getExigency() {
		return exigency;
	}

	public void setExigency(Integer exigency) {
		this.exigency = exigency;
	}

    public long getSourceType() {
        return sourceType;
    }

    public void setSourceType(long sourceType) {
        this.sourceType = sourceType;
        this.sourceTypeDsc = CampaignTaskSourceType.fromOrdinal((int)sourceType);
    }

	public long getSourceType2() {
		return sourceType2;
	}

	public void setSourceType2(long sourceType2) {
		this.sourceType2 = sourceType2;
		if(StringUtils.isNotBlank(sourceTypeDsc) && sourceType2 >= 0) {
			this.sourceTypeDsc = sourceTypeDsc +" "+ CampaignTaskSourceType2.fromOrdinal((int)sourceType2);
		}
	}

	public long getSourceType3() {
		return sourceType3;
	}

	public void setSourceType3(long sourceType3) {
		this.sourceType3 = sourceType3;
		if(StringUtils.isNotBlank(sourceTypeDsc) && sourceType3 >= 0) {
			this.sourceTypeDsc = sourceTypeDsc +" "+ CampaignTaskSourceType3.fromOrdinal((int)sourceType3);
		}
	}

	public String getSourceTypeDsc() {
		return sourceTypeDsc;
	}
}
