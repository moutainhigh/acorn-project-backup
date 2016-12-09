/**
 * Copyright (c) 2013, Acorn and/or its affiliates. All rights reserved.
 * ACORN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
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
 * 2013-7-24 下午4:25:10
 * @version 1.0.0
 * @author yangfei
 *
 */
@Entity
@Table(name = "PROBLEMATICORDER", schema = "IAGENT")
public class ProblematicOrder implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String orderId;
	private String problemType;
	private String problemStatus;
	private String problemDsc;
	private String replyDsc;
	private Date createdDate;
	private String createdUsr;
	private Date importDate;
	private Date replyDate;
	private Date accomplishDate;
	private String accoomplishUsr;
	private String contact;
	private Date orderDate;
	private String orderUsr;
	
    @Id
    @Column(name = "ID")
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqproblematicorder")
//    @SequenceGenerator(name = "seqproblematicorder", sequenceName = "IAGENT.seqproblematicorder")
    @GeneratedValue(generator = "seqproblematicorder")
    @GenericGenerator(
            name = "seqproblematicorder",
            strategy = "com.chinadrtv.erp.model.agent.ProblematicOrderIdGenerator")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "ORDERID")
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	@Column(name = "PROBLEMTYPE")
	public String getProblemType() {
		return problemType;
	}
	public void setProblemType(String problemType) {
		this.problemType = problemType;
	}
	
	@Column(name = "PROBLEMSTATUS")
	public String getProblemStatus() {
		return problemStatus;
	}
	public void setProblemStatus(String problemStatus) {
		this.problemStatus = problemStatus;
	}
	
	@Column(name = "PROBLEMDSC")
	public String getProblemDsc() {
		return problemDsc;
	}
	public void setProblemDsc(String problemDsc) {
		this.problemDsc = problemDsc;
	}
	
	@Column(name = "REPLYDSC")
	public String getReplyDsc() {
		return replyDsc;
	}
	public void setReplyDsc(String replyDsc) {
		this.replyDsc = replyDsc;
	}
	
	@Column(name = "CRDT")
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	@Column(name = "CRUSR")
	public String getCreatedUsr() {
		return createdUsr;
	}
	public void setCreatedUsr(String createdUsr) {
		this.createdUsr = createdUsr;
	}
	
	@Column(name = "IMDT")
	public Date getImportDate() {
		return importDate;
	}
	public void setImportDate(Date importDate) {
		this.importDate = importDate;
	}
	
	@Column(name = "REDT")
	public Date getReplyDate() {
		return replyDate;
	}
	public void setReplyDate(Date replyDate) {
		this.replyDate = replyDate;
	}
	
	@Column(name = "ACDT")
	public Date getAccomplishDate() {
		return accomplishDate;
	}
	public void setAccomplishDate(Date accomplishDate) {
		this.accomplishDate = accomplishDate;
	}
	
	@Column(name = "ACUSR")
	public String getAccoomplishUsr() {
		return accoomplishUsr;
	}
	public void setAccoomplishUsr(String accoomplishUsr) {
		this.accoomplishUsr = accoomplishUsr;
	}
	
	@Column(name = "CONTACT")
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	
	@Column(name = "OCRDT")
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	
	@Column(name = "OCRUSR")
	public String getOrderUsr() {
		return orderUsr;
	}
	public void setOrderUsr(String orderUsr) {
		this.orderUsr = orderUsr;
	}	
}
