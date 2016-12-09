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
@Table(name = "PHONE_CHANGE", schema = "IAGENT")
@Entity
public class PhoneChange implements java.io.Serializable{
    private Long phoneChangeId;

    private Long phoneid;

    private String contactid;

    private String phn1;

    private String phn2;

    private String phn3;

    private String entityid;

    private String phonetypid;

    private String prmphn;

    private String remark;

    private String contactRowid;

    private Integer lastLockSeqid;

    private Integer lastUpdateSeqid;

    private Date lastUpdateTime;

    private String phoneNum;

    private String procInstId;

    private Date createDate;

    private String createUser;

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

    private ContactChange contactChange;

    @Id
    @Column(name = "PHONE_CHANGE_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PHONE_CHANGE_SEQ")
    @SequenceGenerator(name = "PHONE_CHANGE_SEQ", sequenceName = "IAGENT.PHONE_CHANGE_SEQ")
    public Long getPhoneChangeId() {
        return phoneChangeId;
    }

    public void setPhoneChangeId(Long phoneChangeId) {
        this.phoneChangeId = phoneChangeId;
    }

    @Column(name = "PHONEID", length = 16)
    public Long getPhoneid() {
        return phoneid;
    }

    public void setPhoneid(Long phoneid) {
        this.phoneid = phoneid;
    }

    @Column(name = "CONTACTID", length = 16, updatable =false)
    public String getContactid() {
        return contactid;
    }

    public void setContactid(String contactid) {
        this.contactid = contactid;
    }

    @Column(name = "PHN1", length = 10)
    public String getPhn1() {
        return phn1;
    }

    public void setPhn1(String phn1) {
        this.phn1 = phn1;
    }

    @Column(name = "PHN2", length = 20)
    public String getPhn2() {
        return phn2;
    }

    public void setPhn2(String phn2) {
        this.phn2 = phn2;
    }

    @Column(name = "PHN3", length = 20)
    public String getPhn3() {
        return phn3;
    }

    public void setPhn3(String phn3) {
        this.phn3 = phn3;
    }

    @Column(name = "ENTITYID", length = 16)
    public String getEntityid() {
        return entityid;
    }

    public void setEntityid(String entityid) {
        this.entityid = entityid;
    }

    @Column(name = "PHONETYPID", length = 10)
    public String getPhonetypid() {
        return phonetypid;
    }

    public void setPhonetypid(String phonetypid) {
        this.phonetypid = phonetypid;
    }

    @Column(name = "PRMPHN", length = 1)
    public String getPrmphn() {
        return prmphn;
    }

    public void setPrmphn(String prmphn) {
        this.prmphn = prmphn;
    }

    @Column(name = "REMARK", length = 50)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "CONTACT_ROWID", length = 40)
    public String getContactRowid() {
        return contactRowid;
    }

    public void setContactRowid(String contactRowid) {
        this.contactRowid = contactRowid;
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

    @Column(name = "LAST_UPDATE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    @Column(name = "PHONE_NUM", length = 20)
    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    @Column(name = "PROC_INST_ID", length = 64)
    public String getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }

    @ManyToOne
    @JoinColumn(name = "CONTACT_CHANGE_ID")
    public ContactChange getContactChange() {
        return contactChange;
    }

    public void setContactChange(ContactChange contactChange) {
        this.contactChange = contactChange;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PhoneChange that = (PhoneChange) o;

        if (contactRowid != null ? !contactRowid.equals(that.contactRowid) : that.contactRowid != null) return false;
        if (contactid != null ? !contactid.equals(that.contactid) : that.contactid != null) return false;
        if (entityid != null ? !entityid.equals(that.entityid) : that.entityid != null) return false;
        if (lastLockSeqid != null ? !lastLockSeqid.equals(that.lastLockSeqid) : that.lastLockSeqid != null)
            return false;
        if (lastUpdateSeqid != null ? !lastUpdateSeqid.equals(that.lastUpdateSeqid) : that.lastUpdateSeqid != null)
            return false;
        if (lastUpdateTime != null ? !lastUpdateTime.equals(that.lastUpdateTime) : that.lastUpdateTime != null)
            return false;
        if (phn1 != null ? !phn1.equals(that.phn1) : that.phn1 != null) return false;
        if (phn2 != null ? !phn2.equals(that.phn2) : that.phn2 != null) return false;
        if (phn3 != null ? !phn3.equals(that.phn3) : that.phn3 != null) return false;
        if (phoneChangeId != null ? !phoneChangeId.equals(that.phoneChangeId) : that.phoneChangeId != null)
            return false;
        if (phoneNum != null ? !phoneNum.equals(that.phoneNum) : that.phoneNum != null) return false;
        if (phoneid != null ? !phoneid.equals(that.phoneid) : that.phoneid != null) return false;
        if (phonetypid != null ? !phonetypid.equals(that.phonetypid) : that.phonetypid != null) return false;
        if (prmphn != null ? !prmphn.equals(that.prmphn) : that.prmphn != null) return false;
        if (procInstId != null ? !procInstId.equals(that.procInstId) : that.procInstId != null) return false;
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = phoneid != null ? phoneid.hashCode() : 0;
        result = 31 * result + (contactid != null ? contactid.hashCode() : 0);
        result = 31 * result + (phn1 != null ? phn1.hashCode() : 0);
        result = 31 * result + (phn2 != null ? phn2.hashCode() : 0);
        result = 31 * result + (phn3 != null ? phn3.hashCode() : 0);
        result = 31 * result + (entityid != null ? entityid.hashCode() : 0);
        result = 31 * result + (phonetypid != null ? phonetypid.hashCode() : 0);
        result = 31 * result + (prmphn != null ? prmphn.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (contactRowid != null ? contactRowid.hashCode() : 0);
        result = 31 * result + (lastLockSeqid != null ? lastLockSeqid.hashCode() : 0);
        result = 31 * result + (lastUpdateSeqid != null ? lastUpdateSeqid.hashCode() : 0);
        result = 31 * result + (lastUpdateTime != null ? lastUpdateTime.hashCode() : 0);
        result = 31 * result + (phoneNum != null ? phoneNum.hashCode() : 0);
        result = 31 * result + (phoneChangeId != null ? phoneChangeId.hashCode() : 0);
        result = 31 * result + (procInstId != null ? procInstId.hashCode() : 0);
        return result;
    }
}
