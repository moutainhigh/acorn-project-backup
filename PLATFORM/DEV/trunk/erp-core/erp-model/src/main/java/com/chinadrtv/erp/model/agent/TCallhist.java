/*
 * @(#)TCallhist.java 1.0 2014年1月14日上午10:54:38
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.model.agent;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

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
 * @author andrew
 * @version 1.0
 * @since 2014年1月14日 上午10:54:38 
 * 
 */
@Entity
@Table(name = "T_CALLHIST", schema = "IAGENT")
public class TCallhist implements Serializable{
	
	private static final long serialVersionUID = 1571509673124433808L;
	
	private String caseId;
	private String callId;
	private String ani;
	private String dnis;
	private String scallType;
	private String scallGrp;
	private String ecallType;
	private String ecallGrp;
	private Date createTime;
	private String callLen;
	private String agentTn;
	private String agentDn;
	private String agent;
	private String agentCallType;
	private String agentCallResult;
	private String agentStartSpan;
	private String agentDropSpan;
	private String callData;
	private String dnCount;
	private Integer isStat;
	private String agentActiveSpan;
	private String isTrans;
	private Date transDate;
	private String transversion;
	private String bpmInstId;
	
	@Id
    @Column(name = "CASEID", length = 64)
	public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	
	@Column(name = "CALLID", length = 16)
	public String getCallId() {
		return callId;
	}
	public void setCallId(String callId) {
		this.callId = callId;
	}
	
	@Column(name = "ANI", length = 32)
	public String getAni() {
		return ani;
	}
	public void setAni(String ani) {
		this.ani = ani;
	}
	
	@Column(name = "DNIS", length = 32)
	public String getDnis() {
		return dnis;
	}
	public void setDnis(String dnis) {
		this.dnis = dnis;
	}
	
	@Column(name = "SCALLTYPE", length = 8)
	public String getScallType() {
		return scallType;
	}
	public void setScallType(String scallType) {
		this.scallType = scallType;
	}
	
	@Column(name = "SCALLGRP", length = 32)
	public String getScallGrp() {
		return scallGrp;
	}
	public void setScallGrp(String scallGrp) {
		this.scallGrp = scallGrp;
	}
	
	@Column(name = "ECALLTYPE", length = 8)
	public String getEcallType() {
		return ecallType;
	}
	public void setEcallType(String ecallType) {
		this.ecallType = ecallType;
	}
	
	@Column(name = "ECALLGRP", length = 32)
	public String getEcallGrp() {
		return ecallGrp;
	}
	public void setEcallGrp(String ecallGrp) {
		this.ecallGrp = ecallGrp;
	}
	
	@Column(name = "CREATETIME", length = 7)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Column(name = "CALLLEN", length = 8)
	public String getCallLen() {
		return callLen;
	}
	public void setCallLen(String callLen) {
		this.callLen = callLen;
	}
	
	@Column(name = "AGENTTN", length = 8)
	public String getAgentTn() {
		return agentTn;
	}
	public void setAgentTn(String agentTn) {
		this.agentTn = agentTn;
	}
	
	@Column(name = "AGENTDN", length = 32)
	public String getAgentDn() {
		return agentDn;
	}
	public void setAgentDn(String agentDn) {
		this.agentDn = agentDn;
	}
	
	@Column(name = "AGENT", length = 32)
	public String getAgent() {
		return agent;
	}
	public void setAgent(String agent) {
		this.agent = agent;
	}
	
	@Column(name = "AGENTCALLTYPE", length = 8)
	public String getAgentCallType() {
		return agentCallType;
	}
	public void setAgentCallType(String agentCallType) {
		this.agentCallType = agentCallType;
	}
	
	@Column(name = "AGENTCALLRESULT", length = 8)
	public String getAgentCallResult() {
		return agentCallResult;
	}
	public void setAgentCallResult(String agentCallResult) {
		this.agentCallResult = agentCallResult;
	}
	
	@Column(name = "AGENTSTARTSPAN", length = 8)
	public String getAgentStartSpan() {
		return agentStartSpan;
	}
	public void setAgentStartSpan(String agentStartSpan) {
		this.agentStartSpan = agentStartSpan;
	}
	
	@Column(name = "AGENTDROPSPAN", length = 8)
	public String getAgentDropSpan() {
		return agentDropSpan;
	}
	public void setAgentDropSpan(String agentDropSpan) {
		this.agentDropSpan = agentDropSpan;
	}
	
	@Column(name = "CALLDATA", length = 1024)
	public String getCallData() {
		return callData;
	}
	public void setCallData(String callData) {
		this.callData = callData;
	}
	
	@Column(name = "DNCOUNT", length = 8)
	public String getDnCount() {
		return dnCount;
	}
	public void setDnCount(String dnCount) {
		this.dnCount = dnCount;
	}
	
	@Column(name = "ISSTAT")
	public Integer getIsStat() {
		return isStat;
	}
	public void setIsStat(Integer isStat) {
		this.isStat = isStat;
	}
	
	@Column(name = "AGENTACTIVESPAN", length = 8)
	public String getAgentActiveSpan() {
		return agentActiveSpan;
	}
	public void setAgentActiveSpan(String agentActiveSpan) {
		this.agentActiveSpan = agentActiveSpan;
	}
	
	@Column(name = "ISTRANS", length = 32)
	public String getIsTrans() {
		return isTrans;
	}
	public void setIsTrans(String isTrans) {
		this.isTrans = isTrans;
	}
	
	@Column(name = "TRANSDATE", length = 7)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
	public Date getTransDate() {
		return transDate;
	}
	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}
	
	@Column(name = "TRANSVERSION", length = 46)
	public String getTransversion() {
		return transversion;
	}
	public void setTransversion(String transversion) {
		this.transversion = transversion;
	}
	
	@Column(name = "BPM_INST_ID", length = 50)
	public String getBpmInstId() {
		return bpmInstId;
	}
	public void setBpmInstId(String bpmInstId) {
		this.bpmInstId = bpmInstId;
	}
	
}
