package com.chinadrtv.erp.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-5-10
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Table(name = "CONTACT_CHANGE", schema = "IAGENT")
@Entity
public class ContactChange implements java.io.Serializable{
    private static final long serialVersionUID = -1913516578741003646L;
    /**
     * 联系人变更号
     */
    private Long contactChangeId;

    private String contactid;

    private String name;

    private String sex;

    private String title;

    private String dept;

    private String contacttype;

    private String email;

    private String webaddr;

    private Date crdt;

    private Date crtm;

    private String crusr;

    private Date mddt;

    private Date mdtm;

    private String mdusr;

    private String entityid;

    private Date birthday;

    private String ages;

    private String education;

    private String income;

    private String marriage;

    private String occupation;

    private String consumer;

    private String pin;

    private Long total;

    private String purpose;

    private String netcontactid;

    private String jifen;

    private String areacode;

    private Long ticketvalue;

    private String fancy;

    private String idcardFlag;

    private String attitude;

    private String membertype;

    private String memberlevel;

    private Date lastdate;

    private Integer totalfrequency;

    private Long totalmonetary;

    private String sundept;

    private Integer credit;

    private String customersource;

    private String datatype;

    private String car;

    private String creditcard;

    private String children;

    private Date childrenage;

    private Integer carmoney1;

    private Integer carmoney2;

    private String dnis;

    private String caseid;

    private Integer lastLockSeqid;

    private Integer lastUpdateSeqid;

    private Date lastUpdateTime;

    private String occupationStatus;

    private String isHasElder;

    private Integer pickUpAddress;

    /**
     * 流程号
     */
    private String procInstId;

    private OrderChange orderChange;

    private Set<PhoneChange> phoneChanges =new HashSet<PhoneChange>();

    private java.util.Date elderBirthday;

    private Date createDate;

    private String createUser;

    @Id
    @Column(name = "CONTACT_CHANGE_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONTACT_CHANGE_SEQ")
    @SequenceGenerator(name = "CONTACT_CHANGE_SEQ", sequenceName = "IAGENT.CONTACT_CHANGE_SEQ")
    public Long getContactChangeId() {
        return contactChangeId;
    }

    @Column(name = "CONTACTID", length = 16)
    public String getContactid() {
        return contactid;
    }

    public void setContactid(String contactid) {
        this.contactid = contactid;
    }

    @Column(name = "NAME", length = 30)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "SEX", length = 10)
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Column(name = "TITLE", length = 10)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "DEPT", length = 10)
    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    @Column(name = "CONTACTTYPE", length = 2)
    public String getContacttype() {
        return contacttype;
    }

    public void setContacttype(String contacttype) {
        this.contacttype = contacttype;
    }

    @Column(name = "EMAIL", length = 128)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "WEBADDR", length = 128)
    public String getWebaddr() {
        return webaddr;
    }

    public void setWebaddr(String webaddr) {
        this.webaddr = webaddr;
    }

    @Column(name = "CRDT", length = 7)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCrdt() {
        return crdt;
    }

    public void setCrdt(Date crdt) {
        this.crdt = crdt;
    }

    @Column(name = "CRTM", length = 7)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCrtm() {
        return crtm;
    }

    public void setCrtm(Date crtm) {
        this.crtm = crtm;
    }

    @Column(name = "CRUSR", length = 10)
    public String getCrusr() {
        return crusr;
    }

    public void setCrusr(String crusr) {
        this.crusr = crusr;
    }

    @Column(name = "MDDT", length = 7)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getMddt() {
        return mddt;
    }

    public void setMddt(Date mddt) {
        this.mddt = mddt;
    }

    @Column(name = "MDTM", length = 7)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getMdtm() {
        return mdtm;
    }

    public void setMdtm(Date mdtm) {
        this.mdtm = mdtm;
    }

    @Column(name = "MDUSR", length = 10)
    public String getMdusr() {
        return mdusr;
    }

    public void setMdusr(String mdusr) {
        this.mdusr = mdusr;
    }

    @Column(name = "ENTITYID", length = 128)
    public String getEntityid() {
        return entityid;
    }

    public void setEntityid(String entityid) {
        this.entityid = entityid;
    }

    @Column(name = "BIRTHDAY", length = 7)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Column(name = "AGES", length = 2)
    public String getAges() {
        return ages;
    }

    public void setAges(String ages) {
        this.ages = ages;
    }

    @Column(name = "EDUCATION", length = 10)
    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    @Column(name = "INCOME", length = 10)
    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    @Column(name = "MARRIAGE", length = 10)
    public String getMarriage() {
        return marriage;
    }

    public void setMarriage(String marriage) {
        this.marriage = marriage;
    }

    @Column(name = "OCCUPATION", length = 10)
    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    @Column(name = "CONSUMER", length = 2)
    public String getConsumer() {
        return consumer;
    }

    public void setConsumer(String consumer) {
        this.consumer = consumer;
    }

    @Column(name = "PIN", length = 20)
    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    @Column(name = "TOTAL", precision = 9, scale = 2)
    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    @Column(name = "PURPOSE", length = 10)
    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    @Column(name = "NETCONTACTID", length = 50)
    public String getNetcontactid() {
        return netcontactid;
    }

    public void setNetcontactid(String netcontactid) {
        this.netcontactid = netcontactid;
    }

    @Column(name = "JIFEN", length = 10)
    public String getJifen() {
        return jifen;
    }

    public void setJifen(String jifen) {
        this.jifen = jifen;
    }

    @Column(name = "AREACODE", length = 6)
    public String getAreacode() {
        return areacode;
    }

    public void setAreacode(String areacode) {
        this.areacode = areacode;
    }

    @Column(name = "TICKETVALUE", length = 22)
    public Long getTicketvalue() {
        return ticketvalue;
    }

    public void setTicketvalue(Long ticketvalue) {
        this.ticketvalue = ticketvalue;
    }

    @Column(name = "FANCY", length = 10)
    public String getFancy() {
        return fancy;
    }

    public void setFancy(String fancy) {
        this.fancy = fancy;
    }

    @Column(name = "IDCARD_FLAG", length = 3)
    public String getIdcardFlag() {
        return idcardFlag;
    }

    public void setIdcardFlag(String idcardFlag) {
        this.idcardFlag = idcardFlag;
    }

    @Column(name = "ATTITUDE", length = 10)
    public String getAttitude() {
        return attitude;
    }

    public void setAttitude(String attitude) {
        this.attitude = attitude;
    }

    @Column(name = "MEMBERTYPE", length = 20)
    public String getMembertype() {
        return membertype;
    }

    public void setMembertype(String membertype) {
        this.membertype = membertype;
    }

    @Column(name = "MEMBERLEVEL", length = 20)
    public String getMemberlevel() {
        return memberlevel;
    }

    public void setMemberlevel(String memberlevel) {
        this.memberlevel = memberlevel;
    }

    @Column(name = "LASTDATE", length = 7)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getLastdate() {
        return lastdate;
    }

    public void setLastdate(Date lastdate) {
        this.lastdate = lastdate;
    }

    @Column(name = "TOTALFREQUENCY")
    public Integer getTotalfrequency() {
        return totalfrequency;
    }

    public void setTotalfrequency(Integer totalfrequency) {
        this.totalfrequency = totalfrequency;
    }

    @Column(name = "TOTALMONETARY", precision = 10, scale = 2)
    public Long getTotalmonetary() {
        return totalmonetary;
    }

    public void setTotalmonetary(Long totalmonetary) {
        this.totalmonetary = totalmonetary;
    }

    @Column(name = "SUNDEPT", length = 10)
    public String getSundept() {
        return sundept;
    }

    public void setSundept(String sundept) {
        this.sundept = sundept;
    }

    @Column(name = "CREDIT")
    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    @Column(name = "CUSTOMERSOURCE", length = 8)
    public String getCustomersource() {
        return customersource;
    }

    public void setCustomersource(String customersource) {
        this.customersource = customersource;
    }

    @Column(name = "DATATYPE", length = 8)
    public String getDatatype() {
        return datatype;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }

    @Column(name = "CAR", length = 5)
    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    @Column(name = "CREDITCARD", length = 5)
    public String getCreditcard() {
        return creditcard;
    }

    public void setCreditcard(String creditcard) {
        this.creditcard = creditcard;
    }

    @Column(name = "CHILDREN", length = 5)
    public String getChildren() {
        return children;
    }

    public void setChildren(String children) {
        this.children = children;
    }

    @Column(name = "CHILDRENAGE", length = 7)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getChildrenage() {
        return childrenage;
    }

    public void setChildrenage(Date childrenage) {
        this.childrenage = childrenage;
    }

    @Column(name = "CARMONEY1")
    public Integer getCarmoney1() {
        return carmoney1;
    }

    public void setCarmoney1(Integer carmoney1) {
        this.carmoney1 = carmoney1;
    }

    @Column(name = "CARMONEY2")
    public Integer getCarmoney2() {
        return carmoney2;
    }

    public void setCarmoney2(Integer carmoney2) {
        this.carmoney2 = carmoney2;
    }

    @Column(name = "DNIS", length = 32)
    public String getDnis() {
        return dnis;
    }

    public void setDnis(String dnis) {
        this.dnis = dnis;
    }

    @Column(name = "CASEID", length = 64)
    public String getCaseid() {
        return caseid;
    }

    public void setCaseid(String caseid) {
        this.caseid = caseid;
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

    @Column(name = "OCCUPATION_STATUS")
    public String getOccupationStatus() {
        return occupationStatus;
    }

    public void setOccupationStatus(String occupationStatus) {
        this.occupationStatus = occupationStatus;
    }

    @Column(name = "IS_HAS_ELDER")
    public String getHasElder() {
        return isHasElder;
    }

    public void setHasElder(String hasElder) {
        isHasElder = hasElder;
    }

    @Column(name = "PICK_UP_ADDRESS")
    public Integer getPickUpAddress() {
        return pickUpAddress;
    }

    public void setPickUpAddress(Integer pickUpAddress) {
        this.pickUpAddress = pickUpAddress;
    }

    public void setContactChangeId(Long contactChangeId) {
        this.contactChangeId = contactChangeId;
    }

    @Column(name = "PROC_INST_ID", length = 64)
    public String getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval =true, fetch = FetchType.LAZY, mappedBy = "contactChange")
    public Set<PhoneChange> getPhoneChanges() {
        return phoneChanges;
    }

    public void setPhoneChanges(Set<PhoneChange> phoneChanges) {
        this.phoneChanges = phoneChanges;
    }

    @OneToOne(mappedBy="getContactChange")
    public OrderChange getOrderChange() {
        return orderChange;
    }

    public void setOrderChange(OrderChange orderChange) {
        this.orderChange = orderChange;
    }

    @Column(name = "ELDER_BIRTHDAY", length = 7)
    @Temporal(TemporalType.TIMESTAMP)
    public java.util.Date getElderBirthday() {
        return elderBirthday;
    }

    public void setElderBirthday(java.util.Date elderBirthday) {
        this.elderBirthday = elderBirthday;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactChange that = (ContactChange) o;

        if (ages != null ? !ages.equals(that.ages) : that.ages != null) return false;
        if (areacode != null ? !areacode.equals(that.areacode) : that.areacode != null) return false;
        if (attitude != null ? !attitude.equals(that.attitude) : that.attitude != null) return false;
        if (birthday != null ? !birthday.equals(that.birthday) : that.birthday != null) return false;
        if (car != null ? !car.equals(that.car) : that.car != null) return false;
        if (carmoney1 != null ? !carmoney1.equals(that.carmoney1) : that.carmoney1 != null) return false;
        if (carmoney2 != null ? !carmoney2.equals(that.carmoney2) : that.carmoney2 != null) return false;
        if (caseid != null ? !caseid.equals(that.caseid) : that.caseid != null) return false;
        if (children != null ? !children.equals(that.children) : that.children != null) return false;
        if (childrenage != null ? !childrenage.equals(that.childrenage) : that.childrenage != null) return false;
        if (consumer != null ? !consumer.equals(that.consumer) : that.consumer != null) return false;
        if (contactChangeId != null ? !contactChangeId.equals(that.contactChangeId) : that.contactChangeId != null)
            return false;
        if (contactid != null ? !contactid.equals(that.contactid) : that.contactid != null) return false;
        if (contacttype != null ? !contacttype.equals(that.contacttype) : that.contacttype != null) return false;
        if (crdt != null ? !crdt.equals(that.crdt) : that.crdt != null) return false;
        if (credit != null ? !credit.equals(that.credit) : that.credit != null) return false;
        if (creditcard != null ? !creditcard.equals(that.creditcard) : that.creditcard != null) return false;
        if (crtm != null ? !crtm.equals(that.crtm) : that.crtm != null) return false;
        if (crusr != null ? !crusr.equals(that.crusr) : that.crusr != null) return false;
        if (customersource != null ? !customersource.equals(that.customersource) : that.customersource != null)
            return false;
        if (datatype != null ? !datatype.equals(that.datatype) : that.datatype != null) return false;
        if (dept != null ? !dept.equals(that.dept) : that.dept != null) return false;
        if (dnis != null ? !dnis.equals(that.dnis) : that.dnis != null) return false;
        if (education != null ? !education.equals(that.education) : that.education != null) return false;
        if (elderBirthday != null ? !elderBirthday.equals(that.elderBirthday) : that.elderBirthday != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (entityid != null ? !entityid.equals(that.entityid) : that.entityid != null) return false;
        if (fancy != null ? !fancy.equals(that.fancy) : that.fancy != null) return false;
        if (idcardFlag != null ? !idcardFlag.equals(that.idcardFlag) : that.idcardFlag != null) return false;
        if (income != null ? !income.equals(that.income) : that.income != null) return false;
        if (isHasElder != null ? !isHasElder.equals(that.isHasElder) : that.isHasElder != null) return false;
        if (jifen != null ? !jifen.equals(that.jifen) : that.jifen != null) return false;
        if (lastLockSeqid != null ? !lastLockSeqid.equals(that.lastLockSeqid) : that.lastLockSeqid != null)
            return false;
        if (lastUpdateSeqid != null ? !lastUpdateSeqid.equals(that.lastUpdateSeqid) : that.lastUpdateSeqid != null)
            return false;
        if (lastUpdateTime != null ? !lastUpdateTime.equals(that.lastUpdateTime) : that.lastUpdateTime != null)
            return false;
        if (lastdate != null ? !lastdate.equals(that.lastdate) : that.lastdate != null) return false;
        if (marriage != null ? !marriage.equals(that.marriage) : that.marriage != null) return false;
        if (mddt != null ? !mddt.equals(that.mddt) : that.mddt != null) return false;
        if (mdtm != null ? !mdtm.equals(that.mdtm) : that.mdtm != null) return false;
        if (mdusr != null ? !mdusr.equals(that.mdusr) : that.mdusr != null) return false;
        if (memberlevel != null ? !memberlevel.equals(that.memberlevel) : that.memberlevel != null) return false;
        if (membertype != null ? !membertype.equals(that.membertype) : that.membertype != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (netcontactid != null ? !netcontactid.equals(that.netcontactid) : that.netcontactid != null) return false;
        if (occupation != null ? !occupation.equals(that.occupation) : that.occupation != null) return false;
        if (occupationStatus != null ? !occupationStatus.equals(that.occupationStatus) : that.occupationStatus != null)
            return false;
        if (pickUpAddress != null ? !pickUpAddress.equals(that.pickUpAddress) : that.pickUpAddress != null)
            return false;
        if (pin != null ? !pin.equals(that.pin) : that.pin != null) return false;
        if (procInstId != null ? !procInstId.equals(that.procInstId) : that.procInstId != null) return false;
        if (purpose != null ? !purpose.equals(that.purpose) : that.purpose != null) return false;
        if (sex != null ? !sex.equals(that.sex) : that.sex != null) return false;
        if (sundept != null ? !sundept.equals(that.sundept) : that.sundept != null) return false;
        if (ticketvalue != null ? !ticketvalue.equals(that.ticketvalue) : that.ticketvalue != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (total != null ? !total.equals(that.total) : that.total != null) return false;
        if (totalfrequency != null ? !totalfrequency.equals(that.totalfrequency) : that.totalfrequency != null)
            return false;
        if (totalmonetary != null ? !totalmonetary.equals(that.totalmonetary) : that.totalmonetary != null)
            return false;
        if (webaddr != null ? !webaddr.equals(that.webaddr) : that.webaddr != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = contactid != null ? contactid.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (dept != null ? dept.hashCode() : 0);
        result = 31 * result + (contacttype != null ? contacttype.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (webaddr != null ? webaddr.hashCode() : 0);
        result = 31 * result + (crdt != null ? crdt.hashCode() : 0);
        result = 31 * result + (crtm != null ? crtm.hashCode() : 0);
        result = 31 * result + (crusr != null ? crusr.hashCode() : 0);
        result = 31 * result + (mddt != null ? mddt.hashCode() : 0);
        result = 31 * result + (mdtm != null ? mdtm.hashCode() : 0);
        result = 31 * result + (mdusr != null ? mdusr.hashCode() : 0);
        result = 31 * result + (entityid != null ? entityid.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (ages != null ? ages.hashCode() : 0);
        result = 31 * result + (education != null ? education.hashCode() : 0);
        result = 31 * result + (income != null ? income.hashCode() : 0);
        result = 31 * result + (marriage != null ? marriage.hashCode() : 0);
        result = 31 * result + (occupation != null ? occupation.hashCode() : 0);
        result = 31 * result + (consumer != null ? consumer.hashCode() : 0);
        result = 31 * result + (pin != null ? pin.hashCode() : 0);
        result = 31 * result + (total != null ? total.hashCode() : 0);
        result = 31 * result + (purpose != null ? purpose.hashCode() : 0);
        result = 31 * result + (netcontactid != null ? netcontactid.hashCode() : 0);
        result = 31 * result + (jifen != null ? jifen.hashCode() : 0);
        result = 31 * result + (areacode != null ? areacode.hashCode() : 0);
        result = 31 * result + (ticketvalue != null ? ticketvalue.hashCode() : 0);
        result = 31 * result + (fancy != null ? fancy.hashCode() : 0);
        result = 31 * result + (idcardFlag != null ? idcardFlag.hashCode() : 0);
        result = 31 * result + (attitude != null ? attitude.hashCode() : 0);
        result = 31 * result + (membertype != null ? membertype.hashCode() : 0);
        result = 31 * result + (memberlevel != null ? memberlevel.hashCode() : 0);
        result = 31 * result + (lastdate != null ? lastdate.hashCode() : 0);
        result = 31 * result + (totalfrequency != null ? totalfrequency.hashCode() : 0);
        result = 31 * result + (totalmonetary != null ? totalmonetary.hashCode() : 0);
        result = 31 * result + (sundept != null ? sundept.hashCode() : 0);
        result = 31 * result + (credit != null ? credit.hashCode() : 0);
        result = 31 * result + (customersource != null ? customersource.hashCode() : 0);
        result = 31 * result + (datatype != null ? datatype.hashCode() : 0);
        result = 31 * result + (car != null ? car.hashCode() : 0);
        result = 31 * result + (creditcard != null ? creditcard.hashCode() : 0);
        result = 31 * result + (children != null ? children.hashCode() : 0);
        result = 31 * result + (childrenage != null ? childrenage.hashCode() : 0);
        result = 31 * result + (carmoney1 != null ? carmoney1.hashCode() : 0);
        result = 31 * result + (carmoney2 != null ? carmoney2.hashCode() : 0);
        result = 31 * result + (dnis != null ? dnis.hashCode() : 0);
        result = 31 * result + (caseid != null ? caseid.hashCode() : 0);
        result = 31 * result + (lastLockSeqid != null ? lastLockSeqid.hashCode() : 0);
        result = 31 * result + (lastUpdateSeqid != null ? lastUpdateSeqid.hashCode() : 0);
        result = 31 * result + (lastUpdateTime != null ? lastUpdateTime.hashCode() : 0);
        result = 31 * result + (occupationStatus != null ? occupationStatus.hashCode() : 0);
        result = 31 * result + (isHasElder != null ? isHasElder.hashCode() : 0);
        result = 31 * result + (elderBirthday != null ? elderBirthday.hashCode() : 0);
        result = 31 * result + (pickUpAddress != null ? pickUpAddress.hashCode() : 0);
        result = 31 * result + (contactChangeId != null ? contactChangeId.hashCode() : 0);
        result = 31 * result + (procInstId != null ? procInstId.hashCode() : 0);
        return result;
    }
}
