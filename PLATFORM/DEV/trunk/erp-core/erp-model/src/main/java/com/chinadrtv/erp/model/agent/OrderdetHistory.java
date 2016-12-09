package com.chinadrtv.erp.model.agent;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 订单产品快照(TC)
 * User: 王健
 * Date: 13-1-23
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Entity
@Table(name = "ORDERDET_HISTORY", schema = "IAGENT")
public class OrderdetHistory implements java.io.Serializable {

    private static final long serialVersionUID = -4363566411701272355L;
    private Long id;
    //private String orderdetId;
    private String orderid;
    private String prodid;
    private String contactid;
    private String prodscode;
    private String prodname;
    private String soldwith;
    private String status;
    private String reckoning;
    private Date reckoningdt;
    private Date fbdt;
    private BigDecimal uprice;
    private Integer upnum;
    private BigDecimal sprice;
    private Integer spnum;
    private BigDecimal payment;
    private BigDecimal freight;
    private BigDecimal postfee;
    private BigDecimal clearfee;
    private Date orderdt;
    private String provinceid;
    private String state;
    private String city;
    private String mdusr;
    private String breason;
    private String feedback;
    private String goodsback;
    private String producttype;
    private Date backdt;
    private Integer backmoney;
    private String oldprod;
    private BigDecimal compensate;
    private String purpose;
    private String jifen;
    private Integer ticket;
    private String num1;
    private String num2;
    private String baleprodid;
    private String cardrightnum;
    private BigDecimal accountingcost;
    private String spid;
    private String prodbankid;
    private String scratchcard;
    private BigDecimal sccardamount;
    private String catalogno;
    private String promotionsdocno;
    private Integer promotionsdetruid;
    private Integer lastLockSeqid;
    private Integer lastUpdateSeqid;
    private Date lastUpdateTime;
    private Integer orderRefId;
    //private Integer productRefId;
    private Integer revision;

    private OrderHistory orderHistory;

    // Constructors

    /** default constructor */
    public OrderdetHistory() {
    }

    /** full constructor */
    public OrderdetHistory(OrderDetail orderdet, String orderid, String prodid,
                           String contactid, String prodscode, String prodname,
                           String soldwith, String status, String reckoning, Date reckoningdt,
                           Date fbdt, BigDecimal uprice, Integer upnum, BigDecimal sprice, Integer spnum,
                           BigDecimal payment, BigDecimal freight, BigDecimal postfee, BigDecimal clearfee,
                           Date orderdt, String provinceid, String state, String city,
                           String mdusr, String breason, String feedback, String goodsback,
                           String producttype, Date backdt, Integer backmoney,
                           String oldprod, BigDecimal compensate, String purpose, String jifen,
                           Integer ticket, String num1, String num2, String baleprodid,
                           String cardrightnum, BigDecimal accountingcost, String spid,
                           String prodbankid, String scratchcard, BigDecimal sccardamount,
                           String catalogno, String promotionsdocno,
                           Integer promotionsdetruid, Integer lastLockSeqid,
                           Integer lastUpdateSeqid, Date lastUpdateTime,
                           Integer orderRefId, /*Integer productRefId,*/Product product, Integer revision) {
        this.orderdet=orderdet;
        this.orderid = orderid;
        this.prodid = prodid;
        this.contactid = contactid;
        this.prodscode = prodscode;
        this.prodname = prodname;
        this.soldwith = soldwith;
        this.status = status;
        this.reckoning = reckoning;
        this.reckoningdt = reckoningdt;
        this.fbdt = fbdt;
        this.uprice = uprice;
        this.upnum = upnum;
        this.sprice = sprice;
        this.spnum = spnum;
        this.payment = payment;
        this.freight = freight;
        this.postfee = postfee;
        this.clearfee = clearfee;
        this.orderdt = orderdt;
        this.provinceid = provinceid;
        this.state = state;
        this.city = city;
        this.mdusr = mdusr;
        this.breason = breason;
        this.feedback = feedback;
        this.goodsback = goodsback;
        this.producttype = producttype;
        this.backdt = backdt;
        this.backmoney = backmoney;
        this.oldprod = oldprod;
        this.compensate = compensate;
        this.purpose = purpose;
        this.jifen = jifen;
        this.ticket = ticket;
        this.num1 = num1;
        this.num2 = num2;
        this.baleprodid = baleprodid;
        this.cardrightnum = cardrightnum;
        this.accountingcost = accountingcost;
        this.spid = spid;
        this.prodbankid = prodbankid;
        this.scratchcard = scratchcard;
        this.sccardamount = sccardamount;
        this.catalogno = catalogno;
        this.promotionsdocno = promotionsdocno;
        this.promotionsdetruid = promotionsdetruid;
        this.lastLockSeqid = lastLockSeqid;
        this.lastUpdateSeqid = lastUpdateSeqid;
        this.lastUpdateTime = lastUpdateTime;
        this.orderRefId = orderRefId;
        //this.productRefId = productRefId;
        this.product=product;
        this.revision = revision;
    }

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQORDERDETHISTORY")
    @SequenceGenerator(name = "SEQORDERDETHISTORY", sequenceName = "IAGENT.SEQORDERDETHISTORY")
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ORDERDET_ID")
    public OrderDetail getOrderdet() {
        return orderdet;
    }

    public void setOrderdet(OrderDetail orderdet) {
        this.orderdet = orderdet;
    }

    private OrderDetail orderdet;

    /*@Column(name = "ORDERDET_ID", length = 16)
     public String getOrderdetId() {
         return this.orderdetId;
     }

     public void setOrderdetId(String orderdetId) {
         this.orderdetId = orderdetId;
     }*/

    @Column(name = "ORDERID", length = 16)
    public String getOrderid() {
        return this.orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    @Column(name = "PRODID", length = 16)
    public String getProdid() {
        return this.prodid;
    }

    public void setProdid(String prodid) {
        this.prodid = prodid;
    }

    @Column(name = "CONTACTID", length = 16)
    public String getContactid() {
        return this.contactid;
    }

    public void setContactid(String contactid) {
        this.contactid = contactid;
    }

    @Column(name = "PRODSCODE", length = 10)
    public String getProdscode() {
        return this.prodscode;
    }

    public void setProdscode(String prodscode) {
        this.prodscode = prodscode;
    }

    @Column(name = "PRODNAME", length = 100)
    public String getProdname() {
        return this.prodname;
    }

    public void setProdname(String prodname) {
        this.prodname = prodname;
    }

    @Column(name = "SOLDWITH", length = 10)
    public String getSoldwith() {
        return this.soldwith;
    }

    public void setSoldwith(String soldwith) {
        this.soldwith = soldwith;
    }

    @Column(name = "STATUS", length = 10)
    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "RECKONING", length = 10)
    public String getReckoning() {
        return this.reckoning;
    }

    public void setReckoning(String reckoning) {
        this.reckoning = reckoning;
    }

    @Column(name = "RECKONINGDT", length = 7)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getReckoningdt() {
        return this.reckoningdt;
    }

    public void setReckoningdt(Date reckoningdt) {
        this.reckoningdt = reckoningdt;
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

    @Column(name = "UPRICE", precision = 10, scale = 2)
    public BigDecimal getUprice() {
        return this.uprice;
    }

    public void setUprice(BigDecimal uprice) {
        this.uprice = uprice;
    }

    @Column(name = "UPNUM")
    public Integer getUpnum() {
        return this.upnum;
    }

    public void setUpnum(Integer upnum) {
        this.upnum = upnum;
    }

    @Column(name = "SPRICE", precision = 10, scale = 2)
    public BigDecimal getSprice() {
        return this.sprice;
    }

    public void setSprice(BigDecimal sprice) {
        this.sprice = sprice;
    }

    @Column(name = "SPNUM")
    public Integer getSpnum() {
        return this.spnum;
    }

    public void setSpnum(Integer spnum) {
        this.spnum = spnum;
    }

    @Column(name = "PAYMENT", precision = 10, scale = 2)
    public BigDecimal getPayment() {
        return this.payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    @Column(name = "FREIGHT", precision = 10, scale = 2)
    public BigDecimal getFreight() {
        return this.freight;
    }

    public void setFreight(BigDecimal freight) {
        this.freight = freight;
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

    @Column(name = "ORDERDT", length = 7)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getOrderdt() {
        return this.orderdt;
    }

    public void setOrderdt(Date orderdt) {
        this.orderdt = orderdt;
    }

    @Column(name = "PROVINCEID", length = 10)
    public String getProvinceid() {
        return this.provinceid;
    }

    public void setProvinceid(String provinceid) {
        this.provinceid = provinceid;
    }

    @Column(name = "STATE", length = 10)
    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Column(name = "CITY", length = 22)
    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "MDUSR", length = 10)
    public String getMdusr() {
        return this.mdusr;
    }

    public void setMdusr(String mdusr) {
        this.mdusr = mdusr;
    }

    @Column(name = "BREASON", length = 10)
    public String getBreason() {
        return this.breason;
    }

    public void setBreason(String breason) {
        this.breason = breason;
    }

    @Column(name = "FEEDBACK", length = 10)
    public String getFeedback() {
        return this.feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    @Column(name = "GOODSBACK", length = 10)
    public String getGoodsback() {
        return this.goodsback;
    }

    public void setGoodsback(String goodsback) {
        this.goodsback = goodsback;
    }

    @Column(name = "PRODUCTTYPE", length = 16)
    public String getProducttype() {
        return this.producttype;
    }

    public void setProducttype(String producttype) {
        this.producttype = producttype;
    }

    @Column(name = "BACKDT", length = 7)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getBackdt() {
        return this.backdt;
    }

    public void setBackdt(Date backdt) {
        this.backdt = backdt;
    }

    @Column(name = "BACKMONEY")
    public Integer getBackmoney() {
        return this.backmoney;
    }

    public void setBackmoney(Integer backmoney) {
        this.backmoney = backmoney;
    }

    @Column(name = "OLDPROD", length = 30)
    public String getOldprod() {
        return this.oldprod;
    }

    public void setOldprod(String oldprod) {
        this.oldprod = oldprod;
    }

    @Column(name = "COMPENSATE", precision = 10, scale = 2)
    public BigDecimal getCompensate() {
        return this.compensate;
    }

    public void setCompensate(BigDecimal compensate) {
        this.compensate = compensate;
    }

    @Column(name = "PURPOSE", length = 20)
    public String getPurpose() {
        return this.purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    @Column(name = "JIFEN", length = 10)
    public String getJifen() {
        return this.jifen;
    }

    public void setJifen(String jifen) {
        this.jifen = jifen;
    }

    @Column(name = "TICKET")
    public Integer getTicket() {
        return this.ticket;
    }

    public void setTicket(Integer ticket) {
        this.ticket = ticket;
    }

    @Column(name = "NUM1", length = 16)
    public String getNum1() {
        return this.num1;
    }

    public void setNum1(String num1) {
        this.num1 = num1;
    }

    @Column(name = "NUM2", length = 30)
    public String getNum2() {
        return this.num2;
    }

    public void setNum2(String num2) {
        this.num2 = num2;
    }

    @Column(name = "BALEPRODID", length = 30)
    public String getBaleprodid() {
        return this.baleprodid;
    }

    public void setBaleprodid(String baleprodid) {
        this.baleprodid = baleprodid;
    }

    @Column(name = "CARDRIGHTNUM", length = 20)
    public String getCardrightnum() {
        return this.cardrightnum;
    }

    public void setCardrightnum(String cardrightnum) {
        this.cardrightnum = cardrightnum;
    }

    @Column(name = "ACCOUNTINGCOST", precision = 10, scale = 2)
    public BigDecimal getAccountingcost() {
        return this.accountingcost;
    }

    public void setAccountingcost(BigDecimal accountingcost) {
        this.accountingcost = accountingcost;
    }

    @Column(name = "SPID", length = 10)
    public String getSpid() {
        return this.spid;
    }

    public void setSpid(String spid) {
        this.spid = spid;
    }

    @Column(name = "PRODBANKID", length = 30)
    public String getProdbankid() {
        return this.prodbankid;
    }

    public void setProdbankid(String prodbankid) {
        this.prodbankid = prodbankid;
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

    @Column(name = "CATALOGNO", length = 20)
    public String getCatalogno() {
        return this.catalogno;
    }

    public void setCatalogno(String catalogno) {
        this.catalogno = catalogno;
    }

    @Column(name = "PROMOTIONSDOCNO", length = 20)
    public String getPromotionsdocno() {
        return this.promotionsdocno;
    }

    public void setPromotionsdocno(String promotionsdocno) {
        this.promotionsdocno = promotionsdocno;
    }

    @Column(name = "PROMOTIONSDETRUID")
    public Integer getPromotionsdetruid() {
        return this.promotionsdetruid;
    }

    public void setPromotionsdetruid(Integer promotionsdetruid) {
        this.promotionsdetruid = promotionsdetruid;
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


    @Column(name = "ORDER_REF_ID")
    public Integer getOrderRefId() {
        return this.orderRefId;
    }

    public void setOrderRefId(Integer orderRefId) {
        this.orderRefId = orderRefId;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_REF_ID")
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    private Product product;
    /*@Column(name = "PRODUCT_REF_ID", scale = 0)
     public Integer getProductRefId() {
         return this.productRefId;
     }

     public void setProductRefId(Integer productRefId) {
         this.productRefId = productRefId;
     }*/

    @Column(name = "REVISION")
    public Integer getRevision() {
        return this.revision;
    }

    public void setRevision(Integer revision) {
        this.revision = revision;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ORDER_HISTORY_ID")
    public OrderHistory getOrderHistory() {
        return orderHistory;
    }

    public void setOrderHistory(OrderHistory orderHistory) {
        this.orderHistory = orderHistory;
    }

}