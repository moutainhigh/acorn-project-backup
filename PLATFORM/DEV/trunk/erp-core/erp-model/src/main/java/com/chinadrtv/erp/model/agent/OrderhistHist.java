package com.chinadrtv.erp.model.agent;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
* 订单历史(Agent保留)
* User: 王健
* Date: 13-1-23
* 橡果国际-系统集成部
* Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
*/
@Entity
@Table(name = "ORDERHIST_HIST", schema = "IAGENT")
public class OrderhistHist implements java.io.Serializable {

	private static final long serialVersionUID = 6870036253725786190L;
	private Long id;
	private String orderid;
	private String mailid;
	private String entityid;
	private Integer spellid;
	private String provinceid;
	private String cityid;
	private String contactid;
	private String paycontactid;
	private String getcontactid;
	private String crusr;
	private String mdusr;
	private Date mddt;
	private String cbcrusr;
	private String parcelnm;
	private String status;
	private String account;
	private String result;
	private String ordertype;
	private String mailtype;
	private String paytype;
	private String urgent;
	private String confirm;
	private Date crdt;
	private Date senddt;
	private Date fbdt;
	private Date outdt;
	private Date accdt;
	private BigDecimal totalprice;
	private BigDecimal mailprice;
	private BigDecimal prodprice;
	private BigDecimal nowmoney;
	private BigDecimal postfee;
	private BigDecimal clearfee;
	private String bill;
	private String note;
	private String cardtype;
	private String cardnumber;
	private String media;
	private String callid;
	private String callbackid;
	private String parentid;
	private String childid;
	private Date starttm;
	private Date endtm;
	private String laststatus;
	private String remark;
	private String cardrightnum;
	private String emsclearstatus;
	private String refuse;
	private Date parcdt;
	private String dnis;
	private String areacode;
	private String netorderid;
	private Date netcrdt;
	private String jifen;
	private Integer ticket;
	private Integer ticketcount;
	private String ani;
	private String adusr;
	private Date addt;
	private String errcode;
	private String addressid;
	private String histcrusr;
	private Date histcrdt;
	private String grpid;

	// Constructors

	/** default constructor */
	public OrderhistHist() {
	}

	/** minimal constructor */
	public OrderhistHist(String orderid) {
		this.orderid = orderid;
	}

	/** full constructor */
	public OrderhistHist(String orderid, String mailid, String entityid,
			Integer spellid, String provinceid, String cityid,
			String contactid, String paycontactid, String getcontactid,
			String crusr, String mdusr, Date mddt, String cbcrusr,
			String parcelnm, String status, String account, String result,
			String ordertype, String mailtype, String paytype, String urgent,
			String confirm, Date crdt, Date senddt, Date fbdt, Date outdt,
			Date accdt, BigDecimal totalprice, BigDecimal mailprice, BigDecimal prodprice,
			BigDecimal nowmoney, BigDecimal postfee, BigDecimal clearfee, String bill,
			String note, String cardtype, String cardnumber, String media,
			String callid, String callbackid, String parentid, String childid,
			Date starttm, Date endtm, String laststatus, String remark,
			String cardrightnum, String emsclearstatus, String refuse,
			Date parcdt, String dnis, String areacode, String netorderid,
			Date netcrdt, String jifen, Integer ticket, Integer ticketcount,
			String ani, String adusr, Date addt, String errcode,
			String addressid, String histcrusr, Date histcrdt, String grpid) {
		this.orderid = orderid;
		this.mailid = mailid;
		this.entityid = entityid;
		this.spellid = spellid;
		this.provinceid = provinceid;
		this.cityid = cityid;
		this.contactid = contactid;
		this.paycontactid = paycontactid;
		this.getcontactid = getcontactid;
		this.crusr = crusr;
		this.mdusr = mdusr;
		this.mddt = mddt;
		this.cbcrusr = cbcrusr;
		this.parcelnm = parcelnm;
		this.status = status;
		this.account = account;
		this.result = result;
		this.ordertype = ordertype;
		this.mailtype = mailtype;
		this.paytype = paytype;
		this.urgent = urgent;
		this.confirm = confirm;
		this.crdt = crdt;
		this.senddt = senddt;
		this.fbdt = fbdt;
		this.outdt = outdt;
		this.accdt = accdt;
		this.totalprice = totalprice;
		this.mailprice = mailprice;
		this.prodprice = prodprice;
		this.nowmoney = nowmoney;
		this.postfee = postfee;
		this.clearfee = clearfee;
		this.bill = bill;
		this.note = note;
		this.cardtype = cardtype;
		this.cardnumber = cardnumber;
		this.media = media;
		this.callid = callid;
		this.callbackid = callbackid;
		this.parentid = parentid;
		this.childid = childid;
		this.starttm = starttm;
		this.endtm = endtm;
		this.laststatus = laststatus;
		this.remark = remark;
		this.cardrightnum = cardrightnum;
		this.emsclearstatus = emsclearstatus;
		this.refuse = refuse;
		this.parcdt = parcdt;
		this.dnis = dnis;
		this.areacode = areacode;
		this.netorderid = netorderid;
		this.netcrdt = netcrdt;
		this.jifen = jifen;
		this.ticket = ticket;
		this.ticketcount = ticketcount;
		this.ani = ani;
		this.adusr = adusr;
		this.addt = addt;
		this.errcode = errcode;
		this.addressid = addressid;
		this.histcrusr = histcrusr;
		this.histcrdt = histcrdt;
		this.grpid = grpid;
	}
	
	@Id
	@SequenceGenerator(name = "SEQORDERHIST_HIST", sequenceName="IAGENT.SEQORDERHIST_HIST")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQORDERHIST_HIST")
	@Column(name = "ID", unique = true, nullable = false, length = 16)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "ORDERID", nullable = false, length = 16)
	public String getOrderid() {
		return this.orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	@Column(name = "MAILID", length = 20)
	public String getMailid() {
		return this.mailid;
	}

	public void setMailid(String mailid) {
		this.mailid = mailid;
	}

	@Column(name = "ENTITYID", length = 16)
	public String getEntityid() {
		return this.entityid;
	}

	public void setEntityid(String entityid) {
		this.entityid = entityid;
	}

	@Column(name = "SPELLID", precision = 5, scale = 0)
	public Integer getSpellid() {
		return this.spellid;
	}

	public void setSpellid(Integer spellid) {
		this.spellid = spellid;
	}

	@Column(name = "PROVINCEID", length = 16)
	public String getProvinceid() {
		return this.provinceid;
	}

	public void setProvinceid(String provinceid) {
		this.provinceid = provinceid;
	}

	@Column(name = "CITYID", length = 10)
	public String getCityid() {
		return this.cityid;
	}

	public void setCityid(String cityid) {
		this.cityid = cityid;
	}

	@Column(name = "CONTACTID", length = 16)
	public String getContactid() {
		return this.contactid;
	}

	public void setContactid(String contactid) {
		this.contactid = contactid;
	}

	@Column(name = "PAYCONTACTID", length = 16)
	public String getPaycontactid() {
		return this.paycontactid;
	}

	public void setPaycontactid(String paycontactid) {
		this.paycontactid = paycontactid;
	}

	@Column(name = "GETCONTACTID", length = 16)
	public String getGetcontactid() {
		return this.getcontactid;
	}

	public void setGetcontactid(String getcontactid) {
		this.getcontactid = getcontactid;
	}

	@Column(name = "CRUSR", length = 10)
	public String getCrusr() {
		return this.crusr;
	}

	public void setCrusr(String crusr) {
		this.crusr = crusr;
	}

	@Column(name = "MDUSR", length = 10)
	public String getMdusr() {
		return this.mdusr;
	}

	public void setMdusr(String mdusr) {
		this.mdusr = mdusr;
	}

	@Column(name = "MDDT", length = 7)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
	public Date getMddt() {
		return this.mddt;
	}

	public void setMddt(Date mddt) {
		this.mddt = mddt;
	}

	@Column(name = "CBCRUSR", length = 10)
	public String getCbcrusr() {
		return this.cbcrusr;
	}

	public void setCbcrusr(String cbcrusr) {
		this.cbcrusr = cbcrusr;
	}

	@Column(name = "PARCELNM", length = 10)
	public String getParcelnm() {
		return this.parcelnm;
	}

	public void setParcelnm(String parcelnm) {
		this.parcelnm = parcelnm;
	}

	@Column(name = "STATUS", length = 10)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "ACCOUNT", length = 20)
	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	@Column(name = "RESULT", length = 10)
	public String getResult() {
		return this.result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Column(name = "ORDERTYPE", length = 10)
	public String getOrdertype() {
		return this.ordertype;
	}

	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}

	@Column(name = "MAILTYPE", length = 20)
	public String getMailtype() {
		return this.mailtype;
	}

	public void setMailtype(String mailtype) {
		this.mailtype = mailtype;
	}

	@Column(name = "PAYTYPE", length = 10)
	public String getPaytype() {
		return this.paytype;
	}

	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}

	@Column(name = "URGENT", length = 10)
	public String getUrgent() {
		return this.urgent;
	}

	public void setUrgent(String urgent) {
		this.urgent = urgent;
	}

	@Column(name = "CONFIRM", length = 10)
	public String getConfirm() {
		return this.confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}

	@Column(name = "CRDT", length = 7)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
	public Date getCrdt() {
		return this.crdt;
	}

	public void setCrdt(Date crdt) {
		this.crdt = crdt;
	}

	@Column(name = "SENDDT", length = 7)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
	public Date getSenddt() {
		return this.senddt;
	}

	public void setSenddt(Date senddt) {
		this.senddt = senddt;
	}

	@Column(name = "FBDT", length = 7)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
	public Date getFbdt() {
		return this.fbdt;
	}

	public void setFbdt(Date fbdt) {
		this.fbdt = fbdt;
	}

	@Column(name = "OUTDT", length = 7)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
	public Date getOutdt() {
		return this.outdt;
	}

	public void setOutdt(Date outdt) {
		this.outdt = outdt;
	}

	@Column(name = "ACCDT", length = 7)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
	public Date getAccdt() {
		return this.accdt;
	}

	public void setAccdt(Date accdt) {
		this.accdt = accdt;
	}

	@Column(name = "TOTALPRICE", precision = 10)
	public BigDecimal getTotalprice() {
		return this.totalprice;
	}

	public void setTotalprice(BigDecimal totalprice) {
		this.totalprice = totalprice;
	}

	@Column(name = "MAILPRICE", precision = 10)
	public BigDecimal getMailprice() {
		return this.mailprice;
	}

	public void setMailprice(BigDecimal mailprice) {
		this.mailprice = mailprice;
	}

	@Column(name = "PRODPRICE", precision = 10)
	public BigDecimal getProdprice() {
		return this.prodprice;
	}

	public void setProdprice(BigDecimal prodprice) {
		this.prodprice = prodprice;
	}

	@Column(name = "NOWMONEY", precision = 10)
	public BigDecimal getNowmoney() {
		return this.nowmoney;
	}

	public void setNowmoney(BigDecimal nowmoney) {
		this.nowmoney = nowmoney;
	}

	@Column(name = "POSTFEE", precision = 10)
	public BigDecimal getPostfee() {
		return this.postfee;
	}

	public void setPostfee(BigDecimal postfee) {
		this.postfee = postfee;
	}

	@Column(name = "CLEARFEE", precision = 10)
	public BigDecimal getClearfee() {
		return this.clearfee;
	}

	public void setClearfee(BigDecimal clearfee) {
		this.clearfee = clearfee;
	}

	@Column(name = "BILL", length = 10)
	public String getBill() {
		return this.bill;
	}

	public void setBill(String bill) {
		this.bill = bill;
	}

	@Column(name = "NOTE", length = 100)
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Column(name = "CARDTYPE", length = 20)
	public String getCardtype() {
		return this.cardtype;
	}

	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}

	@Column(name = "CARDNUMBER", length = 20)
	public String getCardnumber() {
		return this.cardnumber;
	}

	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}

	@Column(name = "MEDIA", length = 20)
	public String getMedia() {
		return this.media;
	}

	public void setMedia(String media) {
		this.media = media;
	}

	@Column(name = "CALLID", length = 16)
	public String getCallid() {
		return this.callid;
	}

	public void setCallid(String callid) {
		this.callid = callid;
	}

	@Column(name = "CALLBACKID", length = 8)
	public String getCallbackid() {
		return this.callbackid;
	}

	public void setCallbackid(String callbackid) {
		this.callbackid = callbackid;
	}

	@Column(name = "PARENTID", length = 16)
	public String getParentid() {
		return this.parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	@Column(name = "CHILDID", length = 100)
	public String getChildid() {
		return this.childid;
	}

	public void setChildid(String childid) {
		this.childid = childid;
	}

	@Column(name = "STARTTM", length = 7)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
	public Date getStarttm() {
		return this.starttm;
	}

	public void setStarttm(Date starttm) {
		this.starttm = starttm;
	}

	@Column(name = "ENDTM", length = 7)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
	public Date getEndtm() {
		return this.endtm;
	}

	public void setEndtm(Date endtm) {
		this.endtm = endtm;
	}

	@Column(name = "LASTSTATUS", length = 10)
	public String getLaststatus() {
		return this.laststatus;
	}

	public void setLaststatus(String laststatus) {
		this.laststatus = laststatus;
	}

	@Column(name = "REMARK", length = 10)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "CARDRIGHTNUM", length = 20)
	public String getCardrightnum() {
		return this.cardrightnum;
	}

	public void setCardrightnum(String cardrightnum) {
		this.cardrightnum = cardrightnum;
	}

	@Column(name = "EMSCLEARSTATUS", length = 10)
	public String getEmsclearstatus() {
		return this.emsclearstatus;
	}

	public void setEmsclearstatus(String emsclearstatus) {
		this.emsclearstatus = emsclearstatus;
	}

	@Column(name = "REFUSE", length = 1)
	public String getRefuse() {
		return this.refuse;
	}

	public void setRefuse(String refuse) {
		this.refuse = refuse;
	}

	@Column(name = "PARCDT", length = 7)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
	public Date getParcdt() {
		return this.parcdt;
	}

	public void setParcdt(Date parcdt) {
		this.parcdt = parcdt;
	}

	@Column(name = "DNIS", length = 40)
	public String getDnis() {
		return this.dnis;
	}

	public void setDnis(String dnis) {
		this.dnis = dnis;
	}

	@Column(name = "AREACODE", length = 6)
	public String getAreacode() {
		return this.areacode;
	}

	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}

	@Column(name = "NETORDERID", length = 40)
	public String getNetorderid() {
		return this.netorderid;
	}

	public void setNetorderid(String netorderid) {
		this.netorderid = netorderid;
	}

	@Column(name = "NETCRDT", length = 7)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
	public Date getNetcrdt() {
		return this.netcrdt;
	}

	public void setNetcrdt(Date netcrdt) {
		this.netcrdt = netcrdt;
	}

	@Column(name = "JIFEN", length = 10)
	public String getJifen() {
		return this.jifen;
	}

	public void setJifen(String jifen) {
		this.jifen = jifen;
	}

	@Column(name = "TICKET", precision = 10, scale = 0)
	public Integer getTicket() {
		return this.ticket;
	}

	public void setTicket(Integer ticket) {
		this.ticket = ticket;
	}

	@Column(name = "TICKETCOUNT", precision = 10, scale = 0)
	public Integer getTicketcount() {
		return this.ticketcount;
	}

	public void setTicketcount(Integer ticketcount) {
		this.ticketcount = ticketcount;
	}

	@Column(name = "ANI", length = 32)
	public String getAni() {
		return this.ani;
	}

	public void setAni(String ani) {
		this.ani = ani;
	}

	@Column(name = "ADUSR", length = 10)
	public String getAdusr() {
		return this.adusr;
	}

	public void setAdusr(String adusr) {
		this.adusr = adusr;
	}

	@Column(name = "ADDT", length = 7)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
	public Date getAddt() {
		return this.addt;
	}

	public void setAddt(Date addt) {
		this.addt = addt;
	}

	@Column(name = "ERRCODE", length = 10)
	public String getErrcode() {
		return this.errcode;
	}

	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}

	@Column(name = "ADDRESSID", length = 100)
	public String getAddressid() {
		return this.addressid;
	}

	public void setAddressid(String addressid) {
		this.addressid = addressid;
	}

	@Column(name = "HISTCRUSR", length = 10)
	public String getHistcrusr() {
		return this.histcrusr;
	}

	public void setHistcrusr(String histcrusr) {
		this.histcrusr = histcrusr;
	}

	@Column(name = "HISTCRDT", length = 7)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
	public Date getHistcrdt() {
		return this.histcrdt;
	}

	public void setHistcrdt(Date histcrdt) {
		this.histcrdt = histcrdt;
	}

	@Column(name = "GRPID", length = 20)
	public String getGrpid() {
		return this.grpid;
	}

	public void setGrpid(String grpid) {
		this.grpid = grpid;
	}

}