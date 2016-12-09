package com.chinadrtv.erp.admin.model;


import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 * User: gaodejian
 * Date: 12-8-9
 * Time: 下午6:16
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "COMPANY", schema = "IAGENT")
public class Company implements java.io.Serializable {

    private String companyId;
    private String nccompanyId;
    private String companyName;
    private CompanyType companyType;
    private MailType mailType;
    private Province province;
    private String typeId;
    private String mailTypeId;
    private String address;
    private String zip;
    private String fax;
    private String phone;
    private String description;
    private String remark;
    private String areaCode;
    private String subPhone;
    private String spellId;
    private String shortCode;
    private String cityName;
    private String mailCode;
    private String email;
    private Double postFee = 0.0;
    private Double refPostFee = 0.0;
    private String bill;
    private String isReSeller;
    private String noUseBill;
    private String billPostFee;
    private String toWarehouse;

    @Id
    @GeneratedValue(generator="assigned")
    @GenericGenerator(name="assigned", strategy="assigned")
    @Column(name = "COMPANYID", length = 5)
    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String value) {
        this.companyId = value;
    }

    @Column(name = "NCCOMPANYID", length = 50)
    public String getNccompanyId() {
        return nccompanyId;
    }

    public void setNccompanyId(String value) {
        this.nccompanyId = value;
    }

    @Column(name = "NAME", length = 80)
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String value) {
        this.companyName = value;
    }

    @Column(name = "TYPEID", insertable = false, updatable = false)
    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String value) {
        this.typeId = value;
    }

    @Column(name = "MAILTYPE", insertable = false, updatable = false)
    public String getMailTypeId() {
        return mailTypeId;
    }

    public void setMailTypeId(String value) {
        this.mailTypeId = value;
    }


    @ManyToOne
    @JoinColumn(name = "TYPEID")
    @org.hibernate.annotations.Fetch(value = FetchMode.SELECT) //Join不加Where过滤,bug?
    @org.hibernate.annotations.NotFound(action = org.hibernate.annotations.NotFoundAction.IGNORE)
    @org.hibernate.annotations.Cascade(value = org.hibernate.annotations.CascadeType.REFRESH)
    public CompanyType getCompanyType() {
        return companyType;
    }

    public void setCompanyType(CompanyType value) {
        this.companyType = value;
    }

    @ManyToOne
    @JoinColumn(name = "MAILTYPE")
    @org.hibernate.annotations.Fetch(value = FetchMode.SELECT) //Join不加Where过滤,bug?
    @org.hibernate.annotations.NotFound(action = org.hibernate.annotations.NotFoundAction.IGNORE)
    @org.hibernate.annotations.Cascade(value = org.hibernate.annotations.CascadeType.REFRESH)
    public MailType getMailType() {
        return this.mailType;
    }

    public void setMailType(MailType value) {
        this.mailType = value;
    }

    @ManyToOne
    @JoinColumn(name = "PROVINCEID")
    @org.hibernate.annotations.NotFound(action = org.hibernate.annotations.NotFoundAction.IGNORE)
    @org.hibernate.annotations.Cascade(value = org.hibernate.annotations.CascadeType.REFRESH)
    public Province getProvince() {
        return this.province;
    }

    public void setProvince(Province value) {
        this.province = value;
    }

    @Column(name = "ADDRESS", length = 80)
    public String getAddress() {
        return address;
    }

    public void setAddress(String value) {
        this.address = value;
    }

    @Column(name = "PHONE", length = 80)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String value) {
        this.phone = value;
    }

    @Column(name = "SUBPHONE", length = 80)
    public String getSubPhone() {
        return subPhone;
    }

    public void setSubPhone(String value) {
        this.subPhone = value;
    }

    @Column(name = "ZIP", length = 80)
    public String getZip() {
        return zip;
    }

    public void setZip(String value) {
        this.zip = value;
    }

    @Column(name = "FAX", length = 80)
    public String getFax() {
        return fax;
    }

    public void setFax(String value) {
        this.fax = value;
    }

    @Column(name = "DESCRIPTION", length = 80)
    public String getDescription() {
        return description;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    @Column(name = "REMARK", length = 80)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String value) {
        this.remark = value;
    }

    @Column(name = "AREA_CODE", length = 80)
    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String value) {
        this.areaCode = value;
    }

    @Column(name = "SHORT_CODE", length = 80)
    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String value) {
        this.shortCode = value;
    }

    @Column(name = "SPELLID", length = 80)
    public String getSpellId() {
        return spellId;
    }

    public void setSpellId(String value) {
        this.spellId = value;
    }

    @Column(name = "CITY_NAME", length = 80)
    public String getCityName() {
        return cityName;
    }

    public void setCityName(String value) {
        this.cityName = value;
    }

    @Column(name = "MAIL_CODE", length = 80)
    public String getMailCode() {
        return mailCode;
    }

    public void setMailCode(String value) {
        this.mailCode = value;
    }

    @Column(name = "EMAIL", length = 80)
    public String getEmail() {
        return email;
    }

    public void setEmail(String value) {
        this.email = value;
    }

    @Column(name = "POSTFEE", nullable=true)
    public Double getPostFee()
    {
        return postFee;
    }

    public void setPostFee(Double value) {
        this.postFee = value;
    }

    @Column(name = "ISRESELLER", length = 80)
    public String getIsReSeller() {
        return isReSeller;
    }

    public void setIsReSeller(String value) {
        this.isReSeller = value;
    }

    @Column(name = "BILL", length = 80)
    public String getBill() {
        return bill;
    }

    public void setBill(String value) {
        this.bill = value;
    }

    @Column(name = "NOUSEBILL", length = 4)
    public String getNoUseBill() {
        return noUseBill;
    }

    public void setNoUseBill(String value) {
        this.noUseBill = value;
    }

    @Column(name = "BILLPOSTFEE", length = 4)
    public String getBillPostFee() {
        return billPostFee;
    }

    public void setBillPostFee(String value) {
        this.billPostFee = value;
    }

    @Column(name = "REFPOSTFEE", nullable=true)
    public Double getRefPostFee()
    {
        return refPostFee;
    }

    public void setRefPostFee(Double value) {
        this.refPostFee = value;
    }

    @Column(name = "TO_WAREHOUSE", length = 50)
    public String getToWarehouse() {
        return toWarehouse;
    }

    public void setToWarehouse(String value) {
        this.toWarehouse = value;
    }
}
