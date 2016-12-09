package com.chinadrtv.erp.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-5-10
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Table(name = "ORDERDET_CHANGE", schema = "IAGENT")
@Entity
public class OrderdetChange implements java.io.Serializable{
    /**
     * 订单明细变更号
     */
    private Long orderdetChangeId;

    private String orderdetid;

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

    private Long id;

    private Integer productRefId;

    private Integer revision;

    private String procInstId;

    @Column(name = "ORDER_REF_ID")
    public Long getOrderRefId() {
        return orderRefId;
    }

    public void setOrderRefId(Long orderRefId) {
        this.orderRefId = orderRefId;
    }

    private Long orderRefId;

    private OrderChange orderChange;

    @Id
    @Column(name = "ORDERDET_CHANGE_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDERDET_CHANGE_SEQ")
    @SequenceGenerator(name = "ORDERDET_CHANGE_SEQ", sequenceName = "IAGENT.ORDERDET_CHANGE_SEQ")
    public Long getOrderdetChangeId() {
        return orderdetChangeId;
    }

    public void setOrderdetChangeId(Long orderdetChangeId) {
        this.orderdetChangeId = orderdetChangeId;
    }

    @Column(name = "ORDERDETID", length = 16)
    public String getOrderdetid() {
        return orderdetid;
    }

    public void setOrderdetid(String orderdetid) {
        this.orderdetid = orderdetid;
    }

    @Column(name = "ORDERID", length = 16)
    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    @Column(name = "PRODID", length = 16)
    public String getProdid() {
        return prodid;
    }

    public void setProdid(String prodid) {
        this.prodid = prodid;
    }

    @Column(name = "CONTACTID", length = 18)
    public String getContactid() {
        return contactid;
    }

    public void setContactid(String contactid) {
        this.contactid = contactid;
    }

    @Column(name = "PRODSCODE", length = 10)
    public String getProdscode() {
        return prodscode;
    }

    public void setProdscode(String prodscode) {
        this.prodscode = prodscode;
    }

    @Column(name = "PRODNAME", length = 100)
    public String getProdname() {
        return prodname;
    }

    public void setProdname(String prodname) {
        this.prodname = prodname;
    }

    @Column(name = "SOLDWITH", length = 10)
    public String getSoldwith() {
        return soldwith;
    }

    public void setSoldwith(String soldwith) {
        this.soldwith = soldwith;
    }

    @Column(name = "STATUS", length = 10)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "RECKONING", length = 10)
    public String getReckoning() {
        return reckoning;
    }

    public void setReckoning(String reckoning) {
        this.reckoning = reckoning;
    }

    @Column(name = "RECKONINGDT", length = 7)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getReckoningdt() {
        return reckoningdt;
    }

    public void setReckoningdt(Date reckoningdt) {
        this.reckoningdt = reckoningdt;
    }

    @Column(name = "FBDT", length = 7)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getFbdt() {
        return fbdt;
    }

    public void setFbdt(Date fbdt) {
        this.fbdt = fbdt;
    }

    @Column(name = "UPRICE", precision = 10, scale = 2)
    public BigDecimal getUprice() {
        return uprice;
    }

    public void setUprice(BigDecimal uprice) {
        this.uprice = uprice;
    }

    @Column(name = "UPNUM")
    public Integer getUpnum() {
        return upnum;
    }

    public void setUpnum(Integer upnum) {
        this.upnum = upnum;
    }

    @Column(name = "SPRICE", precision = 10, scale = 2)
    public BigDecimal getSprice() {
        return sprice;
    }

    public void setSprice(BigDecimal sprice) {
        this.sprice = sprice;
    }

    @Column(name = "SPNUM")
    public Integer getSpnum() {
        return spnum;
    }

    public void setSpnum(Integer spnum) {
        this.spnum = spnum;
    }

    @Column(name = "PAYMENT", precision = 10, scale = 2)
    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    @Column(name = "FREIGHT", precision = 10, scale = 2)
    public BigDecimal getFreight() {
        return freight;
    }

    public void setFreight(BigDecimal freight) {
        this.freight = freight;
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

    @Column(name = "ORDERDT", length = 7)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getOrderdt() {
        return orderdt;
    }

    public void setOrderdt(Date orderdt) {
        this.orderdt = orderdt;
    }

    @Column(name = "PROVINCEID", length = 10)
    public String getProvinceid() {
        return provinceid;
    }

    public void setProvinceid(String provinceid) {
        this.provinceid = provinceid;
    }

    @Column(name = "STATE", length = 10)
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Column(name = "CITY", length = 60)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "MDUSR", length = 10)
    public String getMdusr() {
        return mdusr;
    }

    public void setMdusr(String mdusr) {
        this.mdusr = mdusr;
    }

    @Column(name = "BREASON", length = 10)
    public String getBreason() {
        return breason;
    }

    public void setBreason(String breason) {
        this.breason = breason;
    }

    @Column(name = "FEEDBACK", length = 10)
    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    @Column(name = "GOODSBACK", length = 10)
    public String getGoodsback() {
        return goodsback;
    }

    public void setGoodsback(String goodsback) {
        this.goodsback = goodsback;
    }

    @Column(name = "PRODUCTTYPE", length = 16)
    public String getProducttype() {
        return producttype;
    }

    public void setProducttype(String producttype) {
        this.producttype = producttype;
    }

    @Column(name = "BACKDT", length = 7)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getBackdt() {
        return backdt;
    }

    public void setBackdt(Date backdt) {
        this.backdt = backdt;
    }

    @Column(name = "BACKMONEY")
    public Integer getBackmoney() {
        return backmoney;
    }

    public void setBackmoney(Integer backmoney) {
        this.backmoney = backmoney;
    }

    @Column(name = "OLDPROD", length = 30)
    public String getOldprod() {
        return oldprod;
    }

    public void setOldprod(String oldprod) {
        this.oldprod = oldprod;
    }

    @Column(name = "COMPENSATE", precision = 10, scale = 2)
    public BigDecimal getCompensate() {
        return compensate;
    }

    public void setCompensate(BigDecimal compensate) {
        this.compensate = compensate;
    }

    @Column(name = "PURPOSE", length = 20)
    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
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

    @Column(name = "NUM1", length = 16)
    public String getNum1() {
        return num1;
    }

    public void setNum1(String num1) {
        this.num1 = num1;
    }

    @Column(name = "NUM2", length = 30)
    public String getNum2() {
        return num2;
    }

    public void setNum2(String num2) {
        this.num2 = num2;
    }

    @Column(name = "BALEPRODID", length = 30)
    public String getBaleprodid() {
        return baleprodid;
    }

    public void setBaleprodid(String baleprodid) {
        this.baleprodid = baleprodid;
    }

    @Column(name = "CARDRIGHTNUM", length = 20)
    public String getCardrightnum() {
        return cardrightnum;
    }

    public void setCardrightnum(String cardrightnum) {
        this.cardrightnum = cardrightnum;
    }

    @Column(name = "ACCOUNTINGCOST", precision = 10, scale = 2)
    public BigDecimal getAccountingcost() {
        return accountingcost;
    }

    public void setAccountingcost(BigDecimal accountingcost) {
        this.accountingcost = accountingcost;
    }

    @Column(name = "SPID", length = 10)
    public String getSpid() {
        return spid;
    }

    public void setSpid(String spid) {
        this.spid = spid;
    }

    @Column(name = "PRODBANKID", length = 30)
    public String getProdbankid() {
        return prodbankid;
    }

    public void setProdbankid(String prodbankid) {
        this.prodbankid = prodbankid;
    }

    @Column(name = "SCRATCHCARD", length = 30)
    public String getScratchcard() {
        return scratchcard;
    }

    public void setScratchcard(String scratchcard) {
        this.scratchcard = scratchcard;
    }

    @Column(name = "SCCARDAMOUNT", precision = 10, scale = 2)
    public BigDecimal getSccardamount() {
        return sccardamount;
    }

    public void setSccardamount(BigDecimal sccardamount) {
        this.sccardamount = sccardamount;
    }

    @Column(name = "CATALOGNO", length = 20)
    public String getCatalogno() {
        return catalogno;
    }

    public void setCatalogno(String catalogno) {
        this.catalogno = catalogno;
    }

    @Column(name = "PROMOTIONSDOCNO", length = 50)
    public String getPromotionsdocno() {
        return promotionsdocno;
    }

    public void setPromotionsdocno(String promotionsdocno) {
        this.promotionsdocno = promotionsdocno;
    }

    @Column(name = "PROMOTIONSDETRUID")
    public Integer getPromotionsdetruid() {
        return promotionsdetruid;
    }

    public void setPromotionsdetruid(Integer promotionsdetruid) {
        this.promotionsdetruid = promotionsdetruid;
    }

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

    @Column(name = "LAST_UPDATE_TIME", length = 7)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "ORDER_CHANGE_ID")
    public OrderChange getOrderChange() {
        return orderChange;
    }

    public void setOrderChange(OrderChange orderChange) {
        this.orderChange = orderChange;
    }

    @Column(name = "PRODUCT_REF_ID")
    public Integer getProductRefId() {
        return productRefId;
    }

    public void setProductRefId(Integer productRefId) {
        this.productRefId = productRefId;
    }

    @Column(name = "REVISION")
    public Integer getRevision() {
        return revision;
    }

    public void setRevision(Integer revision) {
        this.revision = revision;
    }

    @Column(name = "PROC_INST_ID", length = 64)
    public String getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderdetChange that = (OrderdetChange) o;

        if (accountingcost != null ? !accountingcost.equals(that.accountingcost) : that.accountingcost != null)
            return false;
        if (backdt != null ? !backdt.equals(that.backdt) : that.backdt != null) return false;
        if (backmoney != null ? !backmoney.equals(that.backmoney) : that.backmoney != null) return false;
        if (baleprodid != null ? !baleprodid.equals(that.baleprodid) : that.baleprodid != null) return false;
        if (breason != null ? !breason.equals(that.breason) : that.breason != null) return false;
        if (cardrightnum != null ? !cardrightnum.equals(that.cardrightnum) : that.cardrightnum != null) return false;
        if (catalogno != null ? !catalogno.equals(that.catalogno) : that.catalogno != null) return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (clearfee != null ? !clearfee.equals(that.clearfee) : that.clearfee != null) return false;
        if (compensate != null ? !compensate.equals(that.compensate) : that.compensate != null) return false;
        if (contactid != null ? !contactid.equals(that.contactid) : that.contactid != null) return false;
        if (fbdt != null ? !fbdt.equals(that.fbdt) : that.fbdt != null) return false;
        if (feedback != null ? !feedback.equals(that.feedback) : that.feedback != null) return false;
        if (freight != null ? !freight.equals(that.freight) : that.freight != null) return false;
        if (goodsback != null ? !goodsback.equals(that.goodsback) : that.goodsback != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (jifen != null ? !jifen.equals(that.jifen) : that.jifen != null) return false;
        if (lastLockSeqid != null ? !lastLockSeqid.equals(that.lastLockSeqid) : that.lastLockSeqid != null)
            return false;
        if (lastUpdateSeqid != null ? !lastUpdateSeqid.equals(that.lastUpdateSeqid) : that.lastUpdateSeqid != null)
            return false;
        if (lastUpdateTime != null ? !lastUpdateTime.equals(that.lastUpdateTime) : that.lastUpdateTime != null)
            return false;
        if (mdusr != null ? !mdusr.equals(that.mdusr) : that.mdusr != null) return false;
        if (num1 != null ? !num1.equals(that.num1) : that.num1 != null) return false;
        if (num2 != null ? !num2.equals(that.num2) : that.num2 != null) return false;
        if (oldprod != null ? !oldprod.equals(that.oldprod) : that.oldprod != null) return false;
        if (orderdetChangeId != null ? !orderdetChangeId.equals(that.orderdetChangeId) : that.orderdetChangeId != null)
            return false;
        if (orderdetid != null ? !orderdetid.equals(that.orderdetid) : that.orderdetid != null) return false;
        if (orderdt != null ? !orderdt.equals(that.orderdt) : that.orderdt != null) return false;
        if (orderid != null ? !orderid.equals(that.orderid) : that.orderid != null) return false;
        if (payment != null ? !payment.equals(that.payment) : that.payment != null) return false;
        if (postfee != null ? !postfee.equals(that.postfee) : that.postfee != null) return false;
        if (procInstId != null ? !procInstId.equals(that.procInstId) : that.procInstId != null) return false;
        if (prodbankid != null ? !prodbankid.equals(that.prodbankid) : that.prodbankid != null) return false;
        if (prodid != null ? !prodid.equals(that.prodid) : that.prodid != null) return false;
        if (prodname != null ? !prodname.equals(that.prodname) : that.prodname != null) return false;
        if (prodscode != null ? !prodscode.equals(that.prodscode) : that.prodscode != null) return false;
        if (productRefId != null ? !productRefId.equals(that.productRefId) : that.productRefId != null) return false;
        if (producttype != null ? !producttype.equals(that.producttype) : that.producttype != null) return false;
        if (promotionsdetruid != null ? !promotionsdetruid.equals(that.promotionsdetruid) : that.promotionsdetruid != null)
            return false;
        if (promotionsdocno != null ? !promotionsdocno.equals(that.promotionsdocno) : that.promotionsdocno != null)
            return false;
        if (provinceid != null ? !provinceid.equals(that.provinceid) : that.provinceid != null) return false;
        if (purpose != null ? !purpose.equals(that.purpose) : that.purpose != null) return false;
        if (reckoning != null ? !reckoning.equals(that.reckoning) : that.reckoning != null) return false;
        if (reckoningdt != null ? !reckoningdt.equals(that.reckoningdt) : that.reckoningdt != null) return false;
        if (revision != null ? !revision.equals(that.revision) : that.revision != null) return false;
        if (sccardamount != null ? !sccardamount.equals(that.sccardamount) : that.sccardamount != null) return false;
        if (scratchcard != null ? !scratchcard.equals(that.scratchcard) : that.scratchcard != null) return false;
        if (soldwith != null ? !soldwith.equals(that.soldwith) : that.soldwith != null) return false;
        if (spid != null ? !spid.equals(that.spid) : that.spid != null) return false;
        if (spnum != null ? !spnum.equals(that.spnum) : that.spnum != null) return false;
        if (sprice != null ? !sprice.equals(that.sprice) : that.sprice != null) return false;
        if (state != null ? !state.equals(that.state) : that.state != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (ticket != null ? !ticket.equals(that.ticket) : that.ticket != null) return false;
        if (upnum != null ? !upnum.equals(that.upnum) : that.upnum != null) return false;
        if (uprice != null ? !uprice.equals(that.uprice) : that.uprice != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = orderdetid != null ? orderdetid.hashCode() : 0;
        result = 31 * result + (orderid != null ? orderid.hashCode() : 0);
        result = 31 * result + (prodid != null ? prodid.hashCode() : 0);
        result = 31 * result + (contactid != null ? contactid.hashCode() : 0);
        result = 31 * result + (prodscode != null ? prodscode.hashCode() : 0);
        result = 31 * result + (prodname != null ? prodname.hashCode() : 0);
        result = 31 * result + (soldwith != null ? soldwith.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (reckoning != null ? reckoning.hashCode() : 0);
        result = 31 * result + (reckoningdt != null ? reckoningdt.hashCode() : 0);
        result = 31 * result + (fbdt != null ? fbdt.hashCode() : 0);
        result = 31 * result + (uprice != null ? uprice.hashCode() : 0);
        result = 31 * result + (upnum != null ? upnum.hashCode() : 0);
        result = 31 * result + (sprice != null ? sprice.hashCode() : 0);
        result = 31 * result + (spnum != null ? spnum.hashCode() : 0);
        result = 31 * result + (payment != null ? payment.hashCode() : 0);
        result = 31 * result + (freight != null ? freight.hashCode() : 0);
        result = 31 * result + (postfee != null ? postfee.hashCode() : 0);
        result = 31 * result + (clearfee != null ? clearfee.hashCode() : 0);
        result = 31 * result + (orderdt != null ? orderdt.hashCode() : 0);
        result = 31 * result + (provinceid != null ? provinceid.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (mdusr != null ? mdusr.hashCode() : 0);
        result = 31 * result + (breason != null ? breason.hashCode() : 0);
        result = 31 * result + (feedback != null ? feedback.hashCode() : 0);
        result = 31 * result + (goodsback != null ? goodsback.hashCode() : 0);
        result = 31 * result + (producttype != null ? producttype.hashCode() : 0);
        result = 31 * result + (backdt != null ? backdt.hashCode() : 0);
        result = 31 * result + (backmoney != null ? backmoney.hashCode() : 0);
        result = 31 * result + (oldprod != null ? oldprod.hashCode() : 0);
        result = 31 * result + (compensate != null ? compensate.hashCode() : 0);
        result = 31 * result + (purpose != null ? purpose.hashCode() : 0);
        result = 31 * result + (jifen != null ? jifen.hashCode() : 0);
        result = 31 * result + (ticket != null ? ticket.hashCode() : 0);
        result = 31 * result + (num1 != null ? num1.hashCode() : 0);
        result = 31 * result + (num2 != null ? num2.hashCode() : 0);
        result = 31 * result + (baleprodid != null ? baleprodid.hashCode() : 0);
        result = 31 * result + (cardrightnum != null ? cardrightnum.hashCode() : 0);
        result = 31 * result + (accountingcost != null ? accountingcost.hashCode() : 0);
        result = 31 * result + (spid != null ? spid.hashCode() : 0);
        result = 31 * result + (prodbankid != null ? prodbankid.hashCode() : 0);
        result = 31 * result + (scratchcard != null ? scratchcard.hashCode() : 0);
        result = 31 * result + (sccardamount != null ? sccardamount.hashCode() : 0);
        result = 31 * result + (catalogno != null ? catalogno.hashCode() : 0);
        result = 31 * result + (promotionsdocno != null ? promotionsdocno.hashCode() : 0);
        result = 31 * result + (promotionsdetruid != null ? promotionsdetruid.hashCode() : 0);
        result = 31 * result + (lastLockSeqid != null ? lastLockSeqid.hashCode() : 0);
        result = 31 * result + (lastUpdateSeqid != null ? lastUpdateSeqid.hashCode() : 0);
        result = 31 * result + (lastUpdateTime != null ? lastUpdateTime.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (productRefId != null ? productRefId.hashCode() : 0);
        result = 31 * result + (revision != null ? revision.hashCode() : 0);
        result = 31 * result + (orderdetChangeId != null ? orderdetChangeId.hashCode() : 0);
        result = 31 * result + (procInstId != null ? procInstId.hashCode() : 0);
        return result;
    }
}
