package com.chinadrtv.erp.model.agent;

import com.fasterxml.jackson.annotation.JsonFormat;
//import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单商品信息(TC)
 * User: 徐志凯
 * Date: 13-1-23
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */

@Table(name = "ORDERDET", schema = "IAGENT")
@Entity
//@Audited
public class OrderDetail implements Serializable {

    private Long id;
    private String orderid;
    private String orderdetid;
    private String prodid;
    private String prodscode;
    private String contactid;
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
    private Product product;
    private Integer revision;
    private Order orderhist;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDERDET_SEQ")
    @SequenceGenerator(name = "ORDERDET_SEQ", sequenceName = "IAGENT.SEQORDERDET_ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull()
    @Column(name = "ORDERDETID", length = 16, updatable=false)
    public String getOrderdetid() {
        return orderdetid;
    }

    public void setOrderdetid(String orderdetid) {
        this.orderdetid = orderdetid;
    }

    //@JsonIgnore
    @ManyToOne
    @JoinColumn(name = "ORDER_REF_ID")
    public Order getOrderhist() {
        return orderhist;
    }

    public void setOrderhist(Order orderhist) {
        this.orderhist = orderhist;
    }

    @Column(name = "ORDERID", length = 16)
    @NotNull()
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

    @Column(name = "CONTACTID", length = 16)
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
    @NotNull()
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getReckoningdt() {
        return reckoningdt;
    }

    public void setReckoningdt(Date reckoningdt) {
        this.reckoningdt = reckoningdt;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
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

    @Column(name = "CITY", length = 22)
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

    @Column(name = "BREASON",  length = 10)
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
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

    @Column(name = "PROMOTIONSDOCNO", length = 20)
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_REF_ID")
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    /*private Integer productRefId;

    @Column(name = "PRODUCT_REF_ID", length = 19)
    @Basic
    public Integer getProductRefId() {
        return productRefId;
    }

    public void setProductRefId(Integer productRefId) {
        this.productRefId = productRefId;
    }*/

    @Column(name = "REVISION")
    @Version
    public Integer getRevision() {
        return revision;
    }

    public void setRevision(Integer revision) {
        this.revision = revision;
    }

    @Override
    public int hashCode() {
        Integer result = orderdetid != null ? orderdetid.hashCode() : 0;
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
        //result = 31 * result + (productRefId!=null?productRefId.hashCode():0);
        result = 31 * result + (revision != null ? revision.hashCode() : 0);
        return result;
    }
}
