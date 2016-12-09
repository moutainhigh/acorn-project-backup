/*
 * @(#)ObCustomerDto.java 1.0 2013-5-9下午3:27:08
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
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
 * @since 2013-5-9 下午3:27:08 
 * 
 */
public class ObCustomerDto{
	private static final long serialVersionUID = -651547427797050603L;
	private String contactid;
	private String name;
	private String sex;
	private String title;
	private String dept;
	private String contacttype;
	private String email;
	private String webaddr;
	private java.util.Date crdt;
	private java.util.Date crtm;
	private String crusr;
	private java.util.Date mddt;
	private java.util.Date mdtm;
	private String mdusr;
	private String entityid;
	private java.util.Date birthday;
	private String ages;
	private String education;
	private String income;
	private String marriage;
	private String occupation;
	private String consumer;
	private String pin;
	private Long total;
	private String purpose;
	private String netcontactid;
	private String jifen;
	private String areacode;
	private Long ticketvalue;
	private String fancy;
	private String idcardFlag;
	private String attitude;
	private String membertype;
	private String membertypeLabel;
	private String memberlevel;
	private String memberlevelLabel;
	private java.util.Date lastdate;
	private Integer totalfrequency;
	private Long totalmonetary;
	private String sundept;
	private Integer credit;
	private String customersource;
	private String datatype;
	private String car;
	private String creditcard;
	private String children;
	private java.util.Date childrenage;
	private java.lang.Integer carmoney1;
	private java.lang.Integer carmoney2;
	private String dnis;
	private String caseid;
	
	private String comments;
	private Integer customerType;
	private String phoneType;
	private String phoneNo;
	private String agentId;
	/*联系结果*/
	private String resultCode;
	private String resultDesc;
	/*最近联系时间*/
	private Date lastCallDate;
	/*客户归属*/
	private Integer customerOwner;
	/*客户来源*/
	private Integer customerFrom;
	/*待办任务数量*/
	private Integer taskQty;
	/*工作流编号*/
	private Integer instId;
	/*流程创建时间*/
	private Date applyDate;
	/*流程状态*/
	private String instStatus;
    /*流程审核类型*/
    private String auditTaskType;
    //老客户绑定时间
    private Date bindBeginDate;
    private Date bindEndDate;
    //总下单数
    private Integer totalOrderCount;
    //总成单数
    private Integer totalFinishCount;
	
	/**
	 * 如果是自主取数：时间为ob_contact表的 assigntime
	 * 如果是历史成单客户：时间为orderhist表的crdt
	 */
	private Date beginDate;
	private Date endDate;

    public String getAuditTaskType() {
        return auditTaskType;
    }

    public void setAuditTaskType(String auditTaskType) {
        this.auditTaskType = auditTaskType;
    }

    public Integer getCustomerType() {
		return customerType;
	}
	public void setCustomerType(Integer customerType) {
		this.customerType = customerType;
	}
	public String getAgentId() {
		return agentId;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	public Integer getCustomerOwner() {
		return customerOwner;
	}
	public void setCustomerOwner(Integer customerOwner) {
		this.customerOwner = customerOwner;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultDesc() {
		return resultDesc;
	}
	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}
	public Date getLastCallDate() {
		return lastCallDate;
	}
	public void setLastCallDate(Date lastCallDate) {
		this.lastCallDate = lastCallDate;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getContactid() {
		return contactid;
	}
	public void setContactid(String contactid) {
		this.contactid = contactid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getContacttype() {
		return contacttype;
	}
	public void setContacttype(String contacttype) {
		this.contacttype = contacttype;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getWebaddr() {
		return webaddr;
	}
	public void setWebaddr(String webaddr) {
		this.webaddr = webaddr;
	}
	public java.util.Date getCrdt() {
		return crdt;
	}
	public void setCrdt(java.util.Date crdt) {
		this.crdt = crdt;
	}
	public java.util.Date getCrtm() {
		return crtm;
	}
	public void setCrtm(java.util.Date crtm) {
		this.crtm = crtm;
	}
	public String getCrusr() {
		return crusr;
	}
	public void setCrusr(String crusr) {
		this.crusr = crusr;
	}
	public java.util.Date getMddt() {
		return mddt;
	}
	public void setMddt(java.util.Date mddt) {
		this.mddt = mddt;
	}
	public java.util.Date getMdtm() {
		return mdtm;
	}
	public void setMdtm(java.util.Date mdtm) {
		this.mdtm = mdtm;
	}
	public String getMdusr() {
		return mdusr;
	}
	public void setMdusr(String mdusr) {
		this.mdusr = mdusr;
	}
	public String getEntityid() {
		return entityid;
	}
	public void setEntityid(String entityid) {
		this.entityid = entityid;
	}
	public java.util.Date getBirthday() {
		return birthday;
	}
	public void setBirthday(java.util.Date birthday) {
		this.birthday = birthday;
	}
	public String getAges() {
		return ages;
	}
	public void setAges(String ages) {
		this.ages = ages;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getIncome() {
		return income;
	}
	public void setIncome(String income) {
		this.income = income;
	}
	public String getMarriage() {
		return marriage;
	}
	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public String getConsumer() {
		return consumer;
	}
	public void setConsumer(String consumer) {
		this.consumer = consumer;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getNetcontactid() {
		return netcontactid;
	}
	public void setNetcontactid(String netcontactid) {
		this.netcontactid = netcontactid;
	}
	public String getJifen() {
		return jifen;
	}
	public void setJifen(String jifen) {
		this.jifen = jifen;
	}
	public String getAreacode() {
		return areacode;
	}
	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}
	public Long getTicketvalue() {
		return ticketvalue;
	}
	public void setTicketvalue(Long ticketvalue) {
		this.ticketvalue = ticketvalue;
	}
	public String getFancy() {
		return fancy;
	}
	public void setFancy(String fancy) {
		this.fancy = fancy;
	}
	public String getIdcardFlag() {
		return idcardFlag;
	}
	public void setIdcardFlag(String idcardFlag) {
		this.idcardFlag = idcardFlag;
	}
	public String getAttitude() {
		return attitude;
	}
	public void setAttitude(String attitude) {
		this.attitude = attitude;
	}
	public String getMembertype() {
		return membertype;
	}
	public void setMembertype(String membertype) {
		this.membertype = membertype;
	}
	public String getMemberlevel() {
		return memberlevel;
	}
	public void setMemberlevel(String memberlevel) {
		this.memberlevel = memberlevel;
	}
	public java.util.Date getLastdate() {
		return lastdate;
	}
	public void setLastdate(java.util.Date lastdate) {
		this.lastdate = lastdate;
	}
	public Integer getTotalfrequency() {
		return totalfrequency;
	}
	public void setTotalfrequency(Integer totalfrequency) {
		this.totalfrequency = totalfrequency;
	}
	public Long getTotalmonetary() {
		return totalmonetary;
	}
	public void setTotalmonetary(Long totalmonetary) {
		this.totalmonetary = totalmonetary;
	}
	public String getSundept() {
		return sundept;
	}
	public void setSundept(String sundept) {
		this.sundept = sundept;
	}
	public Integer getCredit() {
		return credit;
	}
	public void setCredit(Integer credit) {
		this.credit = credit;
	}
	public String getCustomersource() {
		return customersource;
	}
	public void setCustomersource(String customersource) {
		this.customersource = customersource;
	}
	public String getDatatype() {
		return datatype;
	}
	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}
	public String getCar() {
		return car;
	}
	public void setCar(String car) {
		this.car = car;
	}
	public String getCreditcard() {
		return creditcard;
	}
	public void setCreditcard(String creditcard) {
		this.creditcard = creditcard;
	}
	public String getChildren() {
		return children;
	}
	public void setChildren(String children) {
		this.children = children;
	}
	public java.util.Date getChildrenage() {
		return childrenage;
	}
	public void setChildrenage(java.util.Date childrenage) {
		this.childrenage = childrenage;
	}
	public java.lang.Integer getCarmoney1() {
		return carmoney1;
	}
	public void setCarmoney1(java.lang.Integer carmoney1) {
		this.carmoney1 = carmoney1;
	}
	public java.lang.Integer getCarmoney2() {
		return carmoney2;
	}
	public void setCarmoney2(java.lang.Integer carmoney2) {
		this.carmoney2 = carmoney2;
	}
	public String getDnis() {
		return dnis;
	}
	public void setDnis(String dnis) {
		this.dnis = dnis;
	}
	public String getCaseid() {
		return caseid;
	}
	public void setCaseid(String caseid) {
		this.caseid = caseid;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Integer getCustomerFrom() {
		return customerFrom;
	}
	public void setCustomerFrom(Integer customerFrom) {
		this.customerFrom = customerFrom;
	}
	public Integer getTaskQty() {
		return taskQty;
	}
	public void setTaskQty(Integer taskQty) {
		this.taskQty = taskQty;
	}
	public String getPhoneType() {
		return phoneType;
	}
	public void setPhoneType(String phoneType) {
		this.phoneType = phoneType;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public Integer getInstId() {
		return instId;
	}
	public void setInstId(Integer instId) {
		this.instId = instId;
	}
	public Date getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	public String getInstStatus() {
		return instStatus;
	}
	public void setInstStatus(String instStatus) {
		this.instStatus = instStatus;
	}
	public String getMembertypeLabel() {
		return membertypeLabel;
	}
	public void setMembertypeLabel(String membertypeLabel) {
		this.membertypeLabel = membertypeLabel;
	}
	public String getMemberlevelLabel() {
		return memberlevelLabel;
	}
	public void setMemberlevelLabel(String memberlevelLabel) {
		this.memberlevelLabel = memberlevelLabel;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}

	public Date getBindBeginDate() {
		return bindBeginDate;
	}

	public void setBindBeginDate(Date bindBeginDate) {
		this.bindBeginDate = bindBeginDate;
	}

	public Date getBindEndDate() {
		return bindEndDate;
	}

	public void setBindEndDate(Date bindEndDate) {
		this.bindEndDate = bindEndDate;
	}

	public Integer getTotalOrderCount() {
		return totalOrderCount;
	}

	public void setTotalOrderCount(Integer totalOrderCount) {
		this.totalOrderCount = totalOrderCount;
	}

	public Integer getTotalFinishCount() {
		return totalFinishCount;
	}

	public void setTotalFinishCount(Integer totalFinishCount) {
		this.totalFinishCount = totalFinishCount;
	}
	
}
