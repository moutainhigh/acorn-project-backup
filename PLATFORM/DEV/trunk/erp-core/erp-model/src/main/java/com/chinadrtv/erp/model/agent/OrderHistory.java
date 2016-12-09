package com.chinadrtv.erp.model.agent;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 订单快照(TC)
 * User: 王健
 * Date: 13-1-23
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Entity
@Table(name = "ORDER_HISTORY", schema = "IAGENT")
public class OrderHistory implements java.io.Serializable {

    private static final long serialVersionUID = 3596853079065891610L;
    private Long id;
    //private Long orderhistId;
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
    private String transversion;
    private String carddouble;
    private Integer lastLockSeqid;
    private Integer lastUpdateSeqid;
    private Date lastUpdateTime;
    private String isassign;
    private Integer contactidRefId;
    private Integer paycontactidRefId;
    private Integer getcontactidRefId;
    private String returnsstatus;
    private Integer revision;
    private Set<OrderdetHistory> orderdets=new HashSet<OrderdetHistory>();

    // Constructors

    /** default constructor */
    public OrderHistory() {
    }

    /** minimal constructor */
    public OrderHistory(String orderid) {
        this.orderid = orderid;
    }

    /** full constructor */
    public OrderHistory(/*Long orderhistId,*/ String orderid, String mailid,
                        String entityid, Integer spellid, String provinceid, String cityid,
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
                        String addressid, String grpid, String companyid, String spid,
                        String invoicetitle, String customizestatus, String scratchcard,
                        BigDecimal sccardamount, Date rfoutdat, BigInteger expscm, BigInteger expwms,
                        BigInteger issf, Date confirmexpdt, String alipayid, String istrans,
                        Date transdate, String transversion, String carddouble,
                        Integer lastLockSeqid, Integer lastUpdateSeqid,
                        Date lastUpdateTime, String isassign, Integer contactidRefId,
                        Integer paycontactidRefId, Integer getcontactidRefId,
                        String returnsstatus, Integer revision) {
        //this.orderhistId = orderhistId;
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
        this.grpid = grpid;
        this.companyid = companyid;
        this.spid = spid;
        this.invoicetitle = invoicetitle;
        this.customizestatus = customizestatus;
        this.scratchcard = scratchcard;
        this.sccardamount = sccardamount;
        this.rfoutdat = rfoutdat;
        this.expscm = expscm;
        this.expwms = expwms;
        this.issf = issf;
        this.confirmexpdt = confirmexpdt;
        this.alipayid = alipayid;
        this.istrans = istrans;
        this.transdate = transdate;
        this.transversion = transversion;
        this.carddouble = carddouble;
        this.lastLockSeqid = lastLockSeqid;
        this.lastUpdateSeqid = lastUpdateSeqid;
        this.lastUpdateTime = lastUpdateTime;
        this.isassign = isassign;
        this.contactidRefId = contactidRefId;
        this.paycontactidRefId = paycontactidRefId;
        this.getcontactidRefId = getcontactidRefId;
        this.returnsstatus = returnsstatus;
        this.revision = revision;
    }

    @Id
    @Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQORDERHISTORY")
    @SequenceGenerator(name = "SEQORDERHISTORY", sequenceName = "IAGENT.SEQORDERHISTORY")
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ORDERHIST_ID")
    public Order getOrderhist() {
        return orderhist;
    }

    public void setOrderhist(Order orderhist) {
        this.orderhist = orderhist;
    }

    private Order orderhist;

    /*@Column(name = "ORDERHIST_ID", precision = 22, scale = 0)
     public Long getOrderhistId() {
         return this.orderhistId;
     }

     public void setOrderhistId(Long orderhistId) {
         this.orderhistId = orderhistId;
     }*/

    @Column(name = "ORDERID",length = 16)
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

    @Column(name = "TOTALPRICE", precision = 10, scale = 2)
    public BigDecimal getTotalprice() {
        return this.totalprice;
    }

    public void setTotalprice(BigDecimal totalprice) {
        this.totalprice = totalprice;
    }

    @Column(name = "MAILPRICE", precision = 10, scale = 2)
    public BigDecimal getMailprice() {
        return this.mailprice;
    }

    public void setMailprice(BigDecimal mailprice) {
        this.mailprice = mailprice;
    }

    @Column(name = "PRODPRICE", precision = 10, scale = 2)
    public BigDecimal getProdprice() {
        return this.prodprice;
    }

    public void setProdprice(BigDecimal prodprice) {
        this.prodprice = prodprice;
    }

    @Column(name = "NOWMONEY", precision = 10, scale = 2)
    public BigDecimal getNowmoney() {
        return this.nowmoney;
    }

    public void setNowmoney(BigDecimal nowmoney) {
        this.nowmoney = nowmoney;
    }

    @Column(name = "POSTFEE", precision = 10, scale = 2)
    public BigDecimal getPostfee() {
        return this.postfee;
    }

    public void setPostfee(BigDecimal postfee) {
        this.postfee = postfee;
    }

    @Column(name = "CLEARFEE", precision = 10, scale = 2)
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

    @Column(name = "GRPID", length = 20)
    public String getGrpid() {
        return this.grpid;
    }

    public void setGrpid(String grpid) {
        this.grpid = grpid;
    }

    @Column(name = "COMPANYID", length = 50)
    public String getCompanyid() {
        return this.companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    @Column(name = "SPID", length = 10)
    public String getSpid() {
        return this.spid;
    }

    public void setSpid(String spid) {
        this.spid = spid;
    }

    @Column(name = "INVOICETITLE", length = 50)
    public String getInvoicetitle() {
        return this.invoicetitle;
    }

    public void setInvoicetitle(String invoicetitle) {
        this.invoicetitle = invoicetitle;
    }

    @Column(name = "CUSTOMIZESTATUS", length = 10)
    public String getCustomizestatus() {
        return this.customizestatus;
    }

    public void setCustomizestatus(String customizestatus) {
        this.customizestatus = customizestatus;
    }

    @Column(name = "SCRATCHCARD", length = 30)
    public String getScratchcard() {
        return this.scratchcard;
    }

    public void setScratchcard(String scratchcard) {
        this.scratchcard = scratchcard;
    }

    @Column(name = "SCCARDAMOUNT", precision = 10, scale = 2)
    public BigDecimal getSccardamount() {
        return this.sccardamount;
    }

    public void setSccardamount(BigDecimal sccardamount) {
        this.sccardamount = sccardamount;
    }

    @Column(name = "RFOUTDAT", length = 7)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getRfoutdat() {
        return this.rfoutdat;
    }

    public void setRfoutdat(Date rfoutdat) {
        this.rfoutdat = rfoutdat;
    }

    @Column(name = "EXPSCM")
    public BigInteger getExpscm() {
        return this.expscm;
    }

    public void setExpscm(BigInteger expscm) {
        this.expscm = expscm;
    }

    @Column(name = "EXPWMS")
    public BigInteger getExpwms() {
        return this.expwms;
    }

    public void setExpwms(BigInteger expwms) {
        this.expwms = expwms;
    }

    @Column(name = "ISSF")
    public BigInteger getIssf() {
        return this.issf;
    }

    public void setIssf(BigInteger issf) {
        this.issf = issf;
    }

    @Column(name = "CONFIRMEXPDT", length = 7)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getConfirmexpdt() {
        return this.confirmexpdt;
    }

    public void setConfirmexpdt(Date confirmexpdt) {
        this.confirmexpdt = confirmexpdt;
    }

    @Column(name = "ALIPAYID", length = 40)
    public String getAlipayid() {
        return this.alipayid;
    }

    public void setAlipayid(String alipayid) {
        this.alipayid = alipayid;
    }

    @Column(name = "ISTRANS", length = 32)
    public String getIstrans() {
        return this.istrans;
    }

    public void setIstrans(String istrans) {
        this.istrans = istrans;
    }

    @Column(name = "TRANSDATE", length = 7)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getTransdate() {
        return this.transdate;
    }

    public void setTransdate(Date transdate) {
        this.transdate = transdate;
    }

    @Column(name = "TRANSVERSION", length = 46)
    public String getTransversion() {
        return this.transversion;
    }

    public void setTransversion(String transversion) {
        this.transversion = transversion;
    }

    @Column(name = "CARDDOUBLE", length = 510)
    public String getCarddouble() {
        return this.carddouble;
    }

    public void setCarddouble(String carddouble) {
        this.carddouble = carddouble;
    }

    @Column(name = "LAST_LOCK_SEQID")
    public Integer getLastLockSeqid() {
        return this.lastLockSeqid;
    }

    public void setLastLockSeqid(Integer lastLockSeqid) {
        this.lastLockSeqid = lastLockSeqid;
    }

    @Column(name = "LAST_UPDATE_SEQID")
    public Integer getLastUpdateSeqid() {
        return this.lastUpdateSeqid;
    }

    public void setLastUpdateSeqid(Integer lastUpdateSeqid) {
        this.lastUpdateSeqid = lastUpdateSeqid;
    }

    @Column(name = "LAST_UPDATE_TIME", length = 7)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    @Column(name = "ISASSIGN", length = 32)
    public String getIsassign() {
        return this.isassign;
    }

    public void setIsassign(String isassign) {
        this.isassign = isassign;
    }

    @Column(name = "CONTACTID_REF_ID")
    public Integer getContactidRefId() {
        return this.contactidRefId;
    }

    public void setContactidRefId(Integer contactidRefId) {
        this.contactidRefId = contactidRefId;
    }

    @Column(name = "PAYCONTACTID_REF_ID")
    public Integer getPaycontactidRefId() {
        return this.paycontactidRefId;
    }

    public void setPaycontactidRefId(Integer paycontactidRefId) {
        this.paycontactidRefId = paycontactidRefId;
    }

    @Column(name = "GETCONTACTID_REF_ID")
    public Integer getGetcontactidRefId() {
        return this.getcontactidRefId;
    }

    public void setGetcontactidRefId(Integer getcontactidRefId) {
        this.getcontactidRefId = getcontactidRefId;
    }

    @Column(name = "RETURNSSTATUS", length = 10)
    public String getReturnsstatus() {
        return this.returnsstatus;
    }

    public void setReturnsstatus(String returnsstatus) {
        this.returnsstatus = returnsstatus;
    }

    @Column(name = "REVISION")
    public Integer getRevision() {
        return this.revision;
    }

    public void setRevision(Integer revision) {
        this.revision = revision;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "orderHistory")
    public Set<OrderdetHistory> getOrderdets() {
        return orderdets;
    }

    public void setOrderdets(Set<OrderdetHistory> orderdets) {
        this.orderdets = orderdets;
    }

    /**
     * 是否要求EMS送货（Y-表示要求EMS送货）
     */
    private String isReqEMS;

    @Column(name = "ISREQEMS",  length = 10)
    public String getReqEMS() {
        return isReqEMS;
    }

    public void setReqEMS(String reqEMS) {
        isReqEMS = reqEMS;
    }

    /**
     * 要求走EMS送货的提出人
     */
    private String isReqUsr;

    @Column(name = "ISREQUSR", length = 10)
    public String getReqUsr() {
        return isReqUsr;
    }

    public void setReqUsr(String reqUsr) {
        isReqUsr = reqUsr;
    }

}