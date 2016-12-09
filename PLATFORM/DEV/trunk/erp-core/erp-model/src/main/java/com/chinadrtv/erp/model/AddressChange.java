package com.chinadrtv.erp.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-5-10
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Table(name = "ADDRESS_CHANGE", schema = "IAGENT")
@Entity
public class AddressChange implements java.io.Serializable{
    private static final long serialVersionUID = -1913516578741003646L;

    private String addressid;

    private String address;

    private String state;

    private String city;

    private String area;

    private String zip;

    private String contactid;

    private String addrtypid;

    private String additionalinfo;

    private String addconfirmation;

    private String addrconfirm;

    private String flag;

    private Integer areaid;

    private String isdefault;

    private Integer lastLockSeqid;

    private Integer lastUpdateSeqid;

    private Date lastUpdateTime;

    private Integer addressChangeId;

    /**
     * 流程编号
     */
    private String procInstId;

    private Date createDate;

    private String createUser;

    @OneToOne()
    @JoinColumn(name="CONTACT_CHANGE_ID")
    public ContactChange getContactChange() {
        return contactChange;
    }

    public void setContactChange(ContactChange contactChange) {
        this.contactChange = contactChange;
    }

    private ContactChange contactChange;

    private AddressExtChange addressExtChange;

    @Id
    @Column(name = "ADDRESS_CHANGE_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ADDRESS_CHANGE_SEQ")
    @SequenceGenerator(name = "ADDRESS_CHANGE_SEQ", sequenceName = "IAGENT.ADDRESS_CHANGE_SEQ")
    public Integer getAddressChangeId() {
        return addressChangeId;
    }

    public void setAddressChangeId(Integer addressChangeId) {
        this.addressChangeId = addressChangeId;
    }

    @Column(name = "ADDRESSID", length = 16)
    public String getAddressid() {
        return addressid;
    }

    public void setAddressid(String addressid) {
        this.addressid = addressid;
    }

    @Column(name = "ADDRESS", length = 128)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "STATE", length = 10)
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Column(name = "CITY", length = 30)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "AREA", length = 10)
    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @Column(name = "ZIP", length = 10)
    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    @Column(name = "CONTACTID", length = 16)
    public String getContactid() {
        return contactid;
    }

    public void setContactid(String contactid) {
        this.contactid = contactid;
    }

    @Column(name = "ADDRTYPID", length = 16)
    public String getAddrtypid() {
        return addrtypid;
    }

    public void setAddrtypid(String addrtypid) {
        this.addrtypid = addrtypid;
    }

    @Column(name = "ADDITIONALINFO", length = 10)
    public String getAdditionalinfo() {
        return additionalinfo;
    }

    public void setAdditionalinfo(String additionalinfo) {
        this.additionalinfo = additionalinfo;
    }

    @Column(name = "ADDCONFIRMATION", length = 2)
    public String getAddconfirmation() {
        return addconfirmation;
    }

    public void setAddconfirmation(String addconfirmation) {
        this.addconfirmation = addconfirmation;
    }

    @Column(name = "ADDRCONFIRM", length = 10)
    public String getAddrconfirm() {
        return addrconfirm;
    }

    public void setAddrconfirm(String addrconfirm) {
        this.addrconfirm = addrconfirm;
    }

    @Column(name = "FLAG", length = 8)
    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Column(name = "AREAID")
    public Integer getAreaid() {
        return areaid;
    }

    public void setAreaid(Integer areaid) {
        this.areaid = areaid;
    }

    @Column(name = "ISDEFAULT", length = 2)
    public String getIsdefault() {
        return isdefault;
    }

    public void setIsdefault(String isdefault) {
        this.isdefault = isdefault;
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

    @Column(name = "PROC_INST_ID", length = 64)
    public String getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }

    @Column(name = "CREATE_DATE", length = 7)
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

    @OneToOne(mappedBy = "addressChange")
    public AddressExtChange getAddressExtChange() {
        return addressExtChange;
    }

    public void setAddressExtChange(AddressExtChange addressExtChange) {
        this.addressExtChange = addressExtChange;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AddressChange that = (AddressChange) o;

        if (addconfirmation != null ? !addconfirmation.equals(that.addconfirmation) : that.addconfirmation != null)
            return false;
        if (additionalinfo != null ? !additionalinfo.equals(that.additionalinfo) : that.additionalinfo != null)
            return false;
        if (addrconfirm != null ? !addrconfirm.equals(that.addrconfirm) : that.addrconfirm != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (addressChangeId != null ? !addressChangeId.equals(that.addressChangeId) : that.addressChangeId != null)
            return false;
        if (addressid != null ? !addressid.equals(that.addressid) : that.addressid != null) return false;
        if (addrtypid != null ? !addrtypid.equals(that.addrtypid) : that.addrtypid != null) return false;
        if (area != null ? !area.equals(that.area) : that.area != null) return false;
        if (areaid != null ? !areaid.equals(that.areaid) : that.areaid != null) return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (contactid != null ? !contactid.equals(that.contactid) : that.contactid != null) return false;
        if (flag != null ? !flag.equals(that.flag) : that.flag != null) return false;
        if (isdefault != null ? !isdefault.equals(that.isdefault) : that.isdefault != null) return false;
        if (lastLockSeqid != null ? !lastLockSeqid.equals(that.lastLockSeqid) : that.lastLockSeqid != null)
            return false;
        if (lastUpdateSeqid != null ? !lastUpdateSeqid.equals(that.lastUpdateSeqid) : that.lastUpdateSeqid != null)
            return false;
        if (lastUpdateTime != null ? !lastUpdateTime.equals(that.lastUpdateTime) : that.lastUpdateTime != null)
            return false;
        if (procInstId != null ? !procInstId.equals(that.procInstId) : that.procInstId != null) return false;
        if (state != null ? !state.equals(that.state) : that.state != null) return false;
        if (zip != null ? !zip.equals(that.zip) : that.zip != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = addressid != null ? addressid.hashCode() : 0;
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (area != null ? area.hashCode() : 0);
        result = 31 * result + (zip != null ? zip.hashCode() : 0);
        result = 31 * result + (contactid != null ? contactid.hashCode() : 0);
        result = 31 * result + (addrtypid != null ? addrtypid.hashCode() : 0);
        result = 31 * result + (additionalinfo != null ? additionalinfo.hashCode() : 0);
        result = 31 * result + (addconfirmation != null ? addconfirmation.hashCode() : 0);
        result = 31 * result + (addrconfirm != null ? addrconfirm.hashCode() : 0);
        result = 31 * result + (flag != null ? flag.hashCode() : 0);
        result = 31 * result + (areaid != null ? areaid.hashCode() : 0);
        result = 31 * result + (isdefault != null ? isdefault.hashCode() : 0);
        result = 31 * result + (lastLockSeqid != null ? lastLockSeqid.hashCode() : 0);
        result = 31 * result + (lastUpdateSeqid != null ? lastUpdateSeqid.hashCode() : 0);
        result = 31 * result + (lastUpdateTime != null ? lastUpdateTime.hashCode() : 0);
        result = 31 * result + (addressChangeId != null ? addressChangeId.hashCode() : 0);
        result = 31 * result + (procInstId != null ? procInstId.hashCode() : 0);
        return result;
    }
}
