package com.chinadrtv.erp.model.trade;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.chinadrtv.erp.model.agent.Order;


/**
 * 订单收货信息(TC)
 * User: 徐志凯
 * Date: 13-1-23
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Table(name="ORDERADDRESS", schema = "ACOAPP_OMS")
@Entity
public class OrderAddress {
    private Long ruid;

    private Order orderhist;

    private String orderid;

    private String consignee;

    private String addresstype;

    private String province;

    private int cityid;

    private int countyid;

    private int areaid;

    private String address;

    private String fullAddress;

    private String mobile;

    private String phoneArea;

    private String phone;

    private String phoneExt;

    private String telExt;

    private String postalCode;

    private String email;

    private String createdBy;

    private Date dateAdded;

    private Date lastModified;

    private String updateBy;

    @Id
    @Column(name = "RUID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDERADDRESS_SEQ")
    @SequenceGenerator(name = "ORDERADDRESS_SEQ", sequenceName = "ACOAPP_OMS.ORDERADDRESS_SEQ")
    public Long getRuid() {
        return ruid;
    }

    @Column(name = "ADDRESS", length = 100)
    public String getAddress() {
        return address;
    }

    @Column(name = "ADDRESSTYPE", length = 255)
    public String getAddresstype() {
        return addresstype;
    }

    @Column(name = "AREAID", length = 22)
    public int getAreaid() {
        return areaid;
    }

    @Column(name = "CITYID", length = 22)
    public int getCityid() {
        return cityid;
    }

    @Column(name = "CONSIGNEE",length = 25 )
    public String getConsignee() {
        return consignee;
    }

    @Column(name = "COUNTYID",  length = 22 )
    public int getCountyid() {
        return countyid;
    }

    @Column(name = "CREATED_BY",  length = 100 )
    public String getCreatedBy() {
        return createdBy;
    }

    @Column(name = "DATE_ADDED")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getDateAdded() {
        return dateAdded;
    }

    @Column(name = "EMAIL",  length = 25 )
    public String getEmail() {
        return email;
    }

    @Column(name = "FULL_ADDRESS",  length = 255 )
    public String getFullAddress() {
        return fullAddress;
    }

    @Column(name = "LAST_MODIFIED" )
    @Temporal(TemporalType.TIMESTAMP)
    public Date getLastModified() {
        return lastModified;
    }

    @Column(name = "MOBILE",  length = 25 )
    public String getMobile() {
        return mobile;
    }

    @ManyToOne
    @JoinColumn(name = "ORDER_REF_ID")
    public Order getOrderhist() {
        return orderhist;
    }

    @Column(name = "ORDERID", length = 16 )
    public String getOrderid() {
        return orderid;
    }

    @Column(name = "PHONE", length = 25 )
    public String getPhone() {
        return phone;
    }

    @Column(name = "PHONE_AREA",  length = 25 )
    public String getPhoneArea() {
        return phoneArea;
    }

    @Column(name = "PHONE_EXT",  length = 25 )
    public String getPhoneExt() {
        return phoneExt;
    }

    @Column(name = "POSTAL_CODE",  length = 15 )
    public String getPostalCode() {
        return postalCode;
    }

    @Column(name = "PROVINCE",  length = 50 )
    public String getProvince() {
        return province;
    }

    @Column(name = "TEL_EXT",  length = 25 )
    public String getTelExt() {
        return telExt;
    }

    @Column(name = "UPDATE_BY",  length = 100 )
    public String getUpdateBy() {
        return updateBy;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAddresstype(String addresstype) {
        this.addresstype = addresstype;
    }

    public void setAreaid(int areaid) {
        this.areaid = areaid;
    }

    public void setCityid(int cityid) {
        this.cityid = cityid;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public void setCountyid(int countyid) {
        this.countyid = countyid;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setOrderhist(Order orderhist) {
        this.orderhist = orderhist;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPhoneArea(String phoneArea) {
        this.phoneArea = phoneArea;
    }

    public void setPhoneExt(String phoneExt) {
        this.phoneExt = phoneExt;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setRuid(Long ruid) {
        this.ruid = ruid;
    }

    public void setTelExt(String telExt) {
        this.telExt = telExt;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    @Override
    public int hashCode() {
        int result = (ruid!=null?ruid.hashCode():0);
        result = 31 * result + (orderhist!=null?orderhist.hashCode():0);
        result = 31 * result + (orderid != null ? orderid.hashCode() : 0);
        result = 31 * result + (consignee != null ? consignee.hashCode() : 0);
        result = 31 * result + (addresstype != null ? addresstype.hashCode() : 0);
        result = 31 * result + (province != null ? province.hashCode() : 0);
        result = 31 * result + cityid;
        result = 31 * result + countyid;
        result = 31 * result + areaid;
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (fullAddress != null ? fullAddress.hashCode() : 0);
        result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
        result = 31 * result + (phoneArea != null ? phoneArea.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (phoneExt != null ? phoneExt.hashCode() : 0);
        result = 31 * result + (telExt != null ? telExt.hashCode() : 0);
        result = 31 * result + (postalCode != null ? postalCode.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
        result = 31 * result + (dateAdded != null ? dateAdded.hashCode() : 0);
        result = 31 * result + (lastModified != null ? lastModified.hashCode() : 0);
        result = 31 * result + (updateBy != null ? updateBy.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderAddress that = (OrderAddress) o;

        if (areaid != that.areaid) return false;
        if (cityid != that.cityid) return false;
        if (countyid != that.countyid) return false;
        if (orderhist!=null&&!orderhist.equals(that.orderhist)) return false;
        if (ruid != that.ruid) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (addresstype != null ? !addresstype.equals(that.addresstype) : that.addresstype != null) return false;
        if (consignee != null ? !consignee.equals(that.consignee) : that.consignee != null) return false;
        if (createdBy != null ? !createdBy.equals(that.createdBy) : that.createdBy != null) return false;
        if (dateAdded != null ? !dateAdded.equals(that.dateAdded) : that.dateAdded != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (fullAddress != null ? !fullAddress.equals(that.fullAddress) : that.fullAddress != null) return false;
        if (lastModified != null ? !lastModified.equals(that.lastModified) : that.lastModified != null) return false;
        if (mobile != null ? !mobile.equals(that.mobile) : that.mobile != null) return false;
        if (orderid != null ? !orderid.equals(that.orderid) : that.orderid != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        if (phoneArea != null ? !phoneArea.equals(that.phoneArea) : that.phoneArea != null) return false;
        if (phoneExt != null ? !phoneExt.equals(that.phoneExt) : that.phoneExt != null) return false;
        if (postalCode != null ? !postalCode.equals(that.postalCode) : that.postalCode != null) return false;
        if (province != null ? !province.equals(that.province) : that.province != null) return false;
        if (telExt != null ? !telExt.equals(that.telExt) : that.telExt != null) return false;
        if (updateBy != null ? !updateBy.equals(that.updateBy) : that.updateBy != null) return false;

        return true;
    }
}
