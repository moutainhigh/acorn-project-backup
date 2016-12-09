package com.chinadrtv.erp.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-5-10
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Table(name = "ORDER_CHANGE", schema = "IAGENT")
@Entity
public class OrderChange implements java.io.Serializable {
    private static final long serialVersionUID = -1913516578741003646L;

    /**
     * 订单变更号
     */
    private Long orderChangeId;

    private String orderid;

    private String ordertype;

    private String entityid;

    private Integer spellid;

    private String contactid;

    private String paycontactid;

    private String getcontactid;

    private String mdusr;

    private Date mddt;

    private String mailtype;

    private String paytype;

    private String urgent;

    private BigDecimal totalprice;

    private BigDecimal mailprice;

    private BigDecimal prodprice;

    private BigDecimal clearfee;

    private String bill;

    private String note;

    private String cardtype;

    private String cardnumber;

    private Date starttm;

    private Date endtm;

    private String jifen;

    private AddressExtChange address;

    private String spid;

    private String invoicetitle;

    private Long id;

    private ContactChange contactChange;
    //private Integer contactidRefId;

    private ContactChange payContactChange;
    //private Integer paycontactidRefId;

    private ContactChange getContactChange;
    //private Integer getcontactidRefId;

    private String isreqems;

    private String isrequsr;

    /**
     * 流程编号
     */
    private String procInstId;

    private BigDecimal postFee;

    private Date createDate;

    private String createUser;

    private Set<OrderdetChange> orderdetChanges = new HashSet<OrderdetChange>();

    @Column(name = "LASTSTATUS", length = 10)
    public String getLaststatus() {
        return laststatus;
    }

    public void setLaststatus(String laststatus) {
        this.laststatus = laststatus;
    }

    private String laststatus;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval =true, fetch = FetchType.LAZY, mappedBy = "orderChange")
    public Set<CardChange> getCardChanges() {
        return cardChanges;
    }

    public void setCardChanges(Set<CardChange> cardChanges) {
        this.cardChanges = cardChanges;
    }

    private Set<CardChange> cardChanges=new HashSet<CardChange>();

    @Id
    @Column(name = "ORDER_CHANGE_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDER_CHANGE_SEQ")
    @SequenceGenerator(name = "ORDER_CHANGE_SEQ", sequenceName = "IAGENT.ORDER_CHANGE_SEQ")
    public Long getOrderChangeId() {
        return orderChangeId;
    }

    public void setOrderChangeId(Long orderChangeId) {
        this.orderChangeId = orderChangeId;
    }

    @Column(name = "ORDERID", length = 16)
    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    @Column(name = "ORDERTYPE", length = 10)
    public String getOrdertype() {
        return ordertype;
    }

    public void setOrdertype(String ordertype) {
        this.ordertype = ordertype;
    }

    @Column(name = "ENTITYID", length = 16)
    public String getEntityid() {
        return entityid;
    }

    public void setEntityid(String entityid) {
        this.entityid = entityid;
    }

    @Column(name = "SPELLID", length = 5)
    public Integer getSpellid() {
        return spellid;
    }

    public void setSpellid(Integer spellid) {
        this.spellid = spellid;
    }

    @Column(name = "CONTACTID", length = 18)
    public String getContactid() {
        return contactid;
    }

    public void setContactid(String contactid) {
        this.contactid = contactid;
    }

    @Column(name = "PAYCONTACTID", length = 18)
    public String getPaycontactid() {
        return paycontactid;
    }

    public void setPaycontactid(String paycontactid) {
        this.paycontactid = paycontactid;
    }

    @Column(name = "GETCONTACTID", length = 18, precision = 0)
    public String getGetcontactid() {
        return getcontactid;
    }

    public void setGetcontactid(String getcontactid) {
        this.getcontactid = getcontactid;
    }

    @Column(name = "MDUSR", length = 10)
    public String getMdusr() {
        return mdusr;
    }

    public void setMdusr(String mdusr) {
        this.mdusr = mdusr;
    }

    @Column(name = "MDDT", length = 7)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getMddt() {
        return mddt;
    }

    public void setMddt(Date mddt) {
        this.mddt = mddt;
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

    @Column(name = "TOTALPRICE", precision = 10, scale = 2)
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

    @Column(name = "CLEARFEE", precision = 10, scale = 2)
    public BigDecimal getClearfee() {
        return clearfee;
    }

    public void setClearfee(BigDecimal clearfee) {
        this.clearfee = clearfee;
    }

    @Column(name = "BILL", precision = 10, scale = 2)
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

    @Column(name = "STARTTM", length = 7)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getStarttm() {
        return starttm;
    }

    public void setStarttm(Date starttm) {
        this.starttm = starttm;
    }

    @Column(name = "ENDTM", length = 7)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getEndtm() {
        return endtm;
    }

    public void setEndtm(Date endtm) {
        this.endtm = endtm;
    }

    @Column(name = "JIFEN", length = 10)
    public String getJifen() {
        return jifen;
    }

    public void setJifen(String jifen) {
        this.jifen = jifen;
    }

    @OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "addressid")
    public AddressExtChange getAddress() {
        return address;
    }

    public void setAddress(AddressExtChange address) {
        this.address = address;
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

    @Column(name = "ID", length = 20)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /*@Column(name = "CONTACTID_REF_ID", length = 20)
    public Integer getContactidRefId() {
        return contactidRefId;
    }

    public void setContactidRefId(Integer contactidRefId) {
        this.contactidRefId = contactidRefId;
    }

    @Column(name = "PAYCONTACTID_REF_ID", length = 20)
    public Integer getPaycontactidRefId() {
        return paycontactidRefId;
    }

    public void setPaycontactidRefId(Integer paycontactidRefId) {
        this.paycontactidRefId = paycontactidRefId;
    }

    @Column(name = "GETCONTACTID_REF_ID", length = 20)
    public Integer getGetcontactidRefId() {
        return getcontactidRefId;
    }

    public void setGetcontactidRefId(Integer getcontactidRefId) {
        this.getcontactidRefId = getcontactidRefId;
    }*/

    @OneToOne(cascade=CascadeType.ALL )
    @JoinColumn(name = "CONTACTID_REF_ID")
    public ContactChange getContactChange() {
        return contactChange;
    }

    public void setContactChange(ContactChange contactChange) {
        this.contactChange = contactChange;
    }

    @OneToOne
    @JoinColumn(name = "PAYCONTACTID_REF_ID")
    public ContactChange getPayContactChange() {
        return payContactChange;
    }

    public void setPayContactChange(ContactChange payContactChange) {
        this.payContactChange = payContactChange;
    }

    @OneToOne(cascade=CascadeType.ALL )
    @JoinColumn(name = "GETCONTACTID_REF_ID")
    public ContactChange getGetContactChange() {
        return getContactChange;
    }

    public void setGetContactChange(ContactChange getContactChange) {
        this.getContactChange = getContactChange;
    }

    @Column(name = "ISREQEMS", length = 10)
    public String getIsreqems() {
        return isreqems;
    }

    public void setIsreqems(String isreqems) {
        this.isreqems = isreqems;
    }

    @Column(name = "ISREQUSR", length = 10)
    public String getIsrequsr() {
        return isrequsr;
    }

    public void setIsrequsr(String isrequsr) {
        this.isrequsr = isrequsr;
    }

    @Column(name = "PROC_INST_ID", length = 64)
    public String getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }

    @Column(name = "POST_FEE", precision = 10, scale = 2)
    public BigDecimal getPostFee() {
        return postFee;
    }

    public void setPostFee(BigDecimal postFee) {
        this.postFee = postFee;
    }

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval =true, fetch = FetchType.LAZY, mappedBy = "orderChange")
    public Set<OrderdetChange> getOrderdetChanges() {
        return orderdetChanges;
    }

    public void setOrderdetChanges(Set<OrderdetChange> orderdetChanges) {
        this.orderdetChanges = orderdetChanges;
    }

    @Column(name = "CREATE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Column(name = "CREATE_USER", length = 20)
    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderChange that = (OrderChange) o;

        if (bill != null ? !bill.equals(that.bill) : that.bill != null) return false;
        if (cardnumber != null ? !cardnumber.equals(that.cardnumber) : that.cardnumber != null) return false;
        if (cardtype != null ? !cardtype.equals(that.cardtype) : that.cardtype != null) return false;
        if (clearfee != null ? !clearfee.equals(that.clearfee) : that.clearfee != null) return false;
        if (endtm != null ? !endtm.equals(that.endtm) : that.endtm != null) return false;
        if (entityid != null ? !entityid.equals(that.entityid) : that.entityid != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (invoicetitle != null ? !invoicetitle.equals(that.invoicetitle) : that.invoicetitle != null) return false;
        if (isreqems != null ? !isreqems.equals(that.isreqems) : that.isreqems != null) return false;
        if (isrequsr != null ? !isrequsr.equals(that.isrequsr) : that.isrequsr != null) return false;
        if (jifen != null ? !jifen.equals(that.jifen) : that.jifen != null) return false;
        if (mailprice != null ? !mailprice.equals(that.mailprice) : that.mailprice != null) return false;
        if (mailtype != null ? !mailtype.equals(that.mailtype) : that.mailtype != null) return false;
        if (mddt != null ? !mddt.equals(that.mddt) : that.mddt != null) return false;
        if (mdusr != null ? !mdusr.equals(that.mdusr) : that.mdusr != null) return false;
        if (note != null ? !note.equals(that.note) : that.note != null) return false;
        if (orderChangeId != null ? !orderChangeId.equals(that.orderChangeId) : that.orderChangeId != null)
            return false;
        if (orderid != null ? !orderid.equals(that.orderid) : that.orderid != null) return false;
        if (paytype != null ? !paytype.equals(that.paytype) : that.paytype != null) return false;
        if (postFee != null ? !postFee.equals(that.postFee) : that.postFee != null) return false;
        if (procInstId != null ? !procInstId.equals(that.procInstId) : that.procInstId != null) return false;
        if (prodprice != null ? !prodprice.equals(that.prodprice) : that.prodprice != null) return false;
        if (spellid != null ? !spellid.equals(that.spellid) : that.spellid != null) return false;
        if (spid != null ? !spid.equals(that.spid) : that.spid != null) return false;
        if (starttm != null ? !starttm.equals(that.starttm) : that.starttm != null) return false;
        if (totalprice != null ? !totalprice.equals(that.totalprice) : that.totalprice != null) return false;
        if (urgent != null ? !urgent.equals(that.urgent) : that.urgent != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = orderid != null ? orderid.hashCode() : 0;
        result = 31 * result + (entityid != null ? entityid.hashCode() : 0);
        result = 31 * result + (spellid != null ? spellid.hashCode() : 0);
        result = 31 * result + (mdusr != null ? mdusr.hashCode() : 0);
        result = 31 * result + (mddt != null ? mddt.hashCode() : 0);
        result = 31 * result + (mailtype != null ? mailtype.hashCode() : 0);
        result = 31 * result + (paytype != null ? paytype.hashCode() : 0);
        result = 31 * result + (urgent != null ? urgent.hashCode() : 0);
        result = 31 * result + (totalprice != null ? totalprice.hashCode() : 0);
        result = 31 * result + (mailprice != null ? mailprice.hashCode() : 0);
        result = 31 * result + (prodprice != null ? prodprice.hashCode() : 0);
        result = 31 * result + (clearfee != null ? clearfee.hashCode() : 0);
        result = 31 * result + (bill != null ? bill.hashCode() : 0);
        result = 31 * result + (note != null ? note.hashCode() : 0);
        result = 31 * result + (cardtype != null ? cardtype.hashCode() : 0);
        result = 31 * result + (cardnumber != null ? cardnumber.hashCode() : 0);
        result = 31 * result + (starttm != null ? starttm.hashCode() : 0);
        result = 31 * result + (endtm != null ? endtm.hashCode() : 0);
        result = 31 * result + (jifen != null ? jifen.hashCode() : 0);
        result = 31 * result + (spid != null ? spid.hashCode() : 0);
        result = 31 * result + (invoicetitle != null ? invoicetitle.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (isreqems != null ? isreqems.hashCode() : 0);
        result = 31 * result + (isrequsr != null ? isrequsr.hashCode() : 0);
        result = 31 * result + (orderChangeId != null ? orderChangeId.hashCode() : 0);
        result = 31 * result + (procInstId != null ? procInstId.hashCode() : 0);
        result = 31 * result + (postFee != null ? postFee.hashCode() : 0);
        return result;
    }
}
