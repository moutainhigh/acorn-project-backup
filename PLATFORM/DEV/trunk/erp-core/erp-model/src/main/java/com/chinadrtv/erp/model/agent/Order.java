package com.chinadrtv.erp.model.agent;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import com.chinadrtv.erp.model.AddressExt;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
//import org.hibernate.envers.Audited;
//import org.hibernate.envers.NotAudited;

/**
 * 订单信息(TC)
 * User: 徐志凯
 * Date: 13-1-23
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */

@SuppressWarnings("serial")
//@Audited
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@Table(name = "ORDERHIST", schema = "IAGENT")
@Entity
public class Order implements Serializable {

    //private static final long serialVersionUID = 5244135703747963886L;

    /**
     * 序列号
     */
    private Long id;
    /**
     * 订单号
     */
    private String orderid;
    /**
     * 邮件编号
     */
    private String mailid;
    private String entityid;
    private Integer spellid;
    private String provinceid;
    private String cityid;
    private String contactid;
    private String getcontactid;
    private String paycontactid;
    private String crusr;
    private String mdusr;
    private Date mddt;
    private String cbcrusr;
    /**
     * 送货单号(不再使用)
     */
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
    private Date netcrdt;
    private String jifen;
    private Integer ticket;
    private Integer ticketcount;
    private String ani;
    private String adusr;
    private Date addt;
    private String errcode;
    private AddressExt address;
    private String grpid;
    private String companyid;
    private String spid;
    private String invoicetitle;
    private String customizestatus;
    private String scratchcard;
    private BigDecimal sccardamount;
    private Date rfoutdat;
    private BigInteger expscm;
    private BigInteger expwms;
    private BigInteger issf;
    private Date confirmexpdt;
    private String alipayid;
    private String istrans;
    private Date transdate;
    private String netorderid;
    private String transversion;
    //private String carddouble;
    private Integer lastLockSeqid;
    private Integer lastUpdateSeqid;
    private Date lastUpdateTime;
    private String isassign;
    private Integer contactidRefId;
    private Integer paycontactidRefId;
    private Integer getcontactidRefId;
    private String returnsstatus;
    private Integer revision;
    /**
     * 是否要求EMS送货（Y-表示要求EMS送货）
     */
    private String isReqEMS;
    /**
     * 要求走EMS送货的提出人
     */
    private String isReqUsr;

    private Long version;

    private Integer checkResult;

    private String trackRemark;

    private String trackUsr;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDERHIST_SEQ")
    @SequenceGenerator(name = "ORDERHIST_SEQ", sequenceName = "IAGENT.SEQORDERHIST_ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "ORDERID", length = 16)
    @NotNull()
    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    @Column(name = "MAILID", length = 20)
    public String getMailid() {
        return mailid;
    }

    public void setMailid(String mailid) {
        this.mailid = mailid;
    }

    @Column(name = "ENTITYID", length = 16)
    public String getEntityid() {
        return entityid;
    }

    public void setEntityid(String entityid) {
        this.entityid = entityid;
    }


    @Column(name = "SPELLID")
    public Integer getSpellid() {
        return spellid;
    }

    public void setSpellid(Integer spellid) {
        this.spellid = spellid;
    }


    @Column(name = "PROVINCEID", length = 16)
    public String getProvinceid() {
        return provinceid;
    }

    public void setProvinceid(String provinceid) {
        this.provinceid = provinceid;
    }

    @Column(name = "CITYID", length = 10)
    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    @Column(name = "CONTACTID", length = 16)
    public String getContactid() {
        return contactid;
    }

    public void setContactid(String contactid) {
        this.contactid = contactid;
    }

    @Column(name = "PAYCONTACTID", length = 16)
    public String getPaycontactid() {
        return paycontactid;
    }

    public void setPaycontactid(String paycontactid) {
        this.paycontactid = paycontactid;
    }

    @Column(name = "GETCONTACTID", length = 16)
    public String getGetcontactid() {
        return getcontactid;
    }

    public void setGetcontactid(String getcontactid) {
        this.getcontactid = getcontactid;
    }

    @Column(name = "CRUSR", length = 10)
    @NotNull()
    public String getCrusr() {
        return crusr;
    }

    public void setCrusr(String crusr) {
        this.crusr = crusr;
    }

    @Column(name = "MDUSR", length = 10)
    public String getMdusr() {
        return mdusr;
    }

    public void setMdusr(String mdusr) {
        this.mdusr = mdusr;
    }


    @Column(name = "MDDT", length = 7)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getMddt() {
        return mddt;
    }

    public void setMddt(Date mddt) {
        this.mddt = mddt;
    }

    @Column(name = "CBCRUSR", length = 10)
    public String getCbcrusr() {
        return cbcrusr;
    }

    public void setCbcrusr(String cbcrusr) {
        this.cbcrusr = cbcrusr;
    }

    @Column(name = "PARCELNM", length = 10)
    public String getParcelnm() {
        return parcelnm;
    }

    public void setParcelnm(String parcelnm) {
        this.parcelnm = parcelnm;
    }

    @Column(name = "STATUS", length = 10)
    @NotNull()
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "ACCOUNT", length = 20)
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Column(name = "RESULT", length = 10)
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Column(name = "ORDERTYPE", length = 10)
    public String getOrdertype() {
        return ordertype;
    }

    public void setOrdertype(String ordertype) {
        this.ordertype = ordertype;
    }

    @Column(name = "MAILTYPE", length = 20)
    public String getMailtype() {
        return mailtype;
    }

    public void setMailtype(String mailtype) {
        this.mailtype = mailtype;
    }

    @Column(name = "PAYTYPE", length = 10)
    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }

    @Column(name = "URGENT", length = 10)
    public String getUrgent() {
        return urgent;
    }

    public void setUrgent(String urgent) {
        this.urgent = urgent;
    }

    @Column(name = "CONFIRM", length = 10)
    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }


    @Column(name = "CRDT", length = 7)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCrdt() {
        return crdt;
    }

    public void setCrdt(Date crdt) {
        this.crdt = crdt;
    }


    @Column(name = "SENDDT", length = 7)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getSenddt() {
        return senddt;
    }

    public void setSenddt(Date senddt) {
        this.senddt = senddt;
    }


    @Column(name = "FBDT", length = 7)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getFbdt() {
        return fbdt;
    }

    public void setFbdt(Date fbdt) {
        this.fbdt = fbdt;
    }


    @Column(name = "OUTDT", length = 7)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getOutdt() {
        return outdt;
    }

    public void setOutdt(Date outdt) {
        this.outdt = outdt;
    }


    @Column(name = "ACCDT", length = 7)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getAccdt() {
        return accdt;
    }

    public void setAccdt(Date accdt) {
        this.accdt = accdt;
    }

    @Column(name = "TOTALPRICE",  precision = 10, scale = 2)
    public BigDecimal getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(BigDecimal totalprice) {
        this.totalprice = totalprice;
    }

    @Column(name = "MAILPRICE", precision = 10, scale = 2)
    public BigDecimal getMailprice() {
        return mailprice;
    }

    public void setMailprice(BigDecimal mailprice) {
        this.mailprice = mailprice;
    }

    @Column(name = "PRODPRICE", precision = 10, scale = 2)
    public BigDecimal getProdprice() {
        return prodprice;
    }

    public void setProdprice(BigDecimal prodprice) {
        this.prodprice = prodprice;
    }

    @Column(name = "NOWMONEY", precision = 10, scale = 2)
    public BigDecimal getNowmoney() {
        return nowmoney;
    }

    public void setNowmoney(BigDecimal nowmoney) {
        this.nowmoney = nowmoney;
    }

    @Column(name = "POSTFEE", precision = 10, scale = 2)
    public BigDecimal getPostfee() {
        return postfee;
    }

    public void setPostfee(BigDecimal postfee) {
        this.postfee = postfee;
    }

    @Column(name = "CLEARFEE", precision = 10, scale = 2)
    public BigDecimal getClearfee() {
        return clearfee;
    }

    public void setClearfee(BigDecimal clearfee) {
        this.clearfee = clearfee;
    }

    @Column(name = "BILL", length = 10)
    public String getBill() {
        return bill;
    }

    public void setBill(String bill) {
        this.bill = bill;
    }

    @Column(name = "NOTE", length = 100)
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Column(name = "CARDTYPE", length = 20)
    public String getCardtype() {
        return cardtype;
    }

    public void setCardtype(String cardtype) {
        this.cardtype = cardtype;
    }

    @Column(name = "CARDNUMBER", length = 20)
    public String getCardnumber() {
        return cardnumber;
    }

    public void setCardnumber(String cardnumber) {
        this.cardnumber = cardnumber;
    }

    @Column(name = "MEDIA", length = 20)
    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    @Column(name = "CALLID", length = 16)
    public String getCallid() {
        return callid;
    }

    public void setCallid(String callid) {
        this.callid = callid;
    }

    @Column(name = "CALLBACKID", length = 8)
    public String getCallbackid() {
        return callbackid;
    }

    public void setCallbackid(String callbackid) {
        this.callbackid = callbackid;
    }

    @Column(name = "PARENTID", length = 16)
    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    @Column(name = "CHILDID", length = 100)
    public String getChildid() {
        return childid;
    }

    public void setChildid(String childid) {
        this.childid = childid;
    }

    @Column(name = "STARTTM", length = 7)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getStarttm() {
        return starttm;
    }

    public void setStarttm(Date starttm) {
        this.starttm = starttm;
    }

    @Column(name = "ENDTM", length = 7)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getEndtm() {
        return endtm;
    }

    public void setEndtm(Date endtm) {
        this.endtm = endtm;
    }

    @Column(name = "LASTSTATUS", length = 10)
    public String getLaststatus() {
        return laststatus;
    }

    public void setLaststatus(String laststatus) {
        this.laststatus = laststatus;
    }

    @Column(name = "REMARK", length = 10)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "CARDRIGHTNUM", length = 20)
    public String getCardrightnum() {
        return cardrightnum;
    }

    public void setCardrightnum(String cardrightnum) {
        this.cardrightnum = cardrightnum;
    }

    @Column(name = "EMSCLEARSTATUS", length = 10)
    public String getEmsclearstatus() {
        return emsclearstatus;
    }

    public void setEmsclearstatus(String emsclearstatus) {
        this.emsclearstatus = emsclearstatus;
    }

    @Column(name = "REFUSE", length = 1)
    public String getRefuse() {
        return refuse;
    }

    public void setRefuse(String refuse) {
        this.refuse = refuse;
    }

    @Column(name = "PARCDT", length = 7)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getParcdt() {
        return parcdt;
    }

    public void setParcdt(Date parcdt) {
        this.parcdt = parcdt;
    }

    @Column(name = "DNIS", length = 40)
    public String getDnis() {
        return dnis;
    }

    public void setDnis(String dnis) {
        this.dnis = dnis;
    }

    @Column(name = "AREACODE", length = 6)
    public String getAreacode() {
        return areacode;
    }

    public void setAreacode(String areacode) {
        this.areacode = areacode;
    }

    @Column(name = "NETORDERID", length = 40)
    public String getNetorderid() {
        return netorderid;
    }

    public void setNetorderid(String netorderid) {
        this.netorderid = netorderid;
    }

    @Column(name = "NETCRDT", length = 7)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getNetcrdt() {
        return netcrdt;
    }

    public void setNetcrdt(Date netcrdt) {
        this.netcrdt = netcrdt;
    }

    @Column(name = "JIFEN", length = 10)
    public String getJifen() {
        return jifen;
    }

    public void setJifen(String jifen) {
        this.jifen = jifen;
    }

    @Column(name = "TICKET")
    public Integer getTicket() {
        return ticket;
    }

    public void setTicket(Integer ticket) {
        this.ticket = ticket;
    }

    @Column(name = "TICKETCOUNT")
    public Integer getTicketcount() {
        return ticketcount;
    }

    public void setTicketcount(Integer ticketcount) {
        this.ticketcount = ticketcount;
    }

    @Column(name = "ANI", length = 32)
    public String getAni() {
        return ani;
    }

    public void setAni(String ani) {
        this.ani = ani;
    }

    @Column(name = "ADUSR", length = 10)
    public String getAdusr() {
        return adusr;
    }

    public void setAdusr(String adusr) {
        this.adusr = adusr;
    }

    @Column(name = "ADDT", length = 7)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getAddt() {
        return addt;
    }

    public void setAddt(Date addt) {
        this.addt = addt;
    }

    @Column(name = "ERRCODE", length = 10)
    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    @OneToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "addressid", referencedColumnName = "addressid")
    public AddressExt getAddress() {
        return address;
    }

    public void setAddress(AddressExt address) {
        this.address = address;
    }

    @Column(name = "GRPID", length = 20)
    public String getGrpid() {
        return grpid;
    }

    public void setGrpid(String grpid) {
        this.grpid = grpid;
    }

    @Column(name = "COMPANYID", length = 50)
    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    @Column(name = "SPID", length = 10)
    public String getSpid() {
        return spid;
    }

    public void setSpid(String spid) {
        this.spid = spid;
    }

    @Column(name = "INVOICETITLE", length = 50)
    public String getInvoicetitle() {
        return invoicetitle;
    }

    public void setInvoicetitle(String invoicetitle) {
        this.invoicetitle = invoicetitle;
    }

    @Column(name = "CUSTOMIZESTATUS", length = 10)
    public String getCustomizestatus() {
        return customizestatus;
    }

    public void setCustomizestatus(String customizestatus) {
        this.customizestatus = customizestatus;
    }

    @Column(name = "SCRATCHCARD", length = 30)
    public String getScratchcard() {
        return scratchcard;
    }

    public void setScratchcard(String scratchcard) {
        this.scratchcard = scratchcard;
    }

    @Column(name = "SCCARDAMOUNT", precision = 10)
    public BigDecimal getSccardamount() {
        return sccardamount;
    }

    public void setSccardamount(BigDecimal sccardamount) {
        this.sccardamount = sccardamount;
    }

    @Column(name = "RFOUTDAT", length = 7)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getRfoutdat() {
        return rfoutdat;
    }

    public void setRfoutdat(Date rfoutdat) {
        this.rfoutdat = rfoutdat;
    }

    @Column(name = "EXPSCM")
    public BigInteger getExpscm() {
        return expscm;
    }

    public void setExpscm(BigInteger expscm) {
        this.expscm = expscm;
    }

    @Column(name = "EXPWMS")
    public BigInteger getExpwms() {
        return expwms;
    }

    public void setExpwms(BigInteger expwms) {
        this.expwms = expwms;
    }

    @Column(name = "ISSF")
    public BigInteger getIssf() {
        return issf;
    }

    public void setIssf(BigInteger issf) {
        this.issf = issf;
    }

    @Column(name = "CONFIRMEXPDT", length = 7)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getConfirmexpdt() {
        return confirmexpdt;
    }

    public void setConfirmexpdt(Date confirmexpdt) {
        this.confirmexpdt = confirmexpdt;
    }

    @Column(name = "ALIPAYID", length = 40)
    public String getAlipayid() {
        return alipayid;
    }

    public void setAlipayid(String alipayid) {
        this.alipayid = alipayid;
    }

    @Column(name = "ISTRANS", length = 32)
    public String getIstrans() {
        return istrans;
    }

    public void setIstrans(String istrans) {
        this.istrans = istrans;
    }

    @Column(name = "TRANSDATE", length = 7)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getTransdate() {
        return transdate;
    }

    public void setTransdate(Date transdate) {
        this.transdate = transdate;
    }

    @Column(name = "TRANSVERSION", length = 46)
    public String getTransversion() {
        return transversion;
    }

    public void setTransversion(String transversion) {
        this.transversion = transversion;
    }

    /*@Column(name = "CARDDOUBLE", length = 255)
    public String getCarddouble() {
        return carddouble;
    }

    public void setCarddouble(String carddouble) {
        this.carddouble = carddouble;
    }*/

    @Column(name = "LAST_LOCK_SEQID")
    public Integer getLastLockSeqid() {
        return lastLockSeqid;
    }

    public void setLastLockSeqid(Integer lastLockSeqid) {
        this.lastLockSeqid = lastLockSeqid;
    }

    @Column(name = "LAST_UPDATE_SEQID")
    public Integer getLastUpdateSeqid() {
        return lastUpdateSeqid;
    }

    public void setLastUpdateSeqid(Integer lastUpdateSeqid) {
        this.lastUpdateSeqid = lastUpdateSeqid;
    }

    @Column(name = "LAST_UPDATE_TIME",  length = 7)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    @Column(name = "ISASSIGN", length = 32)
    public String getIsassign() {
        return isassign;
    }

    public void setIsassign(String isassign) {
        this.isassign = isassign;
    }

    @Column(name = "CONTACTID_REF_ID")
    public Integer getContactidRefId() {
        return contactidRefId;
    }

    public void setContactidRefId(Integer contactidRefId) {
        this.contactidRefId = contactidRefId;
    }

    @Column(name = "PAYCONTACTID_REF_ID")
    public Integer getPaycontactidRefId() {
        return paycontactidRefId;
    }

    public void setPaycontactidRefId(Integer paycontactidRefId) {
        this.paycontactidRefId = paycontactidRefId;
    }

    @Column(name = "GETCONTACTID_REF_ID")
    public Integer getGetcontactidRefId() {
        return getcontactidRefId;
    }

    public void setGetcontactidRefId(Integer getcontactidRefId) {
        this.getcontactidRefId = getcontactidRefId;
    }

    @Column(name = "RETURNSSTATUS", length = 10)
    public String getReturnsstatus() {
        return returnsstatus;
    }

    public void setReturnsstatus(String returnsstatus) {
        this.returnsstatus = returnsstatus;
    }

    @Column(name = "REVISION")
    public Integer getRevision() {
        return revision==null? 0: revision;
    }

    public void setRevision(Integer revision) {
        this.revision = revision;
    }

    @Column(name = "VERSION")
    @Version
    public Long getVersion()
    {
        return version;
    }

    public void setVersion(Long version)
    {
        this.version=version;
    }

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval =true, fetch = FetchType.LAZY, mappedBy = "orderhist")
    public Set<OrderDetail> getOrderdets() {
        return orderdets;
    }

    public void setOrderdets(Set<OrderDetail> orderdets) {
        this.orderdets = orderdets;
    }

    private Set<OrderDetail> orderdets = new HashSet<OrderDetail>();

    @Column(name = "ISREQEMS", length = 10)
    public String getReqEMS() {
        return isReqEMS;
    }

    public void setReqEMS(String reqEMS) {
        isReqEMS = reqEMS;
    }

    @Column(name = "ISREQUSR", length = 10)
    public String getReqUsr() {
        return isReqUsr;
    }

    public void setReqUsr(String reqUsr) {
        isReqUsr = reqUsr;
    }

    @Column(name = "CHECK_RESULT")
    public Integer getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(Integer checkResult) {
        this.checkResult = checkResult;
    }

    @Column(name = "TRACK_REMARK", length = 500)
    public String getTrackRemark() {
        return trackRemark;
    }

    public void setTrackRemark(String trackRemark) {
        this.trackRemark = trackRemark;
    }

    @Column(name = "TRACK_USR", length = 16)
    public String getTrackUsr() {
        return trackUsr;
    }

    public void setTrackUsr(String trackUsr) {
        this.trackUsr = trackUsr;
    }



    @Override
    public int hashCode() {
        Integer result1 = orderid != null ? orderid.hashCode() : 0;
        return result1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if(id==null&&order.id!=null) return false;
        if(orderid==null&&order.orderid!=null)return false;
        if (!id.equals(order.id)) return false;
        if (!orderid.equals(order.orderid)) return false;

        return true;
    }

}
