/*
 * @(#)CallHist.java 1.0 2013-5-14上午10:23:43
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.model.agent;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;

/**
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author dengqianyong@chinadrtv.com
 * @version 1.0
 * @since 2013-5-14 上午10:23:43 
 * 
 */
@Entity
@Table(name = "CALLHIST", schema = "IAGENT")
public class CallHist implements Serializable {

	private static final long serialVersionUID = 8600481247219821173L;

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(generator = "CALLHIST_SEQ_GEN")
    @GenericGenerator(name = "CALLHIST_SEQ_GEN",strategy="com.chinadrtv.erp.model.CallHistGenerator")
	@Column(name = "CALLID", unique = true, nullable = false, insertable = true, updatable = true, length = 16)
    private String callId;
	
	/**
	 * 呼叫日期
	 */
	@Column(name = "CALLDATE")
	private Date callDate;
	
	/**
	 * 注释
	 */
	@Column(name = "CALLNOTE", length = 2000)
	private String callNote;
	
	/**
	 * 开始时间
	 */
	@Column(name = "STTM",  updatable = true)
	private Date stTm;
	
	/**
	 * 呼叫类型
	 */
	@Column(name = "CALLTYPE", length = 10)
	private String callType;
	
	/**
	 * ACD来电总接电时间(m)
	 */
	@Column(name = "ACDTM",  length = 10)
	private Long acdTm;
	
	/**
	 * 通话结果
	 */
	@Column(name = "RESULT",  length = 10)
	private String result;
	
	/**
	 * 服务人
	 */
	@Column(name = "USRID",length = 10)
	private String userId;
	
	/**
	 * 结束时间
	 */
	@Column(name = "ENDTM",   updatable = true)
	private Date endTm;
	
	/**
	 * 服务组
	 */
	@Column(name = "GRPID",  length = 20)
	private String grpId;
	
	/**
	 * 子类型
	 */
	@Column(name = "SUBTYPE",  length = 10)
	private String subType;
	
	/**
	 * 主叫号码
	 */
	@Column(name = "ANI",  length = 20)
	private String ani;
	
	/**
	 * 被叫号码
	 */
	@Column(name = "DNIS", length = 20)
	private String dnis;
	
	/**
	 * 客户传真号
	 */
	@Column(name = "FAX",  length = 20)
	private String fax;
	
	/**
	 * 客户邮件地址
	 */
	@Column(name = "EMAIL",  length = 128)
	private String email;
	
	/**
	 * ACD总非待机时间(m)
	 */
	@Column(name = "NOTREADYTM",  length = 10)
	private Long notReadyTm;
	
	/**
	 * 电话处于Ready的总时长(m)
	 */
	@Column(name = "READYTM", length = 10)
	private Long readyTm;
	
	/**
	 * DN呼入总时间(m)
	 */
	@Column(name = "DNINTM",  length = 10)
	private Long dnInTm;
	
	/**
	 * DN呼出总时间(m)
	 */
	@Column(name = "DNOUTTM",  length = 10)
	private Long dnOutTm;
	
	/**
	 * 客户编号
	 */
	@Column(name = "CONTACTID",  length = 16)
	private String contactId;
	
	/**
	 * 呼叫类型
	 */
	@Column(name = "histType",  length = 20)
	private String histType;
	
	/**
	 * 客户编号
	 */
	@Column(name = "MEDIAPLANID", length = 16)
	private String mediaPlanId;
	
	@Column(name = "LAST_LOCK_SEQID",  length = 22)
	private Long lastLockSeqid;
	
	@Column(name = "LAST_UPDATE_SEQID",  length = 22)
	private Long lastUpdateSeqid;
	
	@Column(name = "LAST_UPDATE_TIME",  length = 7)
	private Date lastUpdateTime;
	
	/**
	 * 咨询产品ID
	 */
	@Column(name = "PRODID",  length = 30)
	private String prodId;

	public CallHist() {
	}

	public String getCallId() {
		return callId;
	}

	public void setCallId(String callId) {
		this.callId = callId;
	}

	public Date getCallDate() {
		return callDate;
	}

	public void setCallDate(Date callDate) {
		this.callDate = callDate;
	}

	public String getCallNote() {
		return callNote;
	}

	public void setCallNote(String callNote) {
		this.callNote = callNote;
	}

	public Date getStTm() {
		return stTm;
	}

	public void setStTm(Date stTm) {
		this.stTm = stTm;
	}

	public String getCallType() {
		return callType;
	}

	public void setCallType(String callType) {
		this.callType = callType;
	}

	public Long getAcdTm() {
		return acdTm;
	}

	public void setAcdTm(Long acdTm) {
		this.acdTm = acdTm;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getEndTm() {
		return endTm;
	}

	public void setEndTm(Date endTm) {
		this.endTm = endTm;
	}

	public String getGrpId() {
		return grpId;
	}

	public void setGrpId(String grpId) {
		this.grpId = grpId;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public String getAni() {
		return ani;
	}

	public void setAni(String ani) {
		this.ani = ani;
	}

	public String getDnis() {
		return dnis;
	}

	public void setDnis(String dnis) {
		this.dnis = dnis;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getNotReadyTm() {
		return notReadyTm;
	}

	public void setNotReadyTm(Long notReadyTm) {
		this.notReadyTm = notReadyTm;
	}

	public Long getReadyTm() {
		return readyTm;
	}

	public void setReadyTm(Long readyTm) {
		this.readyTm = readyTm;
	}

	public Long getDnInTm() {
		return dnInTm;
	}

	public void setDnInTm(Long dnInTm) {
		this.dnInTm = dnInTm;
	}

	public Long getDnOutTm() {
		return dnOutTm;
	}

	public void setDnOutTm(Long dnOutTm) {
		this.dnOutTm = dnOutTm;
	}

	public String getContactId() {
		return contactId;
	}

	public void setContactId(String contactId) {
		this.contactId = contactId;
	}

	public String getHistType() {
		return histType;
	}

	public void setHistType(String histType) {
		this.histType = histType;
	}

	public String getMediaPlanId() {
		return mediaPlanId;
	}

	public void setMediaPlanId(String mediaPlanId) {
		this.mediaPlanId = mediaPlanId;
	}

	public Long getLastLockSeqid() {
		return lastLockSeqid;
	}

	public void setLastLockSeqid(Long lastLockSeqid) {
		this.lastLockSeqid = lastLockSeqid;
	}

	public Long getLastUpdateSeqid() {
		return lastUpdateSeqid;
	}

	public void setLastUpdateSeqid(Long lastUpdateSeqid) {
		this.lastUpdateSeqid = lastUpdateSeqid;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
	
}
