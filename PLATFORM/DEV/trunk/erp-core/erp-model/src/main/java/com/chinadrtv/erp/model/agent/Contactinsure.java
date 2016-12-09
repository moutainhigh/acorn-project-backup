package com.chinadrtv.erp.model.agent;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-3-12
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
import javax.persistence.*;
import java.util.Date;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-3-12
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Table(name = "CONTACTINSURE", schema = "IAGENT")
@Entity
public class Contactinsure {
    private String contactid;
    private String isok;
    private String dsc;
    private Date crdt;
    private String crusr;
    private Date mddt;
    private String mdusr;
    private String status;
    private String insurecase;
    private String insurenote;
    private Date refusedate;
    private String refuse; //是否拒绝赠险:1拒绝:0:未拒绝
    private Date refuseDateTime;//拒绝时间
    private Date insureSeccDate;
    private Integer hasinsure; //是否有保险
    private String callId;


    @Column(name = "CONTACTID", length = 16)
    @Id
    public String getContactid() {
        return contactid;
    }

    public void setContactid(String contactid) {
        this.contactid = contactid;
    }

    @Column(name = "ISOK", length = 2)
    public String getIsok() {
        return isok;
    }

    public void setIsok(String isok) {
        this.isok = isok;
    }

    @Column(name = "DSC", length = 1000)
    public String getDsc() {
        return dsc;
    }

    public void setDsc(String dsc) {
        this.dsc = dsc;
    }

    @Column(name = "CRDT", length = 7)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCrdt() {
        return crdt;
    }

    public void setCrdt(Date crdt) {
        this.crdt = crdt;
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

    @Column(name = "MDUSR", length = 10)
    public String getMdusr() {
        return mdusr;
    }

    public void setMdusr(String mdusr) {
        this.mdusr = mdusr;
    }

    @Column(name = "STATUS", length = 2)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "INSURECASE", length = 2)
    public String getInsurecase() {
        return insurecase;
    }

    public void setInsurecase(String insurecase) {
        this.insurecase = insurecase;
    }

    @Column(name = "INSURENOTE", length = 2000)
    public String getInsurenote() {
        return insurenote;
    }

    public void setInsurenote(String insurenote) {
        this.insurenote = insurenote;
    }

    @Column(name = "REFUSEDATE", length = 7)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getRefusedate() {
        return refusedate;
    }

    public void setRefusedate(Date refusedate) {
        this.refusedate = refusedate;
    }

    @Column(name = "ISREUSE", length = 1)
    public String getRefuse() {
        return refuse;
    }

    public void setRefuse(String refuse) {
        this.refuse = refuse;
    }
    @Column(name = "REFUSEDATETIME", length = 7)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getRefuseDateTime() {
        return refuseDateTime;
    }

    public void setRefuseDateTime(Date refuseDateTime) {
        this.refuseDateTime = refuseDateTime;
    }
    @Column(name = "INSURESECCDATE", length = 7)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getInsureSeccDate() {
        return insureSeccDate;
    }

    public void setInsureSeccDate(Date insureSeccDate) {
        this.insureSeccDate = insureSeccDate;
    }
    @Transient
    public Integer getHasinsure() {
        return hasinsure;
    }

    public void setHasinsure(Integer hasinsure) {
        this.hasinsure = hasinsure;
    }

    @Transient
    public String getCallId() {
        return callId;
    }

    public void setCallId(String callId) {
        this.callId = callId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contactinsure that = (Contactinsure) o;

        if (contactid != null ? !contactid.equals(that.contactid) : that.contactid != null) return false;
        if (crdt != null ? !crdt.equals(that.crdt) : that.crdt != null) return false;
        if (crusr != null ? !crusr.equals(that.crusr) : that.crusr != null) return false;
        if (dsc != null ? !dsc.equals(that.dsc) : that.dsc != null) return false;
        if (insurecase != null ? !insurecase.equals(that.insurecase) : that.insurecase != null) return false;
        if (insurenote != null ? !insurenote.equals(that.insurenote) : that.insurenote != null) return false;
        if (isok != null ? !isok.equals(that.isok) : that.isok != null) return false;
        if (mddt != null ? !mddt.equals(that.mddt) : that.mddt != null) return false;
        if (mdusr != null ? !mdusr.equals(that.mdusr) : that.mdusr != null) return false;
        if (refusedate != null ? !refusedate.equals(that.refusedate) : that.refusedate != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = contactid != null ? contactid.hashCode() : 0;
        result = 31 * result + (isok != null ? isok.hashCode() : 0);
        result = 31 * result + (dsc != null ? dsc.hashCode() : 0);
        result = 31 * result + (crdt != null ? crdt.hashCode() : 0);
        result = 31 * result + (crusr != null ? crusr.hashCode() : 0);
        result = 31 * result + (mddt != null ? mddt.hashCode() : 0);
        result = 31 * result + (mdusr != null ? mdusr.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (insurecase != null ? insurecase.hashCode() : 0);
        result = 31 * result + (insurenote != null ? insurenote.hashCode() : 0);
        result = 31 * result + (refusedate != null ? refusedate.hashCode() : 0);
        return result;
    }
}
