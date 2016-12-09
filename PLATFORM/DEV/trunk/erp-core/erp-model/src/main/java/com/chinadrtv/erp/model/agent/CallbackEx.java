package com.chinadrtv.erp.model.agent;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: gaudi(话务分配)
 * Date: 14-1-15
 * Time: 上午10:18
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "CALLBACK", schema = "IAGENT")
public class CallbackEx implements java.io.Serializable {
    private Long callbackId;
    private String contactId;
    private String usrId;
    private String orderId;
    private String status;
    private Date requireDate;
    private String priority;
    private String flag;
    private String phn1;
    private String phn2;
    private String phn3;
    private String remark;
    private String name;
    private String mediaPlanId;
    private Date crdt;
    private String dnis;
    private String areaCode;
    private String type;
    private String mdusr;
    private Date mddt;
    private String ani;
    private String latentContactId;
    private String caseId;
    private String dbusrId;
    private String rdbusrId;
    private Date dbdt;
    private String opusr;

    @Id
    public Long getCallbackId() {
        return callbackId;
    }

    public void setCallbackId(Long callbackId) {
        this.callbackId = callbackId;
    }

    @Column(name = "CONTACTID")
    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    @Column(name = "USRID")
    public String getUsrId() {
        return usrId;
    }

    public void setUsrId(String usrId) {
        this.usrId = usrId;
    }

    @Column(name = "ORDERID")
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Column(name = "STATUS")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "REQUIREDT")
    public Date getRequireDate() {
        return requireDate;
    }

    public void setRequireDate(Date requireDate) {
        this.requireDate = requireDate;
    }

    @Column(name = "PRIORITY")
    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    @Column(name = "FLAG")
    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Column(name = "PHN1")
    public String getPhn1() {
        return phn1;
    }

    public void setPhn1(String phn1) {
        this.phn1 = phn1;
    }

    @Column(name = "PHN2")
    public String getPhn2() {
        return phn2;
    }

    public void setPhn2(String phn2) {
        this.phn2 = phn2;
    }

    @Column(name = "PHN3")
    public String getPhn3() {
        return phn3;
    }

    public void setPhn3(String phn3) {
        this.phn3 = phn3;
    }

    @Column(name = "REMARK")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "MEDIAPLANID")
    public String getMediaPlanId() {
        return mediaPlanId;
    }

    public void setMediaPlanId(String mediaPlanId) {
        this.mediaPlanId = mediaPlanId;
    }

    @Column(name = "CRDT")
    public Date getCrdt() {
        return crdt;
    }

    public void setCrdt(Date crdt) {
        this.crdt = crdt;
    }

    @Column(name = "DNIS")
    public String getDnis() {
        return dnis;
    }

    public void setDnis(String dnis) {
        this.dnis = dnis;
    }

    @Column(name = "AREACODE")
    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    @Column(name = "TYPE")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "MDUSR")
    public String getMdusr() {
        return mdusr;
    }

    public void setMdusr(String mdusr) {
        this.mdusr = mdusr;
    }

    @Column(name = "MDDT")
    public Date getMddt() {
        return mddt;
    }

    public void setMddt(Date mddt) {
        this.mddt = mddt;
    }

    @Column(name = "ANI")
    public String getAni() {
        return ani;
    }

    public void setAni(String ani) {
        this.ani = ani;
    }

    @Column(name = "LATENTCONTACTID")
    public String getLatentContactId() {
        return latentContactId;
    }

    public void setLatentContactId(String latentContactId) {
        this.latentContactId = latentContactId;
    }

    @Column(name = "CASEID")
    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    @Column(name = "DBUSRID")
    public String getDbusrId() {
        return dbusrId;
    }

    public void setDbusrId(String dbusrId) {
        this.dbusrId = dbusrId;
    }

    @Column(name = "RDBUSRID")
    public String getRdbusrId() {
        return rdbusrId;
    }

    public void setRdbusrId(String rdbusrId) {
        this.rdbusrId = rdbusrId;
    }

    @Column(name = "DBDT")
    public Date getDbdt() {
        return dbdt;
    }

    public void setDbdt(Date dbdt) {
        this.dbdt = dbdt;
    }

    @Column(name = "OPUSR")
    public String getOpusr() {
        return opusr;
    }

    public void setOpusr(String opusr) {
        this.opusr = opusr;
    }
}
