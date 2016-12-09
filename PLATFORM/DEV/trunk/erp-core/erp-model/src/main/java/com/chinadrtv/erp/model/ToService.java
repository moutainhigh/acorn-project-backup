package com.chinadrtv.erp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "TOSERVICE", schema = "IAGENT")
@IdClass(ToserviceId.class)
public class ToService implements Serializable {
    private String contactId;
    private Date crdt;
    private Date crtm;
    private String crusr;
    private String orderId;
    private String prod;
    private String content;
    private String status;
    private String contactName;
    private String priovity;
    private String phone;
    private String caseId;
    private String opusr;

    @Id
    @Column(name = "CONTACTID", length = 16)
    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    @Column(name = "CRDT", unique = false, nullable = true, insertable = true, updatable = true, length = 7)
    public Date getCrdt() {
        return crdt;
    }

    public void setCrdt(Date crdt) {
        this.crdt = crdt;
    }

    @Id
    @Column(name = "CRTM", unique = false, nullable = true, insertable = true, updatable = true, length = 7)
    public Date getCrtm() {
        return crtm;
    }

    public void setCrtm(Date crtm) {
        this.crtm = crtm;
    }

    @Column(name = "CRUSR", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
    public String getCrusr() {
        return crusr;
    }

    public void setCrusr(String crusr) {
        this.crusr = crusr;
    }

    @Column(name = "ORDERID", length = 20)
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Column(name = "PROD", length = 200)
    public String getProd() {
        return prod;
    }

    public void setProd(String prod) {
        this.prod = prod;
    }

    @Column(name = "CONTENT", length = 3000)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(name = "STATUS", length = 2)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "CONTACTNAME", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    @Column(name = "PRIOVITY", length = 2)
    public String getPriovity() {
        return priovity;
    }

    public void setPriovity(String priovity) {
        this.priovity = priovity;
    }

    @Column(name = "PHONE", length = 100)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "CASEID", length = 16)
    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    @Column(name = "OPUSR", length = 10)
    public String getOpusr() {
        return opusr;
    }

    public void setOpusr(String opusr) {
        this.opusr = opusr;
    }
}