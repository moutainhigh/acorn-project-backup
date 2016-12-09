/*
 * @(#)TCallHistDto.java 1.0 2014年1月13日下午3:50:39
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.uc.dto;

import java.util.Date;

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
 * @since 2014年1月13日 下午3:50:39 
 * 
 */
public class TCallHistDto {

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
	
	public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	public String getCallId() {
		return callId;
	}
	public void setCallId(String callId) {
		this.callId = callId;
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
	public String getScallType() {
		return scallType;
	}
	public void setScallType(String scallType) {
		this.scallType = scallType;
	}
	public String getScallGrp() {
		return scallGrp;
	}
	public void setScallGrp(String scallGrp) {
		this.scallGrp = scallGrp;
	}
	public String getEcallType() {
		return ecallType;
	}
	public void setEcallType(String ecallType) {
		this.ecallType = ecallType;
	}
	public String getEcallGrp() {
		return ecallGrp;
	}
	public void setEcallGrp(String ecallGrp) {
		this.ecallGrp = ecallGrp;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCallLen() {
		return callLen;
	}
	public void setCallLen(String callLen) {
		this.callLen = callLen;
	}
	public String getAgentTn() {
		return agentTn;
	}
	public void setAgentTn(String agentTn) {
		this.agentTn = agentTn;
	}
	public String getAgentDn() {
		return agentDn;
	}
	public void setAgentDn(String agentDn) {
		this.agentDn = agentDn;
	}
	public String getAgent() {
		return agent;
	}
	public void setAgent(String agent) {
		this.agent = agent;
	}
	public String getAgentCallType() {
		return agentCallType;
	}
	public void setAgentCallType(String agentCallType) {
		this.agentCallType = agentCallType;
	}
	public String getAgentCallResult() {
		return agentCallResult;
	}
	public void setAgentCallResult(String agentCallResult) {
		this.agentCallResult = agentCallResult;
	}
	public String getAgentStartSpan() {
		return agentStartSpan;
	}
	public void setAgentStartSpan(String agentStartSpan) {
		this.agentStartSpan = agentStartSpan;
	}
	public String getAgentDropSpan() {
		return agentDropSpan;
	}
	public void setAgentDropSpan(String agentDropSpan) {
		this.agentDropSpan = agentDropSpan;
	}
	public String getCallData() {
		return callData;
	}
	public void setCallData(String callData) {
		this.callData = callData;
	}
	public String getDnCount() {
		return dnCount;
	}
	public void setDnCount(String dnCount) {
		this.dnCount = dnCount;
	}
	public Integer getIsStat() {
		return isStat;
	}
	public void setIsStat(Integer isStat) {
		this.isStat = isStat;
	}
	public String getAgentActiveSpan() {
		return agentActiveSpan;
	}
	public void setAgentActiveSpan(String agentActiveSpan) {
		this.agentActiveSpan = agentActiveSpan;
	}
	public String getIsTrans() {
		return isTrans;
	}
	public void setIsTrans(String isTrans) {
		this.isTrans = isTrans;
	}
	public Date getTransDate() {
		return transDate;
	}
	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}
	public String getTransversion() {
		return transversion;
	}
	public void setTransversion(String transversion) {
		this.transversion = transversion;
	}
}
